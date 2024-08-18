package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/validaRemoteRegistraAlumnoCampoFechaNacimientoServlet")
public class ValidaRemoteRegistraAlumnoCampoFechaNacimientoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String variable = req.getParameter("fechaNacimiento");
		System.out.println("[init] validaRemoteRegistraAlumnoCampoFechaNacimientoServlet DNI => " + variable);
		
		String msg = "";
		try {
			Date fechaHoy = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaHoy);
			int annoActual = cal.get(Calendar.YEAR);
			cal.set(Calendar.YEAR, annoActual-18);
			Date fechaMinima = cal.getTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaIngresada = sdf.parse(variable);
		
			if (fechaIngresada.after(fechaMinima)) {
				msg = "{\"valid\":false}";
			}else {
				msg = "{\"valid\":true}";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentType("application/json;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println(msg);
		
		System.out.println("[fin] validaRemoteRegistraAlumnoCampoDNIServlet");
	}
	
}