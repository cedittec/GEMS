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

class GraficaCogeneracionEficienteController 
{
    def index()
    { 
        def fechaInit = params?.fechaInicio
        def fechaEnd = params?.fechaFinal
        def arreglo1 = [12]
        def arreglo2 = [12]
        def arreglo3 = [12]
        
        Acumulados
        /*
         * 
         * 
         * 
         * */
        
        [   
            arreglo1: arreglo1,
            arreglo2: arreglo2,
            arreglo3: arreglo3
        ]
    }
}
