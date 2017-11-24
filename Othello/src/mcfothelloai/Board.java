package mcfothelloai;

import java.util.ArrayList;

public class Board {

    int[][] board;
    int happy;
    
    Board() {
        this.board = new int[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (x < 10) {
                    if (y == 0 || y == 9) {
                        board[x][y] = -2;
                    }
                } else {
                    board[x][y] = 0;
                }
                if (x == 0 || x == 9) {
                    board[x][y] = -2;
                }
            }
        }//Initialize the board

        board[4][4] = 1;
        board[5][5] = 1;
        board[4][5] = -1;
        board[5][4] = -1;
    }

    public void BoardDraw() {
        for (int x = 0; x < 10; x++) {
            System.out.print("C |");
            for (int y = 0; y < 10; y++) {
                switch (board[x][y]) {
                    case Game.BLACK:
                        System.out.print("B");
                        break;
                    case Game.BORDER:
                        System.out.print("X");
                        break;
                    case Game.EMPTY:
                        System.out.print("0");
                        break;
                    case Game.WHITE:
                        System.out.print("W");
                        break;
                }
                System.out.print("|");
            }
            System.out.println();
        }//Print the initial board state
        System.out.println();
    }//constructor 

    public Board(Board board){
        for (int r = 1; r <= 9; r++) {
            for (int c = 1; c <= 9; c++) {
                this.board[r][c] = board.board[r][c];
            }
        }
    }//AlphaBeta constructor
    
    public void FindValidMoves(int color, ArrayList<Move> movelist) {
        int r;
        int c;
        int i = 0;
        int opp = -color;
        for (r = 1; r <= 9; r++) {
            for (c = 1; c <= 9; c++) {
                if (board[r][c] == Game.EMPTY) {
                    //System.out.println("found empty space " + r + " " + c);
                    for (int dR = -1; dR <= 1; dR++) {
                        for (int dC = -1; dC <= 1; dC++) {
                            if (board[r + dR][c + dC] == opp) {
                                int OppPiece = 1;//Possibly 2
                                //System.out.println("test " + r + " " + c + " " + dR + " " + dC);
                                //System.out.println((r+OppPiece*dR) + " " + (c+OppPiece*dC));
                                while (board[r + OppPiece * dR][c + OppPiece * dC] == opp) {
                                    //board[r][c] = board[r - dR][c - dC];
                                    OppPiece++;
                                    //System.out.println("looping");
                                }
                                //System.out.println((r+OppPiece*dR) + " " + (c+OppPiece*dC));
                                if (board[r + OppPiece * dR][c + OppPiece * dC] == color) {
                                    //System.out.println("add to valid move");
                                    Move valid = new Move(r, c);
                                    if (!movelist.contains(valid)) {
                                        movelist.add(valid);
                                    }
                                    /*whatever makes this a valid move*/
                                } else {
                                    /* not a valid move*/
                                }
                            }
                        }
                    }//Checks all of the direction wheel
                }
            }
        }
    }//Finds the valid moves

    /**
     *
     * @param player -- player for which moves are generated
     * @return a list of moves for player
     */
    public ArrayList<Move> generateMoves(int player) {
        ArrayList<Move> moveList = new ArrayList<>();
        FindValidMoves(player, moveList);
        return moveList;
    }

    public boolean testMove(int player, Move move) {
        ArrayList<Move> moveList = generateMoves(player);
        return (moveList.indexOf(move) != -1);
    }

    /**
     * applies given move on board for given player
     *
     * @param player
     * @param move
     */
    public boolean applyMove(int player, Move move) {
        ArrayList<Move> moveList = generateMoves(player);
        if (moveList.indexOf(move) != -1) {
            int opp = -player;
            board[move.getR()][move.getC()] = player;
            for (int dR = -1; dR <= 1; dR++) {
                for (int dC = -1; dC <= 1; dC++) {
                    if (board[move.getR() + dR][move.getC() + dC] == opp) {
                        moveList.removeAll(moveList);
                        moveList.add(new Move(move.getR() + dR, move.getC() + dC));
                        int OppPiece = 1;//Possibly 2
                        //System.out.println("test " + r + " " + c + " " + dR + " " + dC);
                        //System.out.println((r+OppPiece*dR) + " " + (c+OppPiece*dC));
                        while (board[move.getR() + OppPiece * dR][move.getC() + OppPiece * dC] == opp) {
                            //board[move.getR()][move.getC()] = board[move.getR() - dR][move.getC() - dC];
                            OppPiece++;

                            moveList.add(new Move(move.getR() + (dR * OppPiece), move.getC() + (dC * OppPiece)));
                            //System.out.println("looping");
                        }
                        //System.out.println((r+OppPiece*dR) + " " + (c+OppPiece*dC));
                        if (board[move.getR() + OppPiece * dR][move.getC() + OppPiece * dC] == player) {
                            //System.out.println("add to valid move");
                            while (!moveList.isEmpty()) {
                                Move flippedTile = moveList.remove(0);
                                board[flippedTile.getR()][flippedTile.getC()] = player;
                            }
                            /*whatever makes this a valid move*/
                        } else {
                            /* not a valid move*/
                        }
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }//iterate through, count the pieces

    /**
     *
     * @return true if game is over false otherwise
     */
    public boolean gameOver() {
        ArrayList<Move> moveListB = generateMoves(-1);
        ArrayList<Move> moveListW = generateMoves(1);
        return moveListB.isEmpty() && moveListW.isEmpty();
    }

    public int countBlack(){
        int blackPiece = 0;
        for(int r = 1; r <= 9; r++){
            for(int c = 1; c <= 9; c++){
                if(board[r][c] == -1){
                    blackPiece++;
                }
            }
        }
        return blackPiece;
    }
    
    public int moveVal(int row, int column){
        int value[][] =  {{100, 1, 20, 20, 20, 20, 1, 100},
                            {1, 1, 5, 5, 5, 5, 1, 1}, 
                            {20, 5, 2, 2, 2, 2, 5, 20}, 
                            {20, 5, 2, 0, 0, 2, 5, 20}, 
                            {20, 5, 2, 0, 0, 2, 5, 20}, 
                            {20, 5, 2, 2, 2, 2, 5, 20}, 
                            {1, 1, 5, 5, 5, 5, 1, 1}, 
                            {100, 1, 20, 20, 20, 20, 1, 100}};
        return value[row][column];
    }//Value of positions on the board
    
    public int evaluate(){
        ArrayList<Move> moveList = new ArrayList<>();
        int Bestscore = 0;
        int Bestcolumn = 0;
        int Bestrow = 0;
        for(int i=0; i<moveList.size(); i++){
            Move position = moveList.get(i);
            int r = position.r;
            int c = position.c;
            if(moveVal(r, c)> Bestscore){
                Bestcolumn = c;
                Bestrow = r;
            }
        }
        return this.moveVal(Bestrow, Bestcolumn);
    }
    
}//Board class
