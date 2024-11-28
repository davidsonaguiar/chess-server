package com.fnrc;


class Main {
    public static void main(String[] args) {
        Server server = new Server();

        Player playerOne = new Player(server.acceptPlayer(), Color.WHITE);
        Player playerTwo = new Player(server.acceptPlayer(), Color.BLACK);

        playerOne.setOpponent(playerTwo);
        playerTwo.setOpponent(playerOne);

        playerOne.sendMessage("match");
        playerTwo.sendMessage("match");

        playerOne.startListening();
        playerTwo.startListening();

        server.closeServer();
    }
}

