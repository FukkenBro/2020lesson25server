package com.pchen.model;


public class User {

    private static int id = 0;
    private String name;
    private int age;
    private int userId;

    public User() {
    }

    public User(String firstName, int age) {
        userId = id++;
        this.name = firstName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "\nname='" + name + '\'' +
                "\nage=" + age +
                '}';
    }

}
