package biba.agentes;

import java.util.Vector;
import biba.agentes.Tipo_Info;
import biba.agentes.sincronizacion.BuzonAgentes;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez

 * @version 1.0
 */

public class AgenteFiltradorSimple extends AgenteFiltrador
{

  public AgenteFiltradorSimple(BuzonAgentes buzonFil)
	{
		super(buzonFil);
  }

  public Vector filtra()
  {

  /* NOTA:
  cuando quede tiempo se debería implementar un vector que no admita elementos repetidos y sea
  capaz de ordenarlos por el nº de hit
  -*/
		vecParaFiltrar = eliminaIncorrectos(vecParaFiltrar);
    // meto un elemento minimal en el vector, luego será ignorado
    Tipo_Info t=new Tipo_Info();
    // garantizamos que todos los elementos serán mayores que este
    t.setHits(-1);
    vecFiltrado.addElement(t);

/*
    try
    {
*/
      //Comparacion de elementos en O(n2), sumo 1 por cada coincidencia al primero
      //nos quedarán los elementos coincidentes en orden decreciente de hits
      //actualizo el vector de suministradores en el primer encuentro del elemento

      for(int i=0;i<vecParaFiltrar.size();i++)
      {
        for(int j=i+1;j<vecParaFiltrar.size();j++)
        {
          if ( (((Tipo_Info)vecParaFiltrar.elementAt(i)).dame_url()).equals(((Tipo_Info)vecParaFiltrar.elementAt(j)).dame_url())  )
          {
            double hit_anterior=((Tipo_Info)vecParaFiltrar.elementAt(i)).getHits();
            hit_anterior++;
            ((Tipo_Info)vecParaFiltrar.elementAt(i)).setHits(hit_anterior);
            ((Tipo_Info)vecParaFiltrar.elementAt(i)).actualizaQuien(
                                  ((Tipo_Info)vecParaFiltrar.elementAt(j)).dame_quien()
                                                          );
          }
        }
      }

       // ahora voy insertando elementos eliminando repeticiones y por orden en la respuesta
      for(int i=0;i<vecParaFiltrar.size();i++)
      {
        boolean esta=false;
        // veo si esta en la respuesta
        int j=0;
        while ((j<vecFiltrado.size()) & !esta)
        {
          if (
             (((Tipo_Info)vecParaFiltrar.elementAt(i)).dame_url()).equals(((Tipo_Info)vecFiltrado.elementAt(j)).dame_url())
             )
            {
              esta=true;
            }
          j++;
        }// while

        // si no lo estaba lo inserto en orden

          if(!esta)
          {
              int k=0;
              boolean insertado = false;
              while((k < vecFiltrado.size()) & !insertado)
              {
              if(
                ((Tipo_Info)vecParaFiltrar.elementAt(i)).getHits() >
                ((Tipo_Info)vecFiltrado.elementAt(k)).getHits()
                )
              {
                if (vecParaFiltrar.elementAt(i) != null)
                {
                  vecFiltrado.insertElementAt(vecParaFiltrar.elementAt(i),k);
                  insertado=true;
                }
              }
              k++;
              }// while
          }// if no esta
       }//for i

/*
    }catch(IndexOutOfBoundsException i)
    {}
*/

/*
    //de momento sólo compactamos el vector y lo devolvemos
    for(int i=0;i<v_vecParaFiltrar.capacity();i++)
    {
      vecFiltrado.addAll( (Vector)v_vecParaFiltrar.elementAt(i) );
    }
*/
    //provisional
//    vecFiltrado=vecParaFiltrar;

    // borramos el elemento fantasma del final
    vecFiltrado.removeElementAt(vecFiltrado.size()-1);
    vecFiltrado.setSize(NUM_VENTANA_FILTRO);
    vecFiltrado.trimToSize();

/*    // eliminamos los elementos incongruentes
    vecFiltrado = eliminaIncorrectos (vecFiltrado);
*/
    return vecFiltrado;
  }

}