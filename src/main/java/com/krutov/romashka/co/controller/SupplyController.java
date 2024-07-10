package com.krutov.romashka.co.controller;

import com.krutov.romashka.co.model.Supply;
import com.krutov.romashka.co.service.SupplyService;
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
@RequestMapping("/supply")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @PostMapping
    public Long supply(@RequestBody Supply supply) {
        return supplyService.create(supply);
    }

    @GetMapping("/{id}")
    public Supply getSupply(@PathVariable("id") long id) {
        return (Supply) supplyService.findByProductId(id);
    }

    @PatchMapping
    public void update(@RequestParam(name = "id") long id,
                       @RequestBody Supply editSupply) {
        supplyService.update(id, editSupply);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        supplyService.delete(id);
    }
}
