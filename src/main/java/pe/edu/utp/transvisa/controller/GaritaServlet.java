package pe.edu.utp.transvisa.controller;

import pe.edu.utp.transvisa.business.MovimientoService;
import pe.edu.utp.transvisa.dao.VehiculoDAO;
import pe.edu.utp.transvisa.domain.MovimientoGarita;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MovimientoRepositoryImpl;
import pe.edu.utp.transvisa.persistence.VehiculoRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/garita")
public class GaritaServlet extends HttpServlet {
    
    private MovimientoService service;
    private VehiculoDAO vehiculoDAO;
    
    @Override
    public void init() {
        service = new MovimientoService(new MovimientoRepositoryImpl(), new VehiculoRepositoryImpl());
        vehiculoDAO = new VehiculoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
        req.setAttribute("vehiculos", vehiculos);
        req.getRequestDispatcher("/WEB-INF/views/garita/registroEntrada.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        int idVehiculo = Integer.parseInt(req.getParameter("idVehiculo"));
        String tipoOperacion = req.getParameter("tipoOperacion");
        BigDecimal kilometraje = new BigDecimal(req.getParameter("kilometraje"));
        
        MovimientoGarita movimiento = new MovimientoGarita();
        movimiento.setIdVehiculo(idVehiculo);
        movimiento.setIdUsuario(1);
        movimiento.setTipoOperacion(tipoOperacion);
        movimiento.setKilometrajeRegistro(kilometraje);
        
        try {
            service.procesarRegistroGarita(movimiento);
            req.setAttribute("mensaje", "Movimiento registrado exitosamente");
            req.setAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            req.setAttribute("mensaje", e.getMessage());
            req.setAttribute("tipoMensaje", "error");
        }
        
        List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
        req.setAttribute("vehiculos", vehiculos);
        req.getRequestDispatcher("/WEB-INF/views/garita/registroEntrada.jsp").forward(req, resp);
    }
}