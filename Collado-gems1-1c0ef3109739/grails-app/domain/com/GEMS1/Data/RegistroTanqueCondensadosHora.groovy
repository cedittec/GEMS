package com.GEMS1.Data

class RegistroTanqueCondensadosHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    float presion
    float nivel
    float temp
    Date dateCreated
    
    static constraints = {
        temp nullable:true
    }
}
