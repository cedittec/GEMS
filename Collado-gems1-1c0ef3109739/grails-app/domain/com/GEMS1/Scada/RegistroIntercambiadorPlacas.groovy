package com.GEMS1.Scada

class RegistroIntercambiadorPlacas {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float tempAguaEntradaTanqueInercia
    float tempAguaSalidaTanqueInercia
    float tempAguaEntradaTanqueServicio
    float tempAguaSalidaTanqueServicio
    float flujoAguaTanqueInercia
    float flujoAguaTanqueServicio
    Date dateCreated
    
    static constraints = {
    }
}
