package com.fnrc.src.chess;

import com.fnrc.src.Player;

import java.util.InputMismatchException;

public class ChessMessage {
    public static ChessPosition readChessPositon(Player player) {
        try {
            String sourceMessage = player.receiveMessage();

            char sourceColumn = sourceMessage.charAt(0);
            int sourceRow = Integer.parseInt(sourceMessage.substring(1));

            return new ChessPosition(sourceColumn, sourceRow);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Erro na posição. Valores validos são de a1 até h8.");
        }
    }
}
