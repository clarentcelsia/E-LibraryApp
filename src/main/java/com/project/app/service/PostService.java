package com.project.app.service;

import com.project.app.entity.Post;
import com.project.app.entity.Post;

import java.util.List;

public interface PostService {
    public Post getById(String id);
    public List<Post> getAll();
    public Post create(Post post);
    public String deleteById(String id);
}
