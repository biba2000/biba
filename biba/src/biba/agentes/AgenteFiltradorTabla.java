package biba.agentes;

import java.util.Vector;
import biba.agentes.Tipo_Info;
import biba.agentes.sincronizacion.BuzonAgentes;
import biba.persistencia.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) Emilio Bobadilla
 * Company:
 * @author Emilio Bobadilla, Jorge González
 * @version 1.0
 */

/**
 * Agente filtrador que se sirve de una tabla premio/castigo<br>
 * para realizar el filtraje
 *
 *
 */
 public class AgenteFiltradorTabla extends AgenteFiltrador {

 /**
  * Tema de la búsqueda
	* Necesario ya que no tenemos esa información en el TipoInfo
  */
	private String tema="";

	/**
	 * Query de la busqueda
	 * Necesario ya que no tenemos esta info en el TipoInfo
	 */
	String query="";

	/**
	 * @param buzon Buzon en el que se depositan los resultados
	 * @param tema Tema de la búsqueda efectuada
	 */
	public AgenteFiltradorTabla(BuzonAgentes buzon, String elTema, String elQuery) {

    super(buzon);
		this.tema=elTema;
		this.query=elQuery;
	}

	/**
	 *
	 */
  public Vector filtra() {

		// eliminamos los elementos incongruentes
 		vecParaFiltrar = eliminaIncorrectos(vecParaFiltrar);

		// eliminamos los que no se corresponden con el query
		vecParaFiltrar = eliminaNoApariciones(vecParaFiltrar,query);


		biba.persistencia.TablaBuscadores tabla = biba.persistencia.AccesoBD.cargarTabla();

		// recorrer el vector
		// asignar los pesos
		// ordenar por peso
		// devolver los NUM_VENTANA primeros
		double puntuacionPonderada = tabla.VAL_MIN;

		String nombreBuscador="";
		Tipo_Info tipoInfo;
		for(int i=0;i<vecParaFiltrar.size();i++)
      {
				tipoInfo = (Tipo_Info)vecParaFiltrar.elementAt(i);
				nombreBuscador = (String)tipoInfo.dame_quien().firstElement();

				puntuacionPonderada = tabla.getValoracion(nombreBuscador,this.tema);
				tipoInfo.setHits(puntuacionPonderada);
				if (debug>0) System.out.println("FiltradorTabla: Tema:"+this.tema+",NombreBuscador:"+nombreBuscador+",puntuacion:"+puntuacionPonderada+"TipoInfo:");
				if (debug>0) ((Tipo_Info)vecParaFiltrar.elementAt(i)).toString();
      }

		// mostramos el vector para compro
		//if (debug>0)System.out.println("AgenteFiltradorTabla: Vector de informacion ponderada");
		//if (debug>0)System.out.println(vecParaFiltrar.toString());

    // recogemos un array de srtings con los nombres de los buscadores ordenados por ponderación
		String[] buscadoresOrd = tabla.getBuscadoresOrdenados(this.tema);
		// los buscadores que tenemos son
  	//if (debug>0)System.out.println("AgenteFiltradorTabla:"+tabla.toString());


		String buscadorActual ="";
	  String buscadorEncontrado="";
		Tipo_Info tinfo =null;
		int numElems = 0;
		int numElemsPorBuscador = biba.agentes.AgenteFiltrador.NUM_VENTANA_FILTRO / buscadoresOrd.length;
		// añadimos este número a los elementos para eliminar los huecos
		int sumarAlPrimero = biba.agentes.AgenteFiltrador.NUM_VENTANA_FILTRO - 	buscadoresOrd.length * numElemsPorBuscador;

		boolean esta=false;

		for(int i=0;i<buscadoresOrd.length;i++){

			  	buscadorActual = buscadoresOrd[i];

					numElems = 0;

					// añadimos este número a los elementos para eliminar los huecos
				  if (i==0) numElems -= sumarAlPrimero;

					for (int j=0; j < vecParaFiltrar.size() && numElems<numElemsPorBuscador;j++){

						  tinfo = (Tipo_Info)vecParaFiltrar.elementAt(j);
						  buscadorEncontrado = (String) tinfo.dame_quien().firstElement();

							if(buscadorEncontrado.equalsIgnoreCase(buscadorActual)){
								// comprobamos si está ya en la lista
								esta=false;
								for(int k=0;k < vecFiltrado.size() && !esta; k++)
								{
									if(((Tipo_Info)vecFiltrado.elementAt(k)).dame_url().equals(tinfo.dame_url()))
									{
										esta=true;
										((Tipo_Info)vecFiltrado.elementAt(k)).actualizaQuien(tinfo.dame_quien());
									}
								}
							  if (!esta)
								{
								  this.vecFiltrado.addElement(tinfo);
								  numElems++;
								}
							}


					}

		}
/*    // eliminamos los elementos incongruentes
    vecFiltrado = eliminaIncorrectos (vecFiltrado);
*/
	  return this.vecFiltrado;
	}



}