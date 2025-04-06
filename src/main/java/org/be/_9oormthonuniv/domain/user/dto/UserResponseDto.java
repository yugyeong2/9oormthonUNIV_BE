package org.be._9oormthonuniv.domain.user.dto;

import lombok.Data;
import org.be._9oormthonuniv.domain.user.entity.User;

@Data
public class UserResponseDto {
    private String username;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}
