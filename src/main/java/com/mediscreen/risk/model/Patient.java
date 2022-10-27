package com.mediscreen.risk.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gender;

    private String adress;

    private String phone;

}
