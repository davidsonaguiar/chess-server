package com.fnrc.src;

import com.fnrc.src.exception.ReceiveMessageException;

public class Match extends Thread {
    private final Player white;
    private final Player black;

    public Match(Player white, Player black) {
        this.white = white;
        System.out.println("Jogador 1 conectado.");

        this.black = black;
        System.out.println("Jogador 2 conectado.");
    }

    @Override public void run() {
        this.sendMessageAll("match");

        while(this.white.isConnected() && this.black.isConnected()) {
            try {
                String message = this.white.receiveMessage();
                this.black.sendMessage(message);

                message = this.black.receiveMessage();
                this.white.sendMessage(message);
            }
            catch (ReceiveMessageException exception) {
                System.out.println(exception.getMessage());
                break;
            }
        }

        System.out.println("Partida finalizada.");

        white.close();
        black.close();
    }

    public void sendMessageAll(String message) {
        white.sendMessage(message);
        black.sendMessage(message);
    }

}
