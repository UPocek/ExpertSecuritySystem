package com.ftn.sbnz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.dtos.ProductDTO;
import com.ftn.sbnz.model.models.Product;
import com.ftn.sbnz.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public Product getProduct(@RequestParam Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/all")
    public List<ProductDTO> getAllProductsForRoom(@RequestParam Long roomId) {
        return productService.getAllProductsForRoom(roomId);
    }

    @GetMapping("/all_for_building")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping()
    public Product addProduct(@RequestParam String name, @RequestParam Long productGroupId, @RequestParam Long roomId) {
        return productService.addProduct(name, productGroupId, roomId);
    }

}
