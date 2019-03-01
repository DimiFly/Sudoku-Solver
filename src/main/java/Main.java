public class Main {

    public static void main(String[] args) {
        Creator c = new Creator(9, 3, 17);
        Solver s = new Solver(c.getSudoku(), 9, 3);
        s.solve(0, 1);
        s.printSudoku();
    }
}
