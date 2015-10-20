package com.GEMS1

class Alarma {    
    Date dateCreated
    long sitioId
    String claveAlarma
    byte visto       
    
    static constraints = {
        claveAlarma maxSize: 5
        visto defaultValue: 0
    }
}
