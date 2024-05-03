package com.Dice.Weather.Dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthRequestDto {
    private String username;
    private String password;

}
