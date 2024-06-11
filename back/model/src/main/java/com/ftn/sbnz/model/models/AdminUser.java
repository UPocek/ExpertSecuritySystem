package com.ftn.sbnz.model.models;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class AdminUser extends AbstractUser implements Serializable {
    public AdminUser() {
    }

    public AdminUser(String name, String username, String password) {
        super(name, username, password);
    }
}
