
public class Queen {
    private int row;
    private final int column;

    public Queen(int row, int column) {
        super();
        this.row = row;
        this.column = column;
    }

    public void move() {
        if (row < Node.N - 1) {
            row++;
        } else
            this.row = 0;
    }

    //check whether this Queen can attack the given Queen (q)
    public boolean isConflict(Queen q) {
        // Check rows and columns
        if (row == q.getRow() || column == q.getColumn()) {
            return true;
        }
            // Check diagonals
        else {
            return Math.abs(column - q.getColumn()) == Math.abs(row - q.getRow());
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
