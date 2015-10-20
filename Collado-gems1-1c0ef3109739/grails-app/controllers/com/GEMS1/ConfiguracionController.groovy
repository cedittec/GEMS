package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import java.text.SimpleDateFormat;
import com.GEMS1.ExternalData.Diesel
import org.apache.commons.lang.RandomStringUtils
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Secured(['ROLE_SysAdmin','ROLE_adminTecnico']) 
@Transactional(readOnly = true)
class ConfiguracionController {
    def AlarmasService
    def GasLpService 
    def CFEService
    def GasNaturalService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    def index() { render "index configuracion"}
    
    
    def cliente()
    {
        def users = Usuario.list([sort:"dateCreated", order: "asc"])
        def totalUsuarios = Usuario.count()
        def clientes = Cliente.list([sort:"dateCreated", order: "asc"])
        
        def responsables = []
        
        for(cliente in clientes)
        {
            responsables.add(Usuario.findAllById(cliente.responsableId))
        }
        
       
        [usuarios:users,responsables:responsables, totalUsuarios: totalUsuarios, clientes:clientes]
        
     
    }
    
    @Transactional    
    def saveSitio() {
       
        def nuevoSitio = Sitio.findByNombre(params?.nombre)
        
        if(!nuevoSitio)
        {
                       
            def telefonoSitio = new TelefonoSitio(params)
          
            def sitio = new Sitio(params).addToTelefono(telefonoSitio)
                
            
            if (!sitio.save(flush:true)) {
                sitio.errors.each {
                    println it
                }
                flash.error = "Ha ocurrido un error con la base de datos"
                redirect(controller: "Configuracion", action: "sitio")
                return;
                
            }
               
          
        }
        flash.message = "Creación exitosa del sitio"
        redirect(controller: "Configuracion", action: "sitio")
    }
    
    
    @Transactional    
    def inhabilitarSitio() {
       
        
        def sitio = Sitio.findByNombre(params?.nombreSitio)
        if(sitio)
        {
            if(sitio.estatus == "inactivo")
            sitio.estatus = "activo"
            else
            sitio.estatus = "inactivo"
                
            if (!sitio.save(flush:true)) {
                sitio.errors.each {
                    println it
                }
                flash.error = "Ha ocurrido un error con la base de datos"
                redirect(controller: "Configuracion", action: "sitio")
                return;
                
            }
            
            flash.message = "Operación exitosa"
            redirect(controller: "Configuracion", action: "sitio")
            return;
          
        }
        
        
        flash.error = "Ha ocurrido un error con la base de datos"
        redirect(controller: "Configuracion", action: "sitio")
        return;
        
    }
    
    
    def infoSitio(){
        def sitio        
        def telefono 
        def cliente
        def arregloUsuarios
        def accesos = []
        def tarifa
        def region
        if(params?.nombreSitio){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
            sitio = Sitio.findByNombre(params.nombreSitio)           
            if(sitio){
                telefono = sitio.telefono
                cliente = Cliente.get(sitio.clienteId)
                def tuplas = UsuarioSitio.findAllBySitioId(sitio.id)
                println tuplas
                for(tupla in tuplas)
                {
                    def usuario = Usuario.findById(tupla.usuario.id)
                    if(usuario){
                        def nombreCorreo = (usuario.displayName + " - "+usuario.email)
                        accesos.add(nombreCorreo )
                    }

                }
                tarifa = com.GEMS1.ExternalData.TarifaCfe.get(sitio.tarifaId)
                region = com.GEMS1.ExternalData.RegionCfe.get(sitio.regionId)
   
            }
            
        }
        
      
        def resultado = []
        resultado.add(cliente)
        resultado.add(sitio)
        resultado.add(telefono)
        resultado.add(accesos)
        resultado.add(tarifa)
        resultado.add(region)
        //resultado.add(tarifaName)
        //resultado.add(regionName)
        def xml
        withFormat()
        {
            xml = resultado as XML
             
        }

        render xml
        
    }
    
