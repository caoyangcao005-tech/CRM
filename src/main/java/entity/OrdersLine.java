package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersLine {
    private Integer oddId;
    private Integer oddOrderId;
    private Integer oddProdId;
    private Integer oddCount;
    private String oddUnit;
    private Double oddPrice;
}
