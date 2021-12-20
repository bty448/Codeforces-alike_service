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

import java.util.*;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagService tagService;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, TagService tagService, CommentService commentService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post createPost(PostForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setTags(getTagsSetFromTagsString(postForm.getTagsAsString()));
        post.setText(postForm.getText());
        return post;
    }

    public void addComment(User user, Post post, Comment comment) {
        comment.setText(comment.getText().trim());
        comment.setPost(post);
        comment.setUser(user);
        commentService.save(comment);
        post.getComments().add(comment);
    }

    public List<Tag> getTagsSetFromTagsString(String tagsAsString) {
        if (tagsAsString == null) {
            return null;
        }

        tagsAsString = tagsAsString.trim().toLowerCase();
        if (tagsAsString.isEmpty()) {
            return null;
        }

        Set<Tag> tagSet = new HashSet<>();
        Set<String> tagStringSet = new HashSet<>();

        for (String tagName : tagsAsString.split("\\s+")) {
            if (tagStringSet.contains(tagName)) {
                continue;
            }
            tagStringSet.add(tagName);
            if (tagService.existsByName(tagName)) {
                tagSet.add(tagService.findByName(tagName));
                continue;
            }
            Tag tag = new Tag(tagName);
            tagSet.add(tag);
            tagService.save(tag);
        }

        List<Tag> tagList = new ArrayList<>();

        for (Tag tag : tagSet) {
            tagList.add(tag);
        }

        return tagList;
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }
}
