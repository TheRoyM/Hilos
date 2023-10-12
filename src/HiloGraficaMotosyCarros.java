import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HiloGraficaMotosyCarros extends JPanel implements Runnable {
    private static final int MAX_ELEMENTOS_GRAFICO = 100;
    private List<Integer> autosRegistrados = new ArrayList<>();
    private List<Integer> motosRegistradas = new ArrayList<>();

    @Override
    public void run() {
        do {
            // Actualizar los gráficos con nuevos valores en el hilo
            setAutosRegistrados(InterfazGrafica.registrosCarros);
            setMotosRegistradas(InterfazGrafica.registrosMotos);
            repaint();

            // Tiempo para mi hilo
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public void setAutosRegistrados(List<Integer> autosRegistrados) {
        this.autosRegistrados = autosRegistrados;
    }

    public void setMotosRegistradas(List<Integer> motosRegistradas) {
        this.motosRegistradas = motosRegistradas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja los ejes X e Y
        g.setColor(Color.BLACK);
        g.drawLine(60, 150, 160, 150); // Eje X
        g.drawLine(60, 150, 60, 50);  // Eje Y

        // Asegura que solo se muestren los últimos 100 elementos de las listas
        int inicioAutos = Math.max(autosRegistrados.size() - MAX_ELEMENTOS_GRAFICO, 0);
        int inicioMotos = Math.max(motosRegistradas.size() - MAX_ELEMENTOS_GRAFICO, 0);
        List<Integer> autosUltimos = autosRegistrados.subList(inicioAutos, autosRegistrados.size());
        List<Integer> motosUltimas = motosRegistradas.subList(inicioMotos, motosRegistradas.size());

        // Encuentra la máxima cantidad de autos y motos
        int maxAutos = autosUltimos.isEmpty() ? 0 : autosUltimos.stream().mapToInt(Integer::intValue).max().getAsInt();
        int maxMotos = motosUltimas.isEmpty() ? 0 : motosUltimas.stream().mapToInt(Integer::intValue).max().getAsInt();

        // altura máxima deseada (100 en este caso)
        int escalaAutos = maxAutos == 0 ? 1 : Math.min(100, 100 * 100 / maxAutos);
        int escalaMotos = maxMotos == 0 ? 1 : Math.min(100, 100 * 100 / maxMotos);


        g.setColor(Color.darkGray);
        for (int altura : autosRegistrados) {
            int altoBarraAutos = altura * escalaAutos / 100;
            g.fillRect(70, 150 - altoBarraAutos, 30, altoBarraAutos);
        }

        g.setColor(Color.LIGHT_GRAY);
        for (int altura : motosRegistradas) {
            int altoBarraMotos = altura * escalaMotos / 100;
            g.fillRect(130, 150 - altoBarraMotos, 30, altoBarraMotos);
        }

        //etiquetas
        g.setColor(Color.BLACK);
        g.drawString("Carros", 70, 170);
        g.drawString("Motos", 130, 170);
        g.drawString("100", 40, 60);
        g.drawString("50", 45, 100);
        g.drawString("0", 50, 150);
        g.drawString("Cantidad", 90, 10);
    }
}
