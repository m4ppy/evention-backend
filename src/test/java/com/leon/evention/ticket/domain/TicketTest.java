package com.leon.evention.ticket.domain;

import com.leon.evention.comment.domain.Comment;
import com.leon.evention.member.domain.Member;
import com.leon.evention.project.domain.Project;
import com.leon.evention.project.domain.exception.UnauthorizedProjectOperationException;
import com.leon.evention.ticket.domain.exception.UnauthorizedTicketOperationException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    // "non-maintainer cannot change ticket status"
    @Test
    void non_maintainer_cannot_change_ticket_status() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(owner, contributor);

        Ticket ticket = Ticket.open(project, contributor);

        // WHEN & THEN
        assertThrows(
                UnauthorizedTicketOperationException.class,
                () -> ticket.changeStatus(TicketStatus.IN_PROGRESS, contributor)
        );
    }

    @Test
    void maintainer_can_change_ticket_status() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member maintainer = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addMaintainer(owner, maintainer);

        Ticket ticket = Ticket.open(project, maintainer);

        // WHEN
        ticket.changeStatus(TicketStatus.IN_PROGRESS, maintainer);

        // THEN
        assertEquals(TicketStatus.IN_PROGRESS, ticket.getStatus());

    }


    // non-project-member-cannot-write-comment
    @Test
    void non_project_member_cannot_write_comment() {
        // GIVEN
        Member non_project_member = new Member(UUID.randomUUID());
        Member owner = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        Ticket ticket = Ticket.open(project, owner);

        String context = "context for test.";

        // WHEN & THEN
        assertThrows(
                UnauthorizedTicketOperationException.class,
                () -> ticket.createComment(non_project_member, context)
        );

    }

    @Test
    void project_member_can_write_comment() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(owner, contributor);

        Ticket ticket = Ticket.open(project, contributor);

        String context = "this should be end tomorrow.";

        // WHEN
        UUID commentId = ticket.createComment(contributor, context);

        // THEN
        assertEquals(1, ticket.commentCount());
    }


    // ticket-cannot-allow-non-author-to-edit-comment
    @Test
    void ticket_cannot_allow_non_author_to_edit_comment() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member contributor1 = new Member(UUID.randomUUID());
        Member contributor2 = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(owner, contributor1);
        project.addContributor(owner, contributor2);

        Ticket ticket = Ticket.open(project, contributor1);

        String context = "this should be end tomorrow.";

        UUID commentId = ticket.createComment(contributor1, context);

        String new_context = "this should be end today!";

        // WHEN & THEN
        assertThrows(UnauthorizedTicketOperationException.class,
                () -> ticket.updateComment(contributor2, commentId, new_context));
    }

    @Test
    void ticket_allow_author_to_edit_comment() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(owner, contributor);

        Ticket ticket = Ticket.open(project, contributor);

        String context = "this should be end tomorrow.";

        UUID commentId = ticket.createComment(contributor, context);

        String new_context = "this should be end today!";

        // WHEN
        ticket.updateComment(contributor, commentId, new_context);

        // THEN
        assertEquals(new_context, ticket.getCommentContent(commentId));
    }


    // ticket-cannot-be-created-by-non-project-member
    @Test
    void ticket_cannot_be_created_by_non_project_member() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member non_project_member = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        // WHEN & THEN
        assertThrows(UnauthorizedTicketOperationException.class,
                () -> Ticket.open(project, non_project_member));
    }

    @Test
    void ticket_can_be_created_by_project_member() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addContributor(owner, contributor);

        // WHEN
        Ticket ticket = Ticket.open(project, contributor);

        // THEN
        assertNotNull(ticket);

    }
}
