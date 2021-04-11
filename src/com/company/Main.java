package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        //asking for the number of computers
        System.out.print("Number of computers (3 - 9): ");
        int nrComputers = Util.getNr(3,9);

        //create the executor service for the computer threads
        ExecutorService executorService = Executors.newCachedThreadPool();

        //create an array of computers and launch threads for each computer
        Computer[] computers = Util.launchThreads(nrComputers, executorService);

        //empty token object
        Token token = new Token();

        //start the token
        computers[0].setTokenPlace(token);

        //menu
        int op = 0;
        while (op != 5) {
            //display options
            System.out.print("\n\nOptions:\n" +
                    "--------\n" +
                    "1. Display Computers\n" +
                    "2. Send Message\n" +
                    "3. Display Token History\n" +
                    "4. Change direction\n" +
                    "5. Stop Token and Quit\n" +
                    "|> ");

            op = Util.getNr(1,5);
            switch (op) {
                case 1: {
                    Util.displayComputers(computers);
                    break;
                }
                case 2:{
                    Util.sendMessage(computers);
                    break;
                }
                case 3:{
                    Util.displayTokenHistory(computers);
                    break;
                }
                case 4:{
                    Util.changeDirection(computers);
                    break;
                }
                case 5:{
                    Util.stopTokenAndExit(computers);
                    executorService.shutdown();
                    break;
                }
            }
        }
        System.out.println("\nClosing the program...");
    }
}
