import java.util.*;

public class Solucion {

	public static int[][] objetivo = new int[][] { { 1, 2, 3, 4 },
			{ 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	public static Estado estadoObjetivo = new Estado(new Puzzle(objetivo));

	public static void main(String[] args) {

		int[][] datos = new int[][] { { 9, 12, 5, 4 }, { 13, 0, 1, 7 },
				{ 2, 10, 6, 11 }, { 14, 8, 15, 3 } };

		Estado inicial = new Estado(new Puzzle(datos));
		solucionar(inicial);
	}

	public static Estado solucionar(Estado inicial) {
		List<Estado> abiertos = new ArrayList<Estado>();
		Set<Estado> cerrados = new TreeSet<Estado>();
		boolean ordenadaL = false;

		abiertos.add(inicial);

		while (abiertos.size() != 0) {
			Estado estado = abiertos.get(0);

			if (estado.equals(estadoObjetivo)) {
				System.out.println("hay solucion");
				estado.imprimir();
				System.out.println("numero de movimientos actuales "
						+ estado.getPath().size());
				System.out.println(estado.getPath().toString());
				return estado;
			} else {

				if (estado.getLOrdenada() && !ordenadaL) {
					estado.imprimir();
					System.out.println("numero de movimientos actuales "
							+ estado.getPath().size());
					ordenadaL = true;
				}
				List<Estado> estados = estado.generarEstados();
				for (Estado estadoGenerado : estados) {
					if (!abiertos.contains(estadoGenerado)
							&& !cerrados.contains(estadoGenerado)) {
						abiertos.add(estadoGenerado);
					}
				}
			}
			cerrados.add(estado);
			abiertos.remove(estado);
			Collections.sort(abiertos, new Comparator<Estado>() {
				@Override
				public int compare(Estado o1, Estado o2) {
					return ((Integer) o1.getValor()).compareTo(o2.getValor());
				}
			});
		}
		return null;
	}
}
