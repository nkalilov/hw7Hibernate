package peaksoft;

import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService  = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Asan", "Asanov", (byte) 25);
        userService.saveUser("Uson", "Usonov", (byte) 30);
        userService.saveUser("Aibek", "Sary", (byte) 30);
        for (User u :userService.getAllUsers()) {
            System.out.println(u);
        }
        System.out.println("----------------------------");
        userService.removeUserById(1);
        for (User u :userService.getAllUsers()) {
            System.out.println(u);
        }
        System.out.println("----------------------------");
        userService.cleanUsersTable();
        for (User u :userService.getAllUsers()) {
            System.out.println(u);
        }

    }
}
