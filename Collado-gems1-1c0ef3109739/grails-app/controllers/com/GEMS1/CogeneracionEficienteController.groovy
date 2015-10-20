package com.GEMS1
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import static org.springframework.http.HttpStatus.*
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import groovy.time.TimeCategory
import com.GEMS1.Data.*
import com.GEMS1.*
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat

/////
@Secured(['ROLE_SysAdmin','ROLE_adminVentas','ROLE_adminTecnico','ROLE_clienteAdmin','ROLE_clienteTecnico', 'ROLE_adminVentas'])
class CogeneracionEficienteController {
    def springSecurityService
    def index() {
        /* 
         * Definiendo las variables a utilizar al final del
         * controlador (las que se mandan al index.gsp de CogeneracionEficiente
         * */
        
       if (params?.fechaInicio){
            def sitios = []
            //Numero de sitios
            def numeroSitios = 0
            //Guarda los meses, son 12 en total 0-11
            def arregloGraficaMeses = ["","","","","","","","","","","",""]
            //Energía térmica (H)
            def arregloGraficaValores = [0,0,0,0,0,0,0,0,0,0,0,0]
            //Energía eléctrica (E)
            def arregloGraficaValores2 = [0,0,0,0,0,0,0,0,0,0,0,0]
            //Combustible consumido (F)
            def arregloGraficaValores3 = [0,0,0,0,0,0,0,0,0,0,0,0]
            //Eficiencia
            def arregloEficiencia = [0,0,0,0,0,0,0,0,0,0,0,0]

            
            def principal = springSecurityService.principal
            def usuario = Usuario.get(principal.id)
            def rol = UsuarioRol.findByUsuario(usuario)
            rol = Rol.findById(rol.rol.id)
            if(rol.authority == 'ROLE_SysAdmin')
            {
                //sitios = Sitio.getAll()
                sitios = Sitio.findAllWhere(estatus:"activo")
            }
            else{
                def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
                //sitios = Sitio.findAllWhere(estatus:"activo")
                sitios = Sitio.getAll(accesosDeUsuario.sitioId)
                def listTemp = []
                for(sitio in sitios)
                {
                    if(sitio.estatus == "inactivo")
                    {
                        listTemp.add(sitio)
                    }
                }
                sitios = sitios.minus(listTemp)
            }
            //Ahora obtenemos el número de sitios, con la función 'size()'
            //en el arreglo 'sitios'.
            numeroSitios = sitios.size()

            def fechaInit = params?.fechaInicio
            def fechaEnd = params?.fechaFinal
            //Realizamos lo mismo para los ultimos registros del 
            // RegistroSistemaCogeneracionHora y el RegistroIntercambiadorHora.
            // Y obtenemos el resto de los datos...
            def flujoMasicoAguaVal = 0
            def temperaturaAguaEntradaVal = 0
            def temperaturaAguaSalidaVal = 0
            def temperaturaGasEntradaVal = 0
            def temperaturaGasSalidaVal = 0
            def flujoMasicoGasVal = 0
            

            def lastDataSistema = 
                RegistroSistemaCogeneracionHora.find(
                    "from RegistroSistemaCogeneracionHora where dateCreated <= '"
                    +fechaEnd+
                    "' order by dateCreated desc"
                )
            if (lastDataSistema){
                flujoMasicoGasVal = lastDataSistema.fuelEnergyFlow
            }
            def lastDataIntercambiador = 
                RegistroIntercambiadorHora.find(
                    "from RegistroIntercambiadorHora where dateCreated <= '"
                    +fechaEnd+
                    "' order by dateCreated desc"
                )
            if (lastDataIntercambiador){
                flujoMasicoAguaVal = lastDataIntercambiador.flujoAguaEntrada
                temperaturaAguaEntradaVal = lastDataIntercambiador.tempAguaEntrada
                temperaturaAguaSalidaVal = lastDataIntercambiador.tempAguaSalida
                temperaturaGasEntradaVal =lastDataIntercambiador.tempAireEntrada
                temperaturaGasSalidaVal =lastDataIntercambiador.tempAireSalida
            }


            //Se consiguen todos los acumulados en el rango de fechas que corresponden al sitio..
            def tuplas = Acumulados.executeQuery (
                        "select f, h, e, date from Acumulados where date <= '"+fechaEnd+
                        "' and date >= '"+fechaInit+
                        "'"
                    )
            
            def energiaCombustibleVal = Acumulados.executeQuery(
                "select sum(f) from Acumulados where date <= '"+fechaEnd+
                        "' and date >= '"+fechaInit+
                        "'"
            )[0]
            if (energiaCombustibleVal == null)
                energiaCombustibleVal = 0
            def energiaElectricaGeneradaVal = Acumulados.executeQuery(
                "select sum(e) from Acumulados where date <= '"+fechaEnd+
                        "' and date >= '"+fechaInit+
                        "'"
            )[0]
            if (energiaElectricaGeneradaVal == null)
                energiaElectricaGeneradaVal = 0
            def energiaTermicaGeneradaVal = Acumulados.executeQuery(
                "select sum(h) from Acumulados where date <= '"+fechaEnd+
                        "' and date >= '"+fechaInit+
                        "'"
            )[0]
            if (energiaTermicaGeneradaVal == null)
                energiaTermicaGeneradaVal = 0
            def energiaNetaVal = (energiaElectricaGeneradaVal +
                energiaTermicaGeneradaVal -
                energiaCombustibleVal)

            def eficiencia = 0
            
            if ((energiaCombustibleVal-energiaTermicaGeneradaVal/0.82) != 0)
                eficiencia = (energiaElectricaGeneradaVal/(0.44*0.91) + 
                    energiaTermicaGeneradaVal/0.82 - energiaCombustibleVal)/
                    (energiaCombustibleVal-energiaTermicaGeneradaVal/0.82);

            //Recorre los 12 meses.
            if (tuplas.size() > 0){
                def tempAcumulados = tuplas[0]
                def recorre = 1
                def limit = 12
                if (tuplas.size()>12){
                    def recorreB = (tuplas.size()-(tuplas.size()%12)) /12
                    recorre = recorreB.intValue()
                }
                for (def i = 0; i<12;i++)
                    {
                            //Solo la gráfica...
                            if (tempAcumulados)
                            {
                                arregloGraficaMeses[i] = 
                                    tempAcumulados[3]
                                    //tempAcumulados.date
                                arregloGraficaValores[i] = 
                                    tempAcumulados[1].round(3)
                                    //tempAcumulados.h
                                arregloGraficaValores2[i] = 
                                    tempAcumulados[2].round(3)
                                    //tempAcumulados.e
                                arregloGraficaValores3[i] = 
                                    tempAcumulados[0].round(3)
                                    //tempAcumulados.f
                            }
                            else{
                                //Si ya no existen los acumulados, se detiene ahí...

                                break;
                            }
                            tempAcumulados = tuplas[(recorre*i)%tuplas.size()]
                    }
                    
                
                    //Arreglo Eficiencia
                    for (def i = 0; i<12; i++)
                    {
                        if ((arregloGraficaValores3[i]-arregloGraficaValores[i]/0.82) != 0)
                        arregloEficiencia[i] = 
                            (arregloGraficaValores2[i]/(0.44*0.91) + 
                            arregloGraficaValores[i]/0.82 - arregloGraficaValores3[i])/
                            (arregloGraficaValores3[i]-arregloGraficaValores[i]/0.82);
                    }
            }
            
            //Area en donde se evita que se mande en formato de notación científica
            /*
                energiaTermicaGeneradaVal =String.format("%.2f",energiaTermicaGeneradaVal)
                energiaElectricaGeneradaVal =String.format("%.2f",energiaElectricaGeneradaVal)
                energiaCombustibleVal =String.format("%.2f",energiaCombustibleVal)
                energiaNetaVal =String.format("%.2f",energiaNetaVal)
                flujoMasicoAguaVal =String.format("%.2f",flujoMasicoAguaVal)
                flujoMasicoGasVal =String.format("%.2f",flujoMasicoGasVal)
                temperaturaAguaEntradaVal =String.format("%.2f",temperaturaAguaEntradaVal)
                temperaturaAguaSalidaVal =String.format("%.2f",temperaturaAguaSalidaVal)
                temperaturaGasEntradaVal =String.format("%.2f",temperaturaGasEntradaVal)
                temperaturaGasSalidaVal =String.format("%.2f",temperaturaGasSalidaVal)
            */           
            /*
                 Esta linea es para evitar que se imprima la variable energiaNetaVal en notación científica
                 Con esto, sale el valor real
                 Código String.format(".2f", variable)
                 * */
            
            /*
                 en 210 y 454 hay []'s
            */
            [outSalida: fechaEnd,
                eficiencia: eficiencia,
                sitios: sitios, 
                numeroSitios: numeroSitios,
                energiaTermicaGeneradaVal: energiaTermicaGeneradaVal, 
                energiaElectricaGeneradaVal: energiaElectricaGeneradaVal,
                energiaCombustibleVal: energiaCombustibleVal,
                energiaNetaVal: energiaNetaVal, 
                flujoMasicoAguaVal: flujoMasicoAguaVal,
                flujoMasicoGasVal: flujoMasicoGasVal,
                temperaturaAguaEntradaVal: temperaturaAguaEntradaVal,
                temperaturaAguaSalidaVal: temperaturaAguaSalidaVal,
                temperaturaGasEntradaVal: temperaturaGasEntradaVal,
                temperaturaGasSalidaVal: temperaturaGasSalidaVal,
                arregloGraficaMeses: arregloGraficaMeses,
                arregloGraficaValores: arregloGraficaValores,
                arregloGraficaValores2: arregloGraficaValores2,
                arregloGraficaValores3: arregloGraficaValores3,
                arregloEficiencia: arregloEficiencia]
        }
        else{
            def out = 0
            //Variable que guarda los sitios...
            def sitios
            //Numero de sitios
            def numeroSitios = 0

            //La H, la E y las eficiencia:

            //Variable que guarda la eficiencia del sistema
            def eficiencia = 0
            //Energía eléctrica generada por el sistema, en Kwh (la H)
            def energiaTermicaGeneradaVal = 0
            //Energia eléctrica generada por el sistema, en Kwh (la E)
            def energiaElectricaGeneradaVal = 0
            //Energia neta generada en kwh 
            def energiaNetaVal = 0 

            //Flujos másicos de gas (combustible) y el agua, y las temperaturas de entrada y salida se usan los más actuales:

            //Flujo másico de agua en kg/s
            def flujoMasicoAguaVal = 0
            //Flujo másico del gas, en kg/s
            def flujoMasicoGasVal = 0
            //Temperatura de entrada de agua al intercambiador
            def temperaturaAguaEntradaVal = 0
            //Temperatura de salida de agua al intercambiador
            def temperaturaAguaSalidaVal = 0
            //Temperatura de entrada del gas al intercambiador
            def temperaturaGasEntradaVal = 0
            //Temperatura de salida del gas al intercambiador
            def temperaturaGasSalidaVal = 0
            //Guarda los meses, son 12 en total 0-11
            def arregloGraficaMeses = ["","","","","","","","","","","",""]
            //Energía térmica (H)
            def arregloGraficaValores =     [0,0,0,0,0,0,0,0,0,0,0,0]
            //Energía eléctrica (E)
            def arregloGraficaValores2 =    [0,0,0,0,0,0,0,0,0,0,0,0]
            //Combustible consumido (F)
            def arregloGraficaValores3 =    [0,0,0,0,0,0,0,0,0,0,0,0]
            //Eficiencia
            def arregloEficiencia =         [0,0,0,0,0,0,0,0,0,0,0,0]

            /*
             * Se obtienen los sitios de acuerdo a las licencias de usuario...
            */

            def principal = springSecurityService.principal
            def usuario = Usuario.get(principal.id)
            def rol = UsuarioRol.findByUsuario(usuario)
            rol = Rol.findById(rol.rol.id)
            if(rol.authority == 'ROLE_SysAdmin')
            {
                //sitios = Sitio.getAll()
                sitios = Sitio.findAllWhere(estatus:"activo")
            }
            else{
                def accesosDeUsuario = UsuarioSitio.findAllByUsuario(usuario)
                //sitios = Sitio.findAllWhere(estatus:"activo")
                sitios = Sitio.getAll(accesosDeUsuario.sitioId)
                def listTemp = []
                for(sitio in sitios)
                {
                    if(sitio.estatus == "inactivo")
                    {
                        listTemp.add(sitio)
                    }
                }
                sitios = sitios.minus(listTemp)
            }
            //Ahora obtenemos el número de sitios, con la función 'size()'
            //en el arreglo 'sitios'.
            numeroSitios = sitios.size()


            /*Se obtiene la eficiencia, la H, la F y la E.*/

            //Ahora procede conseguir el último elemento (por fecha) que corresponde
            //a la domain class Acumulados.groovy, la cual tiene el acumulado.



            //Realizamos lo mismo para los ultimos registros del 
            //RegistroSistemaCogeneracionHora y el RegistroIntercambiadorHora.
            //Y obtenemos el resto de los datos...
            def lastDataSistema = 
                RegistroSistemaCogeneracionHora.find(
                    "from RegistroSistemaCogeneracionHora order by dateCreated desc"
                )
            if (lastDataSistema){
                flujoMasicoGasVal = lastDataSistema.fuelEnergyFlow
            }
            def lastDataIntercambiador = 
                RegistroIntercambiadorHora.find(
                    "from RegistroIntercambiadorHora order by dateCreated desc"
                )
            if (lastDataIntercambiador){
                flujoMasicoAguaVal = lastDataIntercambiador.flujoAguaEntrada
                temperaturaAguaEntradaVal = lastDataIntercambiador.tempAguaEntrada
                temperaturaAguaSalidaVal = lastDataIntercambiador.tempAguaSalida
                temperaturaGasEntradaVal =lastDataIntercambiador.tempAireEntrada
                temperaturaGasSalidaVal =lastDataIntercambiador.tempAireSalida
            }

            //Ahora proceden las variables de la gráfica, donde son H (valores 1),
            //E (valores 2),
            //F (valores 3).
            //Estas se obtienen según el mes en el que estamos...
            energiaTermicaGeneradaVal = 0
            energiaElectricaGeneradaVal = 0
            energiaNetaVal = 0
            def energiaCombustibleVal = 0

            def tempAcumulados = Acumulados.find(
                    "from Acumulados order by date desc"
                )
            /*
            def tempAcumulados = Acumulados.executeQuery(
                    "select h,e,f,date,anterior from Acumulados order by date desc"
                )[0]
            */
            //Se crea un calendario para sacar la fecha de hace 1 año...
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, -12);
            if (tempAcumulados){
                energiaCombustibleVal = Acumulados.executeQuery(
                    "select sum(f) from Acumulados where date <= '"+
                    //tempAcumulados[3]
                    tempAcumulados.date
                    +"'and date > :oneYearAgo",
                    [oneYearAgo: cal.time]
                )[0]
                energiaElectricaGeneradaVal = Acumulados.executeQuery(
                    "select sum(e) from Acumulados where date <= '"+
                    //tempAcumulados[3]
                    tempAcumulados.date
                    +"'and date > :oneYearAgo",
                    [oneYearAgo: cal.time]
                )[0]
                energiaTermicaGeneradaVal = Acumulados.executeQuery(
                    "select sum(h) from Acumulados where date <= '"+
                    //tempAcumulados[3]
                    tempAcumulados.date
                    +"'and date > :oneYearAgo",
                    [oneYearAgo: cal.time]
                )[0]
                energiaNetaVal = (energiaElectricaGeneradaVal +
                    energiaTermicaGeneradaVal -
                    energiaCombustibleVal)
                
                
                
                eficiencia = (energiaElectricaGeneradaVal/(0.44*0.91) + 
                    energiaTermicaGeneradaVal/0.82 - energiaCombustibleVal)/
                    (energiaCombustibleVal-energiaTermicaGeneradaVal/0.82);

                //Recorre los 12 meses.
                for (def i = 0; i<12;i++)
                {
                        //Solo la gráfica...
                        if (tempAcumulados)
                        {
                            arregloGraficaMeses[i] = 
                                //tempAcumulados[3]
                                tempAcumulados.date
                            arregloGraficaValores[i] = 
                                //tempAcumulados[3]
                                tempAcumulados.h
                            arregloGraficaValores2[i] = 
                                //tempAcumulados[3]
                                tempAcumulados.e
                            arregloGraficaValores3[i] = 
                                //tempAcumulados[3]
                                tempAcumulados.f
                        }
                        else{
                            //Si ya no existen los acumulados, se detiene ahí...

                            break;
                        }

                        def siguienteAcumulado =  Acumulados.find(
                            "from Acumulados where date > :oneMonthAgo order by date asc",
                            [oneMonthAgo: tempAcumulados.anterior]
                            )
                        /*
                        def siguienteAcumulado =  Acumulados.executeQuery(
                        "select h,e,f,date,anterior from Acumulados where date > :oneMonthAgo order by date asc",
                            [oneMonthAgo: cal.time]
                        )[0]
                        */

                        tempAcumulados = siguienteAcumulado
                }



            }
            
            //Area en donde se evita que se mande en formato de notación científica
            /*
                eficiencia =String.format("%.2f",eficiencia)
                energiaTermicaGeneradaVal =String.format("%.2f",energiaTermicaGeneradaVal)
                energiaElectricaGeneradaVal =String.format("%.2f",energiaElectricaGeneradaVal)
                energiaCombustibleVal =String.format("%.2f",energiaCombustibleVal)
                energiaNetaVal =String.format("%.2f",energiaNetaVal)
                flujoMasicoAguaVal =String.format("%.2f",flujoMasicoAguaVal)
                flujoMasicoGasVal =String.format("%.2f",flujoMasicoGasVal)
                temperaturaAguaEntradaVal =String.format("%.2f",temperaturaAguaEntradaVal)
                temperaturaAguaSalidaVal =String.format("%.2f",temperaturaAguaSalidaVal)
                temperaturaGasEntradaVal =String.format("%.2f",temperaturaGasEntradaVal)
                temperaturaGasSalidaVal =String.format("%.2f",temperaturaGasSalidaVal)
            */
            /*
                Esta linea es para evitar que se imprima la variable energiaNetaVal en notación científica
                Con esto, sale el valor real
                Código String.format(".2f", variable)
            */
            /*
                Se devuelven al Gsp las variables, en este caso nombradas como
                en el controlador.
            */
            [outSalida: out, 
                sitios: sitios, 
                numeroSitios: numeroSitios, 
                eficiencia: eficiencia,
                energiaTermicaGeneradaVal: energiaTermicaGeneradaVal, 
                energiaElectricaGeneradaVal: energiaElectricaGeneradaVal,
                energiaCombustibleVal: energiaCombustibleVal,
                energiaNetaVal: energiaNetaVal, 
                flujoMasicoAguaVal: flujoMasicoAguaVal,
                flujoMasicoGasVal: flujoMasicoGasVal,
                temperaturaAguaEntradaVal: temperaturaAguaEntradaVal,
                temperaturaAguaSalidaVal: temperaturaAguaSalidaVal,
                temperaturaGasEntradaVal: temperaturaGasEntradaVal,
                temperaturaGasSalidaVal: temperaturaGasSalidaVal,
                arregloGraficaMeses: arregloGraficaMeses,
                arregloGraficaValores: arregloGraficaValores,
                arregloGraficaValores2: arregloGraficaValores2,
                arregloGraficaValores3: arregloGraficaValores3]
        }
    }
    
    
}
