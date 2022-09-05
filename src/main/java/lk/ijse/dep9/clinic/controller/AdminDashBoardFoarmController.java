package lk.ijse.dep9.clinic.controller;

import lk.ijse.dep9.clinic.security.SecurityContextHolder;

public class AdminDashBoardFoarmController {
    public void initialize(){
        System.out.println(SecurityContextHolder.user.getUserName());
    }
}
