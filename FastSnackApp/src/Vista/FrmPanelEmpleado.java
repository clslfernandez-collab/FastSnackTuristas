/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import BD.PedidoDAO;
import BD.ProductoDAO;
import BD.UsuarioDAO;
import Modelo.Sesion;
import Modelo.Usuario;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel del EMPLEADO. Tiene 3 pestañas: Pedidos, Clientes e Inventario.
 * Aqui llega el empleado tras iniciar sesion (en lugar del flujo de pedido).
 */
public class FrmPanelEmpleado extends JFrame {

    public FrmPanelEmpleado() {
        setTitle("Panel del Empleado - FastSnack");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(255, 153, 102));
        header.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        String nombre = (Sesion.usuarioActual != null) ? Sesion.usuarioActual.getNombre() : "Empleado";
        JLabel titulo = new JLabel("Panel del Empleado  -  " + nombre);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);
        JButton btnSalir = new JButton("Cerrar sesion");
        btnSalir.addActionListener(e -> { new FrmBienvenida().setVisible(true); dispose(); });
        header.add(btnSalir, BorderLayout.EAST);
        principal.add(header, BorderLayout.NORTH);

        // Pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Pedidos", crearPanelPedidos());
        tabs.addTab("Clientes", crearPanelClientes());
        tabs.addTab("Inventario", crearPanelInventario());
        principal.add(tabs, BorderLayout.CENTER);

        add(principal);
    }

    // ---------- Pestaña PEDIDOS ----------
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"N° Pedido", "Cliente (doc)", "Detalle", "Total", "Pago", "Tiempo", "Fecha"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        PedidoDAO dao = new PedidoDAO();
        for (Object[] fila : dao.listarPedidos()) {
            modelo.addRow(fila);
        }
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ---------- Pestaña CLIENTES ----------
    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Nombre", "Correo", "Telefono", "Tipo Doc", "Documento", "Direccion"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> clientes = dao.listarPorTipo("CLIENTE");
        for (Usuario u : clientes) {
            modelo.addRow(new Object[]{
                u.getNombre(), u.getCorreo(), u.getTelefono(),
                u.getTipoDoc(), u.getDocumento(), u.getDireccion()
            });
        }
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ---------- Pestaña INVENTARIO ----------
    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Producto", "Precio", "Stock"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(modelo);
        ProductoDAO dao = new ProductoDAO();
        for (Object[] fila : dao.listarProductos()) {
            modelo.addRow(fila);
        }
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Zona para actualizar stock
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        abajo.add(new JLabel("ID producto:"));
        JTextField txtId = new JTextField(4);
        abajo.add(txtId);
        abajo.add(new JLabel("Nuevo stock:"));
        JTextField txtStock = new JTextField(4);
        abajo.add(txtStock);
        JButton btnActualizar = new JButton("Actualizar stock");
        abajo.add(btnActualizar);
        panel.add(abajo, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                if (dao.actualizarStock(id, stock)) {
                    JOptionPane.showMessageDialog(this, "Stock actualizado.");
                    // refrescar tabla
                    modelo.setRowCount(0);
                    for (Object[] fila : dao.listarProductos()) modelo.addRow(fila);
                    txtId.setText(""); txtStock.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar. Revisa el ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingresa numeros validos en ID y stock.");
            }
        });

        return panel;
    }
}
