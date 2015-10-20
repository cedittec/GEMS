package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import groovy.time.TimeCategory
import com.GEMS1.Data.*
import com.GEMS1.Scada.*


@Secured(['ROLE_SysAdmin','ROLE_adminTecnico','ROLE_clienteAdmin','ROLE_clienteTecnico', 'ROLE_adminVentas'])
class TiempoRealController {
    
def springSecurityService
    def index() { 
        def num =0
        def numFuentes = 0
        def sitios
        def fuentes = []
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def resultTempTanqueInercia  = 0.0
        def resultPotenciaTotal= 0.0
        def comsutiblesArreglo = [0.0,0.0]
        def radiacionSolar = 0.0
        def nivelesHumedad = 0.0
        def temp = 0.0
        
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
        
        if(sitios)
        {
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
            fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")            
            if(fuentes){
                resultTempTanqueInercia= temperaturaTanqueInerciaDT030( fuentes.get(0))   
                resultPotenciaTotal=  potenciaGeneradaDT012( fuentes.get(0))   
                comsutiblesArreglo = auxiliarT007( fuentes.get(0))
                def arregloTemp =  datosAmbiente( fuentes.get(0))
                radiacionSolar = arregloTemp[0]
                nivelesHumedad = arregloTemp[1]
                temp = arregloTemp[2]
            }
        }
        
        num = sitios.size()
        
       numFuentes = fuentes.size()
       [sitios:sitios,fuentes:fuentes, numeroSitios:num, rol:rol, numFuentes:numFuentes, resultTempTanqueInercia:resultTempTanqueInercia,
       resultPotenciaTotal:resultPotenciaTotal,comsutiblesArreglo:comsutiblesArreglo,radiacionSolar:radiacionSolar,nivelesHumedad:nivelesHumedad,
       temp:temp]        
    }
    
    
     def energiaElectrica() { 
        def num =0
        def numFuentes = 0
        def sitios
        def fuentes
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def resultadoPotenciaCFE = 0.0
        def resultadoPotenciaFotovoltaico = 0.0
        def resultadoPotenciaCogeneracion = 0.0
        def resultadoPotenciaTotal = 0.0
        
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
                
        if(sitios)
        {
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
            fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")            
            if(fuentes){
             
                resultadoPotenciaCFE += auxiliarDT009(fuentes.get(0))
                resultadoPotenciaFotovoltaico += auxiliarDT011(fuentes.get(0))
                resultadoPotenciaCogeneracion += auxiliarDT010(fuentes.get(0))
                resultadoPotenciaTotal += resultadoPotenciaCFE+resultadoPotenciaFotovoltaico+resultadoPotenciaCogeneracion               
            }
        }
        
        num = sitios.size()
        numFuentes = fuentes.size()
       [sitios:sitios,numFuentes:numFuentes,fuentes:fuentes,resultadoPotenciaTotal:resultadoPotenciaTotal, numeroSitios:num, rol:rol, resultadoPotenciaCFE:resultadoPotenciaCFE,resultadoPotenciaFotovoltaico:resultadoPotenciaFotovoltaico
       ,resultadoPotenciaCogeneracion:resultadoPotenciaCogeneracion]        
    }
    
    def energiaTermica() { 
        def num =0
         def numFuentes = 0
        def sitios
        def fuentes
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def resultTempTanqueInercia  = 0.0
        def resultTempTanqueServicios = 0.0
        def resultTempTanqueSistemaTermico = 0.0
        def resultTempTanqueCondensados = 0.0
        def resultTempTanqueVapor = 0.0
        def resultPosicionDiverter  = 0.0
        def resultTempAireCalienteEntradaIntercambiador = 0.0
        def resultTempAireCalienteSalidaIntercambiador = 0.0
        def resultFlujoEconomizador = 0.0
        def resultFlujoVaporizador = 0.0
        def resultFlujoSupercalentador = 0.0
        def resultNivelTanqueServicio = 0.0
        def resultNivelTanqueInercia = 0.0
        def resultNivelTanqueCondensados = 0.0
        def resultNivelTanqueVapor = 0.0
        
        def graficaFlujoCircuitoValores = [0.0,0.0,0.0,0.0]
        def graficaTempEntradaCog = [0.0,0.0,0.0,0.0,0.0,0.0 ]
        def graficaTempAgua = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0 ]
        def graficaPresionTanquesCog = [0.0,0.0,0.0,0.0,0.0]
        
        
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
                
