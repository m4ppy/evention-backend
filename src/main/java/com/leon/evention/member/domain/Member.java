package com.leon.evention.member.domain;

import java.util.Objects;
import java.util.UUID;

public class Member {

    private final MemberId id;

    public Member(UUID id) {
        this.id = new MemberId(id);
    }

    public MemberId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
