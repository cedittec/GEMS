package com.GEMS1.ExternalData

class GasNatural {

    double precio
    String precioUnidad
    Date dateCreated
    Date lastUpdated
    static constraints = {
        precioUnidad maxSize:7
        precioUnidad nullable: true
    }
}
