package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import conexion.Conexion;

/**
 * Clase para realizar una consulta SELECT a la base de datos en la tabla empleados.
 */
public class ConsultaSelect {

    private static final String QUERY_SELECT = "SELECT * FROM empleados";
    
    /**
     * Constructor que ejecuta la consulta SELECT a la base de datos.
     * Utiliza un bloque try-with-resources para garantizar el cierre de recursos.
     */
    public ConsultaSelect() {
    	
        // Se obtiene la instancia de la conexión a la base de datos
        try (Connection conexion = Conexion.conectar(toString());
             PreparedStatement instruccion = conexion.prepareStatement(QUERY_SELECT);
             ResultSet resultado = instruccion.executeQuery()) {

            // Obtiene la metadata del resultado para manejar las columnas de manera dinámica
            ResultSetMetaData metadata = resultado.getMetaData();
            int numeroDeColumnas = metadata.getColumnCount();

            // Procesa el resultado de la consulta fila por fila
            while (resultado.next()) {
                for (int i = 1; i <= numeroDeColumnas; i++) {
                	
                    String nombreColumna = metadata.getColumnName(i);
                    Object valorColumna = resultado.getObject(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                    
                }
                
                System.out.println("------------------------");
                
            }

        } catch (SQLException e) {
        	
            // Maneja las excepciones de SQL y muestra el mensaje de error en la consola
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            
        }
        
    }
    
}
