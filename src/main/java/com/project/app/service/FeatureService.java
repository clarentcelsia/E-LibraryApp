package com.project.app.service;

import com.project.app.entity.Features;

import java.util.List;

public interface FeatureService {
    List<Features> getFeatures();

    Features getFeatureById(String id);

    Features saveFeature(Features feature);

    Features updateFeature(Features feature);

    void deleteFeature(String id);
}
