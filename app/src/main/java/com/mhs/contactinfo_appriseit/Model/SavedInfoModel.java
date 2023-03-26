package com.mhs.contactinfo_appriseit.Model;

public class SavedInfoModel {
    private String name, phone, email;
    private long create_time;

    public SavedInfoModel() {
    }

    public SavedInfoModel(String name, String phone, String email, long create_time) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
