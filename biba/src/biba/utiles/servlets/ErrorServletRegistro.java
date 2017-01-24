package biba.utiles.servlets;

//ErrorRegistro nos da la caracterizaci�n de los errores que el usuario
//ha cometido al registrarse

  public class ErrorServletRegistro{

      boolean hayErrores=false;
      boolean errorSintacticoNombre=false;
      boolean errorSintacticoContrasena=false;
      boolean errorNombreRepetido=false;

      //constructor
      public ErrorServletRegistro(boolean hayError){
             this.hayErrores=hayError;

      }
      //m�todos
      public void setHayErrores(boolean valor){this.hayErrores=valor;}
      public void setErrorSintacticoNombre(boolean valor){this.errorSintacticoNombre=valor;}
      public void setErrorSintacticoContrasena(boolean valor){this.errorSintacticoContrasena=valor;}
      public void setErrorNombreRepetido(boolean valor){this.errorNombreRepetido=valor;}

      public boolean getHayErrores(){return this.hayErrores;}
      public boolean getErrorSintacticoNombre(){return this.errorSintacticoNombre;}
      public boolean getErrorSintacticoContrasena(){return this.errorSintacticoContrasena;}
      public boolean getErrorNombreRepetido(){return this.errorNombreRepetido;}



  }
