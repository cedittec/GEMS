package com.GEMS1

class TelefonoSitio {
    
    String tipoTelefono;
    String numTelefono;    
    Date dateCreated;
    Date lastUpdated;
    String displayName;
    static belongsTo = [sitio:Sitio]
    
    static constraints = {
        tipoTelefono maxSize:16
        numTelefono maxSize:12        
        displayName nullable:true
    }
    
    def beforeInsert() {
        displayName = numTelefono
      
    }
    def beforeUpdate() {
        displayName = numTelefono
      
    }
    
}
