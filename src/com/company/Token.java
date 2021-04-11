package com.company;

import java.util.ArrayList;

public class Token {
    private String source;
    private String destination;
    private String message;
    private boolean isAtDest;
    private boolean isAvailable;
    private final ArrayList<String> history;

    public Token() {
        this.history = new ArrayList<>();
        this.isAtDest = false;
        this.isAvailable = true;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAtDest() {
        return isAtDest;
    }

    public void setAtDest(boolean atDest) {
        isAtDest = atDest;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void addHistory(String historyMessage){
        history.add(historyMessage);
    }

    @Override
    public String toString() {
        return "Token{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", message='" + message + '\'' +
                ", isAtDest=" + isAtDest +
                ", isAvailable=" + isAvailable +
                ", history=" + history +
                '}';
    }
}
