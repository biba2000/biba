package biba.estructurasDatos;


public class PlantillaCaracteristicasParticularesDeportes
             extends PlantillaCaracteristicasGenerales  implements java.io.Serializable{



  //atributos
  public boolean imprescindible=false;

  public boolean resultados=false;
  public boolean fotos=false;
  public boolean calendario=false;
  public boolean articulos=false;
  public boolean estadisticas=false;


  public PlantillaCaracteristicasParticularesDeportes(){
  this.resultados=false;
  this.fotos=false;
  this.calendario=false;
  this.articulos=false;
  this.estadisticas=false;

  }
  public PlantillaCaracteristicasParticularesDeportes
                                (boolean imprescindible,
                                 boolean sitiosWeb,
                                 boolean imagenes,
                                 boolean audioMp3,
                                 boolean noticias,
                                 boolean productos,
                                 boolean resultados,
                                 boolean fotos,
                                 boolean calendario,
                                 boolean articulos,
                                 boolean estadisticas){

    super(sitiosWeb,imagenes,audioMp3,noticias,productos);
    this.imprescindible=imprescindible;

    this.resultados=resultados;
    this.fotos=fotos;
    this.calendario=calendario;
    this.articulos=articulos;
    this.estadisticas=estadisticas;
    }

  /**
	 *	 devuelve un string que hay que añadir a la query según el valor
   *   de las plantillas particulares
	 */
  public String aniadirAquery() {

     String dev = "";

     if (this.resultados) dev = dev + "score";
     if (this.fotos) dev = dev + "pic";
     if (this.calendario) dev = dev + "date";
     if (this.estadisticas) dev = dev + "stats";

     return dev;
  }

  public String aniadirAquerySeparadoPorMas() {

     String dev = "";

     if (this.resultados) dev = dev + "+score";
     if (this.fotos) dev = dev + "+pic";
     if (this.calendario) dev = dev + "+date";
     if (this.estadisticas) dev = dev + "+stats";

     return dev;
  }
}