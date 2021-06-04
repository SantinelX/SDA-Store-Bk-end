package com.sda.store.controller.dto.shoppingCart;

public class ShoppingCartOrderLineDto {

    private Long productId;
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public void setId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
