
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
public class servidorP {
   // private ventana mensajes;
    int puerto = 9877;//puerto
    int maxCone = 4;//#max de nodos
    int noPaquete=1;
    protected String mensajeServidor;
    VentanaSP ventana;
   
    private LinkedList<Socket> clientes = new LinkedList<Socket>();//aqui se guardara los sockets conectados
    public servidorP()
    {
       /* mensajes= new ventana();
        mensajes.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        mensajes.setVisible(true);*/
        
        try {
             ServerSocket servidor = new ServerSocket(puerto,maxCone);//Se crea socket con puerto 9876
             ventana = new VentanaSP();
             ventana.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
             ventana.setVisible(true);
            //Inicio del hilo para escuchar lo que manden al servidor
             ventana.ponerTexto("Iniciando Servidor Principal....");
            while(true){
                //System.out.println("Servidor principal escuchando....");//icono verde
                ventana.ponerTexto("\nServidor principal escuchando....");
                Socket cliente = servidor.accept();//cliente aceptado
                clientes.add(cliente);
              
                Runnable  run = new servidor(cliente,clientes,this,ventana);//iniciamos hilo para escuchar cliente
                Thread hiloServidor = new Thread(run);
                hiloServidor.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void decodificar(String paquete,int tamaño){
        int paquetes[][]=new int[tamaño][tamaño],mensajes[]=new int[tamaño],k=0,validador=0,determinanteA=0,determinante2=0,va=0,va1=0;
        String num="",mensajeLLego="";
        cramer cramer = new cramer();
        //recuperar mensajes
        for(int i=0;i<tamaño;i++){
            mensajeLLego=mensajeLLego+"[ ";
            for(int j=0;j<tamaño+1;j++){
                while(k<paquete.length() && validador==0){
                    if(!paquete.substring(k,k+1).equals(";")){
                        num=num+paquete.substring(k,k+1);
                    }
                    else{
                        validador=1;
                    }
                    k=k+1;
                }
                if(j<tamaño){
                    paquetes[i][j]=Integer.parseInt(num);
                    mensajeLLego=mensajeLLego+Integer.parseInt(num)+"   ";
                   /* if(va1==0){
                            mensajeLLego=mensajeLLego+Integer.parseInt(num)+"a";
                        }
                        else{
                            if(va1==1){
                            mensajeLLego=mensajeLLego+"+"+Integer.parseInt(num)+"b";
                            }
                            else{
                                if(va1==2){
                                mensajeLLego=mensajeLLego+"+"+Integer.parseInt(num)+"c";
                                }
                                else{
                                    if(va1==3){
                                    mensajeLLego=mensajeLLego+"+"+Integer.parseInt(num)+"d";
                                    }
                                    else{

                                    }
                                }
                            }
                        }
                    va1=va1+1;*/
                }
                else{
                    if(j==tamaño){
                        mensajes[i]=Integer.parseInt(num);
                        mensajeLLego=mensajeLLego+Integer.parseInt(num)+"   ";
                        /*if(va==0){
                            mensajeLLego=mensajeLLego+" = "+Integer.parseInt(num)+"\n";
                        }
                        else{
                            if(va==1){
                            mensajeLLego=mensajeLLego+" = "+Integer.parseInt(num)+"\n";
                            }
                            else{
                                if(va==2){
                                mensajeLLego=mensajeLLego+" = "+Integer.parseInt(num)+"\n";
                                }
                                else{
                                    if(va==3){
                                    mensajeLLego=mensajeLLego+" = "+Integer.parseInt(num)+"\n";
                                    }
                                    else{

                                    }
                                }
                            }
                        }
                        va=va+1;*/
                    }
                }
                validador=0;
                num="";
            }
            mensajeLLego=mensajeLLego+"]\n";
                //va1=0;
        }
        ///
        ventana.ponerTexto("\nPaquete recibido #"+noPaquete+"\n"+mensajeLLego);//<---------------------------------------------------
        noPaquete=noPaquete+1;
        //ya se recupero los mensajes, ahora se procede a decodificar
        int mod[][]=new int[tamaño][tamaño],i=0;
        ArrayList letras=new ArrayList();
        letras.add("a");
        letras.add("b");
        letras.add("c");
        letras.add("d");
                        for(int ii=0;ii<tamaño;ii++){
                                for(int jj=0;jj<tamaño;jj++){
                                    mod[ii][jj]=paquetes[ii][jj];
                                }
                            }
                        if(tamaño<4){
                             determinanteA=cramer.determinante(paquetes, tamaño, tamaño);//<-----det de abajo
                        }
                        else{
                            
                            determinanteA=det4(paquetes);//<-------------------det abajo
                        }
                        while(i<tamaño){
                            for(int j=0;j<tamaño;j++){
                                mod[j][i]=mensajes[j];
                            }
                            if(tamaño<4){
                             determinante2=cramer.determinante(mod, tamaño, tamaño);
                            }
                            else{
                             determinante2=det4(mod);
                            }
                            
                             ventana.ponerTexto("Determinante ("+letras.get(i)+") = "+determinanteA+"\n"); 
                             ventana.ponerTexto("Determinante ("+letras.get(i)+"') = "+determinante2+"\n");
                            for(int ii=0;ii<tamaño;ii++){
                                for(int jj=0;jj<tamaño;jj++){
                                    mod[ii][jj]=paquetes[ii][jj];
                                }
                            }
                            i=i+1;
                            ventana.ponerTexto("Mensaje # "+i+" : \n"+(determinante2/determinanteA));
                            //System.out.println("Mensaje # "+i+" : \n"+(determinante2/determinanteA));
                        }
        //////////////////////////////////////////////////////////
    }
    public int det4(int coeficientes[][]){
        cramer cramer = new cramer();
        int kk=0,uu=0,valor4=0,signo=0,cont=0,coeficientes2[][]=new int[3][3];
                            while(cont<4){
                                for(int k=0;k<4;k++){
                                    for(int u=0;u<4;u++){
                                      if(k!=cont && u!=0){
                                          coeficientes2[kk][uu]=coeficientes[k][u];
                                          uu=uu+1;
                                      }
                                    }
                                    if(uu==3){
                                        uu=0;
                                        kk=kk+1;
                                    }
                                }
                                if(cont%2==0){
                                    signo=1;
                                }
                                else{
                                    signo=-1;
                                }
                                valor4=(cramer.determinante(coeficientes2, 3, 3)*coeficientes[cont][0]*signo)+valor4;
                                kk=0;
                                uu=0;
                                cont=cont+1;
                            }
                            return valor4;
    }
        //Main servidor y inicio programa
    public static void main(String[] args) {
        servidorP servidor= new servidorP();
        
    }
}
