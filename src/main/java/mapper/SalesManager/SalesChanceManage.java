package mapper.SalesManager;

import entity.SalChance;

//销售主管、客户经理模块的Mapper接口
public interface SalesChanceManage {
    //根据客户名称或联系人查询销售机会
    int selectSalesChanceByNameOrContactPerson(String name, String contactPerson);
    //插入销售机会
    int insertSalesChance(SalChance salChance);
}
