package org.sinhasoft.basicspringboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserSearchDTO implements Serializable {
    private String name;
    private String surname;
    private String email;
    private Integer age;
}
