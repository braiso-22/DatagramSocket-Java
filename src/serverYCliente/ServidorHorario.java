/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverYCliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brais.fernandezvazqu
 */
public class ServidorHorario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        InetSocketAddress addr;

        System.out.println("Iniciando servidor de horario");
        try {
            addr = new InetSocketAddress(InetAddress.getLocalHost(), 5555);
            datagramSocket = new DatagramSocket(addr);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServidorHorario.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (datagramSocket != null) {
            System.out.println("Esperando Mensaje ..........");
            try {
                //Código donde creo el DatagramPacket
                byte[] buffer = new byte[1024];
                DatagramPacket datagrama1 = new DatagramPacket(buffer, buffer.length);

                //datagramSocket.receive (datagrama1);
                datagramSocket.receive(datagrama1);
                String recibido = new String(datagrama1.getData());

                //int clientPort = datagrama1.getPort();
                int clientPort = datagrama1.getPort();
                InetAddress clientIp = datagrama1.getAddress();
                System.out.println("Mensaje recibido desde el puerto: " + clientPort);
                System.out.println(recibido);
                // Para obtener la fecha y la hora utilizar la clase Date
                //Date(System.currentTimeMillis())
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String hora = LocalDateTime.now().format(formatter).toString();
                
                byte[] mensaje = hora.getBytes();

                //generar mensaje de hora
                DatagramPacket envio;
                String contenido = "Contenido mensaje: ";
                if (recibido.contains("hora")) {
                    envio = new DatagramPacket(mensaje, mensaje.length, clientIp, clientPort);
                    contenido += "hora";
                } else {
                    envio = new DatagramPacket(mensaje, mensaje.length, clientIp, clientPort);
                    contenido += "error";
                }
                System.out.println(contenido);

                // ENVIO DATAGRAMA
                System.out.println("Mensaje enviado!");
                datagramSocket.send(envio);
                

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
