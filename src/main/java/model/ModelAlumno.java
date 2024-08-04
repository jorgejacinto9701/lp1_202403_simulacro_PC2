package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import entity.Alumno;
import util.MySqlDBConexion;

public class ModelAlumno {

	public int insertarAlumno(Alumno obj) {
		Connection conn = null;
		PreparedStatement pstm = null;
		int salida = -1;
		try {

			conn = MySqlDBConexion.getConexion();
			String sql = "insert into alumno values(null,?,?,?,?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombres());
			pstm.setString(2, obj.getApellidos());
			pstm.setString(3, obj.getTelefono());
			pstm.setString(4, obj.getDni());
			pstm.setString(5, obj.getCorreo());
			pstm.setDate(6, obj.getFechaNacimiento());
			pstm.setDate(7, obj.getFechaRegistro());
			pstm.setDate(8, obj.getFechaActualizacion());
			pstm.setInt(9, obj.getEstado());
			pstm.setInt(10, obj.getPais().getIdPais());

			System.out.println("SQL => " + pstm);

			salida = pstm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {}
		}
		return salida;
	}
	
}
