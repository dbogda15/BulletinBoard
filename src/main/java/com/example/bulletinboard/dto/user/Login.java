package com.example.bulletinboard.dto.user;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class Login {
    @Length(min = 8, max = 16)
    private String username;

    @Length(min = 8, max = 32)
    private String password;
}
