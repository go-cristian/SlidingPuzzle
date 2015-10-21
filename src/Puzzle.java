public class Puzzle {

    public static int SIZE = 4;

    private final int[][] puzzle;
    private final Punto zero;

    public Puzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        zero = encontrarCero(puzzle);
    }

    private Punto encontrarCero(int[][] puzzle) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] == 0) {
                    return new Punto(i, j);
                }
            }
        }
        throw new IllegalStateException("No se encontro un cero");
    }

    public Punto getZero() {
        return zero;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }
}
