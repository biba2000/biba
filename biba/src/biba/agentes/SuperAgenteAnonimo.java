package biba.agentes;

import biba.agentes.*;
import java.util.Vector;
import biba.agentes.sincronizacion.*;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 */

public class SuperAgenteAnonimo extends SuperAgente
{

  public SuperAgenteAnonimo() {
    super();
  }

  public Vector busca(String cad_busqueda) {

    //determinar los agentes recolectores a usar
		Busqueda bus = new Busqueda(null, "general",cad_busqueda,"");

    AgenteRecolectorGoogle arg = new AgenteRecolectorGoogle(buzonRecolector,bus);
    AgenteRecolectorExcite are = new AgenteRecolectorExcite(buzonRecolector,bus);
    AgenteRecolectorYahoo  ary = new AgenteRecolectorYahoo(buzonRecolector,bus);
    AgenteRecolectorLycos  arl = new AgenteRecolectorLycos(buzonRecolector,bus);

    // preparamos la busqueda
    Vector plantilla = new Vector();
    biba.estructurasDatos.PlantillaCaracteristicasGenerales plantillaVacia;
    plantillaVacia = new biba.estructurasDatos.PlantillaCaracteristicasGenerales();
    plantilla.addElement(plantillaVacia);
/*    arg.setConsulta(plantilla,"general",cad_busqueda,"");
    are.setConsulta(plantilla,"general",cad_busqueda,"");
    ary.setConsulta(plantilla,"general",cad_busqueda,"");
    arl.setConsulta(plantilla,"general",cad_busqueda,"");
*/
    /********** RECOLECCION **********/

    // crear un temporizador para asegurarnos un tiempo máximo de búsqueda
    temporizador=new Temporizador(TIEMPO_MAXIMO_RECOLECCION_EN_MS,buzonRecolector);

    // llamar concurrentemente a los recolectores y temporizador
    arg.start();
    are.start();
    ary.start();
    arl.start();
    temporizador.start();

    //almacén de respuestas
    Vector resp_total_no_filtrada=new Vector();

    //evento que sacamos del buzon
    Event eventoSacado;
    boolean salir=false;

    // acotamos el tiempo si ya han acabado todos
    int numRecolectoresActivos = NUM_RECOLECTORES;

    while ((!salir) && (numRecolectoresActivos>0)) {
      eventoSacado = (Event)buzonRecolector.sacar();
      if(eventoSacado.getTipoEvento() == Event.INFORMACION) {
        //tratamos evento
        if (debug>0)System.out.println(eventoSacado.getContenido().toString());
      }
      else if(eventoSacado.getTipoEvento() == Event.TIME_OUT) {
        //tratamos evento
        if (debug>0)System.out.println("Evento de TIME_OUT !");
        salir = true;
      }
      else if(eventoSacado.getTipoEvento() == Event.BUSQUEDA_FINALIZADA) {
        //tratar
        if (debug>0)System.out.println("Ha finalizado una búsqueda");
        numRecolectoresActivos--;
        // añadimos al almacén de respuestas no filtradas
        if(eventoSacado.getContenido()!=null)resp_total_no_filtrada.addAll((Vector)eventoSacado.getContenido());
      }
      else
        if (debug>0)System.err.println("Error: Tipo de Evento no esperado");
    }

    if (debug>0)System.out.println("Hemos terminado con las búsquedas");

		for(int i=0;i<resp_total_no_filtrada.size();i++)
		{
			if(debug>0)System.out.print(((Tipo_Info)resp_total_no_filtrada.elementAt(i)).toString());
		}



    /********** FILTRADO **********/

    // Usamos un filtrado simple
    AgenteFiltradorSimple afs = new AgenteFiltradorSimple(buzonFiltrador);

    // filtrar esos resultados
    Vector resp_filtrada=new Vector();
    afs.setObjetoAFiltrar(resp_total_no_filtrada);

    Temporizador temporizador2=new Temporizador(TIEMPO_MAXIMO_FILTRAJE_EN_MS,buzonFiltrador);

    //ejecutar la hebra
    afs.start();
    temporizador2.start();

    //esperar los resultados
    // acotamos el tiempo si ya han acabado todos
    int numFiltradoresActivos = 1;
    salir=false;
    while ((!salir)&&(numFiltradoresActivos>0)) {
      eventoSacado = (Event)buzonFiltrador.sacar();
      if(eventoSacado.getTipoEvento() == Event.INFORMACION) {
        //tratamos evento
        if (debug>0)System.out.println(eventoSacado.getContenido().toString());
      }
      else if(eventoSacado.getTipoEvento() == Event.TIME_OUT) {
        //tratamos evento
        if (debug>0)System.out.println("Evento de TIME_OUT !");
        salir = true;
      }
      else if(eventoSacado.getTipoEvento() == Event.FILTRAJE_FINALIZADO) {
        //tratar
        if (debug>0)System.out.println("Ha finalizado un filtrador");
        numFiltradoresActivos--;
        resp_filtrada.addAll((Vector)eventoSacado.getContenido());
      }
      else
        if (debug>0)System.err.println("Error: Tipo de Evento no esperado");
    }

    if (debug>0)System.out.println("Hemos terminado con el filtraje");

    return resp_filtrada;

  }


}