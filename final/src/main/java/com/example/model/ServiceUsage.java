package com.example.model;

import java.time.LocalDateTime;

public class ServiceUsage {

    private String serviceId;
    private String serviceName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private LocalDateTime usageDate;

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public LocalDateTime getUsageDate() { return usageDate; }
    public void setUsageDate(LocalDateTime usageDate) { this.usageDate = usageDate; }
}
