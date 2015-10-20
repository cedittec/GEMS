package Data

import grails.transaction.Transactional
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.GEMS1.ExternalData.GasNatural;

@Transactional
class GasNaturalService {

    def serviceMethod() {
        Document doc = Jsoup.connect("http://oilprice.com/newwidgets-4.html").get();
        Element div3 = doc.select("table[class=boxes] tr").get(7);
        Iterator<Element> iterator4 = div3.select("tbody").iterator(); 
        String texto ="";
        while(iterator4.hasNext()){
            texto = iterator4.next().text();
            System.out.println(texto); 
        }
        def array = texto.split(" ");
        def precio = array[2].toDouble();
        def GasNaturalRegistroNuevo = (new GasNatural( precio:precio  ).save(flush:true));
        println "SE GUARDO GAS NATURAL"
    }

}

