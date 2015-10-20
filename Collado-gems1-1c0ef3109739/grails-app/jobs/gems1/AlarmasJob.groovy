package gems1
import com.GEMS1.*

class AlarmasJob 
{
    def AlarmasService

    static triggers = 
    {
      simple repeatInterval: 10000l
    }

    def execute() 
    {
        AlarmasService.checkAlarmas()
        AlarmasService.alarma()
    }
}