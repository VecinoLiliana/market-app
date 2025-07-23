package com.tecdesoftware.market.web.controller;
import com.tecdesoftware.market.domain.Product;
import com.tecdesoftware.market.domain.service.ProductService;
import com.tecdesoftware.market.persistence.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
//Le dice a Spring que va a ser el controlador de una API REST
@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Manage products in the store")
public class ProductController {
    //Inyectar el servicio
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/all")
    @Operation(
            summary = "Get all products",
            description = "Return a list of all available products"
    )
    @ApiResponse(responseCode = "200", description = "Succesful retrie")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Return a product by its ID if exists"
    )
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "ID of the product to be retrieved",
                    example = "7", required = true)
            @PathVariable("id") int productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get proudct by Category", description = "Returns all products in a specific category")
    @ApiResponse(responseCode = "200", description = "Product found in the category")
    @ApiResponse(responseCode = "201", description = "No product found in the Category")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category ID", example = "2",
                    required = true)
            @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save a nex product",
            description = "Registers a new product and returns the created product",
            //te muestra como se verá visualmente
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Product",
                                    value = """
                                            {
                                            "name" : "Ian coke",
                                            "categoryId": 2,
                                            "price": "99.50",
                                            "stock": 30,
                                            "active": true
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Product conflict (duplicate code or SKU)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID", description = "Deletes a product if it exists")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product ID")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the product to be deleted", example = "7",
                    required = true)
            @PathVariable("id") int productId) {
        if (productService.deleteProduct(productId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}