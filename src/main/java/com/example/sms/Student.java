package com.example.sms;


import java.util.List;

public class Student extends Person {
    private List<Integer> grades; // Score list
    private String contactInfo; // Contact info!

    public Student(String name, int id, int age, String surname, List<Integer> grades, String contactInfo) {
        super(name, surname, id, age);
        this.grades = grades;
        this.contactInfo = contactInfo;
    }



    // Get ID

    // Average grade!
    public double getAverageGrade() {
        return grades.stream().mapToInt(i -> i).average().orElse(0.0);
    }

    // Passed or not
    public String isStringPassed() {
        if(getAverageGrade() >= 60){
            return "passed";
        } // Passing grade
        else{
            return "failed";
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Grade: " + getAverageGrade() + "\nContact Info: " + contactInfo + "\n";
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
