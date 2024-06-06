package com.ftn.sbnz.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserCredentialsDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min = 6)
    private String password;

    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserCredentialsDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
