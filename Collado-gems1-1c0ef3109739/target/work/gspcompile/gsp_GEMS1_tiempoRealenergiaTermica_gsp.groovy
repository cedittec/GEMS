import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_tiempoRealenergiaTermica_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/tiempoReal/energiaTermica.gsp" }
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
expressionOut.print(resultTempTanqueInercia)
printHtmlPart(24)
expressionOut.print(resultTempTanqueInercia)
printHtmlPart(25)
expressionOut.print(resultTempTanqueServicios)
printHtmlPart(26)
invokeTag('formatNumber','g',112,['number':(resultTempTanqueServicios),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(27)
expressionOut.print(resultTempTanqueSistemaTermico)
printHtmlPart(28)
invokeTag('formatNumber','g',122,['number':(resultTempTanqueSistemaTermico),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(29)
expressionOut.print(resultTempTanqueCondensados)
printHtmlPart(30)
invokeTag('formatNumber','g',132,['number':(resultTempTanqueCondensados),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(31)
expressionOut.print(resultTempTanqueVapor)
printHtmlPart(32)
invokeTag('formatNumber','g',142,['number':(resultTempTanqueVapor),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(33)
expressionOut.print(resultPosicionDiverter)
printHtmlPart(34)
invokeTag('formatNumber','g',152,['number':(resultPosicionDiverter),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(35)
invokeTag('formatNumber','g',168,['number':(resultTempAireCalienteEntradaIntercambiador),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(36)
invokeTag('formatNumber','g',175,['number':(resultTempAireCalienteSalidaIntercambiador),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(37)
invokeTag('formatNumber','g',188,['number':(resultFlujoEconomizador),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(38)
invokeTag('formatNumber','g',200,['number':(resultFlujoVaporizador),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(39)
invokeTag('formatNumber','g',212,['number':(resultFlujoSupercalentador),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(40)
invokeTag('formatNumber','g',300,['number':(resultNivelTanqueServicio),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(41)
invokeTag('formatNumber','g',314,['number':(resultNivelTanqueInercia),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(42)
invokeTag('formatNumber','g',327,['number':(resultNivelTanqueCondensados),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(43)
invokeTag('formatNumber','g',340,['number':(resultNivelTanqueVapor),'type':("number"),'maxFractionDigits':("3")],-1)
printHtmlPart(44)
createClosureForHtmlPart(45, 3)
invokeTag('jasperReport','g',361,['type':("hidden"),'jasper':("MTRealETermica"),'format':("PDF"),'name':("PDF"),'action':("createMTRealETermica"),'controller':("pdfUtil")],3)
printHtmlPart(46)
}
else {
printHtmlPart(5)
}
printHtmlPart(47)
expressionOut.print(request.contextPath)
printHtmlPart(48)
expressionOut.print(request.contextPath)
printHtmlPart(49)
expressionOut.print(request.contextPath)
printHtmlPart(50)
expressionOut.print(request.contextPath)
printHtmlPart(51)
expressionOut.print(request.contextPath)
printHtmlPart(52)
expressionOut.print(request.contextPath)
printHtmlPart(53)
expressionOut.print(request.contextPath)
printHtmlPart(54)
expressionOut.print(request.contextPath)
printHtmlPart(55)
expressionOut.print(request.contextPath)
printHtmlPart(56)
expressionOut.print(request.contextPath)
printHtmlPart(57)
createTagBody(2, {->
printHtmlPart(58)
expressionOut.print(graficaFlujoCircuitoValores[0])
printHtmlPart(59)
expressionOut.print(graficaFlujoCircuitoValores[1])
printHtmlPart(60)
expressionOut.print(graficaFlujoCircuitoValores[2])
printHtmlPart(61)
expressionOut.print(graficaFlujoCircuitoValores[3])
printHtmlPart(62)
})
invokeTag('javascript','g',408,[:],2)
printHtmlPart(63)
createTagBody(2, {->
printHtmlPart(64)
expressionOut.print(graficaTempEntradaCog[0])
printHtmlPart(65)
expressionOut.print(graficaTempEntradaCog[1])
printHtmlPart(66)
expressionOut.print(graficaTempEntradaCog[1])
printHtmlPart(65)
expressionOut.print(graficaTempEntradaCog[2])
printHtmlPart(67)
expressionOut.print(graficaTempEntradaCog[3])
printHtmlPart(65)
expressionOut.print(graficaTempEntradaCog[4])
printHtmlPart(68)
})
invokeTag('javascript','g',428,[:],2)
printHtmlPart(63)
createTagBody(2, {->
printHtmlPart(69)
expressionOut.print(graficaTempAgua[0])
printHtmlPart(65)
expressionOut.print(graficaTempAgua[1])
printHtmlPart(70)
expressionOut.print(graficaTempAgua[1])
printHtmlPart(65)
expressionOut.print(graficaTempAgua[2])
printHtmlPart(71)
expressionOut.print(graficaTempAgua[3])
printHtmlPart(65)
expressionOut.print(graficaTempAgua[4])
printHtmlPart(72)
})
invokeTag('javascript','g',448,[:],2)
printHtmlPart(73)
createTagBody(2, {->
printHtmlPart(74)
expressionOut.print(graficaPresionTanquesCog[0])
printHtmlPart(75)
expressionOut.print(graficaPresionTanquesCog[1])
printHtmlPart(76)
expressionOut.print(graficaPresionTanquesCog[2])
printHtmlPart(77)
expressionOut.print(graficaPresionTanquesCog[3])
printHtmlPart(78)
expressionOut.print(graficaPresionTanquesCog[4])
printHtmlPart(79)
})
invokeTag('javascript','g',471,[:],2)
printHtmlPart(80)
})
invokeTag('captureBody','sitemesh',475,[:],1)
printHtmlPart(81)
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
