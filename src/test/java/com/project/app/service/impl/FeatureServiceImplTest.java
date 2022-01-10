package com.project.app.service.impl;

import com.project.app.dto.ClientDTO;
import com.project.app.entity.Clients;
import com.project.app.entity.Features;
import com.project.app.repository.ClientRepository;
import com.project.app.repository.FeatureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeatureServiceImplTest {


    @InjectMocks
    private FeatureServiceImpl service;

    @Mock
    FeatureRepository repository;

    @Test
    public void whenGetFeatures_thenReturnFeatureInPage(){

        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        Pageable pageable = PageRequest.of(0,5);

        List<Features> features = new ArrayList<>();
        features.add(feature);

        Page<Features> page = new PageImpl<Features>(features, pageable, 1L);

        assertThat(page).isNotNull();

        given(repository.findAll(any(Pageable.class))).willReturn(page);

        Page<Features> featuresPage = service.getFeatures(pageable);

        assertEquals(page.getContent(), featuresPage.getContent());

    }

    @Test
    public void whenGetFeatureByIdSucceed_theReturnFeature(){
        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        given(repository.findById(any(String.class))).willReturn(Optional.of(feature));

        Features features = service.getFeatureById(feature.getFeatureId());

        assertNotNull(features);

        assertEquals(feature.getFeatureId(), features.getFeatureId());

        assertEquals(feature, features);
    }

    @Test
    public void whenSaveFeatureSucceed_thenReturnFeature(){
        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        given(repository.save(any(Features.class))).willReturn(feature);

        Features saveFeature = service.saveFeature(feature);

        assertThat(saveFeature).isNotNull();

    }

    @Test
    public void whenUpdateFeatureSucceed_thenReturnFeature(){
        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );

        given(repository.save(feature)).willReturn(feature);

        given(repository.findById(any(String.class))).willReturn(Optional.of(feature));

        Features updateFeature = service.updateFeature(feature);

        assertThat(updateFeature).isNotNull();

        //check repo do save(client) once after service update client being called
        verify(repository, times(1)).save(feature);
    }

    @Test
    public void whenDeleteFeatureSucceed_thenFeatureDeleteChange(){
        Features feature = new Features(
                "C01",
                "Loan system",
                "Handle book lending"
        );
        feature.setDeleted(false);

        given(repository.save(any(Features.class))).willReturn(feature);

        given(repository.findById(any(String.class))).willReturn(Optional.of(feature));

        Features features = service.getFeatureById(feature.getFeatureId());

        service.deleteFeature(features.getFeatureId());

        assertEquals(true, features.getDeleted());

    }
}