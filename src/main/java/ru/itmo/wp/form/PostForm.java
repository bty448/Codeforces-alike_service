package ru.itmo.wp.form;

import org.hibernate.annotations.CreationTimestamp;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostForm {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @Size(max = 1000)
    @Lob
    private String tagsAsString;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }
}
