package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.validator.PostFormValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.*;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final PostService postService;
    private final PostFormValidator postFormValidator;

    public WritePostPage(UserService userService, PostService postService, PostFormValidator postFormValidator) {
        this.userService = userService;
        this.postService = postService;
        this.postFormValidator = postFormValidator;
    }

    @InitBinder("postForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postFormValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "WritePostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("postForm") PostForm postForm,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        Post post = postService.createPost(postForm);
        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/";
    }
}
