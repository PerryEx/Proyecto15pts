/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2_Autor;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Conexion.ConexionBaseDatos;

/**
 *
 * @author brian
 */
public class Autor {
    
    private ConexionBaseDatos conn;
    private Connection cn;
    private PreparedStatement prstmt = null;
    private ResultSet result = null;

    public Autor() {
        conn = new ConexionBaseDatos();
        cn = conn.conectar();        
    }
    
    public void insert(int idAutor, String nombre_autor, String apellido_autor, String nacionalidad_autor,
            int fecha_nacimiento_autor, StringBuffer salida){
        String querySql = "INSERT INTO biblioteca.autor(idautor, nombre_autor, apellido_autor, "
                + "nacionalidad_autor, fecha_nacimiento_autor)";
          querySql += " VALUES(?,?,?,?,?)"; 
          
          try{
              
            prstmt = cn.prepareStatement(querySql); 
            prstmt.setInt(1, idAutor);
            prstmt.setString(2, nombre_autor);
            prstmt.setString(3, apellido_autor);
            prstmt.setString(4, nacionalidad_autor);
            prstmt.setInt(5, fecha_nacimiento_autor);
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
             sql="select * from biblioteca.autor where autor.nombre_autor LIKE '%"+ datoParaBuscar +"%' ORDER BY nombre_autor ASC";
        }else{
            sql="SELECT * FROM biblioteca.autor ORDER BY nombre_autor ASC";
        }
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql);                        
        result = prstmt.executeQuery();    

            if (result!=null){
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idautor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("apellido_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nacionalidad_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("fecha_nacimiento_autor")).append("</td>");
                 
                respuesta.append("<td id=\"").append(result.getString("idautor")).append("\"  onclick=\"edit(this.id);\">") 
                        .append(" <a  class=\"btn btn-warning\"'><i class=\"fas fa-edit\"></i>  </a>"
                                +" <a id=\"borrar\" name=\"borrar\" class=\"btn btn-danger\"'> <i class=\"fas fa-trash-alt\"></i> </a>"
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
    
    public void consultarGenero(StringBuffer respuesta){       
         conn= new ConexionBaseDatos();
        cn=conn.conectar();
       
        String sql="select * from biblioteca.autor order by nombre_autor ASC";
       
        try{
        prstmt = cn.prepareStatement(sql);                        
        result = prstmt.executeQuery();    
                 System.out.println("prueba");
            if (result!=null){
                while (result.next()){  
                respuesta.append("<option value=\"").append(result.getString("idautor")).append(" \">").append(result.getString("nombre_autor")).append("</option>");    
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    
    public void consultarBorrar(int idBorrar, StringBuffer respuestas){
        conn= new ConexionBaseDatos();
        cn=conn.conectar();
       
        String sql="DELETE FROM biblioteca.autor where idautor = ?";
       
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
             sql="select * from biblioteca.autor where autor.nombre_autor LIKE '%"+ datoParaBuscar +"%' ORDER BY nombre_autor ASC";
       
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql); 
        result = prstmt.executeQuery();    

           
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idautor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("apellido_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("nacionalidad_autor")).append("</td>");
                respuesta.append("<td >").append(result.getString("fecha_nacimiento_autor")).append("</td>");
                 
                respuesta.append("<td id=\"").append(result.getString("idautor")).append("\"  onclick=\"edit(this.id);\">") 
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
