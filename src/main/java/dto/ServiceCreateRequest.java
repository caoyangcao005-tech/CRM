package dto;

import lombok.Data;

@Data
public class ServiceCreateRequest {
    private String customerNo;
    private String customerName;
    private String type;
    private String title;
    private String request;
}
