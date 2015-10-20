package Data

import grails.transaction.Transactional
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.GEMS1.ExternalData.GasLpAmexgas;

@Transactional
class GasLpService {

    def serviceMethod() {
        Document doc = Jsoup.connect("http://amexgas.com.mx/precioglp.php#").get();
        //método obtenido de http://maspanji.wordpress.com/2012/07/25/jsoup-getting-data-in-html-table/ modificado para su uso       
        Element link = doc.select("div#actual").first(); //Aqui seleccionamos que parte del código vamos a usar con el nombre de la variable link
        Iterator<Element> iterator = link.select("h1").iterator();// depejamos con la función de iterator el contenido h1 de la variable link 
        while (iterator.hasNext()) {//método booleano, si hay valores en el iterator ntonces se hace lo que sigue
            System.out.println("HeadLine : " + iterator.next().text());

        }

        // Método obtenido de http://stackoverflow.com/questions/15853002/extract-and-parse-html-table-using-jsoup modificado para su uso 
        for (Element table : doc.select("table#precio_gas_lp_data_display")) {
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 1) {
                    if(!(tds.get(0).text().contains("No.")))
                    {
//                        System.out.println(tds.get(0).text() + ":" + tds.get(1).text() + "/" + tds.get(2).text() +
//                                            "/" + tds.get(3).text() +"/" + tds.get(4).text() + 
//                                            "/" + tds.get(5).text() + "/" + tds.get(6).text());
                        
                        String estado = tds.get(1).text();
                        
                        String precioKgCadena = tds.get(5).text();
                        precioKgCadena =  precioKgCadena.replace("\$", "");
                        precioKgCadena =  precioKgCadena.trim();
                        
                        String precioLtCadena = tds.get(6).text();
                        precioLtCadena =  precioLtCadena.replace("\$","");
                        precioLtCadena =  precioLtCadena.trim();
                        
                        double precio_kg = Double.parseDouble(precioKgCadena); 
                        double precio_lt = Double.parseDouble(precioLtCadena); 
                        
                        System.out.println("Guardando:"+estado + " - " + precio_kg + " - " + precio_lt);
                        try{
                            if(estado.contains("Distrito Federal"))
                                estado = "Distrito Federal"
                                
                            def gasLpRegistroNuevo = (new GasLpAmexgas(estado:estado, precioKg: precio_kg, precioLt:precio_lt).save(flush:true));
                        }catch(Exception e)
                        {
                            System.out.println("Error al almacenar registro de amexgas : "+ (new Date()));
                        }
                    }
                    
                    
                }
            }
        }

    }
}
