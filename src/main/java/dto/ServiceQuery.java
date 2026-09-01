package dto;

import lombok.Data;

@Data
public class ServiceQuery {
    private String customerName;
    private String title;
    private String type;
    private String status;
    private String startDate;
    private String endDate;
    private Integer dueUserId;
    private boolean onlyMine;
    private Integer page = 1;
    private Integer pageSize = 10;

    public void normalize() {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        } else if (pageSize > 100) {
            pageSize = 100;
        }
        customerName = trimToNull(customerName);
        title = trimToNull(title);
        type = trimToNull(type);
        status = trimToNull(status);
        startDate = trimToNull(startDate);
        endDate = trimToNull(endDate);
    }

    public int getOffset() {
        return (page - 1) * pageSize;
    }

    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty() || "全部".equals(value.trim())) {
            return null;
        }
        return value.trim();
    }
}
