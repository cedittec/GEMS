import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_emisionescolector_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/emisiones/colector.gsp" }
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
expressionOut.print(createLink(controller:'emisiones', action:'index'))
printHtmlPart(6)
if(true && (numeroSitios > 0)) {
printHtmlPart(7)
}
printHtmlPart(8)
if(true && (numeroSitios < 1)) {
printHtmlPart(9)
}
else {
printHtmlPart(10)
for( sitio in (sitios) ) {
printHtmlPart(11)
expressionOut.print(sitio.id)
printHtmlPart(12)
expressionOut.print(sitio.nombre)
printHtmlPart(13)
}
printHtmlPart(14)
}
printHtmlPart(15)
if(true && (numeroSitios > 0)) {
printHtmlPart(16)
expressionOut.print(request.contextPath)
printHtmlPart(17)
invokeTag('formatNumber','g',90,['number':(emisionCFE),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(18)
invokeTag('formatNumber','g',108,['number':(emisionColector),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(19)
invokeTag('formatNumber','g',126,['number':(reduccionEmisionesAmbiente),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(20)
}
else {
printHtmlPart(2)
}
printHtmlPart(21)
createClosureForHtmlPart(22, 2)
invokeTag('jasperReport','g',170,['type':("hidden"),'jasper':("EmisionesColectorSolar"),'format':("PDF"),'name':("PDF"),'action':("createEmisionesColectorSolar"),'controller':("pdfUtil")],2)
printHtmlPart(23)
expressionOut.print(request.contextPath)
printHtmlPart(24)
expressionOut.print(request.contextPath)
printHtmlPart(25)
expressionOut.print(request.contextPath)
printHtmlPart(26)
expressionOut.print(request.contextPath)
printHtmlPart(27)
expressionOut.print(request.contextPath)
printHtmlPart(28)
expressionOut.print(request.contextPath)
printHtmlPart(29)
expressionOut.print(request.contextPath)
printHtmlPart(30)
createTagBody(2, {->
printHtmlPart(31)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(32)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(34)
})
invokeTag('javascript','g',268,[:],2)
printHtmlPart(35)
})
invokeTag('captureBody','sitemesh',269,[:],1)
printHtmlPart(36)
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
