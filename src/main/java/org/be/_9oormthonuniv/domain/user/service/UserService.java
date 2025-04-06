package org.be._9oormthonuniv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.domain.user.dto.UserResponseDto;
import org.be._9oormthonuniv.domain.user.dto.UserSignupRequestDto;
import org.be._9oormthonuniv.domain.user.entity.User;
import org.be._9oormthonuniv.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto signup(UserSignupRequestDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}