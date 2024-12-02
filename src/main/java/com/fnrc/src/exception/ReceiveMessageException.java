package com.fnrc.src.exception;

import com.fnrc.src.Color;

public class ReceiveMessageException extends StandardException {
    public ReceiveMessageException(Color color) {
        super("Erro ao receber mensagem: " + color);
    }
}
