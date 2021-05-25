package codeworld.elastic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.elastic.service.api.ECountryService;
import codeworld.elastic.service.document.ECity;
import codeworld.elastic.service.document.ECountry;
import codeworld.elastic.service.document.EState;
import codeworld.elastic.service.repository.ECountryRepository;
import codeworld.health.service.model.CityDto;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.model.StateDto;

@Service
@Transactional
public class ECountryServiceImpl implements ECountryService {

    @Resource
    private ECountryRepository eCountryRepository;

    public static CountryDto convertToDto(ECountry data) {
        return CountryDto.builder()
                .id(data.getId())
                .name(data.getName())
                .region(data.getRegion())
                .population(data.getPopulation())
                .build();
    }

    public static ECountry convertToDocument(CountryDto data) {
        return ECountry.builder()
                .id(data.getId())
                .name(data.getName())
                .region(data.getRegion())
                .population(data.getPopulation())
                // .states(convertToStateDocument(data.getStateDtos()))
                .build();
    }

    private static List<EState> convertToStateDocument(List<StateDto> stateDtos) {
        List<EState> stateList = new ArrayList<EState>();
        for (StateDto stateDto : stateDtos) {
            EState eState = EState.builder()
                    .id(stateDto.getId())
                    .name(stateDto.getName())
                    .population(stateDto.getPopulation())
                    .cities(convertToCityDoc(stateDto.getCityDtos()))
                    .build();
            stateList.add(eState);
        }
        return stateList;
    }

    private static List<ECity> convertToCityDoc(List<CityDto> cityDtos) {
        List<ECity> result = new ArrayList<ECity>();
        for (CityDto cityDto : cityDtos) {
            ECity eCity = ECity.builder()
                    .id(cityDto.getId())
                    .name(cityDto.getName())
                    .population(cityDto.getPopulation())
                    .build();
            result.add(eCity);
        }
        return result;
    }

    @Override
    public List<CountryDto> findAll() {
        List<CountryDto> resultList = new ArrayList<CountryDto>();

        Iterable<ECountry> result = eCountryRepository.findAll();
        result.forEach(data -> {
            resultList.add(convertToDto(data));
        });
        return resultList;
    }

    @Override
    public Long create(CountryDto countryDto) {
        ECountry entity = eCountryRepository.save(convertToDocument(countryDto));
        return entity.getId();
    }

    @Override
    public Long update(CountryDto countryDto) {
        ECountry entity = eCountryRepository.save(convertToDocument(countryDto));
        return entity.getId();
    }

    @Override
    public void delete(@NotNull Long id) {
        Optional<ECountry> countryOptional = eCountryRepository.findById(id);
        if (countryOptional.isPresent()) {
            eCountryRepository.delete(countryOptional.get());
        }
    }

}
