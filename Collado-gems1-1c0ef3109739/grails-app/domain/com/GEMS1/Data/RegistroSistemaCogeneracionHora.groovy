package com.GEMS1.Data

class RegistroSistemaCogeneracionHora {

    static belongsTo = [fuenteSitio:com.GEMS1.FuenteSitio]
    
    float energiaTermicaSalida
    float flujoAireEntrada
    float flujoAireSalida
    float tempAireSalida
    float voltajeSalida
    float corrienteSalida
    float potenciaSalida
    float energiaSalida
    float voltajeCfe
    float corrienteCfe
    float potenciaCfe
    float energiaCfe
    float totalEnergy
    float fuelEnergyFlow
    float controlBoardPanel
    float powerDemand
    float engineSpeed
    float turbineExhaustTempAverage
    float combustorStarts
    float numberOfStarts
    float testSetpoint
    float runningHours
    float bearingSeconds
    float bearingMinutes
    float combustorHours
    float combustorMinutes
    float combustorSeconds
    float dcBusVoltage
    float outputAcFrequency
    float outputPhase_a_current
    float outputPhase_b_current
    float outputPhase_c_current
    float phase_a_powerVoltage
    float phase_b_powerVoltage
    float phase_c_powerVoltage
    float phase_a_powerAverage
    float phase_b_powerAverage
    float phase_c_powerAverage
    float total_3_phase_current
    float apparentPower
    float powerFactor
    float nivelHumedad
    float tempAmbiente
    float posicionDiverter
    float presionCombustible
    float temperaturaCombustible
    Date dateCreated
    
    
    
    static constraints = {
    }
}
