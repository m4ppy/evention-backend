package com.leon.evention.project.domain;

import com.leon.evention.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private final List<ProjectMember> members = new ArrayList<>();

    public Project(Member owner) {
        members.add(new ProjectMember(owner, ProjectRole.PROJECT_OWNER));
    }

    public void addMaintainer(Member member) {
        members.add(new ProjectMember(member, ProjectRole.MAINTAINER));
    }

    public void addContributor(Member member) {
        members.add(new ProjectMember(member, ProjectRole.CONTRIBUTOR));
    }

    public boolean isMaintainer(Member actor) {
        return members.stream()
                .anyMatch(projectMember ->
                        projectMember.isSame(actor) && projectMember.isMaintainer());
    }
}
