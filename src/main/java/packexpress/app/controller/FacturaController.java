package packexpress.app.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import packexpress.app.model.Pedido;
import packexpress.app.repository.PedidoRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RestController
public class FacturaController {

    @Autowired
    private PedidoRepository repo;

    @GetMapping("/pedido/factura")
    public void generarFactura(@RequestParam Long id, HttpServletResponse response) throws IOException {
        Optional<Pedido> pedidoOpt = repo.findById(id);

        if (!pedidoOpt.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado");
            return;
        }

        Pedido pedido = pedidoOpt.get();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura_pedido_" + pedido.getId() + ".pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Logo
            Image logo = Image.getInstance("src/main/resources/static/images/logoexpress.jpg");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);

            // Título
            Font titulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.ORANGE);
            Paragraph encabezado = new Paragraph("Factura de Envío - PackExpress", titulo);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fecha: " + LocalDate.now()));
            document.add(new Paragraph("Número de pedido: " + pedido.getId()));
            document.add(new Paragraph("Cliente: " + pedido.getCliente()));
            document.add(new Paragraph("Dirección: " + pedido.getDireccion()));
            document.add(new Paragraph("Contenido: " + pedido.getContenido()));
            document.add(new Paragraph("Valor estimado: $" + pedido.getValor()));
            document.add(new Paragraph("Asegurado: " + (pedido.isAsegurado() ? "Sí" : "No")));
            document.add(new Paragraph("Fecha estimada de entrega: " + pedido.getFechaEntrega()));
            document.add(new Paragraph("Estado actual: " + pedido.getEstado()));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
