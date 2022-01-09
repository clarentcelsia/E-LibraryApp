package com.project.app.service;

import com.project.app.entity.Features;
import com.project.app.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeatureService {
    Page<Features> getFeatures(Pageable pageable);

    Features getFeatureById(String id);

    Features saveFeature(Features feature);

    Features updateFeature(Features feature);

    void deleteFeature(String id);
}
