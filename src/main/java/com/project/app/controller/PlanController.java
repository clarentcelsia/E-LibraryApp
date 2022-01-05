package com.project.app.controller;

import com.project.app.entity.Plan;
import com.project.app.request.PlanRequest;
import com.project.app.response.PlanResponse;
import com.project.app.response.Response;
import com.project.app.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v7/plans")
public class PlanController {

    @Autowired
    PlanService service;

    @PostMapping
    public ResponseEntity<Response<PlanResponse>> savePlan(
            @RequestBody PlanRequest plan
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Succeed: plan saved successfully!", service.savePlan(plan)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Plan>> getPlanById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: get plan id successfully!", service.getPlanById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<List<Plan>>> getPlans() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: get update successfully!", service.getPlans()));
    }

    @PutMapping
    public ResponseEntity<Response<Plan>> updatePlan(
            @RequestBody Plan plan
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: update plan successfully!", service.updatePlan(plan)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> getPlans(
            @PathVariable(name = "id") String id
    ) {
        service.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>("Succeed: delete client successfully!", id));
    }
}
