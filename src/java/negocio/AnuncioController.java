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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.DBHelper;

/**
 *
 * @author Cristofer
 */
public class AnuncioController {

    public static JSONArray listarAnuncios() {
        JSONArray a = new JSONArray();
        return a;
    }

    public static JSONObject anunciar(String nombre_c, String apellido_c, String sexo_c, String parentesco_c, int telefono_c, String email_c,
            String nombre_d, String apellido_d, int edad_d, String sexo_d, int estatura_d, String colorpelo_d, String colorpiel_d, String constitucion_d, String otros_d,
            String direccion_imagen, int id_usuario) throws JSONException {
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
            Imagen i = new Imagen();
            i.setDirarchivo(direccion_imagen);
            int id_i = DBHelper.getHelper().add(i);
            l = DBHelper.getHelper().buscar(Imagen.class, "imagenid", id_i + "");
            i = (Imagen) l.get(0);
            l = DBHelper.getHelper().buscar(Usuario.class, "usuarioid", id_i + "");
            Usuario u = (Usuario) l.get(0);
            Anuncio a =new Anuncio();
            a.setContacto(c);
            a.setDesaparecido(d);
            a.setImagen(i);
            a.setUsuario(u);
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
}
