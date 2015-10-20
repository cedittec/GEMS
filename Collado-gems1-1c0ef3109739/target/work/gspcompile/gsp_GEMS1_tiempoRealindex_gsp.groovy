import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_tiempoRealindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/tiempoReal/index.gsp" }
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
if(true && (numFuentes < 1)) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
for( fuente in (fuentes) ) {
printHtmlPart(10)
expressionOut.print(fuente.id)
printHtmlPart(11)
expressionOut.print(fuente.nombre)
printHtmlPart(12)
}
printHtmlPart(13)
}
printHtmlPart(14)
if(true && (numeroSitios < 1)) {
printHtmlPart(15)
}
else {
printHtmlPart(16)
for( sitio in (sitios) ) {
printHtmlPart(10)
expressionOut.print(sitio.id)
printHtmlPart(11)
expressionOut.print(sitio.nombre)
printHtmlPart(17)
}
printHtmlPart(18)
}
printHtmlPart(19)
if(true && (numeroSitios > 0)) {
printHtmlPart(20)
expressionOut.print(resultTempTanqueInercia)
printHtmlPart(21)
expressionOut.print(createLink(controller:'TiempoReal', action:'energiaTermica'))
printHtmlPart(22)
invokeTag('formatNumber','g',124,['number':(resultPotenciaTotal),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(23)
expressionOut.print(createLink(controller:'TiempoReal', action:'energiaElectrica'))
printHtmlPart(24)
invokeTag('formatNumber','g',142,['number':(comsutiblesArreglo[0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(25)
invokeTag('formatNumber','g',145,['number':(comsutiblesArreglo[1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(26)
expressionOut.print(createLink(controller:'TiempoReal', action:'nivelesCombustible'))
printHtmlPart(27)
invokeTag('formatNumber','g',165,['number':(radiacionSolar),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
invokeTag('formatNumber','g',181,['number':(nivelesHumedad),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(29)
invokeTag('formatNumber','g',197,['number':(temp),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(30)
expressionOut.print(request.contextPath)
printHtmlPart(31)
}
else {
printHtmlPart(2)
}
printHtmlPart(32)
createClosureForHtmlPart(33, 2)
invokeTag('jasperReport','g',247,['type':("hidden"),'jasper':("MonTRealIndex"),'format':("PDF"),'name':("PDF"),'action':("createMonTRealIndex"),'controller':("pdfUtil")],2)
printHtmlPart(34)
expressionOut.print(request.contextPath)
printHtmlPart(35)
expressionOut.print(request.contextPath)
printHtmlPart(36)
expressionOut.print(request.contextPath)
printHtmlPart(37)
expressionOut.print(request.contextPath)
printHtmlPart(38)
expressionOut.print(request.contextPath)
printHtmlPart(39)
expressionOut.print(request.contextPath)
printHtmlPart(40)
expressionOut.print(request.contextPath)
printHtmlPart(41)
expressionOut.print(request.contextPath)
printHtmlPart(42)
expressionOut.print(request.contextPath)
printHtmlPart(43)
})
invokeTag('captureBody','sitemesh',266,[:],1)
printHtmlPart(44)
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
