package com.GEMS1.Scada
class RegistroTanqueCondensados {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    float presion
    float nivel
    float temp
    Date dateCreated
    
    static constraints ={ 
        temp nullable:true
    }
} 
