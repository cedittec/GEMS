import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_estadoFinancieroindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/estadoFinanciero/index.gsp" }
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
if(true && (numeroSitios > 0)) {
printHtmlPart(6)
}
printHtmlPart(7)
if(true && (numeroSitios < 1)) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
for( sitio in (sitios) ) {
printHtmlPart(10)
expressionOut.print(sitio.id)
printHtmlPart(11)
expressionOut.print(sitio.nombre)
printHtmlPart(12)
}
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (numeroSitios > 0)) {
printHtmlPart(15)
invokeTag('jasperReport','g',73,['jasper':("EstadoFinancieroIndex"),'format':("PDF"),'name':("PDF"),'action':("createEstadoFinancieroIndex"),'controller':("pdfUtil")],-1)
printHtmlPart(16)
invokeTag('formatNumber','g',91,['number':(resultadoCostos)],-1)
printHtmlPart(17)
expressionOut.print(createLink(controller:'CostosGlobales', action:'index'))
printHtmlPart(18)
invokeTag('formatNumber','g',114,['number':(resultadoAhorros)],-1)
printHtmlPart(19)
expressionOut.print(createLink(controller:'AhorrosGlobales', action:'index'))
printHtmlPart(20)
createTagBody(3, {->
printHtmlPart(21)
invokeTag('formatNumber','g',137,['number':(resultadoFlujoEfectivo)],-1)
printHtmlPart(22)
})
invokeTag('ifNotGranted','sec',144,['roles':("ROLE_adminVentas")],3)
printHtmlPart(23)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(24)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(25)
}
else {
printHtmlPart(26)
}
printHtmlPart(27)
createClosureForHtmlPart(28, 2)
invokeTag('jasperReport','g',202,['type':("hidden"),'jasper':("EstadoFinancieroIndex"),'format':("PDF"),'name':("PDF"),'action':("createEstadoFinancieroIndex"),'controller':("pdfUtil")],2)
printHtmlPart(29)
expressionOut.print(request.contextPath)
printHtmlPart(30)
expressionOut.print(request.contextPath)
printHtmlPart(31)
expressionOut.print(request.contextPath)
printHtmlPart(32)
expressionOut.print(request.contextPath)
printHtmlPart(33)
createTagBody(2, {->
printHtmlPart(34)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(36)
expressionOut.print(arregloGraficaValores2[0])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[1])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[2])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[3])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[4])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[5])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[6])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[7])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[8])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[9])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[10])
printHtmlPart(37)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(38)
expressionOut.print(arregloGraficaValores2[11])
printHtmlPart(39)
})
invokeTag('javascript','g',308,[:],2)
printHtmlPart(40)
})
invokeTag('captureBody','sitemesh',310,[:],1)
printHtmlPart(41)
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
