package com.vfg.controllers.users;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vfg.repository.entities.User;
import com.vfg.services.UserServiceI;

/**
 * Controlador principal de acceso a la administración de usuarios.
 *
 * @author vifergo
 * @since v0.1
 *
 */
@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * Servicio de usuarios.
     */
    @Autowired
    private UserServiceI userService;

    /**
     * @return Listado con todos los usuarios de la aplicacion.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final Collection<User> userList() {
        return userService.findUsers();
    }

    /**
     * Busca un usuario concreto en la base de datos.
     *
     * @param idUser identificador del usuario.
     * @return Usuario correspondiente al id
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public final Map<String, Object> userDetails(@PathVariable("id") final String idUser) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", idUser);
        return model;
    }

    /**
     * Borrado lógico de un usuario de la aplicacion.
     *
     * @param idUser usuario que se quiere borrar.
     * @return informacion sobre el exito de la operacion.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final Map<String, Object> deleteUser(@PathVariable("id")final String idUser) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", idUser);
        return model;
    }
}
