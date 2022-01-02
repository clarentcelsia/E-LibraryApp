package com.project.app.service;

import com.project.app.entity.Topic;

import java.util.List;

public interface TopicService {
    public Topic getById(String id);
    public List<Topic> getAll();

    public Topic create(Topic topic);
    public String deleteById(String id);
}
