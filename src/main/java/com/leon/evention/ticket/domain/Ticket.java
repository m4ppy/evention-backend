package com.leon.evention.ticket.domain;

import com.leon.evention.comment.domain.Comment;
import com.leon.evention.ticket.domain.exception.CommentNotFoundException;
import com.leon.evention.project.domain.Project;
import com.leon.evention.member.domain.Member;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ticket {

    private final Project project;
    private TicketStatus status;
    private List<Comment> comments = new ArrayList<>();

    public Ticket(Project project) {
        this.project = project;
        this.status = TicketStatus.OPEN;
    }

    public static Ticket open(Project project) {
        return new Ticket(project);
    }

    public void changeStatus(TicketStatus newStatus, Member actor) throws UnauthorizedTicketOperationException {
        if (!project.isMaintainer(actor)) {
            throw new UnauthorizedTicketOperationException();
        }
        this.status = newStatus;
    }

    public TicketStatus getStatus() {
        return this.status;
    }

    public UUID createComment(Member actor, String context) {
        if (!project.isProjectMember(actor)) {
            throw new UnauthorizedTicketOperationException();
        }
        Comment newComment = Comment.createComment(actor, context);
        this.comments.add(newComment);
        return newComment.getId();
    }

    public Integer commentCount() {
        return this.comments.size();
    }

    public void updateComment(Member actor, UUID commentId, String context) {
        Comment comment = getCommentById(commentId);
        if (!comment.isAuthor(actor)) {
            throw new UnauthorizedTicketOperationException();
        }
        comment.updateComment(context);
    }

    private Comment getCommentById(UUID commentId) {
        return comments.stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .orElseThrow(CommentNotFoundException::new);
    }

    public String getCommentContent(UUID commentId) {
        Comment comment = getCommentById(commentId);
        return comment.getContext();
    }
}
