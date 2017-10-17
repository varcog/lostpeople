/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Usuario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import util.DBHelper;

/**
 *
 * @author Cristofer
 */
public class UsuarioController {

    public static JSONObject registrarUsuario(String nombre, String apelido, String sexo, String email, String pass, String estado) throws JSONException {
        List lista = DBHelper.getHelper().buscar(Usuario.class, "email", "'" + email + "'");
        JSONObject o = new JSONObject();
        if (lista.size() > 0) {
            o.put("res", false);
            o.put("mensaje", "EMAIL YA REGISTRADO");

        } else {
            Usuario u = new Usuario(0, nombre, apelido, email, pass);
            Date f = new Date();
            u.setFechacre(f);
            u.setFechamod(f);
            u.setEstado(estado);
            u.setSexo(sexo);
            try {
                int id = DBHelper.getHelper().add(u);
                o.put("res", true);
                o.put("id", id);
                o.put("mensaje", "REGISTRADO CORRECTAMENTE");
            } catch (Exception ex) {
                o.put("res", false);
                o.put("mensaje", "ERROR AL CREAR USUARIO, INTENTELO DE NUEVO");
            }
        }
        return o;
    }
    
    public static JSONObject validarUsuario(String email, String contrasena) throws JSONException {
        List lista = DBHelper.getHelper().buscar(Usuario.class, "email", "'" + email + "'");
        JSONObject o = new JSONObject();
        if (lista.size() > 0) {
            Usuario u = (Usuario) lista.get(0);
            if(u.getPass().equals(contrasena)){
                o.put("res", true);
                o.put("id", u.getUsuarioid());
                o.put("mensaje", "USUARIO CORRECTO");
            }else{
                o.put("res", false);
                o.put("mensaje", "CONTRASEÑA INCORRECTA");
            }
        } else {
            o.put("res", false);
            o.put("mensaje", "EMAIL INCORRECTO");
        }
        return o;
    }

    public static void modificarUsuario(String nombre, String apelido, String sexo, String email, String pass, String estado) {

    }

    public static void main(String[] args) throws Exception {
        registrarUsuario("Gerardo", "vargas", "Masculino", "gerardo@gmail.com", "gerardo", "soltero");
    }
}
