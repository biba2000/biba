/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Emilio Bobadilla<p>
 * @author Emilio Bobadilla
 * @version 1.0
 */
package biba.agentes.sincronizacion;

import java.util.Vector;
/**
 * Clase que implementa los buzones con espera.
 * El buz�n puede contener cualquier tipo de Object.
 *
 * Ejemplo de uso:
 *         // Productor / Consumidor
 *
 *         public void consumidor(BuzonAgentes buzon){
 *            String cadena  = (String) buzon.sacar();
 *            System.out.println(cadena);
 *         }
 *         public void productor(BuzonAgentes buzon){
 *             buzon.meter(new String("BIBA!"));
 *         }
 *
 */

public class BuzonAgentes
{

    //Contenido del buz�n
    private java.util.Vector contenido=new Vector();

    /**
     * mete el objeto obj en el buz�n,despu�s despierta a todas las hebras que
     * esten esperando en el buz�n.
     * @param obj , objeto que se introduce en el buz�n
     */
    public synchronized void meter (Object obj)
    {
        contenido.addElement(obj);
        notifyAll();
    }

    /**
     * la hebra que invoque este m�todo quedar� suspendida si no hubiesen objetos
     * en el buz�n que sacar.
     * En el momento que exista un objeto, la hebra se despierta y obtiene el objeto
     *
     * Si existiesen varias hebras esperando, solo una obtiene el objeto y
     * las dem�s continuaran esperando.
     *
     * @return Object : objeto que obtiene del buz�n.
     */
    public synchronized Object sacar()
    {

        //mientras no hayan objetos en el buz�n esperamos
        while(contenido.size()<1){
            try {
                wait();
            }catch(java.lang.InterruptedException ie) {
             System.out.println("Error en la operacion de sacar del BuzonAgentes,se ha interrumpido de forma externa la espera");
            }
        }

        Object sacado = contenido.elementAt(0);
        contenido.removeElementAt(0);
        return sacado;
    }

    /**
     * Comprueba si el buz�n est� vac�o
     *
     * @return true si el buz�n vac�o
     *         false en otro caso
     *
     */

    public synchronized boolean vacio(){
           return (this.contenido.size()==0);

    }


    /**
     * elimina todos los objetos contenidos en el buz�n
     *
     */

    public synchronized void reset()
    {

        contenido.removeAllElements();

    }



}

