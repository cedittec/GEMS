package com.GEMS1.Data

class RegistroTanqueServicioHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float nivel
    float presion
    float tempAguaTanque
    float tempAguaEntrada
    float tempAguaSalida
    Date dateCreated
    static constraints = {
    }
}
