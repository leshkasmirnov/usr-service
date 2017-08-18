package com.asmirnov.usrservice.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * User Api Model.
 *
 * Created by Alexey Smirnov (aleksey.smirnov89@gmail.com) on 17/08/2017.
 */
@ApiModel
public class UserModel {

    @ApiModelProperty
    private Long id;
    @ApiModelProperty
    @NotNull
    private String username;
    @ApiModelProperty
    @NotNull
    private String firstName;
    @ApiModelProperty
    @NotNull
    private String lastName;
    @ApiModelProperty
    private String email;
    @ApiModelProperty
    @NotNull
    private String password;
    @ApiModelProperty
    private String phone;
    @ApiModelProperty
    private Integer userStatus;

    public UserModel() {
    }

    public UserModel(Long id, String username, String firstName, String lastName, String email, String password, String phone, Integer userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserStatus() {
        if (userStatus == null) {
            userStatus = 1;
        }
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
