package com.company;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class Util {

    public static int getNr(int from, int to){
        int nrComputers = 0;
        Scanner scanner = new Scanner(System.in);

        //testing the input
        while (nrComputers == 0) {
            try {
                nrComputers = scanner.nextInt();
                if (nrComputers < from || nrComputers > to) {
                    System.out.print("Not in range! Try again: ");
                    nrComputers = 0;
                }
            } catch (Throwable t) {
                System.out.print("Not a number! Try again: ");
                scanner.next();
            }
        }
        return nrComputers;
    }

    public static Computer[] launchThreads(int nrComputers, ExecutorService es){
        //creating the Computer objects
        String ip;
        Computer[] computers = new Computer[nrComputers];
        for (int i = 0; i < nrComputers; i++) {
            ip = "192.168.0.10" + i;
            computers[i] = new Computer(ip);
        }

        //setting up relations and submitting the threads to executorService
        for (int i = 0; i < nrComputers; i++) {
            if (i==0) {
                computers[i].setLeft(computers[nrComputers - 1]);
                computers[i].setRight(computers[i + 1]);
            } else if (i==nrComputers-1){
                computers[i].setLeft(computers[i - 1]);
                computers[i].setRight(computers[0]);
            } else {
                computers[i].setLeft(computers[i - 1]);
                computers[i].setRight(computers[i + 1]);
            }
            es.submit(computers[i]);
        }
        return computers;
    }

    public static void displayComputers(Computer[] computers){
        System.out.println("\nComputers in the ring:");
        for (Computer computer : computers) {
            System.out.println(computer.getIp());
        }
    }

    //helper for sendMessage
    public static int isValidIp(Computer[] computers, String searched){
        for (int i = 0; i < computers.length; i++) {
            if (searched.equals(computers[i].getIp()))
                return i;
        }
        return 999;
    }

    public static void sendMessage(Computer[] computers){
        System.out.print("\nSource ip: ");
        Scanner scanner = new Scanner(System.in);
        String source = scanner.nextLine();
        while (isValidIp(computers,source) == 999) {
            System.out.print("Not a valid ip! Try again: ");
            source = scanner.nextLine();
        }

        System.out.print("Destination ip: ");
        String destination = scanner.nextLine();
        while (isValidIp(computers,destination) == 999) {
            System.out.print("Not a valid ip! Try again: ");
            destination = scanner.nextLine();
        }

        System.out.print("Message: ");
        String messageStr = scanner.nextLine();

        Message message = new Message(messageStr,destination);
        computers[isValidIp(computers,source)].addMessageToSend(message);
    }

    public static void displayTokenHistory(Computer[] computers){
        System.out.println("\nToken History:");
        for (Computer computer : computers) {
            if (computer.getTokenPlace()!=null){
                for (String i : computer.getTokenPlace().getHistory()) {
                    System.out.println(i);
                }
            }
        }
    }

    public static void changeDirection(Computer[] computers){
        System.out.println("\nDirection changed!");
        Computer temp;
        for (Computer computer : computers) {
            temp = computer.getLeft();
            computer.setLeft(computer.getRight());
            computer.setRight(temp);

            if (computer.getTokenPlace()!=null){
                computer.getTokenPlace().addHistory("Direction changed!");
            }
        }

    }

    public static void stopTokenAndExit(Computer[] computers){
        for (Computer computer : computers) {
            computer.setOnOff(false);
        }
    }
}
