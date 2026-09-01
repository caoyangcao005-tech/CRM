package dto;

import com.example.entity.CstCustomer;
import com.example.entity.SysUser;

import java.util.List;

public class ServiceMetadata {
    private final List<String> serviceTypes;
    private final List<CstCustomer> customers;
    private final List<SysUser> users;
    private final SysUser currentUser;

    public ServiceMetadata(List<String> serviceTypes,
                           List<CstCustomer> customers,
                           List<SysUser> users,
                           SysUser currentUser) {
        this.serviceTypes = serviceTypes;
        this.customers = customers;
        this.users = users;
        this.currentUser = currentUser;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public List<CstCustomer> getCustomers() {
        return customers;
    }

    public List<SysUser> getUsers() {
        return users;
    }

    public SysUser getCurrentUser() {
        return currentUser;
    }
}
