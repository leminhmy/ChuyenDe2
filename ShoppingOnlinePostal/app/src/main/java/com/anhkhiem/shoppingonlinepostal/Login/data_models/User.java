package com.anhkhiem.shoppingonlinepostal.Login.data_models;

public class User {
    String id;
    String email;
    String password;
    String name;
    String area;
    String phone;
    String roles;
    String image;

    public User() {
    }

    public User(String id, String email, String password, String name, String area, String phone, String roles,String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.area = area;
        this.phone = phone;
        this.roles = roles;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
