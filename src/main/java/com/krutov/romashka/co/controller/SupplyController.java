package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.model.Supply;
import com.krutov.romashka.co.util.Direction;
import com.krutov.romashka.co.util.ListData;
import com.krutov.romashka.co.util.SortData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/supply")
@RequiredArgsConstructor
@Validated
public class SupplyController {


    private final DocumentDao documentDao;

    @PostMapping
    public Long createSupply(@RequestBody @Valid Supply editSupply, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return documentDao.create(editSupply);
        }
    }

    @GetMapping("/{id}")
    public Supply getSupply(@PathVariable("id") long id) {
        return (Supply) documentDao.getById(id);
    }

    @GetMapping
    List<Supply> getAllProducts() {
        return (List<Supply>) documentDao.getAll();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody @Valid Supply editSupply,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            documentDao.update(id, editSupply);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        documentDao.delete(id);
    }
}
