package biba.persistencia;

import java.io.*;
import java.util.*;


/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 2
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo SI)=(Emilio Bobadilla,Juan Gallardo,Jorge Glez.,Daniel Vilches)
 * @version 1.1
 */

public class TablaBuscadores extends Hashtable implements Serializable {

  public final static String rutaTablaBuscadores = propiedades.getPropiedad ("RUTA_TABLA_BUSCADORES");


  // Constantes para el control de la valoracion de los buscadores

  public final static double  PREMIO = 0.05;
  public final static double  CASTIGO = 0.01;
  public final static double  VAL_MAX = 2.0;
  public final static double  VAL_MIN = 0.5;


	protected int debug=0;

  /* METODOS */

  /* Constructor */
  public TablaBuscadores() {

    // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

    Hashtable tablaTemas1,tablaTemas2,tablaTemas3,tablaTemas4;
    // Creamos una tabla hash indexada por los temas conteniendo la info.
    tablaTemas1 = new Hashtable();
    tablaTemas1.put("general",new InfoBuscador());
    tablaTemas1.put("informatica",new InfoBuscador());
    tablaTemas1.put("musica",new InfoBuscador());
    tablaTemas1.put("deportes",new InfoBuscador());
    tablaTemas1.put("viajes",new InfoBuscador());

    tablaTemas2 = new Hashtable();
    tablaTemas2.put("general",new InfoBuscador());
    tablaTemas2.put("informatica",new InfoBuscador());
    tablaTemas2.put("musica",new InfoBuscador());
    tablaTemas2.put("deportes",new InfoBuscador());
    tablaTemas2.put("viajes",new InfoBuscador());

    tablaTemas3 = new Hashtable();
    tablaTemas3.put("general",new InfoBuscador());
    tablaTemas3.put("informatica",new InfoBuscador());
    tablaTemas3.put("musica",new InfoBuscador());
    tablaTemas3.put("deportes",new InfoBuscador());
    tablaTemas3.put("viajes",new InfoBuscador());

    tablaTemas4 = new Hashtable();
    tablaTemas4.put("general",new InfoBuscador());
    tablaTemas4.put("informatica",new InfoBuscador());
    tablaTemas4.put("musica",new InfoBuscador());
    tablaTemas4.put("deportes",new InfoBuscador());
    tablaTemas4.put("viajes",new InfoBuscador());

    // Para cada buscador tendremos una tabla de Temas
    put("Google",tablaTemas1);
    put("Excite",tablaTemas2);
    put("Yahoo",tablaTemas3);
    put("Lycos",tablaTemas4);
//		if (debug>0) System.out.println("Antes de mostrar");
//    if (debug>0) System.out.println("TablaBuscadores:Creando tabla"+toString());
  }

  /**
  * Devuelve la ponderación actual de un buscador para un determinado tema
  */
  public synchronized double getValoracion(String buscador, String tema) {
    double aux=0;

    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    // Actualizamos la valoracion
    InfoBuscador ib=null;
    Hashtable ht = (Hashtable) get(buscador);
    if (ht!=null)
      ib = (InfoBuscador) ht.get(tema);
    else
      if (debug>0) System.err.println("TablaBuscadores:HashTable era null al buscar:"+buscador);

    if (ib!=null)
      aux = ib.getValoracion();
    else
      if (debug>0) System.err.println("TablaBuscadores:InfoBuscador era null al buscar por:"+tema);

    if (aux < biba.persistencia.TablaBuscadores.VAL_MIN) {
      if (debug>0) System.err.println("ERROR: Se accedio a la tabla de buscadores con nombre de buscador erróneo o tema erróneo");
      aux = biba.persistencia.TablaBuscadores.VAL_MIN;
    }
    return aux;
  }


  /* Esta función actualiza la información referente a la valoración (premia)
     y al nº de clicks del buscador en el tema */
  public synchronized void premiaBuscador (String buscador, String tema) {
    double  valoracion;

    if (debug>0) System.out.println("TablaBuscadores:Intento premiar a:"+buscador+" en tema:"+tema);
    if (debug>0) System.out.println("TablaBuscadores:Estado antes de premiar"+toString());

    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    if(this.containsKey(buscador)) {
      // Actualizamos nº de clicks
      ((InfoBuscador)
       ((Hashtable)this.get(buscador)).get(tema)
      ).unClickMas();

      // Actualizamos la valoracion
      valoracion = ((InfoBuscador)
                    ((Hashtable)this.get(buscador)).get(tema)
                   ).getValoracion();
      valoracion = valoracion + PREMIO;
      if (valoracion > VAL_MAX) valoracion = VAL_MAX;
      ((InfoBuscador)
       ((Hashtable)this.get(buscador)).get(tema)
      ).setValoracion(valoracion);
    }
    else {
      if (debug>0) System.err.println("ERROR: Se intento premiar buscador:"+buscador+" en la clase TablaBuscadores");
    }

    // Guarda la tabla en archivo
    biba.persistencia.AccesoBD.guardarTabla(this);

    if (debug>0) System.out.println("TablaBuscadores:Estado después de premiar"+toString());
  }

