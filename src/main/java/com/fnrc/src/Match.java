package com.fnrc.src;

import com.fnrc.src.exception.ReceiveMessageException;

import java.net.Socket;

public class Match extends Thread {
    private Player[] players = new Player[2];

    public Match(Socket socketOne, Socket socketTwo) {
        Player playerOne = new Player(socketOne, Color.WHITE);
        playerOne.sendMessage(playerOne.getColor().getColor());
        this.players[0] = playerOne;
        System.out.println("Jogador 1 conectado.");

        Player playerTwo = new Player(socketTwo, Color.BLACK);
        playerTwo.sendMessage(playerTwo.getColor().getColor());
        this.players[1] = playerTwo;
        System.out.println("Jogador 2 conectado.");
    }

    @Override public void run() {
        this.sendMessageAll("match");

        while(this.players[0].isConnected() && this.players[1].isConnected()) {
            try {
                String message = this.players[0].receiveMessage();
                this.players[1].sendMessage(message);

                message = this.players[1].receiveMessage();
                this.players[0].sendMessage(message);
            }
            catch (ReceiveMessageException exception) {
                System.out.println(exception.getMessage());
                break;
            }
        }

        System.out.println("Partida finalizada.");

        for (Player player : this.players) {
            player.close();
        }
    }

    public void sendMessageAll(String message) {
        for(Player player : this.players) {
            player.sendMessage(message);
        }
    }

}
