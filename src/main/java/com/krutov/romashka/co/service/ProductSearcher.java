package com.krutov.romashka.co.service;

import com.krutov.romashka.co.controller.dto.ProductSearchRequest;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.service.validation.ValidationError;
import com.krutov.romashka.co.service.validation.ValidationException;
import com.krutov.romashka.co.util.ListData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.krutov.romashka.co.service.validation.ProductValidator.validatePrice;
import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.PRICE_COMPARISON_SIGN;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.PRODUCT_NAME_SIZE;

@Service
@RequiredArgsConstructor
public class ProductSearcher {

    private final ProductDao productDao;

    public List<Product> search(ProductSearchRequest request, ListData listData) {

        validateRequest(request);
        return productDao.search(request, listData);
    }

    private void validateRequest(ProductSearchRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        validateRequestName(request.getName(), errors);
        validatePrice(request.getPrice(), errors);
        validatePriceSign(request.getPriceSign(), errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private static void validateRequestName(String name, List<ValidationError> errors) {
        if (name != null && name.length() > 255) {
            errors.add(error(PRODUCT_NAME_SIZE, "Name length must be less than 255 characters)"));
        }
    }

    private static void validatePriceSign(String priceSign, List<ValidationError> errors) {
        if (priceSign != null && !Set.of("lt", "gt", "eq").contains(priceSign)) {
            errors.add(error(PRICE_COMPARISON_SIGN, "Wrong price comparison sign. Might be lt gt or eq"));
        }
    }
}
