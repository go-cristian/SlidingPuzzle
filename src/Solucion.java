import java.util.*;

public class Solucion {

    public static int[][] objetivo =
        new int[][] {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    public static Estado estadoObjetivo = new Estado(new Puzzle(objetivo));

    public static void main(String[] args) {

        int[][] datos = new int[][] {{11, 2, 4, 7}, {6, 0, 15, 8}, {10, 12, 3, 13}, {5, 9, 1, 14}};

        Estado inicial = new Estado(new Puzzle(datos));
        solucionar(inicial);
    }

    public static Estado solucionar(Estado inicial) {
        List<Estado> abiertos = new ArrayList<>();
        Set<Estado> cerrados = new TreeSet<>();

        abiertos.add(inicial);

        while (abiertos.size() != 0) {
            Estado estado = abiertos.get(0);
            //System.out.println("estado actual " + estado.toString());
            System.out.println(estado.getValor());

            if (estado.equals(estadoObjetivo)) {
                System.out.println("hay solucion");
                System.out.println(estado.getPath().toString());
                return estado;
            } else {
                List<Estado> estados = estado.generarEstados();
                //System.out.println("no hay solucion, generando " + estados.size() + " estados");
                for (Estado estadoGenerado : estados) {
                    if (!abiertos.contains(estadoGenerado) && !cerrados.contains(estadoGenerado)) {
                        abiertos.add(estadoGenerado);
                        //System.out.println("estado agregado " + estadoGenerado.toString());
                    } else {
                        //System.out.println("estado no agregado " + estadoGenerado.toString());
                    }
                }
            }
            cerrados.add(estado);
            //System.out.println("estado cerrado " + estado.toString());
            abiertos.remove(estado);
            Collections.sort(abiertos, new Comparator<Estado>() {
                @Override public int compare(Estado o1, Estado o2) {
                    return ((Integer) o1.getValor()).compareTo(o2.getValor());
                }
            });
            //System.out.println("aun existen " + abiertos.size() + " estados disponibles");
        }
        return null;
    }
}
