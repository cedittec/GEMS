/*JAPO ES GAY*/
package com.GEMS1
import com.GEMS1.Data.*
import com.GEMS1.Scada.*

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.SpringSecurityService

import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*

@Secured(['ROLE_SysAdmin','ROLE_adminTecnico','ROLE_clienteAdmin','ROLE_clienteTecnico'])
class PdfUtilController {
    def springSecurityService    
    def CalculosService
    def TiempoRealService
    
    //Métodos para proporcionar datos de interes con XML hacia las gráficas ******
    
    @Secured(['permitAll'])   
 
    def grafica2Arreglos(){
        
        def arregloResultadoGrafica
        def mensajeEjeX1 
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        def sitioId = params.sitioId
        def sitio = Sitio.get(params.sitioId);        
        def codigoGrafica = params?.graficaId
        
        if(codigoGrafica){
            if(codigoGrafica=="9")
            {   
               
                arregloResultadoGrafica = CalculosService.graficaCogeneracion(sitio);        
                mensajeEjeX1 = 'Sistema cogeneración'                
                 
            }   
            if(codigoGrafica=="10")
            {   
                
                arregloResultadoGrafica = CalculosService.graficaFotovoltaico(sitio);        
                mensajeEjeX1 = 'Sistema fotovoltaico'                
                 
            }   
            if(codigoGrafica=="11")
            {   
                
                arregloResultadoGrafica = CalculosService.graficaColector(sitio);        
                mensajeEjeX1 = 'Colector solar'                
                 
            }  
            if(codigoGrafica=="14")
            {                   
                arregloResultadoGrafica = CalculosService.graficaIndexEnergeticos(sitio);        
                mensajeEjeX1 = 'Energía cogeneración'                
                 
            } 
            if(codigoGrafica=="16")
            {                   
                arregloResultadoGrafica = CalculosService.graficaIndexEnergeticos(sitio);        
                mensajeEjeX1 = 'Sistema de Cogeneración'                
                 
            }  
            
            
            if( codigoGrafica == "100")
            {          
                def arregloGraficaMeses = ["Enero", "Febrero","Marzo","Abril", "Mayo", "Junio","Julio","Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]
                def arregloGraficaValores = [12]
                        
                try
                {
                    println "params recibidos en el 100!!!"
                    println params   
                    
                    def miLista = Acumulados.executeQuery(
                        "select sum(e)/(0.44*.91)+sum(h)/0.82 - sum(f)/ (sum(f)-sum(h)/0.82) "+
                        "from Acumulados "+ 
                        "where year(date) >= '"+params.year+"' and month(date) >= '"+params.month+"' or year(date) > '"+params.year+"' "+ 
                        "group by substr(date, 1, 7) "+
                        "order by substr(date, 1, 7) asc", [max: 12, offset: 0] 
                    )

                    for(def i = 0; i < 12; i++)
                        arregloGraficaValores[i] = miLista[i]
                    
                    def temp
                    for(def i = 0; i < params.int('month')-1; i ++)
                    {
                        temp = arregloGraficaMeses[0]
                        for(def j = 0; j < 11; j++)
                            arregloGraficaMeses[j] = arregloGraficaMeses[j+1]
                        arregloGraficaMeses[11] = temp
                    }
                }
                catch(Exception e)
                {
                    log.error("--------------\n--------------\n--------------\n")
                    log.error "Error: ${e.message}", e
                    log.error("--------------\n--------------\n--------------\n")
                }
                        
                xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
                xml.meses() 
                {
                    nombreEjeX1("Eficiencia")   
                    for(int i=0; i<12; i++)
                    {
                        mes()
                        {
                            nombre(arregloGraficaMeses[i])
                            valor(arregloGraficaValores[i])            
                        }
                    }           
                }
                render(text: writer, contentType: "text/xml", encoding: "UTF-8")
                return;
            }
            if( codigoGrafica == "101")
            {   
                    def arregloGraficaMeses = ["Enero", "Febrero","Marzo","Abril", "Mayo", "Junio", "Julio", "Agosto",  "Septiembre","Octubre","Noviembre","Diciembre"]
                    def arregloGraficaValores = [12]
                    def arregloGraficaValores2 = [12]
                    def arregloGraficaValores3 = [12]
                    
                    try
                    {
                        println "params recibidos en el 101!!!"
                        println params   
                        
                        def miLista = Acumulados.executeQuery(
                            "select sum(f), sum(h), sum(e) "+
                            "from Acumulados "+ 
                            "where year(date) >= '"+params.year+"' and month(date) >= '"+params.month+"' or year(date) > '"+params.year+"' "+ 
                            "group by substr(date, 1, 7) "+
                            "order by substr(date, 1, 7) asc", [max: 12, offset: 0] 
                        )
                    
                        for(def i = 0; i < 12; i++)
                        {
                            arregloGraficaValores[i] = miLista[i][0]
                            arregloGraficaValores2[i] = miLista[i][1]
                            arregloGraficaValores3[i] = miLista[i][2]
                        }
                         
                        def temp
                        for(def i = 0; i < params.int('month')-1; i ++)
                        {
                            temp = arregloGraficaMeses[0]
                            for(def j = 0; j < 11; j++)
                                arregloGraficaMeses[j] = arregloGraficaMeses[j+1]
                            arregloGraficaMeses[11] = temp
                        }
                    }
                    catch(Exception e)
                    {
                        log.error("--------------\n--------------\n--------------\n")
                        log.error "Error: ${e.message}", e
                        log.error("--------------\n--------------\n--------------\n")
                    }
                        
                    xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
                    xml.meses() 
                    {           
                        nombreEjeX1("Combustible")   
                        nombreEjeX2("Energia Termica")    
                        nombreEjeX3("EnergiaElectrica")    
                        for(int i=0; i<12; i++)
                        {
                            mes() 
                            {
                                nombre(arregloGraficaMeses[i])
                                valor(arregloGraficaValores[i])                              
                                valor2(arregloGraficaValores2[i])     
                                valor3(arregloGraficaValores3[i])   
                            }
                        }           
                    }              
                    render(text: writer, contentType: "text/xml", encoding: "UTF-8")
                    return;
            }
        }
                
        def arregloGraficaMeses = arregloResultadoGrafica[0]
        def arregloGraficaValores = arregloResultadoGrafica[1]
        
        if(codigoGrafica=="14")
        arregloGraficaValores = arregloResultadoGrafica[3]
        
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)             
            if(codigoGrafica=="14")
            nombreEjeX2(" ")             
                    
            for(int i=0; i<12; i++){
                mes() {
                    nombre(arregloGraficaMeses[i])
                    valor(arregloGraficaValores[i])                  
                    if(codigoGrafica=="14")
                    valor2("0")                  
                }
            }           
            
        }
              
