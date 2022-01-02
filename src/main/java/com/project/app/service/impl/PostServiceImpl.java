package com.project.app.service.impl;

import com.project.app.entity.Post;
import com.project.app.entity.Topic;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.PostRepository;
import com.project.app.repository.TopicRepository;
import com.project.app.service.PostService;
import com.project.app.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new NotFoundException(String.format("Post with ID %s Not Found", id));
        }
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post create(Post post) {
        Post savedPost = postRepository.save(post);

        Topic topic = topicService.getById(savedPost.getTopic().getId());
        List<Post> posts = topic.getPosts();
        posts.add(savedPost);
        topic.setPosts(posts);
        topicService.create(topic);

        return savedPost;
    }

    @Override
    public String deleteById(String id) {
        postRepository.delete(getById(id));
        return String.format("Post with ID %s has been deleted", id);
    }

}
