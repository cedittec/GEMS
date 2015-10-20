package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import groovy.time.TimeCategory
import com.GEMS1.Data.*
import java.text.SimpleDateFormat

@Secured(['ROLE_SysAdmin','ROLE_adminTecnico','ROLE_clienteAdmin','ROLE_clienteTecnico', 'ROLE_adminVentas'])

class EmisionesController {
    def springSecurityService

    def index() { 
        
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def emisionCogeneracion  = 0.0
        def emisionFotovoltaico = 0.0
        def emisionColectorSolar = 0.0
        
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
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                fechaMesAtras = new Date(fechaMesAtras.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            emisionCogeneracion = reduccionEmisionesCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
            emisionFotovoltaico = reduccionEmisionesFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
            emisionColectorSolar = reduccionEmisionesColectorSolar(sitios.get(0),fechaHoy, fechaMesAtras)
           
            //GRAFICA
            def arregloResultadoGrafica = graficaIndex(sitios.get(0))
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
             
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
            
        }
        
        [sitios:sitios, numeroSitios:num,emisionSistemaColectorSolar:emisionColectorSolar,emisionSistemaCogeneracion:emisionCogeneracion,emisionSistemaFotovoltaico:emisionFotovoltaico, rol:rol
            ,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2,arregloGraficaValores3:arregloGraficaValores3]
            
    }
    
