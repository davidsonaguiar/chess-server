package com.fnrc.src.dto;

import com.fnrc.src.chess.ChessPiece;

import java.util.List;

public record DataChessMatch (
    String currentPlayer,
    ChessPiece[][] pieces,
    int turn,
    List<ChessPiece> captured
) {}
