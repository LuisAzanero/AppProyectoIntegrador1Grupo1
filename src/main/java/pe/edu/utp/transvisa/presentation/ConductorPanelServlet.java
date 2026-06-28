package pe.edu.utp.transvisa.presentation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/conductor/panel")
public class ConductorPanelServlet extends HttpServlet { 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !"Conductor".equals(s.getAttribute("rolUsuario"))) {
            res.sendRedirect("../login");
            return;
        }
        req.getRequestDispatcher("../conductor_panel.jsp").forward(req, res);
    }
}