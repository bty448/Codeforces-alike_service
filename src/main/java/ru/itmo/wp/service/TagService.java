package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean existsByName(String tagName) {
        if (tagName == null) {
            return false;
        }
        return tagRepository.existsByName(tagName);
    }

    public Tag findByName(String tagName) {
        if (tagName == null) {
            return null;
        }
        return tagRepository.findByName(tagName);
    }

    public void save(Tag tag) {
        if (tag != null) {
            tagRepository.save(tag);
        }
    }
}
