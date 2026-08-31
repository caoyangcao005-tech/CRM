package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRight {
    private Integer rightId;
    private String rightParentId;
    private Integer rightType;
    private String rightText;
    private String rightUrl;
}
