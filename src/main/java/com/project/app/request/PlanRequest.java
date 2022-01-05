package com.project.app.request;

import com.project.app.entity.Features;

import java.util.Set;

public class PlanRequest {
    private String plan;
    private String description;
    private Integer price;
    private Set<Features> features;

    public PlanRequest() {
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

    public Set<Features> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Features> features) {
        this.features = features;
    }
}