        //println  request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    @Secured(['permitAll'])
    //Para graficas con dos conjuntos y las fechas (3 arreglso involucrados en total)
    def grafica3Arreglos(){
        
        def arregloResultadoGrafica
        def mensajeEjeX1 
        def mensajeEjeX2 
        def mensajeEjeX3
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)            
        def sitio = Sitio.get(params.sitioId);        
        def codigoGrafica = params?.graficaId        
        if(codigoGrafica){
            if(codigoGrafica=="1")
            {   
                
                arregloResultadoGrafica = CalculosService.graficaEstadoFinancieroIndex(sitio);        
                mensajeEjeX1 = 'Evitado energías alternas'
                mensajeEjeX2 ='Evitado energía térmica'
                 
            }    
            if(codigoGrafica=="5")
            {   
                
                arregloResultadoGrafica = CalculosService.graficaIndexAhorros(sitio);        
                mensajeEjeX1 = 'Evitado eléctrica por energías alternas'
                mensajeEjeX2 ='Evitado energía termica por energías alternas'
                 
            }  
            if(codigoGrafica=="6")
            {                                 
                arregloResultadoGrafica = CalculosService.graficaElectrico(sitio);                             
                mensajeEjeX1 = 'Evitado eléctrica por energías alternas'
                mensajeEjeX2 ='Evitado energía termica por energías alternas'
                 
            }  
            if(codigoGrafica=="12")
            {                
                arregloResultadoGrafica = CalculosService.graficaEnergiaTermicaEnergeticos(sitio);                
                mensajeEjeX1 = 'Sistema cogeneración'
                mensajeEjeX2 ='Sistema térmico'
                 
            }  
            
            if(codigoGrafica=="14")
            {                
                arregloResultadoGrafica = CalculosService.graficaIndexEnergeticos(sitio);                
                mensajeEjeX1 = 'Energía térmica'
                mensajeEjeX2 ='Energía eléctrica'
                 
            }  
            if(codigoGrafica=="15")
            {                
                arregloResultadoGrafica = CalculosService.graficaCombustiblesEnergeticos(sitio);                
                mensajeEjeX1 = 'Combustible Cogeneración'
                mensajeEjeX2 ='Combustible Térmico'
                 
            }  
            if(codigoGrafica=="152")
            {                
                arregloResultadoGrafica = CalculosService.graficaCombustiblesEnergeticos(sitio);                
                mensajeEjeX1 = 'Energía Cogeneración'
                mensajeEjeX2 ='Energía Térmico'
                 
            }  
             
            
        }
                
