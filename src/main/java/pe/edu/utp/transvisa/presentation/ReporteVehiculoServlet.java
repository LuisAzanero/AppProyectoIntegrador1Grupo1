package pe.edu.utp.transvisa.presentation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.transvisa.domain.Vehiculo;
import pe.edu.utp.transvisa.persistence.MySQLVehiculoRepository;
import pe.edu.utp.transvisa.persistence.VehiculoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vehiculos/exportar")
public class ReporteVehiculoServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ReporteVehiculoServlet.class);
    private final VehiculoRepository vehiculoRepository = new MySQLVehiculoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("../login");
            return;
        }

        String tipo = request.getParameter("tipo"); // 'excel' o 'csv' solo agregamos estos 2 tipos de descarga
        try {
            List<Vehiculo> lista = vehiculoRepository.listarTodos();

            if ("excel".equalsIgnoreCase(tipo)) {
                exportarExcel(response, lista);
            } else if ("csv".equalsIgnoreCase(tipo)) {
                exportarCSV(response, lista);
            } else {
                response.sendRedirect("../vehiculos");
            }
        } catch (Exception e) {
            logger.error("Error al exportar reporte de flota vehicular", e);
            response.sendRedirect("../vehiculos");
        }
    }

    private void exportarExcel(HttpServletResponse response, List<Vehiculo> lista) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Flota_TRANSVISA.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Flota Vehicular");

            // Estilos del encabezado corporativo de la empresa
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // cabecera del excel
            String[] columnas = {"ID", "Placa", "Marca", "Modelo", "Kilometraje (Km)", "Estado Operativo"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // llenar registros
            int rowNum = 1;
            for (Vehiculo v : lista) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(v.getIdVehiculo());
                row.createCell(1).setCellValue(v.getPlaca());
                row.createCell(2).setCellValue(v.getMarca());
                row.createCell(3).setCellValue(v.getModelo());
                row.createCell(4).setCellValue(v.getKilometrajeActual().doubleValue());
                row.createCell(5).setCellValue(v.getEstadoOperativo());
            }

            // ajustar columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
            logger.info("Reporte Excel generado y descargado exitosamente.");
        }
    }

    private void exportarCSV(HttpServletResponse response, List<Vehiculo> lista) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_Flota_TRANSVISA.csv");

        // Forzar codificación UTF-8 con BOM para que Excel lea correctamente las tildes y eñes
        try (PrintWriter writer = response.getWriter()) {
            writer.write('\ufeff');

            try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Placa", "Marca", "Modelo", "Kilometraje_Actual", "Estado_Operativo"))) {

                for (Vehiculo v : lista) {
                    csvPrinter.printRecord(
                            v.getIdVehiculo(),
                            v.getPlaca(),
                            v.getMarca(),
                            v.getModelo(),
                            v.getKilometrajeActual(),
                            v.getEstadoOperativo()
                    );
                }
                csvPrinter.flush();
                logger.info("Reporte CSV generado y descargado exitosamente.");
            }
        }
    }
}
