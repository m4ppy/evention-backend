package com.leon.evention.project.domain;

import java.util.List;

public class Project {

    private List<ProjectMember> member;

    public boolean isMaintainer(ProjectMember member) {
        return member.role == ProjectRole.MAINTAINER;
    }
}
