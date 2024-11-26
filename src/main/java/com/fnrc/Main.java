package com.fnrc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnrc.src.Player;
import com.fnrc.src.Server;
import com.fnrc.src.chess.*;
import com.fnrc.src.dto.DataChessMatch;

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
                turnMessage(chessMatch, captured);
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        server.closeServer();
    }

    public static void turnMessage(ChessMatch chessMatch, List<ChessPiece> captured) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataChessMatch data = new DataChessMatch(chessMatch.getCurrentPlayer().getColor().toString(), chessMatch.getPieces(), chessMatch.getTurn(), captured);
            String json = mapper.writeValueAsString(data);
            String dataJson = "TURN " + json;

            chessMatch.sendMessageForAll(dataJson);
            chessMatch.getNextPlayer().sendMessage("Esperando jogada do OPONENTE...");
            chessMatch.getCurrentPlayer().sendMessage("Sua vez de jogar!");

            chessMatch.getCurrentPlayer().sendMessage("Selecione a peça que deseja mover: ");
            ChessPosition source = ChessMessage.readChessPositon(chessMatch.getCurrentPlayer());

            chessMatch.getCurrentPlayer().sendMessage("Informe a posição alvo: ");
            ChessPosition target = ChessMessage.readChessPositon(chessMatch.getCurrentPlayer());

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

            if (capturedPiece != null) captured.add(capturedPiece);

            if (chessMatch.getPromoted() != null) {
                chessMatch.getCurrentPlayer().sendMessage("Selecione uma peça para promoção (B/N/R/Q): ");
                String type = chessMatch.getCurrentPlayer().receiveMessage().toUpperCase();
                while (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
                    System.out.print("Valor invalido! Selecione uma peça para promoção (B/N/R/Q): ");
                    type = chessMatch.getCurrentPlayer().receiveMessage().toUpperCase();
                }
                chessMatch.replacePromotedPiece(type);
            }
        }
        catch (RuntimeException e) {
            chessMatch.getCurrentPlayer().sendMessage(e.getMessage());
        }
        catch (JsonProcessingException e) {
            chessMatch.sendMessageForAll(e.getMessage());
        }
    }
}

