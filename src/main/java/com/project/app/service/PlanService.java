package com.project.app.service;

import com.project.app.entity.Plan;
import com.project.app.request.PlanRequest;
import com.project.app.response.PageResponse;
import com.project.app.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlanService {
    Page<Plan> getPlans(Pageable pageable);

    Plan getPlanById(String id);

    PlanResponse savePlan(PlanRequest plan);

    Plan updatePlan(Plan plan);

    void deletePlan(String id);
}
