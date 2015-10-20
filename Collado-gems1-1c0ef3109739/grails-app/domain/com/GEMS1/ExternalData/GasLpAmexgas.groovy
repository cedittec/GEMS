package com.GEMS1.ExternalData

class GasLpAmexgas {
    
    String estado   
    double precioKg
    double precioLt
    
    Date lastUpdated
    Date dateCreated

    static constraints = {
        estado maxSize:25
    }
}
