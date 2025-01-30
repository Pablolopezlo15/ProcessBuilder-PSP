package com.processBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        String nombredeusuario = System.getProperty("user.name");
        System.out.println("Bienvenido, " + nombredeusuario + "!");
        do {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Comprobar Ping a un Servidor");
            System.out.println("2. Abrir Discord");
            System.out.println("3. Abrir Steam");
            System.out.println("4. Cerrar Discord y Steam");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    checkPing();
                    break;
                case 2:
                    openApplication("Discord", "C:\\Users\\"+nombredeusuario+"\\AppData\\Local\\Discord\\Update.exe --processStart Discord.exe");
                    break;
                case 3:
                    openApplication("Steam", "C:\\Program Files (x86)\\Steam\\steam.exe");
                    break;
                case 4:
                    closeApplications();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (option != 5);

        scanner.close();
    }

    // Método para comprobar el ping a un servidor
    private static void checkPing() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Comprobando ping a google.com ...");
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "google.com");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Leer la salida del comando
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Error al comprobar el ping: " + e.getMessage());
        }
    }

    // Método para abrir una aplicación
    private static void openApplication(String appName, String command) {
        try {
            System.out.println("Abriendo " + appName + "...");
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.start();
        } catch (Exception e) {
            System.out.println("Error al abrir " + appName + ": " + e.getMessage());
        }
    }

    // Método para cerrar Discord y Steam
    private static void closeApplications() {
        try {
            System.out.println("Cerrando Discord y Steam...");
            // Cerrar Discord
            ProcessBuilder closeDiscord = new ProcessBuilder("taskkill", "/F", "/IM", "Discord.exe");
            closeDiscord.start().waitFor();

            // Cerrar Steam
            ProcessBuilder closeSteam = new ProcessBuilder("taskkill", "/F", "/IM", "Steam.exe");
            closeSteam.start().waitFor();

            System.out.println("Discord y Steam han sido cerrados.");
        } catch (Exception e) {
            System.out.println("Error al cerrar las aplicaciones: " + e.getMessage());
        }
    }
}