package Data
import com.GEMS1.*

import grails.transaction.Transactional
@Transactional
class AlarmasService {
    
    def brokerMessagingTemplate
    
    //Metodo principal
    def checkAlarmas()
    {
        byte valorPrev = 0;
        byte valorSig = 1;
        
        def alarmasNuevas = Alarma.findAllWhere(visto: valorPrev)
        if(alarmasNuevas)
        {
            for(alarma in alarmasNuevas)
            { 
                println "ejecutar"
                ejecutar(alarma.sitioId, alarma.id, alarma.claveAlarma)
                /*
                alarma.visto = valorSig                
                
                if (!alarma.save(flush:true)) 
                {
                    alarma.errors.each 
                    {
                        println "ERROR SAVE ALARMA :" + it
                    }
                }
                */
            }
          //Person.executeUpdate("update Person set age = age + 1 where surname = 'Doe' ");
            Alarma.executeUpdate("update Alarma set visto = 1")
        }
    }
    
    //Devuelve las alarmas pendientes
    void alarma() {
      
        def msg = ""
        def numAlarmasNuevas = 0
        byte valorPrev = 0;
        byte valorSig = 1;
       
        def alarmasLista = AlarmaUsuario.findAllWhere(visto: valorPrev)
      
        println "ALARMAS PENDIENTES: "+ alarmasLista.size()
        def alarmasNuevas = alarmasLista.unique{ it.alarmaId }
            
        // def alarmasNuevas = AlarmaUsuario.findAllWhere(visto: valorPrev)
        if(alarmasNuevas)
        {   
            for(alarmaNueva in alarmasNuevas)
            {
                numAlarmasNuevas++;
                def alarmaReg = Alarma.get(alarmaNueva.alarmaId)
                def alarmaInfo = CodigoAlarma.findWhere(claveAlarma:alarmaReg.claveAlarma)
                def sitioInfo = Sitio.get(alarmaReg.sitioId)
                              
                msg+= alarmaReg.id + "%" +alarmaInfo.mensaje +". En sitio "+sitioInfo.nombre+"%"
            }
        }
        msg += (msg + numAlarmasNuevas)             
        
        println "Numero a mandar: "+ numAlarmasNuevas

        brokerMessagingTemplate.convertAndSend "/topic/alarma", msg   
    }
    
  
    //Ejecuta el metodo de insertAlarmaUsuario para registrar una alarma para usuarios y aparte mandarles correo electronico    
    def ejecutar(sitioId, alarmaId, claveAlarma)
    {
        insertAlarmaUsuario(getUsuariosNotificarAlarma(sitioId), alarmaId, sitioId, claveAlarma)
    }
    
    //Inserta una alarma para un usuario y envia un correo electronico 
    //Ejecutara una funcion para hacer un push del servidor a quién tenga abeirto el sistema y tenga permiso de ver la alarma
    def insertAlarmaUsuario(listaUsuarios_, alarmaId_, sitioId_, claveAlarma_)
    {
        byte valor = 0;
       
        //Conseguir el texto de la alarma para enviarlo por mail
        def codigoAlarmaRegistro = CodigoAlarma.findWhere(claveAlarma:claveAlarma_)
        def sitio = Sitio.get(sitioId_)
        def mensaje = "Sitio " +sitio.nombre+" : "+ codigoAlarmaRegistro.mensaje

        for(usuario in listaUsuarios_)
        { 
            //if((usuario.getAuthorities()).authority != "ROLE_SysAdmin")
            //{
                def registroNuevo = (new AlarmaUsuario(sitioId:sitioId_, usuarioId:usuario[0], alarmaId: alarmaId_, visto: valor ));

                if (!registroNuevo.save(flush:true))
                {
                    registroNuevo.errors.each 
                    {
                        println "ERROR :" + it
                    }
                }
                correoElectronico(usuario[1], mensaje);            
            //}
        }
    }
    
    //Devuelve a los usuarios que se deben notificar en caso de alarma para este caso: "ROLE_adminTecnico", "ROLE_clienteTecnico"
    def getUsuariosNotificarAlarma(sitioId)
    {
        def usuariosResultado = []
        
        usuariosResultado = Usuario.executeQuery(
            "select u.id, u.email "+
            "from Usuario u, UsuarioRol r, UsuarioSitio s "+
            "where "+
            "r.usuario = u.id and "+
            "r.rol IN ('2', '5') and "+
            "s.usuario = u.id and "+
            "s.sitioId = 1"
       )
        
        /*
        def query = Usuario.where
        {
            (rol == "ROLE_adminTecnico" || rol == "ROLE_clienteTecnico" || rol = "ROLE_SysAdmin")
            and 
        }
Person bart = query.find()
            Usuario.getAllWhere()
        //
        def usuariosEnSitio = UsuarioSitio.findAllBySitioId(sitioId)
        def usuariosPermisos = Usuario.getAll(usuariosEnSitio.usuarioId)

        
        def role = Rol.findAllByAuthorityInList(["ROLE_adminTecnico", "ROLE_clienteTecnico", "ROLE_SysAdmin"])
        def usuariosTecnicos = UsuarioRol.findAllByRolInList(role).usuario

        for( usuario in usuariosPermisos)
        {
            for(usuarioTecnico in usuariosTecnicos)
            {
                if( usuarioTecnico.id == usuario.id)
                {
                    //println "El usuario "+ usuario.displayName + " esta en la lista"
                    usuariosResultado.add(usuario)
                }
            }  
        }      
        */
       
        return usuariosResultado
    }
	
    //Envia un correo electronico al email parametro 1, con el mensaje del parametro 2
    def correoElectronico(email, texto)
    {
        sendMail
        {
            to email
            from "energia@emsmx.com"
            subject "Nueva Alarma de sistema GEMS"
            body texto
        }
        println "SE DEBIO HABER ENVIADO UN CORREO a "+ email + "-" + texto
    }
}