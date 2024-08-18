package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Alumno;
import entity.Pais;
import util.FechaUtil;
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
	
	public int eliminarAlumno(int idAlumno) {
		Connection conn = null;
		PreparedStatement pstm = null;
		int salida = -1;
		try {

			conn = MySqlDBConexion.getConexion();
			String sql = "delete from alumno where idAlumno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idAlumno);

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
	
	
	public int actualizarAlumno(Alumno obj) {
		Connection conn = null;
		PreparedStatement pstm = null;
		int salida = -1;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "update alumno set nombres = ?, apellidos = ?, telefono = ?, dni = ?, correo = ?, "
									 + " fechaNacimiento = ?, fechaActualizacion = ?, estado = ?, idPais = ? "
									 + " where idAlumno=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombres());
			pstm.setString(2, obj.getApellidos());
			pstm.setString(3, obj.getTelefono());
			pstm.setString(4, obj.getDni());
			pstm.setString(5, obj.getCorreo());
			pstm.setDate(6, obj.getFechaNacimiento());
			pstm.setDate(7, obj.getFechaActualizacion());
			pstm.setInt(8, obj.getEstado());
			pstm.setInt(9, obj.getPais().getIdPais());
			pstm.setInt(10, obj.getIdAlumno());

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
	
	
	public List<Alumno> listaAlumnoPorNombres(String filtro) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Alumno> lstSalida = new ArrayList<Alumno>();
		try {

			conn = MySqlDBConexion.getConexion();
			String sql = "select a.*, p.nombre from alumno a inner join pais p on a.idPais = p.idPais where a.nombres like ? or a.apellidos like ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, filtro);
			pstm.setString(2, filtro);

			System.out.println("SQL => " + pstm);
			rs = pstm.executeQuery();
			Alumno objAlumno;
			Pais objPais;
			while(rs.next()) {
				objAlumno = new Alumno();
				objAlumno.setIdAlumno(rs.getInt(1));
				objAlumno.setNombres(rs.getString(2));
				objAlumno.setApellidos(rs.getString(3));
				objAlumno.setTelefono(rs.getString(4));
				objAlumno.setDni(rs.getString(5));
				objAlumno.setCorreo(rs.getString(6));
				objAlumno.setFechaNacimiento(rs.getDate(7));
				objAlumno.setFechaNacimientoFormateada(FechaUtil.getFechaFormateadaYYYYMMdd(rs.getDate(7)));
				objAlumno.setFechaRegistro(rs.getDate(8));
				objAlumno.setFechaActualizacion(rs.getDate(9));
				objAlumno.setEstado(rs.getInt(10));
				
				objPais = new Pais();
				objPais.setIdPais(rs.getInt(11));
				objPais.setNombre(rs.getString(12));
				objAlumno.setPais(objPais);
				lstSalida.add(objAlumno);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {}
		}
		return lstSalida;
	}
	
	
	public Alumno buscaAlumnoPorPK(int idAlumno) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Alumno objAlumno =null;
		try {

			conn = MySqlDBConexion.getConexion();
			String sql = "select a.*, p.nombre from alumno a inner join pais p on a.idPais = p.idPais where a.idAlumno = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idAlumno);

			System.out.println("SQL => " + pstm);
			rs = pstm.executeQuery();
			Pais objPais;
			if(rs.next()) {
				objAlumno = new Alumno();
				objAlumno.setIdAlumno(rs.getInt(1));
				objAlumno.setNombres(rs.getString(2));
				objAlumno.setApellidos(rs.getString(3));
				objAlumno.setTelefono(rs.getString(4));
				objAlumno.setDni(rs.getString(5));
				objAlumno.setCorreo(rs.getString(6));
				objAlumno.setFechaNacimiento(rs.getDate(7));
				objAlumno.setFechaNacimientoFormateada(FechaUtil.getFechaFormateadaYYYYMMdd(rs.getDate(7)));
				objAlumno.setFechaRegistro(rs.getDate(8));
				objAlumno.setFechaActualizacion(rs.getDate(9));
				objAlumno.setEstado(rs.getInt(10));
				
				objPais = new Pais();
				objPais.setIdPais(rs.getInt(11));
				objPais.setNombre(rs.getString(12));
				objAlumno.setPais(objPais);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {}
		}
		return objAlumno;
	}
	
	public List<Alumno> listaAlumnoComplejo(String nombre, String telefono,String dni,Date fdesde,Date fhasta) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Alumno> lstSalida = new ArrayList<Alumno>();
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select a.*, p.nombre from alumno a inner join pais p on a.idPais = p.idPais "
						+ "where (a.nombres like ? or a.apellidos like ? ) and "
						+ "( ? = '' or telefono = ?) and "
						+ "( ? = '' or dni = ?) and "
						+ "(fechaNacimiento >= ? and fechaNacimiento <= ?)";
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, nombre);
			pstm.setString(2, nombre);
			pstm.setString(3, telefono);
			pstm.setString(4, telefono);
			pstm.setString(5, dni);
			pstm.setString(6, dni);
			pstm.setDate(7, fdesde);
			pstm.setDate(8, fhasta);

			System.out.println("SQL => " + pstm);
			rs = pstm.executeQuery();
			Alumno objAlumno;
			Pais objPais;
			while(rs.next()) {
				objAlumno = new Alumno();
				objAlumno.setIdAlumno(rs.getInt(1));
				objAlumno.setNombres(rs.getString(2));
				objAlumno.setApellidos(rs.getString(3));
				objAlumno.setTelefono(rs.getString(4));
				objAlumno.setDni(rs.getString(5));
				objAlumno.setCorreo(rs.getString(6));
				objAlumno.setFechaNacimiento(rs.getDate(7));
				objAlumno.setFechaNacimientoFormateada(FechaUtil.getFechaFormateadaYYYYMMdd(rs.getDate(7)));
				objAlumno.setFechaRegistro(rs.getDate(8));
				objAlumno.setFechaActualizacion(rs.getDate(9));
				objAlumno.setEstado(rs.getInt(10));
				
				objPais = new Pais();
				objPais.setIdPais(rs.getInt(11));
				objPais.setNombre(rs.getString(12));
				objAlumno.setPais(objPais);
				lstSalida.add(objAlumno);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {}
		}
		return lstSalida;
	}
	
	public boolean existeAlumnoPorDNI(String dni) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean existe = false;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select idAlumno from alumno where dni = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, dni);

			System.out.println("SQL => " + pstm);
			rs = pstm.executeQuery();
			if(rs.next()) {
				existe = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {}
		}
		return existe;
	}
	
}
