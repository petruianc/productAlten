package com.petru.controller;


import com.petru.model.Product;
import com.petru.model.Products;
import com.petru.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Management System")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
            summary = "View a list of available products",
            responses = {
                    @ApiResponse(
                            description = "Successfully retrieved list",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Products.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Products> getAllProducts() {
        try{
           return productService.getAllProducts();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by its ID",
            responses = {
                    @ApiResponse(
                            description = "Successfully retrieved Product",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content
                    )
            })
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try{
            return productService.getProductById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    @Operation(
            summary = "Add a new product",
            responses = {
                    @ApiResponse(
                            description = "Product created",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class),
                                    examples = @ExampleObject(value = "{\"code\": \"123test\",\"description\": \"Test\",\"name\": \"Test\", \"category\": \"Electronics\", \"price\": 23}")
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try{
            return productService.createProduct(product);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing product",
            responses = {
                    @ApiResponse(
                            description = "Product updated!",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class),
                                    examples = @ExampleObject(value = "{\"code\": \"f234gh5\",\"description\": \"Product description\",\"name\": \"Smartphone\", \"category\": \"Electronics\", \"price\": 23}")
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class),
                                    examples = @ExampleObject(value = "{\"code\": \"f234gh5\",\"description\": \"Product description\",\"name\": \"Smartphone\", \"category\": \"Electronics\", \"id\": -100}")
                            )
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500",
                            content = @Content
                    )
                }
            )
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try{
            return productService.updateProduct(id, updatedProduct);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by its ID", description = "Deletes a product with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product has been deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class)))
            ,
            @ApiResponse(
                    description = "Product doesn't exist!",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content)
    })
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true) @PathVariable Long id) {
        try{
            return productService.deleteProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
