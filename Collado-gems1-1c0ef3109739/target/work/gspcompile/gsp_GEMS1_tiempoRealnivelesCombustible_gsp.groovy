import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_tiempoRealnivelesCombustible_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/tiempoReal/nivelesCombustible.gsp" }
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
if(true && (numFuentes < 1)) {
printHtmlPart(9)
}
else {
printHtmlPart(10)
for( fuente in (fuentes) ) {
printHtmlPart(11)
expressionOut.print(fuente.id)
printHtmlPart(12)
expressionOut.print(fuente.nombre)
printHtmlPart(13)
}
printHtmlPart(14)
}
printHtmlPart(15)
if(true && (numeroSitios < 1)) {
printHtmlPart(16)
}
else {
printHtmlPart(17)
for( sitio in (sitios) ) {
printHtmlPart(11)
expressionOut.print(sitio.id)
printHtmlPart(12)
expressionOut.print(sitio.nombre)
printHtmlPart(18)
}
printHtmlPart(19)
}
printHtmlPart(20)
if(true && (numeroSitios > 0)) {
printHtmlPart(21)
invokeTag('formatNumber','g',96,['number':(tempCombustibleTermico),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(22)
invokeTag('formatNumber','g',104,['number':(tempCombustibleCogeneracion),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(23)
expressionOut.print(resultadoNivelTanqueTermico)
printHtmlPart(24)
invokeTag('formatNumber','g',116,['number':(resultadoNivelTanqueTermico),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(25)
expressionOut.print(resultadoNivelTanqueCondensados)
printHtmlPart(26)
invokeTag('formatNumber','g',124,['number':(resultadoNivelTanqueCondensados),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(27)
invokeTag('formatNumber','g',134,['number':(presionCombustibleTermico),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
invokeTag('formatNumber','g',146,['number':(presionCombustibleCogeneracion),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(29)
}
else {
printHtmlPart(30)
}
printHtmlPart(31)
expressionOut.print(request.contextPath)
printHtmlPart(32)
expressionOut.print(request.contextPath)
printHtmlPart(33)
expressionOut.print(request.contextPath)
printHtmlPart(34)
expressionOut.print(request.contextPath)
printHtmlPart(35)
expressionOut.print(request.contextPath)
printHtmlPart(36)
expressionOut.print(request.contextPath)
printHtmlPart(37)
expressionOut.print(request.contextPath)
printHtmlPart(38)
createTagBody(2, {->
printHtmlPart(39)
expressionOut.print(graficaFlujoCombustibles[0])
printHtmlPart(40)
expressionOut.print(graficaFlujoCombustibles[1])
printHtmlPart(41)
})
invokeTag('javascript','g',217,[:],2)
printHtmlPart(42)
})
invokeTag('captureBody','sitemesh',219,[:],1)
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
