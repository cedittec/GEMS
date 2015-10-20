package com.GEMS1

class UsuarioSitio {    
    long sitioId
    
    static belongsTo = [usuario:com.GEMS1.Usuario]
    Date dateCreated
    
    static constraints = {
    }
}
