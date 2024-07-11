package com.krutov.romashka.co.service;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.model.Selling;
import com.krutov.romashka.co.model.Supply;
import com.krutov.romashka.co.service.validation.ValidationError;
import com.krutov.romashka.co.service.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.*;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final DocumentDao<Supply> supplyDao;
    private final DocumentDao<Selling> sellingDao;
    private final ProductService productService;

    @Transactional
    public long create(Supply supply) {

        validateSupply(supply);

        Product product = productService.findById(supply.getProductId());
        if (product == null) {
            throw new ValidationException(error(PRODUCT_NOT_FOUND, "Product with such productID is not exist"));
        }

        productService.update(product.getId(), product.withAvailable(true));
        return supplyDao.create(supply);
    }

    @Transactional
    public void update(long id, Supply newSupply) {

        validateSupply(newSupply);

        Supply oldSupply = supplyDao.findById(id);
        if (oldSupply == null) {
            throw new ValidationException(error(SUPPLY_NOT_FOUND, "Supply with such ID not found: " + String.valueOf(id)));
        }

        Product product = productService.findById(newSupply.getProductId());

        updateAvailable(newSupply.getAmount(), oldSupply, product);
        supplyDao.update(id, newSupply);
    }

    @Transactional
    public void delete(long id) {

        Supply supply = supplyDao.findById(id);
        Long productId = supply.getProductId();
        Product product = productService.findById(productId);

        updateAvailable(0L, supply, product);
        supplyDao.delete(id);
    }

    public Supply findById(long id) {
        return supplyDao.findById(id);
    }

    public List<Supply> findByProductId(long productId) {
        return supplyDao.findByProductId(productId);
    }

    private void updateAvailable(Long newSupplyAmount, Supply oldSupply, Product product) {

        Long supplyId = oldSupply.getId();
        Long productId = product.getId();


        Long otherSuppliesAmount = findByProductId(productId)
            .stream()
            .filter(supply -> !supply.getId().equals(supplyId))
            .map(Supply::getAmount)
            .reduce(Long::sum)
            .orElse(0L);
        Long sellingsAmount = sellingDao.findByProductId(productId)
            .stream()
            .map(Selling::getAmount)
            .reduce(Long::sum)
            .orElse(0L);

        long totalAmount = newSupplyAmount + otherSuppliesAmount - sellingsAmount;

        if (totalAmount <= 0) {
            productService.update(productId, product.withAvailable(false));
        }
    }

    private void validateSupply(Supply supply) {
        List<ValidationError> errors = new ArrayList<>();

        String name = supply.getName();
        if (name != null && name.length() > 255) {
            errors.add(error(SUPPLY_NAME_SIZE, "Supply Name must be less then 256 symbols"));
        }

        Long amount = supply.getAmount();
        if (amount == null || amount <= 0) {
            errors.add(error(SUPPLY_AMOUNT, "Supply Amount must be more than 0"));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
