package com.fnrc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Player {
    private Color color;
    private Socket player = null;
    private BufferedReader inputMessage = null;
    private PrintWriter outputMessage = null;
    private Player opponent;

    public Player(Socket socket, Color color) {
        this.player = socket;
        this.color = color;
        this.initialize();
        outputMessage.println(color.getColor());
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    private void setBufferedReader() {
        try {
            this.inputMessage = new BufferedReader(new InputStreamReader(this.player.getInputStream()));
        } catch (IOException e) {
            System.out.println("Erro ao criar o buffer de leitura");
        }
    }

    public void setPrintWriter() {
        try {
            this.outputMessage = new PrintWriter(this.player.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Erro ao criar o buffer de escrita");
        }
    }

    public boolean isConnected() {
        return this.player != null
                && this.player.isConnected()
                && !this.player.isClosed();
    }

    public void close() {
        try {
            if (this.player != null) {
                this.player.close();
            }
            System.out.println("Jogador desconectado");
        } catch (IOException e) {
            System.out.println("Erro ao disconectar o jogador");
        }
    }

    public void sendMessage(String message) {
        this.outputMessage.println(message);
    }

    public void initialize() {
        setBufferedReader();
        setPrintWriter();
    }

    public String receiveMessage() {
        try {
            return this.inputMessage.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao receber mensagem: " + e.getMessage());
            return null;
        }
    }

    public void startListening() {
        Thread listenerThread = new Thread(() -> {
            try {
                String message;
                while ((message = this.inputMessage.readLine()) != null) {
                    System.out.println("Mensagem recebida do jogador " + color.getColor() + ": " + message);
                    if (opponent != null && opponent.isConnected()) {
                        opponent.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Conexão encerrada para o jogador " + color.getColor());
            }
        });
        listenerThread.start();
    }
}
