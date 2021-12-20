package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.TagRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment comment) {
        if (comment != null) {
            commentRepository.save(comment);
        }
    }
}
