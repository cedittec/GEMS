package com.GEMS1
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.converters.XML
import org.apache.commons.lang.RandomStringUtils

@Transactional(readOnly = true)
@Secured(['ROLE_SysAdmin'])
class UsuarioController {
    
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
     
    @Secured(['ROLE_SysAdmin','ROLE_adminTecnico' ])
    def index(Integer max) {
        //    params.max = Math.min(max ?: 10, 100)
        //    respond Usuario.list(params), model:[usuarioInstanceCount: Usuario.count()]        
        def roles =  Rol.list()
        def users = Usuario.list([sort:"dateCreated", order: "asc"])        
        def totalUsuarios = Usuario.count()
      
        [roles:roles,usuarios:users, totalUsuarios: totalUsuarios]
        
    }
    
    def show(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    def create() {
        respond new Usuario(params)
    }
    
    @Secured(['IS_AUTHENTICATED_FULLY'])
    def homeRedirect(){
       
        if(SpringSecurityUtils.ifAnyGranted("ROLE_adminTecnico,ROLE_clienteTecnico")){
            redirect controller: 'TiempoReal', action: 'index'
        }else{
            redirect controller: 'EstadoFinanciero', action: 'index'
        }
    }
    
    
    
    @Transactional
    def save2() {
        def msg = "Creación exitosa de usuario"
             
        def tel = new TelefonoUsuario(params)
            
            def user = (Usuario.findByEmail(params?.email))
            if(!user){
                
            params.password = generaPassword()    
            
               user= new Usuario(params).addToTelefono(tel)
              
                if (!user.save()) {
                    user.errors.each {
                        println it
                    }
                    flash.error = "No ha podido insertarse en la BD"
                    redirect(controller: "Usuario", action: "index")
                    return;
                }else
                {
                    def rol = Rol.findByDisplayName(params?.rol)                   
                    UsuarioRol.create user, rol, true                      
                }
            }else{
                flash.error = "Ese email ya esta dado de alta"
                redirect(controller: "Usuario", action: "index")
                return;
            }
            
       
        
        enviarCorreo(params.email, params.password)
        
        flash.message = msg
        redirect(controller: "Usuario", action: "index")
       
    }
    
    def generaPassword()
    {
        String charset = (('A'..'Z') + ('0'..'9')).join()
        Integer length = 9
        String randomString = RandomStringUtils.random(length, charset.toCharArray())
        return randomString
    }
    
    def enviarCorreo(email,pass)
    {
        sendMail {     
            to email
            from "energia@emsmx.com"
            subject "Cuenta GEMS realizada"
            body "Correo dado de alta: "+ email +"\n con la contraseña: "+pass +" \n\
            le pedimos cambiar su contraseña a una personal lo más pronto posible"
        }        
    }
    
    @Transactional
    def actualizarUsuario() {
        def msg = "Actualización exitosa de usuario"     
        def user = (Usuario.findByEmail(params?.email))
        
            if(user){
                //user = new Usuario(params)
                user.displayName = params.nombre1 + " "+params.apellido1
                user.properties = params
                params.usuario = user
                
                def rolActual = UsuarioRol.findByUsuario(user)
                def rolNuevo = Rol.findByDisplayName(params?.rol)      
                
                def tel = new TelefonoUsuario(params)
                def telBD = TelefonoUsuario.get(user.telefono.id)
                
               
                telBD.numTelefono = tel.numTelefono                
                telBD.tipoTelefono = tel.tipoTelefono
                
                if (!user.save(flush:true)) {
                    user.errors.each {
                        println it
                    }
                    flash.error = "No ha podido modificarse"
                    redirect(controller: "Usuario", action: "index")
                    return;
                }
                else{
                    if(rolActual.rol != rolNuevo){
                    UsuarioRol.remove user,rolActual.rol,true
                    UsuarioRol.create user,rolNuevo,true
                    }
                }
            
            }else{
                flash.error = "El correo ingresado no existe"
                redirect(controller: "Usuario", action: "index")
                return;
            }
            
        flash.message = msg
        redirect(controller: "Usuario", action: "index")
       
    }
    
    
    def infoUsuario(){
        def usuario
        def rol 
        def telefono 
        def contactoDeCliente
        def accesoSitios 
        def usuarioSitio
        def arregloAccesos = []
        
        if(params?.email){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
            usuario = Usuario.findByEmail(params.email)
            rol = UsuarioRol.findByUsuario(usuario)
            rol = Rol.findById(rol.rol.id)   
            telefono = usuario.telefono
            contactoDeCliente = Cliente.findAllByResponsableId(usuario.id)
            usuarioSitio = UsuarioSitio.findAllByUsuario(usuario)
            
            for(tupla in usuarioSitio)
            {
                def sitio = Sitio.get(tupla.sitioId)
                arregloAccesos.add(sitio.nombre)
            }
            
        }
       
        def resultado = []
        resultado.add(usuario)
        resultado.add(rol)
        resultado.add(telefono)
        resultado.add(contactoDeCliente)
        resultado.add(arregloAccesos)
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
    }
    
    @Transactional
    def eliminarUsuario() {
        def usuario = Usuario.findByEmail(params?.email)
        println "email"
        println params?.email
        println usuario
        if(usuario)
        {
            
            def userRoles = UsuarioRol.findAllByUsuario(usuario);
            userRoles*.delete();
            usuario.delete(flush:true);
                 flash.message = "Eliminacion realizada exitosamente"
                 redirect(controller: "Usuario", action: "index")
                 return;
            
            /*
            if (!usuario.delete(flush:true)) {
                
                usuario.errors.each {
                        println it
                }
                
                flash.error = "No ha podido eleminar en la BD"
                redirect(controller: "Usuario", action: "index")
                return;
                
            }else{
                 flash.message = "Eliminacion realizada exitosamente"
                 redirect(controller: "Usuario", action: "index")
                 return;
            }*/
        }
        else{
            flash.error = "El usuario no existe"
            redirect(controller: "Usuario", action: "index")
        }
    }

    

    @Transactional
    def save(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'create'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*' { respond usuarioInstance, [status: CREATED] }
        }
    }

    def edit(Usuario usuarioInstance) {
        respond usuarioInstance
    }

    @Transactional
    def update(Usuario usuarioInstance) {
        if (usuarioInstance == null) {
            notFound()
            return
        }

        if (usuarioInstance.hasErrors()) {
            respond usuarioInstance.errors, view:'edit'
            return
        }

        usuarioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect usuarioInstance
            }
            '*'{ respond usuarioInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Usuario usuarioInstance) {

        if (usuarioInstance == null) {
            notFound()
            return
        }

        usuarioInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
 