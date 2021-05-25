package codeworld.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.health.service.api.CountryService;
import codeworld.health.service.entity.CountryEntity;
import codeworld.health.service.jms.EntityType;
import codeworld.health.service.jms.JMSMessage;
import codeworld.health.service.jms.JMSOperation;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.repository.CountryRepository;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    @Resource
    private CountryRepository countryRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    public static CountryDto convertToDto(CountryEntity data) {
        return CountryDto.builder()
                .id(data.getId())
                .name(data.getName())
                .region(data.getRegion())
                .population(data.getPopulation())
                .build();
    }

    public static CountryEntity convertToEntity(CountryDto data) {
        return CountryEntity.builder()
                .id(data.getId())
                .name(data.getName())
                .region(data.getRegion())
                .population(data.getPopulation())
                .build();
    }

    @Override
    public List<CountryDto> findAll() {
        List<CountryEntity> countryEntities = (List<CountryEntity>) countryRepository.findAll();
        return countryEntities.stream()
                .map(data -> convertToDto(data))
                .collect(Collectors.toList());
    }

    @Override
    public Long create(CountryDto countryDto) {
        CountryEntity entity = countryRepository.save(convertToEntity(countryDto));
        // sendJmsMessage(entity.getId(), JMSOperation.PERSIST, EntityType.COUNTRY);
        return entity.getId();
    }

    private void sendJmsMessage(Long id, JMSOperation operation, EntityType type) {
        JMSMessage message = JMSMessage.builder()
                .id(id)
                .jmsOperation(operation)
                .entityType(type)
                .build();
        jmsTemplate.convertAndSend(queue, message);
    }

    @Override
    public Long update(CountryDto countryDto) {
        Optional<CountryEntity> countryOptional = countryRepository.findById(countryDto.getId());
        if (countryOptional.isPresent()) {
            CountryEntity entity = countryRepository.save(convertToEntity(countryDto));
            // sendJmsMessage(entity.getId(), JMSOperation.UPDATE, EntityType.COUNTRY);
            return entity.getId();
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        Optional<CountryEntity> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent()) {
            countryRepository.delete(countryOptional.get());
            // sendJmsMessage(id, JMSOperation.DELETE, EntityType.COUNTRY);
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }

    @Override
    public CountryDto findById(@NotNull Long id) {
        Optional<CountryEntity> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent()) {
            return convertToDto(countryOptional.get());
        }
        else {
            throw new BadRequestException("Data not found");
        }
    }
}
