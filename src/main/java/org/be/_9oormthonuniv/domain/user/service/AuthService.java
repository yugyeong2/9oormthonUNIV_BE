package org.be._9oormthonuniv.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.domain.user.config.JwtTokenProvider;
import org.be._9oormthonuniv.domain.user.dto.LoginRequest;
import org.be._9oormthonuniv.domain.user.dto.SignupRequest;
import org.be._9oormthonuniv.domain.user.entity.User;
import org.be._9oormthonuniv.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void signup(SignupRequest request) {
        // 사용자 이름이 데이터베이스에 이미 존재하면 에러
        if (userRepository.findByUsername(request.username()).isPresent())
            throw new RuntimeException("이미 존재하는 사용자");

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password())) // 비밀번호 암호화
                .build();

        userRepository.save(user);
    }

    // 로그인
    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // 입력받은 토큰에 저장된 비밀번호
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new RuntimeException("비밀번호 불일치");

        // 사용자 이름을 저장한 토큰 생성
        return jwtTokenProvider.generateToken(user.getUsername());
    }
}
