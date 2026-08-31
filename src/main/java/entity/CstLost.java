package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CstLost {
    private Integer lstId;
    private String lstCustNo;
    private String lstCustName;
    private Integer lstCustManagerId;
    private String lstCustManagerName;
    private Date lstLastOrderDate;
    private Date lstLostDate;
    private String lstDelay;
    private String lstReason;
    private String lstStatus;
}
