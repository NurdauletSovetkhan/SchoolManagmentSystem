package com.example.sms;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class main extends Application {
    private School school;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        school = new School("Semey School N8", "Dastenova 25");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f4f4f4;");

        Label titleLabel = new Label("🏫 School Management System");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-background-color: #ecf0f1; -fx-font-size: 14px;");

        Button viewInfoButton = createStyledButton("📜 View School Info");
        viewInfoButton.setOnAction(e -> animateText(school.toString()));

        Button addStudentButton = createStyledButton("➕ Add Student");
        addStudentButton.setOnAction(e -> addStudentDialog());

        Button removeStudentButton = createStyledButton("❌ Remove Student");
        removeStudentButton.setOnAction(e -> removeStudentDialog());

        Button viewStudentsButton = createStyledButton("👨‍🎓 View All Students");
        viewStudentsButton.setOnAction(e -> outputStudents());

        Button addCourses = createStyledButton("👨‍🏫 Add Courses");
        addCourses.setOnAction(e -> addCourseDialog());

        Button addTeacherButton = createStyledButton("👨‍🏫 Add Teacher");
        addTeacherButton.setOnAction(e -> addTeacherDialog());

        Button removeTeacherButton = createStyledButton("🗑️ Remove Teacher");
        removeTeacherButton.setOnAction(e -> removeTeacherDialog());

        Button viewTeachersButton = createStyledButton("👨‍🎓 View All Teachers");
        viewTeachersButton.setOnAction(e -> outputTeachers());

        Button exitButton = createStyledButton("🚪 Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(titleLabel, viewInfoButton, addStudentButton, removeStudentButton,
                viewStudentsButton, addCourses, addTeacherButton, removeTeacherButton, viewTeachersButton, outputArea, exitButton);

        primaryStage.setTitle("School Management");
        primaryStage.setScene(new Scene(root, 450, 600));
        primaryStage.show();
    }

    /* === Styyles === */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"));
        return button;
    }
    private void animateText(String text) {
        outputArea.setText(text);
        FadeTransition fade = new FadeTransition(Duration.millis(500), outputArea);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    /* === Teacher Operations === */
    private void removeTeacherDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Teacher");
        dialog.setHeaderText("Enter teacher ID to remove:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            try {
                Teacher toRemove = school.getTeacherById(Integer.parseInt(id));
                if (toRemove != null) {
                    school.removeTeacher(toRemove);
                    animateText("Teacher removed successfully!");
                } else {
                    animateText("Teacher not found!");
                }
            } catch (NumberFormatException ex) {
                animateText("Invalid ID!");
            }
        });
    }

    private void addTeacherDialog() {
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Add Teacher");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField ageField = new TextField();
        TextField idField = new TextField();
        TextField experienceField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(ageField, 1, 2);
        grid.add(new Label("ID:"), 0, 3);
        grid.add(idField, 1, 3);
        grid.add(new Label("Experience (years):"), 0, 4);
        grid.add(experienceField, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                int id = Integer.parseInt(idField.getText());
                int age = Integer.parseInt(ageField.getText());
                int experience = Integer.parseInt(experienceField.getText());
                Teacher newTeacher = new Teacher(nameField.getText(), age, surnameField.getText(), experience, id);
                school.hireTeacher(newTeacher);
                return newTeacher;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void outputTeachers(){
        StringBuilder sb = new StringBuilder("All Teachers:\n");
        List<Teacher> teachers = school.getTeachers(); // Получаем студентов из базы данных

        if (teachers.isEmpty()) {
            sb.append("No teachers found.");
        } else {
            for (Teacher teacher : teachers) {
                sb.append(teacher).append("\n");
            }
        }
        animateText(sb.toString());
    }


    /* === Student Operations === */
    private void addStudentDialog() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add Student");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField ageField = new TextField();
        TextField idField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(ageField, 1, 2);
        grid.add(new Label("ID:"), 0, 3);
        grid.add(idField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int age = Integer.parseInt(ageField.getText());
                    Student newStudent = new Student(nameField.getText(), id, age, surnameField.getText(), new ArrayList<>(), "");
                    school.registerStudent(newStudent);
                    animateText("Student added successfully!");
                    return newStudent;
                } catch (NumberFormatException ex) {
                    animateText("Invalid input!");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void removeStudentDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Student");
        dialog.setHeaderText("Enter student ID to remove:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            try {
                Student toRemove = school.getStudentById(Integer.parseInt(id));
                if (toRemove != null) {
                    school.removeStudent(toRemove);
                    animateText("Student removed successfully!");
                } else {
                    animateText("Student not found!");
                }
            } catch (NumberFormatException ex) {
                animateText("Invalid ID!");
            }
        });
    }

    private void outputStudents() {
        StringBuilder sb = new StringBuilder("All Students:\n");
        List<Student> students = school.getAllStudents(); // Получаем студентов из базы данных

        if (students.isEmpty()) {
            sb.append("No students found.");
        } else {
            for (Student student : students) {
                sb.append(student).append("\n");
            }
        }
        animateText(sb.toString());
    }

    private void addCourseDialog() {
        System.out.println("Opening Add Course Dialog");
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Add Course");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField courseNameField = new TextField();
        TextField teacherIdField = new TextField();
        TextField courseIdField = new TextField();

        grid.add(new Label("Course Name:"), 0, 0);
        grid.add(courseNameField, 1, 0);
        grid.add(new Label("Course ID:"), 0, 1);
        grid.add(courseIdField, 1, 1);
        grid.add(new Label("Teacher ID:"), 0, 2);
        grid.add(teacherIdField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int teacherId = Integer.parseInt(teacherIdField.getText());
                    int courseId = Integer.parseInt(courseIdField.getText());

                    Teacher teacher = school.getTeacherById(teacherId);
                    if (teacher != null) {
                        Course newCourse = new Course(teacher, courseNameField.getText(), courseId);
                        school.addCourse(newCourse);
                        animateText("Course added successfully!");
                        return newCourse;
                    } else {
                        animateText("Teacher not found!");
                    }
                } catch (NumberFormatException e) {
                    animateText("Invalid ID format! Please enter valid numbers.");
                }
            }
            return null;

        });
        dialog.showAndWait();
    }
}