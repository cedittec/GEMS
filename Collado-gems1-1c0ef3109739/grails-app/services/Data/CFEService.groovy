package Data

import grails.transaction.Transactional
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.GEMS1.ExternalData.TarifaRegionCfe;
import java.util.Calendar;
import java.util.Date;

@Transactional
class CFEService {

    def serviceMethod() {
        def currentYear = Calendar.getInstance().get(Calendar.YEAR);
        try{
            Document documento = Jsoup.connect("http://app.cfe.gob.mx/Aplicaciones/CCFE/Tarifas/Tarifas/tarifas_negocio.asp?Tarifa=CMAMT&Anio=" + currentYear+"").get();
            guardarTablasHM(currentYear, documento)
            guardarTablasOM(currentYear, documento)
            guardarTablasHMC(currentYear, documento)
        }catch(Exception e)
        {
            println "Se ha generado una exp"
            println "Info. " 
            println e.toString()
        }
               
    }
    
    def guardarTablasHM(int year, Document doc) throws IOException
    {
        boolean checker = false;
        checker = saveTableHM(doc,year,14, 4,3,1);
        return true;
    }
    
    def guardarTablasOM(int year, Document doc) throws IOException
    {
        //Document documento = Jsoup.connect("http://app.cfe.gob.mx/Aplicaciones/CCFE/Tarifas/Tarifas/tarifas_negocio.asp?Tarifa=CMAMT&Anio=" + year).get();
       
        boolean checker = false;
            checker = saveTable123OM(doc,year,15, 0,1,1);    
            checker = saveTable123OM(doc,year,16, 1,2,1);     
            checker = saveTable123OM(doc,year,14, 2,3,1);   
            checker = saveTable4OM(doc,year,14, 3,3,1);   
        
        return true;
        
    }
    
    def guardarTablasHMC(int year, Document doc) throws IOException
    {       
       
        boolean checker = saveTablesHMC(doc,year,14, 5,1);   
        checker = saveTablesHMC(doc,year,14, 6,5);   
        return checker;
        
    }
    
