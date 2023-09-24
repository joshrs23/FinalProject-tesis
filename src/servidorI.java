
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JDialog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joserafael
 */
public class servidorI {
    private ventana mensajes;
    int puerto = 9876;//puerto
    int maxCone = 4;//#max de nodos
    hiloCliente clienteServ;
   
    private LinkedList<Socket> clientes = new LinkedList<Socket>();//aqui se guardara los sockets conectados
    public servidorI()
    {
        mensajes= new ventana();
        mensajes.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        mensajes.setVisible(true);
        mensajes.PasarServ(this);
        //////////////////////
        clienteServ= new hiloCliente();
        Thread hilo = new Thread(clienteServ);
            
        try {
             ServerSocket servidor = new ServerSocket(puerto,maxCone);//Se crea socket con puerto 9876
            //Inicio del hilo para escuchar lo que manden al servidor
            while(true){
                System.out.println("Servicio/cliente escuchando....");//icono verde
                Socket cliente = servidor.accept();//cliente aceptado
                clientes.add(cliente);
                clienteServ.enviarMsg("Cliente conectado # "+clientes.size());
                Runnable  run = new servidor(cliente,clientes,mensajes);//iniciamos hilo para escuchar cliente
               
                Thread hiloServidor = new Thread(run);
                hiloServidor.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void enviarMensajeServidor(String paquete)
    {
        clienteServ.enviarMsg(paquete);//enviamos el mensaje------aqui cuando ya se mande todos los datos
    }
        //Main servidor y inicio programa
   public static void main(String[] args) {
        servidorI servidor= new servidorI();
        
    }
}