        if(sitios)
        {
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
            fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")            
            if(fuentes){
                resultTempTanqueInercia += temperaturaTanqueInerciaDT030( fuentes.get(0))   
                
                resultTempTanqueServicios += auxiliarDT031(fuentes.get(0))
                resultTempTanqueSistemaTermico += auxiliarDT044(fuentes.get(0))
                resultTempTanqueCondensados += auxiliarDT0505(fuentes.get(0))
                
                resultTempTanqueVapor += auxiliarDT05011(fuentes.get(0))
                resultPosicionDiverter  += auxiliarDT05021(fuentes.get(0))
                resultTempAireCalienteEntradaIntercambiador += auxiliarDT034(fuentes.get(0))
                resultTempAireCalienteSalidaIntercambiador += auxiliarDT035(fuentes.get(0))
                resultFlujoEconomizador += auxiliarDT05010(fuentes.get(0))
                resultFlujoVaporizador += auxiliarDT05016(fuentes.get(0))
                resultFlujoSupercalentador += auxiliarDT05019(fuentes.get(0))
                
                resultNivelTanqueServicio += auxiliarDT042(fuentes.get(0))
                resultNivelTanqueInercia += auxiliarDT043(fuentes.get(0))
                resultNivelTanqueCondensados += auxiliarDT0507(fuentes.get(0))
                resultNivelTanqueVapor += auxiliarDT05013(fuentes.get(0))
                
                graficaFlujoCircuitoValores=graficasEnergiaTermicaFlujoAgua(fuentes.get(0))             
                graficaTempEntradaCog= graficasEnergiaTermicaTemperaturaEntradaCog(fuentes.get(0)) 
                graficaTempAgua = graficasEnergiaTermicaTemperaturaAgua(fuentes.get(0)) 
                graficaPresionTanquesCog = graficasEnergiaTermicaPresionTanquesCog(fuentes.get(0))  
                
            }
        }
        
