
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joserafael
 */
public class servidor extends Thread{
    private Socket sc;
    private DataInputStream entradaServidor;
    private DataOutputStream salidaServidor;
    private ventana mensajes;
    private servidorP servidorP;
    private int tipo,client=0;
    private VentanaSP vent;
    //Lista clientes
    private LinkedList<Socket> cliente = new LinkedList<Socket>();
    
    //Constructor que recibe el socket que atendera el hilo y la lista de usuarios conectados
    public servidor(Socket soc,LinkedList users, ventana vent){
        sc = soc;
        cliente = users;
        this.mensajes=vent;
        tipo=0;
    }
     public servidor(Socket soc,LinkedList users,servidorP serv, VentanaSP ventSP){
        sc = soc;
        cliente = users;
        tipo=1;
        servidorP=serv;
        vent=ventSP;
    }
    
    @Override
    public void run() {
        try {
            
            //Inicializamos los canales de comunicacion y mandamos un mensaje de bienvenida
            entradaServidor = new DataInputStream(sc.getInputStream());
            salidaServidor = new DataOutputStream(sc.getOutputStream());
            salidaServidor.writeUTF("Cliente aceptado # "+cliente.size());
            
            if(tipo==0){
              mensajes.cliente();  
            }
            
            //hilo del servidor para recibir paquetes de las clases hiloCliente

            while(true){
               String recibido  = entradaServidor.readUTF(); 
               
               if(tipo==0){
                 mensajes.mensajes(recibido);  
               }
               else
               {
                   if(recibido.startsWith("Cliente")){
                       client=client+1;
                       vent.ponerTexto("Cliente aceptado # "+client);
            
                   }
                   else{
                    servidorP.decodificar(recibido, client);//tocara pedir lo que mando el cliente aqui aprovechando que tengo la varialle de servidorP  y mandarlo en el else de abajo
                   //aqui envia mensaje de servidor principal 
                   }
               }
            }
            
            
        } catch (IOException e) {
            for (int i = 0; i < cliente.size(); i++) {
                if(cliente.get(i) == sc){
                    cliente.remove(i);
                    break;
                } 
            }
        }
    }
    


}

