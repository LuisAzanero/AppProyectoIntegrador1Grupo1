package pe.edu.utp.transvisa.controller;

import pe.edu.utp.transvisa.dao.VehiculoDAO;
import pe.edu.utp.transvisa.domain.Vehiculo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {
    
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
        req.setAttribute("vehiculos", vehiculos);
        req.getRequestDispatcher("/WEB-INF/views/vehiculos/listaVehiculos.jsp").forward(req, resp);
    }
}