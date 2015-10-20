package com.GEMS1

class Servicio {

    String nombre
    String descripcion
    //static hasMany = [fuenteServicioSitio:FuenteServicioSitio]
    static constraints = {
        nombre maxSize:35        
    }
}
