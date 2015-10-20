package com.GEMS1

class TelefonoUsuario {
    
    String tipoTelefono;
    String numTelefono;
    Date dateCreated;
    Date lastUpdated;
    String displayName;
    static belongsTo = [usuario:Usuario]
    
    
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
