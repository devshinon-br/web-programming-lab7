package com.example.lab7.controller;

import com.example.lab7.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductSessionController {
    @GetMapping("/cart")
    public String process(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) session.getAttribute("MY_SESSION_PRODUCT");
        if (products == null) {
            products = new ArrayList<>();
        }
        model.addAttribute("sessionProducts", products);
        model.addAttribute("sessionId", session.getId());
        Product storedProduct = (Product) session.getAttribute("SESSION_PRODUCT_DATA");
        if (storedProduct != null) {
            model.addAttribute("productData", storedProduct);

            if(!products.contains(storedProduct)){
                products.add(storedProduct);
                session.setAttribute("MY_SESSION_PRODUCT", products);
            }
        }

        return "productCart";
    }


    @PostMapping("/cart/persistProduct")
    public String persistProduct(Product product, Model model, HttpSession session) {
        if(product != null){
            session.setAttribute("SESSION_PRODUCT_DATA", product);
            model.addAttribute("productData", product);
        }
        return "redirect:/cart";
    }
}
