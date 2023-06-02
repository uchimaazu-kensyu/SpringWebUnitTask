package com.example.springWebTask.entity;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserInfo {
    public int id;

    @NotEmpty(message = "IDは必須です")
    public String login_id;

    @NotEmpty(message = "PASSWORDは必須です")
    public String password;
    public String name;
    public int role;
    public String created_at;
    public String updated_at;


    public UserInfo(){}
}
