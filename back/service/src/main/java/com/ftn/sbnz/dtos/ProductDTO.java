package com.ftn.sbnz.dtos;

public class ProductDTO {
    public Long productId;
    public String name;
    public Long roomId;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String name, Long roomId) {
        this.productId = productId;
        this.name = name;
        this.roomId = roomId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

}
