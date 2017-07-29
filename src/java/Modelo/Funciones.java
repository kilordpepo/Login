package Modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pepo
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Funciones {

    private Connection conectarBd() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Salvatore", "postgres", "salvatore");
            return connection;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean validarUsuario(String usuario, String clave) {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT cedula FROM login.empleado WHERE usuario='" + usuario + "' AND clave='" + clave + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            if (rs.equals(0)) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean userExist(String usuario) {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT usuario FROM login.empleado WHERE usuario='" + usuario + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            if (rs.equals(usuario)) {
                return false; // si devuelve false, usuario no existe
            }
            return true; // si devuelve true, usuario existe
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean emailExist(String correo) {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT correo_personal FROM login.empleado WHERE correo_personal='" + correo + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            if (rs.equals(correo)) {
                return false; // si devuelve false, usuario no existe
            }
            return true; // si devuelve true, usuario existe
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean cedulaExist(String cedula) {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT cedula FROM login.empleado WHERE cedula='" + cedula + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            if (rs.equals(cedula)) {
                return false; // si devuelve false, usuario no existe
            }
            return true; // si devuelve true, usuario existe
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String[] obtenerRoles() {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            List<String> lroles = new ArrayList<>();
            String roles[];
            conexion.setSchema("login");
            sql = "SELECT nombre FROM login.rol";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                lroles.add(rs.getString(1));
            }
            roles = new String[lroles.size()];
            for (int i = 0; i < lroles.size(); i++) {
                roles[i] = lroles.get(i);
            }
            System.out.println("roles: "+ roles[0]);
            return roles;
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public boolean registrarUsuario(String pnombre, String snombre, String papellido, String sapellido, String cedula, String usuario, String clave, String fnacimiento, String correo, String direccion, String rol) {

        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT cedula FROM EMPLEADO WHERE usuario='" + usuario + "' AND clave='" + clave + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            if (rs.getRow() == 1) {
                return false;
            }
            sql = "INSERT INTO login.direccion(\n"
                    + "            id_direc, nombre)\n"
                    + "    VALUES (DEFAULT, '" + direccion + "')";
            sentencia = conexion.createStatement();
            rs = sentencia.executeQuery(sql);
            sql = "SELECT currval(pg_get_serial_sequence('direccion', 'id_direc'))";
            sentencia = conexion.createStatement();
            rs = sentencia.executeQuery(sql);
            sql = "INSERT INTO login.empleado(\n"
                    + "            cedula, usuario, primer_nombre, segundo_nombre, primer_apellido, \n"
                    + "            segundo_apellido, fecha_nacimiento, correo_personal, nombre, \n"
                    + "            id_direc, clave)\n"
                    + "    VALUES ('"+cedula+"','"+usuario+"','"+pnombre+"','"+snombre+"','"+papellido+"', \n"
                    + "            '"+sapellido+"','"+fnacimiento+"','"+correo+"','"+rol+"', \n"
                    + "            "+rs.getInt(1)+",'"+clave+"')";
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
