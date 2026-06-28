package pe.edu.utp.transvisa.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/supervisor/panel")
public class SupervisorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Supervisor de flota".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }
        req.getRequestDispatcher("../supervisor_panel.jsp").forward(req, res);
    }
}

// Redireccionamiento del mecanico
@WebServlet("/mecanico/tareas")
class MecanicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Tecnico Mecanico".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }
        req.getRequestDispatcher("../mecanico_tareas.jsp").forward(req, res);
    }
}

@WebServlet("/conductor/panel")
class ConductorPanelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Conductor".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }
        // Redirige al panel o vista operativa del chofer
        req.getRequestDispatcher("../conductor_panel.jsp").forward(req, res);
    }
}

// Redireccionamiento de garita
@WebServlet("/garita/panel")
class GaritaPanelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Operador de Garita".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }
        req.getRequestDispatcher("../garita_control.jsp").forward(req, res);
    }
}