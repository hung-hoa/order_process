package com.example.order_process.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String userId;
    private List<String> items;
    private double totalPrice;
    private String status;
}