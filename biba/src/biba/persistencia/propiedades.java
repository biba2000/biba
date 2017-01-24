package biba.persistencia;

import java.util.Properties;
import java.io.FileInputStream;

public final class propiedades {

  public static String SEPARADOR_PATH = java.io.File.pathSeparator;

  // IMPORTANTE
  // IMPORTANTE
  // IMPORTANTE
  // IMPORTANTE
  //
  //La localización del fichero de configuración es dependiente de la máquina
  //servidora donde se intale el sistema BIBA
  public static String FICHERO_CONFIGURACION = "C:\\Java\\Apache13\\Tomcat32\\webapps\\biba\\WEB-INF\\classes\\biba\\persistencia\\bibaConfig.txt";

  public propiedades() {

  }

  public static String getPropiedad(String Key){

     Properties propiedades= new Properties();
     try{
          propiedades.load(new FileInputStream(FICHERO_CONFIGURACION));

     }catch (java.io.IOException e){
        System.out.println("No se ha encontado el fichero de configuracion de biba.\nComprueba que "+FICHERO_CONFIGURACION + " está en el directorio: biba\\persistencia");
     }

     return propiedades.getProperty(Key);

  }


}