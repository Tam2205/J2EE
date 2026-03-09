package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceUsage {

    private String serviceId;
    private String serviceName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private LocalDateTime usageDate;
}
