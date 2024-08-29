package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role implements GrantedAuthority {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
     
    private String name;

    @ManyToMany(mappedBy = "roles")
    private  List<User> users;
    @Override
    public String getAuthority() {
        return name;
    }
    
}
