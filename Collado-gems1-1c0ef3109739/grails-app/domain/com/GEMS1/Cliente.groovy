package com.GEMS1

class Cliente {
    
    String razonSocial
    String rfc
    Date dateCreated
    long responsableId
    //static belongsTo = [clienteResponsable:Usuario]
    static hasMany = [sitio:Sitio, telefono: TelefonoCliente, direccionCliente:DireccionCliente]

    static constraints = {
    }
}
