
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
   
     private String url="jdbc:mysql://localhost:3306?useSSL=true";//url de MySQL
    private String usuario="root";
    private String clave="9ouFRiEb"; 
    private Connection conexion=null;    
    
    public Connection conectar(){   
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion=DriverManager.getConnection( url, usuario,clave);
            
        }  catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {   
           ex.printStackTrace();
        }         
        return conexion;        
    }
    
    public static void main(String[] args){
    
        ConexionBaseDatos conn = new ConexionBaseDatos();
        conn.conectar();
    }
}
