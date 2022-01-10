package com.project.app.response;

import com.project.app.entity.Features;

import java.util.List;
import java.util.Set;

public class PlanResponse {
    private String planId;
    private String plan;
    private String description;
    private Integer price;
    private List<String> features;

    public PlanResponse() {
    }

    public PlanResponse(String planId, String plan, String description, Integer price, List<String> features) {
        this.planId = planId;
        this.plan = plan;
        this.description = description;
        this.price = price;
        this.features = features;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
}
