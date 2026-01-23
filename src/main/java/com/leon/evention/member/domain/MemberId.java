package com.leon.evention.member.domain;

import java.util.Objects;
import java.util.UUID;

public class MemberId {

    private final UUID value;

    public MemberId(UUID value) {
        this.value = value;
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(value, memberId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
