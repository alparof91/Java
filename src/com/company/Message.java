package com.company;

public class Message {
    private String content;
    private String destination;

    public Message(String content, String destination) {
        this.content = content;
        this.destination = destination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
