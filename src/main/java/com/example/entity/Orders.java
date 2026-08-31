package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Integer odrId;
    private String odrCustomer;
    private Date odrDate;
    private String odrAddr;
    private String odrStatus;
}
