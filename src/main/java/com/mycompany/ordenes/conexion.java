/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ordenes;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Luis F Montoya
 */
public class conexion {
    Connection conectar = null;
    String usuario = "root";
    String contraseña = "Clave4_MySQL";
    String db= "dbordenes";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    
    public Connection estableConexion(){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            //JOptionPane.showMessageDialog(null, "Conexion exitosa");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos, erro:"+ e.toString());
        }
        return conectar;
    } 
}
