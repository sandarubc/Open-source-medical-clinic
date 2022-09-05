package lk.ijse.dep9.clinic.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.dep9.clinic.misc.CryptoUtil;
import lk.ijse.dep9.clinic.security.SecurityContextHolder;
import lk.ijse.dep9.clinic.security.User;
import lk.ijse.dep9.clinic.security.UserRole;

import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnLogin;


    public void initialize(){





    }

    public boolean checkUserName(String uname){
        Pattern compile = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher matcher = compile.matcher(uname);

        boolean matches = matcher.matches();
        return matches;
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if(userName.isBlank()){
            new Alert(Alert.AlertType.ERROR,"User name cant be empty").show();
            txtUserName.requestFocus();
            txtUserName.selectAll();
            return;
        } else if(password.isBlank()){
            new Alert(Alert.AlertType.ERROR,"Password cant be empty").show();
            txtPassword.requestFocus();
            txtPassword.selectAll();
            return;
        } else if (!checkUserName(userName)) {
            new Alert(Alert.AlertType.ERROR,"Invalid Username Format").show();
            txtUserName.requestFocus();
            txtUserName.selectAll();

        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_clinic","root","2012")) {

            System.out.println(connection);

//            String sql = String.format("SELECT role FROM User WHERE username='%s' AND password='%s';", userName, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);



//            String sql="SELECT role FROM User WHERE username=? AND password=?;";
//            PreparedStatement sta = connection.prepareStatement(sql);
//            sta.setString(1,userName);
//            sta.setString(2,password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = sta.executeQuery();


            String sql="SELECT password,role FROM User WHERE username=?;";
            PreparedStatement sta = connection.prepareStatement(sql);
            sta.setString(1,userName);
            ResultSet resultSet = sta.executeQuery();




            if(resultSet.next()){
                String ciperText=resultSet.getString("password");
                System.out.println(CryptoUtil.sha256String(password));
                boolean equals = CryptoUtil.sha256String(password).equals(ciperText);
                if(!equals){
                    new Alert(Alert.AlertType.ERROR,"Password is Wrong!").show();
                    txtPassword.requestFocus();
                    txtPassword.selectAll();
                    return;
                }

                String role = resultSet.getString("role");
                User user = new User(userName, password, UserRole.valueOf(role));
                System.out.println(user);
                SecurityContextHolder.setPrinciple(user);

                URL resource=null;
                switch (role){
                    case "Admin":
                        resource = this.getClass().getResource("/view/AdminDashBoardForm.fxml");
                        break;
                    case "Doctor":
                        resource=this.getClass().getResource("/view/DoctorDashBoardForm.fxml");
                        break;

                    default:
                        resource=this.getClass().getResource("/view/ReceptionistDashBoardForm.fxml");
                }
                System.out.println(resource);
                Scene scene = null;
                if (resource != null) {
                    scene = new Scene(FXMLLoader.load(resource));
                }
                else{
                    System.out.println("why null?");
                }
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                btnLogin.getScene().getWindow().hide();


            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid Login Credentials").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to connect database,try agin").show();
        }
    }
}
