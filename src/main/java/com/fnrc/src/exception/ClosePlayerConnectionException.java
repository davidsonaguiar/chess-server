package com.fnrc.src.exception;

public class ClosePlayerConnectionException extends StandardException{
    public ClosePlayerConnectionException(String host, int port) {
        super("Erro ao fechar a conexão com: " + host + ":" + port);
    }
}
