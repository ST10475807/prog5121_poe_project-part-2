/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import javax.swing.*;
import java.util.Random;

public class ChatApp {

    static boolean isLoggedIn = false;
    static int numMessagesSent = 0;
    static Random random = new Random();

    // Registered user data
    static String registeredUsername;
    static String registeredPassword;

    public static void main(String[] args) {
        registerUser();  // Step 1: Register
        login();         // Step 2: Login

        if (isLoggedIn) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

            while (true) {
                String[] options = {"Send Messages", "Show Recently Sent Messages", "Quit"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Select an option:",
                        "Main Menu",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                switch (choice) {
                    case 0:
                        sendMessages();
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Feature Coming Soon.");
                        break;
                    case 2:
                    case JOptionPane.CLOSED_OPTION:
                        JOptionPane.showMessageDialog(null, "Exiting application.");
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid option.");
                }
            }
        }
    }

    // Register user with validation
    static void registerUser() {
        String name = JOptionPane.showInputDialog("Enter your name:");
        String surname = JOptionPane.showInputDialog("Enter your surname:");

        while (true) {
            registeredUsername = JOptionPane.showInputDialog("Enter a username (max 5 characters, must include '_'):");
            if (registeredUsername != null && registeredUsername.contains("_") && registeredUsername.length() <= 5) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username. Must be <= 5 characters and include an underscore.");
            }
        }

        while (true) {
            registeredPassword = JOptionPane.showInputDialog("Enter a password (8+ chars, 1 uppercase, 1 digit, 1 special):");
            if (isValidPassword(registeredPassword)) {
                JOptionPane.showMessageDialog(null, "Password is valid.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid password. Try again.");
            }
        }

        while (true) {
            String cellphone = JOptionPane.showInputDialog("Enter your cellphone number (10 digits):");
            if (cellphone != null && cellphone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid cellphone number. Must be 10 digits.");
            }
        }
    }

    // Login validation
    static void login() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");

        if (username != null && password != null &&
                username.equals(registeredUsername) && password.equals(registeredPassword)) {
            isLoggedIn = true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Incorrect username or password.");
            System.exit(0); // Exit if login fails
        }
    }

    // Password validation
    static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        String specialChars = "~'!@#$%^&*()_+=<>?/{}[];:'\",.";

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUpper = true;
            if (Character.isDigit(ch)) hasDigit = true;
            if (specialChars.contains(String.valueOf(ch))) hasSpecial = true;
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    // Message sending
    static void sendMessages() {
        String countInput = JOptionPane.showInputDialog("How many messages would you like to send?");
        if (countInput == null) return;

        int messageCount;
        try {
            messageCount = Integer.parseInt(countInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < messageCount; i++) {
            String messageID = String.format("%010d", random.nextInt(1000000000));
            numMessagesSent++;

            String recipient;
            do {
                recipient = JOptionPane.showInputDialog("Enter recipient number (max 10 digits, with country code):");
                if (recipient == null) return;
            } while (!recipient.matches("\\d{1,10}"));

            String message;
            while (true) {
                message = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
                if (message == null) return;
                if (message.length() <= 250) break;
                JOptionPane.showMessageDialog(null, "Message too long. Must be <= 250 characters.");
            }

            String[] words = message.trim().split("\\s+");
            String firstWord = words.length > 0 ? words[0] : "NULL";
            String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
            String messageHash = messageID.substring(0, 2) + ":" + numMessagesSent + ":" +
                    firstWord.toUpperCase() + lastWord.toUpperCase();

            // Choose send option
            String[] sendOptions = {"Send", "Disregard", "Store for Later"};
            int sendChoice = JOptionPane.showOptionDialog(
                    null,
                    "Choose what to do with this message:",
                    "Send Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    sendOptions,
                    sendOptions[0]
            );

            switch (sendChoice) {
                case 0:
                    JOptionPane.showMessageDialog(null,
                            "Message sent!\nMessage ID: " + messageID + "\nHash: " + messageHash);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Message disregarded.");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Message stored for later.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "No valid option selected.");
                    break;
            }
        }
    }
}
