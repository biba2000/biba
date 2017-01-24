package biba.utiles.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
//Una InfoSevlet contiene el contexto de un servlet y el request y response del usuario
//que le han llegado al servlet.
//Esta clase es útil para que un Servlet llame a un recurso y le de toda la información
//necesaria al recuros en un solo objeto

public class InfoServlet {

       //atributos
       ServletContext contexto=null;
       HttpServletRequest peticion=null;
       HttpServletResponse respuesta=null;
       HttpSession sesion=null;
  //constructor
  public InfoServlet(HttpSession session,ServletContext contexto, HttpServletRequest request, HttpServletResponse response){
         this.contexto=contexto;
         this.peticion=request;
         this.respuesta=response;
         this.sesion=session;

  }

  public ServletContext getContexto(){ return this.contexto;}
  public HttpServletRequest getRequest() {return this.peticion;}
  public HttpServletResponse getResponse(){return this.respuesta;}
  public HttpSession getSession() {return this.sesion;}

}