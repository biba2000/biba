
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) Grupo SI
 * Company:
 * @author Daniel Vilches
 * @version 1.0
 */
package biba.utiles;

import biba.agentes.Tipo_Info;
import java.util.Vector;
public class EliminaRepeticiones {



  /**
   * Elimina las componentes que tienen la misma URL fundiéndolas en una
   *
   * Ademas, actualiza la puntuacion, quedándose con la mayor de las dos
   *
   * @param vectorInfo vector que modificamos compactando la información que hay en él
   */
  public  Vector elimina(Vector vectorInfo){

    Tipo_Info elemAct = null;
    Tipo_Info elemJ = null;
    int actual;
    double valorAct = 0;
    double valorJ = 0;

    actual = vectorInfo.size()-1;

    while (actual > 0)
    {
      elemAct = (Tipo_Info) vectorInfo.elementAt(actual);

      for (int j=actual-1; j >= 0;j--)
      {
        elemJ = (Tipo_Info) vectorInfo.elementAt(j);
        if ( elemAct.dame_url().equals(elemJ.dame_url()) )
        {
          //Hemos encontrado dos elem con igual url, los fundimos en uno
          elemAct.actualizaQuien(elemJ.dame_quien());
          vectorInfo.removeElementAt(j);

          // Nos quedamos con la puntuacion mayor de las dos
          valorAct = elemAct.getHits();
          valorJ = elemJ.getHits();
          if (valorJ > valorAct)
            elemAct.setHits(valorJ);

          // Al eliminar un elemento del vector, el elemento actual+1 pasa a ser el
          // elemento actual. Tenemos que decrementar el índice actual, para no
          // saltarnos el siguiente
          actual = actual -1;
        } // end if
      } // end for
      actual = actual -1;
    } // end while

  /*     Tipo_Info elemI = null;
     Tipo_Info elemJ = null;
     for (int i=0; i < (vectorInfo.size()-1); i++){

         elemI = (Tipo_Info) vectorInfo.elementAt(i);

         for (int j=i+1; j < vectorInfo.size();j++){
             elemJ = (Tipo_Info) vectorInfo.elementAt(j);
             if (elemI.dame_url().trim().equalsIgnoreCase(elemJ.dame_url().trim())){

                //Hemos encontrado dos elem con igual url, los fundimos en uno
                elemI.actualizaQuien(elemJ.dame_quien());
                vectorInfo.removeElementAt(j);

                // Al eliminar un elemento del vector el elemento j+1 pasa a ser el
                //elemento j-ésimo. Tenemos que decrementar el índice j, para no
                //saltarnos el siguiente
                j--;

             }

         }

     }*/
     return vectorInfo;

  }
  public static void main(String args[]){

         Vector vectorInfo = new Vector();
         vectorInfo.addElement(new Tipo_Info("http://java.sun.com","","",0,"yahoo"));
         vectorInfo.addElement(new Tipo_Info("http://java.sun.com","","",0,"google"));
         vectorInfo.addElement(new Tipo_Info("http://java.sun.com","","",0,"lycos"));
         vectorInfo.addElement(new Tipo_Info("http://java.sun.com/docs","","",0,"excite"));


         EliminaRepeticiones er = new EliminaRepeticiones();
         vectorInfo = er.elimina(vectorInfo);
         System.out.println(vectorInfo.toString());

  }

}