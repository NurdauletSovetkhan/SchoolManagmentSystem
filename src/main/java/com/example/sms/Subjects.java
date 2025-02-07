package com.example.sms;

import java.sql.*;
import java.util.*;

public class Subjects {
    private List<Subject> subjectsList; // Список предметов
    private String classroomStatus; // Текущий статус класса

    /* === Constructor === */
    public Subjects() {
        subjectsList = new ArrayList<>();
        loadSubjectsFromDatabase(); // Загружаем предметы из базы данных
    }

    public void loadInitialSubjects() {
        List<Subject> initialSubjects = Arrays.asList(
                new Subject(0, "Mathematics"),
                new Subject(0, "Physics"),
                new Subject(0, "Chemistry"),
                new Subject(0, "Biology"),
                new Subject(0, "History"),
                new Subject(0, "Geography"),
                new Subject(0, "Computer Science"),
                new Subject(0, "Art"),
                new Subject(0, "Physical Education")
        );

        for (Subject subject : initialSubjects) {
            addSubjectToDatabase(subject);
        }
    }

    /* === Метод для загрузки предметов из базы данных === */
    private void loadSubjectsFromDatabase() {
        String query = "SELECT * FROM subjects"; // Предполагается, что у вас есть таблица subjects

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("subject_name");
                subjectsList.add(new Subject(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* === Метод для получения списка предметов на основе статуса класса === */
    public List<Subject> getSubjects() {
        List<Subject> filteredSubjects = new ArrayList<>();

        // Фильтруем предметы на основе статуса класса
        for (Subject subject : subjectsList) {
            if (classroomStatus != null && subject.getName().toLowerCase().contains(classroomStatus.toLowerCase())) {
                filteredSubjects.add(subject);
            }
        }

        return filteredSubjects;
    }

    /* === Метод для установки статуса класса === */
    public void setClassroomStatus(String status) {
        this.classroomStatus = status.toLowerCase(); // Приводим статус к нижнему регистру для согласованности
    }

    /* === Метод для добавления предмета в базу данных === */
    public void addSubjectToDatabase(Subject subject) {
        String query = "INSERT INTO subjects (subject_name) VALUES (?)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, subject.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Subjects subjects = new Subjects();

        // Пример использования
        subjects.setClassroomStatus("tech");
        System.out.println("Subjects for tech class: " + subjects.getSubjects());

        subjects.setClassroomStatus("non-tech");
        System.out.println("Subjects for non-tech class: " + subjects.getSubjects());

        subjects.setClassroomStatus("unknown");
        System.out.println("Subjects for unknown class: " + subjects.getSubjects());
    }
}