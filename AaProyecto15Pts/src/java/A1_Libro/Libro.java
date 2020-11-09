
package A1_Libro;

import Conexion.ConexionBaseDatos;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Libro {
       
    private ConexionBaseDatos conn;
    private Connection cn;
    private PreparedStatement prstmt = null;
    private ResultSet result = null;

    public Libro() {
        conn = new ConexionBaseDatos();
        cn = conn.conectar();        
    }
    
    public void insert(int idLibro, String nombreLibro, int isbnLibro, String autorIdAutor, String casaEditorialId, 
            String categoriaIdCategoria, StringBuffer salida)
    {
        String querySql = "INSERT INTO biblioteca.libro (idlibro, nombre_libro, isbn_libro, autor_idautor,"
                + " casa_editorial_idcasa_editorial, categoria_idcategoria)";
          querySql += " VALUES(?,?,?,?,?,?)"; 
          
          try{
              
            prstmt = cn.prepareStatement(querySql); 
            prstmt.setInt(1, idLibro);
            prstmt.setString(2, nombreLibro);
            prstmt.setInt(3, isbnLibro);
            prstmt.setString(4, autorIdAutor);
            prstmt.setString(5, casaEditorialId);
             prstmt.setString(6, categoriaIdCategoria);
             int resultado = prstmt.executeUpdate(); 
                if(resultado > 0){
                    salida.append("1");
                }else{
                    salida.append("0");
                }
          }catch(SQLException e){
              String error = e.getMessage();  
            if(error.indexOf("ORA-00001") != -1){
                salida.append("ORA-00001");
          }else{
                salida.append(error);
            }
        }
    }
    
    public void consultarRegistros(StringBuffer respuesta, String datoParaBuscar){  
        //base de datos: universidad
        //tabla: alumno           
        //String sql="select * from alumno"; ORACLE
        
        String sql;
        if(datoParaBuscar.length()>0){
           
           sql= "select idlibro, nombre_libro, isbn_libro, nombre_autor, nombre_casa_editorial, nombre_categoria" +
                "from biblioteca.libro, biblioteca.autor, biblioteca.casa_editorial, biblioteca.categoria" +
                "where libro.autor_idautor = autor.idautor and libro.casa_editorial_idcasa_editorial = casa_editorial.idcasa_editorial" +
                "and libro.categoria_idcategoria = categoria.idcategoria LIKE '%"+ datoParaBuscar +"%' order by nombre_libro;";
           
        }else{
            
            sql="select idlibro, nombre_libro, isbn_libro, nombre_autor, nombre_casa_editorial, nombre_categoria "
                    + "from biblioteca.libro, biblioteca.autor, biblioteca.casa_editorial, biblioteca.categoria "
                    + "where libro.autor_idautor = autor.idautor and libro.casa_editorial_idcasa_editorial = casa_editorial.idcasa_editorial "
                    + "and libro.categoria_idcategoria = categoria.idcategoria order by nombre_libro ASC";
        }
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql);                        
        result = prstmt.executeQuery();    

            if (result!=null){
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idlibro")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_libro")).append("</td>");
                respuesta.append("<td >").append(result.getString("isbn_libro")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_casa_editorial")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_categoria")).append("</td>");
                 
                respuesta.append("<td id=\"").append(result.getString("idlibro")).append("\"  onclick=\"edit(this.id);\">") 
                        .append(" <a class=\"btn btn-warning\"'><i class=\"fas fa-edit\"></i>  </a>"
                                +" <a class=\"btn btn-danger\"'> <i class=\"fas fa-trash-alt\"></i> </a>"
                                + " <td></tr>");
                }
            }else{
                respuesta.append("No hay registros para mostrar");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
     public void consultarBorrar(int idBorrar, StringBuffer respuestas){
        conn= new ConexionBaseDatos();
        cn=conn.conectar();
       
        String sql="DELETE FROM biblioteca.libro where idlibro = ?";
       
        try{
        prstmt = cn.prepareStatement(sql);
        prstmt.setInt(1, idBorrar);
          prstmt.executeUpdate();
        
           
        }
        catch(SQLException ex){
            ex.printStackTrace();
          
        }
    }
     
      public void BuscarRegistros(StringBuffer respuesta, String datoParaBuscar){  
        //base de datos: universidad
        //tabla: alumno           
        //String sql="select * from alumno"; ORACLE
        conn= new ConexionBaseDatos();
        cn=conn.conectar();
       
        String sql;
             sql="select idlibro, nombre_libro, isbn_libro, nombre_autor, nombre_casa_editorial, nombre_categoria "
                     + "from biblioteca.libro, biblioteca.autor, biblioteca.casa_editorial, biblioteca.categoria "
                     + "where libro.autor_idautor = autor.idautor and libro.casa_editorial_idcasa_editorial = casa_editorial.idcasa_editorial "
                     + "and libro.categoria_idcategoria = categoria.idcategoria "
                     + "and libro.nombre_libro LIKE '%"+ datoParaBuscar +"%' order by nombre_libro ASC";
       
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql); 
        result = prstmt.executeQuery();    

           
                while (result.next()){
                 respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idlibro")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_libro")).append("</td>");
                respuesta.append("<td >").append(result.getString("isbn_libro")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_casa_editorial")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_categoria")).append("</td>");
                
                respuesta.append("<td id=\"").append(result.getString("idlibro")).append("\"  onclick=\"edit(this.id);\">") 
                        .append(" <a  class=\"btn btn-warning\"'><i class=\"fas fa-edit\"></i>  </a>"
                                +" <a id=\"borrar\" name=\"borrar\" class=\"btn btn-danger\"'> <i class=\"fas fa-trash-alt\"></i> </a>"
                                + " <td></tr>");
                }
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
