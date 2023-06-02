package com.example.springWebTask.dao;

import com.example.springWebTask.entity.*;
import com.example.springWebTask.entity.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.List.*;
import java.util.List;


@Repository
public class ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private JdbcTemplate jdbcForMaxId;



    public List<DisplayProduct> findAll(){

        return jdbcTemplate.query("SELECT \n" +
                        "product_id,\n" +
                        "p.name,\n" +
                        "price,\n" +
                        "c.name AS category_name,\n" +
                        "description \n" +
                        "FROM products p\n" +
                        "JOIN categories c\n" +
                        "ON p.category_id = c.id\n" +
                        "ORDER BY p.id ",
                new DataClassRowMapper<>(DisplayProduct.class));

    }



    public DisplayProduct findByProductId(String productId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("product_id", productId);
        //この処理の返り値はリストになるので、nullでなければ0番目を取ってくる処理が別途必要。
        List<DisplayProduct> list = jdbcTemplate.query("SELECT p.id, product_id, p.name,price, c.name AS category_name, description FROM products p JOIN categories c ON p.category_id = c.id WHERE  product_id = '"+productId+"'"
                , new DataClassRowMapper<>(DisplayProduct.class));
        System.out.println(list);
        return list.isEmpty()? null:list.get(0);
    }

    public ProductRecord findProductById(Integer id){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        //この処理の返り値はリストになるので、nullでなければ0番目を取ってくる処理が別途必要。
        List<ProductRecord> list = jdbcTemplate.query("SELECT * FROM products WHERE id = :id",param
                , new DataClassRowMapper<>(ProductRecord.class));
        System.out.println(list);
        return list.isEmpty()? null:list.get(0);
    }


    public UserInfo getUserInfo(String inputId){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("inputId" , inputId);
        List<UserInfo> list =jdbcTemplate.query("SELECT * FROM users WHERE login_id = " + "'" +inputId+ "'",param,new DataClassRowMapper<>(UserInfo.class));
        return list.isEmpty()? null:list.get(0);
    }

    public int insert(ProductRecord addProduct){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("product_id", addProduct.getProduct_id());
        param.addValue("category_id",addProduct.getCategory_id());
        param.addValue("name", addProduct.getName());
        param.addValue("price", addProduct.getPrice());
        param.addValue("description",addProduct.getDescription());
        int result =jdbcTemplate.update("insert into products ( product_id, category_id, name, price, description,created_at)" +
                "values(:product_id, :category_id , :name, :price, :description,now() );",param);
        return result;
    }


    public int update(ProductRecord updateInfo){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id",updateInfo.getId());
        param.addValue("product_id", updateInfo.getProduct_id());
        param.addValue("category_id",updateInfo.getCategory_id());
        param.addValue("name", updateInfo.getName());
        param.addValue("price", updateInfo.getPrice());
        param.addValue("description",updateInfo.getDescription());
        int result =jdbcTemplate.update("UPDATE products SET product_id ='"+updateInfo.getProduct_id()+"',category_id = :category_id ,name = :name ,price =:price , description =:description, updated_at= now() WHERE id = :id;",param);
        return result;
    }

    public int delete(int id){
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id",id);
        int result =jdbcTemplate.update("DELETE FROM products WHERE id =:id",param);
        return result;
    }

    public List<DisplayProduct> findByName(String searchTarget){
        if(searchTarget.equals("")){

        }
        var result =jdbcTemplate.query("SELECT p.id, product_id, p.name,price, c.name AS category_name, description FROM products p JOIN categories c ON p.category_id = c.id WHERE p.name LIKE '%"+ searchTarget + "%' ORDER BY p.id",
                new DataClassRowMapper<>(DisplayProduct.class));
        System.out.println("result"+result);
        return result;
    }


    public List<CategoryInfo> getCategoryInfo(){
        return jdbcTemplate.query("SELECT id, name FROM categories;",new DataClassRowMapper<>(CategoryInfo.class));
    }



}