    @Transactional    
    def crearAccesoASitio(){
        def sitio        
        def usuariosTotal = Usuario.list()
        def usuariosConAcceso = []
        def tuplas
        def usuariosNuevosAccesos
        
        def array = params?.list('emailActuales')
     
        if(params?.nombreSitio){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
            sitio = Sitio.findByNombre(params.nombreSitio)           
            tuplas = UsuarioSitio.findAllBySitioId(sitio.id)
            
        }
        
        flash.message = "Operacion exitosa"
        
        for(tupla in tuplas)
        {
           
            def usuario = Usuario.findById(tupla.usuario.id)
            if(!array.contains(usuario.id)){                
                tupla.delete()
            }
        }
        
        for(usuario in array)
        {
            
            def usuarioTemp = Usuario.findByEmail(usuario)  
           
            def prueba = UsuarioSitio.find("from UsuarioSitio as b where b.sitioId=:sitioId and b.usuario=:usuario", [sitioId: sitio.id, usuario:usuarioTemp])
            if(!prueba){                
                usuarioTemp.addToUsuarioSitio(sitioId: sitio.id).save(flush:true)
                
                
            }else
            {
                flash.message = "Algunos usuarios ya contaban con el acceso"                
            }
            
        }
        redirect(controller: "Configuracion", action: "sitio")
        
    }
    
    
    def verAccesosSitio(){
        def sitio        
        def usuariosTotal = Usuario.list()
        def usuariosConAcceso = []
        def tuplas
        if(params?.nombreSitio){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
            sitio = Sitio.findByNombre(params.nombreSitio)           
            tuplas = UsuarioSitio.findAllBySitioId(sitio.id)
            
        }
        
        def usuarios = Usuario.list()
        for( usuario in usuarios)
        {
            for(tupla in tuplas)
            {
                if(usuario.id == tupla.usuario.id)
                {   
                    usuariosConAcceso.add(usuario)
                    break;
                }   
            }
        }
      
        def resultado = []
        resultado.add(usuariosConAcceso) 
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
    }
    
    
    @Transactional
    def modificarSitio() {
        def msg = "Modificación exitosa" 
        
        def sitio = (Sitio.findByNombre(params?.nombreSitio))
        
        if(sitio){
            sitio.properties = params                 
            params.sitio = sitio
                
            def tel = new TelefonoSitio(params)
            def telBD = TelefonoSitio.get(sitio.telefono.id)
               
            telBD.properties = tel.properties
              
            
            if (!sitio.save(flush:true)) {
                sitio.errors.each {
                    println it
                }
                flash.error = "No ha podido modificarse"
                redirect(controller: "Configuracion", action: "sitio")
                return;
            }
        }else{
            flash.error = "Error al modificar"
            redirect(controller: "Configuracion", action: "sitio")
            return;
        }
            
        flash.message = msg
        redirect(controller: "Configuracion", action: "sitio")
       
    }
    
    
    
    
    def sitio()
    {
        def users = Usuario.list([sort:"dateCreated", order: "asc"])
        def regiones = com.GEMS1.ExternalData.RegionCfe.list([sort:"dateCreated", order: "asc"])
        def tarifas = com.GEMS1.ExternalData.TarifaCfe.list([sort:"dateCreated", order: "asc"])
        def clientes = Cliente.list([sort:"dateCreated", order: "asc"])
        def sitios = Sitio.list([sort:"dateCreated", order: "asc"])
   
       def sitiosLista=[]
        
        for(sitio in sitios)
        {
            def clienteNombre = Cliente.get(sitio.clienteId).razonSocial
            def regionNombre = com.GEMS1.ExternalData.RegionCfe.get(sitio.regionId).nombre
            def tarifaNombre = com.GEMS1.ExternalData.TarifaCfe.get(sitio.tarifaId).nombre
            def telefono = sitio.telefono.displayName
            def estatus = sitio.estatus
            def map = [nombre:sitio.nombre, cliente:clienteNombre, region:regionNombre, tarifa:tarifaNombre, telefono:telefono, estatus:estatus ]           
            sitiosLista.add(map)
            
        }
        
        
        [usuarios:users, clientes:clientes, sitios:sitios, regiones:regiones, tarifas:tarifas,sitiosLista:sitiosLista]
     
     
     
    }
    
    
    def fuente()
    {
        def fuentesSitio = FuenteSitio.list()
        def sitios = Sitio.list()
        def fuentes = FuenteDeEnergia.list()
        def combustibles = CombustibleFuente.list()
        def medidas = UnidadDeMedida.list()
        def servicios = Servicio.list()
        
        def fuentesLista = []
        for(fuente in fuentesSitio)
        {
            
            def combustible = CombustibleFuente.get(fuente.combustibleFuenteId).nombre
            def unidad = UnidadDeMedida.get(fuente.unidadDeMedidaId).unidadDeMedida
            def generacionEnergia = fuente.nombre
            def cantidad = fuente.cantidad
            
            def map = [id:fuente.id,combustible:combustible, unidad:unidad, capacidadGeneracion:generacionEnergia, cantidad:cantidad]           
            fuentesLista.add(map)
            
        }
       
        [sitios:sitios, servicios:servicios, fuentesSitio:fuentesSitio, fuentes:fuentes, combustibles:combustibles, medidas:medidas,fuentesLista:fuentesLista]
    }
    
    
    
    
    
