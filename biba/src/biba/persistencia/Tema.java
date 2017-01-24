package biba.persistencia;


import java.io.*;
import java.util.*;
import biba.estructurasDatos.*;
import biba.agentes.Tipo_Info;

public class Tema implements Serializable {
	/* ATRIBUTOS */

  // se pondra a true cuando supere un ciero umbral
  //la densidad de búsqueda
  private boolean activoEnSesion=false;
	private float densidad=-1;
	private int num_busquedas=0;
	private boolean imprescindible=false;
  // número de hotlinks que se considerarán
  private final static int NUM_HOTLINKS=3;

  // tendremos una cola de hotlinks (vectores de Tipo_Info)
	private Vector hotlinks;

  private PlantillaCaracteristicasGenerales plantilla=null;
  private Calendar calendario = new GregorianCalendar();
  private int debug;
  private long minutosPrimeraBusqueda = -1;
	/* METODOS */
	/* Funciones para obtener los atributos */

  public Tema(PlantillaCaracteristicasGenerales plantilla) {
     this.plantilla = plantilla;
     if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;
     hotlinks=new Vector();
  }

  public boolean getActivoEnSesion(){return this.activoEnSesion;}
  public void setActivoEnSesion(boolean activar){this.activoEnSesion=activar;}
  public PlantillaCaracteristicasGenerales getPlantilla(){
    return this.plantilla;
  }
	public float getDensidad(){
		return densidad;
	}


	public int getNumeroBusquedas(){
		return num_busquedas;
	}

	public boolean getEsImprescindible(){
		return imprescindible;
	}
  public boolean getNuncaUsadoEnUnaBusqueda(){
         return this.num_busquedas==0;
  }

  //recibe el nombre del tema según aparece en el interfaz de usuario, devuelve
  //el identificador asociado
  public static String convierteTema(String tema){

     if("General".equals(tema))     return "general";
     if("Informática".equals(tema)) return "informatica";
     if("Música".equals(tema))      return "musica";
     if("Deportes".equals(tema))    return "deportes";
     if("Viajes".equals(tema))      return "viajes";
     return "error";
  }
	/* Funciones para modificar los atributos */

	public void calculaDensidad(){

    Date dateActual = new Date();
    calendario.setTime(dateActual);

    int anioAct, mesAct, diaAct, horaAct, minAct, secAct;

    long diferenciaFechas;
    long minutosFechaActual;

    calendario.setTime(dateActual);


    anioAct = (calendario.get(Calendar.YEAR)) % 100;
    mesAct = calendario.get(Calendar.MONTH) +1;
    diaAct = calendario.get(Calendar.DAY_OF_MONTH);
    horaAct = calendario.get(Calendar.HOUR_OF_DAY);
    minAct = calendario.get( Calendar.MINUTE );

    // minutos que tiene un año: 525600
    // minutos que tiene un mes: 43200
    // minutos que tiene un día: 1440

    minutosFechaActual = anioAct * 525600 + mesAct * 43200 + diaAct * 1440 +
                  horaAct * 60 + minAct;

    diferenciaFechas = minutosFechaActual - this.minutosPrimeraBusqueda;

    if (debug>0) System.out.println( "Diferencia fechas: " + diferenciaFechas );

    if (diferenciaFechas == 0) diferenciaFechas = 1;

    this.densidad = (float)(this.num_busquedas * 526000 )/ (float)diferenciaFechas;


    if (debug>0) System.out.println( "minutosFechaActual: "+ minutosFechaActual +
    "\nDiferenciaFechas: " + diferenciaFechas + "\nDensidad: " + this.densidad);
	}



	public void setUnaBusquedasMas(){


      Date dateActual = null;
      int anioPrimeraBusqueda, mesPrimeraBusqueda,
          diaPrimeraBusqueda, horaPrimeraBusqueda, minutoPrimeraBusqueda;

      //Si es la primera vez que buscamos en este tema
      // cogemos fecha actual y la almacenamos como fecha de
      // la primera búsqueda
      if( this.num_busquedas==0 ) {

        dateActual = new Date();
        calendario.setTime(dateActual);
        anioPrimeraBusqueda = (calendario.get(Calendar.YEAR)) % 100;
        mesPrimeraBusqueda = calendario.get(Calendar.MONTH) +1;
        diaPrimeraBusqueda = calendario.get(Calendar.DAY_OF_MONTH);
        horaPrimeraBusqueda = calendario.get(Calendar.HOUR_OF_DAY);
        minutoPrimeraBusqueda = calendario.get( Calendar.MINUTE );

        // minutos que tiene un año: 525600
        // minutos que tiene un mes: 43200
        // minutos que tiene un día: 1440
        this.minutosPrimeraBusqueda = (anioPrimeraBusqueda * 525600) +
                            mesPrimeraBusqueda * 43200 +
                            diaPrimeraBusqueda * 1440 +
                            horaPrimeraBusqueda * 60 +
                            minutoPrimeraBusqueda;
       }
       if (debug>0) System.out.println( "Minutos primera busqueda: " + this.minutosPrimeraBusqueda );
     	 num_busquedas++;
	}


  public void setNumBusquedas( int n ) {
     num_busquedas = n;
  }

	public void setImprescindible( boolean b ){
		imprescindible = b;
	}
  public void setPlantilla (PlantillaCaracteristicasGenerales plantilla){
    this.plantilla=plantilla;
  }

  public Vector getHotlinks()
  {
    return hotlinks;
  }

  public void actualiza_hotlinks ( Tipo_Info info )
  {
    // comprobar que esté ya
    String titulo=info.dame_titulo();
    String url=info.dame_url();

    boolean esta=false;
    System.out.println(hotlinks.size());
    Tipo_Info h = info;
    for (int i=0;i<hotlinks.size();i++)
    {
      if
      (
      (((Tipo_Info)hotlinks.elementAt(i)).dame_url()).equals(h.dame_url())
      )
        esta=true;
    }

    if (!esta)
    {
      if(hotlinks.size()<NUM_HOTLINKS) hotlinks.insertElementAt(h,0);
      else
      {
        for(int k=NUM_HOTLINKS-1;k>0;k--)
        {
          hotlinks.setElementAt(hotlinks.elementAt(k-1),k);
        }
        hotlinks.setElementAt(h,0);
      }
    }

    if (debug==1) for(int t=0;t<hotlinks.size();t++) System.out.println(((Tipo_Info)hotlinks.elementAt(t)).dame_titulo()+" "+((Tipo_Info)(hotlinks.elementAt(t))).dame_url()+"\n");
  }



  public static void main( String args[] ) {

     Tema t = new Tema(new PlantillaCaracteristicasParticularesInformatica());
     t.setUnaBusquedasMas();
     t.calculaDensidad();
     Tipo_Info i = new Tipo_Info("url","tit","resum",50,"yo");

     i.pon_url("url1");
     i.pon_titulo("url1");
     t.actualiza_hotlinks(i);
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_url("url2");
     i.pon_titulo("url2");
     t.actualiza_hotlinks(i);
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_url("url3");
     i.pon_titulo("url3");
     t.actualiza_hotlinks(i);
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_url("url4");
     i.pon_titulo("url4");
     t.actualiza_hotlinks(i);
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_url("url5");
     i.pon_titulo("url5");
     t.actualiza_hotlinks(i);
     System.out.println("Ahora\n");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     i = new Tipo_Info("url","tit","resum",50,"yo");
     i.pon_titulo("url6");
     i.pon_url("url6");
     t.actualiza_hotlinks(i);


  }

}