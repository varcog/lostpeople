/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Anuncio;
import datos.Contacto;
import datos.Desaparecido;
import datos.Imagen;
import datos.Usuario;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.DBHelper;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Cristofer
 */
public class AnuncioController {

    public static JSONArray listarAnuncios() {
        try {
            JSONArray arr = new JSONArray();
            List<Anuncio> anuncios = DBHelper.getHelper().buscarTodos(Anuncio.class);
            JSONObject o;
            Anuncio a;
            Desaparecido d;
            Imagen i;
            for (int j = 0; j < anuncios.size(); j++) {
                a = anuncios.get(j);
                o = new JSONObject();
                d = a.getDesaparecido();
                o.put("nombre", d.getNombre());
                o.put("estatura", d.getEstatura());
                o.put("edad", d.getEdad());
                o.put("colorpiel", d.getColorpiel());
                i = a.getImagen();
                if (i != null) {
                    o.put("imagen", cargarImagenToBase64(i.getDirarchivo()));
                }
                arr.put(o);
            }
            return arr;
        } catch (Exception ex) {
            Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, null, ex);
            return new JSONArray();
        }
    }

    public static JSONObject anunciar(String nombre_c, String apellido_c, String sexo_c, String parentesco_c, int telefono_c, String email_c,
            String nombre_d, String apellido_d, int edad_d, String sexo_d, int estatura_d, String colorpelo_d, String colorpiel_d, String constitucion_d, String otros_d,
            String nombre_imagen, int id_usuario) throws JSONException {
        try {
            Contacto c = new Contacto(0, nombre_d, apellido_d);
            c.setSexo(sexo_c);
            c.setParentesco(parentesco_c);
            c.setTelefono(telefono_c);
            c.setEmail(email_c);
            int id_c = DBHelper.getHelper().add(c);
            List l = DBHelper.getHelper().buscar(Contacto.class, "contactoid", id_c + "");
            c = (Contacto) l.get(0);
            Desaparecido d = new Desaparecido(0, nombre_d, apellido_d, edad_d, sexo_d);
            d.setEstatura(estatura_d);
            d.setColorpelo(colorpelo_d);
            d.setColorpiel(colorpiel_d);
            d.setConstitucion(constitucion_d);
            d.setOtros(otros_d);
            int id_d = DBHelper.getHelper().add(d);
            l = DBHelper.getHelper().buscar(Desaparecido.class, "desaparecidoid", id_d + "");
            d = (Desaparecido) l.get(0);
            Imagen i = null;
            if (nombre_imagen != null) {
                i = new Imagen();
                i.setDirarchivo(nombre_imagen);
                int id_i = DBHelper.getHelper().add(i);
                l = DBHelper.getHelper().buscar(Imagen.class, "imagenid", id_i + "");
                i = (Imagen) l.get(0);
            }
            l = DBHelper.getHelper().buscar(Usuario.class, "usuarioid", id_usuario + "");
            Usuario u = (Usuario) l.get(0);
            Anuncio a = new Anuncio();
            a.setContacto(c);
            a.setDesaparecido(d);
            a.setImagen(i);
            a.setUsuario(u);
            Date da = new Date();
            a.setFechacre(da);
            a.setFechamod(da);
            a.setEstado("  ");
            DBHelper.getHelper().add(a);
            JSONObject o = new JSONObject();
            o.put("res", true);
            return o;
        } catch (Exception ex) {
            JSONObject o = new JSONObject();
            o.put("res", false);
            return o;
        }
    }

    public static JSONObject verAnuncio() {
        return null;
    }

    public static JSONObject comentar() {
        return null;
    }

    public static String FOLDER_IMAGEN = "C:\\Imagenes\\";

    public static String cargarImagenToBase64(String nombre_imagen) {

        FileInputStream fis = null;
        try {
            //encode image to Base64 String
            File f = new File(FOLDER_IMAGEN + nombre_imagen);
            fis = new FileInputStream(f);
            byte byteArray[] = new byte[(int) f.length()];
            fis.read(byteArray);
            String imageString = Base64.encodeBase64String(byteArray);
            //        //decode Base64 String to image
//        FileOutputStream fos = new FileOutputStream("H:/decode/destinationImage.png"); //change path of image according to you
//        byteArray = Base64.decodeBase64(imageString);
//        fos.write(byteArray);
            fis.close();
            //        fos.close();
            return imageString;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public static boolean guardarImagen(InputStream input, String fileName) {
        FileOutputStream output = null;
        boolean ok = false;
        try {
            output = new FileOutputStream(FOLDER_IMAGEN + fileName);
            int leido = 0;
            leido = input.read();
            while (leido != -1) {
                output.write(leido);
                leido = input.read();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, ex.getMessage());
        } finally {
            try {
                output.flush();
                output.close();
                input.close();
                ok = true;
            } catch (IOException ex) {
                Logger.getLogger(AnuncioController.class.getName()).log(Level.SEVERE, "Error cerrando flujo de salida", ex);
            }
        }
        return ok;
    }

    public static void main(String[] args) throws JSONException {
//        anunciar("nombre_c", "apellido_c", "sexo_c", "parentesco_c", 100, "email_c", "nombre_d", "apellido_d", 10, "sexo_d", 150,"colorpelo_d" , "color_piel_d", "constitucion_d", "otros_d", null, 3);
        System.out.println(listarAnuncios().toString());
    }
}
