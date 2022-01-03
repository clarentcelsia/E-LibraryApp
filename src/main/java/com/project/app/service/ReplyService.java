package com.project.app.service;

import com.project.app.entity.Reply;

import java.util.List;

public interface ReplyService {
    public Reply getById(Integer id);
    public List<Reply> getAll();
    public Reply create(Reply reply);
    public String deleteById(Integer id);
}
