package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    private Integer userId;
    private String userName;
    private String userPassword;
    private Integer userRoleId;
    private Integer userFlag;
}
