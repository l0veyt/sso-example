package com.l0veyt.example.ssoserver.service;

import com.l0veyt.example.ssoserver.model.User;
import com.l0veyt.example.ssoserver.pojo.dto.LoginDto;
import com.l0veyt.example.ssoserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loginVerify(LoginDto loginDto) {
        User user = new User();
        user.setUsername(loginDto.getUsername());
        return userRepository.findOne(Example.of(user));
    }
}
