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
class AhorrosGlobalesController {
def springSecurityService

    def index() { 
        
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
        
        def arregloEnergiaTotalCFE = [0.0,0.0,0.0,0.0]
        def costoEvitadoElectricaAlternas = 0.0
        def costoEvitadoTermicasAlternas = 0.0
        
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
            
            arregloEnergiaTotalCFE =  reporteC001EnergiaTotalCFE(sitios.get(0),fechaHoy, fechaMesAtras)
            costoEvitadoElectricaAlternas = auxiliarDC027(sitios.get(0),fechaHoy, fechaMesAtras)
            costoEvitadoTermicasAlternas=   auxiliarDC048(sitios.get(0),fechaHoy, fechaMesAtras)
            
            //Grafica:
            def arregloResultadoGrafica = graficaIndex(sitios.get(0))
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            
            
        }
        
        
        [sitios:sitios, numeroSitios:num, rol:rol,arregloEnergiaTotalCFE:arregloEnergiaTotalCFE,costoEvitadoElectricaAlternas:costoEvitadoElectricaAlternas,
        costoEvitadoTermicasAlternas:costoEvitadoTermicasAlternas,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,
        arregloGraficaValores2:arregloGraficaValores2]
        
    }
    
    def costoEvitadoElectrica(){
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
   
        def costoEvitadoCogeneracion = [0.0,0.0,0.0,0.0]
        def costoEvitadoFotovoltaico = [0.0,0.0,0.0,0.0]
        
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
               // fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
            costoEvitadoCogeneracion = reporteC001CostoEvitadoCogeneracion(sitios.get(0),fechaHoy, fechaMesAtras)
            costoEvitadoFotovoltaico = reporteC001CostoEvitadoFotovoltaico(sitios.get(0),fechaHoy, fechaMesAtras)
            
              //Grafica:
            def arregloResultadoGrafica = graficaElectrico(sitios.get(0))           
            arregloGraficaMeses = arregloResultadoGrafica[0]
            
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
           
            
        }
     
        
       [sitios:sitios, numeroSitios:num, rol:rol,costoEvitadoCogeneracion:costoEvitadoCogeneracion,costoEvitadoFotovoltaico:costoEvitadoFotovoltaico,
       arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,arregloGraficaValores2:arregloGraficaValores2]
    }
    def costoEvitadoTermica(){
        def num =0
        def sitios
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
        def rol = UsuarioRol.findByUsuario(usuario)
        rol = Rol.findById(rol.rol.id)   
   
        def costoEvitadoTermicaTermico = 0
        def costoEvitadoTermicaCogeneracion = 0
        def costoEvitadoColectorSolar = 0
        
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
               // fechaMesAtras = new Date(fechaHoy.getYear(), fechaMesAtras.getMonth(), 1)
            }
            
          costoEvitadoTermicaCogeneracion += auxiliarDC046(sitios.get(0),fechaHoy, fechaMesAtras)
          costoEvitadoTermicaTermico += auxiliarDC047(sitios.get(0),fechaHoy, fechaMesAtras)
          costoEvitadoColectorSolar += auxiliarDC492(sitios.get(0),fechaHoy, fechaMesAtras)       
            
            def arregloResultadoGrafica = graficaTermico(sitios.get(0))
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
             
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
            
            
        }
     
        
       [sitios:sitios, numeroSitios:num, rol:rol,costoEvitadoTermicaCogeneracion:costoEvitadoTermicaCogeneracion,costoEvitadoTermicaTermico:costoEvitadoTermicaTermico,
       costoEvitadoColectorSolar:costoEvitadoColectorSolar,arregloGraficaMeses:arregloGraficaMeses,arregloGraficaValores:arregloGraficaValores,
       arregloGraficaValores2:arregloGraficaValores2,arregloGraficaValores3:arregloGraficaValores3]
    }
    
    //***************************METODOS DE CALCULOS*****************************************
    
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

                    def DC022 = auxiliarDC022(sitio, fechaTempActual, fechaAtras)
                    def DC026 = auxiliarDC026(sitio, fechaTempActual, fechaAtras)
                    def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
         
                    valoresXmes1[i] = (double) DC027
                    valoresXmes2[i] = (double) auxiliarDC048(sitio, fechaTempActual, fechaAtras)
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
     def graficaElectrico(sitio)
    {
        Date fechaHoy = new Date();            
        def fechaAtras
        def valoresXmes1=[12] //El primer arreglo para el primer conjunto de valores a graficar (este caso ahorro energias alternas)
        def valoresXmes2=[12] //El segundo arreglo para el primer conjunto de valores a graficar         
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
                   def  costoEvitadoCogeneracion = reporteC001CostoEvitadoCogeneracion(sitio, fechaTempActual, fechaAtras)
                   def  costoEvitadoFotovoltaico = reporteC001CostoEvitadoFotovoltaico(sitio, fechaTempActual, fechaAtras)

                    valoresXmes1[i] = costoEvitadoCogeneracion[0]
                    valoresXmes2[i] = costoEvitadoFotovoltaico[0]
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
    
     def graficaTermico(sitio)
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
                    
                    
                    valoresXmes1[i] = (double) auxiliarDC046(sitio, fechaTempActual, fechaAtras)
                    valoresXmes2[i] = (double) auxiliarDC047(sitio, fechaTempActual, fechaAtras)
                    valoresXmes3[i] = (double) auxiliarDC492(sitio, fechaTempActual, fechaAtras)
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
    
    /* función para ser llamada mediante AJAX cada que cambia de fecha o sitio el usuario
    devuelve un xml con los datos requeridos, debe llegar por parametrola fecha inicio y la 
    fecha final así como también un id de un sitio
    */
    def actualizaReporteIndex(){
        
         def arregloEnergiaTotalCFE = [0.0,0.0,0.0,0.0]
        def costoEvitadoElectricaAlternas = 0.0
        def costoEvitadoTermicasAlternas = 0.0
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
            
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
         
          arregloEnergiaTotalCFE =  reporteC001EnergiaTotalCFE(thisSite,fechaEnd, fechaInit)
          costoEvitadoElectricaAlternas = auxiliarDC027(thisSite,fechaEnd, fechaInit)
          costoEvitadoTermicasAlternas=   auxiliarDC048(thisSite,fechaEnd, fechaInit)
       
          //Grafica:
            def arregloResultadoGrafica = graficaIndex(thisSite)
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
        
        

        def resultado = []
        resultado.add(arregloEnergiaTotalCFE[0])
        resultado.add(arregloEnergiaTotalCFE[1])
        resultado.add(arregloEnergiaTotalCFE[2])
        resultado.add(arregloEnergiaTotalCFE[3])
        
        resultado.add(costoEvitadoElectricaAlternas)
        resultado.add(costoEvitadoTermicasAlternas)
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        
        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    //metodo para ser llamado mediante AJAX cada que cambia de fecha o sitio el usuario
    def actualizaReporteEnergiaElectrica(){
        
        def costoEvitadoCogeneracion = [0.0,0.0,0.0,0.0]
        def costoEvitadoFotovoltaico = [0.0,0.0,0.0,0.0]
        
        def arregloGraficaMeses = ["","","","","","","","","","","",""]
        def arregloGraficaValores = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        def arregloGraficaValores2 = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
            
        def fechaInit = new Date(Long.valueOf(params?.fechaInicio))
        def fechaEnd = new Date(Long.valueOf(params?.fechaFinal))
        
        //modificar tiempo de fechaInit
        fechaEnd.setHours(23)
        fechaEnd.setMinutes(59)
        
        def thisSite = Sitio.findById(Integer.parseInt(params?.sitioId))
         
         costoEvitadoCogeneracion = reporteC001CostoEvitadoCogeneracion(thisSite,fechaEnd, fechaInit)
         costoEvitadoFotovoltaico = reporteC001CostoEvitadoFotovoltaico(thisSite,fechaEnd, fechaInit)
         
       
            //Grafica:
            def arregloResultadoGrafica = graficaElectrico(thisSite)
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
            
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
        
        def resultado = []
        resultado.add(costoEvitadoCogeneracion[0])
        resultado.add(costoEvitadoCogeneracion[1])
        resultado.add(costoEvitadoCogeneracion[2])
        resultado.add(costoEvitadoCogeneracion[3])
        
        resultado.add(costoEvitadoFotovoltaico[0])
        resultado.add(costoEvitadoFotovoltaico[1])
        resultado.add(costoEvitadoFotovoltaico[2])
        resultado.add(costoEvitadoFotovoltaico[3])
        
        resultado.add(arregloGraficaMeses)
        resultado.add(arregloGraficaValores)
        resultado.add(arregloGraficaValores2)
        
        
        def xml
        withFormat(){
            xml = resultado as XML
        }
        render xml
        
    }
    //metodo para ser llamado mediante AJAX cada que cambia de fecha o sitio el usuario
    def actualizaReporteEnergiaTermica(){
        
        def costoEvitadoTermicaTermico = 0.0
        def costoEvitadoTermicaCogeneracion = 0.0
        def costoEvitadoColectorSolar = 0.0
        
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
         
        
        
        costoEvitadoTermicaCogeneracion += (double)auxiliarDC046(thisSite,fechaEnd, fechaInit)
        costoEvitadoTermicaTermico += (double) auxiliarDC047(thisSite,fechaEnd, fechaInit)
        costoEvitadoColectorSolar += (double) auxiliarDC492(thisSite,fechaEnd, fechaInit)       
        
          def arregloResultadoGrafica = graficaTermico(thisSite)
            
            arregloGraficaMeses = arregloResultadoGrafica[0]
             
            arregloGraficaValores = arregloResultadoGrafica[1]
            arregloGraficaValores2 = arregloResultadoGrafica[2]
            arregloGraficaValores3 = arregloResultadoGrafica[3]
        
        
        def resultado = []
        resultado.add(costoEvitadoTermicaCogeneracion)
        resultado.add(costoEvitadoTermicaTermico)
        resultado.add(costoEvitadoColectorSolar)
        
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
    
    //Devuelve un arreglo de 3 que conteine [0]:Sis. de cog, [1]: S. Termico, [2]:total
    //Para el cuadro de Costo de Combustibles en costoCombustibles
    def costosDeCombustibles(sitio,fechaActual, fechaMesAtras)
    {
        def arregloResult = [0.0,0.0,0.0]
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {        
        
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)      
            def DC059 = precioCombustibleCog(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            tuplasCombustible = tuplasSistemaTermico(sitio, fechaActual, fechaMesAtras)
            def DC060 = auxiliarDC060(sitio, tipoCombustible(fuentes[0]) ,fechaActual, fechaMesAtras, tuplasCombustible)
            def DC061 = auxiliarDC061(sitio, fechaActual, fechaMesAtras)
            arregloResult[0] = DC059
            arregloResult[1] = DC060
            arregloResult[2] = DC061
        }
        return arregloResult
    }
    
    //Se devuelve un arerglo que contiene [0]:Lectura inicial [1]:Lectura final de Cogeneracion
    def lecturaInicialFinalCogeneracion(sitio,fechaActual, fechaMesAtras)
    {
        def arregloResult = [0.0,0.0]
        return arregloResult
    }
    //Se devuelve un arreglo que contiene [0]:Lectura inicial [1]:Lectura final de Termico
    def lecturaInicialFinalTermico(sitio,fechaActual, fechaMesAtras)
    {
        def arregloResult = [0.0,0.0]
        return arregloResult
    }
    
    
    def costoKWHEnergiaTermica(sitio,fechaActual, fechaMesAtras)
    {
        def DC044 = auxiliarDC044(sitio,fechaActual, fechaMesAtras)
        def DC045 = auxiliarDC045(sitio,fechaActual, fechaMesAtras)
        def DC0491 = 0 //??
        def arregloResult = [0.0,0.0,0.0]
        arregloResult[0] += DC044
        arregloResult[1] += DC045
        arregloResult[2] += DC0491
        
        return arregloResult
    }
    
    //Devuleve un arreglo de 2 conteniendo el precio acordado del comb de [0]: cogeneracion y [1]: termico
     //Si devuelve Ceros, en la Vista se debe mtrar que se toman de las páginas correspondientes
    def precioUnitarioCombustibles(sitio,fechaActual, fechaMesAtras)
    {
        def arregloResult = [0.0,0.0]
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes){
            arregloResult[0] += AuxiliarDC012(sitio, tipoCombustible(fuentes[0]),fechaActual, fechaMesAtras)
            arregloResult[1] += AuxiliarDC049(sitio, tipoCombustible(fuentes[0]),fechaActual, fechaMesAtras)
        }
               
        return arregloResult
        
    }
    
    def AuxiliarDC049(sitio, tipoCombustible,fechaActual, fechaMesAtras){
        def precioResult = 0
        if( tipoCombustible == "Gas LP")
        {
            if(sitio.precioGasLp == 0)
            {
              return 0.0
            }
            else
            {
                def precio = sitio.precioGasLp
                return precio
            }
        }else if(tipoCombustible=="Gas Natural")
        {
            if(sitio.precioGasNatural == 0)
            {
                return 0.0
            }else
            {
                def precio = sitio.precioGasNatural
                return precio
                
            }
        }else{        
            if(sitio.precioDiesel == 0)
            {
               
              return 0.0
                
            }else
            {
                def precio = sitio.precioDiesel
               return precio
                
            }            
        }
        return 0.0        
    }
    
    def AuxiliarDC012(sitio, tipoCombustible,fechaActual, fechaMesAtras){
        def precioResult = 0
        if( tipoCombustible == "Gas LP")
        {
            if(sitio.precioGasLp == 0)
            {
              return 0.0
            }
            else
            {
                def precio = sitio.precioGasLp
                return precio
            }
        }else if(tipoCombustible=="Gas Natural")
        {
            if(sitio.precioGasNatural == 0)
            {
                return 0.0
            }else
            {
                def precio = sitio.precioGasNatural
                return precio
                
            }
        }else{        
            if(sitio.precioDiesel == 0)
            {
               
              return 0.0
                
            }else
            {
                def precio = sitio.precioDiesel
               return precio
                
            }            
        }
        return 0.0        
    }
    
    def auxiliarDC046(sitio, fechaActual, fechaMesAtras)
    {
        
       def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras) 
       
       def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
       def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
       
       def DC046 = (DT062 * DC044)
       return DC046
    }
    
    def auxiliarDC047(sitio, fechaActual, fechaMesAtras)
    {
       def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
       def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)        
       def DC047 = (DT063*DC045)
       return DC047
    }
    
    def costoEvitadoFuentesAlternasReporteC003(sitio, fechaActual, fechaMesAtras){
       
       def arregloResult = [0.0,0.0,0.0,0.0]
        
       def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)         
       def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
       def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
       def DC046 = (DT062 * DC044)
       
       def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
       def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)        
       def DC047 = (DT063*DC045)
        
       def DC0491 = 0 //??
       def DC0492 = DC045 - DC0491
       
       arregloResult[0] += (DC046+DC047+DC0492)
       arregloResult[1] += DC046
       arregloResult[2] += DC047
       arregloResult[3] += DC0492
       
       return arregloResult
       
        
    }
    
    def tipoTarifaSitio(sitio)
    {
        if(sitio.tarifaId == 1)
        {
            return "OM"
        }else if(sitio.tarifaId == 2)
        {
            return "H-M"
        }else
            return "H-MC"
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
    
    //Devuelve el flujo de agua total del colector solar
    def flujoAguaTotalColectorSolar(sitio, fechaActual, fechaMesAtras) //DT0503
    {
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Colector solar")                  
        def energiaCorrienteAlterna = 0
      
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroColectorSolarHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

             
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    
                    energiaCorrienteAlterna += tupla.flujoAgua
                }        
            }
           
        }
        return energiaCorrienteAlterna
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
    
    def getGasLPTuplas(fechaActual, fechaMesAtras)
    {       
       def arreglo = GasLpAmexgas.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras)
                }
        return arreglo
    }
    
    //Devulve el resultado de una operacion de uso de combustible * precio del comb usado en esa fecha por el sist. de cog
    //DC059
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
    
    //Devuelve el reprote DC027
    def auxiliarDC027(sitio, fechaActual, fechaMesAtras)
    {
       def DC022 = auxiliarDC022(sitio, fechaActual, fechaMesAtras)
       def DC026 = auxiliarDC026(sitio, fechaActual, fechaMesAtras)      
       def DC027 = (DC022[0]+DC022[1]+DC022[2] + DC026[0] + DC026[1] + DC026[2])
       return DC027
    }
    
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
                  if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
            
             for(tupla in arregloEnergiasCog[1])
            {
                  if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
            
             for(tupla in arregloEnergiasCog[2])
            {
                  if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
    
    def auxiliarDT062(sitio, fechaActual, fechaMesAtras)
    {
       def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)          
       def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
       return DT062
    }
    
    //DT053*DT050.3*(DT050.2-DT050.1)
    def auxiliarDT0681(sitio, fechaActual, fechaMesAtras)
    {
        def DT053 = 4.186; //???????
        def DT0503 = flujoAguaTotalColectorSolar(sitio, fechaActual, fechaMesAtras)
        def arregloTemp = tempAguaEntradaSalidaColectorSolar(sitio, fechaActual, fechaMesAtras)
        def DT0681 = DT053*DT0503*(arregloTemp[1]-arregloTemp[0])
        return DT0681
    }
    
    //Devuelve un arreglo de 2 devolviendo en [0]:Temp. aguan entrada al modulo , [1]: Temp. agua salida al modulo
    def tempAguaEntradaSalidaColectorSolar(sitio, fechaActual, fechaMesAtras){ // DT0501 y DT0502
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Colector solar")                  
        def energiaCorrienteAlterna = 0
       def arrayResult = [0.0,0.0]
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
 

        if(fuentes){
            
            for(fuente in fuentes){
                
                def tuplas = RegistroColectorSolarHora.findAll {
                    dateCreated <= (fechaActual) && (dateCreated >=fechaMesAtras) && (fuente)
                }

             
                for(tupla in tuplas)
                {
                    //energiaTermicaTotalCogeneracion += tupla.energiaTermicaSalida   
                    arrayResult[0] += tupla.tempAguaEntradaModulo
                    arrayResult[1] += tupla.tempAguaSalidaModulo
                   // energiaCorrienteAlterna += tupla.flujoAgua
                }        
            }
           
        }
        return arrayResult
    }
    
    def auxiliarDT0503(sitio, fechaActual, fechaMesAtras)
    {
        
    }
    
    def auxiliarDT066(dt062, dt063,dt068)
    {
        return dt062 + dt063 + dt068
    }
    
    def auxiliarDT067(dt066, dt065)
    {
       
        if(dt065 == 0)
        return 0
        return ((dt066 / dt065) *100 )
    }
    
    //Regresa un arreglo contenindo Porcentaje de eficiecnia termica, energia aprov. de Cogeneracion y Aprovechada de S. Termico
    //Reporte C003 Costos de energia en el periodo
    def eficienciaTermicaReporteC003(sitio, fechaActual, fechaMesAtras){
        def DT062 = auxiliarDT062(sitio, fechaActual, fechaMesAtras)
        def DT063 = auxiliarDT063(sitio, fechaActual, fechaMesAtras)
        def DT065 = auxiliarDT065(sitio, fechaActual, fechaMesAtras)
        def DT0682 = auxiliarDT0681(sitio, fechaActual, fechaMesAtras)
                    
        def DT066 = auxiliarDT066(DT062, DT063,DT0682)
        def DT067 = auxiliarDT067(DT066, DT065)
        
        def arregloResult = [0.0,0.0,0.0]
        arregloResult[2] = DT062
        arregloResult[1] = DT063
        arregloResult[0] = DT067
         return arregloResult
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
    
    
    def auxiliarDT065(sitio, fechaActual, fechaMesAtras) //DT065
    {
        def DT061 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Termica")
        def DT064 = energiaTermicaTotalSistemaTermico(sitio, fechaActual, fechaMesAtras)
        return DT061 - DT064
    }
    
    def auxiliarDC492(sitio, fechaActual, fechaMesAtras)
    {
       def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
       def DC491 = 0 //??
       def result = (DC045 - DC491)
       return result
    }
    def auxiliarDC048(sitio, fechaActual, fechaMesAtras)
    {
       def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)  
        //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
       def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
       def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
       def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
       def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
       def DC491 = 0 //??
       def DC048 = DT062 + DC044 + DT063 + DC045 +(DC045 - DC491)
       return DC048
    }
    
     def auxiliarDC044(sitio, fechaActual, fechaMesAtras){
        
        def fuenteMicroturbina = FuenteDeEnergia.findByTipo("Microturbina")              
        def fuentes = FuenteSitio.findAllBySitioAndFuenteDeEnergiaId(sitio,fuenteMicroturbina.id)
        
        if(fuentes)
        {
            
            def tuplasCombustible = tuplasSistemaCogeneracion(sitio, fechaActual, fechaMesAtras)
            def DT061 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Termica")
            def DT020 = energiaTotalCogeneracion(sitio, fechaActual, fechaMesAtras, "Electrica")
            def oper =1
            if((DT020+DT061)!=0)
                oper = (DT061 /(DT020+DT061))

            
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
    
    def auxiliarDC061(sitio, fechaActual, fechaMesAtras){
       def arregloEnergiaAprovechadaCog =  energiaAprovechadaCogeneracion(sitio,fechaActual, fechaMesAtras)  
       
       //DC048 = DT062 + DC044 + DT063 + DC045 + ( DC045 - DC49.1 )
       def DT062 = arregloEnergiaAprovechadaCog[0]+ arregloEnergiaAprovechadaCog[1] + arregloEnergiaAprovechadaCog[2]
       def DC044 = auxiliarDC044(sitio, fechaActual, fechaMesAtras)
       def DT063 =auxiliarDT063(sitio, fechaActual, fechaMesAtras)
       def DC045 = auxiliarDC045(sitio, fechaActual, fechaMesAtras)
       def DC491 = 0 //??
       def DC048 = (DT062 * DC044) + (DT063 * DC045) +(DC045 - DC491)
       
        //DC061 = DC047 + DC048 = DT063* DC045 + (DC046 + DC047+DC049.2)
       def DC061 = (DC045 * DT063) + DC048 
       return DC061
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
    
    
    def auxiliarDC060(sitio,tipoCombustible, fechaActual, fechaMesAtras,registrosTermico){
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
                    
                    precioResult += precioMes.precioLt * tupla.flujoCombustible
                    
               } 
            }
            else
            {
                def precio = sitio.precioGasLp
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible 
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
                    
                    precioResult += precioMes.precio * tupla.flujoCombustible 
                    
               } 
            }else
            {
                def precio = sitio.precioGasNatural
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible 
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
                    
                    precioResult += precioMes.precio * tupla.flujoCombustible 
                    
               } 
            }else
            {
                def precio = sitio.precioDiesel
                for(tupla in registrosTermico){
                
                    precioResult += precio * tupla.flujoCombustible
               } 
            }
            
        }
        
        return precioResult
    }
    
    //Devuelve un arreglo de 4 conteneindo: [0]:total de enrgias cFE, [1] total energiaBase CFE, [2]: total energia intermedia CFE, [3]:Total energ. Punta CFE
    //Cada una mult. por su costo de la página de la CFE
    def reporteC001EnergiaTotalCFE(sitio, fechaActual, fechaMesAtras)
    {
        def sumaBIP = [0.0,0.0,0.0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)         
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)               
        //El sitio se rige por Tarifa OM
        if(sitio.tarifaId == 1)
        {
           
            //Las bajas estan en [0]
            for(tupla in arregloEnergiasCog[0])
            {
                if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
            
             for(tupla in arregloEnergiasCog[1])
            {
                if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
             for(tupla in arregloEnergiasCog[2])
            {
                if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
        
        def arregloRestult = [0.0,0.0,0.0,0.0]
        arregloRestult[0] = (double)(sumaBIP[0]+sumaBIP[1]+sumaBIP[2])
        arregloRestult[1] = (double)sumaBIP[0]
        arregloRestult[2] = (double)sumaBIP[1]
        arregloRestult[3] = (double)sumaBIP[2]
        
        return arregloRestult
    }
    
    //Devuelve un arreglo contenido la [0]:energia total, [1]:Base*su precio, [2]:energia Inter * [3]:precio, energia Punta*Precio
    def reporteC001CostoEvitadoCogeneracion(sitio, fechaActual, fechaMesAtras)
    {
        def sumaBIP = [0.0,0.0,0.0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)            
        //El sitio se rige por Tarifa OM
        if(sitio.tarifaId == 1)
        {
            
            //Las bajas estan en [0]
            for(tupla in arregloEnergiasCog[0])
            {
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
            
            for(tupla in arregloEnergiasCog[1])
            {
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
            
            for(tupla in arregloEnergiasCog[2])
            {
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
        def arregloRestult = [0.0,0.0,0.0,0.0]
        arregloRestult[0] = (double)(sumaBIP[0]+sumaBIP[1]+sumaBIP[2])
        arregloRestult[1] = (double)sumaBIP[0]
        arregloRestult[2] = (double)sumaBIP[1]
        arregloRestult[3] = (double)sumaBIP[2]
      
        return arregloRestult
    }
    
    //Devuelve un arreglo que contiene el total de energia, total de energia base (fotovoltaico, cog y CFE ) , ...
    def reporteC001(sitio, fechaActual, fechaMesAtras)
    {
        def arregloResult = [0,0,0,0]
        def arregloBIPCog = energiasBIPCogeneracion(sitio, fechaActual, fechaMesAtras) 
        def arregloBIPFoto = energiasBIPFotovoltaico(sitio, fechaActual, fechaMesAtras)
        def arregloBIPCFE =  energiasBIPCFE(sitio, fechaActual, fechaMesAtras)
        arregloResult[1] += arregloBIPCog[0] + arregloBIPFoto[0] + arregloBIPCFE[0]
        arregloResult[2] += arregloBIPCog[1] + arregloBIPFoto[1] + arregloBIPCFE[1]
        arregloResult[3] += arregloBIPCog[2] + arregloBIPFoto[2] + arregloBIPCFE[2]
        arregloResult[0] += arregloResult[1] + arregloResult[2] + arregloResult[3]
        return arregloResult
        
    }
    
    //Devuelve un arreglo conteniedo el total, energai base * precio, energia inter*precio, energiaPunta*precio del sist. fotovoltaico
    def reporteC001CostoEvitadoFotovoltaico(sitio, fechaActual, fechaMesAtras)
    {
        
        def sumaBIP = [0.0,0.0,0.0]
        def tuplasCFE = getCFETuplas(fechaActual,fechaMesAtras)
        //Este arreglo contiene las tuplas de registros de energia baja inter y punta. OJO contiene la tupla completa, es un arreglo de 3xN
        def arregloEnergiasCog = tuplasBIPCogeneracion(sitio, fechaActual, fechaMesAtras)            
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
            
            for(tupla in arregloEnergiasCog[1])
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
            
            for(tupla in arregloEnergiasCog[2])
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
        def arregloRestult = [0.0,0.0,0.0,0.0]
        arregloRestult[0] = (double)(sumaBIP[0]+sumaBIP[1]+sumaBIP[2])
        arregloRestult[1] = (double)sumaBIP[0]
        arregloRestult[2] = (double)sumaBIP[1]
        arregloRestult[3] = (double)sumaBIP[2]       
        
        return arregloRestult
    }
    
    
    //Devuelve un arreglo de BIP de las energias de fotovoltaico 
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
    
     //Devuelve las tuplas de CFE con los precios desde la fechaMesAtras (fecha1) hasta la fechaActual (fecha2)
    def getCFETuplas(fechaActual, fechaMesAtras)
    {       
       def arreglo = TarifaRegionCfe.findAll {
                    (dateCreated <= fechaActual) && (dateCreated >=fechaMesAtras)
                }                
        return arreglo
    }
    
    //Devuelve un arreglo de 3 conteniendo la energia sumada de CFE [0]:Base, [1]:Inter, [2]:Punta
    def energiasBIPCFE(sitio, fechaActual, fechaMesAtras)
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
                
        println arregloEnergias
    
        return arregloEnergias
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el costo q genero el consumo de energia de CFE de dicha tupla (registro)
    //esto si el sitio se encuentra bajo tarifa OM y region Baja Calif.
    def calculoBajaCaliforniaOMCFE(tupla, tuplasCFE, tipoSistema)
    {
           if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
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
            
        if(tipoSistema=="Microturbina"){
                   if(valCFEMes.energia != 0)
                    return (tupla.energiaCfe  * valCFEMes.energia)
                  else
                    return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
               }            
                    
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
            if(tipoSistema=="Microturbina"){
                if( valCFEMes.energia !=0)
                return (tupla.energiaCfe  * valCFEMes.energia)
                else
                 return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
            }
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
               if(tipoSistema=="Microturbina"){
                   if(valCFEMes.energia != 0)
                    return (tupla.energiaCfe  * valCFEMes.energia)
                  else
                    return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
               }
                
            }else if(tipoTarifa=="HM")
            {
                if(tipoBIP == "Base"){
                   if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energiaBase)
                 
                }
                else if (tipoBIP == "Intermedia"){
                    if(tipoSistema=="Microturbina")
                        return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
                  
                }
                else{
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energiaPunta)
             
                }
            }
            else{
                if(tipoBIP == "Base"){
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe  * valCFEMes.energiaBase)
               
                }
                else if (tipoBIP == "Intermedia")
                {
                  if(tipoSistema=="Microturbina")  
                    return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
                 
                }
                else{
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaCfe * valCFEMes.energiaPunta)
              
                }
            }
        }
        return 0
    }
    
    //Dado una tupla viniente de siste de cog. se devuelve el valor de energia generada * costo de energia de la CFE
    //esto si el sitio se encuentra bajo tarifa OM y Baja california
    def calculoBajaCaliforniaOM(tupla, tuplasCFE, tipoSistema)
    {
           if((!(tupla instanceof List) && !(tupla)) || (tupla == 0))
        {            
            return 0
        }
        println "PASO!!!!!!!!!"
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
            println "AQUIIIIIIIIIIIIIIIIIII"
             if(tipoSistema=="Microturbina"){
                if( valCFEMes.energia !=0)
                return (tupla.energiaCfe  * valCFEMes.energia)
                else
                 return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
            }
            
            if(tipoSistema=="Fotovoltaico"){
                if( valCFEMes.energia !=0)
                return (tupla.energiaCfe  * valCFEMes.energia)
                else
                 return (tupla.energiaCfe  * valCFEMes.energiaIntermedia)
            }
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
    
     //Dado una tupla de paramatro de siste de cog. se devuelve el valor de energia generada * costo de energia de la CFE
    def calculoTablas(tupla, tuplasCFE, tipoTarifa, tipoBIP, tipoSistema)
    {
        if(!tupla)
        {
            return 0.0
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
                    return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                   if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
                else if (tipoBIP == "Intermedia"){
                    if(tipoSistema=="Microturbina")
                        return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                    if(tipoSistema=="Fotovoltaico")
                        return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
                else{
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
            }
            else{
                if(tipoBIP == "Base"){
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
                else if (tipoBIP == "Intermedia")
                {
                  if(tipoSistema=="Microturbina")  
                    return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                  if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
                else{
                if(tipoSistema=="Microturbina")
                    return (tupla.energiaSalida  * valCFEMes.energiaIntermedia)
                if(tipoSistema=="Fotovoltaico")
                    return (tupla.energiaCa  * valCFEMes.energiaIntermedia)
                }
            }
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
