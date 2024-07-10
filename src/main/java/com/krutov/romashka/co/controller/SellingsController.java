package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.SellingDao;
import com.krutov.romashka.co.model.Selling;
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

import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@Validated
public class SellingsController {

    private final SellingDao sellingDao;

    @PostMapping
    public Long createSupply(@RequestBody @Valid Selling editSale, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return sellingDao.create(editSale);
        }
    }

    @GetMapping("/{id}")
    public Selling getSupply(@PathVariable("id") long id) {
        return sellingDao.getById(id);
    }

    @GetMapping
    List<Selling> getAllProducts() {
        return (List<Selling>) sellingDao.getAll();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody @Valid Selling editSale,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            sellingDao.update(id, editSale);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        sellingDao.delete(id);
    }
}
