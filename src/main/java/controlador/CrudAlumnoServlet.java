package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;

import entity.Alumno;
import entity.Pais;
import entity.Respuesta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelAlumno;

@WebServlet("/crudAlumno")
public class CrudAlumnoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metodo = req.getParameter("metodo");	
		switch (metodo) {
			case "paramLista": 		{ lista(req, resp); break;}
			case "paramInserta": 	{ inserta(req, resp); break;}
			case "paramActualiza": 	{ actualiza(req, resp); break;}
			case "paramELogica": 	{ eliminacionLogica(req, resp); break;}
			case "paramEFisica": 	{ eliminacionFisica(req, resp);}
		}
	}

	protected void lista(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[ini] lista");
		String filtro = req.getParameter("filtro");

		ModelAlumno model = new ModelAlumno();
		List<Alumno> lista = model.listaAlumnoPorNombres(filtro + "%");

		Gson gson = new Gson();
		String json = gson.toJson(lista);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		out.println(json);

		System.out.println("[fin] lista");
	}

	protected void inserta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[ini] inserta");

		// 1 Recuperar datos de la GUI
		String nom = req.getParameter("nombres");
		String ape = req.getParameter("apellidos");
		String tel = req.getParameter("telefono");
		String dni = req.getParameter("dni");
		String con = req.getParameter("correo");
		String fecNac = req.getParameter("fechaNacimiento");
		String pa = req.getParameter("pais");

		// 2 Llenar objAlumno
		Alumno objAlumno = new Alumno();
		objAlumno.setNombres(nom);
		objAlumno.setApellidos(ape);
		objAlumno.setTelefono(tel);
		objAlumno.setDni(dni);
		objAlumno.setCorreo(con);
		objAlumno.setFechaNacimiento(Date.valueOf(fecNac));
		objAlumno.setFechaRegistro(new Date(System.currentTimeMillis()));
		objAlumno.setFechaActualizacion(new Date(System.currentTimeMillis()));
		objAlumno.setEstado(1);

		Pais objPais = new Pais();
		objPais.setIdPais(Integer.parseInt(pa));
		objAlumno.setPais(objPais);

		// 3 Se envía objAlumno registrar
		ModelAlumno modelAlumno = new ModelAlumno();
		int salida = modelAlumno.insertarAlumno(objAlumno);

		// 4 Se envía el mensaje
		Respuesta objRespuesta = new Respuesta();

		if (salida>0) {
			List<Alumno>  lstDatos = modelAlumno.listaAlumnoPorNombres("%");
			objRespuesta.setDatos(lstDatos);
			objRespuesta.setMensaje("Registro existoso");
		}

		Gson gson = new Gson();
		String json = gson.toJson(objRespuesta);

		resp.setContentType("application/json;charset=UTF-8");

		PrintWriter out = resp.getWriter();
		out.println(json);

		System.out.println("[fin] inserta");
	}

	protected void actualiza(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[ini] actualiza");

		System.out.println("[fin] actualiza");
	}

	protected void eliminacionFisica(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("[ini] eliminacionFisica");

		String idAlumno = req.getParameter("idAlumno");
		Respuesta objResupeta = new Respuesta();
		ModelAlumno modelAlumno = new ModelAlumno();
		int salida =modelAlumno.eliminarAlumno(Integer.parseInt(idAlumno));
		if (salida>0) {
			List<Alumno>  lstDatos = modelAlumno.listaAlumnoPorNombres("%");
			objResupeta.setDatos(lstDatos);
			objResupeta.setMensaje("Eliminaci\u00f3n existosa");
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(objResupeta);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json);
		
		System.out.println("[fin] eliminacionFisica");

	}

	protected void eliminacionLogica(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("[ini] eliminacionLogica");

		String idAlumno = req.getParameter("idAlumno");
		
		ModelAlumno modelAlumno = new ModelAlumno();
		Alumno objAlumno = modelAlumno.buscaAlumnoPorPK(Integer.parseInt(idAlumno));
		int estadoNuevo = objAlumno.getEstado() == 0 ? 1 : 0;
		objAlumno.setEstado(estadoNuevo);
		modelAlumno.actualizarAlumno(objAlumno);
		
		Respuesta objResupeta = new Respuesta();
		List<Alumno>  lstDatos = modelAlumno.listaAlumnoPorNombres("%");
		objResupeta.setDatos(lstDatos);
		
		Gson gson = new Gson();
		String json = gson.toJson(objResupeta);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(json);
		
		System.out.println("[fin] eliminacionLogica");

	}

}
