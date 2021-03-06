package com.sda.store.faker;

import com.github.javafaker.Faker;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class CustomFaker {

    public List<Product> autogeneratedProductlist(){

        Faker faker = new Faker();
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i<30; i++) {
            Product product = new Product();
//            Category category = new Category();

            product.setProductName(faker.commerce().productName());
            product.setProductType(ProductType.APPLIANCES);
            product.setThumbnail("assets/img/SDA%20Store%20Logo.png");

            product.setDescription("Description of: " + product.getProductName());
            product.setPrice(faker.number().randomDouble(2,1,10000));
            productList.add(product);
        }
        return productList;
    }
}
