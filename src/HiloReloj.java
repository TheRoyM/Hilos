import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HiloReloj extends Thread{
   static JLabel lblHora;

    public HiloReloj() {
        lblHora = InterfazGrafica.lblHora;
    }

    @Override
    public void run() {
        while (true) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String hora = sdf.format(calendar.getTime());


            lblHora.setText("Hora: " + hora);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
