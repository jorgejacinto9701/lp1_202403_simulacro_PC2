package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;

import entity.Alumno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelAlumno;

@WebServlet("/consultaAlumno")
public class ConsultaAlumnoServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nom = req.getParameter("nombre");
		String tel = req.getParameter("telefono");
		String dni = req.getParameter("dni");
		String desde = req.getParameter("desde");
		String hasta = req.getParameter("hasta");

		if (desde.isEmpty()) {desde = "1900-01-01";}
		if (hasta.isEmpty()) {hasta = "3000-01-01";}
		
		Date dateDesde =  Date.valueOf(desde);
		Date dateHasta =  Date.valueOf(hasta);
		
		ModelAlumno model = new ModelAlumno();
		List<Alumno> lista = model.listaAlumnoComplejo("%"+nom+"%", tel, dni, dateDesde, dateHasta);

		Gson gson = new Gson();
		String json = gson.toJson(lista);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		out.println(json);

		
		
	}

}
