package com.yim.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Login {
    private String userName;
    private String password;
    private boolean remember;
}
