package com.goodiser.modal;

public class User {

    private String UID;
    private String name;
    private String email;
    private Integer phone;

    public User() {
        this.UID = "EMPTY";
        this.name = null;
        this.email = null;
        this.phone = null;
    }

    public User(String UID, String name, String email, Integer phone) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getPhone() {
        return this.phone;
    }



}
