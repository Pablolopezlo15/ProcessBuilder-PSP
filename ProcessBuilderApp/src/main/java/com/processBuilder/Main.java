package com.processBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
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
            System.out.println("5. Optimizar Equipo");
            System.out.println("6. Obtener Procesos");
            System.out.println("0. Salir");
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
                    closeSteamyDiscord();
                    break;
                case 5:
                    optimizarEquipo();
                    break;
                case 6:
                    obtenerDatosDeProcesos();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (option != 0);

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
    private static void closeSteamyDiscord() {
        try {
            System.out.println("Cerrando Discord y Steam...");
            // Cerrar Discord
            closeProcess("Discord.exe");
            // Cerrar Steam
            closeProcess("Steam.exe");

            System.out.println("Discord y Steam han sido cerrados.");
        } catch (Exception e) {
            System.out.println("Error al cerrar las aplicaciones: " + e.getMessage());
        }
    }

    public static void optimizarEquipo() throws IOException {
        List<Proceso> processes = getRunningProcesses();

        // Lista de procesos innecesarios a cerrar
        List<String> procesosABorrar = Arrays.asList(
                "chrome.exe", "brave.exe", "opera.exe", "firefox.exe",
                "spotify.exe", "discord.exe", "teams.exe", "slack.exe",
                "steam.exe", "epicgameslauncher.exe", "battle.net.exe",
                "riotclient.exe", "notion.exe", "zoom.exe", "skype.exe"
        );

        System.out.println("\nCerrando procesos innecesarios...\n");

        for (Proceso process : processes) {
            if (procesosABorrar.contains(process.getNombre().toLowerCase())) {
                closeProcess(process.getNombre());
                System.out.println("Proceso terminado: " + process.getNombre());
            }
        }

        System.out.println("\nOptimización finalizada.");
    }

    private static void obtenerDatosDeProcesos() throws IOException {
        List<Proceso> processes = getRunningProcesses();
        int contador = 1;

        System.out.println("Procesos en ejecución:");
        for (Proceso process : processes) {
            System.out.println(contador + ". " + process.getNombre() + " - Memoria usada: " + process.getMemoria());
            contador++;
        }

    }

    private static void closeProcess(String processName) throws IOException, IOException {
        System.out.println("Cerrando proceso: " + processName);
        ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", processName);
        processBuilder.start();
    }

    private static List<Proceso> getRunningProcesses() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();


        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        List<Proceso> processes = new ArrayList<>();

        reader.readLine();
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+", 5);

            if (parts.length == 5) {
                String nombre = parts[0];
                String id = parts[1];
                String tipo = parts[2];
                String memoria = parts[4];

                processes.add(new Proceso(nombre, id, tipo, memoria));
            }
        }
        return processes;
    }


}