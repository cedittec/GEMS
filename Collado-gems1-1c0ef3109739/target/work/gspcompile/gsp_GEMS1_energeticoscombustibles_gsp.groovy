import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_energeticoscombustibles_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/energeticos/combustibles.gsp" }
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
expressionOut.print(createLink(controller:'energeticos', action:'index'))
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
expressionOut.print(tipoCombustibleCog)
printHtmlPart(17)
invokeTag('formatNumber','g',81,['number':(combustibleCogeneracionTotal[0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(18)
invokeTag('formatNumber','g',89,['number':(combustibleCogeneracionTotal[1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(19)
expressionOut.print(tipoCombustibleCog)
printHtmlPart(20)
invokeTag('formatNumber','g',102,['number':(combustibleTermicoTotal[0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(21)
invokeTag('formatNumber','g',110,['number':(combustibleTermicoTotal[1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(22)
}
else {
printHtmlPart(2)
}
printHtmlPart(23)
createClosureForHtmlPart(24, 2)
invokeTag('jasperReport','g',156,['type':("hidden"),'jasper':("EnergeticosCombustibles"),'format':("PDF"),'name':("PDF"),'action':("createEnergeticosCombustibles"),'controller':("pdfUtil")],2)
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
expressionOut.print(request.contextPath)
printHtmlPart(31)
expressionOut.print(request.contextPath)
printHtmlPart(32)
createTagBody(2, {->
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores2[0])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[1])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[2])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[3])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[4])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[5])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[6])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[7])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[8])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[9])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[10])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores2[11])
printHtmlPart(38)
})
invokeTag('javascript','g',263,[:],2)
printHtmlPart(39)
createTagBody(2, {->
printHtmlPart(33)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[0])
printHtmlPart(35)
expressionOut.print(arregloGraficaValores4[0])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[1])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[1])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[2])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[2])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[3])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[3])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[4])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[4])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[5])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[5])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[6])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[6])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[7])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[7])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[8])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[8])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[9])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[9])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[10])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[10])
printHtmlPart(36)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(34)
expressionOut.print(arregloGraficaValores3[11])
printHtmlPart(37)
expressionOut.print(arregloGraficaValores4[11])
printHtmlPart(40)
})
invokeTag('javascript','g',360,[:],2)
printHtmlPart(41)
})
invokeTag('captureBody','sitemesh',362,[:],1)
printHtmlPart(42)
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
