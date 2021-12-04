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

/**
 *
 * @author brais.fernandezvazqu
 */
public class ClienteHorario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();) {

            String peticion = "hora";
            byte[] mensaje = peticion.getBytes(); // Codifico String a bytes

            // CONSTRUYO EL DATAGRAMA A ENVIAR
            DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getLocalHost(), 5555);

            // ENVIO DATAGRAMA
            System.out.println("Enviando petición al servidor");
            socket.send(envio);
            System.out.println("Mensaje enviado: " + peticion);
            //Recibe respuesta
            System.out.println("Recibiendo respuesta.....");
            DatagramPacket hora = new DatagramPacket(new byte[1024], 1024);
            socket.receive(hora);
            //Escribe Mensaje
            System.out.println("Mensaje recibido: " + new String(hora.getData()));
            socket.close(); // cierro el socket

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
