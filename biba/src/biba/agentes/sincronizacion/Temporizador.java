package biba.agentes.sincronizacion;

import biba.persistencia.propiedades;

/**
 *  La clase temporizador nos permite lanzar un evento de TIME_OUT a un buzon
 *  para así evitar que la hebra que espera en el buzón lo haga indefinidamente
 *  El tipo de Evento que introducirá el temporizador en el buzón es Event.TIME_OUT
 */

public class Temporizador extends Thread{


   //atributos

	 private int debug=0;

   BuzonAgentes buzon=null; //buzon donde meteremos el evento Event.TIME_OUT
   long tiempoEspera=0; //tiempo que esperaremos antesr de insertar el Evento



	/**
	*	Constructor.
	*	@param tiempoEspera, tiempo en milisegundos que espera la hebra Temporizador
	*				   antes de generar el Evento de TIME_OUT
	*	@param buzon, buzón donde se genera el evento de TIME_OUT
	*
	*/


	public Temporizador(long tiempoEspera,BuzonAgentes buzon){
         this.buzon = buzon;
         this.tiempoEspera= tiempoEspera;

	 // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

	}


  /**
  * Genera un evento Event.TIME_OUT cuando el tiempo de Espera llega a 0
  *
  */
  public void run (){
		try{
			this.sleep(tiempoEspera);
		}catch(InterruptedException ex){
			if(debug>0)System.err.println("WARNING:Temporizador interrumpido antes de tiempo de forma inesperada");
			ex.printStackTrace();
		}
		Event ev = new Event(Event.TIME_OUT);
		buzon.meter(ev);
  }

}




