package com.project.app.service;

import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyService {
    public Reply getById(Integer id);
    public Page<Reply> getAll(ReplyDTO dto, Pageable pageable);
    public Reply create(Reply reply);
    public String deleteById(Integer id);
}
