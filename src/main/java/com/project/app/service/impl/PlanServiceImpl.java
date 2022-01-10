package com.project.app.service.impl;

import com.project.app.entity.Features;
import com.project.app.entity.Plan;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.PlanRepository;
import com.project.app.request.PlanRequest;
import com.project.app.response.PageResponse;
import com.project.app.response.PlanResponse;
import com.project.app.service.FeatureService;
import com.project.app.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.project.app.util.Utility.RESPONSE_NOT_FOUND;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {
    @Autowired
    PlanRepository repository;

    @Autowired
    FeatureService featureService;

    @Override
    public Page<Plan> getPlans(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Plan getPlanById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
    }

    @Override
    public PlanResponse savePlan(PlanRequest plan) {
        Plan newPlan = new Plan();
        newPlan.setPlan(plan.getPlan());
        newPlan.setPrice(plan.getPrice());
        newPlan.setDescription(plan.getDescription());
        newPlan.setFeatures(plan.getFeatures());
        Plan save = repository.save(newPlan);

        List<String> strings = new ArrayList<>();
        for(Features features: save.getFeatures()){
            Features featureById = featureService.getFeatureById(features.getFeatureId());
            strings.add(featureById.getName());
        }

        PlanResponse planResponse = new PlanResponse(
                save.getPlanId(),
                save.getPlan(),
                save.getDescription(),
                save.getPrice(),
                strings
        );

        return planResponse;
    }

    @Override
    public Plan updatePlan(Plan plan) {
        Plan planById = getPlanById(plan.getPlanId());
        planById.setPlan(plan.getPlan());
        planById.setDescription(plan.getDescription());

        planById.setFeatures(plan.getFeatures());

        planById.setPrice(plan.getPrice());
        return repository.save(planById);
    }

    @Override
    public void deletePlan(String id) {
        Plan plan = getPlanById(id);
        plan.setDeleted(true);
        repository.save(plan);
    }
}
