package com.project.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.entity.Features;
import com.project.app.entity.Plan;
import com.project.app.request.PlanRequest;
import com.project.app.response.Response;
import com.project.app.service.PlanService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.project.app.util.Utility.*;
import static com.project.app.util.Utility.RESPONSE_DELETE_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlanController.class)
class PlanControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PlanService service;


    @Test
    public void whenGivenValidUrlAndMethod_onSavePlan_thenReturn201() throws Exception {

        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        mockMvc.perform(post("/api/v7/plans")
                        .content(mapper.writeValueAsString(plan))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));
    }

    @Test
    public void whenGivenPlanIsSucceed_thenMapToModel() throws Exception {

        PlanRequest plan = new PlanRequest(
                "Basic",
                "Limited production",
                180000
        );

        mockMvc.perform(post("/api/v7/plans")
                        .content(mapper.writeValueAsString(plan))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(RESPONSE_CREATE_SUCCESS));

        ArgumentCaptor<PlanRequest> captor = ArgumentCaptor.forClass(PlanRequest.class);
        verify(service, times(1)).savePlan(captor.capture());
        assertThat(captor.getValue().getPlan()).isEqualTo("Basic");
        assertThat(captor.getValue().getDescription()).isEqualTo("Limited production");
        assertThat(captor.getValue().getPrice()).isEqualTo(180000);
    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetPlanById_thenReturn200() throws Exception {

        mockMvc.perform(get("/api/v7/plans/{id}", "C01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));

    }

    @Test
    public void whenGetPlanByIdSucceed_thenReturnPlan() throws Exception {

        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        Response<Plan> response = new Response<>(RESPONSE_GET_SUCCESS, plan);

        given(service.getPlanById(plan.getPlanId())).willReturn(response.getData());

        MvcResult result = mockMvc.perform(get("/api/v7/plans/{id}", plan.getPlanId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS))
                .andReturn();

        String expected = mapper.writeValueAsString(response);

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);

    }

    @Test
    public void whenGivenValidUrlAndMethod_onGetPlans_thenReturn200() throws Exception {

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

        assertNotNull(page);

        given(service.getPlans(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/v7/plans")
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_GET_SUCCESS));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onUpdatePlan_thenReturn200() throws Exception {
        Plan plan = new Plan(
                "C01",
                "Basic",
                "Limited production",
                180000
        );

        given(service.updatePlan(any(Plan.class))).willReturn(plan);

        mockMvc.perform(put("/api/v7/plans")
                        .content(mapper.writeValueAsString(plan))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_UPDATE_SUCCESS))
                .andExpect(jsonPath("$.['data'].['plan']").value(plan.getPlan()));
    }

    @Test
    public void whenGivenValidUrlAndMethod_onDeletePlan_thenReturn200() throws Exception {

        mockMvc.perform(delete("/api/v7/plans/{id}", "C01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(RESPONSE_DELETE_SUCCESS))
                .andExpect(jsonPath("$.data").value("C01"));
    }
}