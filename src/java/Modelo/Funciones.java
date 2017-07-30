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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funciones {

    private Connection conectarBd() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Salvatore", "postgres", "admin");
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
            sentencia = conexion.createStatement();;
            ResultSet rs = sentencia.executeQuery(sql);
            boolean condicion = rs.next();
            if (!condicion) {
                System.out.println("sss");
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
            boolean condicion = rs.next();
            if (!condicion) {
                return false; // si devuelve false, usuario no existe
            }
            return true; // si devuelve true, usuario existe
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    // si devuelve 0, no hay errores
    // si devuelve 1, el correo existe
    // si devuelve 2, formato de escritura incorrecto
    public int emailExist(String correo) {
            Pattern patron = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher match = patron.matcher(correo);
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT correo_personal FROM login.empleado WHERE correo_personal='" + correo + "'";
            sentencia = conexion.createStatement();
            boolean condicion;
            if (match.matches()){
                ResultSet rs = sentencia.executeQuery(sql);
                condicion = rs.next();
                if (!condicion) {
                    return 0; // si devuelve 0, correo no existe y no tiene errores
                }else 
                    return 1; // si devuelve 1, correo existe
            }else
                return 2; // si devuelve 2, formato incorrecto
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        }
    }
    
    // si es 0, no hay error
    // si es 1, cedula existe
    // si es 2, cedula con formato erroneo
    public int cedulaExist(String cedula) {
            Pattern patron = Pattern.compile("[0-9]{1,}");
            Matcher match = patron.matcher(cedula);
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT cedula FROM login.empleado WHERE cedula='" + cedula + "'";
            sentencia = conexion.createStatement();
            boolean condicion;
            if (match.matches()){
                ResultSet rs = sentencia.executeQuery(sql);
                condicion = rs.next();
                if (!condicion) {
                    return 0; // si devuelve 0, cedula no existe y no tiene errores
                }else 
                    return 1; // si devuelve 1, cedula existe
            }else
                return 2; // si devuelve 2, formato incorrecto
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        }
    }
    
    // si devuelve 0, SQLException
    // si devuelve x, la direccion existe y x es el id
    public int dirExist(String direccion){
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sql = "SELECT id_direc FROM login.direccion WHERE nombre='" + direccion + "'";
            sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            boolean condicion = rs.next();
            int id;
            if (!condicion) {// si devuelve false, direccion no existe
                sql = "INSERT INTO login.direccion(nombre) VALUES ('" + direccion + "') RETURNING id_direc"; 
                rs = sentencia.executeQuery(sql);
                rs.next();
                id=rs.getInt(1);
            } else {// si devuelve true, direccion existe
                rs = sentencia.executeQuery(sql);
                rs.next();
                id=rs.getInt(1);
            }
            return id; 
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Entraste en catch");
            return 0;
        }
    }
    
    // si devuelve 0, telefono correcto
    // si devuelve 1, telefono formato erroneo
    public int validarTelefono(String telefono){
        Pattern patron = Pattern.compile("[0-9]{1,}");
        Matcher match = patron.matcher(telefono);
        if(match.matches())
            return 0;
        return 1;
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
    
    // si devuelve 0, no hay error
    // si devuelve 1, usuario existe
    // si devuelve 2, cedula existe
    // si devuelve 3, cedula con formato incorrecto
    // si devuelve 4, correo existe
    // si devuelve 5, correo con formato incorrecto
    // si devuelve 6, telefono con formato incorrecto
    // si devuelve 7, error de SQLException
    public int registrarUsuario(String pnombre, String snombre, String papellido, String sapellido, 
            String cedula, String usuario, String clave, String fnacimiento, String correo, String direccion, String rol, String telefono) {
        try {
            Connection conexion = conectarBd();
            Statement sentencia = null;
            String sql = null;
            conexion.setSchema("login");
            sentencia = conexion.createStatement();
            ResultSet rs;
            boolean existUser = userExist(usuario);
            int idDir = dirExist(direccion),existCedula = cedulaExist(cedula), existEmail = emailExist(correo), telfCorrecto = validarTelefono(telefono);
            if (!existUser){
                if (existCedula == 0){
                    if (existEmail == 0){
                        if (telfCorrecto == 0){
                            // Aqui va el INSERT de la sql
                            sql = "INSERT INTO login.empleado(cedula,usuario, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido,"
                                    + "fecha_nacimiento, correo_personal, nombre, id_direc, clave)"
                                    + "VALUES ('" + cedula + "','" + usuario + "','" + pnombre + "','" + snombre + "','" + papellido + "','" + sapellido + "',"
                                    + "to_date('" + fnacimiento +"','DD Month, YYYY'),'" + correo + "','" + rol + "','" + idDir + "','" + clave + "')";
                            sentencia.executeUpdate(sql);
                            sql = "INSERT INTO login.telefono(usuario, numero_telf)"
                                    + "VALUES ('" + usuario +"','" + telefono +"')";
                            sentencia.executeUpdate(sql);
                        }
                        else
                            return 6;// si devuelve 6, telefono con formato incorrecto
                    }
                    else{
                        if (existEmail == 1)
                            return 4;// si devuelve 4, correo existe
                        else 
                            return 5;// si devuelve 5, correo con formato incorrecto
                    }
                }    
                else{
                    if (existCedula == 1)
                        return 2;// si devuelve 2, cedula existe
                    else 
                        return 3;// si devuelve 3, cedula con formato incorrecto
                }
            }
            else
                return 1;// si devuelve 1, usuario existe
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return 7;
        }
    }

    /*public boolean registrarUsuario(String pnombre, String snombre, String papellido, String sapellido, 
            String cedula, String usuario, String clave, String fnacimiento, String correo, String direccion, String rol) {

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
    }*/
}
