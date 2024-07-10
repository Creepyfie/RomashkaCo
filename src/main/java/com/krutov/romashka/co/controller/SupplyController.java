package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.dao.SupplyDao;
import com.krutov.romashka.co.model.Supply;
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
@RequestMapping("/supply")
@RequiredArgsConstructor
@Validated
public class SupplyController {


    private final SupplyDao supplyDao;

    @PostMapping
    public Long createSupply(@RequestBody @Valid Supply editSupply, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            return supplyDao.create(editSupply);
        }
    }

    @GetMapping("/{id}")
    public Supply getSupply(@PathVariable("id") long id) {
        return (Supply) supplyDao.getById(id);
    }

    @GetMapping
    List<Supply> getAllProducts() {
        return (List<Supply>) supplyDao.getAll();
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody @Valid Supply editSupply,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new StringIndexOutOfBoundsException();
        } else {
            supplyDao.update(id, editSupply);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        supplyDao.delete(id);
    }
}
