package com.example.sms;

import java.util.List;

public class Classroom {
    private int classroomId;
    private String classroomStatus;
    private List<Student> students;
    private Teacher teacher;

    public Classroom(int classroomId, List<Student> students, Teacher teacher, String classroomStatus) {
        this.classroomId = classroomId;
        this.students = students;
        this.teacher = teacher;
        this.classroomStatus = classroomStatus;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getClassroomStatus() {
        return classroomStatus;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        StringBuilder studentsInfo = new StringBuilder("Students: ");
        for (Student student : students) {
            studentsInfo.append(student.getName()).append(", ");
        }
        if (studentsInfo.length() > 0) {
            studentsInfo.setLength(studentsInfo.length() - 2);
        }

        return String.format(
                "Classroom ID: %d\nType: %s\nTeacher: %s\n%s",
                classroomId,
                classroomStatus,
                teacher != null ? teacher.getName() : "No teacher assigned",
                studentsInfo.toString()
        );
    }
}