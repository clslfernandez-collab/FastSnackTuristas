/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.*;
import javax.swing.*;

public class FrmBienvenida extends JFrame {

    public FrmBienvenida() {
        setTitle("FastSnack - Bienvenida");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 240, 230));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.insets = new Insets(12, 12, 12, 12);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel logo = new JLabel();
        ImageIcon icono = Img.cargar("Logo.png", 160, 120);
        if (icono != null) {
            logo.setIcon(icono);
        } else {
            logo.setText("FastSnack");
            logo.setFont(new Font("Arial", Font.BOLD, 28));
        }
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 0; panel.add(logo, g);

        JLabel titulo = new JLabel("Bienvenido a FastSnack");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 1; panel.add(titulo, g);

        JButton btnLogin = new JButton("Iniciar Sesion");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
        btnLogin.setBackground(new Color(255, 153, 102));
        g.gridy = 2; panel.add(btnLogin, g);

        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.setFont(new Font("Arial", Font.PLAIN, 16));
        btnRegistro.setBackground(new Color(255, 204, 153));
        g.gridy = 3; panel.add(btnRegistro, g);

        add(panel);

        btnLogin.addActionListener(e -> {
            new FrmLogin().setVisible(true);
            dispose();
        });

        btnRegistro.addActionListener(e -> {
            String tipo = preguntarTipoUsuario();
            if (tipo != null) {
                new FrmRegistro(tipo).setVisible(true);
                dispose();
            }
        });
    }

    private String preguntarTipoUsuario() {
        Object[] opciones = {"Cliente", "Empleado"};
        int op = JOptionPane.showOptionDialog(this,
                "¿Como deseas registrarte?",
                "Tipo de usuario",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
        if (op == 0) return "CLIENTE";
        if (op == 1) return "EMPLEADO";
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmBienvenida().setVisible(true));
    }
} 
