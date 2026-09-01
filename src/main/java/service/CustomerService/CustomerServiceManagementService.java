package service.CustomerService;

import common.PageResult;
import dto.ServiceAllotRequest;
import dto.ServiceCreateRequest;
import dto.ServiceDealRequest;
import dto.ServiceFeedbackRequest;
import dto.ServiceMetadata;
import dto.ServiceQuery;
import com.example.entity.CstService;
import com.example.entity.SysUser;

public interface CustomerServiceManagementService {
    PageResult<CstService> list(ServiceQuery query);
    CstService detail(Integer id);
    ServiceMetadata metadata(String customerKeyword, SysUser currentUser);
    CstService create(ServiceCreateRequest request, SysUser currentUser);
    CstService allot(Integer id, ServiceAllotRequest request);
    CstService deal(Integer id, ServiceDealRequest request, SysUser currentUser);
    CstService feedback(Integer id, ServiceFeedbackRequest request, SysUser currentUser);
    void deleteNew(Integer id);
}
