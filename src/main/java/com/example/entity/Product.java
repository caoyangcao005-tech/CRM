package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer prodId;
    private String prodName;
    private String prodType;
    private String prodBatch;
    private String prodUnit;
    private Double prodPrice;
    private String prodMemo;
}
