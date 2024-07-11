package com.krutov.romashka.co.service;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.service.validation.ValidationError;
import com.krutov.romashka.co.service.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.krutov.romashka.co.service.validation.ProductValidator.*;
import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public long create(Product product) {

        if (product.getPrice() == null)
            product.withPrice(BigDecimal.ZERO);

        validateProduct(product);
        return productDao.create(product);
    }

    public void update(long id, Product product) {

        validateProduct(product);

        Product existingProduct = productDao.findById(id);
        if (existingProduct == null) {
            throw new ValidationException(error(PRODUCT_NOT_FOUND, "Product with such productID is not exist"));
        }
        productDao.update(id, product);
    }

    public void delete(long id) {
        productDao.delete(id);
    }

    public Product findById(long id) {
        return productDao.findById(id);
    }

    private void validateProduct(Product product) {
        List<ValidationError> errors = new ArrayList<>();

        validateProductName(product.getName(), errors);
        validatePrice(product.getPrice(), errors);
        validateDescription(product.getDescription(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
