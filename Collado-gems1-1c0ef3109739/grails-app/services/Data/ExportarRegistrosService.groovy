package Data

import com.GEMS1.FuenteSitio
import com.GEMS1.Data.*
import com.GEMS1.Scada.*
import grails.transaction.Transactional

@Transactional
class ExportarRegistrosService {

    def serviceMethod() {
        
        def sitiosActivos = getSitiosActivos()
        if(sitiosActivos)
        {
            for(sitio in sitiosActivos)
            {
                println "Sitio "+sitio.nombre + " exportando a sus tablas"
                exportar(sitio)
            }
        }
    }
    
    def getSitiosActivos()
    {
        def listaSitios = FuenteSitio.findAllByEstatus("activo")
        return listaSitios
    }
    
    def exportar(thisFuente)
    {
        
        //def thisFuente = FuenteSitio.findById(Integer.parseInt(params?.fuenteId))
        int contador = 0;
        def listaInserciones = []
        //COLECTOR SOLAR
        def registro = RegistroColectorSolar.find("From RegistroColectorSolar as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroColectorSolarHora nvoRegistro = new RegistroColectorSolarHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroColectorSolarHora.executeUpdate( """UPDATE RegistroColectorSolarHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroColectorSolarHora")
            
        /*
        RegistroColectorSolar.executeUpdate("delete RegistroColectorSolar c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
        */
            
        }
        
        //INTERCAMBIADOR
        registro = RegistroIntercambiador.find("From RegistroIntercambiador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroIntercambiadorHora nvoRegistro = new RegistroIntercambiadorHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroIntercambiadorHora.executeUpdate( """UPDATE RegistroIntercambiadorHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroIntercambiadorHora")
            
            /*
            RegistroIntercambiador.executeUpdate("delete RegistroIntercambiador c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
            
        }
        
        //INTERCAMBIADOR PLACAS
        registro = RegistroIntercambiadorPlacas.find("From RegistroIntercambiadorPlacas as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroIntercambiadorPlacasHora nvoRegistro = new RegistroIntercambiadorPlacasHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroIntercambiadorPlacasHora.executeUpdate( """UPDATE RegistroIntercambiadorPlacasHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroIntercambiadorPlacasHora")
            /*
             RegistroIntercambiadorPlacas.executeUpdate("delete RegistroIntercambiadorPlacas c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
            
        }
        
         //Sistema de Cogeneracion
        registro = RegistroSistemaCogeneracion.find("From RegistroSistemaCogeneracion as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroSistemaCogeneracionHora nvoRegistro = new RegistroSistemaCogeneracionHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroSistemaCogeneracionHora.executeUpdate( """UPDATE RegistroSistemaCogeneracionHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroSistemaCogeneracionHora")
            
            /*
             RegistroSistemaCogeneracion.executeUpdate("delete RegistroSistemaCogeneracion c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
         //Sistema Fotovoltaico
        registro = RegistroSistemaFotovoltaico.find("From RegistroSistemaFotovoltaico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroSistemaFotovoltaicoHora nvoRegistro = new RegistroSistemaFotovoltaicoHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroSistemaFotovoltaicoHora.executeUpdate( """UPDATE RegistroSistemaFotovoltaicoHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
             listaInserciones.add("RegistroSistemaFotovoltaicoHora")
            /*
            RegistroSistemaFotovoltaico.executeUpdate("delete RegistroSistemaFotovoltaico c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
            
        }
        
        //Sistema Termico
        registro = RegistroSistemaTermico.find("From RegistroSistemaTermico as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroSistemaTermicoHora nvoRegistro = new RegistroSistemaTermicoHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroSistemaTermicoHora.executeUpdate( """UPDATE RegistroSistemaTermicoHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
             listaInserciones.add("RegistroSistemaTermicoHora")
             
            /*
             RegistroSistemaTermico.executeUpdate("delete RegistroSistemaTermico c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
        //Tanque Condensados
        registro = RegistroTanqueCondensados.find("From RegistroTanqueCondensados as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroTanqueCondensadosHora nvoRegistro = new RegistroTanqueCondensadosHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroTanqueCondensadosHora.executeUpdate( """UPDATE RegistroTanqueCondensadosHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroTanqueCondensadosHora")
            /*
             RegistroTanqueCondensados.executeUpdate("delete RegistroTanqueCondensados c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
        //Tanque INERCIA
        registro = RegistroTanqueInercia.find("From RegistroTanqueInercia as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroTanqueInerciaHora nvoRegistro = new RegistroTanqueInerciaHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroTanqueInerciaHora.executeUpdate( """UPDATE RegistroTanqueInerciaHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
            listaInserciones.add("RegistroTanqueInerciaHora")
            
            /*
            RegistroTanqueInercia.executeUpdate("delete RegistroTanqueInercia c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
        //Tanque SERVICIO
        registro = RegistroTanqueServicio.find("From RegistroTanqueServicio as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroTanqueServicioHora nvoRegistro = new RegistroTanqueServicioHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroTanqueServicioHora.executeUpdate( """UPDATE RegistroTanqueServicioHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
             listaInserciones.add("RegistroTanqueServicioHora")
             
            /*
              RegistroTanqueServicio.executeUpdate("delete RegistroTanqueServicio c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
        //Tanque VAPOR
        registro = RegistroTanqueVapor.find("From RegistroTanqueVapor as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            RegistroTanqueVaporHora nvoRegistro = new RegistroTanqueVaporHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            RegistroTanqueVaporHora.executeUpdate( """UPDATE RegistroTanqueVaporHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
             listaInserciones.add("RegistroTanqueVaporHora")
             
            /*
            RegistroTanqueVapor.executeUpdate("delete RegistroTanqueVapor c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
         //SUPERCALENTADOR
        registro = Supercalentador.find("From Supercalentador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            SupercalentadorHora nvoRegistro = new SupercalentadorHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            SupercalentadorHora.executeUpdate( """UPDATE SupercalentadorHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
             listaInserciones.add("SupercalentadorHora")
             
            /*
            Supercalentador.executeUpdate("delete Supercalentador c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
         //VAPORIZADOR
        registro = Vaporizador.find("From Vaporizador as reg where reg.fuenteSitio = ? ORDER BY dateCreated DESC",[thisFuente])                
        if(registro)
        {
            VaporizadorHora nvoRegistro = new VaporizadorHora(registro.properties)            
            nvoRegistro.save(flush:true)            
            VaporizadorHora.executeUpdate( """UPDATE VaporizadorHora SET dateCreated = :date WHERE id = :something""",
            [date:registro.dateCreated, something: nvoRegistro.id])
            contador++;
              listaInserciones.add("VaporizadorHora")
              
            /*
            Vaporizador.executeUpdate("delete Vaporizador c where c.fuenteSitio = :anterior",
                       [anterior: thisFuente])
                       */
        }
        
        
        println "Se han realizado: "
        println "Fuente: \n"+ thisFuente.id + " registros insertados: \n" + contador + " En: \n" + listaInserciones
        
      
    }
    
}
