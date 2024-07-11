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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.krutov.romashka.co.service.validation.ProductValidator.validatePrice;
import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.*;

@Service
@RequiredArgsConstructor
public class SellingService {

    private final DocumentDao<Supply> supplyDao;
    private final DocumentDao<Selling> sellingDao;
    private final ProductService productService;

    @Transactional
    public long create(Selling selling) {

        validateSelling(selling);

        Long productId = selling.getProductId();
        Product product = productService.findById(productId);
        if (product == null) {
            throw new ValidationException(error(PRODUCT_NOT_FOUND));
        }

        validateTotalAvailable(selling.getAmount(), productId);

        updateAvailable(selling.getAmount(), product);
        return sellingDao.create(selling);
    }

    @Transactional
    public void update(long id, Selling newSelling) {
        validateSelling(newSelling);

        Selling oldSelling = sellingDao.findById(id);
        if (oldSelling == null) {
            throw new ValidationException(error(SELLING_NOT_FOUND));
        }

        long currentSoldProductAmount = newSelling.getAmount() - oldSelling.getAmount();
        validateTotalAvailable(currentSoldProductAmount, newSelling.getProductId());

        Product product = productService.findById(newSelling.getProductId());
        updateAvailable(id, newSelling.getAmount(), oldSelling.getAmount(), product);
        sellingDao.update(id, newSelling);
    }

    @Transactional
    public void delete(long id) {

        Selling selling = sellingDao.findById(id);
        Long productId = selling.getProductId();
        Product product = productService.findById(productId);

        updateAvailable(id, 0L, selling.getAmount(), product);
        sellingDao.delete(id);
    }

    public Selling findById(long id) {
        return sellingDao.findById(id);
    }

    public List<Selling> findByProductId(long productId) {
        return sellingDao.findByProductId(productId);
    }

    private void validateTotalAvailable(Long currentSoldProductsAmount, Long productId) {

        Long totalSuppliedProductCount = supplyDao.findByProductId(productId)
            .stream()
            .map(Supply::getAmount)
            .reduce(Long::sum)
            .orElse(0L);

        Long totalSoldProductCount = sellingDao.findByProductId(productId)
            .stream()
            .map(Selling::getAmount)
            .reduce(Long::sum)
            .orElse(0L);

        long availableProductCount = totalSuppliedProductCount - totalSoldProductCount - currentSoldProductsAmount;
        if (availableProductCount < 0) {
            throw new ValidationException(error(SELLING_AMOUNT_TOO_MUCH));
        }
    }

    private void updateAvailable(Long currentSellingAmount, Product product) {

        Long productId = product.getId();

        if (product.getAvailable()) {
            Long otherSellingsAmount = findByProductId(productId)
                .stream()
                .map(Selling::getAmount)
                .reduce(Long::sum)
                .orElse(0L);
            Long suppliesAmount = supplyDao.findByProductId(productId)
                .stream()
                .map(Supply::getAmount)
                .reduce(Long::sum)
                .orElse(0L);

            long totalAmount = suppliesAmount - otherSellingsAmount - currentSellingAmount;

            if (totalAmount <= 0) {
                productService.update(productId, product.withAvailable(false));
            }
        }
    }

    private void updateAvailable(Long sellingId, Long newSellingAmount, Long oldSellingAmount, Product product) {

        Long productId = product.getId();

        // Количество товара на складе не может быть меньше 0,
        // значит если он был недоступен и количество проданных товаров уменьшилось,
        // то товар становится доступным.
        if (!product.getAvailable() && oldSellingAmount > newSellingAmount) {
            productService.update(productId, product.withAvailable(true));
        } else if (product.getAvailable() && oldSellingAmount < newSellingAmount) {
            Long otherSellingsAmount = findByProductId(productId)
                .stream()
                .filter(selling -> !selling.getId().equals(sellingId))
                .map(Selling::getAmount)
                .reduce(Long::sum)
                .orElse(0L);
            Long suppliesAmount = supplyDao.findByProductId(productId)
                .stream()
                .map(Supply::getAmount)
                .reduce(Long::sum)
                .orElse(0L);

            long totalAmount = suppliesAmount - otherSellingsAmount - newSellingAmount;

            if (totalAmount <= 0) {
                productService.update(productId, product.withAvailable(false));
            }
        }
    }

    private void validateSelling(Selling selling) {
        List<ValidationError> errors = new ArrayList<>();

        String name = selling.getName();
        if (name != null && name.length() > 255) {
            errors.add(error(SELLING_NAME_SIZE));
        }

        BigDecimal totalPrice = selling.getTotalPrice();
        validatePrice(totalPrice, errors);

        Long amount = selling.getAmount();
        if (amount <= 0) {
            errors.add(error(SELLING_AMOUNT));
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
