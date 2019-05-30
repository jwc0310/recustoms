package com.andy.frameworks.bean;

public class GitRepo {
    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String name;

    public GitRepo() {
    }

    @Override
    public String toString() {
        return "GitRepo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
