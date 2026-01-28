package com.leon.evention.project.domain;

import com.leon.evention.member.domain.Member;
import com.leon.evention.project.domain.exception.UnauthorizedProjectOperationException;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private final List<ProjectMember> members = new ArrayList<>();

    public Project(Member owner) {
        members.add(new ProjectMember(owner, ProjectRole.PROJECT_OWNER));
    }

    public void addMaintainer(Member actor, Member member) {
        if (!isProjectOwner(actor)) {
            throw new UnauthorizedProjectOperationException();
        }
        members.add(new ProjectMember(member, ProjectRole.MAINTAINER));
    }

    public void addContributor(Member actor, Member member) {
        if (!isProjectOwner(actor)) {
            throw new UnauthorizedProjectOperationException();
        }
        members.add(new ProjectMember(member, ProjectRole.CONTRIBUTOR));
    }

    public void removeMember(Member actor, Member member) {
        if (!isProjectOwner(actor)) {
            throw new UnauthorizedProjectOperationException();
        }

        members.removeIf(pm -> pm.getMember().equals(member));
    }

    public boolean isProjectMember(Member actor) {
        return members.stream()
                .anyMatch(projectMember -> projectMember.isSame(actor));
    }

    public boolean isProjectOwner(Member actor) {
        return members.stream()
                .anyMatch(projectMember ->
                        projectMember.isSame(actor) && projectMember.isProjectOwner());
    }

    public boolean isMaintainer(Member actor) {
        return members.stream()
                .anyMatch(projectMember ->
                        projectMember.isSame(actor) && projectMember.isMaintainer());
    }

    public boolean isContributor(Member actor) {
        return members.stream()
                .anyMatch(projectMember ->
                        projectMember.isSame(actor) && projectMember.isContributor());
    }

}
