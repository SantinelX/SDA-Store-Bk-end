package com.sda.store.controller.dto.user;
import com.sda.store.model.MessagingChannel;

public class UserDto {
    private String email;
    private String password;
    private String imgUrl;
    private String firstName;
    private String lastName;
    private String role;
    private MessagingChannel messagingChannel;
    private AddressDto addressDto;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public MessagingChannel getMessagingChannel() {
        return messagingChannel;
    }

    public void setMessagingChannel(MessagingChannel messagingChannel) {
        this.messagingChannel = messagingChannel;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
