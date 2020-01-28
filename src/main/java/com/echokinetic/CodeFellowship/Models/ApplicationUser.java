package com.echokinetic.CodeFellowship.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(mappedBy = "user")
    public List<Post> posts;

    String username;
    String password;

    String first_Name;
    String last_Name;
    Date date_Of_Birth;
    String bio;

    public ApplicationUser() {};

    public ApplicationUser(String username, String password, String first_Name, String last_Name, Date date_Of_Birth, String bio)
    {
        this.username = username;
        this.password = password;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.date_Of_Birth = date_Of_Birth;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
