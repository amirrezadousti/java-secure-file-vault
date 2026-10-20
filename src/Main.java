import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Path; // to use paths
import java.nio.file.Files; // to use files 

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        int ans = -1;
        String filePathName;

        while (ans != 3){

        System.out.println("=== R00Tsec Secure File Vault ===");
        System.out.println("1. Encrypt File");
        System.out.println("2. Decrypt File");
        System.out.println("3. Exit");

        try {
            ans = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Invalid entry. Please input number only.");
            scan.nextLine();
            continue; // Stop this iteration of the loop right here and immediately go back to the top.
        }

        if (ans < 1 || ans > 3) {
            System.out.println("Invalid entry. Please input numbers 1-3 only.");
        }

        else if (ans == 1){
            System.out.println("Please Enter file path: ");
            filePathName = scan.nextLine();

            System.out.println("Searching for: " + filePathName);
            Path filePath = Path.of(filePathName);
            
            if (Files.exists(filePath)){
                System.out.println("File Found!");

                //encrypt
            }
        }

        else if (ans == 2){
            
        }


        }
    }
}