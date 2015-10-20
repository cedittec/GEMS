package gems1

class DatosPaginasExternasJob {
    def GasLpService 
    def CFEService
    def GasNaturalService
    static triggers = {
     //    simple repeatInterval: 1000
     //    cronExpression: Sec Min Hr DiaDelMes Mes DiaDeLaSemana
     // cron name:'extraccionDatos',  cronExpression: '0 10 12 21 * ?'
      cron name:'extraccionDatos',  cronExpression: '0 5 5 1 * ?'
    }
    

    def execute() {
        GasLpService.serviceMethod()
        CFEService.serviceMethod()
        GasNaturalService.serviceMethod()
        println "****Operacion automatica Realizada****"
    }
}
