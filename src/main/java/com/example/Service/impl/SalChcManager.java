package com.example.Service.impl;

import com.example.Service.SalChcManage;
import com.example.entity.SalChance;
import com.example.mapper.SalesManager.SalesChanceManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalChcManager implements SalChcManage {

    @Autowired
    private SalesChanceManage salesChanceManage;

    @Override
    public List<SalChance> getSalChance(String name, String contactTel) {
        // Mapper 接口对象由 Spring 注入，不能 new
        return salesChanceManage.selectSalesChanceByNameOrContactPerson(name, contactTel);
    }

    @Override
    public int insertSalChance(SalChance salChance) {
        return salesChanceManage.insertSalesChance(salChance);
    }

    @Override
    public int editSalChance(int id, SalChance salChance) {
        return salesChanceManage.editSalesChance(id, salChance);
    }

    @Override
    public int deleteSalChance(int id) {
        return salesChanceManage.deleteSalesChance(id);
    }
}
