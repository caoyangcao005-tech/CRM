package dto;

import lombok.Data;

@Data
public class ServiceFeedbackRequest {
    private String result;
    private Integer satisfy;
}
