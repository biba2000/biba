package biba.agentes;

/**
* @(#) Tipo_Info.java
*/

import java.util.*;
import java.io.*;


/**
 * Clase que va a contener las URL, el resumen y el porcentaje de cada una de
 * las páginas que se elaboren como resultado de una búsqueda, ademas de un vector
 *  con todos los buscadores que lo han encontrado.
 */


public class Tipo_Info implements Serializable{

	/**
 	 * Url de la página.
	 */
	private String url;

	/**
 	 * Título de la página.
	 */
	private String titulo;

	/**
 	 * Resúmen de la página.
	 */
	private String resumen;

	/**
 	 * Puntuación obtenida por la página
	 */
	private int porcentaje;

/**
 * Guarda un vector con los nombres de los buscadores que han devuelto este enlace.
 */
	private Vector quien;

  /**
   * Guarda el número de hits a esa página para facilitar el filtrado
   */
  private double hits=0;

  public double getHits(){ return hits;}
  public void setHits(double hi){ hits = hi; }

	/**
	 * Constructor de la clase.
	 */
	public Tipo_Info() {
	    url="";
	    titulo="";
	    resumen="";
	    porcentaje=0;
	    quien=new Vector();
	}

	/**
	 * Otro constructor de la clase.
	 */
	public Tipo_Info (String inurl, String intitulo, String inresumen, int porcent,String inquien) {
	    url = inurl;
	    titulo = intitulo;
	    resumen = inresumen;
	    porcentaje = porcentaje;
	    quien=new Vector();
	    quien.addElement(inquien);
	}

	public String toString(){
	  String res="";
		res+="**************************************************************\n";
		res+= "URL:"+ this.url + " Titulo:" +this.titulo+"\n";
	  res+= "Resumen:" + this.resumen + "\nValoracion:" +this.hits+"\n";
		res+= "Buscadores:"+quien.toString()+"\n";
		res+="**************************************************************\n";

		return res;
	}
			 public void vacia()
      {
       url="";
       titulo="";
       resumen="";
       quien=new Vector();
      }


	public String dame_url() {
	  return url;
	}

	public String dame_titulo() {
	  return titulo;
	}

	public String dame_resumen() {
	  return resumen;
	}

	public int dame_porcentaje() {
	  return porcentaje;
	}

        public Vector vect_quien() {
          return quien;
        }

	/**
	 * A partir del atributo “quien”, devuelve una String con todos los nombres
	 * de los buscadores en una sola línea. Se utiliza para mostrarlo por pantalla.
	 */
	public Vector dame_quien() {

/*	String aux="";
	for (int i=0;i<quien.size();i++)
	  {
	   aux=aux+(String) quien.elementAt(i);
	 }
*/
	return quien;
	}

  /**
   * Añade un vector de Strings suministradores al existente eliminando las repeticiones
   */
   public void actualizaQuien(Vector nuevosSuminis)
   {
    for (int i=0;i<nuevosSuminis.size();i++)
    {
      String elem=nuevosSuminis.elementAt(i).toString();
      pon_quien(elem);
    }
   }

	 /**
	  * Devuelve el tamaño del vector “quien”.
	  */
       public int num_buscadores() {
         return quien.size();
        }

	 /**
	  * Este método devuelve un número decimal (cada nombre de buscador tiene
	  * asociado un número de 1 al 7) que representa al vector quien. Cada posición
	  * decimal representa a un buscador. Por ejemplo 127 significa que Yahoo,
	  * Altavista y Ole están en el vector “quien”.
	  */
       public int quien_numero() {
        int aux=0;
        for (int i=0;i<quien.size();i++)
        {
				  aux=aux*10;
          String nombre=(String) quien.elementAt(i);
          if (nombre.compareTo("Google")==0) aux=aux+1;
          if (nombre.compareTo("Excite")==0) aux=aux+2;
          if (nombre.compareTo("Lycos")==0) aux=aux+3;
					if (nombre.compareTo("Yahoo")==0) aux=aux+4;

        }
        return aux;
      }


	public void pon_url (String dato) {
	  url = dato;
	}

	public void pon_titulo (String dato) {
	  titulo = dato;
	}

	public void pon_resumen (String dato) {
	  resumen = dato;
	}

	public void pon_porcentaje (int dato) {
	  porcentaje = dato;
	}

	 /**
	  * Método auxiliar que nos dice si una cierta cadena  s está presente en el vector v.
	  */
       public boolean esta(String s,Vector v)
	 {
	  boolean encontrado=false;
	  int i=0;

	  while ((!encontrado) && (i<v.size()))
	  {
	   encontrado=(s.compareTo((String) v.elementAt(i)) == 0);
	   i++;
	  }

	  return encontrado;
	 }

	/**
	 * Añade un nuevo buscador al vector “quien” sin añadir repeticiones.
	 */
	public void pon_quien(String q) {
	  //Solo a¤adimos si no es una repeticion
	  if (!esta(q,quien))
	   quien.addElement(q);
	}

  /**
   * Comprueba si el TipoInfo es vacío
   */
  public boolean esVacio()
  {
    boolean dev=false;
    if ( url==null ) dev = true;
    return dev;
  }
	public void pon (String u, String t, String r, int p,String q) {
	  url = u;
	  titulo = t;
	  resumen = r;
	  porcentaje = p;
	  quien.addElement(q);
	}

	/**
	 * Duplica los valores del objeto en d1, que ya tiene que tener
	 * reservado espacio.
	 */
	public void duplica_en (Tipo_Info d1) {

	  d1.url = url;
	  d1.titulo = titulo;
	  d1.resumen = resumen;
	  d1.porcentaje = porcentaje;
	  for (int i=0;i<quien.size();i++) d1.quien.addElement(quien.elementAt(i));
	}

	public void ver () {
	   System.out.println ("URL:"+url+"\nTITULO:"+titulo+"\nRESUMEN: "+resumen+"\nQUIEN: "+quien.elementAt(0)+"\n");
	}
}
