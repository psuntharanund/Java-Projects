import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class bankApp{
    
    static Scanner kb = new Scanner(System.in);
    static HashMap<String, Integer> editedUsers = new HashMap<>();

    public static void main(String[] args){
        startProgram();
        System.out.println("Thank you for using my banking app. Please come again.");
    }
    
    public static void startProgram(){
        System.out.println("Hello, welcome to my basic banking app.");
        System.out.println("Please pick from the below options: \n" +
                            "1) Get user \n" +
                            "2) Create user \n" +
                            "3) Remove user \n" + 
                            "4) Update user \n");
        int choice = kb.nextInt();
        kb.nextLine();
        if (choice < 1 || choice > 4){
            choice = choiceValidation(kb);
        }
        chooseOperation(choice);
    }
    
    public static void chooseOperation(int choice){
        System.out.println("How many users would you like to add?");
        int size = kb.nextInt();
        kb.nextLine();
        for (int i = 0; i < size; i++){
            System.out.println("Please add user's name: ");
            String name = kb.nextLine();
            System.out.println("Please add user's ID: ");
            int ID = kb.nextInt();
            kb.nextLine();
            switch (choice){
                case 1: readUser(name, ID); break;
                case 2: createUser(name, ID); break;
                case 3: deleteUser(name, ID); break;
                case 4: updateUser(name, ID); break;
                default: System.out.println("You did not provide a valid input. Have a nice day.");
            }
        }   System.out.println(editedUsers);

    }
    public static void createUser(String name, int ID){
        boolean found = checkUser(name, ID);
        if (found){
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt", true))) {            
            writer.write(name + " : " + ID + "\n");
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readUser(String name, int ID){
        boolean found = true;
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.contains(name) && line.contains(String.valueOf(ID))){
                    System.out.println(line);
                } else {
                    found = false;
                    break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        if (!found){
            System.out.println("There are no accounts with these credentials.");
        }
    }
   
    public static void updateUser(String name, int ID) {
        ArrayList<String> updateLines = new ArrayList<>();
        checkUser(name, ID);
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(name) && line.contains(String.valueOf(ID))){
                    System.out.println("What would you like to update? Name/ID?");
                    String choice = kb.nextLine();
                    choice = choice.toLowerCase();
                    switch (choice){
                        case "name": System.out.println("What would you like to change the name to?");
                                    name = kb.nextLine();
                                    break;
                        case "id": System.out.println("What would you like to change the ID to?");
                                    ID = kb.nextInt();
                                    kb.nextLine();
                                    break;
                        default: System.out.println("You did not provide an appropriate attribute."); 
                    }
                    line = name + " : " + ID;
                }             
                updateLines.add(line);
            }
        }   catch (IOException e){
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"))){
            for (String line : updateLines){
                writer.write(line + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        editedUsers.remove(name, ID);
        System.out.println("User " + name + " information updated successfully.");
    }

    public static void deleteUser(String name, int ID) {
        // a temporary list to store lines to keep
        ArrayList<String> keepLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // check if line matches the namei and ID to be removed
                if (line.contains(name) && line.contains(String.valueOf(ID))) {
                    // skip this line (effectively removing it)
                    continue;
                }
                keepLines.add(line);  // keep all other lines
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // rewrite the file with only the kept lines
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"))) {
            for (String line : keepLines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // also remove from your HashMap
        editedUsers.remove(name, ID);
        System.out.println("User " + name + " removed successfully.");
    }

    public static boolean checkUser(String name, int ID){
        boolean found = false;
        //Read the file
        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                //check to see if my inputs already exist within the file
                if (line.contains(name) && line.contains(String.valueOf(ID))){
                    found = true;
                    break;
                } 
            }   
        }   catch (IOException e){
            e.printStackTrace();    
            }
        if (found){
            //if they do - write a message that says they're already in the file and you can't add.
            System.out.println("There is already an account with these credentials.");
        }
        return found;
    }
    
    public static int choiceValidation(Scanner kb){
        int choice;
        System.out.println("\nYou did not enter a valid number. Please re-enter: ");
        choice = kb.nextInt();
        kb.nextLine();
        while (choice < 1 || choice > 4){
            System.out.println("\nPlease re-enter a valid number: ");
            choice = kb.nextInt();
            kb.nextLine();
        }
        return choice;
    }
}
