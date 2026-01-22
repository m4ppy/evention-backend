package com.leon.evention.ticket.domain;

import com.leon.evention.project.domain.Project;
import com.leon.evention.project.domain.ProjectMember;
import com.leon.evention.project.domain.ProjectRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketTest {
    @Test
    void non_maintainer_cannot_change_ticket_status() {
        Project project = new Project();
        ProjectMember contributor = new ProjectMember(ProjectRole.CONTRIBUTOR);

        Ticket ticket = Ticket.open(project);

        assertThrows(
                Exception.class,
                () -> ticket.changeStatus(TicketStatus.IN_PROGRESS, contributor)
        );
    }

}
