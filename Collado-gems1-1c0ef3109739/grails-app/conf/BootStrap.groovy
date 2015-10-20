
import com.GEMS1.*
import com.GEMS1.ExternalData.RegionCfe
import com.GEMS1.ExternalData.TarifaCfe

class BootStrap {
    
    def init = { servletContext ->
      
     def alarma = CodigoAlarma.findByClaveAlarma('PS01') ?: (new CodigoAlarma(claveAlarma: 'PS01' , mensaje: 'Alarma Eléctrica').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS01') ?: (new CodigoAlarma(claveAlarma: 'CS01' , mensaje: 'Temperatura BAJA alberca').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS02') ?: (new CodigoAlarma(claveAlarma: 'CS02' , mensaje: 'Temperatura ALTA alberca').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS03') ?: (new CodigoAlarma(claveAlarma: 'CS03' , mensaje: 'Tanque BAJO de Diesel').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS04') ?: (new CodigoAlarma(claveAlarma: 'CS04' , mensaje: 'Tanque ALTO de Diesel').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS05') ?: (new CodigoAlarma(claveAlarma: 'CS05' , mensaje: 'Tanque BAJO de Gas LP').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS06') ?: (new CodigoAlarma(claveAlarma: 'CS06' , mensaje: 'Tanque ALTO de Gas LP ').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('CS07') ?: (new CodigoAlarma(claveAlarma: 'CS07' , mensaje: 'Alarma Térmica').save(flush:true))
     
     alarma = CodigoAlarma.findByClaveAlarma('MT01') ?: (new CodigoAlarma(claveAlarma: 'MT01' , mensaje: 'Alarma Eléctrica').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT02') ?: (new CodigoAlarma(claveAlarma: 'MT02' , mensaje: 'Alarma Térmica').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT03') ?: (new CodigoAlarma(claveAlarma: 'MT03' , mensaje: 'Presión de tanque inercia').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT04') ?: (new CodigoAlarma(claveAlarma: 'MT04' , mensaje: 'Presión de tanque Servicios').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT05') ?: (new CodigoAlarma(claveAlarma: 'MT05' , mensaje: 'Nivel BAJO tanque de inercia').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT06') ?: (new CodigoAlarma(claveAlarma: 'MT06' , mensaje: 'Nivel ALTO tanque de inercia ').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT07') ?: (new CodigoAlarma(claveAlarma: 'MT07' , mensaje: 'Nivel BAJO tanque de servicios ').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT08') ?: (new CodigoAlarma(claveAlarma: 'MT08' , mensaje: 'Nivel ALTO tanque de servicios ').save(flush:true))
     
     alarma = CodigoAlarma.findByClaveAlarma('MT09') ?: (new CodigoAlarma(claveAlarma: 'MT09' , mensaje: 'Temperatura BAJA  Tanque de Inercia').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT10') ?: (new CodigoAlarma(claveAlarma: 'MT10' , mensaje: 'Temperatura ALTA  Tanque de Inercia').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT11') ?: (new CodigoAlarma(claveAlarma: 'MT11' , mensaje: 'Temperatura BAJA Tanque de Servicios').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT12') ?: (new CodigoAlarma(claveAlarma: 'MT12' , mensaje: 'Temperatura ALTA Tanque de Servicios').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT13') ?: (new CodigoAlarma(claveAlarma: 'MT13' , mensaje: 'Tanque BAJO de Gas LP ').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT14') ?: (new CodigoAlarma(claveAlarma: 'MT14' , mensaje: 'Tanque ALTO de Gas LP ').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT15') ?: (new CodigoAlarma(claveAlarma: 'MT15' , mensaje: 'Tanque BAJO de Diesel').save(flush:true))
     alarma = CodigoAlarma.findByClaveAlarma('MT16') ?: (new CodigoAlarma(claveAlarma: 'MT16' , mensaje: 'Tanque AlTO de Diesel').save(flush:true))
        
      def sysAdmin = Rol.findByAuthority('ROLE_SysAdmin') ?:(new Rol(authority: 'ROLE_SysAdmin',displayName: 'SysAdmin').save(flush:true))
      def adminTecnico = Rol.findByAuthority('ROLE_adminTecnico') ?:(new Rol(authority: 'ROLE_adminTecnico',displayName: 'Administrador técnico').save(flush:true))
      def adminVentas = Rol.findByAuthority('ROLE_adminVentas') ?:(new Rol(authority: 'ROLE_adminVentas',displayName: 'Administrador de ventas').save(flush:true))
      def clienteAdmin = Rol.findByAuthority('ROLE_clienteAdmin') ?:(new Rol(authority: 'ROLE_clienteAdmin',displayName: 'Cliente administrador').save(flush:true))
      def clienteTecnico = Rol.findByAuthority('ROLE_clienteTecnico') ?:(new Rol(authority: 'ROLE_clienteTecnico',displayName:'Cliente técnico').save(flush:true))
      
      def fuenteDeEnergia = FuenteDeEnergia.findByModelo('C001') ?:(new FuenteDeEnergia(tipo:'Microturbina', modelo:'C001', descripcion: 'Microturbina importada de alemania').save(flush:true))
      def fuenteDeEnergia2 = FuenteDeEnergia.findByModelo('P001') ?: (new FuenteDeEnergia(tipo:'Panel solar', modelo:'P001', descripcion: 'Panel solar EMS').save(flush:true))
      def fuenteDeEnergia4 = FuenteDeEnergia.findByModelo('CS001') ?: (new FuenteDeEnergia(tipo:'Colector solar', modelo:'CS001', descripcion: 'Colector Solar JAPON').save(flush:true))
      def fuenteDeEnergia3 = FuenteDeEnergia.findByModelo('F001') ?: (new FuenteDeEnergia(tipo:'Otra fuente', modelo:'F001', descripcion: 'Una descripcion').save(flush:true))
      
      def combustibleFuente = CombustibleFuente.findByNombre('Gas LP')?:(new CombustibleFuente(nombre:'Gas LP', poderCalorifico: 0).save(flush:true))
      def combustibleFuente2 = CombustibleFuente.findByNombre('Gas Natural')?:(new CombustibleFuente(nombre:'Gas Natural', poderCalorifico: 0).save(flush:true))
      def combustibleFuente3 = CombustibleFuente.findByNombre('Diesel')?:(new CombustibleFuente(nombre:'Diesel', poderCalorifico: 0).save(flush:true))
      def combustibleFuente4 =CombustibleFuente.findByNombre('Biogas')?: (new CombustibleFuente(nombre:'Biogas', poderCalorifico: 0 ).save(flush:true))
      def combustibleFuente5 =CombustibleFuente.findByNombre('Keroseno')?: (new CombustibleFuente(nombre:'Keroseno', poderCalorifico: 0 ).save(flush:true))
      def combustibleFuente6 =CombustibleFuente.findByNombre('Biodiesel')?: (new CombustibleFuente(nombre:'Biodiesel', poderCalorifico: 0 ).save(flush:true))
      def combustibleFuente7 =CombustibleFuente.findByNombre('Turbosina')?: (new CombustibleFuente(nombre:'Turbosina', poderCalorifico: 0 ).save(flush:true))
      
      def servicio1 = Servicio.findByNombre('Alberca')?:(new Servicio(nombre:'Alberca', descripcion:'Control de temperatura de la alberca').save(flush:true))
      def servicio2 = Servicio.findByNombre('Agua Caliente')?:(new Servicio(nombre:'Agua Caliente', descripcion:'Calefaccion de agua del lugar').save(flush:true))
      def servicio3 = Servicio.findByNombre('Vapor')?:(new Servicio(nombre:'Vapor', descripcion:'Generacion de vapor').save(flush:true))
      def servicio4 = Servicio.findByNombre('Electrico')?:(new Servicio(nombre:'Electrico', descripcion:'Producción de elecrticidad').save(flush:true))
      def servicio5 = Servicio.findByNombre('Aire acondicionado')?:(new Servicio(nombre:'Aire acondicionado', descripcion:'Aire acondicionado').save(flush:true))
      
        
      def unidad = UnidadDeMedida.findByUnidadDeMedida('Litros')?:(new UnidadDeMedida(unidadDeMedida:'Litros', abreviatura:'Lts').save(flush:true))
      def unidad2 = UnidadDeMedida.findByUnidadDeMedida('Joules')?:(new UnidadDeMedida(unidadDeMedida:'Joules', abreviatura:'j').save(flush:true))
      def unidad3 = UnidadDeMedida.findByUnidadDeMedida('Metro cubico')?:(new UnidadDeMedida(unidadDeMedida:'Metro cubico', abreviatura:'m^3').save(flush:true))
        
      def regionCfe = RegionCfe.findByNombre('BAJA CALIFORNIA') ?:(new RegionCfe(nombre: 'BAJA CALIFORNIA').save(flush:true))
      regionCfe = RegionCfe.findByNombre('BAJA CALIFORNIA SUR') ?:(new RegionCfe(nombre: 'BAJA CALIFORNIA SUR').save(flush:true))
      regionCfe = RegionCfe.findByNombre('NORESTE') ?:(new RegionCfe(nombre: 'NORESTE').save(flush:true))
      regionCfe = RegionCfe.findByNombre('CENTRAL') ?:(new RegionCfe(nombre: 'CENTRAL').save(flush:true))
      regionCfe = RegionCfe.findByNombre('NOROESTE') ?:(new RegionCfe(nombre: 'NOROESTE').save(flush:true))
      regionCfe = RegionCfe.findByNombre('PENINSULAR') ?:(new RegionCfe(nombre: 'PENINSULAR').save(flush:true))
      regionCfe = RegionCfe.findByNombre('SUR') ?:(new RegionCfe(nombre: 'SUR').save(flush:true))
      regionCfe = RegionCfe.findByNombre('NORTE') ?:(new RegionCfe(nombre: 'NORTE').save(flush:true))
        
      def tarifaCfe = TarifaCfe.findByNombre('TARIFA O-M') ?:(new TarifaCfe(nombre: 'TARIFA O-M',tipo:'OM').save(flush:true)) 
      tarifaCfe = TarifaCfe.findByNombre('TARIFA H-M') ?:(new TarifaCfe(nombre: 'TARIFA H-M',tipo:'HM').save(flush:true)) 
      tarifaCfe = TarifaCfe.findByNombre('TARIFA H-MC') ?:(new TarifaCfe(nombre: 'TARIFA H-MC',tipo:'HMC').save(flush:true)) 
        
        
        
        
      def admin = (Usuario.findByEmail("energia@emsmx.com"))
        if(!admin){
            admin= new Usuario(
            email:"energia@emsmx.com",
            password: "energiaems",
            
            
             nombre1:"EMS",
             nombre2:"EMS",
             apellido1:"EMS",
             apellido2:"EMS",
             displayName:"EMS",
             dateCreated: new Date(),
             lastUpdated: new Date()
             
                
        ).save(flush:true)
        }        
        if(!admin.authorities.contains(sysAdmin)){
          UsuarioRol.create admin, sysAdmin, true
        }
       /* 
        def admin2 = (Usuario.findByEmail("laltamirano@emsmx.com"))
        if(!admin){
            admin= new Usuario(
            email:"laltamirano@emsmx.com",
            password: "Laltamirano.2014",
            
            
             nombre1:"Leonardo",
             nombre2:"",
             apellido1:"Altamirano",
             apellido2:"Casillas",
             displayName:"Leonardo Altamirano",
             dateCreated: new Date(),
             lastUpdated: new Date()
             
                
        ).save(flush:true)
        }        
        if(!admin.authorities.contains(sysAdmin)){
          UsuarioRol.create admin, sysAdmin, true
        }
        
        def admin3 = (Usuario.findByEmail("adepavia@emsmx.com"))
        if(!admin){
            admin= new Usuario(
            email:"adepavia@emsmx.com",
            password: "Adepavia.2014",
            
            
             nombre1:"Alicia",
             nombre2:"EMS",
             apellido1:"EMS",
             apellido2:"EMS",
             displayName:"Alicia EMS",
             dateCreated: new Date(),
             lastUpdated: new Date()
             
                
        ).save(flush:true)
        }        
        if(!admin.authorities.contains(sysAdmin)){
          UsuarioRol.create admin, sysAdmin, true
        }
        */
        /*
        println "Obteniendo datos de CFE del mes"
        try{             
        CFEService.serviceMethod()
        }catch(Exception e)
        {
            println "Error datos CFE: " e.toString();
        }
        println "Obteniendo datos de Gas LP"
        try{
            GasLpService.serviceMethod()
        }catch(Exception e)
        {
            
        }
        println "Obteniendo datos de Gas Natural"
        try{
             GasNaturalService.serviceMethod()
        }catch(Exception e){
            
        }*/
        
    }
    def destroy = {
    }
}
