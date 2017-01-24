package biba.visualizador;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Date;
import java.util.Vector;
import biba.persistencia.InfoUsuario;
import biba.persistencia.propiedades;
import biba.estructurasDatos.*;
import biba.utiles.servlets.InfoServlet;
import biba.utiles.servlets.ErrorServletRegistro;


public class Gestor {

  private String rutaHTML="";
  private String rutaJsp="";
  private int debug=0;

  public Gestor() {
     rutaHTML = propiedades.getPropiedad("RUTA_HTML");
     rutaJsp  = propiedades.getPropiedad("RUTA_JSP");
     if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

  }

  //La sesi�n clientes-servidor se ha ca�do o el tiempo m�ximo de vida
  // de sesi�n ha expirado.

  public void errorNoHaySesion(HttpServletResponse response){

    try{

        response.sendRedirect(rutaHTML+"errorNoHaySesion.html");

    }catch(java.io.IOException e){
      e.printStackTrace();
    }


  }

  //Error gen�rico dado por mensaje
  public void error(String mensaje,InfoServlet infoSer)throws ServletException, IOException{

    //Le pasamos al JSP el mensaje de error a presentar
    infoSer.getSession().setAttribute("mensajeError",mensaje);

    String url=rutaJsp+"error.jsp";
    if (debug>0)System.out.println("biba.visualizador.error,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());

  }

   //Error al registrarse el usuario
   public void errorRegistroUsuario(String nombreErroneo,ErrorServletRegistro error,InfoServlet infoServlet )throws ServletException, IOException{

         if(error.getHayErrores() && error.getErrorNombreRepetido())

             error("Lo sentimos pero el nombre :"+nombreErroneo+" ya est� en uso.",infoServlet);

         else

           System.out.println("biba.visualizador.errorRegistroUsuario. NO existen errores en el registro,NO tiene sentido invocar este m�todo");

  }
  //Error al introducir la contrase�a, no es la correcta
   public void errorUsuarioContrasenaIncorrecta(InfoServlet infoSer )throws ServletException, IOException{

    //Le pasamos al JSP el mensaje de error a presentar
    infoSer.getSession().setAttribute("mensajeError","Error: Contrase�a incorrecta.");

    String url=rutaJsp+"error.jsp";
    if (debug>0)System.out.println("biba.visualizador.error,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());





  }

  /**
   * Produce una p�gina de informaci�n al usuario que le indica que acaba de hacer logout
   */
   public void hacerLogout(InfoServlet infoSer )throws ServletException, IOException{

    String url=rutaJsp+"logout.jsp";
    if (debug>0)System.out.println("biba.visualizador.logout,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());
  }


  // Error al intentar abrir la sesion de usuario, el usuario no est� registrado
  public void errorUsuarioNoRegistrado (InfoServlet infoServlet )throws ServletException, IOException{

       error("Lo sentimos pero para poder entrar en una sesi�n de b�squeda primero debe registrarse.",infoServlet);
  }

  //Presentamos los resultados de la b�squeda en una b�squeda rapida
  public void resultadoBusquedaRapida(String valorBusqueda, Vector resultado,InfoServlet infoSer)throws ServletException, IOException{

    //Le pasamos al JSP el valor de Busqueda y el vector de resultados a presentar
    infoSer.getSession().setAttribute("valorBusqueda",valorBusqueda);
    infoSer.getSession().setAttribute("resultadoBusqueda",resultado);

    String url=rutaJsp+"resultadoBusquedaRapida.jsp";
    if (debug>0)System.out.println("biba.visualizador.resultadoBusquedaRapida,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());

  }
  //Preseentamos la sesi�n del usaurio contenido en infoUsuario

    public void abrirSesionPrimeraVez(Vector temasActivos,String idTema,PlantillaCaracteristicasGenerales plantilla,String nombreUsuario,InfoServlet infoSer)throws ServletException, IOException{

    //Le pasamos al JSP la InfoUsuario para que presenta la sesi�n del usuario
    infoSer.getSession().setAttribute("pestanasActivas",temasActivos);
    infoSer.getSession().setAttribute("pestana",idTema);
    infoSer.getSession().setAttribute("plantillaGeneral",plantilla);
    infoSer.getSession().setAttribute("nombreUsuario",nombreUsuario);

    //Asertamos resultadoBusqueda y textoBusqueda vac�os para que
    //el JSP no lea posible basura de otros session
    infoSer.getSession().setAttribute("resultadoBusqueda",new Vector());
    infoSer.getSession().setAttribute("textoBusqueda",new String(""));
    String url=rutaJsp+"abrirSesion.jsp";

    if (debug>0)System.out.println("biba.visualizador.abrirSesion,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());


  }


  //Presenta un resultado de la b�squeda en una nueva ventana
  public void abrirResultado(String urlResultado,InfoServlet infoSer)throws ServletException, IOException{

    infoSer.getSession().setAttribute("urlResultado",urlResultado);

    String url=rutaJsp+"abreUrlResultado.jsp";

    if (debug>0)System.out.println("biba.visualizador.abrirResultado,url="+urlResultado);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());


  }


  //Preseentamos la sesi�n del usaurio contenido en infoUsuario por primera vez
  public void abrirSesion(String valorBusqueda,Vector resultados,Vector temasActivos,String idTema,PlantillaCaracteristicasGenerales plantilla,String nombreUsuario,InfoServlet infoSer)throws ServletException, IOException{

    //Le pasamos al JSP la InfoUsuario para que presenta la sesi�n del usuario
    infoSer.getSession().setAttribute("pestanasActivas",temasActivos);
    infoSer.getSession().setAttribute("pestana",idTema);
    infoSer.getSession().setAttribute("plantillaGeneral",plantilla);
    infoSer.getSession().setAttribute("nombreUsuario",nombreUsuario);
    infoSer.getSession().setAttribute("resultadoBusqueda",resultados);
    infoSer.getSession().setAttribute("textoBusqueda",valorBusqueda);
    String url=rutaJsp+"abrirSesion.jsp";

    if (debug>0)System.out.println("biba.visualizador.abrirSesion,url="+url);

    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());


  }

  public void abrirHotLinks(Vector vectorHotlinks, InfoServlet infoSer)throws ServletException, IOException{

    String url=rutaJsp+"hotlinks.jsp";

    if (debug>0)System.out.println("biba.visualizador.abrirHotLinks,url="+url);

    infoSer.getSession().setAttribute("vectorHotlinks", vectorHotlinks);
    ServletContext sc = infoSer.getContexto();
    RequestDispatcher rd = sc.getRequestDispatcher(url);
    //redireccionamos la petici�n y repuesta del usuario al jsp
    rd.forward(infoSer.getRequest(),infoSer.getResponse());


  }

}