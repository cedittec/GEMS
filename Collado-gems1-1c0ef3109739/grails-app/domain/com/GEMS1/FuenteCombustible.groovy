package com.GEMS1

class FuenteCombustible {
    
    long combustibleFuenteId
    long fuenteDeEnergiaId
  //  static belgonsTo = [combustibleFuente:CombustibleFuente, fuenteDeEnergia:FuenteDeEnergia]
    static constraints = {
        combustibleFuenteId unique:'fuenteDeEnergiaId'
    }
}
