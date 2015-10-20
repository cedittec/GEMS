package com.GEMS1.Scada

class RegistroSistemaTermico {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
     
    float tempAguaEntradaModulo
    float tempAguaSalidaModulo
    float tempAguaTanque
    float flujoAgua
    float voltaje
    float corriente
    float potencia
    float energia
    float radiacionSolar
    float nivelHumedad
    float tempAmbiente
    float flujoCombustible
    float presionCombustible
    float temperaturaCombustible
    float nivelTanque
    Date dateCreated
    
    static constraints = {
        nivelTanque nullable:true
    }
}
