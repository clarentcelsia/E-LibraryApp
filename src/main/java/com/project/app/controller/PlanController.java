package com.project.app.controller;

import com.project.app.entity.Features;
import com.project.app.entity.Plan;
import com.project.app.request.PlanRequest;
import com.project.app.response.PageResponse;
import com.project.app.response.PlanResponse;
import com.project.app.response.Response;
import com.project.app.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.app.util.Utility.*;

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
                .body(new Response<>(RESPONSE_CREATE_SUCCESS, service.savePlan(plan)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Plan>> getPlanById(
            @PathVariable(name = "id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, service.getPlanById(id)));

    }

    @GetMapping
    public ResponseEntity<Response<PageResponse<Plan>>> getPlans(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        Page<Plan> plan = service.getPlans(PageRequest.of(page, size));
        PageResponse<Plan> response = new PageResponse<>(
                plan.getContent(),
                plan.getTotalElements(),
                plan.getTotalPages(),
                page,
                size
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_GET_SUCCESS, response));
    }

    @PutMapping
    public ResponseEntity<Response<Plan>> updatePlan(
            @RequestBody Plan plan
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_UPDATE_SUCCESS, service.updatePlan(plan)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deletePlans(
            @PathVariable(name = "id") String id
    ) {
        service.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(RESPONSE_DELETE_SUCCESS, id));
    }
}
