package com.sda.store.service.implementation;

import com.sda.store.exceptions.ResourceNotFoundInDatabase;
import com.sda.store.model.Product;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.ProductSpecifcation;
import com.sda.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ResourceNotFoundInDatabase(String.format("Product with id %d is not found in database !!!", id));
        }
    }

    @Override
    public Product updateProduct(Product product) {
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);
            existingProduct.setProductName(product.getProductName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setThumbnail(product.getThumbnail());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setProductType(product.getProductType());
            existingProduct.setAuthor(product.getAuthor());
            return productRepository.save(existingProduct);
    }

    @Override
    public Page<Product> searchProducts(Map<String, String> params) {
        Specification<Product>  specification = new ProductSpecifcation();

        if(params.get("page") == null || params.get("pageSize") == null) {
            throw new RuntimeException("Page and page size must be valued !!!");
        }
        Integer page = Integer.valueOf(params.get("page"));
        Integer pageSize = Integer.valueOf(params.get("pageSize"));

        for(String parameterName: params.keySet()) {
            specification = specification.and(ProductSpecifcation.getSpecicationByParameters(parameterName, params.get(parameterName)));
        }
        return  productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    //Faker create products method
    @Override
    public List<Product> createAll(List<Product> products) {
        return productRepository.saveAll(products);
    }
}
