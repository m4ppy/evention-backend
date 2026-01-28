package com.leon.evention.project.domain;

import com.leon.evention.member.domain.Member;
import com.leon.evention.project.domain.exception.UnauthorizedProjectOperationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectTest {

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


    // non-project-owner-cannot-remove-members-from-project
    @Test
    void non_project_owner_cannot_remove_members_from_project() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member maintainer = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addMaintainer(owner, maintainer);
        project.addContributor(owner, contributor);

        // WHEN & THEN
        assertThrows(UnauthorizedProjectOperationException.class,
                () -> project.removeMember(maintainer, contributor));
    }

    @Test
    void project_owner_can_remove_members_from_project() {
        // GIVEN
        Member owner = new Member(UUID.randomUUID());
        Member maintainer = new Member(UUID.randomUUID());
        Member contributor = new Member(UUID.randomUUID());

        Project project = new Project(owner);

        project.addMaintainer(owner, maintainer);
        project.addContributor(owner, contributor);

        // WHEN
        project.removeMember(owner, maintainer);

        // THEN
        assertFalse(project.isMaintainer(maintainer));
        assertTrue(project.isContributor(contributor));
    }

}
