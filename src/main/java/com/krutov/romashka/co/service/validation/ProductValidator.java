package com.krutov.romashka.co.service.validation;

import java.math.BigDecimal;
import java.util.List;

import static com.krutov.romashka.co.service.validation.ValidationError.error;
import static com.krutov.romashka.co.service.validation.ValidationErrorCode.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ProductValidator {

    public static void validateProductName(String name, List<ValidationError> errors) {
        if (isBlank(name)) {
            errors.add(error(Product_NAME_IS_BLANK));
        } else if (name.length() > 255) {
            errors.add(error(PRODUCT_NAME_SIZE, "Wrong Name Size (more than 255 characters)"));
        }
    }

    public static void validatePrice(BigDecimal price, List<ValidationError> errors) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            errors.add(error(PRICE_NEGATIVE, "Price is negative (< 0)"));
        }
    }

    public static void validateDescription(String description, List<ValidationError> errors) {
        if (description != null && description.length() > 4096) {
            errors.add(error(DESCRIPTION_SIZE,"Description Size is more than 4096 symbols"));
        }
    }
}
