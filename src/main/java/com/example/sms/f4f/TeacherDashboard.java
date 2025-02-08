package com.example.sms.f4f;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class TeacherDashboard extends Application {
    private ImageView profileImageView = new ImageView();
    private TextField nameField = new TextField("Имя Учителя");
    private ListView<String> classListView = new ListView<>();
    private ListView<String> studentListView = new ListView<>();
    private TextField gradeField = new TextField();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Teacher Dashboard");

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(createProfileTab(), createClassesTab(), createGradesTab());

        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /* === Profile tab === */
    private Tab createProfileTab() {
        Tab tab = new Tab("Профиль");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        Button uploadButton = new Button("Загрузить фото");
        uploadButton.setOnAction(e -> uploadPhoto());

        layout.getChildren().addAll(profileImageView, nameField, uploadButton);
        tab.setContent(layout);
        return tab;
    }

    private void uploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            profileImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    /* === Classrooms tab === */
    private Tab createClassesTab() {
        Tab tab = new Tab("Классы");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        classListView.getItems().addAll("Класс 1", "Класс 2", "Класс 3");
        Button openClassButton = new Button("Открыть класс");
        openClassButton.setOnAction(e -> openClass());

        layout.getChildren().addAll(classListView, openClassButton);
        tab.setContent(layout);
        return tab;
    }

    private void openClass() {
        String selectedClass = classListView.getSelectionModel().getSelectedItem();
        if (selectedClass != null) {
            studentListView.getItems().setAll("Студент 1", "Студент 2", "Студент 3");
        }
    }

    /* === Grades tab === */
    private Tab createGradesTab() {
        Tab tab = new Tab("Оценки");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        studentListView.getItems().addAll("Студент 1", "Студент 2", "Студент 3");
        Button updateGradeButton = new Button("Изменить оценку");
        updateGradeButton.setOnAction(e -> updateGrade());

        layout.getChildren().addAll(studentListView, gradeField, updateGradeButton);
        tab.setContent(layout);
        return tab;
    }

    private void updateGrade() {
        String selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null && !gradeField.getText().isEmpty()) {
            System.out.println("Оценка для " + selectedStudent + ": " + gradeField.getText());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
