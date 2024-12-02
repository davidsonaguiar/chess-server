package com.fnrc.src;

import com.fnrc.src.exception.ClosePlayerConnectionException;
import com.fnrc.src.exception.CreatePlayerException;
import com.fnrc.src.exception.ReceiveMessageException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {

    private final Socket socket;

    private final Color color;

    private final PrintWriter output;

    private final BufferedReader input;

    public Player(Socket socket, Color color) throws CreatePlayerException {
        this.socket = socket;
        this.color = color;

        try {
            this.output = new PrintWriter(socket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new CreatePlayerException();
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void sendMessage(String message) {
        this.output.println(message);
    }

    public String receiveMessage() throws ReceiveMessageException {
        try {
            return this.input.readLine();
        } catch (Exception e) {
            throw new ReceiveMessageException(this.color);
        }
    }

    public Boolean isConnected() {
        return this.socket.isConnected();
    }

    public void close() throws ClosePlayerConnectionException {
        try {
            this.socket.close();
        } catch (Exception e) {
            throw new ClosePlayerConnectionException(this.socket.getInetAddress().getHostName(), this.socket.getPort());
        }
    }
}
