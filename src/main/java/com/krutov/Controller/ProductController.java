package com.krutov.Controller;

import com.krutov.DAO.ProductDao;
import com.krutov.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDao productDao;

    @PostMapping
    public Long createProduct(@RequestParam(value = "name") String name,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(name = "price", required = false, defaultValue = "0") Double price,
                              @RequestParam(name = "available", required = false, defaultValue = "false") Boolean available) {
        return productDao.create(name, description, price, available);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id) {
    return productDao.getById(id);
    }

    @GetMapping
    List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(value = "id") long id,@RequestBody Product editProduct) {
        productDao.update(id, editProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productDao.delete(id);
    }


}
