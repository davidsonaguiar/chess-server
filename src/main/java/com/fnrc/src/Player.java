package com.fnrc.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.fnrc.src.chess.Color;

public class Player {
    private Color color;
    private Socket player = null;
    private BufferedReader inputMessage = null;
    private PrintWriter outputMessage = null;

    public Player(Socket socket, Color color) {
        this.player = socket;
        this.color = color;
        this.initialize();
        outputMessage.println("Conectado ao servidor de xadrez");
        outputMessage.println("Aguardando oponente");

        System.out.println("Jogador conectado " + this.color);
    }

    public Socket getSocket() {
        return this.player;
    }

    public Color getColor() {
        return this.color;
    }

    public BufferedReader getInputMessage() {
        return this.inputMessage;
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

//    public void startListening() {
//        Thread output = new Thread(() -> {
//            try {
//                String serverMessage;
//                while ((serverMessage = this.inputMessage.readLine()) != null) {
//                    System.out.println("Servidor: " + serverMessage);
//                    if ("init".equals(serverMessage)) {
//                        System.out.println("Partida iniciada!");
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Conexão com o servidor foi encerrada.");
//            }
//        });
//
//        output.start();
//    }

    public void initialize() {
        setBufferedReader();
        setPrintWriter();
//        startListening();
    }

    public String receiveMessage() {
        try {
            return this.inputMessage.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao receber mensagem: " + e.getMessage());
            return null;
        }
    }
}
