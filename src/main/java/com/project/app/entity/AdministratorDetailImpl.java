package com.project.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDetailImpl implements UserDetails {
    private String id;

    private String identityNumber;

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

    private String username;

    private String password;

    private Boolean status;

    private Date createdAt;

    private Date updatedAt;

    private Collection<? extends GrantedAuthority> authorities;

    public static AdministratorDetailImpl build(Administrator administrator) {
        List<GrantedAuthority> authorities = administrator.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new AdministratorDetailImpl(
                administrator.getId(),
                administrator.getIdentityNumber(),
                administrator.getName(),
                administrator.getEmail(),
                administrator.getAddress(),
                administrator.getPhoneNumber(),
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getStatus(),
                administrator.getCreatedAt(),
                administrator.getUpdatedAt(),
                authorities
        );
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