    //Metodo auxiliar para primeras 3 tablas de taridas O-M
    def saveTable123OM(Document doc,int year, int numColumnas, int numTabla, int idRegion, int idTarifa) {
        try {
            
            int check = 0;
            String[] meses = new String[numColumnas];
            double[] demanda = new double[numColumnas];
            double[] energia = new double[numColumnas];

            Element table = doc.select("tr table.tablaTarifa").get(numTabla);
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 1) {

                    if (check == 0) {
                        //SE guardan los meses dentro de la primer fila de la tabla
                         for (int i = 1; i < numColumnas; i++) {
                            meses[i] = tds.get(i).text();
                          
                        }

                    } else if (check == 1) {  //Los demás if hacen funcion de guardar en el arreglo cada fila de la tabla
                        
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                demanda[i] = price;
                                
                            }

                        }

                    } else {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                energia[i] = price;
                               
                            }
                        }
                    }
                    check++;
                }
            }
            Date date = new Date();            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            if(numTabla == 0){
                    int month = cal.get(Calendar.MONTH)+2;   
                    if(month >11) month++;            
 
                    //GUARDAR A BD con meses[month] demanda[month] energia[i]}
                     //regionId : 1
                    
                    if(month==11)
                    {
                        int oct2 = 12;
                        def CFERegistroNuevo1 = (new TarifaRegionCfe(mes: meses[oct2],ano:year,demanda:demanda[oct2], energia:energia[oct2],regionId:1,tarifaId:1 ).save(flush:true));
                        //System.out.println("Mes BAJA CAL:"+ meses[oct2] + "Demanda:" +demanda[oct2]+ " Energia "+energia[oct2]);
                    }
                        def CFERegistroNuevo2 = (new TarifaRegionCfe(mes: meses[month],ano:year,demanda:demanda[month], energia:energia[month],regionId:1,tarifaId:1 ).save(flush:true));
                        //System.out.println("Mes BAJA CAL:"+ meses[month] + "Demanda:" +demanda[month]+ " Energia "+energia[month]);
            
            }else if(numTabla == 1)
            {
                    int month = cal.get(Calendar.MONTH)+2;   
                    if(month >5) month++;   
                    if(month >12) month++;   

                    //GUARDAR A BD con meses[month] demanda[month] energia[i]}
                      //regionID: 2
                    if(month==5 || month == 12)
                    {
                        int oct2 = 13;
                        def CFERegistroNuevo1 = (new TarifaRegionCfe(mes:meses[oct2],ano:year, demanda:demanda[oct2], energia:energia[oct2],regionId:2,tarifaId:1 ).save(flush:true));
                        //System.out.println("Mes:"+ meses[oct2] + "Demanda:" +demanda[oct2]+ " Energia "+energia[oct2]);
                    }
                        def CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:demanda[month], energia:energia[month],regionId:2,tarifaId:1 ).save(flush:true));
                        //System.out.println("Mes:"+ meses[month] + "Demanda:" +demanda[month]+ " Energia "+energia[month]);
            }else
            {
                  int month = cal.get(Calendar.MONTH)+2;  
                  //regioniD: 3

                      
                      def CFERegistroNuevo = (new TarifaRegionCfe(mes:meses[month],ano:year,demanda:demanda[month], energia:energia[month],regionId:3,tarifaId:1 ).save(flush:true));
                      //System.out.println("Mes:"+ meses[month] + "Demanda:" +demanda[month]+ " Energia "+energia[month]);
            }
            
            
            //Para cargar todos los costos que se tienen en la tabla CFE se utilizaría el for
           // for (int i = 1; i < numColumnas; i++) {  
                //meses[i] demanda[i] energia[i]
                //Se debe guardar en la bd, tambien el año                 
            //}

            return true;
        } catch (Exception e) {
            System.out.println("Error al cargar pagina web: " + e.toString());
            return false;
        }

    }
    
    
    
    //Metodo para guardar la tabla 4 (mezclada en el html) de O-M
    @Transactional
    def saveTable4OM(Document doc,int year, int numColumnas, int numTabla, int idRegion, int idTarifa) {
        try {
            
            int check = 0;
            def meses =["","Dic./13","Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.","Nov.","Dic."];
            double[] CentralDemanda = new double[numColumnas];
            double[] CentralEnergia = new double[numColumnas];
            
            double[] NoroesteDemanda = new double[numColumnas];
            double[] NoroesteEnergia = new double[numColumnas];
            
            double[] NorteDemanda = new double[numColumnas];
            double[] NorteEnergia = new double[numColumnas];
            
            double[] PeninsularDemanda = new double[numColumnas];
            double[] PeninsularEnergia = new double[numColumnas];
            
            double[] SurDemanda = new double[numColumnas];
            double[] SurEnergia = new double[numColumnas];

            Element table = doc.select("tr table.tablaTarifa").get(numTabla);
            int rnum = 0;
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 1) {

                    if (check == 0) {
                       
                        for (int i = 1; i < numColumnas; i++) {
                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                CentralDemanda[i] = price;
                                //System.out.println("Se guardo Central demanda:" + CentralDemanda[i]);
                            }                           
                           
                        }

                    } else if (check == 1) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                CentralEnergia[i] = price;
                                //System.out.println("Se guardo Central Energia:" + CentralEnergia[i]);
                            }

                        }

                    } else {
                        
                    }
                   if (check == 2) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                NoroesteDemanda[i] = price;
                                //System.out.println("Se guardo Noroeste demanda:" + NoroesteDemanda[i]);
                            }

                        }
                    }
                   if (check == 3) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                NoroesteEnergia[i] = price;
                                //System.out.println("Se guardo Noroeste Energia:" + NoroesteEnergia[i]);
                            }

                        }
                    }
                   if (check == 4) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                NorteDemanda[i] = price;
                                //System.out.println("Se guardo Norte demanda:" + NorteDemanda[i]);
                            }

                        }
                    }
                   if (check == 5) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                NorteEnergia[i] = price;
                                //System.out.println("Se guardo Norte energia:" + NorteEnergia[i]);
                            }

                        }
                    }
                   
                   if (check == 6) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                PeninsularDemanda[i] = price;
                                //System.out.println("Se guardo peninsular demanda:" + PeninsularDemanda[i]);
                            }

                        }
                    }
                   if (check == 7) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                PeninsularEnergia[i] = price;
                                //System.out.println("Se guardo peninsular energia:" + PeninsularEnergia[i]);
                            }

                        }
                    }
                   if (check == 8) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                SurDemanda[i] = price;
                               // System.out.println("Se guardo sur demanda:" + SurDemanda[i]);
                            }

                        }
                    }
                   if (check == 9) {
                        for (int i = 1; i < numColumnas; i++) {

                            double price = stringToDouble(tds.get(i).text());
                            if (price != -1) {
                                SurEnergia[i] = price;
                               // System.out.println("Se guardo sur energia:" + SurEnergia[i]);
                            }

                        }
                    }                    
                    check++;
                }
            }
            
            Date date = new Date();            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH)+2;     

          
            //GUARDAR A BD con meses[month] demanda[month] energia[i]}  
            //regionId : 4
            def CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:CentralDemanda[month], energia:CentralEnergia[month],regionId:4,tarifaId:1 ).save(flush:true));
           // System.out.println("CENTRAL Mes:"+ meses[month] + "Demanda:" +CentralDemanda[month]+ " Energia "+CentralEnergia[month]);
            //Region : 5
            CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:NoroesteDemanda[month], energia:NoroesteEnergia[month],regionId:5,tarifaId:1 ).save(flush:true));
            //System.out.println("NOROESTE:"+ meses[month] + "Demanda:" +NoroesteDemanda[month]+ " Energia "+NoroesteEnergia[month]);
            //Reegion 8
            CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:NorteDemanda[month], energia:NorteEnergia[month],regionId:8,tarifaId:1 ).save(flush:true));
            //System.out.println("NORTE:"+ meses[month] + "Demanda:" +NorteDemanda[month]+ " Energia "+NorteEnergia[month]);
            //Region 6
            CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:PeninsularDemanda[month], energia:PeninsularEnergia[month],regionId:6,tarifaId:1 ).save(flush:true));
            //System.out.println("Peninsular:"+ meses[month] + "Demanda:" +PeninsularDemanda[month]+ " Energia "+PeninsularEnergia[month]);
            //REgion 7
            CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:SurDemanda[month], energia:SurEnergia[month],regionId:7,tarifaId:1 ).save(flush:true));
            //System.out.println("Sur:"+ meses[month] + "Demanda:" +SurDemanda[month]+ " Energia "+SurEnergia[month]);
            

            return true;
        } catch (Exception e) {
            System.out.println("Error al cargar pagina web: " + e.toString());
            return false;
        }

    }
    
    //Guarda el ultimo mes de la tabla de HM
    @Transactional
    def saveTableHM(Document doc,int year, int numColumnas, int numTabla, int idRegion, int idTarifa) {
         try {
            Date date = new Date();            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH)+2;     
            // System.out.println("EL Ano ACTUAL ES " +  Calendar.getInstance().get(Calendar.YEAR) );
            
            int check = 0;
            def meses =["","Dic./13","Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.","Nov.","Dic."];
            
            double BajaCaliforniaDemandaFacturable;
            double BajaCaliforniaEnergiaPunta;
            double BajaCaliforniaEnergiaIntermedia;
            double BajaCaliforniaEnergiaBase;
            
            double BajaCaliforniaSurDemandaFacturable;
            double BajaCaliforniaSurEnergiaPunta;
            double BajaCaliforniaSurEnergiaIntermedia;
            double BajaCaliforniaSurEnergiaBase;
            
            double CentralDemandaFacturable;
            double CentralEnergiaPunta;
            double CentralEnergiaIntermedia;
            double CentralEnergiaBase;
            
            double NoroesteDemandaFacturable;
            double NoroesteEnergiaPunta;
            double NoroesteEnergiaIntermedia;
            double NoroesteEnergiaBase;
            
            double NoresteDemandaFacturable;
            double NoresteEnergiaPunta;
            double NoresteEnergiaIntermedia;
            double NoresteEnergiaBase;
            
            double  NorteDemandaFacturable;
            double NorteEnergiaPunta;
            double NorteEnergiaIntermedia;
            double NorteEnergiaBase;
            
            double PeninsularDemandaFacturable;
            double PeninsularEnergiaPunta;
            double PeninsularEnergiaIntermedia;
            double PeninsularEnergiaBase;
            
            double SurDemandaFacturable;
            double SurEnergiaPunta;
            double SurEnergiaIntermedia;
            double SurEnergiaBase;

            Element table = doc.select("tr table.tablaTarifa").get(numTabla);
            int rnum = 0;
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 1) {

                    //System.out.println("File name:" + tds.get(0).text());
                    if (check == 0) {                       
                      //GUARDA MESES
                    }
                    else
                    {
                         
                        double price = stringToDouble(tds.get(month).text());
                        switch(check)
                        {
                            
                            
                            case 1:                                
                                BajaCaliforniaDemandaFacturable = price;
                                break;
                            case 2:
                                BajaCaliforniaEnergiaPunta = price;
                                break;
                            case 3:
                                BajaCaliforniaEnergiaIntermedia = price;
                                break;
                            case 4:
                                BajaCaliforniaEnergiaBase = price;
                                break;
                            
                             case 5:                                
                                BajaCaliforniaSurDemandaFacturable = price;
                                
                                break;
                            case 6:
                                BajaCaliforniaSurEnergiaPunta = price;
                                
                                break;
                            case 7:
                                BajaCaliforniaSurEnergiaIntermedia = price;
                                
                                break;
                            case 8:
                                BajaCaliforniaSurEnergiaBase = price;
                                
                                break;
                                
                            case 9:                                
                                CentralDemandaFacturable = price;
                                break;
                            case 10:
                                CentralEnergiaPunta = price;
                                break;
                            case 11:
                                CentralEnergiaIntermedia = price;
                                break;
                            case 12:
                                CentralEnergiaBase = price;
                                break;
                            
                            case 13:                                
                                NoresteDemandaFacturable = price;                                
                                break;
                            case 14:
                                NoresteEnergiaPunta = price;
                                break;
                            case 15:
                                NoresteEnergiaIntermedia = price;
                                break;
                            case 16:
                                NoresteEnergiaBase = price;
                                break;
                            
                             case 17:                                
                                NoroesteDemandaFacturable = price;                                
                                break;
                            case 18:
                                NoroesteEnergiaPunta = price;
                                break;
                            case 19:
                                NoroesteEnergiaIntermedia = price;
                                break;
                            case 20:
                                NoroesteEnergiaBase = price;
                                break;
                                
                             case 21:                                
                                NorteDemandaFacturable = price;                                
                                break;
                            case 22:
                                NorteEnergiaPunta = price;
                                break;
                            case 23:
                                NorteEnergiaIntermedia = price;
                                break;
                            case 24:
                                NorteEnergiaBase = price;
                                break;
                                
                            case 25:                                
                                PeninsularDemandaFacturable = price;                                
                                break;
                            case 26:
                                PeninsularEnergiaPunta = price;
                                break;
                            case 27:
                                PeninsularEnergiaIntermedia = price;
                                break;
                            case 28:
                                PeninsularEnergiaBase = price;
                                break;
                                
                            case 29:                                
                                SurDemandaFacturable = price;                                
                                break;
                            case 30:
                                SurEnergiaPunta = price;
                                break;
                            case 31:
                                SurEnergiaIntermedia = price;
                                break;
                            case 32:
                                SurEnergiaBase = price;
                                break;                                                               
                        }
                    }
                    check++;                  
                }
            }
                
            
