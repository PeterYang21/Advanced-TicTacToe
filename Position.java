/**
 * Position of each of the 9 entries on a 3 * 3 board
 */

public class Position {
    int row;
    int col;
    int ID; // map (row, column) position to a single number
    int backup_Value;

    Position() {
        row = -1;
        col = -1;
        ID = 0;
        backup_Value = 0;
    }
}
