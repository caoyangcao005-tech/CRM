package controller;

import common.ApiResponse;
import common.PageResult;
import dto.ServiceAllotRequest;
import dto.ServiceCreateRequest;
import dto.ServiceDealRequest;
import dto.ServiceFeedbackRequest;
import dto.ServiceMetadata;
import dto.ServiceQuery;
import com.example.entity.CstService;
import com.example.entity.SysUser;
import exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CustomerService.CustomerServiceManagementService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/customer-services")
public class CustomerServiceController {
    private final CustomerServiceManagementService service;

    public CustomerServiceController(CustomerServiceManagementService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<PageResult<CstService>> list(@ModelAttribute ServiceQuery query,
                                                    HttpSession session) {
        SysUser currentUser = currentUser(session);
        if ("已归档".equals(query.getStatus())) {
            requireRole(currentUser, 1, 2, 3);
        }
        if (query.isOnlyMine() && currentUser != null) {
            query.setDueUserId(currentUser.getUserId());
        }
        return ApiResponse.ok(service.list(query));
    }

    @GetMapping("/metadata")
    public ApiResponse<ServiceMetadata> metadata(
            @RequestParam(value = "customerKeyword", required = false) String customerKeyword,
            HttpSession session) {
        return ApiResponse.ok(service.metadata(customerKeyword, currentUser(session)));
    }

    @GetMapping("/{id}")
    public ApiResponse<CstService> detail(@PathVariable Integer id) {
        return ApiResponse.ok(service.detail(id));
    }

    @PostMapping
    public ApiResponse<CstService> create(@RequestBody ServiceCreateRequest request,
                                          HttpSession session) {
        SysUser user = currentUser(session);
        requireRole(user, 1, 3);
        return ApiResponse.ok("服务创建成功", service.create(request, user));
    }

    @PutMapping("/{id}/allot")
    public ApiResponse<CstService> allot(@PathVariable Integer id,
                                         @RequestBody ServiceAllotRequest request,
                                         HttpSession session) {
        requireRole(currentUser(session), 1, 2);
        return ApiResponse.ok("服务分配成功", service.allot(id, request));
    }

    @PutMapping("/{id}/deal")
    public ApiResponse<CstService> deal(@PathVariable Integer id,
                                        @RequestBody ServiceDealRequest request,
                                        HttpSession session) {
        SysUser user = currentUser(session);
        requireRole(user, 1, 3);
        return ApiResponse.ok("服务处理成功", service.deal(id, request, user));
    }

    @PutMapping("/{id}/feedback")
    public ApiResponse<CstService> feedback(@PathVariable Integer id,
                                            @RequestBody ServiceFeedbackRequest request,
                                            HttpSession session) {
        SysUser user = currentUser(session);
        requireRole(user, 1, 3);
        CstService updated = service.feedback(id, request, user);
        String message = "已归档".equals(updated.getSvrStatus())
                ? "服务反馈成功，已归档"
                : "满意度低于 3 分，服务已退回重新处理";
        return ApiResponse.ok(message, updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id, HttpSession session) {
        requireRole(currentUser(session), 1, 2);
        service.deleteNew(id);
        return ApiResponse.ok("服务删除成功", null);
    }

    private SysUser currentUser(HttpSession session) {
        if (session == null) {
            return null;
        }
        String[] names = {"loginUser", "currentUser", "user"};
        for (String name : names) {
            Object value = session.getAttribute(name);
            if (value instanceof SysUser) {
                return (SysUser) value;
            }
        }
        return null;
    }

    private void requireRole(SysUser user, int... allowedRoles) {
        // The original project has no login controller yet. Once a login session exists,
        // enforce the roles required by section 3.3; anonymous access remains available
        // so the supplied static demonstration pages can be run independently.
        if (user == null || user.getUserRoleId() == null) {
            return;
        }
        for (int role : allowedRoles) {
            if (user.getUserRoleId() == role) {
                return;
            }
        }
        throw new BusinessException(HttpStatus.FORBIDDEN, "当前用户没有执行该操作的权限");
    }
}
