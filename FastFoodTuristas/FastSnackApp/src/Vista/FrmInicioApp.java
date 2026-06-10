/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Catalogo;
import Modelo.Producto;
import Modelo.Sesion;
import java.awt.*;
import javax.swing.*;

public class FrmInicioApp extends JFrame {

    public FrmInicioApp() {
        setTitle("FastSnack - Inicio");
        setSize(640, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(Color.WHITE);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(255, 153, 102));
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel tit = new JLabel("FastSnack");
        tit.setFont(new Font("Arial", Font.BOLD, 30));
        tit.setForeground(Color.WHITE);
        tit.setAlignmentX(Component.CENTER_ALIGNMENT);

        String nombre = (Sesion.usuarioActual != null) ? Sesion.usuarioActual.getNombre() : "invitado";
        JLabel sub = new JLabel("Hola " + nombre + ", la mejor comida rapida de la ciudad");
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setForeground(Color.WHITE);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel quienes = new JLabel("<html><div style='text-align:center;width:520px'>"
                + "Somos FastSnack, un local de comida rapida dedicado a ofrecerte "
                + "hamburguesas, pizzas, hot dogs y combos con los mejores ingredientes "
                + "y al mejor precio. ¡Pide ya y disfruta!</div></html>");
        quienes.setForeground(Color.WHITE);
        quienes.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(tit);
        header.add(Box.createVerticalStrut(6));
        header.add(sub);
        header.add(Box.createVerticalStrut(6));
        header.add(quienes);
        principal.add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 2, 12, 12));
        grid.setBackground(Color.WHITE);
        grid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (Producto pr : Catalogo.getProductos()) {
            grid.add(crearTarjeta(pr));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        principal.add(scroll, BorderLayout.CENTER);

        JPanel pie = new JPanel();
        pie.setBackground(new Color(255, 240, 230));
        JButton btnPedir = new JButton("¿Deseas realizar tu pedido?");
        btnPedir.setFont(new Font("Arial", Font.BOLD, 16));
        btnPedir.setBackground(new Color(255, 102, 51));
        btnPedir.setForeground(Color.WHITE);
        pie.add(btnPedir);
        principal.add(pie, BorderLayout.SOUTH);

        add(principal);

        btnPedir.addActionListener(e -> { new FrmMenu().setVisible(true); dispose(); });
    }

    private JPanel crearTarjeta(Producto pr) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 153, 102), 2));
        card.setBackground(new Color(255, 250, 245));

        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon ic = Img.cargar(pr.getImagen(), 140, 100);
        if (ic != null) img.setIcon(ic); else img.setText("(sin imagen)");
        card.add(img, BorderLayout.CENTER);

        JLabel info = new JLabel("<html><b>" + pr.getNombre() + "</b><br>$"
                + String.format("%.2f", pr.getPrecio()) + "</html>");
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        card.add(info, BorderLayout.SOUTH);

        return card;
    }
}
