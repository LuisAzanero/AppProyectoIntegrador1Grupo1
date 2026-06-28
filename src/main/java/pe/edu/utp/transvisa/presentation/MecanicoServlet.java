package pe.edu.utp.transvisa.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/mecanico/tareas")
public class MecanicoServlet extends HttpServlet {

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