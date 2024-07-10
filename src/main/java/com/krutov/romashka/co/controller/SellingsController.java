package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.model.Selling;
import com.krutov.romashka.co.model.Supply;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@Validated
public class SellingsController {

    private final DocumentDao documentDao;

    @PostMapping
    public Long createSupply(@RequestBody @Valid Selling editSale, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return documentDao.create(editSale);
        }
    }

    @GetMapping("/{id}")
    public Selling getSupply(@PathVariable("id") long id) {
        return (Selling) documentDao.getById(id);
    }

    @GetMapping
    List<Selling> getAllProducts() {
        return (List<Selling>) documentDao.getAll();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody @Valid Supply editSale,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            documentDao.update(id, editSale);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        documentDao.delete(id);
    }
}
