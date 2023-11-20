import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GA_NQueenAlgo {
    public static final int POP_SIZE = 100;//Population size
    public static final double MUTATION_RATE = 0.03;
    public static final int MAX_ITERATIONS = 1000;
    List<Node> population = new ArrayList<>();
    Random rd = new Random();

    // initialize the individuals of the population
    public void initPopulation() {
        for (int i = 0; i < POP_SIZE; i++) {
            Node ni = new Node();
            ni.generateBoard();
            population.add(ni);
        }
    }

    public Node execute() {
        initPopulation();
        int index = 0;
        while (index++ < MAX_ITERATIONS) {
            List<Node> newPopulation = new ArrayList<>();
            for (int i = 0; i < POP_SIZE; i++) {
                Node x = getParentByTournamentSelection(5);
                Node y = getParentByTournamentSelection(5);
                Node child = reproduce(x, y);
                if (rd.nextDouble() < MUTATION_RATE) {
                    mutate(child);
                }
                System.out.println("index: " + index + ": " + child.getH());
                if (child.getH() == 0) {
                    return child;
                }
                newPopulation.add(child);
            }
            population = newPopulation;
        }
        Collections.sort(population);
        return population.get(0);
    }

    // Mutate an individual by selecting a random Queen and
    //move it to a random row.
    public void mutate(Node node) {
        int rdIndexQueen = rd.nextInt(Node.N);
        int rdRowQueen = rd.nextInt(Node.N);
        node.setRow(rdIndexQueen, rdRowQueen);
    }

    //Crossover x and y to reproduce a child
    public Node reproduce(Node x, Node y) {
        int crossover = rd.nextInt(1, Node.N - 1);
        Node child = new Node();
        child.generateBoard();
        for (int i = 0; i < crossover; i++) {
            child.setRow(i, x.getRow(i));
        }
        for (int i = crossover + 1; i < Node.N; i++) {
            child.setRow(i, child.getRow(i));
        }
        return child;
    }

    // Select K individuals from the population at random and
//select the best out of these to become a parent.
    public Node getParentByTournamentSelection(int amount) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < amount; i++) {
            int rdSelection = rd.nextInt(POP_SIZE);
            if (population.get(rdSelection).getH() < min) {
                min = population.get(rdSelection).getH();
                index = rdSelection;
            }
        }
        return population.get(index);
    }

    //Select a random parent from the population
    public Node getParentByRandomSelection() {
        return null;
    }

}
