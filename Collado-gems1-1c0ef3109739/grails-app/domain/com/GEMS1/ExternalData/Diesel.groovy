package com.GEMS1.ExternalData

class Diesel {

    double precio
    String precioUnidad
    Date dateCreated    
    
    static constraints = {
        precioUnidad nullable:true, defaultValue: "Litros"   
    }
}
