package com.sda.store.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private String imageUrl;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name= "role_id"))
    private Set<Role> role;

    @Enumerated(value=EnumType.STRING)
    private MessagingChannel messagingChannel;

    @OneToOne (cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart = new ShoppingCart();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address adress) {
        this.address = adress;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }


    public MessagingChannel getMessagingChannel() {
        return messagingChannel;
    }

    public void setMessagingChannel(MessagingChannel messagingChannel) {
        this.messagingChannel = messagingChannel;
    }

    public ShoppingCart getShopingCart() {
        return shoppingCart;
    }

    public void setShopingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
