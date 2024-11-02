package kz.edu.astanait.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private Integer limit;
    private String field;
    private Integer order;

    public int getLimit() {
        return limit == null ? 5 : limit;
    }

    public String getField() {
        return field == null ? "price" : field;
    }

    public Integer getOrder() {
        return order == null ? 1 : order;
    }
}
