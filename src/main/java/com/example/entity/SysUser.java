package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private Integer userId;
    private String userName;
    @JsonIgnore
    private String userPassword;
    private Integer userRoleId;
    private Integer userFlag;
}
