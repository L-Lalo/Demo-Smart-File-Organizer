package com.example.smartorganizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import java.io.File;

import java.io.IOException;

public class HelloApplication extends Application {
    private Label statusLabel;
    private File selectDirectory;
    @Override
    public void start(Stage primeryStage) throws IOException {
        //Frontend según yo
        primeryStage.setTitle("Demo Smart File Organizer v.1");
        statusLabel = new Label("No directory selected. Please choose a folder.");
        Button browseButton = new Button("Select folder");
        Button organizeButton = new Button("Organize by format");
        //Backend huevaaa
        browseButton.setOnAction(actionEvent -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select folder to organize.");
            selectDirectory = directoryChooser.showDialog(primeryStage);

            if (selectDirectory != null){
                statusLabel.setText("TARGET: " + selectDirectory.getAbsolutePath());
            }
        });

        organizeButton.setOnAction(actionEvent -> {
            if (selectDirectory == null) {
                statusLabel.setText("Error: Please select a folder first.");
                return;
            }
            File[] filesList = selectDirectory.listFiles();
            if (filesList == null || filesList.length == 0) {
                statusLabel.setText("This folder is already empty.");
                return;
            }

            int movedCount = 0;

            for (File file : filesList) {
                if (file.isFile()) {
                    String fileName = file.getName().toLowerCase();
                    String folderName = "Others";

                    if (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".flac")) {
                        folderName = "Music";
                    } else if (fileName.endsWith(".docx") || fileName.endsWith(".xlsx") || fileName.endsWith(".pptx") || fileName.endsWith(".txt")) {
                        folderName = "Documents";
                    } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        folderName = "Images";
                    } else if (fileName.endsWith(".pdf")) {
                        folderName = "PDF´S";
                    } else if (fileName.endsWith("mp4")){
                        folderName = "Videos";
                    }
                    try {
                        File targetFolder = new File(selectDirectory, folderName);
                        if (!targetFolder.exists()){
                            targetFolder.mkdir();
                        }
                        File destFile = new File(targetFolder,file.getName());
                        if(file.renameTo(destFile)){
                            System.out.println("[MOVED] -> " + file.getName() + " to "+ folderName);
                            movedCount++;
                        }
                    } catch (Exception ex) {
                        System.out.println("Error moving file: " +  file.getName());
                        ex.printStackTrace();
                    }
                }
            }
            if (movedCount > 0) {
                statusLabel.setText("Success! Organized " + movedCount + " files successfully.");
            } else {
                statusLabel.setText("No loose files found to organize.");
            }
        });


        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(browseButton, statusLabel, organizeButton);
        Scene scene = new Scene(root,460,320);
        primeryStage.setScene(scene);
        primeryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
