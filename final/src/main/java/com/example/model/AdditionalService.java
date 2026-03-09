package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
@Data
public class AdditionalService {

    @Id
    private String id;
    private String name;
    private double price;
    private String description;
    private String category;
    private boolean available;
}
