package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Storage {
    private Integer stkId;
    private Integer stkProdId;
    private String stkWarehouse;
    private String stkWare;
    private Integer stkCount;
    private String stkMemo;
}
