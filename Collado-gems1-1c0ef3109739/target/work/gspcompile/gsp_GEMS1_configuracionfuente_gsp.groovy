import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_configuracionfuente_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/configuracion/fuente.gsp" }
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
if(true && (flash.message)) {
printHtmlPart(6)
}
else {
printHtmlPart(7)
}
printHtmlPart(8)
createClosureForHtmlPart(9, 2)
invokeTag('ifAnyGranted','sec',62,['roles':("ROLE_SysAdmin")],2)
printHtmlPart(10)
if(true && (flash.message)) {
printHtmlPart(11)
}
else {
printHtmlPart(12)
}
printHtmlPart(13)
if(true && (flash.message)) {
printHtmlPart(14)
expressionOut.print(flash.message)
printHtmlPart(15)
}
else {
printHtmlPart(16)
}
printHtmlPart(17)
for( fuente in (fuentesLista) ) {
printHtmlPart(18)
expressionOut.print(fuente.id)
printHtmlPart(19)
expressionOut.print(fuente.capacidadGeneracion)
printHtmlPart(19)
expressionOut.print(fuente.combustible)
printHtmlPart(19)
expressionOut.print(fuente.unidad)
printHtmlPart(20)
expressionOut.print(fuente.cantidad)
printHtmlPart(21)
}
printHtmlPart(22)
if(true && (flash.message)) {
printHtmlPart(23)
}
else {
printHtmlPart(24)
if(true && (flash.error)) {
printHtmlPart(25)
expressionOut.print(flash.error)
printHtmlPart(26)
}
printHtmlPart(27)
}
printHtmlPart(28)
createTagBody(2, {->
printHtmlPart(29)
for( sitio in (sitios) ) {
printHtmlPart(30)
expressionOut.print(sitio.id)
printHtmlPart(31)
expressionOut.print(sitio.nombre)
printHtmlPart(32)
}
printHtmlPart(33)
for( fuente in (fuentes) ) {
printHtmlPart(30)
expressionOut.print(fuente.id)
printHtmlPart(31)
expressionOut.print(fuente.tipo)
printHtmlPart(32)
}
printHtmlPart(34)
for( combustible in (combustibles) ) {
printHtmlPart(30)
expressionOut.print(combustible.id)
printHtmlPart(31)
expressionOut.print(combustible.nombre)
printHtmlPart(32)
}
printHtmlPart(35)
for( medida in (medidas) ) {
printHtmlPart(30)
expressionOut.print(medida.id)
printHtmlPart(31)
expressionOut.print(medida.abreviatura)
printHtmlPart(32)
}
printHtmlPart(36)
})
invokeTag('form','g',209,['name':("configuracion"),'action':("saveFuente")],2)
printHtmlPart(37)
createTagBody(2, {->
printHtmlPart(38)
for( sitio in (sitios) ) {
printHtmlPart(39)
expressionOut.print(sitio.id)
printHtmlPart(31)
expressionOut.print(sitio.nombre)
printHtmlPart(40)
}
printHtmlPart(41)
for( fuente in (fuentes) ) {
printHtmlPart(30)
expressionOut.print(fuente.id)
printHtmlPart(31)
expressionOut.print(fuente.tipo)
printHtmlPart(32)
}
printHtmlPart(42)
for( combustible in (combustibles) ) {
printHtmlPart(30)
expressionOut.print(combustible.id)
printHtmlPart(31)
expressionOut.print(combustible.nombre)
printHtmlPart(32)
}
printHtmlPart(43)
for( medida in (medidas) ) {
printHtmlPart(30)
expressionOut.print(medida.id)
printHtmlPart(31)
expressionOut.print(medida.abreviatura)
printHtmlPart(32)
}
printHtmlPart(44)
})
invokeTag('form','g',309,['name':("configuracion"),'action':("modificarFuenteSitio")],2)
printHtmlPart(45)
createTagBody(2, {->
printHtmlPart(46)
for( sitio in (sitios) ) {
printHtmlPart(39)
expressionOut.print(sitio.id)
printHtmlPart(31)
expressionOut.print(sitio.nombre)
printHtmlPart(40)
}
printHtmlPart(47)
for( servicio in (servicios) ) {
printHtmlPart(39)
expressionOut.print(servicio.id)
printHtmlPart(31)
expressionOut.print(servicio.nombre)
printHtmlPart(48)
expressionOut.print(servicio.descripcion)
printHtmlPart(40)
}
printHtmlPart(49)
})
invokeTag('form','g',391,['name':("configuracion"),'action':("crearServiciosDeFuente")],2)
printHtmlPart(50)
createTagBody(2, {->
printHtmlPart(51)
createTagBody(3, {->
printHtmlPart(52)
for( sitio in (sitios) ) {
printHtmlPart(39)
expressionOut.print(sitio.id)
printHtmlPart(31)
expressionOut.print(sitio.nombre)
printHtmlPart(40)
}
printHtmlPart(53)
})
invokeTag('form','g',464,['name':("configuracion"),'action':("inhabilitarFuente")],3)
printHtmlPart(54)
})
invokeTag('ifAnyGranted','sec',469,['roles':("ROLE_SysAdmin")],2)
printHtmlPart(55)
expressionOut.print(request.contextPath)
printHtmlPart(56)
})
invokeTag('captureBody','sitemesh',1004,[:],1)
printHtmlPart(57)
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
