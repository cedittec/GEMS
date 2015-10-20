package gems1



class ExportarRegistrosJob {
    
    def ExportarRegistrosService
    static triggers = {
       //cronExpression: Sec Min Hr DiaDelMes Mes DiaDeLaSemana
       cron name:'exportacionRegistros',  cronExpression: '0 0 * * * ?'
      //simple repeatInterval: 5000l // execute job once in 5 seconds
      
        
    }

    def execute() {
        // execute job
        ExportarRegistrosService.serviceMethod()
         println "FIN DE OPERACION "
        
        
        
    }
}
