package com.example.mapper.SalesManager;

import com.example.entity.SalChance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//销售主管、客户经理模块的Mapper接口
public interface SalesChanceManage {
    //根据客户名称或联系人查询销售机会
    List<SalChance> selectSalesChanceByNameOrContactPerson(@Param("name") String name, @Param("contactPerson") String contactPerson);

    //插入销售机会
    int insertSalesChance(SalChance salChance);

    //编辑信息
    int editSalesChance(@Param("id") int id, @Param("salChance") SalChance salChance);

    //删除销售机会
    int deleteSalesChance(@Param("id") int id);

}
