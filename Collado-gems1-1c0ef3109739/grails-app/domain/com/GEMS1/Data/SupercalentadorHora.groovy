package com.GEMS1.Data

class SupercalentadorHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float tempEntrada
    float tempSalida
    float flujo
    Date dateCreated
    
    static constraints = {
    }
}
