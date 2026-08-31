package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalChance {
    private Integer chcId;
    private String chcSource;
    private String chcCustName;
    private String chcTitle;
    private Integer chcRate;
    private String chcLinkman;
    private String chcTel;
    private String chcDesc;
    private Integer chcCreateId;
    private String chcCreateBy;
    private Date chcCreateDate;
    private Integer chcDueId;
    private String chcDueTo;
    private Date chcDueDate;
    private Integer chcStatus;
}
