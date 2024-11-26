package com.fnrc;

import com.fnrc.src.Player;
import com.fnrc.src.Server;
import com.fnrc.src.chess.ChessMatch;
import com.fnrc.src.chess.ChessPiece;
import com.fnrc.src.chess.ChessPosition;
import com.fnrc.src.chess.Color;

import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Server server = new Server();

        Player playerOne = new Player(server.acceptPlayer(), Color.WHITE);
        Player playerTwo = new Player(server.acceptPlayer(), Color.BLACK);

        ChessMatch chessMatch = new ChessMatch(playerOne, playerTwo);
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                int currentTurn = chessMatch.getTurn();
                System.out.println("Turno: " + currentTurn);

                if(currentTurn % 2 == 0) {
                    playerOne.sendMessage("Esperando jogada do OPONENTE...");
                    playerTwo.sendMessage("Sua vez de jogar!");

                    playerTwo.sendMessage("Selecione a peça que deseja mover: ");
                    String sourceMessage = playerTwo.receiveMessage();
                    System.out.println("sourceMessage: " + sourceMessage);

                    char sourceColumn = sourceMessage.charAt(0);
                    System.out.println("sourceColumn: " + sourceColumn);
                    int sourceRow = Integer.parseInt(sourceMessage.substring(1));
                    System.out.println("sourceRow: " + sourceRow);

                    ChessPosition source = new ChessPosition(sourceColumn, sourceRow);

                    playerTwo.sendMessage("Selecione a posição que deseja mover a peça: ");
                    String targetMessage = playerTwo.receiveMessage();
                    System.out.println("targetMessage: " + targetMessage);

                    char targetColumn = targetMessage.charAt(0);
                    System.out.println("targetColumn: " + targetColumn);
                    int targetRow = Integer.parseInt(targetMessage.substring(1));
                    System.out.println("targetRow: " + targetRow);

                    ChessPosition target = new ChessPosition(targetColumn, targetRow);

                    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                    if (capturedPiece != null) captured.add(capturedPiece);
                } else {
                    playerTwo.sendMessage("Esperando jogada do OPONENTE...");
                    playerOne.sendMessage("Sua vez de jogar!");

                    playerOne.sendMessage("Selecione a peça que deseja mover: ");
                    String sourceMessage = playerOne.receiveMessage();

                    char sourceColumn = sourceMessage.charAt(0);
                    int sourceRow = Integer.parseInt(sourceMessage.substring(1));

                    ChessPosition source = new ChessPosition(sourceColumn, sourceRow);

                    playerOne.sendMessage("Selecione a posição que deseja mover a peça: ");
                    String targetMessage = playerOne.receiveMessage();

                    char targetColumn = targetMessage.charAt(0);
                    int targetRow = Integer.parseInt(targetMessage.substring(1));

                    ChessPosition target = new ChessPosition(targetColumn, targetRow);

                    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                    if (capturedPiece != null) captured.add(capturedPiece);
                }
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        server.closeServer();
    }
}

