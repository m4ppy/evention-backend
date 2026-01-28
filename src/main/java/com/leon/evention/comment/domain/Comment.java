package com.leon.evention.comment.domain;

import com.leon.evention.member.domain.Member;

public class Comment {
    private final Member author;
    private final String context;

    public Comment(Member author, String context) {
        this.author = author;
        this.context = context;
    }

    public static Comment createComment(Member author, String context) {
        return new Comment(author, context);
    }

    public String getContext() { return this.context; }
}
