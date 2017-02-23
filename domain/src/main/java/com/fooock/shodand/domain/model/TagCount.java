package com.fooock.shodand.domain.model;

/**
 * Tag name with the number of occurrences
 */
public class TagCount {

    private final int count;
    private final String name;

    public TagCount(int count, String name) {
        this.count = count;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagCount tagCount = (TagCount) o;

        return name.equals(tagCount.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
