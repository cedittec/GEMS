package com.GEMS1

class AlarmaUsuario {

    long sitioId 
    long usuarioId
    long alarmaId
    byte visto 
    Date dateCreated
    static constraints = {
        visto defaultValue : 0
        
    }
}
