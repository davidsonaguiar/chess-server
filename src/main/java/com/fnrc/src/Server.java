package com.fnrc.src;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server = null;
    private int PORT = 8080;

    public Server() {
        try {
            System.out.println("Iniciando o servidor xadrez");
            this.server = new ServerSocket(this.PORT);
            System.out.println("Server started on port " + this.PORT);
        }
        catch (IOException e) {
            e.getStackTrace();
            System.out.println("Erro ao iniciar o servidor");
        }
    }

    public Socket acceptPlayer() {
        try {
            return this.server.accept();
        } catch (IOException e) {
            System.out.println("Erro ao aceitar a conexão");
            return null;
        }
    }

    public void closeServer() {
        try {
            if(this.server != null) this.server.close();
            System.out.println("Servidor fechado");
        } catch (IOException e) {
            System.out.println("Erro ao fechar o servidor");
        }
    }
}