//            double BajaCaliforniaDemandaFacturable;
//            double BajaCaliforniaEnergiaPunta;
//            double BajaCaliforniaEnergiaIntermedia;
//            double BajaCaliforniaEnergiaBase;
    
            def CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:BajaCaliforniaDemandaFacturable, energiaPunta:BajaCaliforniaEnergiaPunta,
                    energiaBase:BajaCaliforniaEnergiaBase,energiaIntermedia:BajaCaliforniaEnergiaIntermedia,regionId:1,tarifaId:2 ).save(flush:true));
            
//            double BajaCaliforniaSurDemandaFacturable;
//            double BajaCaliforniaSurEnergiaPunta;
//            double BajaCaliforniaSurEnergiaIntermedia;
//            double BajaCaliforniaSurEnergiaBase;
            
              CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:BajaCaliforniaSurDemandaFacturable, energiaPunta:BajaCaliforniaSurEnergiaPunta,
                    energiaBase:BajaCaliforniaSurEnergiaBase,energiaIntermedia:BajaCaliforniaSurEnergiaIntermedia,regionId:2,tarifaId:2 ).save(flush:true));
            
//            double CentralDemandaFacturable;
//            double CentralEnergiaPunta;
//            double CentralEnergiaIntermedia;
//            double CentralEnergiaBase;

             CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:CentralDemandaFacturable, energiaPunta:CentralEnergiaPunta,
                    energiaBase:CentralEnergiaBase,energiaIntermedia:CentralEnergiaIntermedia,regionId:4,tarifaId:2 ).save(flush:true));
            
    
            //NORESTE

                CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:NoresteDemandaFacturable, energiaPunta:NoresteEnergiaPunta,
                    energiaBase:NoresteEnergiaBase,energiaIntermedia:NoresteEnergiaIntermedia,regionId:3,tarifaId:2 ).save(flush:true));
            
            