    def verServiciosFuente(){
        def servicios        
        def serviciosDeFuente = []
        def tuplas
        
        if(params?.fuenteId){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
          
            tuplas = FuenteServicioSitio.findAllByFuenteSitioId(params.fuenteId)
            for(tupla in tuplas)
            {
                def servicio = Servicio.get(tupla.servicioId)
                serviciosDeFuente.add(servicio)
            }
            
        }
        
        
        def resultado = []
        resultado.add(serviciosDeFuente) 
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
    }
    
    
    
    def fuentesEnSitio()
    {
        def sitio = Sitio.get(params?.sitioId)
        def fuentesDeSitio = FuenteSitio.findAllBySitio(sitio)
       
        def resultado = []
        resultado.add(fuentesDeSitio)
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
        
    }
    
    
    
    @Transactional
    def crearServiciosDeFuente() {
        def sitio        
        def usuariosTotal = Usuario.list()
        def usuariosConAcceso = []
        def tuplas
        def usuariosNuevosAccesos
        
        def array = params?.list('serviciosActuales')
     
        if(params?.fuenteSitioId){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)               
            tuplas = FuenteServicioSitio.findAllByFuenteSitioId(params.fuenteSitioId)
            
        }
        
        flash.message = "Operacion exitosa"
        
        for(tupla in tuplas)
        {
           
            def servicio = Servicio.findById(tupla.servicioId)
            if(!array.contains(servicio.id)){                
                tupla.delete()
            }
        }
        
