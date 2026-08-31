package com.example.mapper.SalesManager;

import com.example.entity.SalChance;

//销售主管、客户经理模块的Mapper接口
public interface SalesChanceManage {
    //根据客户名称或联系人查询销售机会
    int selectSalesChanceByNameOrContactPerson(String name, String contactPerson);
    //插入销售机会
    int insertSalesChance(SalChance salChance);
    //编辑信息
    int editSalesChance(int id,SalChance salChance);
    //删除销售机会
    int deleteSalesChance(int id);

}
