package com.petru.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petru.model.Product;
import com.petru.model.Products;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Repository
public class ProductDao {
    private static final File DATA_FILE = new File("./products.json");
    private static final ObjectMapper mapper = new ObjectMapper();

    public Products getAllProducts() throws IOException {
        return mapper.readValue(Files.readAllBytes(DATA_FILE.toPath()), Products.class);
    }

    public Product getProductById(Long id) throws IOException{
        Products products = getAllProducts();
        List<Product> listProducts = products.getProducts();
        return listProducts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public Product createProduct(Product product) throws IOException {
        Products products = getAllProducts();
        List<Product> listProducts = products.getProducts();
        long maxId = listProducts.stream().mapToLong(Product::getId).max().orElse(0);
        product.setId(maxId + 1);
        listProducts.add(product);
        products.setProducts(listProducts);
        saveProducts(products);
        return product;
    }

    public void saveProducts(Products products) throws IOException {
        mapper.writeValue(DATA_FILE, products);
    }

    public Product updateProduct(Long id, Product updatedProduct) throws IOException {
        Products products = getAllProducts();
        List<Product> listProducts = products.getProducts();
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getId().equals(id)) {
                updatedProduct.setId(id);
                listProducts.set(i, updatedProduct);
                products.setProducts(listProducts);
                saveProducts(products);
                return updatedProduct;
            }
        }
        return null;
    }

    public boolean deleteProduct(Long id) throws IOException {
        Products products = getAllProducts();
        List<Product> listProducts = products.getProducts();
        if(listProducts.removeIf(product -> product.getId().equals(id))){
            products.setProducts(listProducts);
            saveProducts(products);
            return true;
        }
        return false;
    }
}
