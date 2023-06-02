package com.example.springWebTask.entity;


import lombok.Data;

@Data
public class DisplayProduct {
    Integer id;
    String product_id;
    String name;
    int price;
    String category_name;
    String description;

    public DisplayProduct(){

    }

}


