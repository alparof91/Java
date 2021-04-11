package com.company;


import java.util.ArrayDeque;
import java.util.ArrayList;

public class Computer implements Runnable{
    static int instanceNr = 0;

    private final String ip;
    private boolean onOff;
    private Computer left;
    private Computer right;
    private Token tokenPlace;
    private ArrayDeque<Message> messageToSend;
    private ArrayList<String> receivedMessage;


    public Computer(String ip) {
        this.ip = ip;
        this.onOff = true;
        this.messageToSend = new ArrayDeque<>();
        this.receivedMessage = new ArrayList<>();
        instanceNr++;
    }

    public boolean isOnOff() {
        return onOff;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    public String getIp() {
        return ip;
    }

    public Computer getLeft() {
        return left;
    }

    public void setLeft(Computer left) {
        this.left = left;
    }

    public Computer getRight() {
        return right;
    }

    public void setRight(Computer right) {
        this.right = right;
    }

    public Token getTokenPlace() {
        return tokenPlace;
    }

    public void setTokenPlace(Token tokenPlace) {
        this.tokenPlace = tokenPlace;
    }

    public void addMessageToSend(Message message){
        this.messageToSend.add(message);
    }

    @Override
    public void run() {
        Message message;
        while (onOff){
            try {
                //incrementing delay depending the instanceNr
                Thread.sleep(2000 + 10L * instanceNr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (tokenPlace != null) {
                if(tokenPlace.isAvailable() && !messageToSend.isEmpty()) {
                    message = messageToSend.poll();
                    tokenPlace.setSource(ip);
                    tokenPlace.setMessage(message.getContent());
                    tokenPlace.setDestination(message.getDestination());
                    tokenPlace.setAvailable(false);
                    tokenPlace.addHistory("Sending: " + message);
//                    System.out.println(tokenPlace);
                }

                if(!tokenPlace.isAvailable() && tokenPlace.getDestination().equals(ip) && !tokenPlace.isAtDest()){
                    tokenPlace.setAtDest(true);
                    receivedMessage.add(tokenPlace.getMessage());
                    tokenPlace.setDestination(tokenPlace.getSource());
                    tokenPlace.setSource(ip);
                    tokenPlace.addHistory("Received! Sending confirmation back to source: " + tokenPlace.getDestination());
//                    System.out.println(tokenPlace);
                }

                if(!tokenPlace.isAvailable() && tokenPlace.getDestination().equals(ip) && tokenPlace.isAtDest()){
                    tokenPlace.addHistory("Message delivered successfully to " + tokenPlace.getDestination());
                    tokenPlace.setMessage("");
                    tokenPlace.setDestination("");
                    tokenPlace.setSource("");
                    tokenPlace.setAvailable(true);
                    tokenPlace.setAtDest(false);
//                    System.out.println(tokenPlace);
                }

                tokenPlace.addHistory("Token passed " + ip);
                right.setTokenPlace(tokenPlace);
                tokenPlace = null;
//                System.out.println(ip);
            }
        }
        System.out.println("Shutting down " + ip);
    }
}
