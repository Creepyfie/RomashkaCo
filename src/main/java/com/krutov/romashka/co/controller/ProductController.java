package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.DB.Direction;
import com.krutov.romashka.co.dao.DB.ListData;
import com.krutov.romashka.co.dao.DB.SortData;
import com.krutov.romashka.co.dao.DB.SqlFilters;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prod")
@RequiredArgsConstructor
@Validated
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
    List<Product> getAllProducts(
            @RequestParam (name = "filterName", required = false) @Size(max = 255) String filterName,
            @RequestParam (name = "filterPrice", required = false) @Min(0) Double filterPrice,
            @RequestParam(name = "filterPriceSIgn", required = false) String filterSign,
            @RequestParam(name = "filterAvailable", required = false) Double filterAv,
            @RequestParam(name = "sortByNameDirection", required = false) Direction nameDirection,
            @RequestParam(name = "sortByPriceDirection", required = false) Direction priceDirection,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "offset", required = false) Integer offset) {

        List<SortData> sortDataList = new ArrayList<>();
        if (!(nameDirection == null)){
            sortDataList.add(new SortData("name",nameDirection));
        }
        if (!(priceDirection == null)) {
            sortDataList.add(new SortData("price",priceDirection));
        }
        ListData listData = new ListData(limit,offset,sortDataList);

        String sqlFilters = SqlFilters.builder()
                .like("name", filterName)
                .eq("available", filterAv)
                .priceFilter("price",filterPrice, filterSign).build().makeWhereClause();


            return productDao.getAllProducts(listData, sqlFilters);
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
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
