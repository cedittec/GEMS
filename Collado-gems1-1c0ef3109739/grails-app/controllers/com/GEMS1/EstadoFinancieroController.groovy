package com.GEMS1

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import groovy.time.TimeCategory
import java.text.SimpleDateFormat
import com.GEMS1.Data.*
import com.GEMS1.ExternalData.*
import java.util.concurrent.TimeUnit
import java.util.Calendar;


@Secured(['ROLE_SysAdmin','ROLE_adminTecnico','ROLE_clienteAdmin', 'ROLE_adminVentas'])
class EstadoFinancieroController {
    def springSecurityService
    def AlarmasService
    def index() { 
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def emisionCogeneracion  = 0
        
        def costosG = 0
        def ahorrosG = 0
        def flujoEfectivo = 0
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
        num = sitios.size()
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
           
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                
                fechaMesAtras = new Date(fechaMesAtras.getYear(), fechaMesAtras.getMonth(), 1)
                
            }
            //De una vez se calcula el arregloEnergiaAprovechadaCog, y se pasa como parámetro
            def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)  
            
            //Variables utilizadas en ahorrosGlobales y costoGlobales...
            def elAuxiliarDC045 = auxiliarDC045(sitios.get(0), fechaHoy, fechaMesAtras)
            def elAuxiliarDC022 = auxiliarDC022(sitios.get(0), fechaHoy, fechaMesAtras)
            def elAuxiliarDC026 = auxiliarDC026(sitios.get(0), fechaHoy, fechaMesAtras)
            def elAuxiliarDC027 = (elAuxiliarDC022[0]+elAuxiliarDC022[1]+elAuxiliarDC022[2] + 
                elAuxiliarDC026[0] + elAuxiliarDC026[1] + elAuxiliarDC026[2])
            def elAuxiliarDC044 = auxiliarDC044(sitios.get(0), fechaHoy, fechaMesAtras)
            def elAuxiliarDT063 = auxiliarDT063(sitios.get(0), fechaHoy, fechaMesAtras)

         
            //costosG += costosGlobales(sitios.get(0),fechaHoy, fechaMesAtras)

            costosG += costosGlobales2(sitios.get(0),fechaHoy, fechaMesAtras, arregloEnergiaAprovechadaCog,
                elAuxiliarDC045, elAuxiliarDC022, elAuxiliarDC026, elAuxiliarDC027, elAuxiliarDC044, elAuxiliarDT063)
          
            //ahorrosG += ahorrosGlobales(sitios.get(0),fechaHoy, fechaMesAtras)

            ahorrosG += ahorrosGlobales2(sitios.get(0),fechaHoy, fechaMesAtras, arregloEnergiaAprovechadaCog,
                elAuxiliarDC045, elAuxiliarDC022, elAuxiliarDC026, elAuxiliarDC027, elAuxiliarDC044, elAuxiliarDT063)
          
            flujoEfectivo += flujoEfectivoAcumulado(sitios.get(0),fechaHoy, fechaMesAtras)
         
            
            if(costosG <0)
                costosG = 0
            if(ahorrosG<0)
                ahorrosG=0
            if(flujoEfectivo<0)
                flujoEfectivo = 0.0
      
            def arregloResultadoGrafica = grafica(sitios.get(0))
 
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            
            
        }
     
        
        [sitios:sitios, numeroSitios:num, rol:rol, resultadoCostos: costosG, resultadoAhorros:ahorrosG, resultadoFlujoEfectivo:flujoEfectivo,
            arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2]
        
    }
    
    @Secured(['ROLE_SysAdmin','ROLE_adminTecnico','ROLE_clienteAdmin'])
    def ROI()
    {
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def costoCombustibleROI = 0
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
   
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
            def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitios.get(0),fuenteMicroturbina.id)
        
            if(fuentes)
            {
                def tuplasCombustible = tuplasSistemaCogeneracion(sitios.get(0), fechaHoy, fechaMesAtras)
                costoCombustibleROI = precioCombustibleCog(sitios.get(0), tipoCombustible(fuentes[0]) ,fechaHoy, fechaMesAtras, tuplasCombustible) 
                
                def arregloResultadoGrafica = graficaROI(sitios.get(0))
            
                arregloGraficaMeses = arregloResultadoGrafica[0]
                arregloGraficaValores = arregloResultadoGrafica[1]
            }   
        } 
            
        [sitios:sitios, numeroSitios:num,costoCombustibleROI:costoCombustibleROI, rol:rol,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores]
    }
    
    
    def actualizaReporteROI(){
        
        def costoCombustibleROI = 0.0
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
                 
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(thisSite,fuenteMicroturbina.id)
        
        if(fuentes){
            def tuplasCombustible = tuplasSistemaCogeneracion(thisSite,fechaEnd, fechaInit)
            costoCombustibleROI = precioCombustibleCog(thisSite, tipoCombustible(fuentes[0]) ,fechaEnd, fechaInit, tuplasCombustible)  
            
            
            def arregloResultadoGrafica = graficaROI(thisSite)
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            
        }

        def resultado = []
        resultado.add((double)costoCombustibleROI)
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
                
        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    
    def actualizaReporteIndex(){
        
        def costosG = 0.0
        def ahorrosG = 0.0
        def flujoEfectivo = 0.0
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
            
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
          
        costosG += (double)costosGlobales(thisSite,fechaEnd, fechaInit)
        ahorrosG += (double)ahorrosGlobales(thisSite,fechaEnd, fechaInit)
        flujoEfectivo += (double)flujoEfectivoAcumulado(thisSite,fechaEnd, fechaInit)
        
        if(costosG <0)
        costosG = 0.0
        if(ahorrosG<0)
        ahorrosG=0.0
        if(flujoEfectivo<0)
        flujoEfectivo = 0.0
        
        def arregloResultadoGrafica = grafica(thisSite)
        
        arregloGraficaMeses = arregloResultadoGrafica[0]
        arregloGraficaValores = arregloResultadoGrafica[1]
        arregloGraficaValores2 = arregloResultadoGrafica[2]       

        def resultado = []
        resultado.add(costosG)
        resultado.add(ahorrosG)
        resultado.add(flujoEfectivo)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    
    def graficaROI(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)      
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [3] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        def meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre","Noviembre","Diciembre"]
        def i = 0;
       
        use (TimeCategory) {
            fechaAtras = fechaHoy - 11.month
            fechaAtras = new Date(fechaAtras.year, fechaAtras.month, 1)

            def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
            def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
            
            while(i<12)
            {
                def fechaTempActual = fechaAtras + 1.month
                if((i+1) == 12)
                {
                        
                    fechaAtras = new Date(fechaHoy.year, fechaHoy.month, 1)
                    fechaTempActual = fechaHoy
                      
                }
                
                if(fuentes)
                {
                    def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaTempActual, fechaAtras)
                    def costoCombustibleROI = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaTempActual, fechaAtras, tuplasCombustible) 
                    valoresXmes1[i] = costoCombustibleROI
                }

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
       
        return resultArreglo
    }
    
    //Metodo que regresa los conjuntos de valores para llenar la grafica de la pantalla Index.
    def grafica(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar 
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [3] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        def meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre","Noviembre","Diciembre"]
        def i = 0;
        def id = params?.id
        if(id){
          
            sitio=Sitio.get(id)
        }
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

                def DC022 = auxiliarDC022(sitio, fechaTempActual, fechaAtras)
                def DC026 = auxiliarDC026(sitio, fechaTempActual, fechaAtras)
                def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
                  
                valoresXmes1[i] = (double)DC027
                valoresXmes2[i] = (double)auxiliarDC048(sitio, fechaTempActual, fechaAtras)

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
        return resultArreglo
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
    
    def auxiliarDC048(sitio, fechaActual, fechaMesAtras)
    {
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)  
       
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //????
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       
        return DC048
    }
    
    //Devulve un arreglo de 3 conteniendo la suma energiaCFE en base, intermedia y punta obtenenida del sistema de cogeneracion
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
    
    //Devuelve una matrz de 3xn el numero de columnas es el numero de tuplas devueltas. 
    //[0] : Contendrá un arreglo de las tuplas en la base
    //[1] : Contendrá un arreglo de las tuplas en la Intermedia
    //[2] : Contendrá un arreglo de las tuplas en la Punta
    def tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
    {        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
        def arregloEnergias = []
        def arregloBases = []
        def arregloIntermedio = []
        def arregloPunta = []
        
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
                                //arregloEnergias[0]+=tupla.energiaSalida
                                arregloBases.add(tupla)
                            }
                            //Es intermedio
                            else if((tupla.dateCreated.hours >= 6 && tupla.dateCreated.hours< 20) || (tupla.dateCreated.hours >= 22 && tupla.dateCreated.hours< 24) )
                            {
                                //arregloEnergias[1]+=tupla.energiaSalida
                                arregloIntermedio.add(tupla)
                            }else //es punta
                            {
                                //arregloEnergias[2]+=tupla.energiaSalida
                                arregloPunta.add(tupla)
                            }
                        }else if(tupla.dateCreated.day == 6)
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 7)
                            {
                                //arregloEnergias[0]+=tupla.energiaSalida
                                arregloBases.add(tupla)
                            }else
                            {
                                //arregloEnergias[1]+=tupla.energiaSalida
                                arregloIntermedio.add(tupla)
                            }
                        }else
                        {
                            if(tupla.dateCreated.hours >= 0 && tupla.dateCreated.hours< 19)
                            {
                                //arregloEnergias[0]+=tupla.energiaSalida
                                arregloBases.add(tupla)
                            }else
                            {
                                //arregloEnergias[1]+=tupla.energiaSalida
                                arregloIntermedio.add(tupla)
                            }
                        }
                     
                    }
                    
                    // energiaCFETotalCogeneracion += tupla.energiaCfe
                }        
            }
           
        }
        arregloEnergias.add arregloBases
        arregloEnergias.add arregloIntermedio
        arregloEnergias.add arregloPunta
    
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
    
    //Devuelve las tuplas de CFE con los precios desde la fechaMesAtras (fecha1) hasta la fechaActual (fecha2)
    def getCFETuplas(fechaActual, fechaMesAtras)
    {       
        def arreglo = TarifaRegionCfe.findAll {
            dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras)
        }
        return arreglo
    }
    
    def getGasLPTuplas(fechaActual, fechaMesAtras)
    {       
        def arreglo = GasLpAmexgas.findAll {
            dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras)
        }
        return arreglo
    }
    def getGasNaturalTuplas(fechaActual, fechaMesAtras)
    {       
        def arreglo = gasNatural.findAll {
            dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras)
        }
        return arreglo
    }
    
    def getDieselTuplas(fechaActual, fechaMesAtras)
    {       
        def arreglo = diesel.findAll {
            dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras)
        }
        return arreglo
    }
    
    //Devuelve arreglo de energia de sist. de cog. * su precio segun la fecha dada
    def auxiliarDC022(sitio, fechaActual, fechaMesAtras)
    {
        def sumaBIP = [0,0,0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
        
        //El sitio se rige por Tarifa OM
        if(sitio.tarifaId == 1)
        {
            
            //Las bajas estan en [0]
            for(tupla in arregloEnergiasCog[0])
            {
                if(tupla == 0)
                continue;
                //Si es baja california
                if(sitio.regionId == 1)
                {
                    sumaBIP[0] +=calculoBajaCaliforniaOM(tupla,tuplasCFE,"Microturbina")                   
                }else if(sitio.regionId == 2)
                {
                    //Es baja SUR
                    sumaBIP[0] +=calculoBajaCaliforniaSurOM(tupla,tuplasCFE,"Microturbina")             
                    
                }else
                {
                    //Es baja tabla normal
                    sumaBIP[0] += calculoTablas(tupla,tuplasCFE,"OM","Baja","Microturbina")             
                }
                
            }
            
        }else if( sitio.tarifaId == 2) //Se rige por tarifa HM
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablas(tupla,tuplasCFE,"HM","Baja","Microturbina")             
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablas(tupla,tuplasCFE,"HM","Intermedia","Microturbina")
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablas(tupla,tuplasCFE,"HM","Punta","Microturbina")         
            }
            
        }else  // SE RIGE POR TARIFA HMC
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablas(tupla,tuplasCFE,"HMC","Baja","Microturbina")                               
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablas(tupla,tuplasCFE,"HMC","Intermedia","Microturbina")      
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablas(tupla,tuplasCFE,"HMC","Punta","Microturbina")         
            }
        }        
        return sumaBIP
    }
    
    
    def auxiliarDC026(sitio, fechaActual, fechaMesAtras)
    {
        
        def sumaBIP = [0,0,0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = energiasBIPFotovoltaico(sitio, fechaActual, fechaMesAtras)
        
        //El sitio se rige por Tarifa OM
        if(sitio.tarifaId == 1)
        {
            //Las bajas estan en [0]
            for(tupla in arregloEnergiasCog[0])
            {
                //Si es baja california
                if(sitio.regionId == 1)
                {
                    sumaBIP[0] +=calculoBajaCaliforniaOM(tupla,tuplasCFE,"Fotovoltaico")                   
                }else if(sitio.regionId == 2)
                {
                    //Es baja SUR
                    sumaBIP[0] +=calculoBajaCaliforniaSurOM(tupla,tuplasCFE,"Fotovoltaico")             
                    
                }else
                {
                    //Es baja tabla normal
                    sumaBIP[0] += calculoTablas(tupla,tuplasCFE,"OM","Baja","Fotovoltaico")             
                }
                
            }
            
        }else if( sitio.tarifaId == 2) //Se rige por tarifa HM
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablas(tupla,tuplasCFE,"HM","Baja","Fotovoltaico")                               
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablas(tupla,tuplasCFE,"HM","Intermedia","Fotovoltaico")             
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablas(tupla,tuplasCFE,"HM","Punta","Fotovoltaico")                            
            }
            
        }else  // SE RIGE POR TARIFA HMC
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablas(tupla,tuplasCFE,"HMC","Baja","Fotovoltaico")                               
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablas(tupla,tuplasCFE,"HMC","Intermedia","Fotovoltaico")             
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablas(tupla,tuplasCFE,"HMC","Punta","Fotovoltaico")                            
            }
        }        
        return sumaBIP
    }
    
    def auxiliarDC044(sitio, fechaActual, fechaMesAtras){
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {
            
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DT061 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Termica")
            def DT020 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Electrica")
            def oper = 0
            if(DT020 != 0 || DT061!= 0)
            oper = (DT061 /(DT020+DT061))
            else
            oper = 0

            
            def combTotalXprecio = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            
            return combTotalXprecio * oper
        }
        else
        return 0
        
    }
    
    def auxiliarDC045(sitio, fechaActual, fechaMesAtras){
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {
            
            def tuplasCombustible = tuplasSistemaTermico(sitio, fechaActual, fechaMesAtras)

            def combTotalXprecio = precioCombustibleTermico(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            
            
            return combTotalXprecio
        }
        else
        return 0
        
    }
    
    
    def auxiliarDT063(sitio, fechaActual, fechaMesAtras){
        def DT053 = 4.186 //??
        def sumaFinal = 0
        def registros = tuplasSistemaTermico(sitio, fechaActual, fechaMesAtras)
        for ( tupla in registros)
        {
            sumaFinal += ( DT053*registros.flujoAgua*(registros.tempAguaEntradaModulo - registros.tempAguaSalidaModulo))
        }
        
        return sumaFinal
        
    }
    
    //Devuleve la energia Total del sistema termico de un sitio dado
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
    
    //Devuelve los regsitros dentro de SistemaTermico_Hora desde la fechaMesAtras hasta fechaActual
    def tuplasSistemaTermico(sitio, fechaActual, fechaMesAtras)
    {
        def tuplasResult = []
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
                    tuplasResult.add tupla
                }        
            }
           
        }
        return tuplasResult
    }
    
    //Devuleve el tipo de un combustible dado el registro FuenteSitio
    def tipoCombustible(fuenteSitio)
    {
        def combustible
        
        combustible = CombustibleFuente.get(fuenteSitio.combustibleFuenteId)              
     
        if(combustible)
        return combustible.nombre
        else 
        return ""
    }
    
    //Devuelve el resultado de una operacion de combustible usado * su costo en dicha fecha * energia
    def precioCombustibleTermico(sitio, tipoCombustible,fechaActual, fechaMesAtras, registrosTermico){
        def precioResult = 0
        if( tipoCombustible == "Gas LP")
        {
            if(sitio.precioGasLp == 0)
            {
                def registrosPaginaWeb = getGasLPTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosTermico){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.flujoCombustible  * tupla.energia
                    else
                    precioResult += 0
                    
                } 
            }
            else
            {
                def precio = sitio.precioGasLp
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible * tupla.energia 
                } 
            }
        }else if(tipoCombustible=="Gas Natural")
        {
            if(sitio.precioGasNatural == 0)
            {
               
                def registrosPaginaWeb = getGasNaturalTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosTermico){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.flujoCombustible  * tupla.energia
                    else
                    precioResult += 0
                    
                } 
            }else
            {
                def precio = sitio.precioGasNatural
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible * tupla.energia
                } 
            }
        }else
        {
            if(sitio.precioDiesel == 0)
            {
               
               
                def registrosPaginaWeb = getDieselTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosTermico){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.flujoCombustible  * tupla.energia
                    else
                    precioResult += 0
                    
                } 
            }else
            {
                def precio = sitio.precioDiesel
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible * tupla.energia
                } 
            }
            
        }
        
        return precioResult
        
    }
    
    //Metodo auxiliar para realizar la operacion de DC012*DC059 
    def operacionAuxiliarDC012xDC059(sitio, tipoCombustible,fechaActual, fechaMesAtras, registrosCogeneracion){
        def precioResult = 0
        if( tipoCombustible == "Gas LP")
        {
            if(sitio.precioGasLp == 0)
            {
                def registrosPaginaWeb = getGasLPTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    precioResult += precioMes.precioLt * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow
                    
                } 
            }
            else
            {
                def precio = sitio.precioGasLp
                for(tupla in registrosCogeneracion){
                
                    precioResult += precio * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow
                } 
            }
        }else if(tipoCombustible=="Gas Natural")
        {
            if(sitio.precioGasNatural == 0)
            {
               
                def registrosPaginaWeb = getGasNaturalTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    precioResult += precioMes.precio * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow
                    
                } 
            }else
            {
                def precio = sitio.precioGasNatural
                for(tupla in registrosCogeneracion){
                
                    precioResult += precio * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow
                } 
            }
        }else
        {
            if(sitio.precioDiesel == 0)
            {
               
               
                def registrosPaginaWeb = getDieselTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    precioResult += precioMes.precio * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow
                    
                } 
            }else
            {
                def precio = sitio.precioDiesel
                for(tupla in registrosCogeneracion){               
                    precioResult += (precio * tupla.fuelEnergyFlow*tupla.fuelEnergyFlow)
                } 
            }
            
        }
        
        return precioResult
        
    }
    
    //Devulve el resultado de una operacion de uso de combustible * precio del comb usado en esa fecha por el sist. de cog
    def precioCombustibleCog(sitio, tipoCombustible,fechaActual, fechaMesAtras, registrosCogeneracion){
        def precioResult = 0
        if( tipoCombustible == "Gas LP")
        {
            if(sitio.precioGasLp == 0)
            {
               
                def registrosPaginaWeb = getGasLPTuplas(fechaActual, fechaMesAtras)
               
             
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.fuelEnergyFlow
                    else
                    precioResult += 0
                    
                } 
            }
            else
            {
                def precio = sitio.precioGasLp
                for(tupla in registrosCogeneracion){
                
                    precioResult += precio * tupla.fuelEnergyFlow                   
                } 
               
            }
        }else if(tipoCombustible=="Gas Natural")
        {
            if(sitio.precioGasNatural == 0)
            {
               
                def registrosPaginaWeb = getGasNaturalTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.fuelEnergyFlow
                    else
                    precioResult += 0
                    
                } 
            }else
            {
                def precio = sitio.precioGasNatural
                for(tupla in registrosCogeneracion){
                
                    precioResult += precio * tupla.fuelEnergyFlow
                } 
            }
        }else
        {
            if(sitio.precioDiesel == 0)
            {
               
               
                def registrosPaginaWeb = getDieselTuplas(fechaActual, fechaMesAtras)
                
                
                for(tupla in registrosCogeneracion){
                
                    def precioMes = registrosPaginaWeb.find{ 
                        (it.dateCreated.month == tupla.dateCreated.month ) && (it.dateCreated.year ==  tupla.dateCreated.year)                 
                    }
                    
                    if(precioMes)
                    precioResult += precioMes.precioLt * tupla.fuelEnergyFlow
                    else
                    precioResult += 0
                    
                } 
            }else
            {
                def precio = sitio.precioDiesel
                for(tupla in registrosCogeneracion){               
                    precioResult += (precio * tupla.fuelEnergyFlow)
                } 
            }
            
        }
        
        return precioResult
        
    }
    
    //Devuelve la suma de energia dada tipoEnergia (Termica | Electrica) y desde la fechaMesAtras hasta fechaActual
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
        
    //Devuelve las tuplas del sistema de cog. desde la fechaMesAtras hasta la fechaActual
    def tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")                  
            
        def combustibleConsumo = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0
        def tuplasResult = []
        
        if(fuentes){
                
            for(fuente in fuentes){
                    
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }
                    
                horasOperacion = tuplas.size()
                    
                for(tupla in tuplas){
                    tuplasResult.add tupla
                }
                      
            }
                
        }
            
        return tuplasResult
            
    }
      
    //Calculo utilizado para el primer cuadro de index 
    def costosGlobales(sitio, fechaActual, fechaMesAtras)
    {
        def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
       
        def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
       
        
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)  
       
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
    
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //??      
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       
        //DC061 = DC047 + DC048 = DT063* DC045 + (DC046 + DC047+DC049.2)
        def DC061 = (DC045 * DT063) + DC048 
        
        return (DC027 + DC048 + DC061)
        
    }

    //Versión qué ya contiene calculada energiaAprovechadaCogeneracion, y el elAuxiliarDC045
    def costosGlobales2(sitio, fechaActual, fechaMesAtras, laEnergiaAprovechadaCogeneracion, 
        elAuxiliarDC045, elAuxiliarDC022, elAuxiliarDC026, elAuxiliarDC027, elAuxiliarDC044, elAuxiliarDT063)
    {
        //def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC022 = elAuxiliarDC022

        //def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
        def DC026 = elAuxiliarDC026
       
        //def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        def DC027 = elAuxiliarDC027
       
        
        def arregloEnergiaAprovechadaCog =  laEnergiaAprovechadaCogeneracion 
       
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
    
        //def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DC044 = elAuxiliarDC044
        
        //def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DT063 = elAuxiliarDT063

        //def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC045 = elAuxiliarDC045

        def DC491 = 0 //??      
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       
        //DC061 = DC047 + DC048 = DT063* DC045 + (DC046 + DC047+DC049.2)
        def DC061 = (DC045 * DT063) + DC048 
        
        return (DC027 + DC048 + DC061)
        
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el costo q genero el consumo de energia de CFE de dicha tupla (registro)
    //esto si el sitio se encuentra bajo tarifa OM y region Baja Calif.
    def calculoBajaCaliforniaOMCFE(tupla, tuplasCFE, tipoSistema)
    {
        if(!(tupla instanceof List))
        {
            return 0
        }
        def mesesOMBajaCalifornia =["Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.(1)","Oct.(2)","Nov.","Dic."];
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        
        def month = tupla.dateCreated.month
        //Es octubre : Se divide en dos, hay q saber que dia del mes es
        if(month == 9)
        {
            if(tupla.dateCreated.day >= 26)
            {
                month++;
            }
        }
        if(month > 10)
        {
            month++;
        }
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == mesesOMBajaCalifornia[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoSistema=="Microturbina")
            return (tupla.energiaCfe  * valCFEMes.energia)       
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el costo q genero el consumo de energia de CFE de dicha tupla (registro)
    //esto si el sitio se encuentra bajo tarifa OM y region BJSur
    def calculoBajaCaliforniaSurOMCFE(tupla, tuplasCFE, tipoSistema)
    {
        def mesesOMBajaCaliforniaSUR =["Ene.","Feb.","Mar.","Abr.(3)","Abr.(4)","May.","Jun.","Jul","Ago","Sep.","Oct.(1)","Oct.(2)","Nov.","Dic."];
       
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        
        def month = tupla.dateCreated.month
        //Es octubre : Se divide en dos, hay q saber que dia del mes es
        
        if(month == 3)
        {
            if(tupla.dateCreated.day >= 5)
            {
                month++;
            }
        }
        if( mont > 3)
        {
            month++;
        }
         
        
        if(month == 9)
        {
            if(tupla.dateCreated.day >= 26)
            {
                month++;
            }
        }
        if(month > 10)
        {
            month++;
        }
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == mesesOMBajaCaliforniaSUR[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoSistema=="Microturbina")
            return (tupla.energiaCfe  * valCFEMes.energia)
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el costo q genero el consumo de energia de CFE de dicha tupla (registro)   
    
    def calculoTablasCFE(tupla, tuplasCFE, tipoTarifa, tipoBIP, tipoSistema)
    {
        def meses =["Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.","Nov.","Dic."];
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        def month = tupla.dateCreated.month
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == meses[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoTarifa=="OM")
            {
                if(tipoSistema=="Microturbina")
                return (tupla.energiaCfe  * valCFEMes.energia)
                
            }else if(tipoTarifa=="HM")
            {
                if(tipoBIP == "Base"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energia)
                 
                }
                else if (tipoBIP == "Intermedia"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energia)
                  
                }
                else{
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energia)
             
                }
            }
            else{
                if(tipoBIP == "Base"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energia)
               
                }
                else if (tipoBIP == "Intermedia")
                {
                    if(tipoSistema=="Microturbina")  
                    return (tupla.energiaCfe  * valCFEMes.energia)
                 
                }
                else{
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe * valCFEMes.energia)
              
                }
            }
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el valor de energia generada * costo de energia de la CFE
    //esto si el sitio se encuentra bajo tarifa OM y Baja california
    def calculoBajaCaliforniaOM(tupla, tuplasCFE, tipoSistema)
    {
        if (!(tupla instanceof List))
        {
            return 0
        }
        def mesesOMBajaCalifornia =["Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.(1)","Oct.(2)","Nov.","Dic."];
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        
        def month = tupla.dateCreated.month
        //Es octubre : Se divide en dos, hay q saber que dia del mes es
        if(month == 9)
        {
            if(tupla.dateCreated.day >= 26)
            {
                month++;
            }
        }
        if(month > 10)
        {
            month++;
        }
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == mesesOMBajaCalifornia[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoSistema=="Microturbina")
            return (tupla.energiaSalida  * valCFEMes.energia)
            if(tipoSistema=="Fotovoltaico")
            return (tupla.energiaCa  * valCFEMes.energia)
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el valor de energia generada * costo de energia de la CFE
    //esto si el sitio se encuentra bajo tarifa OM y Baja california Sur
    def calculoBajaCaliforniaSurOM(tupla, tuplasCFE, tipoSistema)
    {
        def mesesOMBajaCaliforniaSUR =["Ene.","Feb.","Mar.","Abr.(3)","Abr.(4)","May.","Jun.","Jul","Ago","Sep.","Oct.(1)","Oct.(2)","Nov.","Dic."];
       
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        
        def month = tupla.dateCreated.month
        //Es octubre : Se divide en dos, hay q saber que dia del mes es
        
        if(month == 3)
        {
            if(tupla.dateCreated.day >= 5)
            {
                month++;
            }
        }
        if( mont > 3)
        {
            month++;
        }
         
        
        if(month == 9)
        {
            if(tupla.dateCreated.day >= 26)
            {
                month++;
            }
        }
        if(month > 10)
        {
            month++;
        }
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == mesesOMBajaCaliforniaSUR[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoSistema=="Microturbina")
            return (tupla.energiaSalida  * valCFEMes.energia)
            if(tipoSistema=="Fotovoltaico")
            return (tupla.energiaCa  * valCFEMes.energia)
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el valor de energia generada * costo de energia de la CFE
    def calculoTablas(tupla, tuplasCFE, tipoTarifa, tipoBIP, tipoSistema)
    {
        if(!tupla){           
            return 0
        }
        
        def meses =["Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.","Nov.","Dic."];
        Calendar cal = Calendar.getInstance();
        cal.setTime(tupla.dateCreated);                
        def yearFechaTupla = cal.getInstance().get(cal.YEAR);
        def month = tupla.dateCreated.month
        
        def valCFEMes = tuplasCFE.find{ 
            (it.mes == meses[month]) && (it.ano ==  yearFechaTupla)
        }
        if(!valCFEMes)
        {
            return 0
        }
        if(valCFEMes)
        {
            if(tipoTarifa=="OM")
            {
                if(tipoSistema=="Microturbina")
                return (tupla.energiaSalida  * valCFEMes.energia)
                if(tipoSistema=="Fotovoltaico")
                return (tupla.energiaCa  * valCFEMes.energia)
                
            }else if(tipoTarifa=="HM")
            {
                if(tipoBIP == "Base"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
                else if (tipoBIP == "Intermedia"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
                else{
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
            }
            else{
                if(tipoBIP == "Base"){
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
                else if (tipoBIP == "Intermedia")
                {
                    if(tipoSistema=="Microturbina")  
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
                else{
                    if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energia)
                    if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energia)
                }
            }
        }
        return 0
    }
    
    //Devuelve un arreglo de que contiene las energias aproechas del sistema de cogeneracion en los intercambiadores
    //Usado para obtener las variables dentro de los ID's : DT055,DT056,DT057,DT0685,DT0686
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
        
        arregloResult.add(DT055)
        arregloResult.add(DT056)
        arregloResult.add(DT057)
        arregloResult.add(DT0685)
        arregloResult.add(DT0686)
        return arregloResult
    }
    
    
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
    
    def ausxiliarDC018(sitio, fechaActual, fechaMesAtras)
    {
        def sumaBIP = [0,0,0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)
        
        //El sitio se rige por Tarifa OM
        if(sitio.tarifaId == 1)
        {
            
            //Las bajas estan en [0]
            for(tupla in arregloEnergiasCog[0])
            {
             
                if(tupla == 0)
                continue;
                //Si es baja california
                if(sitio.regionId == 1)
                {
                    sumaBIP[0] +=calculoBajaCaliforniaOMCFE(tupla,tuplasCFE,"Microturbina")                   
                }else if(sitio.regionId == 2)
                {
                    //Es baja SUR
                    sumaBIP[0] +=calculoBajaCaliforniaSurOMCFE(tupla,tuplasCFE,"Microturbina")             
                    
                }else
                {
                    //Es baja tabla normal
                    sumaBIP[0] += calculoTablasCFE(tupla,tuplasCFE,"OM","Baja","Microturbina")             
                }
                
            }
            
        }else if( sitio.tarifaId == 2) //Se rige por tarifa HM
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablasCFE(tupla,tuplasCFE,"HM","Baja","Microturbina")             
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablasCFE(tupla,tuplasCFE,"HM","Intermedia","Microturbina")
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablasCFE(tupla,tuplasCFE,"HM","Punta","Microturbina")         
            }
            
        }else  // SE RIGE POR TARIFA HMC
        {
            for(tupla in arregloEnergiasCog[0])
            {
                sumaBIP[0] +=calculoTablasCFE(tupla,tuplasCFE,"HMC","Baja","Microturbina")                               
            }
            for(tupla in arregloEnergiasCog[1])
            {
                sumaBIP[1] += calculoTablasCFE(tupla,tuplasCFE,"HMC","Intermedia","Microturbina")      
            }
            for(tupla in arregloEnergiasCog[2])
            {
                sumaBIP[2] += calculoTablasCFE(tupla,tuplasCFE,"HMC","Punta","Microturbina")         
            }
        }        
        return (sumaBIP[0]+sumaBIP[1]+sumaBIP[2])
    }
    
    //Ahorros globales para el index es A008
    def ahorrosGlobales(sitio, fechaActual, fechaMesAtras)
    {
        def DC018 = ausxiliarDC018(sitio, fechaActual, fechaMesAtras)
        
        def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
       
        def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)  
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //??
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       
        return (DC018+DC027+DC048)
      
    }
    
    def ahorrosGlobales2(sitio, fechaActual,fechaMesAtras,laEnergiaAprovechadaCogeneracion, elAuxiliarDC045, elAuxiliarDC022, elAuxiliarDC026, elAuxiliarDC027, elAuxiliarDC044, elAuxiliarDT063){
        //Versión qué ya tiene calculada la energiaAprovechadaCogeneracion
        def DC018 = ausxiliarDC018(sitio, fechaActual, fechaMesAtras)
        
        //def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC022 = elAuxiliarDC022

        //def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
        def DC026 = elAuxiliarDC026
       
        //def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        def DC027 = elAuxiliarDC027
        
        def arregloEnergiaAprovechadaCog =  laEnergiaAprovechadaCogeneracion
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        //def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DC044 = elAuxiliarDC044

        //def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)

        def DT063 = elAuxiliarDT063
        

        //def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC045 = elAuxiliarDC045
        
        def DC491 = 0 //??
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       
        return (DC018+DC027+DC048)
    }
    //*************Comienzan metodos para Flujo Efectivo acumulado ************************************************
    
    def auxiliarDR041(sitio, fechaActual, fechaMesAtras)
    {      
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")    
       
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
     
        fuentes = FuenteSitio.findAllWhere(sitio:sitio,fuenteDeEnergiaId:fuenteMicroturbina.id )
       
        if(fuentes)
        {        
            def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
            def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
            def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
            def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
            def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
            def DC491 = 0 //??
            def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)            
        
            def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
            def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
            def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
            
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DR030 = sitio.inversion
            
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            def DR001 = DC027 + DC048
          
            def DR031 = DC059 - DR001
          
            return (DR030 - (DR031))
        }else{
            return 0.0
        }
    }
    
    def auxiliarDR042(sitio, fechaActual, fechaMesAtras){
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {        
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
            //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
            def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
            def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
            def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
            def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
            def DC491 = 0 //??
            def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
            
            //DC027
            def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
            def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
            def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
            
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            def DR001 = 1 //??
            def DR031 = DC059 - DR001

            //Ya sea DT103, DT104, DT105
            def consumoCombustible = combustibleSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def operacion = 0
            
            //DR002 = operacion
            if(tipoCombustible(fuentes[0]) == "Gas LP")
            {
                //DC012 precio unitario 
                //DC012 * DC059 * 1.1
               
                operacion = (operacionAuxiliarDC012xDC059(sitio,tipoCombustible(fuentes[0]), fechaActual, fechaMesAtras,tuplasCombustible)) * DC059 * 1.1
                
            }else{
                //DC012 * DC059 * 1.12
                operacion = (operacionAuxiliarDC012xDC059(sitio,tipoCombustible(fuentes[0]), fechaActual, fechaMesAtras,tuplasCombustible)) * DC059 * 1.12
            }
            //** fin de DR002
            
            //DR012 = DC027*1.11
            //DR021 = DC048 * 1.2
            //DR032 = (operacion -( (DC027*1.1)+(DC048*1.2) )
            return ((DC059 - DR001) -(operacion -( (DC027*1.1)+(DC048*1.2) )))
        
        
        }else{
            return 0
        }
        
    }
    
    def auxiliarDR043(sitio, fechaActual, fechaMesAtras){
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //??
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
        
        //DC027
        def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
        def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {       
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)                        
            def DR002 = auxiliarDR002(sitio,DC059,fuentes, fechaActual, fechaMesAtras)
            def DR032 = (DR002 -( (DC027*1.1)+(DC048*1.2) ))
          
           
            def DR003 = auxiliarDR003(DR002,DC059,fuentes)
            
            //DR012 = DC027*1.11
            def DR013 = (DC027*1.11) * 1.1
            //DR021 = DC048 * 1.2
            def DR022 = DC048 * 1.2
            
            //def DR033 = (DR003 - (DR013 + DR022))
            def DR043 = DR032 - (DR003 - (DR013 + DR022))
           
            
            return DR043 
                
                
        }else{ 
            return 0
        }
            
    }
        
    def auxiliarDR044(sitio, fechaActual, fechaMesAtras){
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //??
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
        
        //DC027
        def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
        def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {       
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)                        
            
            def DR002 = auxiliarDR002(sitio,DC059,fuentes, fechaActual, fechaMesAtras)  
            def DR032 = (DR002 -( (DC027*1.1)+(DC048*1.2) ))
            def DR003 = auxiliarDR003(DR002,DC059,fuentes)            
            //DR012 = DC027*1.11
            def DR013 = (DC027*1.11) * 1.1
            //DR021 = DC048 * 1.2
            def DR022 = DC048 * 1.2
            
            def DR033= DR032 - (DR003 - (DR013 + DR022))
            def DR004 = auxiliarDR004(DR003,DC059,fuentes)
            def DR044 = (DR033 - (DR004 - ((DR013*1.1)+(DR022*1.1)))) 
            
            return DR044
                
                
        }else{ 
            return 0
        }
            
    }
       
    def auxiliarDR045(sitio, fechaActual, fechaMesAtras){
        def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
        def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
        def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
        def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
        def DC491 = 0 //??
        def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
        
        //DC027
        def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
        def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)
        def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {       
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)                        
           
            def DR002 = auxiliarDR002(sitio,DC059,fuentes, fechaActual, fechaMesAtras)  
            def DR032 = (DR002 -( (DC027*1.1)+(DC048*1.2) ))
            def DR003 = auxiliarDR003(DR002,DC059,fuentes)            
            //DR012 = DC027*1.11
            def DR013 = (DC027*1.11) * 1.1
            //DR021 = DC048 * 1.2
            def DR022 = DC048 * 1.2
            
            def DR033= DR032 - (DR003 - (DR013 + DR022))
            def DR004 = auxiliarDR004(DR003,DC059,fuentes)
            def DR005 = auxiliarDR004(DR004,DC059,fuentes)
            
            def DR014 = DR013 * 1.1
            def DR023 = DR022 * 1.1
            
            def DR045 = ( DR004 - ((DR013 * 1.1) + (DR022*1.1))) - ((DR005 - ((DR014*1.1) - (DR023*1.1))))
            
            return DR045
            
        }else{ 
            return 0
        }
            
    }
        
    def auxiliarDR002(sitio,DC059,fuentes,fechaActual,fechaMesAtras)
    {
        def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
        def DR002 = 0
        //DR002 
        if(tipoCombustible(fuentes[0]) == "Gas LP")
        {
            //DC012 precio unitario 
            //DC012 * DC059 * 1.1
            DR002 = (operacionAuxiliarDC012xDC059(sitio,tipoCombustible(fuentes[0]), fechaActual, fechaMesAtras,tuplasCombustible))  * DC059 * 1.1
                
        }else{
            //DC012 * DC059 * 1.12
            DR002 = (operacionAuxiliarDC012xDC059(sitio,tipoCombustible(fuentes[0]), fechaActual, fechaMesAtras,tuplasCombustible))  * DC059 * 1.12
        }
        return DR002
        //** fin de DR002
    }
        
    def auxiliarDR003(DR002,DC059,fuentes){
        def DR003 = 0
        //DR002 
        if(tipoCombustible(fuentes[0]) == "Gas LP")
        {
            DR003 = DR002 * DC059 * 1.1
                
        }else{
            DR003 = DR002 * DC059 * 1.12
        }
        return DR003
    }
    
    def auxiliarDR004(DR003,DC059,fuentes){
        def DR004 = 0
        //DR002 
        if(tipoCombustible(fuentes[0]) == "Gas LP")
        {
            DR004 = DR003 * DC059 * 1.1
                
        }else{
            DR004 = DR003 * DC059 * 1.12
        }
        return DR004
    }
    
    def auxiliarDR005(DR004,DC059,fuentes){
        def DR005 = 0
        //DR002 
        if(tipoCombustible(fuentes[0]) == "Gas LP")
        {
            DR005 = DR004 * DC059 * 1.1
                
        }else{
            DR005 = DR004 * DC059 * 1.12
        }
        return DR005
    }
    
    
    //Devuelve la suma de todo el combustible que utiliza el sistema de cogeneracion en el sitio dado, desde
    //la fecha fechaMesAtras hasta fechaActual
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
    
    
    //Dependiendo de la antiguedad del sitio se debe de utiliar DR041, DR042..
    def flujoEfectivoAcumulado(sitio,fechaHoy, fechaMesAtras)
    {
        return auxiliarDR041(sitio,fechaHoy, fechaMesAtras);
    }    
    
}

