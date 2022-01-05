package com.project.app.service.impl;

import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import com.project.app.entity.Topic;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.PostRepository;
import com.project.app.repository.ReplyRepository;
import com.project.app.service.PostService;
import com.project.app.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostService postService;
    
    @Override
    public Reply getById(Integer id) {
        Optional<Reply> optionalReply = replyRepository.findById(id);
        if ( optionalReply.isPresent()){
            return  optionalReply.get();
        } else {
            throw new NotFoundException(String.format("Reply with ID %s Not Found", id));
        }
    }

    @Override
    public List<Reply> getAll() {
        return replyRepository.findAll();
    }

    @Override
    @Transactional
    public Reply create(Reply reply) {
        Reply savedReply = replyRepository.save(reply);

        Post post = postService.getById(savedReply.getPost().getId());
        List<Reply> replies = post.getReply();
        replies.add(savedReply);
        post.setReply(replies);
        postService.update(post);

        return savedReply;
    }

    @Override
    @Transactional
    public String deleteById(Integer id) {
        Reply reply = getById(id);
        Post post = postService.getById(reply.getPost().getId());
        post.getReply().remove(reply);

        replyRepository.delete(getById(id));
        return String.format("Reply with ID %s has been deleted", id);
    }

}
