package com.GEMS1

class DireccionCliente {

    String numeroExt
    String numeroInt
    int codigoPostal
    String calle
    String colonia
    String estado
    String ciudad
    String pais
    Date dateCreated
    static belongsTo = [cliente:Cliente]
    
    static constraints = {
        calle maxSize:50
        colonia maxSize:50
        estado maxSize: 25
        ciudad maxSize: 35
        pais maxSize: 20
        numeroExt nullable:true
        numeroInt nullable:true
    }
}
