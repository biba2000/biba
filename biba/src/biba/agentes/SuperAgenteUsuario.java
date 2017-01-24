//Source file: d:/tomcat/webapps/biba/WEB-INF/classes/biba/agentes/SuperAgenteUsuario.java
package biba.agentes;

import biba.agentes.*;
import java.util.Vector;
import biba.agentes.sincronizacion.*;
import biba.persistencia.propiedades;
import java.io.*;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 *
 */
public class SuperAgenteUsuario extends SuperAgente
{

   public SuperAgenteUsuario()
   {
		super();
   }

   /**
   * @param busqueda lo
   */
  public Vector busca(Busqueda busqueda)
   {
    //determinar los agentes recolectores a usar
		/*
			Pestañas y características generales determinan los agentes recolectores
			a utilizar en la búsqueda

			Características particulares se añaden al query

			Esto crea un filtrado a priori de la información que vamos a obtener
		*/
    AgenteRecolectorGoogle arg = new AgenteRecolectorGoogle(buzonRecolector,busqueda);
    AgenteRecolectorExcite are = new AgenteRecolectorExcite(buzonRecolector,busqueda);
		AgenteRecolectorYahoo  ary = new AgenteRecolectorYahoo(buzonRecolector,busqueda);
		AgenteRecolectorLycos  arl = new AgenteRecolectorLycos(buzonRecolector,busqueda);

    /*arg.setConsulta(busqueda);
    are.setConsulta(busqueda);
    ary.setConsulta(busqueda);
    arl.setConsulta(busqueda);
		*/
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

		while ((!salir) && (numRecolectoresActivos>0))
		{
      eventoSacado = (Event)buzonRecolector.sacar();
		  if(eventoSacado.getTipoEvento() == Event.INFORMACION)
			{
      //tratamos evento
				if (debug>0)System.out.println(eventoSacado.getContenido().toString());
			}
      else if(eventoSacado.getTipoEvento() == Event.TIME_OUT)
			{
				//tratamos evento
        if (debug>0)System.out.println("Evento de TIME_OUT !");
        salir = true;
      }
			else if(eventoSacado.getTipoEvento() == Event.BUSQUEDA_FINALIZADA)
			{
				//tratar
        if (debug>0)System.out.println("Ha finalizado una búsqueda");
				numRecolectoresActivos--;
				// añadimos al almacén de respuestas no filtradas
				if((Vector)eventoSacado.getContenido()!=null)resp_total_no_filtrada.addAll((Vector)eventoSacado.getContenido());
			}
      else if (debug>0)System.err.println("Error: Tipo de Evento no esperado");
    }

		if (debug>0)System.out.println("Hemos terminado con las búsquedas");

		/*
			Filtraje de resultados

			Dependiendo de varias heurísticas se selecciona un conjunto de
			resultados que se muestran al usuario

			Filtraje a posteriori
		*/

		for(int i=0;i<resp_total_no_filtrada.size();i++)
		{
			if(debug>0)((Tipo_Info)resp_total_no_filtrada.elementAt(i)).ver();
		}

		//determinar el agentes filtradores a usar

    AgenteFiltradorSimple afs = null;
		AgenteFiltradorTabla aft = null;
		AgenteFiltradorOrden afo = null;
		Temporizador temporizador2=new Temporizador(TIEMPO_MAXIMO_FILTRAJE_EN_MS,buzonFiltrador);
    Vector resp_filtrada=new Vector();

		// leer el fichero de propiedades
		propiedades props=new propiedades();
		String modoFiltro=props.getPropiedad("MODO_FILTRAJE");
		if(modoFiltro.equalsIgnoreCase("simple"))
		{
				afs=new AgenteFiltradorSimple(buzonFiltrador);
				afs.setObjetoAFiltrar(resp_total_no_filtrada);
				afs.start();
		}
		else if(modoFiltro.equalsIgnoreCase("tabla"))
		{
				aft=new AgenteFiltradorTabla(buzonFiltrador,busqueda.idTema,busqueda.cadenaBusqueda);
			  aft.setObjetoAFiltrar(resp_total_no_filtrada);
			  aft.start();
		}
		else if(modoFiltro.equalsIgnoreCase("orden"))
		{
				afo=new AgenteFiltradorOrden(buzonFiltrador,busqueda.idTema);
				afo.setObjetoAFiltrar(resp_total_no_filtrada);
				afo.start();
		}
		else
		{
			if (debug>0)System.out.println("ERROR:modo de filtraje no reconocido, revise el parámetro en el fichero de propiedades (MODO_FILTRAJE)");
		}

		temporizador2.start();

		//esperar los resultados
		// acotamos el tiempo si ya han acabado todos
		int numFiltradoresActivos = NUM_FILTRADORES;
    salir=false;
		while ((!salir)&&(numFiltradoresActivos>0))
		{
      eventoSacado = (Event)buzonFiltrador.sacar();
		  if(eventoSacado.getTipoEvento() == Event.INFORMACION)
			{
      //tratamos evento
				if (debug>0)System.out.println(eventoSacado.getContenido().toString());
			}
      else if(eventoSacado.getTipoEvento() == Event.TIME_OUT)
			{
				//tratamos evento
        if (debug>0)System.out.println("Evento de TIME_OUT !");
        salir = true;
      }
			else if(eventoSacado.getTipoEvento() == Event.FILTRAJE_FINALIZADO)
			{
				//tratar
        if (debug>0)System.out.println("Ha finalizado un filtrador");
				numFiltradoresActivos--;
				resp_filtrada.addAll((Vector)eventoSacado.getContenido());
			}
      else if (debug>0)System.err.println("Error: Tipo de Evento no esperado");
    }

		if (debug>0)System.out.println("Hemos terminado con el filtraje");

    return resp_filtrada;
   }
}
