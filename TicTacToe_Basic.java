
//Basic TicTacToe file
import java.util.Scanner;

public class TicTacToe_Basic {


    private static char USER = ' ';
    private static char COMPUTER = ' ';
    
    public static void main(String[] args) {

        char[][] board = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }

        int ID = 1;
        Position[] positionArray = new Position[10]; //1 to 9
        for (int i = 1; i < 10; i++) {
            positionArray[i] = new Position();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                positionArray[ID].row = i;
                positionArray[ID].col = j;
                positionArray[ID].ID = ID;
                ID++;
            }
        }

        Scanner scan = new Scanner(System.in);

        do{
            System.out.print("Choose a character to play, X or O (Enter x for X, or o for O; Enter x to go first): ");
            USER = scan.next().charAt(0);
        }while(USER != 'x' && USER != 'o'); //input must be lower case x or o

        boolean computerFirst = false;

        //decide the player that moves first, and change all input characters to upper case
        if (USER == 'x') {
            USER = 'X';
            COMPUTER = 'O';
        }
        else {
            USER = 'O';
            COMPUTER = 'X';
            computerFirst = true;
        }

        while (checkSpace(board)) {

            if(computerFirst){
                Position move_Computer = minimax_Decision(board); //find the best move for computer
                board[move_Computer.row][move_Computer.col] = COMPUTER;
                computerFirst = false;
                printBoard(board); /*print the board*/
            }

            int input;

            do{
                System.out.print("Please tell me your move: "); //prompt the user for input
                input = scan.nextInt();

            }while (input < 1 || input > 9 ); //check the move is legal

            Position move = new Position();

            for (int i = 1; i < 10; i++) { //get the row and column of the user input
                if (positionArray[i].ID == input) {
                    move.row = positionArray[i].row;
                    move.col = positionArray[i].col;
                    break;
                }
            }

            if(board[move.row][move.col] == ' '){
                board[move.row][move.col] = USER;

                if (checkSpace(board)) {
                    Position move_Computer = minimax_Decision(board); //find the best move for computer
                    board[move_Computer.row][move_Computer.col] = COMPUTER;
                }

                printBoard(board); /*print the board*/

                int winner_indicator = getResult(board);
                if (winner_indicator == 1) {
                    System.out.println("You win.");
                    break;
                }

                if (winner_indicator == -1) {
                    System.out.println("The computer wins.");
                    break;
                }

                if (!checkSpace(board)) { //board is full
                    System.out.println("It is a draw. Nobody wins.");
                    break;
                }
            }

            else{
                System.out.println("This position has been occupied. Try again.");
            }

        }

        //System.out.println(USER); //test line
        /*System.out.println(move[5].col);

        for(int i = 1;i < 10;i++) {
            if(move[i].row == 2 && move[i].col == 0){  //search for value //test line
                System.out.println(move[i].ID);
            }
        }*/

    }

    static int maxValue(char[][] board){

        int finalStateVal = getResult(board);
        if(finalStateVal == 1){
            return 1;
        }

        if(finalStateVal == -1){
            return -1;
        }

        if(!checkSpace(board)) { //board is full
            return 0;
        }

        int bestResult = (int)Double.NEGATIVE_INFINITY;
        //traverse the board
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(board[i][j] == ' '){
                    board[i][j] = USER;
                    bestResult = Math.max(bestResult,minValue(board));
                    board[i][j] = ' ';
                }
            }
        }
        return bestResult;

    }

    static int minValue(char[][] board){

        int finalStateVal = getResult(board);
        if(finalStateVal == 1){
            return 1;
        }

        if(finalStateVal == -1){
            return -1;
        }

        if(!checkSpace(board)) { //board is full
            return 0;
        }

        int bestResult = (int)Double.POSITIVE_INFINITY;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(board[i][j] == ' '){
                    board[i][j] = COMPUTER;
                    bestResult = Math.min(bestResult,maxValue(board));
                    board[i][j] = ' ';
                }
            }
        }
        return bestResult;
    }


    static Position minimax_Decision(char[][] board) {
        int bestResult_Computer = (int) Double.POSITIVE_INFINITY;
        Position bestMove = new Position();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') { //move is accepted only when there is space in the target position

                    board[i][j] = COMPUTER;
                    int userResult = maxValue(board);

                    if(userResult < bestResult_Computer){ //find minimum value of the the maximum values generated from user's results
                        bestResult_Computer = userResult;
                        bestMove.row = i;
                        bestMove.col = j;
                    }
                        board[i][j] = ' ';
                    }
                }
            }

        return bestMove;
    }

    static boolean checkSpace(char[][] board) { //check if there is still space on the board

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return true;
            }
        }

        return false;
    }

    static int getResult(char[][] board) {

        for (int row = 0; row < 3; row++) {
            if ((board[row][0] == board[row][1]) && (board[row][1] == board[row][2])) {
                if (board[row][0] == USER)
                    return 1;
                else if (board[row][0] == COMPUTER) {
                    return -1;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            if ((board[0][col] == board[1][col]) && (board[1][col] == board[2][col])) {
                if (board[0][col] == USER) {
                    return 1;
                } else if (board[0][col] == COMPUTER) {
                    return -1;
                }
            }
        }

        if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2])) {
            if (board[0][0] == USER) {
                return 1;
            } else if (board[0][0] == COMPUTER) {
                return -1;
            }
        }

        if ((board[0][2] == board[1][1]) && (board[1][1] == board[2][0])) {
            if (board[0][2] == USER) {
                return 1;
            } else if (board[0][2] == COMPUTER) {
                return -1;
            }
        }

        return 0; // 0 indicates no one wins and the game continues
    }

    static void printBoard(char[][] board){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.print("|");
            System.out.println();
            System.out.println("-------");
        }

    }

}
