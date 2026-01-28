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

        Ticket ticket = Ticket.open(project);

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

        Ticket ticket = Ticket.open(project);

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

        Ticket ticket = Ticket.open(project);

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

        Ticket ticket = new Ticket(project);

        String context = "this should be end tomorrow.";

        // WHEN
        ticket.createComment(contributor, context);

        // THEN
        assertEquals(1, ticket.commentCount());
    }


    // non-project-owner-cannot-add-members-to-project
    @Test
    void non_project_owner_cannot_add_members_to_project() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member maintainer = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        // WHEN & THEN
        assertThrows(UnauthorizedProjectOperationException.class,
                () -> project.addContributor(maintainer, contributor));

        assertThrows(UnauthorizedProjectOperationException.class,
                () -> project.addMaintainer(contributor, maintainer));
    }

    @Test
    void project_owner_can_add_members_to_project() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member maintainer = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        // WHEN
        project.addMaintainer(owner, maintainer);
        project.addContributor(owner, contributor);

        // THEN
        assertTrue(project.isMaintainer(maintainer));
        assertTrue(project.isContributor(contributor));
    }
}
