package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.model.Selling;
import com.krutov.romashka.co.service.SellingService;
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

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SellingsController {

    private final SellingService sellingService;

    @PostMapping
    public Long create(@RequestBody Selling selling) {
        return sellingService.create(selling);
    }

    @GetMapping("/{id}")
    public Selling getSelling(@PathVariable("id") long id) {
        return sellingService.findById(id);
    }

    @PatchMapping
    public void updateProduct(@RequestParam(name = "id") long id,
                              @RequestBody Selling editSale) {

        sellingService.update(id, editSale);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        sellingService.delete(id);
    }
}
