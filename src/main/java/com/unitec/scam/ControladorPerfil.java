package com.unitec.scam;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Representantional State Transfer Controller
//Los estados mas comunes son: 
//Guardar, Buscar, Actualizar y Borrar
@RestController
//api son las siglas de Application  Programming Interface
@RequestMapping("/api")
public class ControladorPerfil {

    //esta es la inversion de control o inyeccion de dependencias
    @Autowired
    RepoPerfil repoPerfil;

    //en los servicios REST se tiene una urlBase que consiste
    //de la ip o host, seguida del puerto, despues /api/hola
    //Es decir, para este caso mi api REST es:
    // http://localhost:8080/api/hola
    @GetMapping("/hola")
    public Saludo saludar() {
        Saludo s = new Saludo();
        s.setNombre("Jose Marcos");
        s.setMensaje("Mi primer mensaje en spring rest");
        return s;
    }

    //el siguiente metodo va a servir para guardar en un back-end nuestros datos
    //del perfil
    //para guardar siempre debes usar el metodo POST
    @PostMapping("/perfil")
    public Estatus guardar(@RequestBody String json) throws Exception {
        //paso 1: para recibir ese objeto json es leerlo y convertirlo
        //en onjeto JAVA a esto se le llama des-serializacion
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
//Por experiencia antes de guardar tenemos que checar que llego bien
//todo el objeto y se leyo bien
        System.out.println("Perfil leido " + perfil);
//aqui este objeto perfil despues se guarda en una sola linea en mongodb
        //aqui va ir la linea para guardar
        repoPerfil.save(perfil);
        //despues enviamos un mensaje de estatus del cliente para que se informe
        //si se guardo su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil guardado con exito!!");
        return e;

    }
    //Vamos a generar nuestros servicio para actualizar un perfil

    @PutMapping("/perfil")
    public Estatus actualizar(@RequestBody String json) throws Exception {
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
//Por experiencia antes de guardar tenemos que checar que llego bien
//todo el objeto y se leyo bien
        System.out.println("Perfil leido " + perfil);
//aqui este objeto perfil despues se guarda en una sola linea en mongodb
        //aqui va ir la linea para guardar
        repoPerfil.save(perfil);
        //despues enviamos un mensaje de estatus del cliente para que se informe
        //si se guardo su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil actualizado con exito!!");
        return e;
    }

    //El metodo para borrar un perfil
    @DeleteMapping("/perfil/{id}")
    public Estatus borrar(@PathVariable String id) {
        //invocamos el repositorio
        repoPerfil.deleteById(id);
        //generamos el mensaje de estatus para que este informado el cliente
        Estatus e = new Estatus();
        e.setMensaje("Perfil borrado con éxito");
        e.setSuccess(true);
        return e;
    }

    //el metodo para buscar todos
    @GetMapping("/perfil")
    public List<Perfil> buscarTodos() {
        return repoPerfil.findAll();
    }

    //Finalmente el de buscar por id
    @GetMapping("/perfil/{id}")
    public Perfil buscarPorId(@PathVariable String id) {
        return repoPerfil.findById(id).get();
    }

}
//a este tipo de controlador estilo Rest es muy
//poderoso y se usa en todas las arquitecturas estilo 
//Rest, y se denomina construccion de API´s
//API== Application Programming Interface
//la interface en este caso es la union entre cliente(android)
//y servidor(Java)