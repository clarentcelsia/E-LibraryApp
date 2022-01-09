package com.project.app.dto;

public class ClientDTO {

    private Integer status;

    public ClientDTO(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
