/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.*;
import javax.swing.*;

public class FrmExito extends JFrame {

    public FrmExito(String numeroPedido, String tiempoEstimado) {
        setTitle("Pedido realizado");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(240, 255, 240));
        p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel check = new JLabel("✔");
        check.setFont(new Font("Arial", Font.BOLD, 50));
        check.setForeground(new Color(102, 204, 102));
        check.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(check);
        
        JLabel imgRepartidor = new JLabel();
        imgRepartidor.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon iconoRep = Img.cargar("repartidor.png", 180, 130);
        if (iconoRep != null) {
            imgRepartidor.setIcon(iconoRep);
            p.add(Box.createVerticalStrut(10));
            p.add(imgRepartidor);
        }

        JLabel tit = new JLabel("¡Pedido realizado con exito!");
        tit.setFont(new Font("Arial", Font.BOLD, 20));
        tit.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(tit);
        p.add(Box.createVerticalStrut(12));

        JLabel msg = new JLabel("<html><div style='text-align:center;width:380px'>"
                + "Su pedido <b>" + numeroPedido + "</b> ha sido recibido y esta siendo procesado."
                + "<br><br>Tiempo estimado: <b>" + tiempoEstimado + "</b></div></html>");
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(msg);
        p.add(Box.createVerticalStrut(20));

        JLabel gracias = new JLabel("Gracias por comprar en FastSnack");
        gracias.setFont(new Font("Arial", Font.ITALIC, 15));
        gracias.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(gracias);
        p.add(Box.createVerticalGlue());

        JPanel pieDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pieDerecha.setBackground(new Color(240, 255, 240));
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        pieDerecha.add(btnSalir);

        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);
        add(pieDerecha, BorderLayout.SOUTH);
    }
}
