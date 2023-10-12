import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class InterfazGrafica extends JFrame implements ActionListener {
    static JLabel lblMotos, lblCarros, lblHora;
    static JTextField txtCarro;
    static JTextField txtMoto;
    static JButton btnOK;
    static HiloGraficaMotosyCarros graficos;
    static final List<Integer> registrosCarros = new ArrayList<>();
    static final List<Integer> registrosMotos = new ArrayList<>();
    public InterfazGrafica() {
        lblHora = new JLabel();
        lblHora.setBounds(200, 20, 150, 30);

        lblCarros = new JLabel("Carros");
        lblCarros.setBounds(20, 40, 120, 40);
        txtCarro = new JTextField();
        txtCarro.setBounds(20, 80, 140, 30);

        lblMotos = new JLabel("Motos");
        lblMotos.setBounds(20, 120, 120, 40);
        txtMoto = new JTextField();
        txtMoto.setBounds(20, 160, 140, 30);

        btnOK = new JButton("Ok");
        btnOK.setBounds(200, 80, 60, 30);
        btnOK.addActionListener(this);
        btnOK.setForeground(Color.BLACK);

        graficos = new HiloGraficaMotosyCarros();
        graficos.setBounds(20, 200, 400, 200); // Establece el tamaño y la posición del gráfico

        HiloReloj hiloReloj = new HiloReloj();
        hiloReloj.start();

        Thread hiloActualizacion = new Thread(graficos);
        hiloActualizacion.start();


        add(lblCarros);
        add(lblMotos);
        add(lblHora);
        add(txtCarro);
        add(txtMoto);
        add(btnOK);
        add(graficos); // Agrega el gráfico al panel principal

        setLayout(null);
        setSize(500, 600);
        setTitle("Hilos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(500, 250);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnOK)){
            try {
                int carro = Integer.parseInt(txtCarro.getText());
                int moto = Integer.parseInt(txtMoto.getText());

                synchronized (registrosCarros) {
                    registrosCarros.add(carro);
                }

                synchronized (registrosMotos) {
                    registrosMotos.add(moto);
                }

                txtCarro.setText("");
                txtMoto.setText("");

                // Actualizar los gráficos con nuevos valores
                graficos.setAutosRegistrados(registrosCarros);
                graficos.setMotosRegistradas(registrosMotos);

            } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingresa números válidos en los campos de texto.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazGrafica();
        });
    }

}
