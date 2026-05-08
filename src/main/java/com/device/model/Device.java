package com.device.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Device {
    private Long id;
    public static Integer STT = 0;
    private String url;

    @NotBlank(message = "Tên thiết bị không được để trống")
    @Size(min = 3, max = 100, message = "Phải từ 3-100 kí tự")
    private String deviceName;

    @NotBlank(message = "Thương hiệu không được để trống")
    private String brand;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0")
    @DecimalMax(value = "9999.99", message = "Giá phải nhỏ hơn 10000")
    private Float price;

    public Device() {
    }

    public Device(Long id, String deviceName, String brand, Float price, String url) {
        this.id = id;
        this.deviceName = deviceName;
        this.brand = brand;
        this.price = price;
        this.url = url;
        STT++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
