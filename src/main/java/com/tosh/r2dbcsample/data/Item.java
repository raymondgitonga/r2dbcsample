package com.tosh.r2dbcsample.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("item")
public class Item {

    @Id
    private String id;
    private String description;
    private Double price;
}
