package com.petru.service;


import com.petru.dao.ProductDao;
import com.petru.model.Product;
import com.petru.model.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public ResponseEntity<Products> getAllProducts() {
        log.info("Inside ProductService.getAllProducts()");
        try{
            return new ResponseEntity<>(productDao.getAllProducts(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Product> getProductById(Long id) {
        log.info("Inside ProductService.getProductById() {}", id);
        try{
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Product> createProduct(Product product) {
        log.info("Inside ProductService.createProduct() {}", product);
        try{
            if(product != null){
                return new ResponseEntity<>(productDao.createProduct(product), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<Product> updateProduct(Long id, Product updatedProduct) {
        log.info("Inside ProductService.updateProduct() {}", updatedProduct);
        try{
            if(updatedProduct != null){
                return new ResponseEntity<>(productDao.updateProduct(id, updatedProduct), HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteProduct(Long id) throws IOException {
        log.info("Inside ProductService.deleteProduct() {}", id);
        try{
            if(productDao.deleteProduct(id)){
                return new ResponseEntity<>("Product has been deleted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Product doesn't exist!", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went  wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
