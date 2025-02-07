package com.example.sms;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class School {
    private String schoolName;
    private String address;
    private List<Student> students; // Student squad
    private List<Teacher> teachers; // Knowledge superheroes
    private List<Course> courses; // Course lists
    private List<Classroom> classrooms; // Classrooms in the school
    private int numberOfStudents; // Total number of students
    private int numberOfTeachers; // Total number of teachers

    /* === Constructor === */
    public School(String schoolName, String address) {
        this.schoolName = schoolName;
        this.address = address;
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.classrooms = new ArrayList<>();
    }

    /* === Number of Students === */
    public int getNumberOfStudents() {
        numberOfStudents = students.size();
        return numberOfStudents;
    }

    /* === Number of Teachers === */
    public int getNumberOfTeachers() {
        numberOfTeachers = teachers.size();
        return numberOfTeachers;
    }

    /* === Register Student === */
    public void registerStudent(Student student) {
        String query = "INSERT INTO students (id, name, surname, age, contact_info) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getSurname());
            statement.setInt(4, student.getAge());
            statement.setString(5, student.getContactInfo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* === Hire Teacher === */
    public void hireTeacher(Teacher teacher) {
        String query = "INSERT INTO teachers (id, name, surname, experience, age) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teacher.getId());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getSurname());
            statement.setInt(4, teacher.getAge());
            statement.setInt(5, teacher.getExperience());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* === Add a Course === */
    public void addCourse(Course course) {
        String query = "INSERT INTO courses (name, teacher_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, course.getCourseName());
            statement.setInt(2, course.getStudentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /* === Add a Classroom === */
    public void addClassroom(Classroom classroom, Course course) {
        String query = "INSERT INTO classrooms (course_id, classroom_status) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, course.getCourseId()); // Предполагается, что у вас есть поле courseId
            statement.setString(2, classroom.getClassroomStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(Classroom classroom, Subject subject) {
        String query = "INSERT INTO subjects (classroom_id, subject_name) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, classroom.getClassroomId());
            statement.setString(2, subject.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* === Assign a Teacher to a Classroom === */
    public void assignTeacherToClassroom(Teacher teacher, Classroom classroom) {
        if (teachers.contains(teacher) && classrooms.contains(classroom)) {
            classroom.setTeacher(teacher);
            System.out.println("Teacher " + teacher.getName() + " has been assigned to Classroom ID " + classroom.getClassroomId());
        } else {
            System.out.println("Error: Teacher or Classroom not found in the school!");
        }
    }

    /* === Get All Courses === */
    public List<Course> getCourses() {
        return courses;
    }

    /* === Remove a Student === */
    public void removeStudent(Student student) {
        String query = "DELETE FROM students WHERE name = ?";
        try (Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();
        String query = "SELECT * FROM subjects";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("subject_name");
                subjectList.add(new Subject(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
    /* === Fire a Teacher === */
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }

    /* === Print School Information === */
    public void printSchoolInfo() {
        System.out.println("School: " + schoolName + "\nAddress: " + address);
    }

    /* === Print Students === */
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String contactInfo = resultSet.getString("contact_info");

                Student student = new Student(name, id, age, surname, new ArrayList<>(), contactInfo);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    /* === Print Teachers === */
    public List<Teacher> getTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        String query = "SELECT * FROM teachers";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                int experience = resultSet.getInt("experience");

                Teacher teacher = new Teacher(name, age, surname, experience, id);
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Можно заменить на логирование
        }

        return teacherList;
    }


    /* === Get Student ID === */
    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Если студент не найден
    }

    /* === Get Teacher ID === */
    public Teacher getTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null; // Если студент не найден
    }

    /* === Print Classrooms === */
    public void printClassrooms() {
        for (Classroom classroom : classrooms) {
            System.out.println(classroom);
        }
    }

    @Override
    public String toString() {
        return "School Name: " + schoolName + "\nAddress: " + address + "\nTotal Students: " + students.size();
    }

}
