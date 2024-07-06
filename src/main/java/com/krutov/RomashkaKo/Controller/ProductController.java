package com.krutov.RomashkaKo.Controller;

import com.krutov.RomashkaKo.DAO.ProductDao;
import com.krutov.RomashkaKo.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prod")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDao productDao;

    @PostMapping
    public Long createProduct(@RequestParam(value = "name") String name,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(name = "price", required = false, defaultValue = "0") Double price,
                              @RequestParam(name = "available", required = false, defaultValue = "false") Boolean available) throws StringIndexOutOfBoundsException {
        if (name.length() < 256 && description.length() < 4097) {
            return productDao.create(name, description, price, available);
        } else {
        throw new StringIndexOutOfBoundsException();
    }

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
        if (editProduct.getName().length() < 256 && editProduct.getDescription().length() < 4097) {
        productDao.update(id, editProduct);
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productDao.delete(id);
    }
}