    //Meotodo que reedirecciona al gsp de Emisiones de cogeneracion "cogeneracion.gsp"
    def cogeneracion(){
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def matrizCombustibles
        def data1
        def combustibleNombre = ""
        
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
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                //fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            if(sitios){
                def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
                def fuentes = FuenteSitio.findAllWhere(sitio:sitios.get(0), estatus:"activo")            
                if(fuentes){
                    combustibleNombre=  tipoCombustible(fuentes.get(0))
                }
                matrizCombustibles =  reduccionEmisionesTotalesCombustible(sitios.get(0),fechaHoy, fechaMesAtras)
                //println matrizCombustibles
                data1 = (double)reduccionEmisionesGeneracionElectrica(sitios.get(0),fechaHoy, fechaMesAtras)
                    
                //GRAFICA
                def arregloResultadoGrafica = graficaCogeneracion(sitios.get(0))
                arregloGraficaMeses = arregloResultadoGrafica[0]
                arregloGraficaValores = arregloResultadoGrafica[1]
                
            }
        }
        
        [sitios:sitios,reduccionEmisionesTotales:matrizCombustibles, reduccionEmisionesGeneracionElectrica:data1 , numeroSitios:num, rol:rol,
            combustibleNombre:combustibleNombre,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores]
        
    }
    
    
    //Meotodo que reedirecciona al gsp de Emisiones de fotovoltaico "fotovoltaico.gsp"
    def fotovoltaico()
    {
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def emisionCFE = 0 //para reporte de emsiones CFE
        def emisionPanel = 0//Para emisiones Panel
        def emisionFotovoltaico = 0
        
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
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                //fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            emisionCFE = emisionesCFEFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
            emisionPanel = emisionesPanelFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
            emisionFotovoltaico = reduccionEmisionesFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
            
           
            //GRAFICA
            def arregloResultadoGrafica = graficaFotovoltaico(sitios.get(0))
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            
        }
        
        [sitios:sitios,emisionCFE:emisionCFE,emisionFotovoltaico:emisionFotovoltaico, emisionPanel:emisionPanel , numeroSitios:num, rol:rol
            ,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores]
        
    }
    
    
    //Metodo que redirecciona al gsp de Emisiones de colector solar "colector.gsp"
    def colector()
    {
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        def emisionCFE = 0 //para reporte de emsiones CFE
        def emisionColector = 0//Para emisiones colector
        def reduccionEmisionesAmbiente = 0
        
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
        def num = sitios.size()
        
        if(num>0){
            def fechaHoy = new Date();             
            def fechaMesAtras
            
            use (TimeCategory) {
                fechaMesAtras = fechaHoy - 1.month
                //fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            emisionCFE = emisionesCFEColector(sitios.get(0),fechaHoy, fechaMesAtras) //primer cuadro de pantalla
            emisionColector = reduccionMedioAmbienteColector(sitios.get(0),fechaHoy, fechaMesAtras) //Segundo cuadro
            reduccionEmisionesAmbiente = emisionCFE - emisionColector //Tercer cuadro
            
            //GRAFICA
            def arregloResultadoGrafica = graficaColector(sitios.get(0))
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
            
        }
        
        [sitios:sitios,emisionCFE:emisionCFE,reduccionEmisionesAmbiente:reduccionEmisionesAmbiente, emisionColector:emisionColector , numeroSitios:num, rol:rol
            ,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores]
        
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
                    
                valoresXmes1[i] = (double) reduccionEmisionesCogeneracion(sitio, fechaTempActual, fechaAtras)
                valoresXmes2[i] = (double) reduccionEmisionesFotovoltaico(sitio, fechaTempActual, fechaAtras)
                valoresXmes3[i] = (double) reduccionEmisionesColectorSolar(sitio, fechaTempActual, fechaAtras)
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
    
    def graficaCogeneracion(sitio){
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)  
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [2] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        def combustibleNombre =""
        def i = 0;
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def fuentes = FuenteSitio.findAllWhere(sitio:sitio, estatus:"activo")            
        if(fuentes){
            combustibleNombre=  tipoCombustible(fuentes.get(0))
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
                def matrix = reduccionEmisionesTotalesCombustible(sitio,fechaTempActual, fechaAtras)                
                if(combustibleNombre == "Gas LP")
                valoresXmes1[i] = matrix[0][0]
                if(combustibleNombre == "Gas Natural")
                valoresXmes1[i] = matrix[0][0]
                if(combustibleNombre == "Diesel")
                valoresXmes1[i] = matrix[0][0]
                
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
    
    def graficaColector(sitio){
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)  
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [2] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        def combustibleNombre =""
        def i = 0;
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def fuentes = FuenteSitio.findAllWhere(sitio:sitio, estatus:"activo")            
        if(fuentes){
            combustibleNombre=  tipoCombustible(fuentes.get(0))
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
                
                valoresXmes1[i] =  reduccionMedioAmbienteColector(sitio,fechaTempActual, fechaAtras)             
                
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
    
    def graficaFotovoltaico(sitio){
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)  
        def mesesOrden =[12] //Las fechas a utiilizar en la grafica con formato YYYY-MM-DD
        def resultArreglo = [2] //arreglo que se devuelve, contiene los arreglos(conjuntos) necesesarios para llenar la tabla, este caso:
        //fechas mensuales, valores 1 por mes, valores 2 por mes.
        def combustibleNombre =""
        def i = 0;
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def fuentes = FuenteSitio.findAllWhere(sitio:sitio, estatus:"activo")            
        if(fuentes){
            combustibleNombre=  tipoCombustible(fuentes.get(0))
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
                
                valoresXmes1[i] = emisionesPanelFotovoltaico(sitio,fechaTempActual, fechaAtras)    
                
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
    
    //metodo que realiza request de las fechas para el reporte
    def actualizaReporte(){
            
        def emisionCogeneracion  = 0
        def emisionFotovoltaico = 0
        def emisionColectorSolar = 0
        
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
            
        emisionCogeneracion = (double)reduccionEmisionesCogeneracion(thisSite,fechaEnd, fechaInit)
        emisionFotovoltaico = (double)reduccionEmisionesFotovoltaico(thisSite,fechaEnd, fechaInit)
        emisionColectorSolar = (double)reduccionEmisionesColectorSolar(thisSite,fechaEnd, fechaInit)
        
        def arregloResultadoGrafica = graficaIndex(thisSite)
            
        arregloGraficaMeses = arregloResultadoGrafica[0]
             
        arregloGraficaValores = arregloResultadoGrafica[1]
        arregloGraficaValores2 = arregloResultadoGrafica[2]
        arregloGraficaValores3 = arregloResultadoGrafica[3]
        

        def resultado = []
        resultado.add(emisionCogeneracion)
        resultado.add(emisionFotovoltaico)
        resultado.add(emisionColectorSolar)
        
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
    
    def actualizaEmisionesCogeneracion(){        
        def combustibleNombre = ""        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def matrizCombustibles
        def data1
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        
        //obtener id del sitio especificado
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")     
        def fuentes = FuenteSitio.findAllWhere(sitio:thisSite, estatus:"activo")            
        if(fuentes){
            combustibleNombre=  tipoCombustible(fuentes.get(0))
                
            matrizCombustibles =  reduccionEmisionesTotalesCombustible(thisSite,fechaEnd, fechaInit)            
            data1 = (double)reduccionEmisionesGeneracionElectrica(thisSite,fechaEnd, fechaInit)
                    
            //GRAFICA
            def arregloResultadoGrafica = graficaCogeneracion(thisSite)
            arregloGraficaMeses = arregloResultadoGrafica[0]
            arregloGraficaValores = arregloResultadoGrafica[1]
        }
        
        
        def resultado = []
        resultado.add(matrizCombustibles)
        resultado.add(data1)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def actualizaFotovoltaico(){
        def emisionCFE
        def emisionPanel
        def emisionFotovoltaico
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        //obtener id del sitio especificado
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
       
        emisionCFE = (double)emisionesCFEFotovoltaico(thisSite,fechaEnd, fechaInit)
        emisionPanel = (double)emisionesPanelFotovoltaico(thisSite,fechaEnd, fechaInit)
        emisionFotovoltaico = (double)reduccionEmisionesFotovoltaico(thisSite,fechaEnd, fechaInit)
        
      
        //GRAFICA
        def arregloResultadoGrafica = graficaFotovoltaico(thisSite)
        arregloGraficaMeses = arregloResultadoGrafica[0]
        arregloGraficaValores = arregloResultadoGrafica[1]
        
        def resultado = []
        resultado.add(emisionCFE)
        resultado.add(emisionPanel)
        resultado.add(emisionFotovoltaico)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def actualizaColectorSolar(){
        
        def emisionCFE
        def emisionColector
        def reduccionEmisionesAmbiente
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        //obtener id del sitio especificado
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
        
        emisionCFE = (double)emisionesCFEColector(thisSite,fechaEnd, fechaInit) //primer cuadro de pantalla
        emisionColector = (double)reduccionMedioAmbienteColector(thisSite,fechaEnd, fechaInit) //Segundo cuadro
        reduccionEmisionesAmbiente = (double)(emisionCFE - emisionColector)  //Tercer cuadro
        
        //GRAFICA
        def arregloResultadoGrafica = graficaColector(thisSite)
        arregloGraficaMeses = arregloResultadoGrafica[0]
        arregloGraficaValores = arregloResultadoGrafica[1]
        
        def resultado = []
        resultado.add(emisionCFE)
        resultado.add(emisionColector)
        resultado.add(reduccionEmisionesAmbiente)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)

        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
    }
    
    def getHistoricData(){
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        def tuplas = RegistroSistemaCogeneracionHora.findAll {
            dateCreated <= (fechaEnd) && (dateCreated >=fechaInit) && (fuente)
        }
        
        
    }
    
  
    
    
    //sber el tipo de combustible de un sistema 
    def tipoCombustible(fuenteSitio)
    {
        def combustible
        
        combustible = CombustibleFuente.get(fuenteSitio.combustibleFuenteId)              
     
        if(combustible)
        return combustible.nombre
        else 
        return ""
    }
    
    //primer pantalla para cogeneración REDUCCIÓN DE EMISIONES DEL S. DE COGENERACIÓN
    def reduccionEmisionesCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")
        def registros = false
        def combustibleLp = true
        
        //DH001 + DH009
        def energiaElectricaSalida =1       
        def factorEmisionesDerivadasGeneracionElectricaAlternativa = 1.11
        
        def factorEmisionesDerivadasTermicaLP = 0.00279
        def factorEmisionesDerivadasTermicaDiesel = 0.002449962
        def factorEmisionesDerivadasTermicaNatural = 0.00215 
        //**********
        
        //DH011
        def energiaTermicaTotal = 0
        def horasOperacion = 0
        def cefGasLp = 1 //??
        def cefGasNatural = 1 //??
        
        def consumoCombustible = 0 //gasLp o gas ntral no importa
            
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
       
        if(fuentes){
            
            for(fuente in fuentes){
               
                def combustible = CombustibleFuente.get(fuente.combustibleFuenteId)
              
                if(combustible){
                                  
                    if(combustible.nombre != "Gas LP")
                    combustibleLp = false

                    def tuplas = RegistroSistemaCogeneracionHora.findAll {
                        dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                    }

                    horasOperacion = tuplas.size()
                    if ( horasOperacion > 0)
                    {
                        registros = true
                    }
                    for(tupla in tuplas)
                    {
                        //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                        energiaElectricaSalida += tupla.energiaSalida
                        consumoCombustible += tupla.fuelEnergyFlow
                        energiaTermicaTotal += tupla.energiaTermicaSalida   
                    }        
                }
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){                                               
                if(combustibleLp == true){
                    def resOper = (((energiaElectricaSalida * factorEmisionesDerivadasGeneracionElectricaAlternativa) + (consumoCombustible*factorEmisionesDerivadasTermicaLP))-(energiaTermicaTotal*horasOperacion*cefGasLp)) / energiaElectricaSalida 
                    if(resOper <0) resOper = resOper*(-1)
                    result = resOper
                }else{
                    def resOper = (((energiaElectricaSalida * factorEmisionesDerivadasGeneracionElectricaAlternativa) + (consumoCombustible*factorEmisionesDerivadasTermicaNatural))-(energiaTermicaTotal*horasOperacion*cefGasNatural)) / energiaElectricaSalida 
                    if(resOper <0) resOper = resOper*(-1)
                    result = resOper
                
                }
            }

            return result
        }
        return 0
       
    }
    
    //primer pantalla para emisiones Histórico: Desglose de reducciones
    def emisionesHistorico(sitio, fechaActual, fechaMesAtras)
    {
        
    }
    
    //primer pantalla para fotovoltaico
    def reduccionEmisionesFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Panel Solar")
        def registros = false                
        def energiaElectricaSalida =1      
        def factorEmisionesDerivadasGenElectricaCFE = 499.9
        def factorEmisionesDerivadasGenElectricaPanel = 0.00005
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        def horasOperacion = 0

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaFotovoltaicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaCa
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = ((factorEmisionesDerivadasGenElectricaCFE*energiaElectricaSalida) - (factorEmisionesDerivadasGenElectricaPanel*energiaElectricaSalida))            
            }

            return result
        }
        return 0
       
    }
    //primer pantalla para cogeneración
    def reduccionEmisionesColectorSolar(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Colector solar")
        def registros = false                
        def energiaElectricaSalida =1      
        def factorEmisionesDerivadasMMTBTUCFE = 1.11 //???
        def factorEmisionesDerivadasMMTBTUPanel = 1 //??
        def horasOperacion = 0
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroColectorSolarHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energia
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = ((factorEmisionesDerivadasMMTBTUCFE*energiaElectricaSalida) - (factorEmisionesDerivadasMMTBTUPanel*energiaElectricaSalida))            
            }

            return result
        }
        return 0
       
    }
    
    //Segunda Pantalla Sistema Cogeneracion
    
    //Segunda pantalla de cogeneracion. Se muestra en REDUCCIÓN DE EMISIONES POR GENERACIÓN ELÉCTRICA
    def reduccionEmisionesGeneracionElectrica(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")
        def registros = false                
        def energiaElectricaSalida =1    
        def factorEmisionesDerivadasGeneracionElectricaAlternativa = 1.11 //DH005
        def horasOperacion =0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaSalida
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = (energiaElectricaSalida * factorEmisionesDerivadasGeneracionElectricaAlternativa)            
            }

            return result
        }
        return 0
        
        
    }
    
    //Metodo que regersa los resultados de las formulas segun sea cada combustible.
    //Devuelve una matris de 3x2 que contiene los resultados en orden: lp, natural, diesel
    //Se usa en la segunda pantalla de emisiones -> cogeneracion
    def reduccionEmisionesTotalesCombustible(sitio, fechaActual, fechaMesAtras)
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")
        def registros = false
        def combustibleLp = true
        
        //Variables de uso segun sea el combustible
        def energiaElectricaSalidaLP =0.0
        def energiaElectricaSalidaNatural =0.0       
        def energiaElectricaSalidaDiesel=0.0   
        def consumoLP = 0.0
        def consumoNatural = 0.0
        def consumoDiesel = 0.0
        def energiaTermicaSalidaLP =0.0
        def energiaTermicaSalidaNatural =0.0
        def energiaTermicaSalidaDiesel=0.0
        
        //Factores de emisiones
        def factorEmisionesDerivadasGeneracionElectricaAlternativa = 1.11
        def factorEmisionesDerivadasTermicaLP = 0.00279
        def factorEmisionesDerivadasTermicaDiesel = 0.002449962
        def factorEmisionesDerivadasTermicaNatural = 0.00215 
        //**********
        
        //CEF's
        def horasOperacion = 0
        def cefGasLp =1
        def cefGasNatural = 1
        def cefDiesel = 1
        
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        
        if(fuentes){
            def combustible
            for(fuente in fuentes){
                
                combustible = FuenteCombustible.get(fuente.combustibleFuenteId)
                if(combustible){
                    
                    def tuplas = RegistroSistemaCogeneracionHora.findAll {
                        dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                    }
                    
                    horasOperacion = tuplas.size()
                    if ( horasOperacion > 0)
                    {
                        registros = true
                    }
                    for(tupla in tuplas)
                    {
                        //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                        if(combustible.nombre == "Gas LP")
                        {
                            energiaElectricaSalidaLP += tupla.energiaSalida
                            consumoLP += tupla.fuelEnergyFlow
                            energiaTermicaSalidaLP += tupla.energiaTermicaSalida   
                            
                        }else if(combustible.nombre == "Gas Natural")
                        {
                            energiaElectricaSalidaNatural += tupla.energiaSalida
                            consumoNatural += tupla.fuelEnergyFlow
                            energiaTermicaSalidaNatural += tupla.energiaTermicaSalida   
                            
                        }else{
                            
                            energiaElectricaSalidaDiesel += tupla.energiaSalida
                            consumoDiesel += tupla.fuelEnergyFlow
                            energiaTermicaSalidaDiesel += tupla.energiaTermicaSalida   
                        }
                    }        
                }
            }
            
            def result = [][]
            if( registros == true){
                
                def DH009 = (double)consumoLP*factorEmisionesDerivadasTermicaLP
                def DH011 = (double)energiaTermicaSalidaLP*horasOperacion*cefGasLp
                def DH016 = (double)(((energiaElectricaSalidaLP * factorEmisionesDerivadasGeneracionElectricaAlternativa) +
                        (consumoLP*factorEmisionesDerivadasTermicaLP))-(energiaTermicaSalidaLP*horasOperacion*cefGasLp)) / energiaElectricaSalidaLP 
                    
                result[0][0] = (double)DH009
                result[0][1] = (double)DH0011
                result[0][2] = (double)DH0016
                  
                def DH010 = (double)consumoNatural*factorEmisionesDerivadasTermicaNatural   //DT105*DH008
                def DH012 = (double)(energiaTermicaSalidaNatural*horasOperacion*cefGasNatural)
                def DH017 = (double)(((energiaElectricaSalidaNatural * factorEmisionesDerivadasGeneracionElectricaAlternativa) +
                        (consumoNatural*factorEmisionesDerivadasTermicaNatural))-(energiaTermicaSalidaNatural*horasOperacion*cefGasNatural)) / energiaElectricaSalidaNatural 
                   
                result[1][0] = (double)DH010
                result[1][1] = (double)DH012
                result[1][2] = (double)DH017
                    
                
                def DH002=(double)factorEmisionesDerivadasTermicaDiesel*consumoDiesel
                def DH003= (double)energiaTermicaSalidaDiesel*horasOperacion*cefDiesel // DT061*DT012.1*DH013
                def DH004= (double)(((energiaElectricaSalidaDiesel * factorEmisionesDerivadasGeneracionElectricaAlternativa) +
                        (consumoCombustible*factorEmisionesDerivadasTermicaDiesel))-(energiaTermicaSalidaDiesel*horasOperacion*cefGasDiesel)) / energiaElectricaSalidaDiesel    // ((DH001 + DH002)-DH003)/DT020
                
                result[2][0] = (double)DH002
                result[2][1] = (double)DH003
                result[2][2] = (double)DH004
               
                return result
            }
            
          
        }
        
        def result = [[0.0, 0.0, 0.0], [0.0, 0.0, 0.0], [0.0, 0.0, 0.0]] as double[][]
        
        
        return result
        
    }
    
    //Segunda Pantalla Sistema Fotovoltaico
    
    def emisionesCFEFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Panel solar")
        def registros = false                
        def energiaElectricaSalida =0  
        def factorEmisionesDerivadasGeneracionElectricaCFE = 1.11 
        def horasOperacion =0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaSalida
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = (energiaElectricaSalida * factorEmisionesDerivadasGeneracionElectricaCFE)            
            }

            return result
        }
        return 0
        
    }
    def emisionesPanelFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Panel solar")
        def registros = false                
        def energiaElectricaSalida =0  
        def factorEmisionesDerivadasGeneracionElectricaPanel = 1.11 
        def horasOperacion =0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaFotovoltaicoHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros =true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaSalida
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = (energiaElectricaSalida * factorEmisionesDerivadasGeneracionElectricaPanel)            
            }

            return result
        }
        return 0
        
    }
    
    //Segunda Pantalla Sistema Colector Solar
    
    //Primer cuadro de pantalla
    def emisionesCFEColector(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Colector solar")
        def registros = false                
        def energiaElectricaSalida =0  
        def factorEmisionesMMBTUCFE = 1.11 
        def horasOperacion =0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaSalida
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = (energiaElectricaSalida * factorEmisionesMMBTU)            
            }

            return result
        }
        return 0
        
    }
    //Segundo cuadro de pantalla
    def reduccionMedioAmbienteColector(sitio, fechaActual, fechaMesAtras)
    {
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Colector Solar")
        def registros = false                
        def energiaElectricaSalida =0  
        def factorEmisionesMMBTUColector= 1.11  //DH027
        def horasOperacion =0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroSistemaCogeneracionHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

                horasOperacion = tuplas.size()
                if ( horasOperacion > 0)
                {
                    registros = true
                }
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaElectricaSalida += tupla.energiaSalida
                }        
            }
            //def energiaTermicaTotalCogeneracion = RegistroSistemaCogeneracionHora.executeQuery("select sum(reg.energiaTermicaSalida) from RegistroSistemaCogeneracionHora as reg where reg.dateCreated between :d1 and :d2",[d1:'2014-09-21 00:00:00',d2:fecha2])[0]

            
            def result = 0
            if( registros == true){
                result = (energiaElectricaSalida * factorEmisionesMMBTUColector)            
            }

            return result
        }
        return 0
        
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
