package com.step.forum.spring.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
public class User implements UserDetails {
    private int id;
    @NotNull
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email pattern not match")
    private String email;
    @NotNull
    @NotBlank(message = "Password must not be blank")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20")
    private String password;
    @NotNull
    @NotBlank(message = "Last Name must not be blank")
    private String lastName;
    @NotNull
    @NotBlank(message = "First Name must not be blank")
    private String firstName;
    private String token;
    private int status;
    private String imagePath;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleType());
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(grantedAuthority);
        return list;
    }

    @Override
    public String getUsername() {
        return email;
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