        def arregloGraficaMeses = arregloResultadoGrafica[0]
        def arregloGraficaValores = arregloResultadoGrafica[1]
        def arregloGraficaValores2 = arregloResultadoGrafica[2]
        if(codigoGrafica=="152"){
            arregloGraficaValores = arregloResultadoGrafica[3]
            arregloGraficaValores2 = arregloResultadoGrafica[4]
        }
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)
            nombreEjeX2(mensajeEjeX2)                
            
            for(int i=0; i<12; i++){
                mes() {
                    nombre(arregloGraficaMeses[i])
                    valor(arregloGraficaValores[i])
                    valor2(arregloGraficaValores2[i])                    
                }
            }           
            
        }
              
        //println  request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    //Usualmente para las de barras que utilizan 4 arreglos, contando los meses.
    @Secured(['permitAll'])    
    def grafica4Arreglos(){
        def arregloResultadoGrafica
        def mensajeEjeX1 
        def mensajeEjeX2 
        def mensajeEjeX3
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)            
        def sitio = Sitio.get(params.sitioId);        
        def codigoGrafica = params?.graficaId
        
        if(codigoGrafica){
            if(codigoGrafica=="1")
            {   
                //en caso de ser costosGlobalesIndex graficaid = 1 (por default se hace esta)        
                arregloResultadoGrafica = CalculosService.graficaIndexCostosGlobales(sitio);  
                mensajeEjeX1 = 'Evitado por energías alternas'
                mensajeEjeX2 = 'Evitado energía térmica por energías alternas'
                mensajeEjeX3 = 'Costo total combustible'
            }
            if(codigoGrafica=="3")
            {
                
                arregloResultadoGrafica = CalculosService.graficaCostoEnergiaTermica(sitio); 
                mensajeEjeX1 = 'S. Cogeneración'
                mensajeEjeX2 = 'S. Térmico'
                mensajeEjeX3 = 'Colector Solar'
            }
            
            if(codigoGrafica=="4")
            {
                
                arregloResultadoGrafica = CalculosService.graficaCostoCombustibles(sitio); 
                mensajeEjeX1 = 'Evitado por energías alternas'
                mensajeEjeX2 = 'Evitado energía térmica por energías alternas'
                mensajeEjeX3 = 'Costo total combustible'
            }
            
            if(codigoGrafica=="7")
            {
                
                arregloResultadoGrafica = CalculosService.graficaTermico(sitio); 
                mensajeEjeX1 = 'Energía térmica aprovechada cog.'
                mensajeEjeX2 = 'Energía térmica aprovechada sist. térmico'
                mensajeEjeX3 = 'Costo evitado colector solar'
            }
            if(codigoGrafica=="8")
            {
                
                arregloResultadoGrafica = CalculosService.graficaIndexEmisiones(sitio); 
                mensajeEjeX1 = 'Sistema cogeneración'
                mensajeEjeX2 = 'Sistema fotovoltaico'
                mensajeEjeX3 = 'Colector solar'
            }
            
            if(codigoGrafica=="13")
            {                
                arregloResultadoGrafica = CalculosService.graficaEnergiaElectricaEnergeticos(sitio);                
                mensajeEjeX1 = 'CFE' 
                mensajeEjeX2 = 'Sistema cogeneración'
                mensajeEjeX3 ='Sistema fotovoltaico'
                
            }  
            
            
        }
        
        def arregloGraficaMeses = arregloResultadoGrafica[0]
        def arregloGraficaValores = arregloResultadoGrafica[1]
        def arregloGraficaValores2 = arregloResultadoGrafica[2]
        def arregloGraficaValores3 = arregloResultadoGrafica[3]
                
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)
            nombreEjeX2(mensajeEjeX2)
            nombreEjeX3(mensajeEjeX3)
            
            for(int i=0; i<12; i++){
                mes() {
                    nombre(arregloGraficaMeses[i])
                  
                    valor(arregloGraficaValores[i])
                    valor2(arregloGraficaValores2[i])
                    valor3(arregloGraficaValores3[i])
                }
            }           
            
        }                      
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    @Secured(['permitAll'])    
    def grafica5Arreglos(){
        def arregloResultadoGrafica
        def mensajeEjeX1 
        def mensajeEjeX2 
        def mensajeEjeX3
        def mensajeEjeX4
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)            
        def sitio = Sitio.get(params.sitioId);        
        def codigoGrafica = params?.graficaId
        
        if(codigoGrafica){
            if(codigoGrafica=="2")
            {   
                //en caso de ser costosGlobalesIndex graficaid = 1 (por default se hace esta)        
                arregloResultadoGrafica = CalculosService.graficaCostoEnergiaElectrica(sitio);  
                mensajeEjeX1 = 'Energía Total'
                mensajeEjeX2 = 'Energía Base'
                mensajeEjeX3 = 'Energía Intermedia'
                mensajeEjeX4 = 'Energía Punta'
            }
          
            
            
        }
        def arregloGraficaMeses = arregloResultadoGrafica[0]
        def arregloGraficaValores = arregloResultadoGrafica[1]
        def arregloGraficaValores2 = arregloResultadoGrafica[2]
        def arregloGraficaValores3 = arregloResultadoGrafica[3]
        def arregloGraficaValores4 = arregloResultadoGrafica[4]
                
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)
            nombreEjeX2(mensajeEjeX2)
            nombreEjeX3(mensajeEjeX3)
            nombreEjeX4(mensajeEjeX4)
            
            for(int i=0; i<12; i++){
                mes() {
                    nombre(arregloGraficaMeses[i])                                        
                    valor(arregloGraficaValores[i])
                    valor2(arregloGraficaValores2[i])
                    valor3(arregloGraficaValores3[i])
                    valor4(arregloGraficaValores4[i])
                }
            }           
            
        }   
        
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    //Para tiempo real
    @Secured(['permitAll'])
    //Para graficas con dos conjuntos y las fechas (3 arreglso involucrados en total)
    def grafica5ArreglosTR(){        
        def arregloResultadoGrafica
        def mensajeEjeX1 
        def mensajeEjeX2 
        def mensajeEjeX3
        def mensajeEjeX4
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)            
        def fuente = FuenteSitio.get(params.fuenteId);  
        
        def codigoGrafica = params?.graficaId        
        if(codigoGrafica){
            if(codigoGrafica=="17")
            {   
                
                arregloResultadoGrafica = TiempoRealService.graficasEnergiaTermicaFlujoAgua(fuente);        
                mensajeEjeX1 = 'Sistema Termico'
                mensajeEjeX2 ='Intercambiador T. Inercia'
                mensajeEjeX3 ='Placas T. Inercia'
                mensajeEjeX4 ='Placas T. Servicio'
                 
            }                  
        }
                        
        def arregloGraficaValores = arregloResultadoGrafica[0]
        def arregloGraficaValores2 = arregloResultadoGrafica[1]
        def arregloGraficaValores3 = arregloResultadoGrafica[2]
        def arregloGraficaValores4 = arregloResultadoGrafica[3]
        
        
        def fechaFormato = TiempoRealService.getFecha()
        
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)
            nombreEjeX2(mensajeEjeX2)                
            nombreEjeX3(mensajeEjeX3)
            nombreEjeX4(mensajeEjeX4)
                        
            mes() {
                nombre(fechaFormato)
                valor(arregloGraficaValores)
                valor2(arregloGraficaValores2)                    
                valor3(arregloGraficaValores3)
                valor4(arregloGraficaValores4)
            }
                  
            
        }
              
        //println  request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    
    @Secured(['permitAll'])
    //Para graficas con dos conjuntos y las fechas (3 arreglso involucrados en total)
    def grafica4ArreglosTR(){        
        def arregloResultadoGrafica
        def mensajeEjeX1 
        def mensajeEjeX2 
        def mensajeEjeX3
        def mensajeEjeX4
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)            
        def fuente = FuenteSitio.get(params.fuenteId);  
        
        def codigoGrafica = params?.graficaId        
        if(codigoGrafica){
            if(codigoGrafica=="172")
            {   
                
                arregloResultadoGrafica = TiempoRealService.graficasEnergiaTermicaTemperaturaEntradaCog(fuente);        
                mensajeEjeX1 = 'Economizador'
                mensajeEjeX2 ='Vaporizador'
                mensajeEjeX3 ='Supercalentador'
                mensajeEjeX4 = " "
                 
            }   
            
            if(codigoGrafica=="173")
            {   
                
                arregloResultadoGrafica = TiempoRealService.graficasEnergiaTermicaTemperaturaAgua(fuente);        
                mensajeEjeX1 = 'Int. del sistema de cogeneración'
                mensajeEjeX2 ='Tanque de servicios cogeneración'
                mensajeEjeX3 ='Tanque inercia cogeneración'
                mensajeEjeX4 = " "
                 
            }   
        }
                        
        def arregloGraficaValores = arregloResultadoGrafica[0]
        def arregloGraficaValores2 = arregloResultadoGrafica[1]
        def arregloGraficaValores3 = arregloResultadoGrafica[2]        
        
        
        def fechaFormato = TiempoRealService.getFecha()
        
        xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
        xml.meses() {           
            nombreEjeX1(mensajeEjeX1)
            nombreEjeX2(mensajeEjeX2)                
            nombreEjeX3(mensajeEjeX3) 
            nombreEjeX4(mensajeEjeX4)    
                        
            mes() {
                nombre(fechaFormato)
                valor(arregloGraficaValores)
                valor2(arregloGraficaValores2)                    
                valor3(arregloGraficaValores3)  
                valor4("0.0")
            }
                  
            
        }
              
        //println  request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
        render(text: writer, contentType: "text/xml", encoding: "UTF-8")
    }
    
    
    //**** Métodos para generar los pdf solicitados según el nombre *********
    
    def createEstadoFinancieroIndex = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/grafica1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=1";          
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createFlujoDeEfectivo = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas2Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=16"; 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createCostosGlobalesIndex = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=1";                    
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createCostosTermicos = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=3";             
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createCostosElectricos = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras5Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica5Arreglos.xml?sitioId="+sitio.id+"&graficaId=2";   
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createCostosCombustibles = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=4";          
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    
    def createAhorrosGlobales = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras3Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");
            // println "DATA: " + pathReporte
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=5";         
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    
    def createAhorrosElectricos = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/grafica1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");           
            params.SUBREPORT_DIR = pathReporte        
            
            //Para pedir los datos para la grafica
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=6";         
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createAhorrosTermicos = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas4Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=7";   
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createEmisionesIndex = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas4Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=8"; 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createEmisionesCogeneracion = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas2Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=9"; 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createEmisionesFotovoltaico = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas2Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=10"; 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createEmisionesColectorSolar = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaLineas2Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=11"; 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
                    
    def createEnergeticosETermica = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras3Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");
            // println "DATA: " + pathReporte
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=12";        
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }    
    
    def createEnergeticosIndex = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/grafica1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");
            // println "DATA: " + pathReporte
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=14";       
            params.URL_GRAFICA2 =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=14";       
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    
    def createEnergeticosEElectrica = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras4Arreglos.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");
            // println "DATA: " + pathReporte
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica4Arreglos.xml?sitioId="+sitio.id+"&graficaId=13";        
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    
    def createEnergeticosCombustibles = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/grafica1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");           
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=15";    
            params.URL_GRAFICA2 =  pathGraficaData + "/PdfUtil/grafica3Arreglos.xml?sitioId="+sitio.id+"&graficaId=152";    
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
                 
    
    
    def createMonTRealIndex = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/grafica1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");
            //println "DATA: " + pathReporte
            params.SUBREPORT_DIR = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()
            //println "PATH: " + pathGraficaData + "/PdfUtil/grafica2.xml"
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2.xml" 
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    def createMTRealETermica = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        def fuente = FuenteSitio.get(params.fuenteIdPDF)       
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            params.inercia = TiempoRealService.temperaturaTanqueInerciaDT030(fuente)
            params.servicios = TiempoRealService.auxiliarDT031(fuente)
            params.termico  = TiempoRealService.auxiliarDT044(fuente)
            params.condensados  = TiempoRealService.auxiliarDT0505(fuente)
            params.vapor = TiempoRealService.auxiliarDT05011(fuente)
            
            params.inercia2 = TiempoRealService.auxiliarDT043(fuente)
            params.servicios2 = TiempoRealService.auxiliarDT042(fuente)
            params.condensados2 = TiempoRealService.auxiliarDT0507(fuente)
            params.vapor2 = TiempoRealService.auxiliarDT05013(fuente)
                
            params.temperaturaAireEntrada = TiempoRealService.auxiliarDT034(fuente)
            params.temperaturaAireSalida = TiempoRealService.auxiliarDT035(fuente)
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras5ArreglosTR.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");           
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()                        
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica5ArreglosTR.xml?fuenteId="+fuente.id+"&graficaId=17";    
            
            
            params.URL_GRAFICA2 =  pathGraficaData + "/PdfUtil/grafica4ArreglosTR.xml?fuenteId="+fuente.id+"&graficaId=172";    
            params.URL_GRAFICA3 =  pathGraficaData + "/PdfUtil/grafica4ArreglosTR.xml?fuenteId="+fuente.id+"&graficaId=173";    
            
            
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
     def createMTRealEElectrica = {
        
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split(" ");
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[3]
        
        def fechaFin = params.fechaFinPDF.split(" ");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[3]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        def fuente = FuenteSitio.get(params.fuenteIdPDF)       
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            
            params.cogeneracion = TiempoRealService.auxiliarDT010(fuente)
            params.fotovoltaico= TiempoRealService.auxiliarDT011(fuente)
            params.cfe = TiempoRealService.auxiliarDT009(fuente)         
            params.potencia =  params.cogeneracion +  params.fotovoltaico +  params.cfe
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras5ArreglosTR.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");           
            params.SUBREPORT_DIR = pathReporte                    
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()                        
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica5ArreglosTR.xml?fuenteId="+fuente.id+"&graficaId=17";    
                                    
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
            render("Error al generar pdf")
    }
    
    
    // NUEVO Cogeneración eficiente 
    def createCogeneracionEficienteIndex = 
    {    
        println "PARAMETROS!!!"
        println params
       
        def principal = springSecurityService.principal
        def usuario = Usuario.get(principal.id)
              
        def fechaIni = params.fechaIniPDF.split("-");
        def year = fechaIni[0]
        def month = fechaIni[1]
        fechaIni = fechaIni[1] + "/" + fechaIni[2] + "/" + fechaIni[0]
        
        def fechaFin = params.fechaFinPDF.split("-");
        fechaFin = fechaFin[1] + "/" + fechaFin[2] + "/" + fechaFin[0]
        
        def sitio = Sitio.get(params.sitioIdPDF)
        def cliente = Cliente.get(sitio.clienteId)
        
        def listaUser = Usuario.list()
        
        params.direccionSitio = (sitio.calle + sitio.numeroExt + sitio.colonia + ", "+sitio.pais)        
        params.nombreUsuario= usuario.displayName
        params.emailContacto= usuario.email
        params.nombreSitio= sitio.nombre
        params.intervaloTiempo= (fechaIni + "-" +fechaFin)       
        params.nombreCliente= cliente.razonSocial         
       
        println "PARAMETROS!!!"
        println params
        
        def lista = []                
        lista.add(listaUser.get(0))
      
        if(listaUser)
        { 
            //Se modifica el path para pasarle una ruta absoluta de cada imagen. Se cambian los \ por \\ para iReports
            
            String pathImagenAviso = grailsAttributes.getApplicationContext().getResource("/images/avisoPNG.png").getFile()   
            pathImagenAviso = pathImagenAviso.replaceAll("\\\\", "\\\\\\\\");
            params.URL_AVISO_IMG = pathImagenAviso
            
            String pathImagenLogo = grailsAttributes.getApplicationContext().getResource("/images/mainLogo.jpg").getFile() 
            pathImagenLogo = pathImagenLogo.replaceAll("\\\\", "\\\\\\\\");
            params.URL_LOGO_IMG = pathImagenLogo    
            
            String pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras1Barra.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR = pathReporte        
            
            pathReporte = grailsAttributes.getApplicationContext().getResource("/reports/graficaBarras1.jasper").getFile() 
            pathReporte = pathReporte.replaceAll("\\\\", "\\\\\\\\");            
            params.SUBREPORT_DIR2 = pathReporte        
            
            String pathGraficaData = request.scheme + "://" + request.serverName + ":" + request.serverPort + request.getContextPath()            
            params.URL_GRAFICA =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=100&year="+year+"&month="+month;   
            
             params.URL_GRAFICA2 =  pathGraficaData + "/PdfUtil/grafica2Arreglos.xml?sitioId="+sitio.id+"&graficaId=101&year="+year+"&month="+month;  
            
          
            println params.URL_GRAFICA
            chain(controller:'jasper',action:'index',model:[data:lista],params:params)  
        }
        else
        render("Error al generar pdf")
        
    }
    
    /* Inicio de códigos de Ariel */
    //Crea una cadena con un numero, similar al Integer.toString("00");
    //Es para asegurar que van a jalar las fechas por que van a jalar...
    def aCadena(number)
    {
        if (number<10)
            return number +""
        return "0" + number    
    }
    
    //Crea
    def rotateMonths(arr,n){
        def r = arr
        for (def i = 1; i<n; i++){
            r = rotateJuanMonth(r)
        }
        return r
    }
    
    def rotateJuanMonth(arr){
      def r = arr
      def i = 0
      def temp = arr[0];
      for (i = 0; i < 11; i++)
         r[i] = r[i+1];
      r[i] = temp;
    }
 /* Fin de los códigos de Ariel */
}
