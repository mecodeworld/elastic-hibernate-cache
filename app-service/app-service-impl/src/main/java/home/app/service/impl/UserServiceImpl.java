package home.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import home.app.api.model.User;
import home.app.api.service.UserService;
import home.app.service.entity.UserEntity;
import home.app.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {

        List<User> data = new ArrayList<>();

        Iterable<UserEntity> resultList = userRepository.findAll();
        resultList.forEach(f1 -> {
            log.info("User is " + f1.getUsername());
            data.add(convertToUser(f1));
        });
        return data;
    }

    private User convertToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .active(userEntity.isActive())
                .build();
    }

}
