package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class PostFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) target;
            String tagString = postForm.getTagsAsString();
            if (!isValidTagString(tagString)) {
                errors.rejectValue("tagsAsString", "tagString.invalid-tag-string", "invalid tags");
            }
        }
    }

    private boolean isValidTagString(String tagString) {
        if (tagString == null || tagString.trim().isEmpty()) {
            return true;
        }

        String[] tagNames = tagString.trim().split("\\s+");

        for (String tagName : tagNames) {
            if (!isValidTagName(tagName)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidTagName(String tagName) {
        return tagName.matches("^[A-Za-z]+$");
    }
}
