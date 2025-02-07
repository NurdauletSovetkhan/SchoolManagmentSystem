package com.example.sms;

import java.util.List;

public class Teacher extends Person {
    private List<String> subjects;
    private double salary;
    private int experience;

    public Teacher(String name, int age, String surname, int experience, int id) {
        super(name, surname, age, id);
        this.experience = experience;
    }

    // Get exp
    public int getExperience() {
        return experience;
    }
    @Override
    public String toString() {
        return super.toString() + "\nSubject: " + subjects + "\nExperience: " + experience +
                " years with $" + salary + " for a month\n";
    }
}