        for(servicioId in array)
        {
            
            def prueba = FuenteServicioSitio.find("from FuenteServicioSitio as b where b.servicioId=:servicioId and b.fuenteSitioId=:fuenteSitioId", [servicioId: (long)servicioId, fuenteSitioId: (long)params.fuenteSitioId])
            if(!prueba){                
                //usuarioTemp.addToUsuarioSitio(sitioId: sitio.id).save(flush:true)
                def nuevaTupla = new FuenteServicioSitio(servicioId:servicioId,fuenteSitioId:params.fuenteSitioId).save(flush:true);
                
            }else
            {
                flash.message = "Algunos servicios ya contaban con la asignacion"                
            }
            
        }
        redirect(controller: "Configuracion", action: "fuente")
        
    }
    
    
    
    @Transactional
    def modificarFuenteSitio() {
        def msg = "Modificación exitosa" 
        
        def fuenteSitio = (FuenteSitio.get(params?.fuenteSitioId))
        
        if(fuenteSitio){
               
            fuenteSitio.properties = params               
                
            if (!fuenteSitio.save(flush:true)) {
                fuenteSitio.errors.each {
                    println it
                }
                flash.error = "No ha podido modificarse"
                redirect(controller: "Configuracion", action: "fuente")
                return;
            }
        }else{
            flash.error = "Error al modificar"
            redirect(controller: "Configuracion", action: "fuente")
            return;
        }
            
        flash.message = msg
        redirect(controller: "Configuracion", action: "fuente")
       
    }
    
    
    def infoFuente()
    {
        def fuenteSitio = FuenteSitio.get(params?.fuenteSitioId)
        def fuenteDeEnergia
        def combustibleFuente
        def unidadDeMedida
        def servicios=[]
        
        if(fuenteSitio)
        {
            fuenteDeEnergia = FuenteDeEnergia.get(fuenteSitio.fuenteDeEnergiaId)
            combustibleFuente = CombustibleFuente.get(fuenteSitio.combustibleFuenteId)
            unidadDeMedida = UnidadDeMedida.get(fuenteSitio.unidadDeMedidaId)
            
            def serviciosFuente = FuenteServicioSitio.findAllByFuenteSitioId(fuenteSitio.id)
            for(servicio in serviciosFuente)
            {
                def serv = Servicio.get(servicio.servicioId)
                servicios.add(serv.nombre)
            }
            
        }
        
        def resultado = []
        resultado.add(fuenteSitio) 
        resultado.add(fuenteDeEnergia) 
        resultado.add(combustibleFuente) 
        resultado.add(unidadDeMedida)
        resultado.add(servicios)
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
        
    }
    
    
    
    def infoFuenteEnSitio()
    {
        def fuenteSitio = FuenteSitio.get(params?.fuenteSitioId)
        def fuenteDeEnergia = FuenteDeEnergia.get(fuenteSitio.fuenteDeEnergiaId)
        def fuenteDeCombustible = CombustibleFuente.get(fuenteSitio.combustibleFuenteId)
        def unidadDeMedida = UnidadDeMedida.get(fuenteSitio.unidadDeMedidaId)
        def resultado = []
        
        resultado.add(fuenteSitio)
        
        
        //string[0] el tipo de fuente...
        resultado.add(fuenteDeEnergia.id)
        //string[1] el tipo de combustible
        resultado.add(fuenteDeCombustible.id)
        //string[2] la unidad de medida
        resultado.add(unidadDeMedida.id)
        
        
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
        
    }
    
    
    @Transactional    
    def saveFuente() {
       
        //def nuevo_cliente = Cliente.findByRfc(params?.rfc)
        if(1==1)
        {
            def fuenteNueva = new FuenteSitio(params)
            def sitio = Sitio.get(params?.sitioId)
            sitio.addToFuenteSitio(fuenteNueva)
            
            
            if (!sitio.save(flush:true)) {
                sitio.errors.each {
                    println it
                }
                flash.error = "No ha podido dar de alta la fuente"
                redirect(controller: "Configuracion", action: "fuente")
                return;
            }
                       
            
            
        }else{
            
            flash.message = "-"
            redirect(controller: "Configuracion", action: "fuente")
        }
 
        flash.message = "Alta exitosa de la fuente"
        redirect(controller: "Configuracion", action: "fuente")
    }
    
    @Transactional    
    def inhabilitarFuente() {
        println "incoming"
        def fuenteSitio= FuenteSitio.get(params?.fuenteSitioId)
     
        if(fuenteSitio)
        {
            if(fuenteSitio.estatus == "activo")     
            fuenteSitio.estatus = "inactivo"
            else
            fuenteSitio.estatus = "activo"    
                
            if (!fuenteSitio.save()) {
                fuenteSitio.errors.each {
                    println it
                }
                flash.error = "No ha podido realizarse la operacion de "+fuenteSitio.nombre
                redirect(controller: "Configuracion", action: "fuente")
                return;
            }
            
                       
        }else
        {
            flash.error = "La fuente no existe"
            redirect(controller: "Configuracion", action: "fuente")
            return;
        }
        
        flash.message = "Operación exitosa"
        redirect(controller: "Configuracion", action: "fuente")
       
    }
    
    
    @Transactional    
    def inhabilitarCliente() {
        
        def cliente = Cliente.findByRazonSocial(params?.razonSocial)
        def sitios
        if(cliente)
        {
           
            sitios = Sitio.findAllByClienteId(cliente.id)
            for(sitio in sitios)
            
            {
                
                sitio.estatus = "inactivo"
                if (!sitio.save()) {
                    sitio.errors.each {
                        println it
                    }
                    flash.error = "No ha podido desactivarse el sitio "+sitio.nombre
                    redirect(controller: "Configuracion", action: "cliente")
                    return;
                }
            }
                       
        }else
        {
            flash.error = "El cliente no existe"
            redirect(controller: "Configuracion", action: "cliente")
            return;
        }
        
        flash.message = "Operación exitosa"
        redirect(controller: "Configuracion", action: "sitio")
       
    }
    
    @Transactional
    def modificarCliente() {
        def msg = "Modificación exitosa" 
        
        def cliente = (Cliente.findByRazonSocial(params?.razonSocialClientes))
        
        if(cliente){
               
            def usuario_responsable = Usuario.findByEmail(params?.responsableIdEmail)
            params.responsableId = usuario_responsable.id
            
            cliente.properties = params               
            params.cliente = cliente
                
            def tel = new TelefonoCliente(params)
            def telBD = TelefonoCliente.get(cliente.telefono.id)
               
            telBD.properties = tel.properties
              
            def direccion = new DireccionCliente(params)
            def dirBD = DireccionCliente.get(cliente.direccionCliente.id)
            
            dirBD.properties = direccion.properties
            
            if (!cliente.save(flush:true)) {
                cliente.errors.each {
                    println it
                }
                flash.error = "No ha podido modificarse"
                redirect(controller: "Configuracion", action: "cliente")
                return;
            }
        }else{
            flash.error = "Error al modificar, no se ha encontrado el cliente"
            redirect(controller: "Configuracion", action: "cliente")
            return;
        }
            
        flash.message = msg
        redirect(controller: "Configuracion", action: "cliente")
       
    }    
    def infoCliente(){
        def cliente
        def direccion 
        def telefono 
        def usuarioResponsable
        def sitios
        if(params?.razonSocial){
            // tareas = Tarea.findAllByEventoAndStatus(Evento.get(params.id), params.status)
            cliente = Cliente.findByRazonSocial(params?.razonSocial)           
            direccion = cliente.direccionCliente
            telefono = cliente.telefono
            usuarioResponsable = Usuario.get(cliente.responsableId)
            sitios = Sitio.findAllByClienteId(cliente.id)
        }
        
      
        def resultado = []
        resultado.add(cliente)
        resultado.add(direccion)
        resultado.add(telefono) 
        resultado.add(usuarioResponsable)
        resultado.add(sitios)
        
        withFormat()
        {
            xml{ render resultado as XML }
        }
        
    }
    
    
    @Transactional    
    def saveCliente() {
       
        def nuevo_cliente = Cliente.findByRfc(params?.rfc)
        if(!nuevo_cliente)
        {
            
            def telefono_cliente = new TelefonoCliente(params)
            def direccion_cliente = new DireccionCliente(params)
            
           
            def usuario_responsable = Usuario.findByEmail(params?.emailCliente)
            params.responsableId = usuario_responsable.id
           
            if(usuario_responsable)
            {
                
                def cliente = new Cliente(params)
                .addToTelefono(telefono_cliente)
                .addToDireccionCliente(direccion_cliente)
                           
                if (!cliente.save()) {
                    cliente.errors.each {
                        println it
                    }
                    flash.error = "No ha podido crearse el cliente"
                    redirect(controller: "Configuracion", action: "cliente")
                    return;
                    
                }
               
                       
            }else
            {
                //No existe el usuario con ese email
                flash.error = "El rfc incluido ya existe"
                redirect(controller: "Configuracion", action: "cliente")
                return;
            }
        }
        flash.message = "Creación exitosa de cliente"
        redirect(controller: "Configuracion", action: "cliente")
    }
    
    def alarmas()
    {
        def alarmas = Alarma.list([sort:"dateCreated", order: "desc"])
        def totalAlarmas = Alarma.count()
        def alarmasLista=[]
        
        def map = [id:"-",alarma:"-",mensaje:"-", sitio:"-", fecha:"-", visto:"1"]           
        alarmasLista.add(map)
            
        for(alarma in alarmas)
        {
            /*
            byte vistoByte = 1;
            AlarmaUsuario.executeUpdate("update AlarmaUsuario b set b.visto=:newVisto " +
            "where b.title=:oldTitle",
            [newVisto: vistoByte, oldTitle: 'GINA'])
             */
            try{
            def msg = CodigoAlarma.findByClaveAlarma(alarma.claveAlarma)
            def sitio = Sitio.get(alarma.sitioId)
            def alarmaUsuario = AlarmaUsuario.findWhere(alarmaId:alarma.id)            
            map = [id:alarma.id,alarma:alarma.claveAlarma,mensaje:msg.mensaje, sitio:sitio.nombre, fecha:alarma.dateCreated, visto:alarmaUsuario.visto]           
            alarmasLista.add(map)
            }catch(Exception e)
            {
                
            }
            
        }
    
       
        [alarmasLista:alarmasLista]
    }
           
    
    @Transactional
    def desactivarAlarma()
    {
        try{
            def idAlarm =  params?.alarmaId              
            if(idAlarm){
                def alarmaX = Alarma.get(params?.alarmaId);   
                println "alarma entrando: " + params?.alarmaId
                if(alarmaX){                    
                byte vistoByte = 1;
                AlarmaUsuario.executeUpdate("update AlarmaUsuario b set b.visto=:newVisto where b.alarmaId=:id",[newVisto: vistoByte, id:alarmaX.id])
                Alarma.executeUpdate("update Alarma b set b.visto=:newVisto where b.id=:id",[newVisto: vistoByte, id:alarmaX.id])    
                println "ejecutando alarmas:" 
                AlarmasService.alarma()
                println "OK!"
                }
                render(status: 200, text: 'ok')
            }
            render(status: 200, text: 'ok')
        }catch(Exception e)
        {
            println "****ERROR: " + e.toString();
            render(status: 404, text: 'error al apagar')
        }
      
    }
    
    @Transactional
    def dieselNuevo()
    {
        def precio = params?.precio
        if(precio)
        {
            precio = (double)Double.parseDouble(precio) 
            println "PRECIO: " + precio
            def registroNuevo = new Diesel(precio:precio)                                        
            println "AFTER : " + registroNuevo.precio
            if (!registroNuevo.save(flush:true)) 
            {
                registroNuevo.errors.each {
                    println it
                }
                flash.error = "No se ha podido guardar el precio"
                redirect(controller: "Configuracion", action: "configuracionArranque")
                return;                
            }else
            {
                flash.message = "Registro exitoso"
                redirect(controller: "Configuracion", action: "configuracionArranque")
                return;
            }
        }else{
            flash.error = "No se ha asigando un precio"
            redirect(controller: "Configuracion", action: "configuracionArranque")
            return;                
        }
    }
    
    @Secured(['ROLE_SysAdmin']) 
    def configurarContrasena(){
        
    }
    
    @Transactional
    @Secured(['IS_AUTHENTICATED_FULLY'])
    def passwordMod()
    {
        
        def usuario = Usuario.findByEmail(params.email)
        println "USER : " + usuario
        String passwordNew = generaPassword()              
        usuario.password = passwordNew            
        
        if (!usuario.save(flush:true)) {
            user.errors.each {
                println it
            }
            flash.error = "No ha podido cambiar password"
            redirect(controller: "Configuracion", action: "configurarContrasena")
            return;
        }
        
        
        println "Cambio de password realizado de usuario : "+usuario.displayName
        flash.message = 'Cambio realizado de usuario '+usuario.email
        enviarMail(usuario.email,passwordNew)
        
        redirect(controller: "Configuracion", action: "configurarContrasena")
        return;
    }
    
     @Secured(['IS_AUTHENTICATED_FULLY'])
    def enviarMail(email,pass)
    {
        sendMail {     
            to email
            from "energia@emsmx.com"
            subject "GEMS Cambio de contraseña"
            body "Por solicitud se ha cambiado la contraseña de acceso a "+pass +"\n\
le pedimos cambiar su contraseña a una personal lo más pronto posible"
        }        
    }
    
        
    //Devuelve la pantalla de configuracion de arranque con datos necesarios.
    def configuracionArranque()
    {
        def ultimoPrecioDiesel = 0.0
        def fechaUltimoPrecioDiesel ="-"
        def diesel = Diesel.list([sort:"dateCreated", order: "desc"])
        if(diesel)
        {
            def now = diesel[0].dateCreated
            
            String dateString = now.toString();        
            SimpleDateFormat format = new SimpleDateFormat("MM-yyyy"); 
            
            ultimoPrecioDiesel = diesel[0].precio;
            fechaUltimoPrecioDiesel = format.format(now);
            
        }
        
        [ultimoPrecioDiesel:ultimoPrecioDiesel,fechaUltimoPrecioDiesel:fechaUltimoPrecioDiesel]
        
    }
    
    //Ejecuta los servicios de recopilación de datos de manera manual
     @Transactional
    def runServiceData()
    {
        try{
            GasLpService.serviceMethod()
            CFEService.serviceMethod()
            GasNaturalService.serviceMethod()
        }catch(Exception e)
        {
            flash.error = "Ha ocurrido un error al obtener datos, revisar registro de sistema"
            redirect(controller: "Configuracion", action: "configuracionArranque")
            return;              
        }
        flash.message = "Algoritmos ejecutados con exito"
        redirect(controller: "Configuracion", action: "configuracionArranque")
        return; 
    }
    
    //Genera un backup de la BD, la almacena en la carpeta backup dentro del proyeto y manda un correo con el archivo .sql adjunto
    def backup()
    {      
        Date now = new Date();
        String dateString = now.toString();        
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy"); 
        String nombreArchivo = "backup-" + format.format(now)+".sql";
        
        try{
            def pathToBackup = request.getSession().getServletContext().getRealPath("/backup")
            if(pathToBackup.contains("\\"))
            pathToBackup += "\\"+nombreArchivo
            else
            pathToBackup += "//"+nombreArchivo
                
            println pathToBackup
            //def comando = "mysqldump -h localhost -u root productos > C:\\Users\\Soporte\\Desktop\\examenes\\julio.sql".execute();           
            def comando = "mysqldump -h localhost --databases gemsbd -u root -p server -C --result-file="
            comando += pathToBackup;
            comando = comando.execute();           
            comando.waitFor()              
            if(result == 0)
            {
                //exito
                flash.message = "Respaldo realizado y enviado a energia@emsms.com, almacenado en carpeta backup (Leer manual)"
                redirect(controller: "Configuracion", action: "configuracionArranque")
                return; 
            }
            else
            {
                //error
                flash.error = "Error al generar respaldo, revisar registro de sistema, intentelo de nuevo en unos minutos"
                redirect(controller: "Configuracion", action: "configuracionArranque")
                return;
            }
            
        }catch(Exception e){
            println "Ha ocurrido un error al generar bakcup, error: \n" + e.toString();
            flash.error = "Error al generar respaldo, revisar registro de sistema, intentelo de nuevo en unos minutos"
            redirect(controller: "Configuracion", action: "configuracionArranque")
            return;
        }                        
    }
            
    //Genera una cadena de caracteres para password
    def generaPassword()
    {
        String charset = (('A'..'Z') + ('0'..'9')).join()
        Integer length = 9
        String randomString = RandomStringUtils.random(length, charset.toCharArray())
        return randomString
    }
     
    
}
