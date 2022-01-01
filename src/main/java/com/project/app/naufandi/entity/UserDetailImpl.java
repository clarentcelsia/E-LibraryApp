package com.project.app.naufandi.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailImpl implements UserDetails {

    private String id;

    private String identityNumber;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private String birthDate;

    private String photo;

    private String username;

    private String password;

    private String status;

    private Date createdAt;

    private Date updatedAt;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserDetailImpl(
                user.getId(),
                user.getIdentityNumber(),
                user.getName(),
                user.getAddress(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getPhoto(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                authorities
        );
    }

    public UserDetailImpl(String id, String identityNumber, String name, String address, String email, String phoneNumber, String birthDate, String photo, String username, String password, String status, Date createdAt, Date updatedAt, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.identityNumber = identityNumber;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.photo = photo;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorities = authorities;
    }

    public UserDetailImpl() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
