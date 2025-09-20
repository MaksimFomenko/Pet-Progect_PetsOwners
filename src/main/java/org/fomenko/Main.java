package org.fomenko;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.addUser(new User(1, "Maksim", "maksim@gmail.com"));
        userService.addUser(new User(4, "Maksim", "maksim1@gmail.com"));
        userService.addUser(new User(2, "Miroslava", "miroslava@gmail.com"));
        try {
            userService.addUser(new User(2, "Miroslava", "miroslava@gmail.com"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        userService.addUser(new User(3, "Alina", "alina@gmail.com"));

        System.out.println(userService.findUserByID(2));

        userService.deleteUserByEmail("maksim1@gmail.com");

        userService.getAllUsers().forEach(System.out::println);

        System.out.println("Find! " + userService.getUserByName("Alina"));
    }
}
