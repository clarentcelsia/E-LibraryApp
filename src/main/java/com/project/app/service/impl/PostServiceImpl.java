package com.project.app.service.impl;

import com.project.app.dto.PostDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Topic;
import com.project.app.repository.PostRepository;
import com.project.app.service.PostService;
import com.project.app.service.TopicService;
import com.project.app.specification.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicService topicService;

    @Override
    public Post getById(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if ( optionalPost.isPresent()){
            return  optionalPost.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("post with id %s not found", id));
        }
    }

    @Override
    public Page<Post> getAll(PostDTO dto, Pageable pageable) {
        Specification<Post> specification = PostSpecification.getSpecification(dto);
        return postRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public Post create(Post post) {
        Post savedPost = postRepository.save(post);

        Topic topic = topicService.getById(savedPost.getTopic().getId());
        topic.getPosts().add(savedPost);

        return savedPost;
    }

    @Override
    @Transactional
    public String deleteById(String id) {
        Post post = getById(id);
        Topic topic = topicService.getById(post.getTopic().getId());
        topic.getPosts().remove(post);

        postRepository.delete(getById(id));
        return String.format("post with id %s has been deleted", id);
    }

}
