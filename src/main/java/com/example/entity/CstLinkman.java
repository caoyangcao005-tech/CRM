package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CstLinkman {
    private Integer lkmId;
    private String lkmCustNo;
    private String lkmCustName;
    private String lkmName;
    private String lkmSex;
    private String lkmPostion;
    private String lkmTel;
    private String lkmMobile;
    private String lkmMemo;
}
