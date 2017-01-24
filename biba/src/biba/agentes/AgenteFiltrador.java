package biba.agentes;

import java.io.*;
import java.util.*;
import biba.agentes.Tipo_Info;
import biba.persistencia.propiedades;
import biba.agentes.sincronizacion.*;
import biba.utiles.UtilidadCadena;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 */

public abstract class AgenteFiltrador extends Thread
{

	protected int debug=0;

  /**
	 * Tamaño del vector respuesta a devolver tras el filtrado
	 */
  public final static int NUM_VENTANA_FILTRO=10;

	/**
	 * Referencia al objeto que vamos a filtrar.
	 */
	protected Vector vecParaFiltrar;

	/**
	 * Almacén de resultados
	 */
  protected Vector vecFiltrado;

	/**
	 * Referencia a buzón de comunicación
	 */
	BuzonAgentes buzonFiltrador;

	UtilidadCadena util= new UtilidadCadena();

	public AgenteFiltrador(BuzonAgentes buzonFil)
	{
	  // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

		buzonFiltrador=buzonFil;
		this.vecFiltrado = new Vector();
  }

	public Vector getVecFiltrado()
	{
		if (debug>0)System.err.println("WARNING:El filtrador devuelve un vector vacío");
		return vecFiltrado;
	}

	/**
	 * Indica el vector que vamos a filtrar
	 */
	public void setObjetoAFiltrar(Vector vecTipoInfo)
	{
		vecParaFiltrar = vecTipoInfo;
	}
/**
 * Realiza el filtrado y mete los resultados en el buzón de comunicación
 */
	public void run()
	{
		if (vecParaFiltrar!=null)
		{
			vecFiltrado=filtra();
			Event ev = new Event(Event.FILTRAJE_FINALIZADO,vecFiltrado);
			buzonFiltrador.meter(ev);
		}
		else
		{
			if (debug>0)System.err.println("WARNING:Intentó filtrar un vector vacío");
		}
	}
  /** Filtrado según estrategia
   *  @param vec_de_TipoInfo Es un vector que contiene elementos TipoInfo
   *  @return Vector de TipoInfo con los resultados ordenados después del filtraje
   */
  public abstract Vector filtra();

	/**
   * Recorre un vector de TipoInfo y elimina los resultados en los que no aparezca
   * en el resumen o en la URL ninguna de las palabras que se le pasan como una lista
   * @param vTipoInfo Vector de datos Tipo_Info
   *
   */

	 public Vector eliminaNoApariciones(Vector vTipoInfo, String query)
	 {
		  Vector listapalabras = util.separaQuery(query);
		  Vector dev=new Vector();
		  boolean hayQueBorrarla=false;
			Tipo_Info tinfo=null;
			String resumen="";
			String url="";
			String titulo="";
			// indice para recorrer las palabras
			int j=0;

		  //recorremos el vector
			for(int i=0;i<vTipoInfo.size();i++)
			{
				hayQueBorrarla = true;
				tinfo = (Tipo_Info)vTipoInfo.elementAt(i);
				resumen=tinfo.dame_resumen().toUpperCase();
				url=tinfo.dame_url().toUpperCase();
				titulo=tinfo.dame_titulo().toUpperCase();

				j=0;
				while((j<listapalabras.size()) && (hayQueBorrarla))
				{
					if( (resumen.indexOf(((String)listapalabras.elementAt(j)).toUpperCase()) != -1) ||
							(url.indexOf(((String)listapalabras.elementAt(j)).toUpperCase()) != -1) ||
							(titulo.indexOf(((String)listapalabras.elementAt(j)).toUpperCase()) != -1)   )
					{
						hayQueBorrarla = false;
					}
					j++;
				}

				if (!hayQueBorrarla)
				{
				  dev.add(tinfo);
				}
			}
			return dev;
	 }

  /**
   * Recorre un vector de TipoInfo y elimina los resultados no congruentes (p.e. null's)
   */
  public Vector eliminaIncorrectos(Vector vec_de_TipoInfo)
  {
		String aux="";
		if(vec_de_TipoInfo==null) return null;
    for(int i=0; i < vec_de_TipoInfo.size() ;i++)
    {
			  // elimino nulls
				if (vec_de_TipoInfo.elementAt(i)==null)
				{
					vec_de_TipoInfo.removeElementAt(i);
				}
				// elimino vacíos
				else if ( ((Tipo_Info)vec_de_TipoInfo.elementAt(i)).esVacio())
				{
					vec_de_TipoInfo.removeElementAt(i);
				}
				/////////////////////////////////////////
				// Parche hasta que se revise el autómata del excite, que falla
				else if ( ((Tipo_Info)vec_de_TipoInfo.elementAt(i)).dame_url().equalsIgnoreCase("Web Sites"))
				{
					vec_de_TipoInfo.removeElementAt(i);
				}
				else
				{
				//elimino los nbsp's del yahoo
				 aux = ((Tipo_Info)vec_de_TipoInfo.elementAt(i)).dame_url();
				 aux = util.elimina2nbspAlFinal(aux);
				 // esta llamada se come los caracteres n,b,s,p de la cadena !!!!!
				 // STRINGTOKENIZER CRIBA CADA CHAR DE LA CADENA SEPARADOR POR SEPARADO !!!!!
				((Tipo_Info)vec_de_TipoInfo.elementAt(i)).pon_url(aux);
				}
    }
    return vec_de_TipoInfo;
  }

	public static void main( String args[] )
	{
	  UtilidadCadena util = new UtilidadCadena();
		AgenteFiltradorSimple af = new AgenteFiltradorSimple(null);
		Tipo_Info t1=new Tipo_Info("1http://msn.com&nbsp;&nbsp;","Microsoft","Res",50,"Google");
		Tipo_Info t2=new Tipo_Info("2http://microsoft.msn.com&nbsp;&nbsp;","Microsoft","Res",50,"Google");
		Tipo_Info t3=new Tipo_Info("3tttt://tt.ttt.com&nbsp;&nbsp;","Microsorf","Res",50,"Google");
		Tipo_Info t4=new Tipo_Info("4http://msn.com&nbsp;&nbsp;","Micro","Res",50,"Google");
		Tipo_Info t5=new Tipo_Info("5http://msn.com&nbsp;&nbsp;","Micro","Res",50,"Google");
		Tipo_Info t6=new Tipo_Info("6http://msn.com&nbsp;&nbsp;","Micro","Res",50,"Google");
		Tipo_Info t7=new Tipo_Info("7http://","o","",5,"e");

		Vector vec = new Vector();
		vec.addElement(t1);
		vec.addElement(t2);
		vec.addElement(t3);
		vec.addElement(t4);
		vec.addElement(t5);
		vec.addElement(t6);
		vec.addElement(t7);

		String lis[]={"MIcrosoft","msn"};
		String query="msn";
		Vector nas = af.eliminaNoApariciones(vec,query);

		for(int i=0;i<nas.size();i++)
		{
			((Tipo_Info)nas.elementAt(i)).ver();
		}
	}


}