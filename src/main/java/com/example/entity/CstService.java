package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CstService {
    private Integer svrId;
    private String svrType;
    private String svrTitle;
    private String svrCustNo;
    private String svrCustName;
    private String svrStatus;
    private String svrRequest;
    private Integer svrCreateId;
    private String svrCreateBy;
    private Date svrCreateDate;
    private Integer svrDueId;
    private String svrDueTo;
    private Date svrDueDate;
    private String svrDeal;
    private Integer svrDealId;
    private String svrDealBy;
    private Date svrDealDate;
    private String svrResult;
    private Integer svrSatisfy;
}
