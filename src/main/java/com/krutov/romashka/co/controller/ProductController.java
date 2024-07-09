package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.Direction;
import com.krutov.romashka.co.util.ListData;
import com.krutov.romashka.co.util.SortData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prod")
@RequiredArgsConstructor
@Validated
public class ProductController {

    /**
     * Слой сервисов не заводил, но понимаю, что он должен быть.
     */
    private final ProductDao productDao;

    @PostMapping
    public Long createProduct(@RequestBody @Valid Product editProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return productDao.create(editProduct);
        }
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id) {
        return productDao.getById(id);
    }

    @GetMapping
    List<Product> getAllProducts(
        @Valid ProductSearchRequest request,
        @RequestParam(name = "sortByNameDirection", required = false) Direction nameDirection,
        @RequestParam(name = "sortByPriceDirection", required = false) Direction priceDirection,
        @RequestParam(name = "limit", required = false, defaultValue = "1000000") Integer limit,
        @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset
    ) {

        List<SortData> sortDataList = new ArrayList<>();
        if (nameDirection != null) {
            sortDataList.add(new SortData("name", nameDirection));
        }
        if (!(priceDirection == null)) {
            sortDataList.add(new SortData("price", priceDirection));
        }
        ListData listData = new ListData(limit, offset, sortDataList);

        return productDao.searchProduct(request, listData);
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody @Valid Product editProduct,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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
