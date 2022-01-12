package com.project.app.service.impl;

import com.project.app.entity.Features;
import com.project.app.exception.ResourceNotFoundException;
import com.project.app.repository.FeatureRepository;
import com.project.app.response.PageResponse;
import com.project.app.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.app.utils.Utility.RESPONSE_NOT_FOUND;

@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    FeatureRepository repository;

    @Override
    public Page<Features> getFeatures(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Features getFeatureById(String id) {
        return repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESPONSE_NOT_FOUND, id)));
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
