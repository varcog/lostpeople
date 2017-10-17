/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import negocio.AnuncioController;
import negocio.UsuarioController;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Cristofer
 */
@WebService(serviceName = "desaparecido")
public class desaparecido {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registrarUsuario")
    public String registrarUsuario(@WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "sexo") String sexo, @WebParam(name = "email") String email, @WebParam(name = "pass") String pass, @WebParam(name = "estado") String estado) {
        try {
            return UsuarioController.registrarUsuario(nombre, apellido, sexo, email, pass, estado).toString();
        } catch (JSONException ex) {
            return "false";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ingresar")
    public String ingresar(@WebParam(name = "email") String email, @WebParam(name = "contrasena") String contrasena) {
        try {
            return UsuarioController.validarUsuario(email, contrasena).toString();
        } catch (JSONException ex) {
            return "false";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listarAnuncios")
    public String listarAnuncios() {
        return AnuncioController.listarAnuncios().toString();

    }
}
