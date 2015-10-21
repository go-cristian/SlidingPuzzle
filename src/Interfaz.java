import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Queue;

public class Interfaz extends JFrame {

    private Solucion solucion;
    private JPanel panel;
    private JButton botonCargar;
    private JLabel movimientos;

    private final ActionListener botonCargarListener = new ActionListener() {

        @Override public void actionPerformed(ActionEvent e) {
            try {
                botonCargar.setVisible(false);
                movimientos.setVisible(true);
                String ruta = mostrarSeleccionArchivo();
                String entrada = cargarArchivo(ruta);
                Estado estadoInicial = Estado.con(entrada);
                Estado solucion = Solucion.solucionar(estadoInicial);
                Queue<Estado.DIRECCION> path = solucion.getPath();
                String rutas = "";
                while (path.size() > 0) {
                    rutas += (path.poll().toString()) + "\n";
                }
                movimientos.setText(rutas);
                repaint();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "Cargue un archivo");
            }
        }
    };

    public Interfaz() {
        setTitle("Saltos Caballo");
        getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 750, 800);
        panel.setBackground(Color.lightGray);
        getContentPane().add(panel);
        botonCargar = new JButton("Buscar");
        botonCargar.setBounds(250, 400, 90, 40);
        botonCargar.addActionListener(botonCargarListener);
        botonCargar.setForeground(Color.blue);

        movimientos = new JLabel();
        movimientos.setVisible(false);
        movimientos.setBounds(0, 0, 620, 500);

        panel.add(botonCargar);
        panel.add(movimientos);


        setSize(620, 500);
        setLocationRelativeTo(this.getParent());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        validate();
        repaint();
        setVisible(true);
    }

    private String mostrarSeleccionArchivo() {
        JFileChooser archivo = new JFileChooser();
        archivo.setDialogTitle("Abrir Archivo: ");
        archivo.setMultiSelectionEnabled(false);
        int leer = archivo.showOpenDialog(null);
        if (leer == JFileChooser.APPROVE_OPTION) {
            return archivo.getSelectedFile().getAbsolutePath();
        } else {
            throw new IllegalStateException();
        }

    }

    public String cargarArchivo(String ruta) throws IOException {
        String resultado = "";
        FileInputStream fileInputStream = new FileInputStream(ruta);
        int bytes = fileInputStream.read();
        while (bytes != -1) {
            resultado += (char) bytes;
            bytes = fileInputStream.read();
        }
        fileInputStream.close();
        return resultado;
    }

}
