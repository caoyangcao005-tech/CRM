package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CstCustomer {
    private String custNo;
    private String custName;
    private String custRegion;
    private Integer custManagerId;
    private String custManagerName;
    private Integer custLevel;
    private Integer custSatisfy;
    private Integer custCredit;
    private String custAddr;
    private String custZip;
    private String custTel;
    private String custFax;
    private String custWebsite;
    private String custLicenceNo;
    private String custChieftain;
    private Integer custBankroll;
    private Integer custTurnover;
    private String custBank;
    private String custBankAccount;
    private String custLocalTaxNo;
    private String custNationalTaxNo;
    private String custStatus;
}
