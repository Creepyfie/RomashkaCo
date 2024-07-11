package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.controller.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.service.ProductSearcher;
import com.krutov.romashka.co.service.ProductService;
import com.krutov.romashka.co.util.Direction;
import com.krutov.romashka.co.util.ListData;
import com.krutov.romashka.co.util.SortData;
import lombok.RequiredArgsConstructor;
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
public class ProductController {

    private final ProductService productService;
    private final ProductSearcher productSearcher;

    @PostMapping
    public Long createProduct(@RequestBody Product editProduct) {
        return productService.create(editProduct);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id) {
        return productService.findById(id);
    }

    @GetMapping
    List<Product> getAllProducts(
        ProductSearchRequest request,
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

        return productSearcher.search(request, listData);
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody Product editProduct) {
        productService.update(id, editProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        productService.delete(id);
    }
}
