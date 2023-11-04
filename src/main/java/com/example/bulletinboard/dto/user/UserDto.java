package com.example.bulletinboard.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String image;

    private Role role;
}
