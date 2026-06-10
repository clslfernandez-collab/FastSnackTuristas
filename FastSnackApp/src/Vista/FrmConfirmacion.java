/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import BD.PedidoDAO;
import Modelo.ItemPedido;
import Modelo.Sesion;
import Modelo.Usuario;
import java.awt.*;
import javax.swing.*;

public class FrmConfirmacion extends JFrame {

    private static final double IVA_PORC = 0.15;
    private static final double COSTO_ENVIO = 1.50;

    private String numeroPedido;
    private double subtotal, iva, total;
    private String tiempoEstimado;
    private JComboBox<String> cmbPago;
    private JTextArea txtPersonalizacion;

    public FrmConfirmacion() {
        setTitle("Confirmacion de Pedido");
        setSize(560, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PedidoDAO pedidoDAO = new PedidoDAO();
        numeroPedido = pedidoDAO.generarNumeroPedido();

        subtotal = Sesion.subtotal();
        iva = subtotal * IVA_PORC;
        total = subtotal + iva + COSTO_ENVIO;
        tiempoEstimado = calcularTiempo(Sesion.totalUnidades());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel tit = new JLabel("Confirmacion de Pedido");
        tit.setFont(new Font("Arial", Font.BOLD, 22));
        tit.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(tit);

        JLabel num = new JLabel("Pedido N° " + numeroPedido);
        num.setFont(new Font("Arial", Font.BOLD, 18));
        num.setForeground(new Color(255, 102, 51));
        num.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(num);
        p.add(Box.createVerticalStrut(10));

        Usuario u = Sesion.usuarioActual;
        String datos = (u != null)
                ? "<html><b>Cliente:</b> " + u.getNombre()
                  + "<br><b>Telefono:</b> " + valor(u.getTelefono())
                  + "<br><b>Direccion:</b> " + valor(u.getDireccion()) + "</html>"
                : "<html><b>Cliente:</b> (sin sesion)</html>";
        p.add(bloque("Datos del cliente", datos));

        StringBuilder sb = new StringBuilder("<html>");
        for (ItemPedido it : Sesion.carrito) {
            sb.append(it.getCantidad()).append(" x ")
              .append(it.getProducto().getNombre())
              .append("  -  $").append(String.format("%.2f", it.getSubtotal()))
              .append("<br>");
        }
        sb.append("</html>");
        p.add(bloque("Productos solicitados", sb.toString()));

        String totales = "<html>"
                + "Subtotal: $" + String.format("%.2f", subtotal) + "<br>"
                + "IVA (15%): $" + String.format("%.2f", iva) + "<br>"
                + "Costo de envio: $" + String.format("%.2f", COSTO_ENVIO) + "<br>"
                + "<b>Total a pagar: $" + String.format("%.2f", total) + "</b></html>";
        p.add(bloque("Resumen de pago", totales));

        JPanel pago = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pago.setBackground(Color.WHITE);
        pago.add(new JLabel("Metodo de pago:"));
        cmbPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia", "Tarjeta"});
        pago.add(cmbPago);
        pago.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(pago);

        JLabel tiempo = new JLabel("Tiempo estimado de entrega: " + tiempoEstimado);
        tiempo.setFont(new Font("Arial", Font.BOLD, 14));
        tiempo.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(tiempo);
        p.add(Box.createVerticalStrut(8));

        JLabel lblPer = new JLabel("¿Algo que desee personalizar de su pedido?");
        lblPer.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lblPer);
        txtPersonalizacion = new JTextArea(3, 30);
        txtPersonalizacion.setLineWrap(true);
        txtPersonalizacion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtPersonalizacion.setText("Ej: sin cebolla, salsas aparte...");
        JScrollPane sp = new JScrollPane(txtPersonalizacion);
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.setMaximumSize(new Dimension(500, 80));
        p.add(sp);
        p.add(Box.createVerticalStrut(12));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botones.setBackground(Color.WHITE);
        JButton btnConfirmar = new JButton("Confirmar pedido");
        btnConfirmar.setBackground(new Color(102, 204, 102));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        JButton btnCancelar = new JButton("Cancelar pedido");
        btnCancelar.setBackground(new Color(255, 102, 102));
        btnCancelar.setForeground(Color.WHITE);
        JButton btnMenu = new JButton("Volver al menu");
        botones.add(btnConfirmar);
        botones.add(btnCancelar);
        botones.add(btnMenu);
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(botones);

        add(new JScrollPane(p));

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> {
            Sesion.limpiarCarrito();
            JOptionPane.showMessageDialog(this, "Pedido cancelado.");
            new FrmInicioApp().setVisible(true);
            dispose();
        });
        btnMenu.addActionListener(e -> { new FrmMenu().setVisible(true); dispose(); });
    }

    private void confirmar() {
        String detalle = construirDetalle();
        String docCli = (Sesion.usuarioActual != null) ? Sesion.usuarioActual.getDocumento() : "N/A";
        String metodo = (String) cmbPago.getSelectedItem();
        String personal = txtPersonalizacion.getText().trim();

        PedidoDAO dao = new PedidoDAO();
        boolean ok = dao.guardar(numeroPedido, docCli, detalle,
                subtotal, iva, COSTO_ENVIO, total, metodo, personal, tiempoEstimado);

        if (ok) {
            new FrmExito(numeroPedido, tiempoEstimado).setVisible(true);
            Sesion.limpiarCarrito();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el pedido. Revisa la conexion con MySQL.");
        }
    }

    private String construirDetalle() {
        StringBuilder sb = new StringBuilder();
        for (ItemPedido it : Sesion.carrito) {
            sb.append(it.getCantidad()).append("x ")
              .append(it.getProducto().getNombre())
              .append(" ($").append(String.format("%.2f", it.getSubtotal())).append("); ");
        }
        return sb.toString();
    }

    private String calcularTiempo(int unidades) {
        if (unidades >= 5) return "45 minutos o mas";
        if (unidades >= 2) return "30 minutos";
        return "15 minutos";
    }

    private String valor(String s) { return (s == null || s.isEmpty()) ? "(no registrado)" : s; }

    private JPanel bloque(String titulo, String htmlContenido) {
        JPanel b = new JPanel(new BorderLayout());
        b.setBackground(new Color(255, 248, 240));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 178, 102)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Arial", Font.BOLD, 14));
        b.add(t, BorderLayout.NORTH);
        b.add(new JLabel(htmlContenido), BorderLayout.CENTER);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(520, 1000));
        return b;
    }
}