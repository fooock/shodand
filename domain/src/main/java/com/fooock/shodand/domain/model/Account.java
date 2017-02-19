package com.fooock.shodand.domain.model;

/**
 *
 */
public class Account {

    private final int credits;
    private final boolean member;
    private final String displayName;
    private final String created;

    public Account(int credits, boolean member, String displayName, String created) {
        this.credits = credits;
        this.member = member;
        this.displayName = displayName;
        this.created = created;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isMember() {
        return member;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCreated() {
        return created;
    }
}
