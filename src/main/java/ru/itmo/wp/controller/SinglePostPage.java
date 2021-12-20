package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SinglePostPage extends Page {
    private final PostService postService;

    public SinglePostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getSinglePost(@PathVariable("id") String id, Model model) {
        try {
            model.addAttribute("singlePost", postService.findById(Long.parseLong(id)));
            model.addAttribute("comment", new Comment());
        } catch (NumberFormatException e) {
            model.addAttribute("singlePost", null);
        }

        return "SinglePostPage";
    }

    @PostMapping("/post/{id}")
    public String writeComment(@PathVariable("id") String id,
                               @Valid Comment comment, BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (getUser(httpSession) == null) {
            return "redirect:/";
        }

        try {
            long postId = Long.parseLong(id);
            model.addAttribute("singlePost", postService.findById(postId));

            if (bindingResult.hasErrors()) {
                return "SinglePostPage";
            }

            Post post = postService.findById(postId);
            postService.addComment(getUser(httpSession), post, comment);
        } catch (NumberFormatException ignored) {
            //ignored
        }

        return "redirect:/post/" + id;
    }
}
