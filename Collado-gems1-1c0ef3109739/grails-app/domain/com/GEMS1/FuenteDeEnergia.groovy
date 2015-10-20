package com.GEMS1

class FuenteDeEnergia {

    String tipo
    String modelo
    String descripcion
   // static hasMany = [fuenteSitio:FuenteSitio]
    
    static constraints = {
        tipo maxSize: 35
        modelo maxSize:20
        descripcion maxSize:255
        
    }
}
