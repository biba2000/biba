package biba.utiles;
/**
 * Title: Utilidades variadas para cadenas
 * Description:
 * Copyright:    Copyright (c) Jorge Glez
 * Company:
 * @author Jorge Glez
 * @version 2.0
 */

import java.lang.String;
import java.util.*;
import biba.persistencia.propiedades;
public final class UtilidadCadena {

	protected int debug=0;

  public UtilidadCadena() {

	   // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

  }

/**
 * elimina las apariciones de la cadena
 */
 	public String elimina2nbspAlFinal(String cad)
	{
		String auxcad="";
		int lon = cad.length();
		// eliminamos la subcadena &nbsp;&nbsp
		if (cad.endsWith("&nbsp;&nbsp;"))
		{
			//quitamos el prefijo que ocupa 11 chars
			auxcad = cad.substring(0,lon-12);
			return auxcad;
		}
		else
			return cad;

	}

	/**
	 * Separa una cadena de palabras separadas por un signo + en una lista de palabras
	 */
	 public Vector separaQuery(String query)
	 {
    StringTokenizer palabras=new StringTokenizer(query,"+");
    Vector respuesta=new Vector();
		String Token = "";
		try
		{
      while (palabras.hasMoreTokens())
      {
        Token = palabras.nextToken();
        respuesta.add(Token);
      } //while
		}
		catch (NoSuchElementException nsee)
		{
			System.err.println("WARNING:Intentó usar nexttoken con frase sin separadores apropiados");
		}
		return respuesta;
	 }


	/**
	 * Transforma todos los blancos de una cadena en la cadena que se le pase como argumento
	 */
	public String convierteCharEnString(String cadenaAConvertir,char elChar,String elString)
	{
		//cogemos todas las palabras de la búsqueda
//		String algo = Algo;
		String aux = "";
		aux+= elChar;
    StringTokenizer palabras=new StringTokenizer(cadenaAConvertir,aux);
    String respuesta="";
		String Token = "";
	  if (debug>0)System.out.println("Convierto de:"+cadenaAConvertir+" los:"+aux+": a:"+elString);

		try
		{
			if(cadenaAConvertir.startsWith(aux))
			{
				respuesta+=elString;
				Token=palabras.nextToken();
			}
		  else if(palabras.hasMoreTokens())Token=palabras.nextToken();
      respuesta += Token;
      // concatenamos todas las palabras con un "+" entre medias
      while (palabras.hasMoreTokens())
      {
        Token = palabras.nextToken();
        respuesta = respuesta + elString + Token;
      } //while
		}
		catch (NoSuchElementException nsee)
		{
			/*if(debug>0)*/System.err.println("WARNING:Intentó usar nexttoken con frase sin separadores apropiados");
		}


		return respuesta;
	}

        // utilizada por google para quitar caracteres extraños cuando es menester. Ej
        // /url?sa=U&start=1&q=http://javaboutique.internet.com/&e=42 la convierte en
        // http://javaboutique.internet.com/
        public static String extraeUrl( String cadenaRaw ) {

            if (cadenaRaw.regionMatches(true,0,"/url?sa=U&start=",0,15))
               return cadenaRaw.substring(20, (cadenaRaw.length()-5) );
            else
               return cadenaRaw;
        }


	public static void main( String args[] )
	{

          System.out.println(UtilidadCadena.extraeUrl("/url?sa=U&start=1&q=http://javaboutique.internet.com/&e=42"));
/*		UtilidadCadena uc = new UtilidadCadena();

		String c1="real madrid barça";
		String c2="  real madrid barça   ";
		String c3="+prop1+prop2";
		String c4="+prop1";

		String s1="";
		String s2="";
		String s3="";
		String s4="";
		System.out.println(uc.convierteCharEnString(c1,' ',"+"));
		System.out.println(uc.convierteCharEnString(c2.trim(),' ',"+"));
		System.out.println(uc.convierteCharEnString(c3,'+',"+OR+"));
		System.out.println(uc.convierteCharEnString(c4,'+',"+OR+"));
*/

	}

}