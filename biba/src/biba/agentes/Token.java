package biba.agentes;

/**
* @(#) Token.java
*/

/**
 * Clase que implementa los tokens y que proporciona m�todos �tiles para su
 * tratamiento. Un token es cualquier comando HTML encerrado entre �ngulos,
 * y cualquier texto no encerrado entre �ngulos. En particular cualquier etiqueta HTML
 * es un token, y se obtiene qued�ndose con la primera palabra de la etiqueta.
 * De esta forma, si en el futuro el HTML se enriquece con nuevos tags �stos podr�n
 * seguir siendo utilizados como tokens sin modificar el sistema. algunos de los tokens m�s
 * usados son (en notaci�n BNF):
 * <token> ::= num_hits | href | texto | any | br | p | font | /font  | input | /input
 * | small | /small | �
 */

import java.util.*;
import java.lang.*;

public class Token {

 /**
  * Tipo del token. Un token es cualquier comando HTML encerrado entre �ngulos,
  * y cualquier texto no encerrado entre �ngulos.
  */
  String tipo="";

 /**
  * Dato que transporta el token. Por ejemplo si el token es un token-texto en este
  * atributo estar�a el texto en s�.
  */
  String dato="";

 /**
  * Atributo auxiliar que se usa s�lo en el caso que sea un token-texto con un porcentaje.
  */
  int porcentaje=0;

 /**
  * Constructora. Recibe el token y un flag que nos dice si es una etiqueta HTML
  * o es un texto ascii. No es lo mismo el texto �b� que la etiqueta �<b>�.
  */
  public Token (String s, int es_texto) {
    String aux = s.trim();
    int pos_percentil=aux.indexOf('%');

    if (es_texto==1) {
    //puede ser un texto normal o puede ser un porcentaje
    //en este ultimo caso hay que tratarlo de una forma especial

      if ((pos_percentil>=1) && (pos_percentil<=3)) {
        try {porcentaje=Integer.parseInt(aux.substring(0,pos_percentil-1));}
        catch (NumberFormatException e) {}
        dato = aux.substring(0,pos_percentil-1); //String que representa el numero
        tipo="porcentaje";
      } else {
        tipo="texto";
        dato=s;
      }
    } else if (aux.startsWith("a href")||aux.startsWith("A HREF")){
      // Es un link!

      /* se ha modificado porque se capturaban mal los contenidos del href, se cog�a mal la longitud */
      tipo="href";
      int lon=aux.length();
      dato=aux.substring(7,lon/*-1*/);
    } else {
      StringTokenizer lista_tok = new StringTokenizer (aux," ",false);
      tipo=lista_tok.nextToken().toLowerCase();
    }
  }

 /**
  * M�todo consultor que devuelve el dato.
  */
  public String dato () {return dato;}

 /**
  * M�todo consultor que devuelve el tipo.
  */
  public String tipo () {return tipo;}

 /**
  * M�todo consultor que devuelve el porcentaje.
  */
  public int porcentaje() {return porcentaje;}


/**
 * Nos dice si el token es un token-texto con un n�mero de hits en su
 * interior. Por ejemplo si el dato es �Lycos devuelve 1400 enlaces�
 * este m�todo devuelve true.
 */
  public boolean esHits() {
    if (!tipo.equals("texto")) return false;
    boolean aux=false;
    for (int i=0;(i<dato.length()) && !aux ;i++) {aux=Character.isDigit(dato.charAt(i));}
    return aux;
  }

/**
 * Extrae el n�mero de hits del token actual. Es conveniente utilizar
 * el m�todo anterior para saber si hay algo que extraer. El sentido puede
 * ser izquierda (I) o derecha (D) y nos dice a partir de que extremo hay que buscar
 * tal n�mero.
 */
  public String hits(String sentido){
    boolean encontrado = false;
    String numero="";
    boolean salir = false;

    if (sentido.equals("izq")) {
	for (int i = 0; i < dato.length() && !salir; i++){
        boolean es_dig=Character.isDigit(dato.charAt(i));
	  if (es_dig) {
          encontrado=true;
          numero=numero+dato.charAt(i);
        } else {
          if (encontrado && (dato.charAt(i)!=',')) salir=true;
        }
	}
    } else {
        for (int i =dato.length()-1; i>=0 && !salir; i--){
        boolean es_dig=Character.isDigit(dato.charAt(i));
      if (es_dig) {
          encontrado=true;
          numero=dato.charAt(i)+numero;
        } else {
          if (encontrado && (dato.charAt(i)!=',')) salir=true;
        }
      }
    }

    return numero;
  }
}