import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_estadoFinancieroROI_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/estadoFinanciero/ROI.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',8,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("frontend")],-1)
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',9,['gsp_sm_xmlClosingForEmptyTag':(""),'http-equiv':("Content-Type"),'content':("text/html; charset=UTF-8")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',10,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',10,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',11,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
if(true && (numeroSitios > 0)) {
printHtmlPart(5)
}
printHtmlPart(6)
if(true && (numeroSitios < 1)) {
printHtmlPart(7)
}
else {
printHtmlPart(8)
for( sitio in (sitios) ) {
printHtmlPart(9)
expressionOut.print(sitio.id)
printHtmlPart(10)
expressionOut.print(sitio.nombre)
printHtmlPart(11)
}
printHtmlPart(12)
}
printHtmlPart(13)
if(true && (numeroSitios > 0)) {
printHtmlPart(14)
invokeTag('formatNumber','g',87,['number':(costoCombustibleROI),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(15)
}
printHtmlPart(16)
createClosureForHtmlPart(17, 2)
invokeTag('jasperReport','g',122,['type':("hidden"),'jasper':("FlujoDeEfectivo"),'format':("PDF"),'name':("PDF"),'action':("createFlujoDeEfectivo"),'controller':("pdfUtil")],2)
printHtmlPart(18)
expressionOut.print(request.contextPath)
printHtmlPart(19)
expressionOut.print(request.contextPath)
printHtmlPart(20)
expressionOut.print(request.contextPath)
printHtmlPart(21)
expressionOut.print(request.contextPath)
printHtmlPart(22)
expressionOut.print(request.contextPath)
printHtmlPart(23)
expressionOut.print(request.contextPath)
printHtmlPart(24)
expressionOut.print(request.contextPath)
printHtmlPart(25)
createTagBody(2, {->
printHtmlPart(26)
expressionOut.print(arregloGraficaMeses[0])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[0])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[1])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[1])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[2])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[2])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[3])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[3])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[4])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[4])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[5])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[5])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[6])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[6])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[7])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[7])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[8])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[8])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[9])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[9])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[10])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[10])
printHtmlPart(28)
expressionOut.print(arregloGraficaMeses[11])
printHtmlPart(27)
expressionOut.print(arregloGraficaValores[11])
printHtmlPart(29)
})
invokeTag('javascript','g',220,[:],2)
printHtmlPart(30)
})
invokeTag('captureBody','sitemesh',222,[:],1)
printHtmlPart(31)
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
