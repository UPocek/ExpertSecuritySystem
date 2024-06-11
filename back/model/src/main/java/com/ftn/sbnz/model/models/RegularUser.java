package com.ftn.sbnz.model.models;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class RegularUser extends AbstractUser implements Serializable {
    public RegularUser() {

    }

    public RegularUser(String name, String username, String password) {
        super(name, username, password);
    }
}
