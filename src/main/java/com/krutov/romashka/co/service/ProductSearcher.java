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
import static com.krutov.romashka.co.service.validation.ProductValidator.validateProductName;
import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.PRICE_COMPARISON_SIGN;

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

        validateProductName(request.getName(), errors);
        validatePrice(request.getPrice(), errors);
        validatePriceSign(request, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private static void validatePriceSign(ProductSearchRequest request, List<ValidationError> errors) {
        if (!Set.of("lt", "gt", "eq").contains(request.getPriceSign())) {
            errors.add(error(PRICE_COMPARISON_SIGN));
        }
    }
}
