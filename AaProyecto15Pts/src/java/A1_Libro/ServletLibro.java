/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1_Libro;

import A2_Autor.Autor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian
 */
@WebServlet(name = "ServletLibro", urlPatterns = {"/ServletLibro"})
public class ServletLibro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       String control = request.getParameter("control");
       String datoParaBuscar = request.getParameter("datoParaBuscar");
       
        Libro libro = new Libro();
        StringBuffer res = new StringBuffer();
        Writer ajaxSalida =  response.getWriter(); 
               
         if(control.toUpperCase().equals("MOSTRAR")){            
            libro.consultarRegistros(res, datoParaBuscar); 
            
        } else if(control.toUpperCase().equals("INSERT")){          
              insertar(request, response, res, libro);
        }

        try(PrintWriter out = response.getWriter()) {

          ajaxSalida.write(res.toString());
          ajaxSalida.flush();
          ajaxSalida.close();
        }
    }
    
    private void insertar(HttpServletRequest request, HttpServletResponse response, StringBuffer res, Libro libro){
        
        String idLibro = request.getParameter("idLibro");
        int idLibroInt = Integer.parseInt(idLibro);
        
        String nombreLibro = request.getParameter("nombreLibro");
        
        String isbnLibro = request.getParameter("isbnLibro");
        int isbnLibroInt = Integer.parseInt(isbnLibro);
        
        String autorIdAutor = request.getParameter("autorIdAutor");
        
        String casaEditorialId = request.getParameter("casaEditorialId");
     
        String categoriaIdCategoria = request.getParameter("categoriaIdCategoria");
       
                      
        libro.insert(idLibroInt, nombreLibro, isbnLibroInt, autorIdAutor, 
                casaEditorialId, categoriaIdCategoria, res);       
    }
    
    private void eliminar(HttpServletRequest request, HttpServletResponse response, StringBuffer res, Autor autor){}
    
    private void editar(HttpServletRequest request, HttpServletResponse response, StringBuffer res, Autor autor){}
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
