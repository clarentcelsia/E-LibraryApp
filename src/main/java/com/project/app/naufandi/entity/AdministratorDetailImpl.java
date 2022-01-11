package com.project.app.naufandi.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public AdministratorDetailImpl(String id, String identityNumber, String name, String email, String address, String phoneNumber, String username, String password, Boolean status, Date createdAt, Date updatedAt, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.identityNumber = identityNumber;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorities = authorities;
    }

    public AdministratorDetailImpl() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
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
