/**
 * Position of each of the 9 boards on a 3 * 3 whole display
 */
public class singleBoard {
    char[][] board;
    int boardID;
    int row_Board;
    int col_Board;

    singleBoard(){
        board = new char[3][3];
        boardID = 0;
        row_Board = -1;
        col_Board = -1;

        for (int i = 0; i < 3; i++) { //initialize all entries in the board as empty chars
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

}
