package app;

import basesdedatos.BasesDeDatos;
import conexion.Conexion;
import consultas.ConsultaDelete;
import consultas.ConsultaInsert;
import consultas.ConsultaSelect;
import consultas.ConsultaUpdate;

public class Principal {

	public static void main(String[] args) {

		String bd = BasesDeDatos.bd_empleados2;

		try {
			
			// Conectar a la base de datos inicial
			Conexion.conectar(bd);

			// Realizar la inserción en otra base de datos
			new ConsultaInsert(bd, "Fernando Navarro", "696588720", "52000");

			// Ejecutar la consulta de selección
			new ConsultaSelect();

			// Ejecutar la consulta UPDATE
			new ConsultaUpdate(bd);

			new ConsultaDelete(bd, 1);

		} finally {

			// Cerrar la conexión a la base de datos
			Conexion.cerrarConexion();

		}

	}

}
