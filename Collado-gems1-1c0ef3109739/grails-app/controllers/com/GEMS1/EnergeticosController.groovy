package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import groovy.time.TimeCategory
import com.GEMS1.Data.*
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat

@Secured(['ROLE_SysAdmin','ROLE_adminVentas','ROLE_adminTecnico','ROLE_clienteAdmin','ROLE_clienteTecnico', 'ROLE_adminVentas'])
class EnergeticosController {
    def springSecurityService
    def index() { 
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def emisionCogeneracion  = 0
        def energiaTermicaGeneradaVal = 0
        def energiaElectricaGeneradaVal= 0
        def consumoCombustibleVal = 0
        def radiacionSolarVal = 0
        def nivelesHumedadVal = 0
        def temperaturaVal = 0
        
        def tipoCombustibleCog = ""
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        if(rol.authority == 'ROLE_SysAdmin')
        {
            //sitios = Sitio.getAll()
            sitios = Sitio.findAllWhere(estatus:"activo")
        }
        else{
            def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
            //sitios = Sitio.findAllWhere(estatus:"activo")
            sitios = Sitio.getAll(accesosDeUsuario.sitioId)
            def listTemp = []
            for(sitio in sitios)
            {
                if(sitio.estatus == "inactivo")
                {
                    listTemp.add(sitio)
                }
            }
            sitios = sitios.minus(listTemp)
            
        }        
        num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                fechaMesAtras = new Date(fechaMesAtras.getYear(), fechaMesAtras.getMonth(), 1)
            }
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
            def fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")   
            if(fuentes){
                tipoCombustibleCog = tipoCombustible(fuentes[0])      
            }
            energiaTermicaGeneradaVal  = energiaTermicaGenerada(sitios.get(0),fechaHoy, fechaMesAtras)
            energiaElectricaGeneradaVal = energiaElectricaGenerada(sitios.get(0),fechaHoy, fechaMesAtras)
            consumoCombustibleVal = consumoCombustible(sitios.get(0),fechaHoy, fechaMesAtras)
            radiacionSolarVal = radiacionSolarSistemaTermico(sitios.get(0),fechaHoy, fechaMesAtras)
            nivelesHumedadVal =  nivelesHumedadSistemaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
            //temperaturaVal =   temperaturaSistemaTermico(sitios.get(0),fechaHoy, fechaMesAtras)
            temperaturaVal =   temperaturaSistemaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
            //Grafica:
            def arregloResultadoGrafica = graficaIndex(sitios.get(0))
                
