import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_tiempoRealenergiaElectrica_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/tiempoReal/energiaElectrica.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("frontend")],-1)
printHtmlPart(3)
invokeTag('captureMeta','sitemesh',13,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(3)
createTagBody(2, {->
createClosureForHtmlPart(4, 3)
invokeTag('captureTitle','sitemesh',14,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',14,[:],2)
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',15,[:],1)
printHtmlPart(6)
createTagBody(1, {->
printHtmlPart(7)
expressionOut.print(createLink(controller:'TiempoReal', action:'index'))
printHtmlPart(8)
if(true && (numeroSitios > 0)) {
printHtmlPart(9)
}
printHtmlPart(10)
if(true && (numFuentes < 1)) {
printHtmlPart(11)
}
else {
printHtmlPart(12)
for( fuente in (fuentes) ) {
printHtmlPart(13)
expressionOut.print(fuente.id)
printHtmlPart(14)
expressionOut.print(fuente.nombre)
printHtmlPart(15)
}
printHtmlPart(16)
}
printHtmlPart(17)
if(true && (numeroSitios < 1)) {
printHtmlPart(18)
}
else {
printHtmlPart(19)
for( sitio in (sitios) ) {
printHtmlPart(13)
expressionOut.print(sitio.id)
printHtmlPart(14)
expressionOut.print(sitio.nombre)
printHtmlPart(20)
}
printHtmlPart(21)
}
printHtmlPart(22)
if(true && (numeroSitios > 0)) {
printHtmlPart(23)
expressionOut.print(request.contextPath)
printHtmlPart(24)
invokeTag('formatNumber','g',111,['number':(resultadoPotenciaCFE),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(25)
invokeTag('formatNumber','g',130,['number':(resultadoPotenciaFotovoltaico),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(26)
invokeTag('formatNumber','g',149,['number':(resultadoPotenciaCogeneracion),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(27)
invokeTag('formatNumber','g',168,['number':(resultadoPotenciaTotal),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
createClosureForHtmlPart(29, 3)
invokeTag('jasperReport','g',204,['type':("hidden"),'jasper':("MTRealEElectrica"),'format':("PDF"),'name':("PDF"),'action':("createMTRealEElectrica"),'controller':("pdfUtil")],3)
printHtmlPart(30)
}
else {
printHtmlPart(3)
}
printHtmlPart(31)
expressionOut.print(request.contextPath)
printHtmlPart(32)
expressionOut.print(request.contextPath)
printHtmlPart(33)
expressionOut.print(request.contextPath)
printHtmlPart(34)
})
invokeTag('captureBody','sitemesh',220,[:],1)
printHtmlPart(35)
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
