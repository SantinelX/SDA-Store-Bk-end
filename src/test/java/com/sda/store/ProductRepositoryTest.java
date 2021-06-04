package com.sda.store;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import com.sda.store.repository.CategoryRepository;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.ProductSpecifcation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup() {
        productRepository.findAll().forEach(product -> productRepository.delete(product));
        categoryRepository.findAll().forEach(category -> categoryRepository.delete(category));
    }

    @Test
    public void testSave() {
        Category drinks = new Category();
        drinks.setName("Drinks");
        drinks = categoryRepository.save(drinks);

        Product cocaCola = new Product();
        cocaCola.setProductName("Coca Cola");
        cocaCola.setPrice(2.59);
        cocaCola.setCategory(drinks);
        cocaCola.setDescription("the best of the best of the best.");
        cocaCola.setProductType(ProductType.BEVERAGES);

        cocaCola = productRepository.save(cocaCola);

        Assertions.assertNotNull(cocaCola.getId());
        Assertions.assertNotNull(cocaCola.getCategory());
        Assertions.assertNotNull(cocaCola.getProductName());
    }

    @Test
    public void searchProductByName() {
        Product tv = new Product();
        tv.setProductName("LG");

        Product tv2 = new Product();
        tv2.setProductName("Samsung");

        productRepository.save(tv);
        productRepository.save(tv2);

        Assertions.assertEquals(2, productRepository.count());
        Assertions.assertEquals(1, productRepository.findAll(ProductSpecifcation.withNameLike("LG")).size());
        Assertions.assertEquals(1, productRepository.findAll(ProductSpecifcation.withNameLike("Samsung")).size());
        Assertions.assertEquals(0, productRepository.findAll(ProductSpecifcation.withNameLike("Philips")).size());
    }

    @Test
    public void searchProductByProductType() {
        Product apliance = new Product();
        apliance.setProductType(ProductType.APPLIANCES);

        Product food = new Product();
        food.setProductType(ProductType.FOOD);

        productRepository.save(apliance);
        productRepository.save(food);

        Assertions.assertEquals(1,productRepository.findAll(ProductSpecifcation.ofType(ProductType.FOOD)).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecifcation.ofType(ProductType.APPLIANCES)).size());
        Assertions.assertEquals(0,productRepository.findAll(ProductSpecifcation.ofType(ProductType.COSMETICS)).size());

    }

    @Test
    public void SearchProductsByTypeAndName() {

        Product samsungPhone = new Product();
        samsungPhone.setProductName("Samsung");
        samsungPhone.setProductType(ProductType.HI_TECH);

        Product samsungTV = new Product();
        samsungTV.setProductName("Samsung");
        samsungTV.setProductType(ProductType.APPLIANCES);

        productRepository.save(samsungPhone);
        productRepository.save(samsungTV);

        Assertions.assertEquals(2, productRepository.findAll(ProductSpecifcation.withNameLike("Samsung")).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecifcation.withNameLike("Samsung").and(ProductSpecifcation.ofType(ProductType.HI_TECH))).size());

    }

    @Test
    public void searchProductbyPriceRange(){
        Product phone1 = new Product();
        phone1.setPrice(100.0);

        Product phone2 = new Product();
        phone2.setPrice(200.0);

        Product phone3 = new Product();
        phone3.setPrice(300.0);

        Product phone4= new Product();
        phone4.setPrice(400.0);

        productRepository.save(phone1);
        productRepository.save(phone2);
        productRepository.save(phone3);
        productRepository.save(phone4);

        Assertions.assertEquals(2,productRepository.findAll(ProductSpecifcation.withPriceInRangeOf(100.0,200.0)).size());
        Assertions.assertEquals(1,productRepository.findAll(ProductSpecifcation.withPriceInRangeOf(350.0,400.0)).size());
        Assertions.assertEquals(0,productRepository.findAll(ProductSpecifcation.withPriceInRangeOf(650.0,800.0)).size());
    }

    @Test
    public void searchProductByCategoryId(){
        Category appianceCategory = new Category();
        appianceCategory.setName("Appliance_Category");

        Category phoneCategory = new Category();
        phoneCategory.setName("Phone_Category");

        categoryRepository.save(appianceCategory);
        categoryRepository.save(phoneCategory);

        Product apliance1 = new Product();
        apliance1.setProductName("appliance1");
        apliance1.setCategory(appianceCategory);

        Product phone1 = new Product();
        phone1.setProductName("phone1");
        phone1.setCategory(phoneCategory);

        Product phone2 = new Product();
        phone2.setProductName("phone2");
        phone2.setCategory(phoneCategory);

        productRepository.save(apliance1);
        productRepository.save(phone1);
        productRepository.save(phone2);

        Assertions.assertEquals(1, productRepository.findAll(ProductSpecifcation.withCategoryId(appianceCategory.getId())).size());
        Assertions.assertEquals(2, productRepository.findAll(ProductSpecifcation.withCategoryId(phoneCategory.getId())).size());

    }


}
