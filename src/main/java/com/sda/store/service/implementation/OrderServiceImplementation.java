package com.sda.store.service.implementation;

import com.sda.store.model.Order;
import com.sda.store.model.OrderLine;
import com.sda.store.model.Product;
import com.sda.store.model.User;
import com.sda.store.repository.OrderRepository;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.UserRepository;
import com.sda.store.service.OrderService;
import com.sda.store.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ShoppingCartService shoppingCartService;

    public OrderServiceImplementation(OrderRepository orderRepository, UserRepository userRepository,
                                      ProductRepository productRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order createOrder(List<OrderLine> orderLineList) {
        Order order = new Order();
        // obiectul userDetails contine email-ul utilizatorului logat
        UserDetails springUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(springUser.getUsername());
        order.setOrderLineList(orderLineList);
        order.setUser(user);

        //salvam order
        Order dbOrder = orderRepository.save(order);

        //modificam stock-ul produselor
        for (OrderLine orderLine : orderLineList) {
            Product product = orderLine.getProduct();
            product.setStock(product.getStock() - orderLine.getQuantity());
            productRepository.save(product);
        }

        //facem clear la shopping cart (utilizatorul trebuie sa aiba shopping cartul gol dupa plasarea comenzii)
        shoppingCartService.clearShoppingCart(user.getShopingCart().getId());
        return dbOrder;
    }

    @Override
    public List<Order> findAllOrders() {
        UserDetails springUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(springUser.getUsername());

        return orderRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Order> findAllOrdersByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }
}
