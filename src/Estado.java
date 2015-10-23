import java.util.*;

public class Estado implements Comparable<Estado>, Comparator<Estado> {

	public static Estado con(String entrada) {
		String[] lineas = entrada.split("\\r?\\n");
		int[][] matriz = new int[Puzzle.SIZE][Puzzle.SIZE];
		for (int i = 0; i < lineas.length; i++) {
			String[] datos = lineas[i].split("-");
			for (int j = 0; j < datos.length; j++) {
				matriz[i][j] = Integer.parseInt(datos[j]);
			}
		}
		return new Estado(new Puzzle(matriz));
	}

	public enum DIRECCION {
		ARRIBA, ABAJO, DERECHA, IZQUIERDA,
	}

	private final Puzzle puzzle;
	private final int valor;
	private boolean ordenadaL = false;

	private Queue<DIRECCION> path = new LinkedList<DIRECCION>();

	public Estado(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.valor = calcluarValor();
	}

	private int calcluarValor() {
		int valor = 0;
		for (int i = 0; i < puzzle.getPuzzle().length; i++) {
			for (int j = 0; j < puzzle.getPuzzle()[i].length; j++) {
				int valorCelda = puzzle.getPuzzle()[i][j] - 1;

				int penalizacion = 1;
				if (puzzle.getPuzzle()[0][0] != 1) {
					penalizacion = 15;
				}
				if (puzzle.getPuzzle()[0][1] != 2) {
					penalizacion = 14;
				}
				if (puzzle.getPuzzle()[0][2] != 3) {
					penalizacion = 13;
				}
				if (puzzle.getPuzzle()[0][3] != 4) {
					penalizacion = 12;
				}
				if (puzzle.getPuzzle()[1][0] != 5) {
					penalizacion = 11;
				}
				if (puzzle.getPuzzle()[2][0] != 9) {
					penalizacion = 10;
				}
				if (puzzle.getPuzzle()[3][0] != 13) {
					penalizacion = 9;
				} 
				
				if(penalizacion==1){
					ordenadaL = true;
				}

				int filaEsperada = (valorCelda / 4);
				int columnaEsperada = valorCelda - (filaEsperada * Puzzle.SIZE);

				valor += (Math.abs(filaEsperada - i) + Math.abs(columnaEsperada
						- j))
						* penalizacion;
			}
		}
		return valor;
	}

	public List<Estado> generarEstados() {
		List<Estado> estados = new ArrayList<Estado>();
		Punto cero = this.puzzle.getZero();

		if (cero.y - 1 >= 0)
			estados.add(Estado.con(this, DIRECCION.DERECHA));
		if (cero.x - 1 >= 0)
			estados.add(Estado.con(this, DIRECCION.ABAJO));
		if (cero.x + 1 < Puzzle.SIZE)
			estados.add(Estado.con(this, DIRECCION.ARRIBA));
		if (cero.y + 1 < Puzzle.SIZE)
			estados.add(Estado.con(this, DIRECCION.IZQUIERDA));

		return estados;
	}

	private static Estado con(Estado estado, DIRECCION direccion) {
		int[][] puzzleCopia = new int[estado.getPuzzle().getPuzzle().length][];
		for (int i = 0; i < estado.getPuzzle().getPuzzle().length; i++) {
			puzzleCopia[i] = estado.getPuzzle().getPuzzle()[i].clone();
		}

		Punto cero = estado.getPuzzle().getZero();

		Punto puntoSwap;
		switch (direccion) {
		case DERECHA:
			puntoSwap = new Punto(cero.x, cero.y - 1);
			break;
		case IZQUIERDA:
			puntoSwap = new Punto(cero.x, cero.y + 1);
			break;
		case ABAJO:
			puntoSwap = new Punto(cero.x - 1, cero.y);
			break;
		case ARRIBA:
			puntoSwap = new Punto(cero.x + 1, cero.y);
			break;
		default:
			throw new IllegalStateException(
					"no es posible realizar este movimiento");
		}
		puzzleCopia[cero.x][cero.y] = puzzleCopia[puntoSwap.x][puntoSwap.y];
		puzzleCopia[puntoSwap.x][puntoSwap.y] = 0;
		Estado nuevoEstado = new Estado(new Puzzle(puzzleCopia));
		nuevoEstado.addToPath(estado.getPath());
		nuevoEstado.addToPath(direccion);
		return nuevoEstado;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public int getValor() {
		return valor;
	}

	@Override
	public int compareTo(Estado o) {
		return toString().compareTo(o.toString());
	}

	@Override
	public int compare(Estado o1, Estado o2) {
		return o1.toString().compareTo(o2.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Estado) {
			int[][] puzzle = getPuzzle().getPuzzle();
			int[][] puzzleComparador = ((Estado) obj).getPuzzle().getPuzzle();
			for (int i = 0; i < puzzle.length; i++) {
				for (int j = 0; j < puzzle[i].length; j++) {
					if (puzzle[i][j] != puzzleComparador[i][j]) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String resultado = "";
		int[][] puzzle = getPuzzle().getPuzzle();
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				resultado += puzzle[i][j] + ",";
			}
		}
		return resultado;
	}

	public Queue<DIRECCION> getPath() {
		return path;
	}

	public void addToPath(DIRECCION direccion) {
		path.add(direccion);
	}

	private void addToPath(Queue<DIRECCION> path) {
		this.path.addAll(path);
	}
	
	public boolean getLOrdenada(){
		return this.ordenadaL;
	}

	public void imprimir() {
		int[][] puzzle = getPuzzle().getPuzzle();
		for (int i = 0; i < puzzle.length; i++) {
			String resultado = "";
			for (int j = 0; j < puzzle[i].length; j++) {
				resultado += puzzle[i][j] + ",";
			}
			System.out.println(resultado);
		}
	}
}
