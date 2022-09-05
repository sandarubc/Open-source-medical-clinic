package lk.ijse.dep9.clinic.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import lk.ijse.dep9.clinic.security.SecurityContextHolder;

public class AdminDashBoardFoarmController {
    public JFXButton btnProfileManagment;
    public JFXButton btnViewRecords;
    public JFXButton btnSettings;

    public void initialize(){
        System.out.println(SecurityContextHolder.user.getUserName());
    }

    public void btnProfileManagmentOnAction(ActionEvent actionEvent) {
    }

    public void btnViewRecordsOnAction(ActionEvent actionEvent) {
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) {
    }
}
