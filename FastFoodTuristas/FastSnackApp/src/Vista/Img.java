/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;

public class Img {

    public static ImageIcon cargar(String nombreArchivo, int ancho, int alto) {
        try {
            java.net.URL url = Img.class.getResource("/Imagenes/" + nombreArchivo);
            if (url == null) {
                System.err.println("No se encontro la imagen: /Imagenes/" + nombreArchivo);
                return null;
            }
            ImageIcon original = new ImageIcon(url);
            Image src = original.getImage();

            BufferedImage destino = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = destino.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(src, 0, 0, ancho, alto, null);
            g.dispose();

            return new ImageIcon(destino);
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + nombreArchivo + ": " + e.getMessage());
            return null;
        }
    }
}