//            double NoroesteDemandaFacturable;
//            double NoroesteEnergiaPunta;
//            double NoroesteEnergiaIntermedia;
//            double NoroesteEnergiaBase;

                CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:NoroesteDemandaFacturable, energiaPunta:NoroesteEnergiaPunta,
                    energiaBase:NoroesteEnergiaBase,energiaIntermedia:NoroesteEnergiaIntermedia,regionId:5,tarifaId:2 ).save(flush:true));
            
//            double  NorteDemandaFacturable;
//            double NorteEnergiaPunta;
//            double NorteEnergiaIntermedia;
//            double NorteEnergiaBase;
  
             CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:NorteDemandaFacturable, energiaPunta:NorteEnergiaPunta,
                    energiaBase:NorteEnergiaBase,energiaIntermedia:NorteEnergiaIntermedia,regionId:8,tarifaId:2 ).save(flush:true));
            
            
//            double PeninsularDemandaFacturable;
//            double PeninsularEnergiaPunta;
//            double PeninsularEnergiaIntermedia;
//            double PeninsularEnergiaBase;

            CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:PeninsularDemandaFacturable, energiaPunta:PeninsularEnergiaPunta,
                    energiaBase:PeninsularEnergiaBase,energiaIntermedia:PeninsularEnergiaIntermedia,regionId:6,tarifaId:2 ).save(flush:true));
            
