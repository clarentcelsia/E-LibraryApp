package com.project.app.service;

import com.project.app.dto.PostDTO;
import com.project.app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    public Post getById(String id);
    public Page<Post> getAll(PostDTO dto, Pageable pageable);
    public Post create(Post post);
    public String deleteById(String id);
}
