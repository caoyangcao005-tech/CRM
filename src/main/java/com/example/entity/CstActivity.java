package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CstActivity {
    private Integer atvId;
    private String atvCustNo;
    private String atvCustName;
    private Date atvDate;
    private String atvPlace;
    private String atvTitle;
    private String atvDesc;
}