//            double SurDemandaFacturable;
//            double SurEnergiaPunta;
//            double SurEnergiaIntermedia;
//            double SurEnergiaBase;
            
            CFERegistroNuevo2 = (new TarifaRegionCfe(
                    mes:meses[month],ano:year, demandaFacturable:SurDemandaFacturable, energiaPunta:SurEnergiaPunta,
                    energiaBase:SurEnergiaBase,energiaIntermedia:SurEnergiaIntermedia,regionId:7,tarifaId:2 ).save(flush:true));
            

            return true;
        } catch (Exception e) {
            System.out.println("Error al cargar pagina web: " + e.toString());
            return false;
        }

    }
    
    //Metodo guarda la tbla de hmc dependiendo el numero de tabla entrante
    @Transactional
    def saveTablesHMC(Document doc,int year, int numColumnas, int numTabla,int idRegion) {
        try {
           
            Date date = new Date();            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH)+2;     
            
            int check = 0;
            def meses =["","Dic./13","Ene.","Feb.","Mar.","Abr.","May.","Jun.","Jul","Ago","Sep.","Oct.","Nov.","Dic."];
            
            double BajaCaliforniaDemandaFacturable;
            double BajaCaliforniaEnergiaPunta;
            double BajaCaliforniaEnergiaIntermedia;
            double BajaCaliforniaEnergiaBase;
            

            Element table = doc.select("tr table.tablaTarifa").get(numTabla);
            int rnum = 0;
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 1) {

                    //System.out.println("File name:" + tds.get(0).text());
                    if (check == 0) {                       
                      //GUARDA MESES
                    }
                    else
                    {
                         
                        double price = stringToDouble(tds.get(month).text());
                        switch(check)
                        {
                            
                            case 1:  
                                
                                BajaCaliforniaDemandaFacturable = price;
                                break;
                            case 2:
                               
                                BajaCaliforniaEnergiaPunta = price;
                                break;
                            case 3:
                               
                                BajaCaliforniaEnergiaIntermedia = price;
                                break;
                            case 4:
                             
                                BajaCaliforniaEnergiaBase = price;
                                break;
                                                                         
                        }
                    }
                    check++;                  
                }
            }
            
             def CFERegistroNuevo2 = (new TarifaRegionCfe(mes:meses[month],ano:year, demanda:BajaCaliforniaDemandaFacturable, energiaPunta:BajaCaliforniaEnergiaPunta,
                    energiaBase:BajaCaliforniaEnergiaBase,energiaIntermedia:BajaCaliforniaEnergiaIntermedia,regionId:idRegion,tarifaId:3 ).save(flush:true));
          
            return true;
        } catch (Exception e) {
            System.out.println("Error al cargar pagina web: " + e.toString());
            return false;
        }

    }
    
    
    //Metodo para convertir de string a double, devuelve -1 en caso de error
   def stringToDouble(String val)
    {
        double resultado = -1;
        try {
         resultado = Double.valueOf(val.trim()).doubleValue();         
        } catch (NumberFormatException nfe) {
           System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        
      return resultado;
    }
    
    
}
