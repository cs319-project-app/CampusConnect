package com.fullstackfidelity.campusconnect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPassword {
    String password;
    String bilkentMail;
    String code;
}
