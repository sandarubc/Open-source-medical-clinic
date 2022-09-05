package lk.ijse.dep9.clinic.security;

public class SecurityContextHolder {

    public static User user;

    public static void setPrinciple(User user){

        if(user==null){
            throw new NullPointerException("Principle can't be null");
        }else if(user.getUserName()==null ||user.getPassword().isBlank()|| user.getRole()==null){
            throw new RuntimeException("Invalid User");
        }

        SecurityContextHolder.user=user;

    }


}
