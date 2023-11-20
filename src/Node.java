import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Node implements Comparable<Node> {
    public static final int N = 8;
    private Queen[] state;

    public Node() {
        state = new Queen[N];
        generateBoard();
    }

    public Node(Queen[] state) {
        this.state = new Queen[N];
        for (int i = 0; i < state.length; i++) {
            this.state[i] = new Queen(state[i].getRow(), state[i].getColumn());
        }
    }

    public Node(Node n) {
        this.state = new Queen[N];
        for (int i = 0; i < N; i++) {
            Queen qi = n.state[i];
            this.state[i] = new Queen(qi.getRow(), qi.getColumn());
        }
    }

    public Queen[] getState() {
        return state;
    }

    // get the row of a queen at index i
    public int getRow(int i) {
        return state[i].getRow();
    }

    // set the row of a queen at index i
    public void setRow(int i, int row) {
        state[i].setRow(row);
    }

    public void generateBoard() {
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            state[i] = new Queen(random.nextInt(N), i);
        }
    }

    public int getH() {
        int heuristic = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                if (state[i].isConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    public List<Node> generateBetterCandidates() {
        List<Node> result = new ArrayList<>();

        // The currentState is assumed as the best board and temp board
        Node nextState = new Node(this.state);
        Node tmpState = new Node(this.state);
        int bestH = this.getH();
        int tempH;

        // Iterate each column
        for (int i = 0; i < N; i++) {
            tmpState = new Node(state);

            // Iterate each row
            for (int j = 0; j < N; j++) {
                // Get the heuristic
                tempH = tmpState.getH();
                // Check if temp board better than best board
                if (tempH < bestH) {
                    bestH = tempH;
                    nextState = new Node(tmpState);
                    result.add(nextState);
                }
                // Move the queen
                tmpState.state[i].move();// increase row by 1, if row=7, set row=0;
            }
        }
        return result;
    }

    public List<Node> generateAllCandidates() {
        List<Node> result = new ArrayList<>();
        // The currentState is assumed as the best board and temp board
        Node nextState = null;
        Node tmpState = null;

        // Iterate each column
        for (int i = 0; i < N; i++) {
            tmpState = new Node(state);

            // Iterate each row
            for (int j = 0; j < N; j++) {
                // Move the queen
                tmpState.state[i].move();// increase row by 1, if row=7, set row=0;
                nextState = new Node(tmpState);
                result.add(nextState);
            }
        }
        return result;
    }

    public Node getBestCandidate() {
        List<Node> re = this.generateAllCandidates();
        Collections.sort(re);
        return re.get(0);
    }

    public Node selectNextRandomCandidate() {
        Random rd = new Random();
        // select a random queen
        int index = rd.nextInt(N);
        Node result = new Node(this.state);
        result.state[index].setRow(rd.nextInt(N));
        return result;
    }

    public void displayBoard() {
        int[][] board = new int[N][N];
        // set queen position on the board
        for (int i = 0; i < N; i++) {
            board[state[i].getRow()][state[i].getColumn()] = 1;
        }
        // print board
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    System.out.print("Q" + " ");
                } else {
                    System.out.print("-" + " ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public int compareTo(Node o) {
        return (this.getH() - o.getH());
    }
}
