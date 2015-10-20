package com.GEMS1.Scada

class RegistroSistemaFotovoltaico {
    
    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float voltajeCa
    float corrienteCa
    float potenciaCa
    float energiaCa
    float voltajeCd
    float corriendeCd
    float potenciaCd
    float energiaCd
    float temp
    float radiacionSolar
    float nivelHumedad
    float tempAmbiente
    float flujoAire
    Date dateCreated
    
    static constraints = {
    }
}
