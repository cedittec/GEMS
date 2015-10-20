package com.GEMS1

class UnidadDeMedida {
    
    String unidadDeMedida
    String abreviatura
    
    //static hasMany = [ unidadMedidaCombustible:UnidadMedidaCombustible]
    
    static constraints = {  
        unidadDeMedida maxSize: 50
        abreviatura maxSize: 10
    }
}
