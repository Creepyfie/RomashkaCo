package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDao productDao;

    @PostMapping
    public Long createProduct(@RequestBody @Valid Product editProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        }
        else {
            return productDao.create(editProduct);
        }
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id) {
    return productDao.getById(id);
    }

    @GetMapping
    List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(value = "id") long id,
                              @RequestBody @Valid Product editProduct,
                              BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            productDao.update(id, editProduct);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productDao.delete(id);
    }
}
