package com.GEMS1.Data

class RegistroTanqueVaporHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float temp
    float nivel
    float tempEntradaVaporizador
    float presionTuberiaDeVapor
    float presion
    Date dateCreated
    
    static constraints = {
    }
}
