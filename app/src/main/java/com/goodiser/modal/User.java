package com.goodiser.modal;

public class User {

    private String UID;
    private String name;
    private String email;
    private String phone;

    public User() {
        this.UID = "";
        this.name = null;
        this.email = null;
        this.phone = null;
    }

    public User(String UID, String name, String email, String phone) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getUID() {
        return this.UID;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