  /**
  *  Esta función actualiza la información referente a la valoración (restando)
  *  del buscador en el tema
  */
  public synchronized void penalizaBuscador (String buscador, String tema) {
    double  valoracion;

    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    if (debug>0) System.out.println("TablaBuscadores:Intento penalizar a:"+buscador+" en tema:"+tema);
    if (debug>0) System.out.println("TablaBuscadores:Estado antes de penzalizar"+toString());

    if(this.containsKey(buscador)) {
      valoracion = ((InfoBuscador)
                    ((Hashtable)this.get(buscador)).get(tema)
                   ).getValoracion();
      valoracion = valoracion - CASTIGO;
      if (valoracion < VAL_MIN) valoracion = VAL_MIN;
      ((InfoBuscador)
       ((Hashtable)this.get(buscador)).get(tema)
      ).setValoracion(valoracion);

      if (debug>0) System.out.println("TablaBuscadores.penalizaBuscador, penalizado buscador: " + buscador + " en tema: "+ tema + "Nuevo Valor: "+valoracion);
    }
    else {
      if (debug>0) System.err.println("ERROR: Se intento penalizar buscador:"+buscador+" en la clase TablaBuscadores");
    }

    // Guarda la tabla en archivo
    biba.persistencia.AccesoBD.guardarTabla(this);

    if (debug>0) System.out.println("TablaBuscadores:Estado después de penzalizar"+toString());
  }

  /* Funcion que devuelve la valoracion del buscador en el tema */
  public synchronized double valor (String buscador, String tema) {

    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    return ((InfoBuscador)
            ((Hashtable)this.get(buscador)).get(tema)
           ).getValoracion();
  }

  /**
  * Premia a los buscadores que nos han proporcionado una busqueda satisfactoria para el usuario
  */
  public synchronized void actualizaClick(String tema, Vector listaBusca) {
    for(int i=0;i<listaBusca.size();i++) {
      premiaBuscador((String)listaBusca.elementAt(i),tema);
    }
  }



  /**
  * Actualiza la información de la tabla de buscadores tras cada búsqueda
  */
  public synchronized void setUnaBusquedaMas(String tema) {

    // penalizamos a todos los buscadores con cada búsqueda

    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    String buscadores[]={"Google","Excite","Yahoo","Lycos"};
    for (int i = 0; i <= 3; i++) {
      penalizaBuscador(buscadores[i],tema);
    }

    // Guarda la tabla en archivo
    biba.persistencia.AccesoBD.guardarTabla(this);
  }


  public String[] getBuscadoresOrdenados( String tema ) {

    String dev[] = new String[4];
    parejaBuscadorValoracion val[] = new parejaBuscadorValoracion[4];
    val[0]=new parejaBuscadorValoracion();
    val[1]=new parejaBuscadorValoracion();
    val[2]=new parejaBuscadorValoracion();
    val[3]=new parejaBuscadorValoracion();

    String busc[]={"Google","Excite","Yahoo","Lycos"};

    // inicializamos
    for (int i = 0;i<4;i++) {
      if (debug>0) System.out.println("getBuscadoresOrdenados:tema:"+tema);
      val[i].buscador = busc[i];
      if (debug>0) System.out.println("getBuscadoresOrdenados:buscador:"+val[i].buscador);
      val[i].valoracion = getValoracion(busc[i],tema);
      if (debug>0) System.out.println("getBuscadoresOrdenados:valorac:"+getValoracion(val[i].buscador,tema));
    }

    // ordenamos
    Arrays.sort(val,new parejaBuscadorValoracion());

    for (int i = 0; i<4; i++)
		{
			dev[i] = val[i].buscador;
      if (debug>0) System.out.println("Buscad en puesto "+i+" es:"+dev[i]);
		}

    return dev;
  }

  public String toString() {
    String ret = "";
    String busc[]={"Google","Excite","Yahoo","Lycos"};
    String tema[]={"general","informatica","musica","deportes","viajes"};

    ret +="\nTablaBuscadores:";
    for (int i = 0;i<4;i++) {
      for (int j=0;j<5;j++) {
        ret +="\nBuscador:"+busc[i]+" Tema:"+tema[j]+" Valoracion:"+getValoracion(busc[i],tema[j]);
      }
    }

    return ret;
  }

  public class parejaBuscadorValoracion implements Comparator {

    public String buscador="";
    public double valoracion=0;

    /**
     * Compara dos elementos de la clase
     * return <0 si o1<o2 <br> ==0 sis o1 == o2 <br> >0 si o1>02
     *
     */
     public int compare(Object o1, Object o2) {
      int aux = 0;
      double v1 = ((parejaBuscadorValoracion) o1).valoracion;
      double v2 = ((parejaBuscadorValoracion) o2).valoracion;

      if (v1 >  v2) aux = -1;
      if (v1 == v2) aux =  0;
      if (v1 <  v2) aux =  1;

      return aux;
    }

    public boolean equals(Object obj){
      return (this.valoracion == ((parejaBuscadorValoracion) obj).valoracion);
    }

  }

  /* Genera un archivo de texto con la tabla de buscadores */
  public void imprimeTablaBuscadores() {
    // Carga la tabla desde archivo
    clear();
    putAll(biba.persistencia.AccesoBD.cargarTabla());

    String ficheroOut = rutaTablaBuscadores + "tbuscadores.txt";
    try {
      System.setOut(new PrintStream(new FileOutputStream(ficheroOut),true));
      System.out.println("TABLA DE BUSCADORES:");
      System.out.println("--------------------");
      System.out.println();
    } catch (FileNotFoundException e){
      System.out.println("No se ha encontrado: "+ficheroOut);
      e.printStackTrace();
    }

    System.out.println(this.toString());
    System.out.close();
  }


  public static void main( String args[] ) {

/*    TablaBuscadores tab = new TablaBuscadores();

		Vector busc;
    busc = new Vector();
    busc.addElement("Lycos");
    busc.addElement("Yahoo");
    //tab.setUnaBusquedaMas("general");
    //tab.setUnaBusquedaMas("general");
    //tab.setUnaBusquedaMas("informatica");
    tab.actualizaClick("informatica",busc);
    tab.actualizaClick("informatica",busc);
*/
		TablaBuscadores tab = biba.persistencia.AccesoBD.cargarTabla();
    tab.imprimeTablaBuscadores();

  }

}