package biba.agentes;

import java.util.Vector;
import biba.agentes.Tipo_Info;
import biba.agentes.sincronizacion.BuzonAgentes;
import biba.persistencia.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) Grupo SI
 * Company:
 * @author Daniel Vilches
 * @version 1.0
 */

/**
 * Agente filtrador que se sirve del orden de los resultados devueltos
 * por los buscadores y de una tabla premio/castigo
 * para realizar el filtraje
 *
 */
 public class AgenteFiltradorOrden extends AgenteFiltrador {

 /**
  * Tema de la búsqueda
	* Necesario ya que no tenemos esa información en el TipoInfo
  */
    private String tema="";

    /**
      * @param buzon Buzon en el que se depositan los resultados
      * @param tema Tema de la búsqueda efectuada
      */
    public AgenteFiltradorOrden(BuzonAgentes buzon, String elTema) {
      super(buzon);
      this.tema=elTema;
    }

    /**
      *
      */
    public Vector filtra() {

      // eliminamos los elementos incongruentes
      vecParaFiltrar = eliminaIncorrectos(vecParaFiltrar);


      biba.persistencia.TablaBuscadores tabla = biba.persistencia.AccesoBD.cargarTabla();

      // ordenar por peso
      // devolver los NUM_VENTANA primeros



    /***** PASO 1: Recorrer el vector asignando la puntuacion
                     segun orden y buscador *****/
      double puntuacionPonderada = tabla.VAL_MIN;

      // enteros para saber el orden de los resultados de cada buscador
      int ordGoogle = 10;
      int ordYahoo = 10;
      int ordExcite = 10;
      int ordLycos = 10;

      String nombreBuscador="";
      Tipo_Info tInfo;
      for(int i=0;i<vecParaFiltrar.size();i++)
      {
        tInfo = (Tipo_Info)vecParaFiltrar.elementAt(i);
        nombreBuscador = (String)tInfo.dame_quien().firstElement();

        puntuacionPonderada = tabla.getValoracion(nombreBuscador,this.tema);
        if (nombreBuscador.equals("Google")) {
          // Calculamos la puntuacion de la url segun su orden en los resultados
          // del buscador
          puntuacionPonderada *= ordGoogle;
          // Actualizamos el entero que nos indica el orden
          // (este valor nunca sera inferior a 1)
          if (ordGoogle > 1)
            ordGoogle = ordGoogle - 1 ;
        }
        else if (nombreBuscador.equals("Excite")) {
          // Calculamos la puntuacion de la url segun su orden en los resultados
          // del buscador
          puntuacionPonderada *= ordExcite;
          // Actualizamos el entero que nos indica el orden
          // (este valor nunca sera inferior a 1)
          if (ordExcite > 1)
            ordExcite = ordExcite - 1 ;
        }
        else if (nombreBuscador.equals("Yahoo")) {
          // Calculamos la puntuacion de la url segun su orden en los resultados
          // del buscador
          puntuacionPonderada *= ordYahoo;
          // Actualizamos el entero que nos indica el orden
          // (este valor nunca sera inferior a 1)
          if (ordYahoo > 1)
            ordYahoo = ordYahoo - 1 ;
        }
        else if (nombreBuscador.equals("Lycos")) {
          // Calculamos la puntuacion de la url segun su orden en los resultados
          // del buscador
          puntuacionPonderada *= ordLycos;
          // Actualizamos el entero que nos indica el orden
          // (este valor nunca sera inferior a 1)
          if (ordLycos > 1)
            ordLycos = ordLycos - 1 ;
        }
        tInfo.setHits(puntuacionPonderada);

      } // end for

    /***** PASO 2: eliminar URLs repetidas
                     (nos quedamos con la puntuacion mayor de las 2) *****/

      biba.utiles.EliminaRepeticiones eliminaRep = new biba.utiles.EliminaRepeticiones();
      vecParaFiltrar = eliminaRep.elimina(vecParaFiltrar);


    /***** PASO 3: ordenar URLs por orden no creciente de puntuacion *****/

      // Inicializamos vecFiltrado (vector a devolver)
      // con NUM_VENTANA_FILTRO elementos de puntuacion 0
      Tipo_Info infoCero = new Tipo_Info("cero","cero","cero",50,"cero");
      infoCero.setHits(0);
      vecFiltrado.clear();
      for(int i=0;i<biba.agentes.AgenteFiltrador.NUM_VENTANA_FILTRO;i++)
      {
        vecFiltrado.addElement(infoCero);
      }

      // variables para movernos por vecFiltrado
      Tipo_Info elemFiltrado;
      int indice;

      for(int i=0;i<vecParaFiltrar.size();i++)
      {
        tInfo = (Tipo_Info)vecParaFiltrar.elementAt(i);
        // empezamos por el ultimo elemento de vecFiltrado
        indice = biba.agentes.AgenteFiltrador.NUM_VENTANA_FILTRO-1;
        elemFiltrado = (Tipo_Info)vecFiltrado.elementAt(indice);
        // Solo añadimos la URL si su puntuacion es mayor que la puntuacion
        // de la ultima URL de vecFiltrado
        // (vecFiltrado esta ordenado por orden no creciente de puntuacion)
        if (tInfo.getHits() > elemFiltrado.getHits()) {
          // Lo insertamos ordenadamente en vecFiltrado
          elemFiltrado = (Tipo_Info)vecFiltrado.elementAt(indice-1);
          while ( (tInfo.getHits() > elemFiltrado.getHits()) && (indice > 0) )
          {
            vecFiltrado.setElementAt(vecFiltrado.elementAt(indice-1),indice);
            indice = indice - 1;
            if (indice > 0)
              elemFiltrado = (Tipo_Info)vecFiltrado.elementAt(indice-1);
          }
          vecFiltrado.setElementAt(tInfo,indice);
        }
      }
      // Quitamos los elementos vacios que hayan podido quedar en vecFiltrado
      for(int i=biba.agentes.AgenteFiltrador.NUM_VENTANA_FILTRO-1;i>=0;i--) {
        if ( ((Tipo_Info)vecFiltrado.elementAt(i)).dame_url().equals("cero"))
          vecFiltrado.removeElementAt(i);
      }
      return this.vecFiltrado;
    }


    public static void main( String args[] )
    {
      AgenteFiltradorOrden afo = new AgenteFiltradorOrden(null,"informatica");

      Tipo_Info t1=new Tipo_Info("Google1","Micro","Res",50,"Google");
      Tipo_Info t2=new Tipo_Info("Google2","Micro","Res",50,"Google");
      Tipo_Info t3=new Tipo_Info("Google3","Micro","Res",50,"Google");
      Tipo_Info t4=new Tipo_Info("Google4","Micro","Res",50,"Google");
      Tipo_Info t5=new Tipo_Info("Google5","Micro","Res",50,"Google");
      Tipo_Info t6=new Tipo_Info("Google1","Micro","Res",50,"Lycos");
      Tipo_Info t7=new Tipo_Info("Lycos2","Micro","Res",50,"Lycos");
      Tipo_Info t8=new Tipo_Info("Lycos3","Micro","Res",50,"Lycos");
      Tipo_Info t9=new Tipo_Info("Lycos4","Micro","Res",50,"Lycos");
      Tipo_Info t10=new Tipo_Info("Lycos5","Micro","Res",50,"Lycos");
      Tipo_Info t11=new Tipo_Info("Google1","Micro","Res",50,"Excite");
      Tipo_Info t12=new Tipo_Info("Excite2","Micro","Res",50,"Excite");
      Tipo_Info t13=new Tipo_Info("Excite3","Micro","Res",50,"Excite");
      Tipo_Info t14=new Tipo_Info("Excite4","Micro","Res",50,"Excite");
      Tipo_Info t15=new Tipo_Info("Excite5","Micro","Res",50,"Excite");
      Tipo_Info t16=new Tipo_Info("Yahoo1","Micro","Res",50,"Yahoo");
      Tipo_Info t17=new Tipo_Info("Yahoo2","Micro","Res",50,"Yahoo");
      Tipo_Info t18=new Tipo_Info("Yahoo3","Micro","Res",50,"Yahoo");
      Tipo_Info t19=new Tipo_Info("Yahoo4","Micro","Res",50,"Yahoo");
      Tipo_Info t20=new Tipo_Info("Yahoo5","Micro","Res",50,"Yahoo");

      Vector vec = new Vector();
      vec.addElement(t1);
      vec.addElement(t2);
      vec.addElement(t3);
      vec.addElement(t4);
      vec.addElement(t5);
      vec.addElement(t6);
      vec.addElement(t7);
      vec.addElement(t8);
      vec.addElement(t9);
      vec.addElement(t10);
      vec.addElement(t11);
      vec.addElement(t12);
      vec.addElement(t13);
      vec.addElement(t14);
      vec.addElement(t15);
      vec.addElement(t16);
      vec.addElement(t17);
      vec.addElement(t18);
      vec.addElement(t19);
      vec.addElement(t20);

     /* for(int i=0;i<vec.size();i++)
      {
        ((Tipo_Info)vec.elementAt(i)).ver();

      }*/

      Vector resultado = new Vector();
      afo.setObjetoAFiltrar(vec);
      resultado = afo.filtra();

      for(int i=0;i<resultado.size();i++)
      {
        ((Tipo_Info)resultado.elementAt(i)).ver();

      }
    }


}