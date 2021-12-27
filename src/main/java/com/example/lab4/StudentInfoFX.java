package com.example.lab4;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentInfoFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setHgap(5);
        root.setVgap(5);
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.BOTTOM_CENTER);
        bottomPane.setHgap(5);
        bottomPane.setVgap(5);
        GridPane leftPane = new GridPane();
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.setHgap(5);
        leftPane.setVgap(5);
        GridPane rightPane = new GridPane();
        rightPane.setAlignment(Pos.TOP_LEFT);
        rightPane.setHgap(2);
        rightPane.setVgap(5);
        GridPane radioPane = new GridPane();
        radioPane.setAlignment(Pos.BOTTOM_CENTER);
        radioPane.setHgap(5);
        radioPane.setVgap(5);
        GridPane rightLeftContainer = new GridPane();
        rightLeftContainer.setAlignment(Pos.TOP_LEFT);
        rightLeftContainer.setHgap(2);
        rightLeftContainer.setVgap(5);

        Label[] myLabel;
        String[] names = {"Name: ", "Address: ", "Province: ", "City: ", "Postal Code: ", "Phone Number: ", "Email: "};
        TextField[] textFields;
        ComboBox    <String> course;
        RadioButton buttonComputerScience, buttonBusiness;
        ToggleGroup groupStudentMajor;
        CheckBox checkStudentCouncil, checkVolunteerWork;
        Button display;
        ListView<String> courseList;
        ObservableList<String> listModel = FXCollections.observableArrayList();
        ScrollPane scroll;
        TextArea output;
        ObservableList<String> ComputerScience = FXCollections.observableArrayList("Python", "C#", "Java");
        ObservableList<String> Business = FXCollections.observableArrayList("Accounting", "Business management", "Marketing");

        buttonComputerScience = new RadioButton("Computer Science");
        buttonBusiness = new RadioButton("Business");

        checkStudentCouncil = new CheckBox("Student Council");
        checkVolunteerWork = new CheckBox("Volunteer Work");

        groupStudentMajor = new ToggleGroup();
        buttonComputerScience.setToggleGroup(groupStudentMajor);
        buttonBusiness.setToggleGroup(groupStudentMajor);

        courseList = new ListView<String>(listModel);
        courseList.setPrefSize(1,100);

        course = new ComboBox<String>();
        course.setVisibleRowCount(3);
        course.setPrefSize(300,60);

        output = new TextArea();
        output.setPrefColumnCount(100);
        output.setPrefRowCount(6);
        output.setEditable(false);

        scroll = new ScrollPane(output);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        GridPane.setHalignment(scroll, HPos.CENTER);

        myLabel = new Label[names.length];
        textFields = new TextField[names.length];

        for (int i = 0; i < names.length; i++) {
            myLabel[i] = new Label(names[i]);
            textFields[i] = new TextField();
            leftPane.add(myLabel[i], 0, i);
            leftPane.add(textFields[i], 5, i);
        }

        display = new Button("Display");
        GridPane.setHalignment(display, HPos.CENTER);

        rightPane.add(radioPane,7,0);
        rightPane.add(course,7,2);
        rightPane.add(courseList, 7,3);

        radioPane.add(buttonComputerScience, 0,0);
        radioPane.add(buttonBusiness,1,0);

        leftPane.add(checkStudentCouncil,6,0);
        leftPane.add(checkVolunteerWork,6,3);

        bottomPane.add(display,0,6);
        bottomPane.add(scroll,0,7);

        rightLeftContainer.add(leftPane,0,0);
        rightLeftContainer.add(rightPane,1,0);

        root.add(rightLeftContainer,0,0);
        root.add(bottomPane,0,1);

        groupStudentMajor.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle newToggle) {
                RadioButton checked = (RadioButton) groupStudentMajor.getSelectedToggle();
                if (checked.getText().equals("Computer Science")){
                    course.getSelectionModel().clearSelection();
                    listModel.clear();
                    course.setItems(ComputerScience);
                }
                else {
                    if(checked.getText().equals("Business")){
                        course.getSelectionModel().clearSelection();
                        listModel.clear();
                        course.setItems(Business);
                    }
                }
            }
        });

        course.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String s, String t1) {
                if(!listModel.contains(t1) && t1 != null){
                    listModel.add(t1);
                }
            }
        });

        display.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                String myCourses = "";
                String otherActivities = "";
                String program = "";

                if(checkStudentCouncil.isSelected() && checkVolunteerWork.isSelected()){
                    otherActivities = checkStudentCouncil.getText().trim() + " - " + checkVolunteerWork.getText().trim();
                }
                else if(checkStudentCouncil.isSelected() && !checkVolunteerWork.isSelected()){
                    otherActivities = checkStudentCouncil.getText().trim();
                }
                else if(!checkStudentCouncil.isSelected() && checkVolunteerWork.isSelected()){
                    otherActivities = checkVolunteerWork.getText().trim();
                }
                else if(!checkStudentCouncil.isSelected() && !checkVolunteerWork.isSelected()){
                    otherActivities = "No activities";
                }

                if (buttonBusiness.isSelected()){
                    program = buttonBusiness.getText();
                }
                else if(buttonComputerScience.isSelected()){
                    program = buttonComputerScience.getText();
                }
                else{
                    program = "No program selected";
                }

                output.setText("");

                for(int i=0;i<listModel.size();i++){
                    myCourses += listModel.get(i) + "\n";
                }
                output.setText(String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s%n%s%n%s", textFields[0].getText(),
                        textFields[1].getText(),textFields[2].getText(),textFields[3].getText(),textFields[4].getText(),
                        textFields[5].getText(),textFields[6].getText(), otherActivities, program, "Courses: ", myCourses));
            }
        });
        Scene scene = new Scene(root, 850,400);
        primaryStage.setTitle("Student Information form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
