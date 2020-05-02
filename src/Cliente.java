import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Cliente extends Thread {
    
    Socket cliente;
    BufferedReader entrada;
    PrintWriter salida;
    int puerto;
    String ip;
    JTextField texto2;
    JLabel estado;


    public Cliente(JTextField texto2,String ip,int puerto,JLabel estado) {
        this.puerto = puerto;
        this.texto2 = texto2;
        this.ip = ip;
        this.estado = estado;
        start();
    }
    
    public void enviar(String mensaje) {
        salida.println(mensaje);
        salida.flush();
        texto2.setText(texto2.getText());
    }
    
    public void conectar() {
        try {
            cliente = new Socket(ip, puerto);

            //flujo de entrada
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            //flujo salida
            salida = new PrintWriter(cliente.getOutputStream());
            estado.setVisible(true);
            estado.setForeground(Color.green);
            estado.setText("Conectado al servidor " + ip + ":" + puerto + "\n");
            salida.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void run() {
        try {
            conectar();
            String linea;
            while (true) {
                while ((linea = entrada.readLine()) != null) {
                    texto2.setText(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
