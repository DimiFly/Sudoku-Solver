public class Main {

    public static void main(String[] args) {
        Creator c = new Creator(9, 3, 41);
        Solver s = new Solver(c.getSudoku(), c.getN(), c.getSqrtN());
    }
}
