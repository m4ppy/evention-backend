package com.leon.evention.project.domain;

import com.leon.evention.member.domain.Member;

class ProjectMember {
    private final Member member;
    private final ProjectRole role;

    public ProjectMember(Member member, ProjectRole projectRole) {
        this.member = member;
        this.role = projectRole;
    }

    boolean isSame(Member actor) {
        return this.member.equals(actor);
    }

    boolean isMaintainer() {
        return role == ProjectRole.MAINTAINER;
    }

    //boolean isProjectOwner() { return role == ProjectRole.PROJECT_OWNER; }
}
