package service.CustomerService.impl;

import common.PageResult;
import dto.ServiceAllotRequest;
import dto.ServiceCreateRequest;
import dto.ServiceDealRequest;
import dto.ServiceFeedbackRequest;
import dto.ServiceMetadata;
import dto.ServiceQuery;
import com.example.entity.CstCustomer;
import com.example.entity.CstService;
import com.example.entity.SysUser;
import exception.BusinessException;
import mapper.CustomerService.CustomerServiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.CustomerService.CustomerServiceManagementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceManagementServiceImpl implements CustomerServiceManagementService {
    public static final String STATUS_NEW = "新创建";
    public static final String STATUS_ASSIGNED = "已分配";
    public static final String STATUS_PROCESSED = "已处理";
    public static final String STATUS_ARCHIVED = "已归档";

    private final CustomerServiceMapper mapper;

    public CustomerServiceManagementServiceImpl(CustomerServiceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PageResult<CstService> list(ServiceQuery query) {
        ServiceQuery safeQuery = query == null ? new ServiceQuery() : query;
        safeQuery.normalize();
        long total = mapper.countServices(safeQuery);
        List<CstService> items = total == 0
                ? new ArrayList<CstService>()
                : mapper.findServices(safeQuery);
        return new PageResult<>(total, safeQuery.getPage(), safeQuery.getPageSize(), items);
    }

    @Override
    public CstService detail(Integer id) {
        return requiredService(id);
    }

    @Override
    public ServiceMetadata metadata(String customerKeyword, SysUser currentUser) {
        List<String> types = mapper.findServiceTypes();
        if (types == null || types.isEmpty()) {
            types = Arrays.asList("咨询", "投诉", "建议");
        }
        return new ServiceMetadata(types,
                mapper.findCustomers(trimToNull(customerKeyword)),
                mapper.findActiveUsers(), currentUser);
    }

    @Override
    @Transactional
    public CstService create(ServiceCreateRequest request, SysUser currentUser) {
        if (request == null) {
            throw new BusinessException("请填写服务信息");
        }
        String customerName = requiredText(request.getCustomerName(), "客户名称不能为空", 100);
        String type = requiredText(request.getType(), "服务类型不能为空", 20);
        String title = requiredText(request.getTitle(), "服务概要不能为空", 500);
        String serviceRequest = requiredText(request.getRequest(), "服务请求不能为空", 3000);
        requireDictionaryType(type);
        CstCustomer customer = findCustomer(request.getCustomerNo(), customerName);
        if (customer == null) {
            throw new BusinessException("请选择系统中状态正常的客户");
        }
        SysUser creator = resolveCreator(currentUser);

        CstService service = new CstService();
        service.setSvrType(type);
        service.setSvrTitle(title);
        service.setSvrCustNo(customer.getCustNo());
        service.setSvrCustName(customer.getCustName());
        service.setSvrStatus(STATUS_NEW);
        service.setSvrRequest(serviceRequest);
        service.setSvrCreateId(creator.getUserId());
        service.setSvrCreateBy(creator.getUserName());
        service.setSvrCreateDate(new Date());
        if (mapper.insert(service) != 1) {
            throw new BusinessException("服务创建失败");
        }
        return requiredService(service.getSvrId());
    }

    @Override
    @Transactional
    public CstService allot(Integer id, ServiceAllotRequest request) {
        if (request == null || request.getUserId() == null) {
            throw new BusinessException("请选择分配对象");
        }
        CstService service = requiredService(id);
        requireStatus(service, STATUS_NEW, "只有“新创建”的服务可以分配");
        SysUser dueUser = findActiveUser(request.getUserId());
        if (mapper.allot(id, dueUser.getUserId(), dueUser.getUserName()) != 1) {
            throw new BusinessException("服务状态已变化，请刷新后重试");
        }
        return requiredService(id);
    }

    @Override
    @Transactional
    public CstService deal(Integer id, ServiceDealRequest request, SysUser currentUser) {
        String deal = requiredText(request == null ? null : request.getDeal(), "处理方法不能为空", 3000);
        CstService service = requiredService(id);
        requireStatus(service, STATUS_ASSIGNED, "只有“已分配”的服务可以处理");
        SysUser handler = resolveHandler(service, currentUser);
        if (mapper.deal(id, deal, handler.getUserId(), handler.getUserName()) != 1) {
            throw new BusinessException("服务状态已变化，请刷新后重试");
        }
        return requiredService(id);
    }

    @Override
    @Transactional
    public CstService feedback(Integer id, ServiceFeedbackRequest request, SysUser currentUser) {
        String result = requiredText(request == null ? null : request.getResult(), "处理结果不能为空", 500);
        Integer satisfy = request == null ? null : request.getSatisfy();
        if (satisfy == null || satisfy < 1 || satisfy > 5) {
            throw new BusinessException("满意度必须是 1 到 5 分");
        }
        CstService service = requiredService(id);
        requireStatus(service, STATUS_PROCESSED, "只有“已处理”的服务可以反馈");
        verifyAssignedUser(service, currentUser);
        String nextStatus = satisfy >= 3 ? STATUS_ARCHIVED : STATUS_ASSIGNED;
        if (mapper.feedback(id, result, satisfy, nextStatus) != 1) {
            throw new BusinessException("服务状态已变化，请刷新后重试");
        }
        return requiredService(id);
    }

    @Override
    @Transactional
    public void deleteNew(Integer id) {
        requiredService(id);
        if (mapper.deleteNew(id) != 1) {
            throw new BusinessException("只能删除“新创建”的服务");
        }
    }

    private CstService requiredService(Integer id) {
        if (id == null) {
            throw new BusinessException("服务编号不能为空");
        }
        CstService service = mapper.findById(id);
        if (service == null) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "未找到该服务记录");
        }
        return service;
    }

    private CstCustomer findCustomer(String customerNo, String customerName) {
        List<CstCustomer> customers = mapper.findCustomers(trimToNull(customerName));
        if (customers == null) {
            return null;
        }
        String no = trimToNull(customerNo);
        for (CstCustomer customer : customers) {
            if (no != null && no.equals(customer.getCustNo())) {
                return customer;
            }
            if (no == null && customerName.equals(customer.getCustName())) {
                return customer;
            }
        }
        return null;
    }

    private SysUser resolveCreator(SysUser currentUser) {
        if (currentUser != null && currentUser.getUserId() != null) {
            return currentUser;
        }
        List<SysUser> users = mapper.findActiveUsers();
        if (users != null && !users.isEmpty()) {
            for (SysUser user : users) {
                if (Integer.valueOf(3).equals(user.getUserRoleId())) {
                    return user;
                }
            }
            return users.get(0);
        }
        SysUser fallback = new SysUser();
        fallback.setUserId(1);
        fallback.setUserName("系统管理员");
        return fallback;
    }

    private SysUser resolveHandler(CstService service, SysUser currentUser) {
        verifyAssignedUser(service, currentUser);
        if (currentUser != null && currentUser.getUserId() != null) {
            return currentUser;
        }
        SysUser assigned = new SysUser();
        assigned.setUserId(service.getSvrDueId());
        assigned.setUserName(service.getSvrDueTo());
        return assigned;
    }

    private void verifyAssignedUser(CstService service, SysUser currentUser) {
        if (currentUser != null && currentUser.getUserId() != null
                && service.getSvrDueId() != null
                && !currentUser.getUserId().equals(service.getSvrDueId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "该服务已分配给其他客户经理");
        }
    }

    private SysUser findActiveUser(Integer userId) {
        List<SysUser> users = mapper.findActiveUsers();
        if (users != null) {
            for (SysUser user : users) {
                if (userId.equals(user.getUserId())) {
                    return user;
                }
            }
        }
        throw new BusinessException("分配对象不存在或已停用");
    }

    private void requireDictionaryType(String type) {
        List<String> types = mapper.findServiceTypes();
        if (types == null || types.isEmpty()) {
            types = Arrays.asList("咨询", "投诉", "建议");
        }
        if (!types.contains(type)) {
            throw new BusinessException("请选择数据字典中有效的服务类型");
        }
    }

    private void requireStatus(CstService service, String status, String message) {
        if (!status.equals(service.getSvrStatus())) {
            throw new BusinessException(message);
        }
    }

    private String requiredText(String value, String message, int maxLength) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            throw new BusinessException(message);
        }
        if (trimmed.length() > maxLength) {
            throw new BusinessException("内容不能超过 " + maxLength + " 个字符");
        }
        return trimmed;
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}
