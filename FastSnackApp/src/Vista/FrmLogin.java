/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import BD.UsuarioDAO;
import Modelo.Sesion;
import Modelo.Usuario;
import java.awt.*;
import javax.swing.*;

public class FrmLogin extends JFrame {

    private JTextField txtDocumento;
    private JPasswordField txtContrasena;

    public FrmLogin() {
        setTitle("Iniciar Sesion");
        setSize(380, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(255, 240, 230));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Iniciar Sesion");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; p.add(titulo, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; p.add(new JLabel("Documento:"), g);
        txtDocumento = new JTextField();
        txtDocumento.setPreferredSize(new Dimension(170, 26));
        g.gridx = 1; p.add(txtDocumento, g);

        g.gridx = 0; g.gridy = 2; p.add(new JLabel("Contraseña:"), g);
        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(170, 26));
        g.gridx = 1; p.add(txtContrasena, g);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(255, 153, 102));
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 15));
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2; p.add(btnEntrar, g);

        JButton btnVolver = new JButton("Volver");
        g.gridy = 4; p.add(btnVolver, g);

        add(p);

        btnEntrar.addActionListener(e -> entrar());
        btnVolver.addActionListener(e -> { new FrmBienvenida().setVisible(true); dispose(); });
    }

    private void entrar() {
        String doc = txtDocumento.getText().trim();
        String contra = new String(txtContrasena.getPassword()).trim();

        if (doc.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa documento y contraseña.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.login(doc, contra);

        if (u != null) {
            Sesion.usuarioActual = u;
            Sesion.limpiarCarrito();
            JOptionPane.showMessageDialog(this, "Bienvenido, " + u.getNombre() + ".");

            // Si es EMPLEADO va al panel; si es CLIENTE va al inicio normal
            if (u.getTipoUsuario().equals("EMPLEADO")) {
                new FrmPanelEmpleado().setVisible(true);
            } else {
                new FrmInicioApp().setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Documento o contraseña incorrectos, o no estas registrado.");
        }
    }
}
