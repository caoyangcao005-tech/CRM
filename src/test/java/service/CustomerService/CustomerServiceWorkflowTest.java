package service.CustomerService;

import common.PageResult;
import config.RootConfig;
import dto.ServiceAllotRequest;
import dto.ServiceCreateRequest;
import dto.ServiceDealRequest;
import dto.ServiceFeedbackRequest;
import dto.ServiceQuery;
import com.example.entity.CstService;
import com.example.entity.SysUser;
import exception.BusinessException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class CustomerServiceWorkflowTest {

    @Autowired
    private CustomerServiceManagementService service;

    @Test
    public void completeServiceWorkflowAndReworkRule() {
        SysUser manager = user(4, "客户经理小李", 3);

        ServiceCreateRequest create = new ServiceCreateRequest();
        create.setCustomerNo("KH202608310000002");
        create.setCustomerName("上海未来科技有限公司");
        create.setType("投诉");
        create.setTitle("集成测试服务");
        create.setRequest("验证服务管理的完整状态流转。");
        CstService created = service.create(create, manager);
        Assert.assertNotNull(created.getSvrId());
        Assert.assertEquals("新创建", created.getSvrStatus());
        Assert.assertEquals(manager.getUserId(), created.getSvrCreateId());

        ServiceAllotRequest allot = new ServiceAllotRequest();
        allot.setUserId(manager.getUserId());
        CstService assigned = service.allot(created.getSvrId(), allot);
        Assert.assertEquals("已分配", assigned.getSvrStatus());
        Assert.assertNotNull(assigned.getSvrDueDate());

        ServiceDealRequest deal = new ServiceDealRequest();
        deal.setDeal("已联系客户并完成第一次处理。");
        CstService processed = service.deal(created.getSvrId(), deal, manager);
        Assert.assertEquals("已处理", processed.getSvrStatus());
        Assert.assertEquals(manager.getUserId(), processed.getSvrDealId());
        Assert.assertNotNull(processed.getSvrDealDate());

        ServiceFeedbackRequest dissatisfied = new ServiceFeedbackRequest();
        dissatisfied.setResult("问题仍需继续处理");
        dissatisfied.setSatisfy(2);
        CstService rework = service.feedback(created.getSvrId(), dissatisfied, manager);
        Assert.assertEquals("已分配", rework.getSvrStatus());

        deal.setDeal("完成补充处理并由客户确认。");
        service.deal(created.getSvrId(), deal, manager);
        ServiceFeedbackRequest satisfied = new ServiceFeedbackRequest();
        satisfied.setResult("客户确认问题已经解决");
        satisfied.setSatisfy(5);
        CstService archived = service.feedback(created.getSvrId(), satisfied, manager);
        Assert.assertEquals("已归档", archived.getSvrStatus());

        ServiceQuery archiveQuery = new ServiceQuery();
        archiveQuery.setStatus("已归档");
        archiveQuery.setTitle("集成测试服务");
        PageResult<CstService> archive = service.list(archiveQuery);
        Assert.assertEquals(1, archive.getTotal());
    }

    @Test(expected = BusinessException.class)
    public void onlyNewServiceCanBeDeleted() {
        service.deleteNew(4);
    }

    private SysUser user(int id, String name, int roleId) {
        SysUser user = new SysUser();
        user.setUserId(id);
        user.setUserName(name);
        user.setUserRoleId(roleId);
        user.setUserFlag(1);
        return user;
    }
}