        num = sitios.size()
           numFuentes = fuentes.size()
       [sitios:sitios,numFuentes:numFuentes,fuentes:fuentes, numeroSitios:num, rol:rol,resultTempTanqueInercia:resultTempTanqueInercia,resultTempTanqueSistemaTermico:resultTempTanqueSistemaTermico,
       resultTempTanqueCondensados:resultTempTanqueCondensados,resultTempTanqueVapor:resultTempTanqueVapor,resultPosicionDiverter:resultPosicionDiverter,
       resultTempAireCalienteEntradaIntercambiador:resultTempAireCalienteEntradaIntercambiador,resultTempAireCalienteSalidaIntercambiador:resultTempAireCalienteSalidaIntercambiador,
        resultFlujoEconomizador:resultFlujoEconomizador,resultFlujoVaporizador:resultFlujoVaporizador,resultFlujoSupercalentador:resultFlujoSupercalentador,
        resultNivelTanqueServicio:resultNivelTanqueServicio,resultNivelTanqueInercia:resultNivelTanqueInercia,resultNivelTanqueCondensados:resultNivelTanqueCondensados,
        resultNivelTanqueVapor:resultNivelTanqueVapor,graficaFlujoCircuitoValores:graficaFlujoCircuitoValores,graficaPresionTanquesCog:graficaPresionTanquesCog,
       graficaTempEntradaCog:graficaTempEntradaCog,graficaTempAgua:graficaTempAgua]        
    }
    
    def nivelesCombustible() { 
        def num =0
         def numFuentes = 0
        def sitios
        def fuentes
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
       
        def resultadoNivelTanqueTermico = 0.0
        def resultadoNivelTanqueCondensados = 0.0
        def tempCombustibleTermico = 0.0
        def tempCombustibleCogeneracion = 0.0
        def presionCombustibleTermico = 0.0
        def presionCombustibleCogeneracion =0.0
        
        def graficaFlujoCombustibles = [0.0,0.0]
        
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
                
        if(sitios)
        {
            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
            fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")            
            if(fuentes){
                
                resultadoNivelTanqueTermico += auxiliarDT075(fuentes.get(0))
                resultadoNivelTanqueCondensados += auxiliarDT087(fuentes.get(0))
                tempCombustibleTermico += getTempCombustibleTermico(fuentes.get(0))
                tempCombustibleCogeneracion += getTempCombustibleCogeneracion(fuentes.get(0))
                presionCombustibleTermico += getPresionCombustibleTermico(fuentes.get(0))
                presionCombustibleCogeneracion +=getPresionCombustibleCogeneracion(fuentes.get(0))
                
                graficaFlujoCombustibles=graficasCombustiblesFlujo(fuentes.get(0))
            }
        }
        
        num = sitios.size()
            numFuentes = fuentes.size()
       [sitios:sitios,numFuentes:numFuentes,fuentes:fuentes, numeroSitios:num, rol:rol,resultadoNivelTanqueTermico:resultadoNivelTanqueTermico,
       resultadoNivelTanqueCondensados:resultadoNivelTanqueCondensados,    
       tempCombustibleTermico:tempCombustibleTermico,tempCombustibleCogeneracion:tempCombustibleCogeneracion,presionCombustibleTermico:presionCombustibleTermico,
       presionCombustibleCogeneracion:presionCombustibleCogeneracion, graficaFlujoCombustibles:graficaFlujoCombustibles]      
    }
    
 //************************************************************************************* 
    def graficasEnergiaTermicaFlujoAgua(fuenteSitio)
    {
        def resultSistemaTermico = 0.0        
        def resultIntercambiador = 0.0  
        def resultIntercambiadorPlacasInercia = 0.0  
        def resultIntercambiadorPlacasServicio = 0.0  
        def arregloResult = []
        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            resultSistemaTermico = registro.flujoAgua
        }
        
        registro = RegistroIntercambiador.find("From RegistroIntercambiador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            resultIntercambiador = registro.flujoAguaEntrada
        }
        
        registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            resultIntercambiadorPlacasInercia = registro.flujoAguaTanqueInercia
        }
        
        registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            resultIntercambiadorPlacasServicio = registro.flujoAguaTanqueServicio
        }
        
        arregloResult.add((double)resultSistemaTermico)
        arregloResult.add((double)resultIntercambiador)
        arregloResult.add((double)resultIntercambiadorPlacasInercia)
        arregloResult.add((double)resultIntercambiadorPlacasServicio)        
        
        return arregloResult 
    }
    
    def graficasCombustiblesFlujo (fuenteSitio)
    {
        def resultSistemaTermico = 0.0        
        def resultSistCogeneracion = 0.0  
        
        def arregloResult = []
        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            resultSistemaTermico = registro.nivelTanque;
        }
        
        registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            resultSistCogeneracion = registro.nivel
        }
        
        arregloResult.add((double)resultSistemaTermico)
        arregloResult.add((double)resultSistCogeneracion)       
        
        return arregloResult 
    }
    
    def graficasEnergiaTermicaTemperaturaEntradaCog(fuenteSitio)
    {
        def tempEconomizadorEntrada = 0.0      // no hay  
        def tempEconomizadorSalida = 0.0        //no hay
        
        def tempVaporizadorEntrada = 0.0  
        def tempVaporizadorSalida = 0.0  
        
        def tempSupercalentadorEntrada = 0.0  
        def tempSupercalentadorSalida = 0.0  
        
        def arregloResult = []
        
        def registro = Vaporizador.find("From Vaporizador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempVaporizadorEntrada = registro.tempEntrada
            tempVaporizadorSalida = registro.tempSalida
        }
        
        registro = Supercalentador.find("From Supercalentador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempSupercalentadorEntrada = registro.tempEntrada
            tempSupercalentadorSalida = registro.tempSalida
        }
        
//        registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
//        if(registro)
//        {
//            resultIntercambiadorPlacasInercia = registro.flujoAguaTanqueInercia
//        }
     
        arregloResult.add((double)tempEconomizadorEntrada)
        arregloResult.add((double)tempEconomizadorSalida)
        arregloResult.add((double)tempVaporizadorEntrada)
        arregloResult.add((double)tempVaporizadorSalida)
        arregloResult.add((double)tempSupercalentadorEntrada)
        arregloResult.add((double)tempSupercalentadorSalida)        
        
        return arregloResult 
    }
    
    def graficasEnergiaTermicaTemperaturaAgua(fuenteSitio)
    {
        def tempIntercambiadorAguaEntradaCog = 0.0      
        def tempIntercambiadorAguaSalidaCog = 0.0        
        
        def tempTanqueServiciosAguaEntradaCog = 0.0      
        def tempTanqueServiciosAguaSalidaCog = 0.0       
        
        def tempTanqueInerciaAguaEntradaCog = 0.0      
        def tempTanqueInerciaAguaSalidaCog = 0.0      
                
        
        def arregloResult = []
        
        def registro = RegistroIntercambiador.find("From RegistroIntercambiador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempIntercambiadorAguaEntradaCog = registro.tempAguaEntrada
            tempIntercambiadorAguaSalidaCog = registro.tempAguaSalida
        }
        
        registro = RegistroTanqueServicio.find("From RegistroTanqueServicio as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempTanqueServiciosAguaEntradaCog = registro.tempAguaEntrada
            tempTanqueServiciosAguaSalidaCog = registro.tempAguaSalida
        }
        
        registro = RegistroTanqueInercia.find("From RegistroTanqueInercia as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempTanqueInerciaAguaEntradaCog = registro.tempAguaEntrada
            tempTanqueInerciaAguaSalidaCog = registro.tempAguaSalida
        }
        
     
        arregloResult.add((double)tempIntercambiadorAguaEntradaCog)
        arregloResult.add((double)tempIntercambiadorAguaSalidaCog)
        arregloResult.add((double)tempTanqueServiciosAguaEntradaCog)
        arregloResult.add((double)tempTanqueServiciosAguaSalidaCog)
        arregloResult.add((double)tempTanqueInerciaAguaEntradaCog)
        arregloResult.add((double)tempTanqueInerciaAguaSalidaCog)        
        
        return arregloResult 
    }
    
    def graficasEnergiaTermicaPresionTanquesCog(fuenteSitio)
    {
        def tempEconomizadorEntrada = 0.0      // no hay  
        def tempEconomizadorSalida = 0.0        //no hay
        
        def tempVaporizadorEntrada = 0.0  
        def tempVaporizadorSalida = 0.0  
        
        def tempSupercalentadorEntrada = 0.0  
        def tempSupercalentadorSalida = 0.0  
        
        def arregloResult = []
        
        def registro = Vaporizador.find("From Vaporizador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempVaporizadorEntrada = registro.tempEntrada
            tempVaporizadorSalida = registro.tempSalida
        }
        
        registro = Supercalentador.find("From Supercalentador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            tempSupercalentadorEntrada = registro.tempEntrada
            tempSupercalentadorSalida = registro.tempSalida
        }
        
//        registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
//        if(registro)
//        {
//            resultIntercambiadorPlacasInercia = registro.flujoAguaTanqueInercia
//        }
     
        arregloResult.add((double)tempEconomizadorEntrada)
        arregloResult.add((double)tempEconomizadorSalida)
        arregloResult.add((double)tempVaporizadorEntrada)
        arregloResult.add((double)tempVaporizadorSalida)
        arregloResult.add((double)tempSupercalentadorEntrada)
        arregloResult.add((double)tempSupercalentadorSalida)        
        
        return arregloResult 
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
    
    def reporteActualizaCombustibles()
    {
        
        def resultadoNivelTanqueTermico = 0.0
        def resultadoNivelTanqueCondensados = 0.0
        def tempCombustibleTermico = 0.0
        def tempCombustibleCogeneracion = 0.0
        def presionCombustibleTermico = 0.0
        def presionCombustibleCogeneracion =0.0
        
        def graficaFlujoCombustibles = [0.0,0.0]
        
        def thisFuente = FuenteSitio.findById(Integer.parseInt(params?.fuenteId))
                 
        if(thisFuente){
            
            resultadoNivelTanqueTermico += (double)auxiliarDT075(thisFuente)
            resultadoNivelTanqueCondensados += auxiliarDT087(fuentes.get(0))
            tempCombustibleTermico +=(double) getTempCombustibleTermico(thisFuente)
            tempCombustibleCogeneracion += (double)getTempCombustibleCogeneracion(thisFuente)
            presionCombustibleTermico +=(double) getPresionCombustibleTermico(thisFuente)
            presionCombustibleCogeneracion +=(double)getPresionCombustibleCogeneracion(thisFuente)
            
            graficaFlujoCombustibles=graficasCombustiblesFlujo(thisFuente)
        }
                

        def resultado = []
        resultado.add(resultadoNivelTanqueTermico)
        resultado.add(resultadoNivelTanqueCondensados)
        resultado.add(tempCombustibleTermico)
        resultado.add(tempCombustibleCogeneracion)
        resultado.add(presionCombustibleTermico)
        resultado.add(presionCombustibleCogeneracion)
        resultado.add(graficaFlujoCombustibles)
        
        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    
    def reporteActualizaIndex()
    {
         def resultTempTanqueInercia  = 0.0
        def resultPotenciaTotal= 0.0
        def combustiblesArreglo = [0.0,0.0]
        def radiacionSolar = 0.0
        def nivelesHumedad = 0.0
        def temp = 0.0
            
       // def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
       // def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
       // fechaEnd.setHours(23)
       // fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        def thisFuente = FuenteSitio.findById(Integer.parseInt(params?.fuenteId))
          
        // costoEnergiaElectrica += (double)auxiliarDC027(thisSite,fechaEnd, fechaInit)
         
       
        if(thisFuente){
            resultTempTanqueInercia += (double) temperaturaTanqueInerciaDT030( thisFuente)   
            resultPotenciaTotal +=  (double) potenciaGeneradaDT012( thisFuente)   
            combustiblesArreglo += auxiliarT007( thisFuente) //Es arreglo de tamaño 2 
            
            //este arreglo no se devuelve al gsp ni en el xml
            def arregloTemp =  datosAmbiente( thisFuente)
            radiacionSolar += (double) arregloTemp[0]
            nivelesHumedad += (double) arregloTemp[1]
            temp += (double) arregloTemp[2]
        }
                

        def resultado = []
        
        resultado.add(resultTempTanqueInercia.round(2))
        resultado.add(resultPotenciaTotal)
        resultado.add((double)combustiblesArreglo[0])
        resultado.add((double)combustiblesArreglo[1])
        resultado.add(radiacionSolar)
        resultado.add(nivelesHumedad)
        resultado.add(temp)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    
    def reporteActualizaEnergiaElectrica()
    {
       def resultadoPotenciaCFE = 0.0
        def resultadoPotenciaFotovoltaico = 0.0
        def resultadoPotenciaCogeneracion = 0.0
        def resultadoPotenciaTotal = 0.0
            
       // def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
       // def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
       // fechaEnd.setHours(23)
       // fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        def thisFuente = FuenteSitio.findById(Integer.parseInt(params?.fuenteId))
          
        // costoEnergiaElectrica += (double)auxiliarDC027(thisSite,fechaEnd, fechaInit)
         
               
       
        if(thisFuente){
            
            resultadoPotenciaCFE += auxiliarDT009(thisFuente)
            resultadoPotenciaFotovoltaico += auxiliarDT011(thisFuente)
            resultadoPotenciaCogeneracion += auxiliarDT010(thisFuente)
            resultadoPotenciaTotal += resultadoPotenciaCFE+resultadoPotenciaFotovoltaico+resultadoPotenciaCogeneracion     
        }
                

        def resultado = []
        resultado.add(resultadoPotenciaCFE)
        resultado.add(resultadoPotenciaFotovoltaico)
      
        resultado.add(resultadoPotenciaCogeneracion)
        resultado.add(resultadoPotenciaTotal)
        

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }    
        
    def reporteActualizaEnergiaTermica()
    {
        def resultTempTanqueInercia  = 0.0
        def resultTempTanqueServicios = 0.0
        def resultTempTanqueSistemaTermico = 0.0
        def resultTempTanqueCondensados = 0.0
        def resultTempTanqueVapor = 0.0
        def resultPosicionDiverter  = 0.0
        def resultTempAireCalienteEntradaIntercambiador = 0.0
        def resultTempAireCalienteSalidaIntercambiador = 0.0
        def resultFlujoEconomizador = 0.0
        def resultFlujoVaporizador = 0.0
        def resultFlujoSupercalentador = 0.0
        def resultNivelTanqueServicio = 0.0
        def resultNivelTanqueInercia = 0.0
        def resultNivelTanqueCondensados = 0.0
        def resultNivelTanqueVapor = 0.0
        
                
        def graficaFlujoCircuitoValores = [0.0,0.0,0.0,0.0]
        def graficaTempEntradaCog = [0.0,0.0,0.0,0.0,0.0,0.0 ]
        def graficaTempAgua = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0 ]
        def graficaPresionTanquesCog = [0.0,0.0,0.0,0.0,0.0]
 
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        def thisFuente = FuenteSitio.findById(Integer.parseInt(params?.fuenteId))
        
                        
            if(thisFuente){
                resultTempTanqueInercia += temperaturaTanqueInerciaDT030( thisFuente)                   
                resultTempTanqueServicios += auxiliarDT031(thisFuente)
                resultTempTanqueSistemaTermico += auxiliarDT044(thisFuente)
                resultTempTanqueCondensados += auxiliarDT0505(thisFuente)
                
                resultTempTanqueVapor += auxiliarDT05011(thisFuente)
                resultPosicionDiverter  += auxiliarDT05021(thisFuente)
                resultTempAireCalienteEntradaIntercambiador += auxiliarDT034(thisFuente)
                resultTempAireCalienteSalidaIntercambiador += auxiliarDT035(thisFuente)
                
                resultFlujoEconomizador += auxiliarDT05010(thisFuente)
                resultFlujoVaporizador += auxiliarDT05016(thisFuente)
                resultFlujoSupercalentador += auxiliarDT05019(thisFuente)
                resultNivelTanqueServicio += auxiliarDT042(thisFuente)
                resultNivelTanqueInercia += auxiliarDT043(thisFuente)
                resultNivelTanqueCondensados += auxiliarDT0507(thisFuente)
                resultNivelTanqueVapor += auxiliarDT05013(thisFuente)
                
                graficaFlujoCircuitoValores=graficasEnergiaTermicaFlujoAgua(thisFuente)             
                graficaTempEntradaCog= graficasEnergiaTermicaTemperaturaEntradaCog(thisFuente)
                graficaTempAgua = graficasEnergiaTermicaTemperaturaAgua(thisFuente)
                graficaPresionTanquesCog = graficasEnergiaTermicaPresionTanquesCog(thisFuente) 
                
            }
                

        def resultado = []
        resultado.add((double)resultTempTanqueInercia)
        resultado.add((double)resultTempTanqueServicios)
        resultado.add((double)resultTempTanqueSistemaTermico)
        resultado.add((double)resultTempTanqueCondensados)
        
        resultado.add((double)resultTempTanqueVapor)
        resultado.add((double)resultPosicionDiverter)
        resultado.add((double)resultTempAireCalienteEntradaIntercambiador)
        resultado.add((double)resultTempAireCalienteSalidaIntercambiador)
        resultado.add((double)resultFlujoEconomizador)
        resultado.add((double)resultFlujoVaporizador)
        resultado.add((double)resultFlujoSupercalentador)
        resultado.add((double)resultNivelTanqueServicio)
        resultado.add((double)resultNivelTanqueInercia)
        resultado.add((double)resultNivelTanqueCondensados)
        resultado.add((double)resultNivelTanqueVapor)
        
        resultado.add(graficaFlujoCircuitoValores)
        resultado.add(graficaTempEntradaCog)
        resultado.add(graficaTempAgua)
        resultado.add(graficaPresionTanquesCog)                
                
        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    
    //Devuelve el nivel de tanque gas lp del sistema termico
    def auxiliarDT075(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivelTanque
        }
        
        return result 
    }
    
    def auxiliarDT087 (fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivel
        }
        
        return result 
    }
    
     def getPresionCombustibleCogeneracion(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.presionCombustible
        }
        
        return result 
    }
    
    //DT081,DT082 y DT083 no importa el tipo de combustible dado que solo pide la temp.
    def getTempCombustibleCogeneracion(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.temperaturaCombustible
        }
        
        return result 
    }
    //DT093,DT094 y DT095 no importa el tipo de combustible dado que solo pide la temp.
    def getTempCombustibleTermico(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.temperaturaCombustible
        }
        
        return result 
    }
    
    //DT096, DT097, DT098
    def getPresionCombustibleTermico(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.presionCombustible
        }
        
        return result 
    }
    
     //potencia CFE demanda facturable cog.
    def auxiliarDT010(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.potenciaSalida
        }
        
        return result 
    }
    
      //potencia CFE demanda facturable cog.
    def auxiliarDT009(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.potenciaCfe
        }
        
        return result 
    }
    
      //potencia ca fotovoltaico
    def auxiliarDT011(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaFotovoltaico.find("From RegistroSistemaFotovoltaico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.potenciaCa
        }
        
        return result 
    }
    
    
     //Nivel tanque vapor
    def auxiliarDT05013(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivel
        }
        
        return result 
    }
    
     //Nivel tanque Inercia
    def auxiliarDT0507(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivel
        }
        
        return result 
    }
    
      //Nivel tanque Inercia
    def auxiliarDT043(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueInercia.find("From RegistroTanqueInercia as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivel
        }
        
        return result 
    }
    
      //Nivel tanque Servicios
    def auxiliarDT042(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueServicio.find("From RegistroTanqueServicio as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.nivel
        }
        
        return result 
    }
    
     //flujo supercalentador sist. cogeneracion
    def auxiliarDT05019(fuenteSitio)
    {
        def result = 0.0        
        def registro = Supercalentador.find("From Supercalentador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.flujo
        }
        
        return result 
    }
    
    //flujo evaporizador sist. cogeneracion
    def auxiliarDT05016(fuenteSitio)
    {
        def result = 0.0        
        def registro = Vaporizador.find("From Vaporizador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.flujo
        }
        
        return result 
    }
    
    //flujo economiador de Cogeneracion
    def auxiliarDT05010(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.flujoAguaTanqueServicio
        }
        
        return result 
    }
    
    def auxiliarDT034(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroIntercambiador.find("From RegistroIntercambiador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.tempAireEntrada
        }
        
        return result 
    }
     def auxiliarDT035(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroIntercambiador.find("From RegistroIntercambiador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.tempAireSalida
        }
        
        return result 
    }
    
    def auxiliarDT05021(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.posicionDiverter
        }
        
        return result 
    }
    
    def auxiliarDT05011(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueVapor.find("From RegistroTanqueVapor as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.temp
        }
        
        return result 
    }
    
    def auxiliarDT0505(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.temp
        }
        
        return result 
    }
    
    def auxiliarDT044(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.tempAguaTanque
        }
        
        return result 
    }
    
    //Devuelve la temp del tanque de servicios de cog.
    def auxiliarDT031(fuenteSitio)
    {
        def result = 0.0        
        def registro = RegistroTanqueServicio.find("From RegistroTanqueServicio as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.tempAguaTanque
        }
        
        return result 
    }
    
    def temperaturaTanqueInerciaDT030(fuenteSitio)
    {   
        def result = 0.0        
        def registro = RegistroTanqueInercia.find("From RegistroTanqueInercia as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            result = registro.tempAguaTanque
        }
        
        return result 
    }
    
     def potenciaGeneradaDT012(fuenteSitio)
    {        
        def result = 0.0
        
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        
        if(registro)
        {            
            result += registro.potenciaCfe
            result += registro.potenciaSalida
        }
        registro = RegistroSistemaFotovoltaico.find("From RegistroSistemaFotovoltaico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {                   
            result += registro.potenciaCa
        }
        
        return result 
    }
    
    //Devuelve un arreglo que devuelev el nivel del tanque de combustible de [0]:Sistema Cog. , [1]:Sist. Termico
    def auxiliarT007(fuenteSitio){
        def DT075 = 0.0 // tmbn es dt076
        def DT087 = 0.0
        def arregloResp = [0.0,0.0]      
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        
        if(registro)
        {
            DT087 += registro.potenciaCfe
        }
        registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {                   
            DT075 += registro.nivelTanque
        }
        
        arregloResp[0] = DT075
        arregloResp[1] = DT087
        return arregloResp
        
    }
    
    //Devuelve las variables de ambiente: radiacion solar, nivel de humedad y temperatura en un arreglo
    //[0]:radiacion solar , [1]:nivel de humedad, [2]: temperatura
    def datosAmbiente(fuenteSitio)
    {   
        def radiacion = 0.0
        def nivelHumedad = 0.0
        def temp = 0.0
        def arregloResultado = [0.0,0.0,0.0]
        def registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            arregloResultado[0] += 0 //registro.radiacionSolar
            arregloResultado[1] += registro.nivelHumedad
            arregloResultado[2] += registro.tempAmbiente
            
        }
        
        registro = RegistroColectorSolar.find("From RegistroColectorSolar as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        println ("REGISTRO> " + registro);
        if(registro)
        {
            
            arregloResultado[0] += registro.radiacionSolar
            arregloResultado[1] += registro.nivelHumedad
            arregloResultado[2] += registro.tempAmbiente
            
            
        }
        
        
        return arregloResultado 
    }
    
    
}
