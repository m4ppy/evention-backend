package com.leon.evention.comment.domain;

import com.leon.evention.member.domain.Member;

import java.util.UUID;

public class Comment {
    private final UUID id = UUID.randomUUID();
    private final Member author;
    private String context;

    public Comment(Member author, String context) {
        this.author = author;
        this.context = context;
    }

    public static Comment createComment(Member author, String context) {
        return new Comment(author, context);
    }

    public void updateComment(String context) {
        this.context = context;
    }

    public UUID getId() {
        return this.id;
    }

    public String getContext() { return this.context; }

    public boolean isAuthor(Member actor) { return actor == this.author; }
}
