package com.GEMS1.Data

class RegistroIntercambiadorHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float energiaTermicaEntrada
    float energiaTermicaSalida
    float tempAireEntrada
    float tempAireSalida
    float tempAguaEntrada
    float tempAguaSalida
    float flujoAireEntrada
    float flujoAguaEntrada
    Date dateCreated
    
    
    static constraints = {
    }
}
