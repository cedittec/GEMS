package com.GEMS1.Scada

class RegistroColectorSolar {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float tempAguaEntradaModulo
    float tempAguaSalidaModulo
    float tempAguaAlberca
    float flujoAgua
    float energia
    float radiacionSolar
    float nivelHumedad
    float tempAmbiente
    float flujoAire
    float tempAlberca
    Date dateCreated
    
    static constraints = {
    }
}
