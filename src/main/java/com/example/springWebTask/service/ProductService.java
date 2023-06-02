package com.example.springWebTask.service;

import com.example.springWebTask.dao.ProductDao;
import com.example.springWebTask.entity.CategoryInfo;
import com.example.springWebTask.entity.DisplayProduct;
import com.example.springWebTask.entity.ProductRecord;
import com.example.springWebTask.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public UserInfo getUserInfo(String inputId){
        return productDao.getUserInfo(inputId);
    }



    public List<DisplayProduct> findAll(){
        return productDao.findAll();
    }



    public DisplayProduct findByProductId(String productId){
//        System.out.println(productId);
//        System.out.println("サービスの受け取るもの:"+productDao.findByProductId(productId));
        return productDao.findByProductId(productId);

    }

    public List<CategoryInfo> getCategoryInfo(){
        return productDao.getCategoryInfo();
    }

    public ProductRecord findProductById(Integer id){
        System.out.println("id:"+id);
        System.out.println("サービスの受け取るもの:"+productDao.findProductById(id));
        return productDao.findProductById(id);

    }



    public int insert(ProductRecord addProduct){
        return productDao.insert(addProduct);
    }

    public int update(ProductRecord updateInfo){
        return productDao.update(updateInfo);
    }

    public int delete(int id){
        return productDao.delete(id);
    }

    public List<DisplayProduct> findByName(String searchTarget){

        return productDao.findByName(searchTarget);
    }



}
