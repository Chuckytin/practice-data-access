package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import conexion.Conexion;
import basesdedatos.BasesDeDatos;

/**
 * Clase para realizar una consulta DELETE a la base de datos en la tabla empleados.
 */
public class ConsultaDelete {

    private static final String QUERY_SELECT = "SELECT * FROM empleados WHERE idEmpleado = ?";
    private static final String QUERY_DELETE = "DELETE FROM empleados WHERE idEmpleado = ?";

    /**
     * Constructor que ejecuta la consulta DELETE en la base de datos.
     * 
     * @param nombreBD nombre de la base de datos a utilizar
     * @param idEmpleadoDelete ID del empleado a eliminar
     */
    public ConsultaDelete(String nombreBD, int idEmpleadoDelete) {
    	
        if (nombreBD.equals(BasesDeDatos.bd_empleados) || nombreBD.equals(BasesDeDatos.bd_empleados2)) {
        	
            eliminarEmpleado(nombreBD, idEmpleadoDelete);
            
        } else {
        	
            JOptionPane.showMessageDialog(null, "Bases de datos no válida.");
            
        }
        
    }

    /**
     * Método para eliminar un empleado de la base de datos.
     * 
     * @param nombreBD Nombre de la base de datos.
     * @param idEmpleadoDelete ID del empleado a eliminar.
     */
    private void eliminarEmpleado(String nombreBD, int idEmpleadoDelete) {
    	
        try (Connection conexion = Conexion.conectar(nombreBD);
             PreparedStatement seleccionar = conexion.prepareStatement(QUERY_SELECT);
             PreparedStatement delete = conexion.prepareStatement(QUERY_DELETE)) {

            // Seleccionar el empleado por ID
            seleccionar.setInt(1, idEmpleadoDelete);
            
            try (ResultSet consulta = seleccionar.executeQuery()) {

                // Verificar que el empleado exista
                if (consulta.next()) {
                	
                    String nombre = consulta.getString("nombre");

                    // Confirmar la eliminación
                    if (confirmarEliminacion(idEmpleadoDelete, nombre, consulta, nombreBD)) {

                        // Ejecutar eliminación
                        delete.setInt(1, idEmpleadoDelete);
                        int resultado = delete.executeUpdate();

                        if (resultado > 0) {
                        	
                            JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito.");
                            
                        } else {
                        	
                            JOptionPane.showMessageDialog(null, "Error al eliminar el empleado.");
                            
                        }
                        
                    } else {
                    	
                        JOptionPane.showMessageDialog(null, "Eliminación cancelada.");
                        
                    }
                    
                } else {
                	
                    JOptionPane.showMessageDialog(null, "No se encontro el empleado con el ID " + idEmpleadoDelete);
                    
                }
                
            }

        } catch (SQLException e) {
        	
        	JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            
        }
        
    }

    /**
     * Método para confirmar la eliminación del empleado.
     * 
     * @param idEmpleado ID del empleado.
     * @param nombre Nombre del empleado.
     * @param consulta ResultSet con los datos del empleado.
     * @param nombreBD Nombre de la base de datos.
     * @return true si se confirma la eliminación, false en caso contrario.
     */
    private boolean confirmarEliminacion(int idEmpleado, String nombre, ResultSet consulta, String nombreBD) throws SQLException {
       
    	if (nombreBD.equals(BasesDeDatos.bd_empleados)) {
    		
            System.out.printf("¿Deseas eliminar al siguiente empleado?\nID: %d, Nombre: %s\n", 
            		idEmpleado, nombre);
            
        } else if (nombreBD.equals(BasesDeDatos.bd_empleados2)) {
        	
            int telefono = consulta.getInt("telefono");
            double salario = consulta.getDouble("salario");
            System.out.printf("¿Deseas eliminar al siguiente empleado?\nID: %d, Nombre: %s, Teléfono: %d, Salario: %.2f\n",
                    idEmpleado, nombre, telefono, salario);
            
        }
    	
        //Aquí se puede reemplazar por un método de confirmación adecuado
        return true; 
        
    }
    
}
