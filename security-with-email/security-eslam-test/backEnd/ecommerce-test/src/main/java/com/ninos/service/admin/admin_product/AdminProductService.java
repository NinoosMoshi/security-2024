package com.ninos.service.admin.admin_product;

import java.io.IOException;
import java.util.List;

import com.ninos.model.dto.ProductDTO;

public interface AdminProductService {

    ProductDTO addProduct(ProductDTO productDTO) throws IOException;
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsByName(String name);

}
