import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_emisionescogeneracion_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/emisiones/cogeneracion.gsp" }
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
invokeTag('formatNumber','g',89,['number':(reduccionEmisionesGeneracionElectrica),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(17)
if(true && (combustibleNombre == "Gas LP")) {
printHtmlPart(18)
}
printHtmlPart(19)
if(true && (combustibleNombre == "Gas Natural")) {
printHtmlPart(20)
}
printHtmlPart(19)
if(true && (combustibleNombre == "Diesel")) {
printHtmlPart(21)
}
printHtmlPart(22)
if(true && (combustibleNombre == "Gas LP")) {
printHtmlPart(23)
invokeTag('formatNumber','g',115,['number':(reduccionEmisionesTotales[0][0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(24)
}
printHtmlPart(19)
if(true && (combustibleNombre == "Gas Natural")) {
printHtmlPart(25)
invokeTag('formatNumber','g',118,['number':(reduccionEmisionesTotales[1][0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(24)
}
printHtmlPart(19)
if(true && (combustibleNombre == "Diesel")) {
printHtmlPart(25)
invokeTag('formatNumber','g',121,['number':(reduccionEmisionesTotales[2][0]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(24)
}
printHtmlPart(26)
if(true && (combustibleNombre == "Gas LP")) {
printHtmlPart(27)
invokeTag('formatNumber','g',135,['number':(reduccionEmisionesTotales[0][1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(29)
if(true && (combustibleNombre == "Gas Natural")) {
printHtmlPart(27)
invokeTag('formatNumber','g',138,['number':(reduccionEmisionesTotales[1][1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(29)
if(true && (combustibleNombre == "Diesel")) {
printHtmlPart(27)
invokeTag('formatNumber','g',141,['number':(reduccionEmisionesTotales[2][1]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(30)
if(true && (combustibleNombre == "Gas LP")) {
printHtmlPart(27)
invokeTag('formatNumber','g',149,['number':(reduccionEmisionesTotales[0][2]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(29)
if(true && (combustibleNombre == "Gas Natural")) {
printHtmlPart(27)
invokeTag('formatNumber','g',152,['number':(reduccionEmisionesTotales[1][2]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(29)
if(true && (combustibleNombre == "Diesel")) {
printHtmlPart(27)
invokeTag('formatNumber','g',155,['number':(reduccionEmisionesTotales[2][2]),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(28)
}
printHtmlPart(31)
}
else {
printHtmlPart(2)
}
printHtmlPart(32)
createClosureForHtmlPart(33, 2)
invokeTag('jasperReport','g',191,['type':("hidden"),'jasper':("EmisionesCogeneracion"),'format':("PDF"),'name':("PDF"),'action':("createEmisionesCogeneracion"),'controller':("pdfUtil")],2)
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
createTagBody(2, {->
printHtmlPart(42)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(44)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(43)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(45)
})
invokeTag('javascript','g',286,[:],2)
printHtmlPart(46)
})
invokeTag('captureBody','sitemesh',288,[:],1)
printHtmlPart(47)
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
