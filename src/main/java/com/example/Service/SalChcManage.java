package com.example.Service;

import com.example.entity.SalChance;

import java.util.List;

public interface SalChcManage {
    List<SalChance> getSalChance(String name, String contactTel);
    int insertSalChance(SalChance salChance);
    int editSalChance(int id, SalChance salChance);
    int deleteSalChance(int id);
    int judgeSalesChanceStatus(int id);
}
