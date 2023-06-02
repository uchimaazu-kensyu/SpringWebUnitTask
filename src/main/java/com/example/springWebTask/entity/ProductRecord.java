package com.example.springWebTask.entity;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRecord {
    Integer id;

    @NotEmpty(message = "商品IDは必須です")
    String product_id;

    int category_id;

    @NotEmpty(message = "商品名は必須です")
    String name;

    @NotNull(message = "値段は必須です")
    Integer price;
    String image_path;
    String description;
    String created_at;
    String updated_at;

    public ProductRecord(){

    }

    public ProductRecord(String product_id,int category_id,String name,int price,String image_path,String description,String created_at,String updated_at){
        this.product_id =product_id;
        this.category_id =category_id;
        this.name =name;
        this.price =price;
        this.image_path = image_path;
        this.description = description;
        this.created_at =created_at;
        this.updated_at = updated_at;

    }



}
