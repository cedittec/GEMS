package com.GEMS1

class UnidadMedidaCombustible {
    
    //static belongsTo = [unidadDeMedida:UnidadDeMedida, combustibleFuente:CombustibleFuente]
    long unidadMedidaId
    long combustibleFuenteId
    
    static constraints = {
           unidadMedidaId unique : 'combustibleFuenteId'
    }
}
 