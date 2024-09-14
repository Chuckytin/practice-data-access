package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Clase Singleton para gestionar la conexión a una base de datos MySQL
 */
public class Conexion {

	//variable que mantiene la conexión única a la base de datos
	private static Connection conexion;
	
	//Instancia única de la clase Conexión
	private static Conexion instancia;
	
	//Constantes para los detalles de la conexión a la base de datos
	private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Nombre del driver

    /**
     * Constructor privado para evitar que se creen múltiples instancias de esta clase
     */
    private Conexion() {}
    
    /**
     * Método estático para obtener la instancia única de la clase Conexion.
     * 
     * @return Conexion instancia única de la clase Conexion.
     */
    public static Conexion getInstancia() {
    	
    	if (instancia == null) {
			
    		instancia = new Conexion();
    		
		}
    	
    	return instancia;
    	
    }
    
    /**
     * Método para conectarse a una base de datos
     * 
     * @param nombreBD nombre de la base de datos
     * @return Connection objeto de conexión a la base de datos
     * @throws ClassNotFoundException si ocurre un error al cargar el driver
     * @throws SQLException si ocurre un error de conexión
     */
    public static Connection conectar(String nombreBD) {
    	
    	//evitará que la conexión se establezca más de una vez
        if (conexion == null) {
			
        	try {
            	
                // Cargar el driver de MySQL, no es necesario pero es buena práctica
                Class.forName(DRIVER);
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL + nombreBD, USER, PASSWORD);
                
               JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos " + nombreBD + ".");
                
            } catch (ClassNotFoundException e) {
            	
            	JOptionPane.showMessageDialog(null, "Error al cargar el driver: " + e.getMessage());
                
            } catch (SQLException e) {
            	
                JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos: " + e.getMessage());
                
            }
        	
		}

        return conexion;
        
    }

    /**
     * Método para cerrar la conexión a la base de datos
     */
	public static void cerrarConexion() {
		
		//si la conexión no está vacía la cerrará
		if (conexion != null) {
			
			try {
				
				conexion.close();
				
				JOptionPane.showMessageDialog(null, "Conexión cerrada con éxito.");
				
			} catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
				
			}
			
		}
		
	}
    
}
