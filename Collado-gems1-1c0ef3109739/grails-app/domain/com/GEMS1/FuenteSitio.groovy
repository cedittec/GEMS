package com.GEMS1
import com.GEMS1.Scada.*
import com.GEMS1.Data.*

class FuenteSitio {
    
    static belongsTo = [sitio:Sitio]
    long fuenteDeEnergiaId
    long combustibleFuenteId
    long unidadDeMedidaId
 
    float capacidadTanque
    float capacidadGeneracionEnergia
    String nombre
    String estatus
    int cantidad
    String modelo
    Date dateCreated
    
    static hasMany = [
                      
                      registroTanqueCondensados:RegistroTanqueCondensados,
                      registroTanqueVapor:RegistroTanqueVapor,
                      vaporizador:Vaporizador,
                      supercalentador:Supercalentador,
                      registroTanqueInercia:RegistroTanqueInercia,
                      registroTanqueServicio:RegistroTanqueServicio,
                      registroSistemaFoltovoltaico:RegistroSistemaFotovoltaico,
                      registroSistemaTermico:RegistroSistemaTermico,
                      registroSistemaCogeneracion:RegistroSistemaCogeneracion,
                      
                      registroIntercambiador:RegistroIntercambiador,
                      registroIntercambiadorPlacas:RegistroIntercambiadorPlacas,
                      
        
                      registroTanqueCondensadosHora:RegistroTanqueCondensadosHora,
                      registroTanqueVaporHora:RegistroTanqueVaporHora,
                      vaporizadorHora:VaporizadorHora,
                      supercalentadorHora:SupercalentadorHora,
                      registroTanqueInerciaHora:RegistroTanqueInerciaHora,
                      registroTanqueServicioHora:RegistroTanqueServicioHora,
                      registroSistemaFoltovoltaicoHora:RegistroSistemaFotovoltaicoHora,
                      registroSistemaTermicoHora:RegistroSistemaTermicoHora,
                      registroSistemaCogeneracionHora:RegistroSistemaCogeneracionHora,
                      registroIntercambiadorHora:RegistroIntercambiadorHora,
                      registroIntercambiadorPlacasHora:RegistroIntercambiadorPlacasHora
                      
                       ]
    
    static constraints = {
        
        estatus nullable :true
        modelo nullable : true
    }
    
    def beforeInsert()
    {
        estatus = "activo"
    }
    
}
