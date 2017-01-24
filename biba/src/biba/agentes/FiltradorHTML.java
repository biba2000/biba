package biba.agentes;

/**
* @(#)Filtrador.java
*/


/**
 *
 * La clase Filtrador es un automata de estados que se encarga de filtrar las
 * paginas devueltas por los buscadores. La constructora del objeto filtrador
 * lee un archivo de texto en un cierto pseudolenguaje (LIA : Lenguaje de
 * implementacion de automatas) y genera la funcion de transicion
 *
 */


import java.io.*;
import java.util.*;

public class FiltradorHTML {

  /**
   * Estado actual del automata
   */
  int state = 0;
  int flag_texto = 1;
  int flag_fin = 0;
  String RUTA_FILTROS=biba.persistencia.propiedades.getPropiedad("RUTA_FILTROS");
  String href = "";
  String titulo = "";
  String comentario = "";
  NumHits n_hits;
  int porcentaje = 50;
  String buscador ="";
  Vector datos_filtrados = new Vector ();
  public String cadena_de_parada ="#none#";

  /**
   *
   * Implementa la funcion de transicion. Cada posición representa un estado
   * y contiene una tabla hash con pares token-vector de acciones.
   *
   */
  Vector transicion;

  /**
   *
   * Consulta la lista de acciones asociada a un cierto token en un cierto
   * estado del automata
   *
   * @param token es el token
   *
   */
  private Vector consulta (Token token) {

    Hashtable h = (Hashtable) transicion.elementAt(state);
    Vector aux=new Vector();       // vector de acciones asociadas al token
    if (token.tipo().equals("texto") && h.containsKey("num_hits") && token.esHits()){
      aux=(Vector) h.get("num_hits");
    } else {
      if (h.containsKey(token.tipo())) {aux=(Vector) h.get(token.tipo());}
      else {aux=(Vector) h.get("any");}
    }
    return aux;

  }
  /**
   *
   * Visualiza el automata
   *
   */
  public void ver() {
    for ( int i = 0; i < transicion.size(); i++){
      Hashtable h = (Hashtable) transicion.elementAt(i);
	System.out.println("Estado "+i);
      System.out.println(h.toString());
    }
  }




  /**
   *
   * Este metodo recibe como entrada un Stream de Datos y lo filtra segun el
   * automata de estados definido durante la construccion del objeto Filtrador
   *
   * @ param d Stream de datos a filtrar
   *
   */

  public Vector filtra (DataInputStream d) throws Exception {

    //ver();

    //Convertirla en cadena

    String linea=d.readLine();
    String pagina = "";

    while ((linea != null)&&(linea!=cadena_de_parada)) {

	pagina=pagina+linea;
	linea = d.readLine();
    }

    //Dividimos pagina en tokens
    StringTokenizer lista_tokens = new StringTokenizer (pagina,"<>",true);

    //la razon de que el tercer parametro este a true es que nos interesa conservar los
    //simbolos "<" y ">" para distinguir entre tokens de etiqueta y de texto.

    flag_texto=1; //sirve para distinguir entre tokens_tag y tokens_texto
    String token ="";
    //Analizamos cada token y actuamos segun el vector de transcion
    while (lista_tokens.hasMoreTokens()) {
      token = lista_tokens.nextToken();
      if (token.equals("<")) {
        flag_texto = 0; //el token siguiente no es un texto
      } else if (token.equals(">")) {
        flag_texto = 1; //a menos que llegue "<" el token siguiente sera un texto
      } else {
        //token *no* es un separador, asi que lo procesamos
        procesa(token);
      }

    }

	System.out.println(n_hits.numHits);
    datos_filtrados.insertElementAt((NumHits)n_hits,0);

   return datos_filtrados;
  }



  /**
   *
   * Procesa una palabra segun el dise~o del automata
   * y cambia el estado interno del mismo
   *
   * @param palabra Es la palabra a procesar
   *
   */
  private void procesa (String palabra) {
    Token token = new Token(palabra,flag_texto);
    Vector acciones = consulta(token);
    for (int i=0; i<acciones.size(); i++) ejecuta((String) acciones.elementAt(i),token);
  }



  /**
   *
   * Ejecuta una accion definida en el automata
   *
   */

