package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import basesdedatos.BasesDeDatos;
import conexion.Conexion;

/**
 * Clase que permite realizar actualizaciones (UPDATE) 
 */
public class ConsultaUpdate {

    // Constantes SQL para las consultas de actualización y selección
    private static final String UPDATE_NOMBRE = "UPDATE empleados SET nombre = ? WHERE idEmpleado = ?";
    private static final String UPDATE_TELEFONO = "UPDATE empleados SET telefono = ? WHERE idEmpleado = ?";
    private static final String UPDATE_SALARIO = "UPDATE empleados SET salario = ? WHERE idEmpleado = ?";
    private static final String SELECT_EMPLEADO = "SELECT * FROM empleados WHERE idEmpleado = ?";

    /**
     * Constructor que inicializa la actualización de un empleado en la base de datos.
     * Dependiendo del nombre de la base de datos, se actualiza solo el nombre del 
     * empleado o se da la opción de actualizar otros atributos como el teléfono o 
     * el salario.
     *
     * @param nombreBD el nombre de la base de datos a utilizar.
     */
    public ConsultaUpdate(String nombreBD) {
        
        if (nombreBD.equals(BasesDeDatos.bd_empleados)) {
            
            actualizarNombreEmpleado(nombreBD);
            
        } else if (nombreBD.equals(BasesDeDatos.bd_empleados2)) {
            
            actualizarDatosEmpleado(nombreBD);
            
        } else {
            
            JOptionPane.showMessageDialog(null, "Base de datos no reconocida.");
            
        }
        
    }

    /**
     * Actualiza el nombre de un empleado en la base de datos especificada.
     *
     * @param nombreBD el nombre de la base de datos a utilizar.
     */
    private void actualizarNombreEmpleado(String nombreBD) {
        
        try (Connection conn = Conexion.conectar(nombreBD);
             PreparedStatement update = conn.prepareStatement(UPDATE_NOMBRE)) {

            int idEmpleado = solicitarIdEmpleado();
            String nuevoNombre = JOptionPane.showInputDialog("Actualiza el nombre y apellido del empleado:");

            update.setString(1, nuevoNombre);
            update.setInt(2, idEmpleado);

            ejecutarActualizacion(update);

        } catch (SQLException e) {
            
            manejarError(e);
            
        }
        
    }

    /**
     * Permite actualizar diferentes datos de un empleado en la base de datos, 
     * como el nombre, teléfono o salario.
     *
     * @param nombreBD el nombre de la base de datos a utilizar.
     */
    private void actualizarDatosEmpleado(String nombreBD) {
        
        try (Connection conn = Conexion.conectar(nombreBD);
             PreparedStatement seleccionar = conn.prepareStatement(SELECT_EMPLEADO)) {

            int idEmpleado = solicitarIdEmpleado();
            seleccionar.setInt(1, idEmpleado);

            try (ResultSet consulta = seleccionar.executeQuery()) {
                
                if (consulta.next()) {
                    
                    mostrarYActualizarEmpleado(conn, consulta, idEmpleado);
                    
                } else {
                    
                    JOptionPane.showMessageDialog(null, "No se encontró un empleado con el ID " + idEmpleado);
                    
                }
            }

        } catch (SQLException e) {
            
            manejarError(e);
            
        }
        
    }

    /**
     * Muestra los datos actuales de un empleado y permite al usuario actualizar 
     * un campo específico (nombre, teléfono o salario).
     *
     * @param conn       la conexión a la base de datos.
     * @param consulta   el {@code ResultSet} que contiene los datos del empleado.
     * @param idEmpleado el ID del empleado a actualizar.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    private void mostrarYActualizarEmpleado(Connection conn, ResultSet consulta, int idEmpleado) throws SQLException {
        
        String nombre = consulta.getString("nombre");
        int telefono = consulta.getInt("telefono");
        double salario = consulta.getDouble("salario");

        String opcion = JOptionPane.showInputDialog(null, String.format(
                "Qué deseas actualizar del empleado:\nID: %d, Nombre: %s, Teléfono: %d, Salario: %.2f\nEscribe una opción (name | phone | salary)",
                idEmpleado, nombre, telefono, salario));

        switch (opcion.toLowerCase()) {
            
            case "name":
                
                actualizarEmpleado(conn, UPDATE_NOMBRE, solicitarNuevoDato("nombre"), idEmpleado);
                break;
                
            case "phone":
                
                actualizarEmpleado(conn, UPDATE_TELEFONO, Integer.parseInt(solicitarNuevoDato("teléfono")), idEmpleado);
                break;
                
            case "salary":
                
                actualizarEmpleado(conn, UPDATE_SALARIO, Double.parseDouble(solicitarNuevoDato("salario")), idEmpleado);
                break;
                
            default:
                
                JOptionPane.showMessageDialog(null, "Opción a modificar no válida.");
                break;
        }
        
    }

    /**
     * Actualiza un campo específico de un empleado en la base de datos.
     *
     * @param conn        la conexión a la base de datos.
     * @param query       la consulta SQL para actualizar el campo.
     * @param nuevoValor  el nuevo valor que se asignará al campo.
     * @param idEmpleado  el ID del empleado a actualizar.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    private void actualizarEmpleado(Connection conn, String query, Object nuevoValor, int idEmpleado) throws SQLException {
        
        try (PreparedStatement update = conn.prepareStatement(query)) {
            
            update.setObject(1, nuevoValor);
            update.setInt(2, idEmpleado);
            ejecutarActualizacion(update);
            
        }
    }

    /**
     * Ejecuta la consulta de actualización y muestra un mensaje al usuario indicando 
     * si la operación fue exitosa o no.
     *
     * @param update la consulta de actualización preparada.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    private void ejecutarActualizacion(PreparedStatement update) throws SQLException {
        
        int resultado = update.executeUpdate();
        
        if (resultado > 0) {
            
            JOptionPane.showMessageDialog(null, "Empleado modificado con éxito.");
            
        } else {
            
            JOptionPane.showMessageDialog(null, "No se pudo modificar el empleado.");
            
        }
        
    }

    /**
     * Solicita al usuario el ID del empleado que desea actualizar.
     *
     * @return el ID del empleado como un entero.
     */
    private int solicitarIdEmpleado() {
        
        return Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID del empleado que deseas actualizar:"));
        
    }

    /**
     * Solicita al usuario el nuevo valor de un campo específico del empleado.
     *
     * @param dato el nombre del campo a actualizar (nombre, teléfono o salario).
     * @return el nuevo valor ingresado por el usuario.
     */
    private String solicitarNuevoDato(String dato) {
        
        return JOptionPane.showInputDialog("Introduce el nuevo " + dato + " del empleado:");
        
    }

    /**
     * Maneja los errores de SQL que puedan ocurrir durante la ejecución de las 
     * consultas, mostrando un mensaje de error al usuario.
     *
     * @param e la excepción SQL lanzada durante la operación.
     */
    private void manejarError(SQLException e) {
        
        JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        
    }
    
}
