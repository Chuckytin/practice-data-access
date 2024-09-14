package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import conexion.Conexion;
import basesdedatos.BasesDeDatos;

/**
 * Clase para realizar una consulta INSERT a la base de datos en la tabla empleados.
 */
public class ConsultaInsert {

    private static final String QUERY_INSERT_SIMPLE = "INSERT INTO empleados (nombre) VALUES (?)";
    private static final String QUERY_INSERT_COMPLETA = "INSERT INTO empleados (nombre, telefono, salario) VALUES (?, ?, ?)";
    
    /**
     * Constructor que ejecuta la consulta INSERT a la base de datos.
     * 
     * @param nombreBD nombre de la base de datos a utilizar
     * @param nombre nombre del empleado
     * @param telefono (opcional) teléfono del empleado
     * @param salario (opcional) salario del empleado
     */
    public ConsultaInsert(String nombreBD, String nombre, String telefono, String salario) {
    	
        if (nombreBD.equals(BasesDeDatos.bd_empleados)) {
        	
            insertarEmpleadoSimple(nombreBD, nombre);
            
        } else if (nombreBD.equals(BasesDeDatos.bd_empleados2)) {
        	
            insertarEmpleadoCompleto(nombreBD, nombre, telefono, salario);
            
        } else {
        	
            JOptionPane.showMessageDialog(null, "Base de datos no válida.");
            
        }
        
    }

    /**
     * Inserta un empleado con solo nombre en la base de datos.
     *
     * @param nombreBD Nombre de la base de datos.
     * @param nombre Nombre del empleado.
     */
    private void insertarEmpleadoSimple(String nombreBD, String nombre) {
    	
        try (Connection conexion = Conexion.conectar(nombreBD);
             PreparedStatement insertar = conexion.prepareStatement(QUERY_INSERT_SIMPLE)) {

            if (nombre != null && !nombre.trim().isEmpty()) {
            	
                insertar.setString(1, nombre.trim());
                int resultado = insertar.executeUpdate();
                
                if (resultado > 0) {
                	
                    JOptionPane.showMessageDialog(null, "Empleado agregado correctamente.");
                    
                }
                
            } else {
            	
            	JOptionPane.showMessageDialog(null, "Nombre no válido.");
                
            }

        } catch (SQLException e) {
        	
        	JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            
        }
        
    }

    /**
     * Inserta un empleado con nombre, teléfono y salario en la base de datos.
     *
     * @param nombreBD Nombre de la base de datos.
     * @param nombre Nombre del empleado.
     * @param telefono Teléfono del empleado.
     * @param salario Salario del empleado.
     */
    private void insertarEmpleadoCompleto(String nombreBD, String nombre, String telefono, String salario) {
       
    	try (Connection conexion = Conexion.conectar(nombreBD);
             PreparedStatement insertar = conexion.prepareStatement(QUERY_INSERT_COMPLETA)) {

            if (nombre != null && !nombre.trim().isEmpty() &&
                telefono != null && !telefono.trim().isEmpty() &&
                salario != null && !salario.trim().isEmpty()) {

                insertar.setString(1, nombre.trim());
                insertar.setInt(2, Integer.parseInt(telefono.trim()));
                insertar.setDouble(3, Double.parseDouble(salario.trim()));

                int resultado = insertar.executeUpdate();
                
                if (resultado > 0) {
                	
                	JOptionPane.showMessageDialog(null, "Empleado agregado correctamente.");
                    
                }

            } else {
            	
            	JOptionPane.showMessageDialog(null, "Datos no válidos.");
                
            }

        } catch (SQLException e) {
        	
        	JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            
        }
    	
    }
    
}
