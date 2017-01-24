package biba.estructurasDatos;

public class PlantillaCaracteristicasParticularesMusica
       extends PlantillaCaracteristicasGenerales  implements java.io.Serializable{

  //atributos
  public boolean letras=false;
  public boolean conciertos=false;
  public boolean paginasOficiales=false;
  public boolean discografia=false;

  //constructor
  public PlantillaCaracteristicasParticularesMusica(){

  //atributos
  this.letras=false;
  this.conciertos=false;
  this.paginasOficiales=false;
  this.discografia=false;



  };
  public PlantillaCaracteristicasParticularesMusica
                                (boolean sitiosWeb,
                                 boolean imagenes,
                                 boolean audioMp3,
                                 boolean noticias,
                                 boolean productos,
                                 boolean letras,
                                 boolean conciertos,
                                 boolean paginasOficiales,
                                 boolean discografia){

    super(sitiosWeb,imagenes,audioMp3,noticias,productos);
    this.letras=letras;
    this.conciertos=conciertos;
    this.paginasOficiales=paginasOficiales;
    this.discografia=discografia;


 }
 /*
 public boolean[] devuelveValorCaracteristicasParticulares() {
     boolean[] arrayBoolean = new boolean[4];
     arrayBoolean[0] = this.letras;
     arrayBoolean[1] = this.conciertos;
     arrayBoolean[2] = this.paginasOficiales;
     arrayBoolean[3] = this.discografia;
     return arrayBoolean;
  }
  */

  /**
	 *	 devuelve un string que hay que añadir a la query según el valor
   *   de las plantillas particulares
	 */
  public String aniadirAquery() {

     String dev = "";

     if (this.letras) dev = dev + "lyrics";
     if (this.conciertos) dev = dev + "concerts tour";
     if (this.paginasOficiales) dev = dev + "official site";
     if (this.discografia) dev = dev + "discography";

     return dev;
  }

  // devuelve un string que hay que añadir a la query según el valor
  // de las plantillas particulares. Es igual que añadirAquery
  // pero devuelve las palabras separadas por el signo +
  public String aniadirAquerySeparadoPorMas() {

     String dev = "";

     if (this.letras) dev = dev + "+lyrics";
     if (this.conciertos) dev = dev + "+concert+tour";
     if (this.paginasOficiales) dev = dev + "+official+site";
     if (this.discografia) dev = dev + "+discography";

     return dev;
  }

}