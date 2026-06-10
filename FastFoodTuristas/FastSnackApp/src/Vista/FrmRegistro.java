/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import BD.UsuarioDAO;
import Modelo.Usuario;
import java.awt.*;
import javax.swing.*;

public class FrmRegistro extends JFrame {

    private final String tipoUsuario;
    private JTextField txtNombre, txtCorreo, txtTelefono, txtDocumento, txtDireccion;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbTipoDoc;

    public FrmRegistro(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;

        setTitle("Registro de " + tipoUsuario);
        setSize(430, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(255, 245, 238));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.gridwidth = 2;

        JLabel titulo = new JLabel("Registro - " + tipoUsuario);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 0; p.add(titulo, g);

        g.gridwidth = 1;
        txtNombre = addCampo(p, g, 1, "Nombre completo:");
        txtCorreo = addCampo(p, g, 2, "Correo electronico:");
        txtContrasena = new JPasswordField();
        addFila(p, g, 3, "Contraseña:", txtContrasena);
        txtTelefono = addCampo(p, g, 4, "Telefono:");

        g.gridx = 0; g.gridy = 5; p.add(new JLabel("Tipo de documento:"), g);
        if (tipoUsuario.equals("EMPLEADO")) {
            cmbTipoDoc = new JComboBox<>(new String[]{"CEDULA"});
        } else {
            cmbTipoDoc = new JComboBox<>(new String[]{"CEDULA", "RUC", "PASAPORTE", "OTRO"});
        }
        g.gridx = 1; p.add(cmbTipoDoc, g);

        txtDocumento = addCampo(p, g, 6, "Numero de documento:");
        txtDireccion = addCampo(p, g, 7, "Direccion:");

        JLabel ayuda = new JLabel("<html><i>Cedula: 10 digitos | RUC: 13 digitos<br>"
                + "Pasaporte/Otro: letras y numeros</i></html>");
        ayuda.setFont(new Font("Arial", Font.PLAIN, 11));
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2; p.add(ayuda, g);

        JButton btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setBackground(new Color(255, 153, 102));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 15));
        g.gridy = 9; p.add(btnRegistrar, g);

        JButton btnVolver = new JButton("Volver");
        g.gridy = 10; p.add(btnVolver, g);

        add(p);

        btnRegistrar.addActionListener(e -> registrar());
        btnVolver.addActionListener(e -> { new FrmBienvenida().setVisible(true); dispose(); });
    }

    private JTextField addCampo(JPanel p, GridBagConstraints g, int fila, String etiqueta) {
        JTextField campo = new JTextField();
        addFila(p, g, fila, etiqueta, campo);
        return campo;
    }

    private void addFila(JPanel p, GridBagConstraints g, int fila, String etiqueta, JComponent campo) {
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = fila; p.add(new JLabel(etiqueta), g);
        g.gridx = 1; campo.setPreferredSize(new Dimension(180, 26)); p.add(campo, g);
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contra = new String(txtContrasena.getPassword()).trim();
        String tel = txtTelefono.getText().trim();
        String tipoDoc = (String) cmbTipoDoc.getSelectedItem();
        String doc = txtDocumento.getText().trim();
        String dir = txtDireccion.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || doc.isEmpty()) {
            msg("Completa todos los campos obligatorios.");
            return;
        }
        if (!UsuarioDAO.validarCorreo(correo)) {
            msg("El correo no tiene un formato valido.");
            return;
        }
        try {
            UsuarioDAO.validarDocumentoConExcepcion(tipoDoc, doc);
        } catch (Modelo.DocumentoInvalidoException ex) {
            msg(ex.getMessage());
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.existeDocumento(doc)) {
            msg("Ya existe un usuario registrado con ese documento.");
            return;
        }

        Usuario u = new Usuario(tipoUsuario, nombre, correo, contra, tel, tipoDoc, doc, dir);
        if (dao.registrar(u)) {
            JOptionPane.showMessageDialog(this,
                    "Registro exitoso. Ahora inicia sesion con tu documento.");
            new FrmLogin().setVisible(true);
            dispose();
        } else {
            msg("No se pudo registrar. Revisa la conexion con MySQL.");
        }
    }

    private void msg(String t) {
        JOptionPane.showMessageDialog(this, t);
    }
}
