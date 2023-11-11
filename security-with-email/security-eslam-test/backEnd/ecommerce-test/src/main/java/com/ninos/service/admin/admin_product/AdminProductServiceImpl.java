package com.ninos.service.admin.admin_product;


import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ninos.model.dto.ProductDTO;
import com.ninos.model.entity.Category;
import com.ninos.model.entity.Product;
import com.ninos.repository.CategoryRepository;
import com.ninos.repository.ProductRepository;

@AllArgsConstructor
@Service
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;



    @Override
    public ProductDTO addProduct(ProductDTO productDTO) throws IOException {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImg(productDTO.getImg().getBytes());

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        product.setCategory(category);

        return productRepository.save(product).getDto();
    }



    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProductsByName(String name) {
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

}
