package com.project.app.service.impl;

import com.project.app.dto.TopicDTO;
import com.project.app.entity.Topic;
import com.project.app.repository.TopicRepository;
import com.project.app.service.TopicService;
import com.project.app.specification.TopicSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic getById(String id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if ( optionalTopic.isPresent()){
            return  optionalTopic.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("topic with id %s not found", id));
        }
    }

    @Override
    public Page<Topic> getAll(TopicDTO dto, Pageable pageable) {
        Specification<Topic> specification = TopicSpecification.getSpecificationTopic(dto);
        return topicRepository.findAll(specification, pageable);
    }

    @Override
    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic update(Topic topic) {
        Topic topicGet = getById(topic.getId());
        topic.setPosts(topicGet.getPosts());
        return topicRepository.save(topic);
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        Topic topic = getById(id);
        topicRepository.delete(topic);
        return String.format("topic with id %s has been deleted", id);
    }

}