  private void ejecuta (String accion,Token token) {
    //Miramos si es una transicion de estados
    int nuevo_estado=0;
    try {
     nuevo_estado=Integer.parseInt(accion);
     if ( state >0 )
       if ( state != nuevo_estado )
         System.out.println("Estado " + state + "  Ejecutando token: " + token.tipo() + " y accion " + accion + " con dato $" + token.dato()+"$" );

     state=nuevo_estado;
    } catch (NumberFormatException e) {
      //no es un numero, luego debe ser una orden
      if (accion.equalsIgnoreCase("captura_href"))
      {href=token.dato().trim();
       href = biba.utiles.UtilidadCadena.extraeUrl(href);}
      else if (accion.equalsIgnoreCase("captura_titulo"))
      {titulo=token.dato();}
      else if (accion.equalsIgnoreCase("concatena_titulo"))
      {titulo=titulo+token.dato();}
      else if (accion.equalsIgnoreCase("captura_comentario"))
      {comentario=token.dato();}
      else if (accion.equalsIgnoreCase("concatena_comentario"))
      {comentario=comentario+token.dato();}
      else if (accion.equalsIgnoreCase("captura_porcentaje"))
      {porcentaje=token.porcentaje();}
      else if (accion.equalsIgnoreCase("reset_titulo"))
      {titulo="";}
      else if (accion.equalsIgnoreCase("reset_comentario"))
      {comentario="";}
      else if (accion.equalsIgnoreCase("genera_dato")) {
        Tipo_Info dato = new Tipo_Info (href, titulo, comentario, porcentaje,buscador);
        datos_filtrados.addElement(dato);
      }
      else if (accion.equalsIgnoreCase("captura_hits")) {
        String cadHits= token.hits("izq");
        int numero=0;
        try {numero=Integer.parseInt(cadHits);} catch (Exception ex) {
           System.out.println(ex);
        }
	  n_hits.numHits = numero;
      }
	else if (accion.equalsIgnoreCase("captura_hitsD")) {
        String cadHits= token.hits("der");
        int numero=0;
        try {numero=Integer.parseInt(cadHits);}
	  catch (Exception ex) {
           System.out.println(ex);
        }
	  n_hits.numHits = numero;
      }
      else if (accion.equalsIgnoreCase("fin")) {flag_fin=1;}
    }
  }



//---------------- CONSTRUCTORA -----------------------------------



  /**
   *
   * Constructora: Recibe un fichero en LIA y construye el
   * automata finito correspondiente
   *
   * @param fichero Cadena de caracteres con el nombre del ficero
   *
   */
  public  FiltradorHTML (String nombre_buscador,String nombre_filtro) throws Exception {
    String datos_filtro;
    int size;
    buscador=nombre_buscador;
    n_hits=new NumHits (buscador,0);
    datos_filtro = "";
    InputStream f1 = new FileInputStream(RUTA_FILTROS+nombre_filtro+".filtro");
    size = f1.available();

    for(int i = 0; i < size; i++){

       datos_filtro = datos_filtro+((char)f1.read());
    }

    f1.close();


    // Incicializamos atributo transicion.

    transicion = new Vector();

    //A partir de este punto en datos_filtro tenemos toda la cadena del fichero

    StringTokenizer tokens_filtro = new StringTokenizer (datos_filtro, ">,; \t\r\n", true);

    /************************************************************************************/
    //estado 0 -> Esta esperando la palabra clave "estado"
    //estado 1 -> Ha aparecido "estado" y esperamos el numero de estado
    //estado 2 -> Despues del numero de estado viene un token
    //estado 3 -> Tiene que llegar un ">"
    //estado 4 -> Llegan las acciones seperadas por "," Se sale de este estado con ";"
    /************************************************************************************/

    // variables auxiliares del while


    String cadena = "";
    int estado = 0; //el parser lo vamos a implementar como una maquina de estados
    int s = 0;
    Vector acciones = new Vector();
    String tok = "";
    int flag_any = 0;
    int flag_FIN = 0;
    Hashtable haux = new Hashtable ();

    //bucle principal del metodo

    while (tokens_filtro.hasMoreTokens()&& (flag_FIN==0)) {
       cadena = tokens_filtro.nextToken();
       while (cadena.equals(" ")||cadena.equals("\n")||cadena.equals("")||
              cadena.equals("\r")||cadena.equals("\t")) {
         if (tokens_filtro.hasMoreTokens()) {cadena = tokens_filtro.nextToken();} //desechamos los tokens " " y "/n"
         else cadena = "STOP";
       }

       /* pruebas*/

  /*     System.out.println("haux:");
        System.out.println(haux.toString());
        System.out.println("Estado = "+estado+"  Token = ["+cadena+"]");
  */
       if (!cadena.equals("STOP")) {
         switch (estado) {
           case 0:
             if (!cadena.equals("estado"))
               {System.out.println("Error en estado 0. Se esperaba 'estado' ");}
             else estado = 1;
           break;
           case 1:
             try {
               s = Integer.parseInt(cadena);
               estado = 2;
             }
	       catch ( NumberFormatException e) {
               System.out.println("Error en estado 1. Se esperaba número ");
             }

           break;
           case 2:
             if ((!cadena.equals("estado")) && (!cadena.equalsIgnoreCase("FIN")) ) {
               tok = cadena.toLowerCase();
               if (tok.equalsIgnoreCase("any")) flag_any = 1;
               estado = 3;
             } else {
               if (flag_any == 0)  {
                 haux.put ("any",new Vector());
               }
               transicion.addElement(haux); //OJO a lo mejor hay que hacer un clone()
               haux = new Hashtable (); //inicializamos la tabla hash auxiliar
               estado = 1;
               if (cadena.equalsIgnoreCase("FIN")) flag_FIN=1; //para saber si hemos terminado
             }
           break;
           case 3:
             if (!cadena.equals(">"))
		   {System.out.println("Error en estado 3. Se esperaba un '>'");}
		 else estado = 4;
           break;
           case 4:
		 if ((!cadena.equals(";")) && (!cadena.equals(","))) {
               acciones.addElement(cadena);
             }
             else if (cadena.equals(";")) {
               haux.put ((String) tok,acciones.clone());
               acciones= new Vector ();
               tok="";
               estado = 2;
             }
         }
       }
    }

    //ver();

  } //fin del constructor

}

