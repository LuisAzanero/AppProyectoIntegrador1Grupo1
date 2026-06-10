package pe.edu.utp.transvisa.presentation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Usuario;
import pe.edu.utp.transvisa.persistence.MySQLUsuarioRepository;
import pe.edu.utp.transvisa.persistence.UsuarioRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/usuarios/exportar")
public class ReporteUsuarioServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ReporteUsuarioServlet.class);
    private final UsuarioRepository usuarioRepository = new MySQLUsuarioRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("../login");
            return;
        }

        String tipo = request.getParameter("tipo");
        try {
            // reutilizamos persistencia existente para la lista de usuarios
            List<Usuario> lista = usuarioRepository.listarTodos();

            if ("excel".equalsIgnoreCase(tipo)) {
                exportarExcel(response, lista);
            } else if ("csv".equalsIgnoreCase(tipo)) {
                exportarCSV(response, lista);
            } else {
                response.sendRedirect("../usuarios");
            }
        } catch (Exception e) {
            logger.error("Error al exportar reporte de personal", e);
            response.sendRedirect("../usuarios");
        }
    }

    private void exportarExcel(HttpServletResponse response, List<Usuario> lista) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Personal_TRANSVISA.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Lista de Personal");

            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());

            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.MAROON.getIndex()); // Distinto color para usuarios
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(HorizontalAlignment.CENTER);

            String[] columnas = {"ID", "DNI", "Nombre Completo", "Username", "Rol Asignado"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(style);
            }

            int rowNum = 1;
            for (Usuario u : lista) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(u.getIdUsuario());
                row.createCell(1).setCellValue(u.getDni());
                row.createCell(2).setCellValue(u.getNombre());
                row.createCell(3).setCellValue(u.getUsername());
                row.createCell(4).setCellValue(u.getRol());
            }

            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Reporte Excel de usuarios descargado.");
        }
    }

    private void exportarCSV(HttpServletResponse response, List<Usuario> lista) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Personal_TRANSVISA.csv");

        try (PrintWriter writer = response.getWriter()) {
            writer.write('\ufeff'); // BOM UTF-8
            try (CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "DNI", "Nombre_Completo", "Username", "Rol_Asignado"))) {
                for (Usuario u : lista) {
                    csv.printRecord(u.getIdUsuario(), u.getDni(), u.getNombre(), u.getUsername(), u.getRol());
                }
                csv.flush();
            }
        }
    }
}
