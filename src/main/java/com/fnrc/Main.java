package com.fnrc;

import com.fnrc.src.exception.ReceiveMessageException;
import com.fnrc.src.Match;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) throws UnknownHostException {
        InetAddress host = args.length > 0 ? InetAddress.getByName(args[0]) : InetAddress.getByName("localhost");
        int port = args.length > 1 ? Integer.parseInt(args[1]) : PORT;

        try(ServerSocket server = new ServerSocket(port, 50, host)) {
            System.out.println("Servidor rodando: " + host.getHostName() + ":" + port);

            while(true) {
                new Match(server.accept(), server.accept()).start();
            }
        }
        catch (IOException exception) {
            System.out.println("Erro ao iniciar o servidor");
            exception.printStackTrace();
        }
        catch (ReceiveMessageException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

