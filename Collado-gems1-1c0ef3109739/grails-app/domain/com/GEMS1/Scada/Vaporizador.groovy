package com.GEMS1.Scada
class Vaporizador {

    static belongsTo = [fuenteSitio: com.GEMS1.FuenteSitio]
    
    float tempEntrada
    float tempSalida
    float flujo
    Date dateCreated
    
    static constraints = {
    }
}
