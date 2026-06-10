/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Catalogo;
import Modelo.ItemPedido;
import Modelo.Producto;
import Modelo.Sesion;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmMenu extends JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel lblTotal;

    public FrmMenu() {
        setTitle("FastSnack - Menu");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerLocation(480);

        JPanel menu = new JPanel(new GridLayout(0, 2, 10, 10));
        menu.setBackground(Color.WHITE);
        menu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Producto pr : Catalogo.getProductos()) {
            menu.add(crearTarjetaMenu(pr));
        }
        JScrollPane scrollMenu = new JScrollPane(menu);
        scrollMenu.getVerticalScrollBar().setUnitIncrement(16);
        split.setLeftComponent(scrollMenu);

        JPanel der = new JPanel(new BorderLayout(8, 8));
        der.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        der.setBackground(new Color(255, 245, 238));

        JLabel titCarrito = new JLabel("Tu pedido");
        titCarrito.setFont(new Font("Arial", Font.BOLD, 18));
        der.add(titCarrito, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new Object[]{"Producto", "Cant.", "P. Unit.", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        der.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelInf = new JPanel(new GridLayout(0, 1, 5, 5));
        panelInf.setBackground(new Color(255, 245, 238));

        JButton btnQuitar = new JButton("Quitar producto seleccionado");
        lblTotal = new JLabel("TOTAL: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnCulminar = new JButton("¿Desea culminar su pedido?");
        btnCulminar.setBackground(new Color(255, 102, 51));
        btnCulminar.setForeground(Color.WHITE);
        btnCulminar.setFont(new Font("Arial", Font.BOLD, 15));

        JButton btnVolver = new JButton("Volver al inicio");

        panelInf.add(btnQuitar);
        panelInf.add(lblTotal);
        panelInf.add(btnCulminar);
        panelInf.add(btnVolver);
        der.add(panelInf, BorderLayout.SOUTH);

        split.setRightComponent(der);
        add(split);

        refrescarTabla();

        btnQuitar.addActionListener(e -> quitarSeleccion());
        btnVolver.addActionListener(e -> { new FrmInicioApp().setVisible(true); dispose(); });
        btnCulminar.addActionListener(e -> {
            if (Sesion.carrito.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agrega al menos un producto.");
                return;
            }
            new FrmConfirmacion().setVisible(true);
            dispose();
        });
    }

    private JPanel crearTarjetaMenu(Producto pr) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 153, 102), 2));
        card.setBackground(new Color(255, 250, 245));

        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon ic = Img.cargar(pr.getImagen(), 120, 90);
        if (ic != null) img.setIcon(ic); else img.setText("(sin imagen)");
        card.add(img, BorderLayout.CENTER);

        JLabel nombre = new JLabel("<html><b>" + pr.getNombre() + "</b><br>$"
                + String.format("%.2f", pr.getPrecio()) + " c/u</html>");
        nombre.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(nombre, BorderLayout.NORTH);

        JPanel abajo = new JPanel();
        abajo.setBackground(new Color(255, 250, 245));
        abajo.add(new JLabel("Cant:"));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        spinner.setPreferredSize(new Dimension(50, 24));
        abajo.add(spinner);
        JButton btnAdd = new JButton("Agregar");
        btnAdd.setBackground(new Color(255, 178, 102));
        abajo.add(btnAdd);
        card.add(abajo, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            int cant = (Integer) spinner.getValue();
            agregarAlCarrito(pr, cant);
        });

        return card;
    }

    private void agregarAlCarrito(Producto pr, int cant) {
        for (ItemPedido it : Sesion.carrito) {
            if (it.getProducto().getNombre().equals(pr.getNombre())) {
                it.setCantidad(it.getCantidad() + cant);
                refrescarTabla();
                return;
            }
        }
        Sesion.carrito.add(new ItemPedido(pr, cant));
        refrescarTabla();
    }

    private void quitarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla.");
            return;
        }
        Sesion.carrito.remove(fila);
        refrescarTabla();
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);
        for (ItemPedido it : Sesion.carrito) {
            modelo.addRow(new Object[]{
                it.getProducto().getNombre(),
                it.getCantidad(),
                "$" + String.format("%.2f", it.getProducto().getPrecio()),
                "$" + String.format("%.2f", it.getSubtotal())
            });
        }
        lblTotal.setText("TOTAL: $" + String.format("%.2f", Sesion.subtotal()));
    }
}
