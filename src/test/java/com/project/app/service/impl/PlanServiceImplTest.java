package com.project.app.service.impl;

import com.project.app.entity.Features;
import com.project.app.entity.Plan;
import com.project.app.repository.FeatureRepository;
import com.project.app.repository.PlanRepository;
import com.project.app.request.PlanRequest;
import com.project.app.response.PlanResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @InjectMocks
    private PlanServiceImpl service;

    @Mock
    PlanRepository repository;

    @Mock
    FeatureServiceImpl featureService;

    @Test
    public void whenGetPlans_thenReturnPlanInPage(){
        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        Pageable pageable = PageRequest.of(0,5);

        List<Plan> plans = new ArrayList<>();
        plans.add(plan);

        Page<Plan> page = new PageImpl<Plan>(plans, pageable, 1L);

        assertThat(page).isNotNull();

        given(repository.findAll(any(Pageable.class))).willReturn(page);

        Page<Plan> planPage = service.getPlans(pageable);

        assertEquals(page.getContent(), planPage.getContent());

    }

    @Test
    public void whenGetPlanByIdSucceed_theReturnPlan(){
        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        given(repository.findById(any(String.class))).willReturn(Optional.of(plan));

        Plan actual = service.getPlanById(plan.getPlanId());

        assertNotNull(actual);

        assertEquals(plan.getPlanId(), actual.getPlanId());

        assertEquals(plan, actual);
    }

    @Test
    public void whenSavePlanSucceed_thenReturnSuccess(){
        Features feature = new Features("F01", "Name", "Title");
        List<Features> features = new ArrayList<>();
        features.add(feature);

        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );
        plan.setFeatures(features);
        given(repository.save(any(Plan.class))).willReturn(plan);

        //service
        PlanRequest request = new PlanRequest(
                "Basic",
                "Limited production",
                180000
        );
        request.setFeatures(features);
        given(featureService.getFeatureById(any(String.class))).willReturn(feature);
        PlanResponse savePlan = service.savePlan(request);

        assertThat(savePlan).isNotNull();
    }

    @Test
    public void whenUpdatePlanSucceed_thenReturnPlan(){
        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        given(repository.save(plan)).willReturn(plan);

        given(repository.findById(any(String.class))).willReturn(Optional.of(plan));

        Plan updatePlan = service.updatePlan(plan);

        assertThat(updatePlan).isNotNull();

        verify(repository, times(1)).save(plan);
    }

    @Test
    public void whenDeleteFeatureSucceed_thenFeatureDeleteChange(){
        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        plan.setDeleted(false);

        given(repository.save(any(Plan.class))).willReturn(plan);

        given(repository.findById(any(String.class))).willReturn(Optional.of(plan));

        Plan plan1 = service.getPlanById(plan.getPlanId());

        service.deletePlan(plan.getPlanId());

        assertEquals(true, plan1.getDeleted());

    }
}