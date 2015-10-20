package com.GEMS1

class FuenteServicioSitio {

    //static belongsTo = [fuenteSitio:FuenteSitio, servicio:Servicio]
    long fuenteSitioId
    long servicioId
    Date dateCreated
    static constraints = {
        fuenteSitioId unique : 'servicioId'
    }
}
