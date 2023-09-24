/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joserafael
 */
public class cramer {
       public cramer()
    {
    }
    //funcion que determina si es L.I o L.D
    //a[][] arreglo que contiene los coeficientes de las ecuaciones, m = # filas, n=# columnas
    public int determinante (int a[][],int m, int n)
    {   int b[][]=new int[m][5];
        if(n==3 && m==3)//se llena las ultims dos columnas si el tamaño es de 3
        {
            for(int i=0;i<m;i++){
                for(int j=0;j<5;j++){
                    if(j<3){
                        b[i][j]=a[i][j];
                    }     
                    else{
                        b[i][j]=a[i][j-3];
                    }
                }
            }            
            n=5;
            a=b;
        }
        int suma1=0,suma2=0,numero1=1,numero2=1,cont1=0,cont2=0;
        //devuelte true si es L.I y false si es L.D
        for(int i=0;i<m;i++){
            numero1=numero1*a[i][i+(1*cont1)];//multiplica coeficientes bajando
            numero2=numero2*a[(m-1)-(1*cont2)][i+(1*cont1)];//multiplica coeficintes subiendo
            cont2=cont2+1;
            if(i==(m-1)){//se pegunta si estamos en la ultima fila para comenzar otro ciclo o acabar recorrido
                if(i==(m-1) && (i+(1*cont1))!=(n-1)){//pregunta si posicion i esta en la ultima posicion pero no es la ultima columna(no ha terminado recorrer todo)
                   i=-1;
                   cont1=cont1+1;
                   cont2=0;
                   suma1=suma1+numero1;//suma la multiplicacion de los numeros para abajo
                   suma2=suma2+numero2;//suma la multiplicacion de los numeros para arriba
                   numero1=1;
                   numero2=1;
                }
                else{
                    if(i==(m-1) && (i+(1*cont1))==(n-1)){//pregunta si posicion i esta en la ultima posicion y en la ultima columna(termino recorrer todo)
                        suma1=suma1+numero1;//suma la multiplicacion de los numeros para abajo
                        suma2=suma2+numero2;//suma la multiplicacion de los numeros para arriba
                        i=m;//i se le asigna tamaño m para que acabe el ciclo
                    }
                }
            }
        }
        System.out.println("suma1: "+suma1+" suma2: "+suma2+" resultado: "+(suma1-suma2));
       
            return (suma1-suma2);//devuelve !=0 si es L.I y 0 si es L.D
        
    }
}
