package com.example.Service.impl;

import com.example.Service.SalChcManage;
import com.example.entity.SalChance;
import com.example.mapper.SalesManager.SalesChanceManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        // 需求3.1.1：创建时间=当前系统时间，新机会状态默认"未指派(1)"
        if (salChance.getChcCreateDate() == null) {
            salChance.setChcCreateDate(new Date());
        }
        if (salChance.getChcStatus() == null) {
            salChance.setChcStatus(1);
        }
        return salesChanceManage.insertSalesChance(salChance);
    }

    @Override
    public int editSalChance(int id, SalChance salChance) {
        return salesChanceManage.editSalesChance(id, salChance);
    }

    @Override
    public int deleteSalChance(int id) {
        // 需求3.1.3：只有"未分配"状态(1)的销售机会才能删除
        if (salesChanceManage.judgeSalesChanceStatus(id) == 1) {
            return salesChanceManage.deleteSalesChance(id);
        }
        return -1; // 已分配或开发中的机会不可删除
    }

    @Override
    public int judgeSalesChanceStatus(int id) {
        return salesChanceManage.judgeSalesChanceStatus(id);
    }
}
