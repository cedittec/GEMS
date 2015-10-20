package com.GEMS1.ExternalData

class RegionCfe {
    String nombre
    Date lastUpdated
    Date dateCreated
    
   // static hasMany = [sitio:com.GEMS1.Sitio]
    static constraints = {
        nombre maxSize:22
    }
}

