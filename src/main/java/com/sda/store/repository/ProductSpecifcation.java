package com.sda.store.repository;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Parameter;
import javax.persistence.criteria.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ProductSpecifcation implements Specification<Product> {

    public static Specification<Product> withNameLike(String productName) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("productTitle"), "%" + productName + "%"));
    }

    public static Specification<Product> ofType (ProductType productType){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productType"),productType));
    }

    public static Specification<Product> withPriceInRangeOf(Double lowerInterval, Double higherInterval) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("price"),lowerInterval,higherInterval));
    }

    public static Specification<Product> withCategoryId(Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Product> getSpecicationByParameters(String parameterName, String parameterValue){
        switch (parameterName) {
            case "name": return withNameLike(parameterValue);
            case "productType": return ofType(ProductType.valueOf(parameterValue));
            case "categoryId":return withCategoryId(Long.valueOf(parameterValue));
            case "price" :
                String price = parameterValue; // lowPrice:50,highPrice:100; // price=highPrice:100,lowPrice:50
                Double lowPrice = 0.0;
                Double highPrice = Double.MAX_VALUE;
                if (price.contains(",")) {
                    List<String> prices = Arrays.asList(price.split(",")); // lowPrice:50 | highPrice:100
                    for (String iteration: prices) {
                        if (iteration.contains("lowPrice")) {
                            String lowPriceInStringFormat = iteration.replaceAll("lowPrice:", ""); // lowPrice:50  ===> 50;
                            lowPrice = Double.valueOf(lowPriceInStringFormat);
                        } else if (iteration.contains("highPrice")) {{
                            String highPriceInStringFormat = iteration.replaceAll("highPrice:", ""); // highPrice:100 ==> 100;
                            highPrice = Double.valueOf(highPriceInStringFormat);
                        }}
                    }
                }
                return withPriceInRangeOf(lowPrice, highPrice);
            default: return new ProductSpecifcation();
        }
    }

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
