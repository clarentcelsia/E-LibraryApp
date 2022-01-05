package com.project.app.service.impl;

import com.project.app.entity.Features;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.FeatureRepository;
import com.project.app.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    FeatureRepository repository;

    @Override
    public List<Features> getFeatures() {
        return repository.findAll();
    }

    @Override
    public Features getFeatureById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Error: data with id "+id+" not found"));
    }

    @Override
    public Features saveFeature(Features feature) {
        return repository.save(feature);
    }

    @Override
    public Features updateFeature(Features feature) {
        Features featureById = getFeatureById(feature.getFeatureId());
        featureById.setName(feature.getName());
        featureById.setDescription(feature.getDescription());
        return saveFeature(featureById);
    }

    @Override
    public void deleteFeature(String id) {
        Features feature = getFeatureById(id);
        feature.setDeleted(true);
        repository.save(feature);
    }
}
