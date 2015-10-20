package com.GEMS1.ExternalData

class TarifaRegionCfe {

    
    
    long regionId
    long tarifaId
    
    double demanda
    double energia
    double demandaFacturable
    double energiaPunta
    double energiaBase
    double energiaIntermedia
    int ano
    
    String mes    
    String demandaUnidad
    String energiaUnidad
    String demandaFacturableUnidad
    String energiaPuntaUnidad
    String energiaIntermediaUnidad
    String energiaBaseUnidad
    Date dateCreated
    
    static constraints = {
        demanda nullable:true
        energia nullable:true
        demandaFacturable nullable:true
        energiaPunta nullable:true
        energiaBase nullable:true
        energiaIntermedia nullable:true
        demandaUnidad nullable:true, maxSize:7
        energiaUnidad nullable:true, maxSize:7
        demandaFacturableUnidad nullable:true, maxSize:7
        energiaPuntaUnidad nullable:true, maxSize:7
        energiaIntermediaUnidad nullable:true, maxSize:7
        energiaBaseUnidad nullable:true, maxSize:7
    }
}
