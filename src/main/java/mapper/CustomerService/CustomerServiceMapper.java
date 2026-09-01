package mapper.CustomerService;

import dto.ServiceQuery;
import com.example.entity.CstCustomer;
import com.example.entity.CstService;
import com.example.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerServiceMapper {
    List<CstService> findServices(ServiceQuery query);
    long countServices(ServiceQuery query);
    CstService findById(@Param("id") Integer id);
    int insert(CstService service);
    int allot(@Param("id") Integer id, @Param("userId") Integer userId,
              @Param("userName") String userName);
    int deal(@Param("id") Integer id, @Param("deal") String deal,
             @Param("dealId") Integer dealId, @Param("dealBy") String dealBy);
    int feedback(@Param("id") Integer id, @Param("result") String result,
                 @Param("satisfy") Integer satisfy, @Param("nextStatus") String nextStatus);
    int deleteNew(@Param("id") Integer id);
    List<String> findServiceTypes();
    List<CstCustomer> findCustomers(@Param("keyword") String keyword);
    List<SysUser> findActiveUsers();
}
