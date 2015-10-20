package gems1
import com.GEMS1.*
import com.GEMS1.Scada.*
import grails.transaction.Transactional
import groovy.time.TimeCategory
import java.text.SimpleDateFormat
import com.GEMS1.ExternalData.*
import java.util.concurrent.TimeUnit
import java.util.Calendar;

@Transactional
class TiempoRealService {

    def serviceMethod() {

    }
    
    def getFecha()
    {
        Date fecha = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);  
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");                    
        String formatted = format1.format(cal.getTime());
        return formatted
    }
    
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
            arregloResultado[1] += registro.nivelHumedad
            arregloResultado[2] += registro.tempAmbiente
            
        }
        
        registro = RegistroColectorSolar.find("From RegistroColectorSolar as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[fuenteSitio])                
        if(registro)
        {
            
            arregloResultado[0] += registro.radiacionSolar
            
        }
        
        
        return arregloResultado 
    }
    
}
