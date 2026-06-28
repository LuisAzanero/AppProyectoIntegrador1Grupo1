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