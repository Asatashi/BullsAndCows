package bullscows;
import java.util.*;
import java.lang.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userNumber = 1;
        int codeLength = 2;
        try {
            System.out.println("Please, enter the secret code's length:");
            userNumber = scanner.nextInt();     //number of digits
            System.out.println("Input the number of possible symbols in the code:");
            codeLength = scanner.nextInt();     //number of symbols saltChars from to shuffle
        } catch (InputMismatchException e) {
            System.out.println("Error: " + userNumber + " isn't a valid number.");
            switch (userNumber) {
                case 1:
                    System.out.println("Error: " + userNumber + " isn't a valid number.");
                    System.exit(0);
                case 2:
                    System.out.println("Error: " + codeLength + " isn't a valid number.");
                    System.exit(0);
            }
        }
        String saltChars = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder characters = new StringBuilder(codeLength);
        characters.append(saltChars);         // StringBuilder = saltChars
        Random rand = new Random();
        char[] preText = new char[codeLength];
        try {
            for (int i = 0; i < codeLength; i++) {
                preText[i] = characters.charAt(i);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println();
        }
        StringBuilder randomString = new StringBuilder(String.valueOf(preText));
        System.out.println(randomString);
        StringBuilder randomStringLoop = new StringBuilder();
        char[] text = new char[codeLength];
        try {
            for (int i = 0; i < userNumber; i++) {
                text[i] = randomString.charAt(rand.nextInt(randomString.length()));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: it's not possible to generate a code with a length of " + userNumber + " with " + codeLength + " unique symbols.");
            System.exit(0);
        }
        for (char c : text) {
            randomStringLoop.append(c);
        }
        String randomStringNotB = randomStringLoop.toString();
        System.out.println(randomStringNotB);
        String y;      //printing digits, and if there are any counted(if length of symbols is more than 10) letters which are possible
        try {
            if (codeLength <= 10) {
                int loopj = codeLength - 1;
                y = "(0-" + loopj + ")";
            } else {
                int loop = codeLength - 1;
                char saltInMain = saltChars.charAt(loop);
                y = "(0-9, a-" + saltInMain + ")";
            }
            System.out.println("The secret code is prepared: " + //number of asterisks depends from number of digits in code
                    "*".repeat(Math.max(0, userNumber)) //printing digits and if there are any counted(if length of symbols is more than 10) letters which are possible
                    + " " + y);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
        if (userNumber > 36 || userNumber < 1) {
            System.out.println("Error: can't generate a secret number with a length of 37 because there aren't enough unique digits. The same goes for 0 char word.");
            System.exit(0);
        } else {
            System.out.println("Okay, let's start a game!");
            for (int l = 1; l < 100000; l++) {
                System.out.println("Turn " + l + ":");      //counting turns
                String guess = scanner.next();
                int bullCount = 0;
                int cowCount = 0;
                for (int i = 0; i < userNumber; i++) {       //cows counter
                    char charGuess = guess.charAt(i);
                    String stringGuess = String.valueOf(charGuess);
                    if (randomStringNotB.contains(stringGuess)) {
                        cowCount++;
                    }
                }
                for (int j = 0; j < userNumber; j++) {       //bulls counter
                    char charCode = randomStringNotB.charAt(j);
                    char charGuess = guess.charAt(j);
                    if (charCode == charGuess) {
                        bullCount++;
                        cowCount--;
                    }
                }
                if (bullCount == userNumber) {       //end of the game, else print how many bulls and cows are in the guessed code
                    System.out.println("Grade: " + bullCount + " bull(s)");
                    System.out.println("Congratulations! You guessed the secret code.");
                    break;
                } else {
                    System.out.println("Grade: " + bullCount + " bull(s) " + cowCount + " cow(s).");
                }
            }
        }
    }
}