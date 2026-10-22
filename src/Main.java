import java.util.InputMismatchException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner; // to use scanner
import java.nio.file.Path; // to use paths
import java.nio.file.Files; // to use files
import javax.crypto.KeyGenerator; // Generates cryptographic keys, such as AES keys
import javax.crypto.SecretKey;    // Represents the secret key used for encryption/decryption
import java.security.SecureRandom; // Generates secure random values for cryptography
import javax.crypto.Cipher; // Performs the actual encryption and decryption
import javax.crypto.spec.GCMParameterSpec; // Stores AES-GCM settings like authentication tag size and nonce


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

        else if (ans == 1){ // Encyrpt File
            System.out.println("Please Enter file path: ");
            filePathName = scan.nextLine();

            System.out.println("Searching for: " + filePathName);
            Path filePath = Path.of(filePathName);
            
            if (Files.exists(filePath)){
                System.out.println(); //Spacing
                System.out.println("File Found!");

                //encryption
                byte[] fileData = readFile(filePath); // read the file
                SecretKey key = generateKey(); // generate secret key
                byte[] nonce = generateNonce(); // generate nonce

                byte[] encryptedData = encryptData(fileData, key, nonce); //encrypt using the filedata, key and nonce


                Path encryptedPath = Path.of(filePathName + ".enc"); // make a new path for the encypted file

                try{
                    Files.write(encryptedPath, encryptedData);
                    System.out.println("Encrypted file saved to: " + encryptedPath);

                }catch(IOException e){
                    System.out.println("Could not save encrypted file.");
                }


                //temp
                System.out.println("Original size: " + fileData.length + " bytes");
                System.out.println("Encrypted size: " + encryptedData.length + " bytes");
                //temp
                
                System.out.println(); //Spacing
            }
        }

        else if (ans == 2){ // Decrypt file
            
        }


        }
    }

    public static byte[] readFile (Path filePath){
        try{
        return Files.readAllBytes(filePath);
        } catch (IOException e){
            System.out.println("Could not read file.");
            return null;
        }
    }

    public static SecretKey generateKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e){
            System.out.println("AES algorithm not available.");
            return null;
        }
    }

    public static byte[] generateNonce() {
        byte[] nonce = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        return nonce;
    }

    public static byte[] encryptData(byte[] fileData, SecretKey key, byte[] nonce) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Create an AES-GCM cipher

            GCMParameterSpec spec = new GCMParameterSpec(128, nonce); // Give GCM the nonce and 128-bit authentication tag size

            cipher.init(Cipher.ENCRYPT_MODE, key, spec); // Set the cipher to encrypt using this key and nonce

            return cipher.doFinal(fileData); // Encrypt the file bytes and return the ciphertext

        } catch (GeneralSecurityException e) { // Catch crypto-related errors
            System.out.println("Encryption failed."); // Show an error message
            return null; // Return nothing useful if encryption fails
        }

    }




}

 