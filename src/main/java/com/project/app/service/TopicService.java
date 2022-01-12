package com.project.app.service;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    public Topic getById(String id);
    public Page<Topic> getAll(TopicDTO dto, Pageable pageable);

    public Topic create(Topic topic);
    public Topic update(Topic topic);
    public String deleteById(String id);
}
