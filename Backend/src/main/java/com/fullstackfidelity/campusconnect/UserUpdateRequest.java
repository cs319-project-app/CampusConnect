package com.fullstackfidelity.campusconnect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private Optional<String> username;
    private Optional<String> password;
    private  Optional<String> profilePath;
}
