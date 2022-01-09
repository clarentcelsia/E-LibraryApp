package com.project.app.request;

import com.project.app.entity.Features;

import java.util.List;
import java.util.Set;

public class PlanRequest {
    private String plan;
    private String description;
    private Integer price;
    private List<Features> features;

    public PlanRequest() {
    }

    public PlanRequest(String plan, String description, Integer price) {
        this.plan = plan;
        this.description = description;
        this.price = price;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }
}
