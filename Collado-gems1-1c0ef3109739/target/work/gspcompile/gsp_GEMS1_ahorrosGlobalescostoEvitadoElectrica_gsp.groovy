import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_ahorrosGlobalescostoEvitadoElectrica_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/ahorrosGlobales/costoEvitadoElectrica.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',11,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("frontend")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',13,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',13,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',14,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
expressionOut.print(createLink(controller:'EstadoFinanciero', action:'index'))
printHtmlPart(6)
expressionOut.print(createLink(controller:'AhorrosGlobales', action:'index'))
printHtmlPart(7)
if(true && (numeroSitios > 0)) {
printHtmlPart(8)
}
printHtmlPart(9)
if(true && (numeroSitios < 1)) {
printHtmlPart(10)
}
else {
printHtmlPart(11)
for( sitio in (sitios) ) {
printHtmlPart(12)
expressionOut.print(sitio.id)
printHtmlPart(13)
expressionOut.print(sitio.nombre)
printHtmlPart(14)
}
printHtmlPart(15)
}
printHtmlPart(16)
if(true && (numeroSitios > 0)) {
printHtmlPart(17)
invokeTag('formatNumber','g',89,['number':(costoEvitadoCogeneracion[0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(18)
invokeTag('formatNumber','g',100,['number':(costoEvitadoCogeneracion[1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(19)
invokeTag('formatNumber','g',106,['number':(costoEvitadoCogeneracion[2]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(20)
invokeTag('formatNumber','g',112,['number':(costoEvitadoCogeneracion[3]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(21)
invokeTag('formatNumber','g',131,['number':(costoEvitadoFotovoltaico[0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(22)
invokeTag('formatNumber','g',142,['number':(costoEvitadoFotovoltaico[1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(23)
invokeTag('formatNumber','g',148,['number':(costoEvitadoFotovoltaico[2]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(24)
invokeTag('formatNumber','g',154,['number':(costoEvitadoFotovoltaico[3]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(25)
}
else {
printHtmlPart(2)
}
printHtmlPart(26)
createClosureForHtmlPart(27, 2)
invokeTag('jasperReport','g',196,['type':("hidden"),'jasper':("AhorrosElectricos"),'format':("PDF"),'name':("PDF"),'action':("createAhorrosElectricos"),'controller':("pdfUtil")],2)
printHtmlPart(28)
expressionOut.print(request.contextPath)
printHtmlPart(29)
expressionOut.print(request.contextPath)
printHtmlPart(30)
expressionOut.print(request.contextPath)
printHtmlPart(31)
expressionOut.print(request.contextPath)
printHtmlPart(32)
expressionOut.print(request.contextPath)
printHtmlPart(33)
expressionOut.print(request.contextPath)
printHtmlPart(34)
expressionOut.print(request.contextPath)
printHtmlPart(35)
createTagBody(2, {->
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[0])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[1])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[2])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[3])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[4])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[5])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[6])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[7])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[8])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[9])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[10])
printHtmlPart(39)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(40)
expressionOut.print(arregloGraficaValores2[11])
printHtmlPart(41)
})
invokeTag('javascript','g',306,[:],2)
printHtmlPart(42)
})
invokeTag('captureBody','sitemesh',308,[:],1)
printHtmlPart(43)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1438719694000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
