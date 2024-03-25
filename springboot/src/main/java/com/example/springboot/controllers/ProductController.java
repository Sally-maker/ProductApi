package com.example.springboot.controllers;

import java.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dto.ProductRecordDto;
import com.example.springboot.model.ProductModel;
import com.example.springboot.repositories.ProductRepositorie;

import jakarta.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    ProductRepositorie productRepositorie;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepositorie.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepositorie.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductsId(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product0 = productRepositorie.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado");

        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProductsId(@PathVariable(value = "id") UUID id,
                                                   @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product0 = productRepositorie.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepositorie.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductsId(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product0 = productRepositorie.findById(id);
        if (product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado");

        }
        productRepositorie.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("deletado");
    }
}
