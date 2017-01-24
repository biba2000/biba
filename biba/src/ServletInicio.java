import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

import biba.visualizador.*;
import biba.utiles.servlets.InfoServlet;
import biba.utiles.servlets.ErrorServletRegistro;
import biba.persistencia.*;

public class ServletInicio extends HttpServlet {

  private int debug=0;
  //Inicializar variables globales
  public void init(ServletConfig config) throws ServletException {

    super.init(config);

    // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;


 		// Obtenemos la ruta donde se deja la salida de error
		String ficheroErr = biba.persistencia.propiedades.getPropiedad("FICHERO_ERROR_SERVLET_INICIO");

    // Obtenemos la ruta donde se deja la salida estándar
    String ficheroOut = biba.persistencia.propiedades.getPropiedad("FICHERO_SALIDA_SERVLET_INICIO");


    // Llevamos la salida estándar y la salida error a fichero.
	try{
			System.setErr(new PrintStream(new FileOutputStream(ficheroErr),true));
			System.err.println("Fecha y hora:" + new Date(System.currentTimeMillis()).toString());
			System.setOut(new PrintStream(new FileOutputStream(ficheroOut),true));
			System.out.println("Fecha y hora:" + new Date(System.currentTimeMillis()).toString());
		} catch (FileNotFoundException e){
      System.out.println("no se han encontrado: "+ficheroErr+" "+ficheroOut);
			e.printStackTrace();
		}
  }



  //Procesar una petición HTTP Post
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //parámetros que recogemos del formulario que envía el usuario
    String nombre= "";
    String contrasena="";
    String action ="";

    //InfoUsario es toda la información del usuario que insertaremos en persistencia
    InfoUsuario infoUsuario = null;

    //ErrorRegistro nos da la caracterización de los errores que el usuario
    //ha cometido al registrarse
    ErrorServletRegistro errorRegistro=null;

    //infoServlet contendrá el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualización
    InfoServlet infoServlet = null;

    // gestor del recurso de visualización
    biba.visualizador.Gestor gestorVisualizador= new Gestor();


    //Creamos la sesion
    HttpSession sesion = request.getSession(true);
    if (sesion == null) {
       gestorVisualizador.errorNoHaySesion(response);
       if (debug>0)System.out.println("Sesion==null");

    }
    if (debug>0)System.out.println("Sesion!=null");

    //Vemos que es lo que hay que hacer, registrar un nuevo usuario o
    //abrir la sesión de un usuario,si accion no es ninguna de estas cosas
    //error!
    action=request.getParameter("action");

    if (action==null){
       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acción a realizar desde"+
                                 " el formulario de registro.",
                                 infoServlet);
       if (debug>0)System.out.println("action==null");
    }
    boolean accionCorrecta = "registro".equals(action) || "abrirSesion".equals(action);
    if (debug>0)System.out.println("action!=null");
    if(!accionCorrecta){
        if (debug>0)System.out.println("action no correcta");
       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acción correcta a realizar desde"+
                                 " el formulario de registro.",
                                 infoServlet);

    }


    if("registro".equals(action)){
        if (debug>0)System.out.println("action ==registro");
       //creamos la infoServlet para entregarla al recurso de visualización
        infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

        //cojemos los datos del usuario
        nombre = request.getParameter("nombre");
        contrasena = request.getParameter("contrasena");
        if (debug>0)System.out.println("nombre="+nombre+" contrasena="+contrasena);

        //vemos si los datos son correctos

        errorRegistro = compruebaDatosRegistro(nombre,contrasena);

        if(!errorRegistro.getHayErrores()){
           //Insertamos al nuevo usuario en persistencia

           infoUsuario = biba.persistencia.AccesoBD.nuevoUsuario(nombre,contrasena);

           if (debug>0)System.out.println("Creamos InfoUsuario");

           if (debug>0)System.out.println("InfoUsuario("+infoUsuario.getNombre()+", "+infoUsuario.getClave());
           // PARA ABRIR SESIÓN DELEGAMOS EL CONTROL EN EL AGENTE DE USUARIO
           // ENTREGAMOS AL AGENTE DE USUARIO LA infoUsuario METIÉNDOLA EN EL
           // ServletContext, el cual es compartido por todos los Servlets


          ServletContext sc = this.getServletContext();
           sc.setAttribute("nombreUsuario",nombre);

           RequestDispatcher rd = sc.getRequestDispatcher("/servlet/AgenteUsuario?accion=abrirSesion");
           //redireccionamos la petición y repuesta del usuario al Agente de Usuario
           rd.forward(request,response);





        }else{
           //errores en el registro del usuario
           if (debug>0)System.out.println("Error en registro usuario");
           gestorVisualizador.errorRegistroUsuario(nombre,errorRegistro,infoServlet);
        }
    }
    else
    if ("abrirSesion".equals(action)){

         if (debug>0)System.out.println("action ==abrirSesion");
         //creamos la infoServlet para entregarla al recurso de visualización
         infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

         //cogemos los datos del usuario
         nombre = request.getParameter("nombre");
         contrasena = request.getParameter("contrasena");
         if (debug>0)System.out.println("nombre="+nombre+" contrasena="+contrasena+")");

         //COMPROBAMOS QUE EL USARIO YA ESTÁ PREVIAMENTE
         //REGISTRADO EN EL SISTEMA Y QUE LA CONTRASEÑA DADA ES CORRECTA
         if (biba.persistencia.AccesoBD.existe(nombre)){

           //Ahora recuperamos la infoUsuario para ver si la contraseña es correcta
           //Recuperamos la info del usuario para saber cual era el resultado de su búsqueda
           if (debug>0)System.out.println("Recuperamos la  InfoUsuario de "+ nombre );
           infoUsuario = biba.persistencia.AccesoBD.cargarUsuario(nombre);
           if (debug>0)System.out.println("InfoUsuario("+infoUsuario.getNombre()+", "+infoUsuario.getClave());

           if (infoUsuario.getClave().equals(contrasena)){
                // PARA ABRIR SESIÓN DELEGAMOS EL CONTROL EN EL AGENTE DE USUARIO
                // ENTREGAMOS AL AGENTE DE USUARIO LA infoUsuario METIÉNDOLA EN EL
                // ServletContext, el cual es compartido por todos los Servlets


                ServletContext sc = this.getServletContext();
                sc.setAttribute("nombreUsuario",nombre);

                RequestDispatcher rd = sc.getRequestDispatcher("/servlet/AgenteUsuario?accion=abrirSesion");
                //redireccionamos la petición y repuesta del usuario al Agente de Usuario
                rd.forward(request,response);
          }else{
            //Contrasena incorrecta
            gestorVisualizador.errorUsuarioContrasenaIncorrecta(infoServlet);
          }

         }
         else // EL USUARIO NO SE ENCUENTRA REGISTRADO EN EL SISTEMA
           gestorVisualizador.errorUsuarioNoRegistrado(infoServlet);



    }


  }

  //comprueba si los datos del usuario son corectos, si existen errores, devuelve
  //una caracterización del mismo
  public ErrorServletRegistro compruebaDatosRegistro(String nombre,String contrasena){

    ErrorServletRegistro error = null;

    if (biba.persistencia.AccesoBD.existe(nombre)){
       error = new ErrorServletRegistro(true);
       error.setErrorNombreRepetido(true);
       return error;
    }
    else return new ErrorServletRegistro(false);



  }
  //Obtener información del servlet
  public String getServletInfo() {
    return "ServletInicio BIBA!";
  }


}
