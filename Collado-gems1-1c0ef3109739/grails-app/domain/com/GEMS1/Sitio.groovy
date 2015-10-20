package com.GEMS1

class Sitio {
    String nombre
    int numeroExt
    String numeroInt
    int codigoPostal
    String calle
    String colonia
    String ciudad
    String estado
    String pais
    double precioGasLp
    byte planGasLp
    
    double precioGasNatural
    byte planGasNatural
    
    double precioDiesel
    byte planDiesel
    
    float latitud
    float longitud
    long regionId
    long tarifaId
    String displayName
    Date dateCreated
    Date lastUpdated
    long clienteId
    
    float poderCalorificoGasLp
    float poderCalorificoGasNatural
    float poderCalorificoDiesel
    
    double inversion
    
    String estatus 
    
    static hasMany = [ telefono:TelefonoSitio, fuenteSitio:FuenteSitio]
   
    
    static constraints = {
        inversion defaultValue:1000.0
        precioGasNatural nullable:true
        precioDiesel nullable:true
        precioGasLp nullable:true
        
        planGasLp defaultValue: "0"
        planDiesel defaultValue: "0"
        planGasNatural defaultValue: "0"
        
        poderCalorificoGasLp defaultValue: 0.0
        poderCalorificoGasNatural defaultValue: 0.0
        poderCalorificoDiesel defaultValue: 0.0
        
        numeroExt nullable: true
        numeroInt nullable: true
        displayName nullable:true
        estatus nullable:true
        nombre unique: true
        
    }
    
    def afterInsert() {
       def clienteDueno = Cliente.get(clienteId)
       def usuario = Usuario.get(clienteDueno.responsableId)  
       usuario.addToUsuarioSitio(sitioId: this.id).save()
     }
     
    def beforeInsert()
    {
        estatus = "activo"
    }
}