            arregloGraficaMeses = arregloResultadoGrafica[0]
                
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
                
            
        }
         
        [sitios:sitios, numeroSitios:num, rol:rol, energiaTermicaGeneradaVal:energiaTermicaGeneradaVal,
            energiaElectricaGeneradaVal:energiaElectricaGeneradaVal,consumoCombustibleVal:consumoCombustibleVal,
            radiacionSolarVal:radiacionSolarVal, nivelesHumedadVal:nivelesHumedadVal,temperaturaVal:temperaturaVal,
            tipoCombustibleCog:tipoCombustibleCog,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,
            arregloGraficaValores2:arregloGraficaValores2,arregloGraficaValores3:arregloGraficaValores3]
           
        
    }
    
    def energiaTermica()
    {
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def arregloTermicaGenerada
        def arregloEnergiaAprovechada 
        def eficiencia = 0
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        if(rol.authority == 'ROLE_SysAdmin')
        {
            //sitios = Sitio.getAll()
            sitios = Sitio.findAllWhere(estatus:"activo")
        }
        else{
            def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
            //sitios = Sitio.findAllWhere(estatus:"activo")
            sitios = Sitio.getAll(accesosDeUsuario.sitioId)
            def listTemp = []
            for(sitio in sitios)
            {
                if(sitio.estatus == "inactivo")
                {
                    listTemp.add(sitio)
                }
            }
            sitios = sitios.minus(listTemp)
            
        }
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                // fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            //Devuleve un arreglo de 3 para el primer cuadro de la pantalla [0]: energia total, [1]: Energ. cogeneracion, [2]: Energ. Termico
            arregloTermicaGenerada= generacionEnergiaTermicaPeriodo(sitios.get(0),fechaHoy, fechaMesAtras)
            
            //Devuelve un arreglo de tamaÃ±o 6 para el segundo cuaro de la pantalla [0]: energiaTotal, [1]:energiaAireAgua, [2]...
            arregloEnergiaAprovechada =  energiaAprovechadaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)   
            eficiencia = eficienciaTermica(sitios.get(0),fechaHoy, fechaMesAtras) 
            
            //GRAFICA
            def arregloResultadoGrafica = graficaEnergiaTermica(sitios.get(0))
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            
        }
        
        [sitios:sitios,eficiencia:eficiencia,arregloTermicaGenerada:arregloTermicaGenerada,arregloEnergiaAprovechada:arregloEnergiaAprovechada, numeroSitios:num, rol:rol,
            arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2]
    }
    
    def energiaElectrica()
    {
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
      
        def arregloEnergiasCFE
        def arregloEnergiasElectricasCogeneracion
        def arregloEnergiasElectricasFotovoltaico
        def totalEnergiaElectrica = 0
        
        if(rol.authority == 'ROLE_SysAdmin')
        {
            //sitios = Sitio.getAll()
            sitios = Sitio.findAllWhere(estatus:"activo")
        }
        else{
            def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
            //sitios = Sitio.findAllWhere(estatus:"activo")
            sitios = Sitio.getAll(accesosDeUsuario.sitioId)
            def listTemp = []
            for(sitio in sitios)
            {
                if(sitio.estatus == "inactivo")
                {
                    listTemp.add(sitio)
                }
            }
            sitios = sitios.minus(listTemp)
            
        }
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                //  fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            //Revuelven arreglos! de arriba hacia abajo segun el componente que se tiene en la pantalla.Ej:
            /*
             * EnergÃ­a ElÃ©ctrica en la Base: arregloEnergiasCFE[0] 
             * EnergÃ­a ElÃ©ctrica en la Intermedia: arregloEnergiasCFE[2] 
             * EnergÃ­a ElÃ©ctrica en la Punta: arregloEnergiasCFE[3] 
             * Y el total es la suma de las 3
             */
            arregloEnergiasCFE= energiaElectricaCFE(sitios.get(0),fechaHoy, fechaMesAtras)
            arregloEnergiasElectricasCogeneracion = energiaElectricaSistemaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
            arregloEnergiasElectricasFotovoltaico = energiaElectricaSistemaFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
           
            // totalEnergiaElectrica contiene el total de energia
            totalEnergiaElectrica+= arregloEnergiasCFE[0]
            totalEnergiaElectrica+=arregloEnergiasCFE[1]
            totalEnergiaElectrica+=arregloEnergiasCFE[2]
           
            totalEnergiaElectrica+= arregloEnergiasElectricasCogeneracion[0]
            totalEnergiaElectrica+=arregloEnergiasElectricasCogeneracion[1]
            totalEnergiaElectrica+=arregloEnergiasElectricasCogeneracion[2]
           
            totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[0]
            totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[1]
            totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[2]
           
            //GRAFICA
            def arregloResultadoGrafica = graficaEnergiaElectrica(sitios.get(0))
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
            
            
        }
        
        [sitios:sitios, numeroSitios:num, rol:rol,
            arregloEnergiasCFE:arregloEnergiasCFE,arregloEnergiasElectricasCogeneracion:arregloEnergiasElectricasCogeneracion,
            arregloEnergiasElectricasFotovoltaico:arregloEnergiasElectricasFotovoltaico, totalEnergiaElectrica:totalEnergiaElectrica,
            arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2,
            arregloGraficaValores3:arregloGraficaValores3]
    }
    
    def combustibles()
    {
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)       
        
        def tipoCombustibleCog = ""
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores4 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        
        def combustibleCogeneracionTotal = [0.0,0.0];
        def combustibleTermicoTotal = [0.0,0.0];
        
        if(rol.authority == 'ROLE_SysAdmin')
        {
            //sitios = Sitio.getAll()
            sitios = Sitio.findAllWhere(estatus:"activo")
        }
        else{
            def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
            //sitios = Sitio.findAllWhere(estatus:"activo")
            sitios = Sitio.getAll(accesosDeUsuario.sitioId)
            def listTemp = []
            for(sitio in sitios)
            {
                if(sitio.estatus == "inactivo")
                {
                    listTemp.add(sitio)
                }
            }
            sitios = sitios.minus(listTemp)
            
        }
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                // fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
            def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitios.get(0),fuenteMicroturbina.id)
            
            if(fuentes){
                
                tipoCombustibleCog = tipoCombustible(fuentes[0])                            
                combustibleCogeneracionTotal = combustibleYEnergiaSistemaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
                combustibleTermicoTotal = combustibleYEnergiaSistemaTermico(sitios.get(0),fechaHoy, fechaMesAtras)
                
                //Grafica:
                def arregloResultadoGrafica = graficaCombustibles(sitios.get(0))
                
                arregloGraficaMeses = arregloResultadoGrafica[0]
                
                arregloGraficaValores = arregloResultadoGrafica[1]
                arregloGraficaValores2 = arregloResultadoGrafica[2]
                arregloGraficaValores3 = arregloResultadoGrafica[3]
                arregloGraficaValores4 = arregloResultadoGrafica[4]
            }
        }
        
        [sitios:sitios,combustibleCogeneracionTotal:combustibleCogeneracionTotal,combustibleTermicoTotal:combustibleTermicoTotal, numeroSitios:num, rol:rol,
            tipoCombustibleCog:tipoCombustibleCog,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2,
            arregloGraficaValores3:arregloGraficaValores3,arregloGraficaValores4:arregloGraficaValores4]
    }
    
    def actualizaEnergeticos(){
        
        def emisionCogeneracion  = 0
        def energiaTermicaGeneradaVal = 0
        def energiaElectricaGeneradaVal= 0
        def consumoCombustibleVal = 0
        def radiacionSolarVal = 0
        def nivelesHumedadVal = 0
        def temperaturaVal = 0
        
        def tipoCombustibleCog = ""
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def fuentes = FuenteSitio.findAllWhere(sitio:thisSite, estatus:"activo")   
        if(fuentes){
            tipoCombustibleCog = tipoCombustible(fuentes[0])      
        }
        
        energiaTermicaGeneradaVal  = (double)energiaTermicaGenerada(thisSite,fechaEnd, fechaInit)
        energiaElectricaGeneradaVal = (double)energiaElectricaGenerada(thisSite,fechaEnd, fechaInit)
        consumoCombustibleVal = (double)consumoCombustible(thisSite,fechaEnd, fechaInit)
        radiacionSolarVal = (double)radiacionSolarSistemaTermico(thisSite,fechaEnd, fechaInit)
        nivelesHumedadVal =  (double)nivelesHumedadSistemaTermico(thisSite,fechaEnd, fechaInit)
        temperaturaVal =   (double)temperaturaSistemaTermico(thisSite,fechaEnd, fechaInit)
         
        //Grafica:
        def arregloResultadoGrafica = graficaIndex(thisSite)
                
        arregloGraficaMeses = arregloResultadoGrafica[0]
                
        arregloGraficaValores = arregloResultadoGrafica[1]
        arregloGraficaValores2 = arregloResultadoGrafica[2]
        arregloGraficaValores3 = arregloResultadoGrafica[3]
        
        
        def resultado = []
        resultado.add(energiaTermicaGeneradaVal)
        resultado.add(energiaElectricaGeneradaVal)
        resultado.add(consumoCombustibleVal)
        resultado.add(radiacionSolarVal)
        resultado.add(nivelesHumedadVal)
        resultado.add(temperaturaVal)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        resultado.add(arregloGraficaValores3)
        
        
        

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def actualizaEnergiaTermica(){
        
        def arregloTermicaGenerada
        def arregloEnergiaAprovechada
        def eficiencia
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        //Devuleve un arreglo de 3 para el primer cuadro de la pantalla [0]: energia total, [1]: Energ. cogeneracion, [2]: Energ. Termico
        arregloTermicaGenerada= generacionEnergiaTermicaPeriodo(thisSite,fechaEnd, fechaInit)
        //Devuelve un arreglo de tamaÃ±o 6 para el segundo cuaro de la pantalla [0]: energiaTotal, [1]:energiaAireAgua, [2]...
        arregloEnergiaAprovechada =  energiaAprovechadaCogeneracion(thisSite,fechaEnd, fechaInit)   
        eficiencia = eficienciaTermica(thisSite,fechaEnd, fechaInit)   
        
        //GRAFICA
        def arregloResultadoGrafica = graficaEnergiaTermica(thisSite)
            
        arregloGraficaMeses = arregloResultadoGrafica[0]
        arregloGraficaValores = arregloResultadoGrafica[1]
        arregloGraficaValores2 = arregloResultadoGrafica[2]
        
        def resultado = []
        resultado.add(arregloTermicaGenerada)
        resultado.add(arregloEnergiaAprovechada)
        resultado.add(eficiencia)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
     def actualizaEnergiaElectrica(){
        
        def arregloEnergiasCFE = 0
        def arregloEnergiasElectricasCogeneracion = 0
        def arregloEnergiasElectricasFotovoltaico = 0
        def totalEnergiaElectrica = 0
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        arregloEnergiasCFE= energiaElectricaCFE(thisSite,fechaEnd, fechaInit)
        arregloEnergiasElectricasCogeneracion = energiaElectricaSistemaCogeneracion(thisSite,fechaEnd, fechaInit)
        arregloEnergiasElectricasFotovoltaico = energiaElectricaSistemaFotovoltaico(thisSite,fechaEnd, fechaInit)

        // totalEnergiaElectrica contiene el total de energia
        totalEnergiaElectrica+= arregloEnergiasCFE[0]
        totalEnergiaElectrica+=arregloEnergiasCFE[1]
        totalEnergiaElectrica+=arregloEnergiasCFE[2]

        totalEnergiaElectrica+= arregloEnergiasElectricasCogeneracion[0]
        totalEnergiaElectrica+=arregloEnergiasElectricasCogeneracion[1]
        totalEnergiaElectrica+=arregloEnergiasElectricasCogeneracion[2]

        totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[0]
        totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[1]
        totalEnergiaElectrica+=arregloEnergiasElectricasFotovoltaico[2]
        
        arregloEnergiasElectricasFotovoltaico[0] = (double)arregloEnergiasElectricasFotovoltaico[0]
        arregloEnergiasElectricasFotovoltaico[1] = (double)arregloEnergiasElectricasFotovoltaico[1]
        arregloEnergiasElectricasFotovoltaico[2] = (double)arregloEnergiasElectricasFotovoltaico[2]
        
        //GRAFICA
        def arregloResultadoGrafica = graficaEnergiaElectrica(thisSite)
            
        arregloGraficaMeses = arregloResultadoGrafica[0]
        arregloGraficaValores = arregloResultadoGrafica[1]
        arregloGraficaValores2 = arregloResultadoGrafica[2]
        arregloGraficaValores3 = arregloResultadoGrafica[3]
        
        def resultado = []
        resultado.add(arregloEnergiasCFE)
        resultado.add(arregloEnergiasElectricasCogeneracion)
        resultado.add(arregloEnergiasElectricasFotovoltaico)
        resultado.add((double)totalEnergiaElectrica)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        resultado.add(arregloGraficaValores3)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def actualizaCombustibles(){
        
        def combustibleCogeneracion
        def combustibleTermico
        
        def tipoCombustibleCog = ""
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores3 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores4 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))        
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(thisSite,fuenteMicroturbina.id)
            
        if(fuentes){
                
            tipoCombustibleCog = tipoCombustible(fuentes[0])                            
            combustibleCogeneracion = combustibleYEnergiaSistemaCogeneracion(thisSite,fechaEnd, fechaInit)
            combustibleTermico = combustibleYEnergiaSistemaTermico(thisSite,fechaEnd, fechaInit)
                
            //Grafica:
            def arregloResultadoGrafica = graficaCombustibles(thisSite)
                
            arregloGraficaMeses = arregloResultadoGrafica[0]
                
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
            arregloGraficaValores4 = arregloResultadoGrafica[4]
        }
        
        def resultado = []
        resultado.add(combustibleCogeneracion)
        resultado.add(combustibleTermico)
        resultado.add(tipoCombustibleCog)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        resultado.add(arregloGraficaValores3)
        resultado.add(arregloGraficaValores4)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def graficaEnergiaTermica(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar         
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [3] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        
        def i = 0;
       
        use (TimeCategory) {
            fechaAtras = fechaHoy - 11.month
            fechaAtras = new Date(fechaAtras.year, fechaAtras.month, 1)

            while(i<12)
            {
                def fechaTempActual = fechaAtras + 1.month
                if((i+1) == 12)
                {
                        
                    fechaAtras = new Date(fechaHoy.year, fechaHoy.month, 1)
                    fechaTempActual = fechaHoy
                     
                }                    
                def arregloTermicaGenerada= generacionEnergiaTermicaPeriodo(sitio, fechaTempActual, fechaAtras)
                    
                valoresXmes1[i] = (double) arregloTermicaGenerada[1]
                valoresXmes2[i] = (double) arregloTermicaGenerada[2]
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaTempActual);  
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");                    
                String formatted = format1.format(cal.getTime());
                                 
                mesesOrden[i] = formatted
                fechaAtras = fechaAtras + 1.month
                i++;
            }
            
        }
            
        resultArreglo[0] = mesesOrden
        resultArreglo[1] = valoresXmes1
        resultArreglo[2] = valoresXmes2
        
         println "devuelve pra grafica**"
        println resultArreglo
       
        return resultArreglo
            
            
    }
    
    def graficaEnergiaElectrica(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar         
        def valoresXmes3=[12] //El segundo arreglo para el primer conjunto de valores a graficar  
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [4] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        
        def i = 0;
       
        use (TimeCategory) {
            fechaAtras = fechaHoy - 11.month
            fechaAtras = new Date(fechaAtras.year, fechaAtras.month, 1)

            while(i<12)
            {
                def fechaTempActual = fechaAtras + 1.month
                if((i+1) == 12)
                {
                        
                    fechaAtras = new Date(fechaHoy.year, fechaHoy.month, 1)
                    fechaTempActual = fechaHoy
                     
                }                    
                
                
                
                //def arregloTermicaGenerada= generacionEnergiaTermicaPeriodo(sitio, fechaTempActual, fechaAtras)
                def arregloEnergiasCFE= energiaElectricaCFE(sitio, fechaTempActual, fechaAtras)
                def arregloEnergiasElectricasCogeneracion = energiaElectricaSistemaCogeneracion(sitio, fechaTempActual, fechaAtras)
                def arregloEnergiasElectricasFotovoltaico = energiaElectricaSistemaFotovoltaico(sitio, fechaTempActual, fechaAtras)                    
                valoresXmes1[i] = (double) (arregloEnergiasCFE[0]+arregloEnergiasCFE[1]+arregloEnergiasCFE[2])
                valoresXmes2[i] = (double) (arregloEnergiasElectricasCogeneracion[0]+arregloEnergiasElectricasCogeneracion[1]+arregloEnergiasElectricasCogeneracion[2])
                valoresXmes3[i] = (double) (arregloEnergiasElectricasFotovoltaico[0]+arregloEnergiasElectricasFotovoltaico[1]+arregloEnergiasElectricasFotovoltaico[2])
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaTempActual);  
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");                    
                String formatted = format1.format(cal.getTime());
                                 
                mesesOrden[i] = formatted
                fechaAtras = fechaAtras + 1.month
                i++;
            }
            
        }
            
        resultArreglo[0] = mesesOrden
        resultArreglo[1] = valoresXmes1
        resultArreglo[2] = valoresXmes2
        resultArreglo[3] = valoresXmes3
       
        return resultArreglo
            
            
    }           
    
   def graficaIndex(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        def valoresXmes3=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [4] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        
        def i = 0;
       
        use (TimeCategory) {
            fechaAtras = fechaHoy - 11.month
            fechaAtras = new Date(fechaAtras.year, fechaAtras.month, 1)

            while(i<12)
            {
                def fechaTempActual = fechaAtras + 1.month
                if((i+1) == 12)
                {
                        
                    fechaAtras = new Date(fechaHoy.year, fechaHoy.month, 1)
                    fechaTempActual = fechaHoy
                     
                }
                
                def energiaTermicaGeneradaVal  = (double)energiaTermicaGenerada(sitio, fechaTempActual, fechaAtras)
                def energiaElectricaGeneradaVal = (double)energiaElectricaGenerada(sitio, fechaTempActual, fechaAtras)
                def consumoCombustibleVal = (double)consumoCombustible(sitio, fechaTempActual, fechaAtras)                
                
                valoresXmes1[i] = (double) energiaTermicaGeneradaVal
                valoresXmes2[i] = (double) energiaElectricaGeneradaVal
                valoresXmes3[i] = (double) consumoCombustibleVal
                
                    
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaTempActual);  
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");                    
                String formatted = format1.format(cal.getTime());
                    
             

                mesesOrden[i] = formatted
                fechaAtras = fechaAtras + 1.month
                i++;
            }
            
        }
            
        resultArreglo[0] = mesesOrden
        resultArreglo[1] = valoresXmes1
        resultArreglo[2] = valoresXmes2
        resultArreglo[3] = valoresXmes3
       
       
        return resultArreglo
            
            
    }
    
    def graficaCombustibles(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        def valoresXmes3=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        def valoresXmes4=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [5] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        
        def i = 0;
       
        use (TimeCategory) {
            fechaAtras = fechaHoy - 11.month
            fechaAtras = new Date(fechaAtras.year, fechaAtras.month, 1)

            while(i<12)
            {
                def fechaTempActual = fechaAtras + 1.month
                if((i+1) == 12)
                {
                        
                    fechaAtras = new Date(fechaHoy.year, fechaHoy.month, 1)
                    fechaTempActual = fechaHoy
                     
                }
                def combustibleCogeneracion = combustibleYEnergiaSistemaCogeneracion(sitio, fechaTempActual, fechaAtras)
                def combustibleTermico = combustibleYEnergiaSistemaTermico(sitio, fechaTempActual, fechaAtras)
                    
                
                valoresXmes1[i] = (double) combustibleCogeneracion[0]
                valoresXmes2[i] = (double) combustibleTermico[0]
                valoresXmes3[i] = (double) combustibleCogeneracion[1]
                valoresXmes4[i] = (double) combustibleTermico[1]
                    
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaTempActual);  
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");                    
                String formatted = format1.format(cal.getTime());
                    
             

                mesesOrden[i] = formatted
                fechaAtras = fechaAtras + 1.month
                i++;
            }
            
        }
            
        resultArreglo[0] = mesesOrden
        resultArreglo[1] = valoresXmes1
        resultArreglo[2] = valoresXmes2
        resultArreglo[3] = valoresXmes3
        resultArreglo[4] = valoresXmes4
       
        return resultArreglo
            
            
    }
    
    //------Metodos Auxiliares -----
    //sber el tipo de combustible de un sistema Gas LP, Diesel o Gas Natural
    def tipoCombustible(fuenteSitio)
    {
        def combustible
        
        combustible = CombustibleFuente.get(fuenteSitio.combustibleFuenteId)              
     
        if(combustible)
        return combustible.nombre
        else 
        return ""
    }
    
    
    //Devuelve la energia termica total de un sistema de cogeneracion segun las fechas y el sitio entrante
    //tipoEnergia puede ser: Termica o Electrica o CFE
    def energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, String tipoEnergia)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def energiaTermicaTotalCogeneracion = 0
        def energiaElectricaTotalCogeneracion = 0
        def energiaCFETotalCogeneracion = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida  
                    energiaElectricaTotalCogeneracion += tupla.energiaSalida  
                    energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
        if( tipoEnergia == "Termica")
        return energiaTermicaTotalCogeneracion
        else if( tipoEnergia == "Electrica")
        return energiaElectricaTotalCogeneracion
        else
        return energiaCFETotalCogeneracion
    }
    
    //Devulve un arreglo de 3 conteniendo la energiaCFE en base, intermedia y punta obtenenida del sistema de cogeneracion
    def energiasCFEBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def arregloEnergias = [0,0,0]
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //Saber en donde sumar las horas:
                    //Seugun la tabla de horarios de excel proporcionada
                    use(groovy.time.TimeCategory) {
                    
                        //Es entre semana
                        if(tupla.dateCreated.day>=1 && tupla.dateCreated.day<=5)
                        {
                            //Es base
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 6)
                            {
                                arregloEnergias[0]+=tupla.energiaCfe
                            }
                            //Es intermedio
                            else if((tupla.dateCreated.hours >= 6 && tupla.dateCreated.hours< 20) || (tupla.dateCreated.hours >= 22 && tupla.dateCreated.hours< 24) )
                            {
                                arregloEnergias[1]+=tupla.energiaCfe
                            }else //es punta
                            {
                                arregloEnergias[2]+=tupla.energiaCfe
                            }
                        }else if(tupla.dateCreated.day == 6)
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 7)
                            {
                                arregloEnergias[0]+=tupla.energiaCfe
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaCfe
                            }
                        }else
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 19)
                            {
                                arregloEnergias[0]+=tupla.energiaCfe
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaCfe
                            }
                        }
                     
                    }
                    
                    // energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
    
        return arregloEnergias
    }
        
    //Devuelve un arreglo de 3 conteniendo la energia base, intermedia y punta del sistema de cogeneracion
    def energiasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def arregloEnergias = [0,0,0]
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //Saber en donde sumar las horas:
                    //Seugun la tabla de horarios de excel proporcionada
                    use(groovy.time.TimeCategory) {
                    
                        //Es entre semana
                        if(tupla.dateCreated.day>=1 && tupla.dateCreated.day<=5)
                        {
                            //Es base
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 6)
                            {
                                arregloEnergias[0]+=tupla.energiaSalida
                            }
                            //Es intermedio
                            else if((tupla.dateCreated.hours >= 6 && tupla.dateCreated.hours< 20) || (tupla.dateCreated.hours >= 22 && tupla.dateCreated.hours< 24) )
                            {
                                arregloEnergias[1]+=tupla.energiaSalida
                            }else //es punta
                            {
                                arregloEnergias[2]+=tupla.energiaSalida
                            }
                        }else if(tupla.dateCreated.day == 6)
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 7)
                            {
                                arregloEnergias[0]+=tupla.energiaSalida
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaSalida
                            }
                        }else
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 19)
                            {
                                arregloEnergias[0]+=tupla.energiaSalida
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaSalida
                            }
                        }
                     
                    }
                    
                    // energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
    
        return arregloEnergias
    }
    
    def energiasBIPFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Panel solar")                  
        def arregloEnergias = [0,0,0]
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaFotovoltaicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //Saber en donde sumar las horas:
                    //Seugun la tabla de horarios de excel proporcionada
                    use(groovy.time.TimeCategory) {
                    
                        //Es entre semana
                        if(tupla.dateCreated.day>=1 && tupla.dateCreated.day<=5)
                        {
                            //Es base
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 6)
                            {
                                arregloEnergias[0]+=tupla.energiaCa
                            }
                            //Es intermedio
                            else if((tupla.dateCreated.hours >= 6 && tupla.dateCreated.hours< 20) || (tupla.dateCreated.hours >= 22 && tupla.dateCreated.hours< 24) )
                            {
                                arregloEnergias[1]+=tupla.energiaCa
                            }else //es punta
                            {
                                arregloEnergias[2]+=tupla.energiaCa
                            }
                        }else if(tupla.dateCreated.day == 6)
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 7)
                            {
                                arregloEnergias[0]+=tupla.energiaCa
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaCa
                            }
                        }else
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 19)
                            {
                                arregloEnergias[0]+=tupla.energiaCa
                            }else
                            {
                                arregloEnergias[1]+=tupla.energiaCa
                            }
                        }
                     
                    }
                    
                    // energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
    
        return arregloEnergias
    }
    
    //Devuelve la energia alterna de salida del sistema fotovoltaico
    def energiaTotalFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Panel solar")                  
        def energiaCorrienteAlterna = 0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaFotovoltaicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaCorrienteAlterna += tupla.energiaCa
                }        
            }
           
        }
        return energiaCorrienteAlterna
    }
    
    //Devuelve la energia  total de un sistema termico segun las fechas y el sitio entrante
    def energiaTermicaTotalSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def energiaTermicaTotal = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaTermicaTotal += tupla.energia   
                }        
            }
           
        }
        return energiaTermicaTotal
    }
    
    //Devuelve un arreglo de 3 conteniendo las 3 energias del sistema de cogeneracion 
    //[0] : Energia Termica, [1]: Energia Electrica, [2]: Energia CFE
    def energiasDeSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def energiaTermicaTotalCogeneracion = 0
        def energiaElectricaTotalCogeneracion = 0
        def energiaCFETotalCogeneracion = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida  
                    energiaElectricaTotalCogeneracion += tupla.energiaSalida  
                    energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
        
        def arregloResultado = []
        arregloResultado.add(energiaTermicaTotalCogeneracion)
        arregloResultado.add(energiaElectricaTotalCogeneracion)
        arregloResultado.add(energiaCFETotalCogeneracion)
        return arregloResultado
        
    }
    
    //Devuelve la suma de todo el combustible que utiliza el sistema de cogeneracion dado
    def combustibleSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def combustibleConsumo = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    combustibleConsumo += tupla.fuelEnergyFlow
                }        
            }
           
        }
        
        return combustibleConsumo
        
    }
    
    
    
    def combustibleSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def combustibleConsumo = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    combustibleConsumo += flujoCombustible
                }        
            }
           
        }
        
        return combustibleConsumo
        
    }
    
    // - ----Primer pantalla de Energeticos-----
    
    //Resultado para primer pantalla de energeticos : ENERGÃ�A TÃ‰RMICA GENERADA
    def energiaTermicaGenerada(sitio, fechaActual, fechaMesAtras) //DT065
    {
        def DT061 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Termica")
        def DT064 = energiaTermicaTotalSistemaTermico(sitio, fechaActual, fechaMesAtras)
        return DT061 - DT064
    }
    
    //Resultado para primer pantalla de energeticos : ENERGÃ�A Electrica GENERADA
    def energiaElectricaGenerada(sitio, fechaActual, fechaMesAtras)
    {
        def energiasCog = energiasDeSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
        def energiaElectricaFotovoltaico = energiaTotalFotovoltaico(sitio, fechaActual, fechaMesAtras)
        return energiasCog[0] + energiasCog[0] + energiaElectricaFotovoltaico
    }
    
    //Resultado del consumo de combustible total primer pantalla: CONSUMO DE COMBUSTIBLE
    def consumoCombustible(sitio, fechaActual, fechaMesAtras)
    {
        return (combustibleSistemaCogeneracion(sitio, fechaActual, fechaMesAtras) + combustibleSistemaTermico(sitio, fechaActual, fechaMesAtras))
    }
    
    //Radiacion solar del sistema termico
    def radiacionSolarSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def radiacionSolar = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }              
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    radiacionSolar += tupla.radiacionSolar
                }        
            }
           
        }
        return radiacionSolar
        
    }
    
    //nivel de humedad del sistema de cogeneracion??? o termico? Se queda en termico
    def nivelesHumedadSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def nivelHumedad = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    nivelHumedad += tupla.nivelHumedad
                }        
            }
           
        }
        
        return nivelHumedad
        
    }
    
    def nivelesHumedadSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def nivelHumedad = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    nivelHumedad += tupla.nivelHumedad
                }        
            }
           
        }
        
        return nivelHumedad
        
    }
    
    //Temperatura del sistema termico? o cogneracion, se queda termico
    def temperaturaSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def temp = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    temp = tupla.tempAmbiente
                }        
            }
           
        }
        
        return temp
        
    }
    
   def temperaturaSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def temp = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }
                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    temp = tupla.tempAmbiente
                }        
            }
           
        }
        
        return temp
    }
    
    //Energia ttoal del intercambiador
    def energiaTermicaIntercambiador(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def energia = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroIntercambiadorHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
              
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    energia += tupla.energiaTermicaSalida
                }        
            }
           
        }
        
        return energia
        
    }
    //Flujo total de agua del intercambiador
    def flujoAguaIntercambiador(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def energia = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroIntercambiadorHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    energia += tupla.flujoAguaEntrada
                }        
            }
           
        }
        return energia
    }
    
    //***************************** FIN METODOS PRIMER PANTALLA ***********************************
    
    //Devuelve un dictionario (mapa) con todos los atributos de de un intercambiador de placas sumados 
    def variablesSumadasIntercambiadorPlacas(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0
        
        def tempAguaEntradaTanqueInercia = 0
        def tempAguaSalidaTanqueInercia= 0
        def tempAguaEntradaTanqueServicio= 0
        def tempAguaSalidaTanqueServicio= 0
        
        def flujoAguaTanqueInercia= 0
        def flujoAguaTanqueServicio= 0
        
        
        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroIntercambiadorPlacasHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    tempAguaEntradaTanqueInercia += tupla.tempAguaEntradaTanqueInercia
                    tempAguaSalidaTanqueInercia += tupla.tempAguaSalidaTanqueInercia
                    tempAguaEntradaTanqueServicio += tupla.tempAguaEntradaTanqueServicio
                    
                    tempAguaSalidaTanqueServicio += tupla.tempAguaSalidaTanqueServicio
                    flujoAguaTanqueInercia += tupla.flujoAguaTanqueInercia
                    flujoAguaTanqueServicio += tupla.flujoAguaTanqueServicio
                    
                }        
            }
           
        }
        
        def map = [tempAguaEntradaTanqueInercia:tempAguaEntradaTanqueInercia,
            tempAguaSalidaTanqueInercia:tempAguaSalidaTanqueInercia,
            tempAguaEntradaTanqueServicio:tempAguaEntradaTanqueServicio,
            tempAguaSalidaTanqueServicio:tempAguaSalidaTanqueServicio,
            flujoAguaTanqueInercia:flujoAguaTanqueInercia,
            flujoAguaTanqueServicio:flujoAguaTanqueServicio]
        
        return map
    }
    
    //Devuelve un dictionario (mapa) con todos los atributos de de un intercambiador  sumados 
    def variablesSumadasIntercambiador(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0
        
        def energiaTermicaEntrada = 0
        def energiaTermicaSalida= 0
        def tempAireSalida= 0
        def tempAguaEntrada= 0
        def tempAguaSalida= 0
        def flujoAireEntrada= 0
        def flujoAguaEntrada= 0
        
        
        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroIntercambiadorHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    energiaTermicaEntrada += tupla.energiaTermicaEntrada
                    energiaTermicaSalida += tupla.energiaTermicaSalida
                    tempAireSalida += tupla.tempAireSalida
                    tempAguaEntrada += tupla.tempAguaEntrada
                    tempAguaSalida += tupla.tempAguaSalida
                    flujoAireEntrada += tupla.flujoAireEntrada
                    flujoAguaEntrada += tupla.flujoAguaEntrada
                }        
            }
           
        }
        
        def map = [energiaTermicaEntrada:energiaTermicaEntrada, energiaTermicaSalida:energiaTermicaSalida, tempAireSalida:tempAireSalida,tempAguaEntrada:tempAguaEntrada
            ,tempAguaSalida:tempAguaSalida,flujoAireEntrada:flujoAireEntrada,flujoAguaEntrada:flujoAguaEntrada]
        
        return map
    }
    
    //-------- pantalla de ENERGIA TERMICA GENERADA ----------------------
    
    //Devuleve un arreglo de 3 para el primer cuadro de la pantalla [0]: energia total, [1]: Energ. cogeneracion, [2]: Energ. Termico
    def generacionEnergiaTermicaPeriodo(sitio, fechaActual, fechaMesAtras) //T005
    {
        def arregloResult = []
        def DT061 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras,"Termica")
        def DT064 = energiaTermicaTotalSistemaTermico(sitio, fechaActual, fechaMesAtras)
        arregloResult.add((double)(DT061+DT064))
        arregloResult.add((double)DT061)
        arregloResult.add((double)DT064)
        return arregloResult
    }
    
    //Devuelve un arreglo de tamaÃ±o 5 para el segundo cuaro de la pantalla [0]:energiaAireAgua, [1]: Augua-agua [2]...
    //La Total es la suma de las 5 Casillas
    def energiaAprovechadaCogeneracion(sitio, fechaActual, fechaMesAtras) //T005
    {
        def arregloResult = []
        
        def DT053 = 4.186 //??
        def todoValorIntercambiador = variablesSumadasIntercambiador(sitio, fechaActual, fechaMesAtras)
      
        def DT048 = todoValorIntercambiador.get("flujoAguaEntrada")
        def DT033 = todoValorIntercambiador.get("tempAguaSalida")
        def DT032 = todoValorIntercambiador.get("tempAguaEntrada")
        
        def todoValorIntercambiadorPlacas  = variablesSumadasIntercambiadorPlacas(sitio, fechaActual, fechaMesAtras)  
        def DT049 = todoValorIntercambiadorPlacas.get("flujoAguaTanqueInercia")
        def DT041 = todoValorIntercambiadorPlacas.get("tempAguaEntradaTanqueInercia")
        def DT040 =  todoValorIntercambiadorPlacas.get("tempAguaSalidaTanqueInercia")
        def DT050 =  todoValorIntercambiadorPlacas.get("flujoAguaTanqueServicio")
        def DT038 = todoValorIntercambiadorPlacas.get("tempAguaSalidaTanqueServicio")
        def DT037 = todoValorIntercambiadorPlacas.get("tempAguaEntradaTanqueServicio")
        
        def DT055 = (DT053*DT048*(DT033-DT032))
        def DT056 = (DT053*DT049*(DT041-DT040))
        def DT057 = (DT053*DT050*(DT038-DT037))
        def DT0685 = 1 //??
        def DT0686 = 1 //??
        
        def total = DT055+DT056+DT057+DT0685+DT0686
        arregloResult.add((double)total)
        arregloResult.add((double)DT055)
        arregloResult.add((double)DT056)
        arregloResult.add((double)DT057)
        arregloResult.add((double)DT0685)
        arregloResult.add((double)DT0686)
        return arregloResult
    }
    
    //Devuelve la eficiencia termica en un arreglo que contiene:
    //[0]:DT062 , [1]:DT055, [2]:DT056, [3]:DT057
    def eficienciaTermica(sitio, fechaActual, fechaMesAtras)
    {
        def DT053 = 4.186 //??
        def todoValorIntercambiador = variablesSumadasIntercambiador(sitio, fechaActual, fechaMesAtras)
      
        def DT048 = todoValorIntercambiador.get("flujoAguaEntrada")
        def DT033 = todoValorIntercambiador.get("tempAguaSalida")
        def DT032 = todoValorIntercambiador.get("tempAguaEntrada")
        
        def todoValorIntercambiadorPlacas  = variablesSumadasIntercambiadorPlacas(sitio, fechaActual, fechaMesAtras)  
        def DT049 = todoValorIntercambiadorPlacas.get("flujoAguaTanqueInercia")
        def DT041 = todoValorIntercambiadorPlacas.get("tempAguaEntradaTanqueInercia")
        def DT040 =  todoValorIntercambiadorPlacas.get("tempAguaSalidaTanqueInercia")
        def DT050 =  todoValorIntercambiadorPlacas.get("flujoAguaTanqueServicio")
        def DT038 = todoValorIntercambiadorPlacas.get("tempAguaSalidaTanqueServicio")
        def DT037 = todoValorIntercambiadorPlacas.get("tempAguaEntradaTanqueServicio")
        
        def DT055 = (DT053*DT048*(DT033-DT032))
        def DT056 = (DT053*DT049*(DT041-DT040))
        def DT057 = (DT053*DT050*(DT038-DT037))
        
        def DT062 = DT055+DT056+DT057
        def eficiencia = []
        eficiencia.add((double)DT062)
        eficiencia.add((double)DT055)
        eficiencia.add((double)DT056)
        eficiencia.add((double)DT057)
        
        return eficiencia
    }
    
    // ------- pantalla ENERGIA ELECTRICA GENERADA -------------------------
    
    //Devuelve el arreglo conteniendo las energiasCFE base inter y punta del sist. de cogeneracion
    def energiaElectricaCFE(sitio, fechaActual, fechaMesAtras)
    {
        return energiasCFEBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
    }
    
    //Devuelve el arreglo conteniendo las energias base, inter y punta de cogeneracion
    def energiaElectricaSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        return energiasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
    }
    
    //Devuelve un arreglo con las energias base, intermecio y punta del sistema fotovoltaico
    def energiaElectricaSistemaFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        return energiasBIPFotovoltaico(sitio, fechaActual, fechaMesAtras)
    }
    
    // ------- pantalla Combustibles -------------------------
    //Devuelve un arreglo de 2, [0] suma total ed combustible, [1] : suma total de energia*poder calorifico de Cogeneracion
    def combustibleYEnergiaSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def arregloResult = [0.0,0.0]
        def combustibleConsumo = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0
        def poderCalorificoGasLP  = sitio.poderCalorificoGasLp //??
        def poderCalorificoGasNatural = sitio.poderCalorificoGasNatural //?
        def poderCalorificoDiesel = sitio.poderCalorificoDiesel //?

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    arregloResult[0] += tupla.fuelEnergyFlow
                    arregloResult[1]  += tupla.fuelEnergyFlow
                }        
            }
            
            if(tipoCombustible(fuentes[0])=="Gas LP")
            {
                arregloResult[1] = arregloResult[1] * poderCalorificoGasLP
            }else if(tipoCombustible(fuentes[0])=="Diesel")
            {
                arregloResult[1] = arregloResult[1] * poderCalorificoDiesel
            }else{
                arregloResult[1] = arregloResult[1] * poderCalorificoGasNatural
            }
            
        }
       
        def arregloResult2 = []
        arregloResult2[0] = (double)arregloResult[0]
        arregloResult2[1] = (double)arregloResult[1]
        
        return arregloResult2
        
    }
    
    //Devuelve un arreglo de 2, [0] suma total de combustible, [1] : suma total de energia*poder calorifico de Termico
    def combustibleYEnergiaSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def arregloResult = [0.0,0.0]
        def combustibleConsumo = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0
        
        
        
        def poderCalorificoGasLP  = sitio.poderCalorificoGasLp //??
        def poderCalorificoGasNatural = sitio.poderCalorificoGasNatural //?
        def poderCalorificoDiesel = sitio.poderCalorificoDiesel //?

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaTermicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
               
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    arregloResult[0] += tupla.flujoCombustible
                    arregloResult[1]  += tupla.flujoCombustible
                }        
            }
            
            if(tipoCombustible(fuentes[0])=="Gas LP")
            {
                arregloResult[1] = arregloResult[1] * poderCalorificoGasLP
            }else if(tipoCombustible(fuentes[0])=="Diesel")
            {
                arregloResult[1] = arregloResult[1] * poderCalorificoDiesel
            }else{
                arregloResult[1] = arregloResult[1] * poderCalorificoGasNatural
            }
            
        }
        def arregloResult2 = []
        arregloResult2[0] = (double)arregloResult[0]
        arregloResult2[1] = (double)arregloResult[1]
        
        return arregloResult2
        
    }
    
    def getFuentesActivas(){
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        def fuentes = FuenteSitio.findAllWhere(sitio:thisSite, estatus:"activo")  
        def arreglo = []
                
        for(fuente in fuentes)
        {
            arreglo.add(fuente.id)
            arreglo.add(fuente.nombre)
        }
        
        def xml
        withFormat(){
            xml = arreglo as XML
        }
        render xml
         
    }
    
    
}
