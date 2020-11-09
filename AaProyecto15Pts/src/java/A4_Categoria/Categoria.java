/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A4_Categoria;

import Conexion.ConexionBaseDatos;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author brian
 */
public class Categoria {
    private ConexionBaseDatos conn;
    private Connection cn;
    private PreparedStatement prstmt = null;
    private ResultSet result = null;

    public Categoria() {
        conn = new ConexionBaseDatos();
        cn = conn.conectar();        
    }
    
    public void insert(int idCategoria, String nombreCategoria, StringBuffer salida){
        String querySql = "INSERT INTO biblioteca.categoria (idcategoria, nombre_categoria)";
          querySql += " VALUES(?,?)"; 
          
          try{
              
            prstmt = cn.prepareStatement(querySql); 
            prstmt.setInt(1, idCategoria);
            prstmt.setString(2, nombreCategoria);
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
             sql="SELECT * FROM biblioteca.categoria WHERE categoria.nombre_categoria LIKE '%"+ datoParaBuscar +"%' ORDER BY nombre_categoria ASC";
        }else{
            sql="SELECT * FROM biblioteca.categoria ORDER BY nombre_categoria ASC";
        }
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql);                        
        result = prstmt.executeQuery();    

            if (result!=null){
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idcategoria")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_categoria")).append("</td>");
                 respuesta.append("</tr>");
                
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
       
        String sql="select * from biblioteca.categoria order by nombre_categoria ASC";
       
        try{
        prstmt = cn.prepareStatement(sql);                        
        result = prstmt.executeQuery();    
                 System.out.println("prueba");
            if (result!=null){
                while (result.next()){  
                respuesta.append("<option value=\"").append(result.getString("idcategoria")).append(" \">").append(result.getString("nombre_categoria")).append("</option>");    
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
       
        String sql="DELETE FROM biblioteca.categoria where idcategoria = ?";
       
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
             sql="select * from biblioteca.categoria where categoria.nombre_categoria LIKE '%"+ datoParaBuscar +"%' ORDER BY nombre_categoria ASC";
       
        out.println(sql);
        try{
        prstmt = cn.prepareStatement(sql); 
        result = prstmt.executeQuery();    

           
                while (result.next()){
                respuesta.append("<tr>");
                respuesta.append("<td >").append(result.getString("idcategoria")).append("</td>");
                respuesta.append("<td >").append(result.getString("nombre_categoria")).append("</td>");
              
                respuesta.append("<td id=\"").append(result.getString("idcategoria")).append("\"  onclick=\"edit(this.id);\">") 
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
