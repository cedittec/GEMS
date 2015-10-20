package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

 
class AyudaSoporteController {
     @Secured(['permitAll'])
    def index()
    {
        
    }
    
      @Secured(['IS_AUTHENTICATED_FULLY'])
    def util()
    {
        
    }
    
    def springSecurityService
    
    @Secured(['IS_AUTHENTICATED_FULLY'])
    def passwordMod()
    {
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        
        String password = params?.passBefore
        String passwordNew = params?.pwd1
        if (!password || !passwordNew ) {
            flash.error = 'Ha ocurrido un error al cambiar la contraseña, contacte al encargado'
            println "Passwords entrando a controlador con valor nulo"
            redirect(controller: "ayudaSoporte", action: "util")
            return;
        }
         
        if (!springSecurityService.passwordEncoder.isPasswordValid(usuario.password, password, null /*salt*/)) {
            flash.error = 'El password actual ingresado NO es el correcto'
            redirect(controller: "ayudaSoporte", action: "util")
            return;
        }

        if (springSecurityService.passwordEncoder.isPasswordValid(usuario.password, passwordNew, null /*salt*/)) {
            flash.error = 'Ingrese un password distinto al actual'
            redirect(controller: "ayudaSoporte", action: "util")
            return;
        }

        usuario.password = passwordNew         
        if (!usuario.save(flush:true)) {
            user.errors.each {
                println it
            }
            flash.error = "No ha podido cambiar password"
            redirect(controller: "ayudaSoporte", action: "util")
            return;
        }
        
        
        println "Cambio de password realizado de usuario : "+usuario.displayName
        flash.message = 'Cambio realizado'
        enviarMail(usuario.email,passwordNew)
        
        redirect(controller: "ayudaSoporte", action: "util")
        return;
    }
     @Secured(['IS_AUTHENTICATED_FULLY'])
    def enviarMail(email,pass)
    {
        sendMail {     
            to email
            from "energia@emsmx.com"
            subject "GEMS Cambio de contraseña"
            body "Se ha cambiado la contraseña de acceso a "+pass
        }        
    }
    
    @Secured(['ROLE_SysAdmin'])
    def backup()
    {
      
        // def command = "mysqldump --opt --user=root --password=server gemsbd"
        // def dump = command.execute()
        // dump.waitFor()
 
        // String dumpCommand = "mysqldump " + "gemsbd" + " -h " + "132.254.39.213" + " -u " + "root" +" -p" + "server";
        
        try{
            def pathToBackup = request.getSession().getServletContext().getRealPath("/backup")
            if(pathToBackup.contains("\\"))
                pathToBackup += "\\backup.sql"
            else
                pathToBackup += "//backup.sql"
                
            println pathToBackup
         //   sql.execute("mysqldump -u root -p server gemsbd > " + pathToBackup)
         //   Runtime.getRuntime().exec("mysqldump -u root -p server gemsbd > " + pathToBackup);
         //String executeCmd = "<Path to MySQL>/bin/mysqldump -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;
            render "ok"                
        }catch(Exception e){
            render "Algo ha salido mal "+e.toString()                
        }
        
    }
    
    
}
