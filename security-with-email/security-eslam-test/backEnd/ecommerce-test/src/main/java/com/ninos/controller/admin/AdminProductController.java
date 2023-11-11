package com.ninos.controller.admin;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninos.model.dto.ProductDTO;
import com.ninos.service.admin.admin_product.AdminProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/save")
    public ResponseEntity<ProductDTO> addProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
        ProductDTO productDTO1 = adminProductService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO1);
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> allProducts = adminProductService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByName(@PathVariable String name){
        List<ProductDTO> allProducts = adminProductService.getAllProductsByName(name);
        return ResponseEntity.ok(allProducts);
    }



}
