package com.GEMS1

class TelefonoCliente {
    
    String tipoTelefono;
    String numTelefono;    
    Date dateCreated;
    Date lastUpdated;
    String displayName;
    static belongsTo = [cliente:Cliente]
    
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
