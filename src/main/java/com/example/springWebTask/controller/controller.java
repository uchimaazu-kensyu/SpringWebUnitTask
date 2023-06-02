package com.example.springWebTask.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.example.springWebTask.controller.*;
import com.example.springWebTask.service.*;
import com.example.springWebTask.entity.*;
import com.example.springWebTask.dao.*;

import java.util.List;

@Controller
public class controller {
    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @GetMapping("/index")
    public String index(@ModelAttribute("inputInfo") UserInfo inputInfo ,Model model) {
        return "index2";
    }


    @PostMapping("/index")
    public String login(@Validated @ModelAttribute("inputInfo") UserInfo inputInfo,BindingResult bindingResult,Model model){
        UserInfo result =productService.getUserInfo(inputInfo.getLogin_id());
        if(bindingResult.hasErrors()) {
            return "index2";
        }

        if (result == null ||!result.getPassword().equals(inputInfo.getPassword())) {
            String message = "IDまたはPASSWORDが不正です";
            model.addAttribute("failedToLogin", message);
            return "index2";
        }else if (result.getLogin_id().equals(inputInfo.getLogin_id()) && result.getPassword().equals(inputInfo.getPassword())){
            session.setAttribute("user",result);
            return "redirect:/menu";
        }
        return "index2";
    }




    @GetMapping("/menu")
    public String menu(Model model,@ModelAttribute("message") String value) {
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }
        //deleteからのredirectできた場合には、成功のめっせーじを受け取って表示する仕組み。
        model.addAttribute("successToDelete",value);
        System.out.println("printSession:"+session.getAttribute("user"));
        return "menu2";
    }

    @GetMapping("/insert")
    public String insert(@ModelAttribute("addProduct") ProductRecord productRecord,Model model) {
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }
        List<CategoryInfo> categoryList =productService.getCategoryInfo();
        model.addAttribute("categoryList",categoryList);
        System.out.println("カテゴリーテスト："+categoryList);

        return "insert2";
    }

    @PostMapping("/insert")
    public String insertPost(@Validated @ModelAttribute("addProduct") ProductRecord input ,BindingResult bindingResult,Model model, @RequestParam("selectCategory") Integer selectedCategory){
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }
        if(bindingResult.hasErrors()) {
            List<CategoryInfo> categoryList =productService.getCategoryInfo();
            model.addAttribute("categoryList",categoryList);
            System.out.println("カテゴリーテスト："+categoryList);
            return "insert2";
        }

        String inputId = input.getProduct_id();
        int inputCategoryId = selectedCategory;
        String inputName = input.getName();
        int inputPrice = input.getPrice();
        String inputDescription = input.getDescription();
        var addProduct = new ProductRecord(inputId,inputCategoryId,inputName,inputPrice,"dummy",inputDescription,"now()","");
        productService.insert(addProduct);
        model.addAttribute("addProduct",addProduct);
        return "redirect:/menu";
    }


    @GetMapping("/updateInput")
    public String updateInput(Model model) {
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }


        return "updateInput";
    }




    @GetMapping("/product/{product_id}")
    public String date2(@PathVariable("product_id") String productId,Model model,@ModelAttribute("message") String value ) {
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }

        System.out.println("コントローラーから："+productId);
        DisplayProduct product = productService.findByProductId(productId);
        model.addAttribute("product", product);

        model.addAttribute("failedToDelete",value);
        return "detail2";
    }



    @GetMapping("/update/{id}")
    public String displayUpdate(@PathVariable("id") Integer id,Model model,@ModelAttribute("updateInfo") ProductRecord updateInfo){
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }

        //パスのidの製品検索&渡す
        ProductRecord product = productService.findProductById(id);
        model.addAttribute("product", product);
        updateInfo.setProduct_id(product.getProduct_id());
        updateInfo.setName(product.getName());
        updateInfo.setPrice(product.getPrice());
        updateInfo.setCategory_id(product.getCategory_id());
        updateInfo.setDescription(product.getDescription());
        model.addAttribute("updateInfo",updateInfo);
        model.addAttribute("selectedValue", product.getCategory_id());

        //カテゴリー取得＆渡す
        List<CategoryInfo> categoryList =productService.getCategoryInfo();
        model.addAttribute("categoryList",categoryList);
        System.out.println("カテゴリーテスト："+categoryList);


        return "updateInput2";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") String Id, @Validated @ModelAttribute("updateInfo") ProductRecord updateInfo,BindingResult bindingResult,@RequestParam("selectCategory")Integer selectedCategory,Model model){
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }

        System.out.println(Id);
        updateInfo.setCategory_id(selectedCategory);
        productService.update(updateInfo);
        return "redirect:/menu";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id,@ModelAttribute("updateInfo") ProductRecord updateInfo,Model model,RedirectAttributes redirectAttributes){
//        //ログインしていなかったらログイン画面へ
//        if(session.getAttribute("user") == null){
//            return "redirect:/index";
//        }
        var product = productService.findProductById(id);
        int result = productService.delete(id);

//        削除失敗したとき用
//        int result = productService.delete(id+500000);
        System.out.println(result);

        if(result ==0){
            String message ="削除に失敗しました。";
            redirectAttributes.addFlashAttribute("message",message);
            return "redirect:/product/"+product.getProduct_id();
        }
        //成功していたら、/menuに値を渡しながらリダイレクト。
        String message ="削除に成功しました。";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/menu";
    }


    @GetMapping("/logout")
    public String logout(){
        //ログインしていなかったらログイン画面へ
        if(session.getAttribute("user") == null){
            return "redirect:/index";
        }

        session.invalidate();
        return "redirect:/index";
    }






}
