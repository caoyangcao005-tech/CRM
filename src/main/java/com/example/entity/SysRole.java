package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysRole {
    private Integer roleId;
    private String roleName;
    private String roleDesc;
}
