package com.project.app.service;

import com.project.app.entity.Plan;
import com.project.app.request.PlanRequest;
import com.project.app.response.PlanResponse;

import java.util.List;

public interface PlanService {
    List<Plan> getPlans();

    Plan getPlanById(String id);

    PlanResponse savePlan(PlanRequest plan);

    Plan updatePlan(Plan plan);

    void deletePlan(String id);
}
