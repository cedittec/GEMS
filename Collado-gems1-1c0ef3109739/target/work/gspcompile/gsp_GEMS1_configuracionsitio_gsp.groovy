import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_configuracionsitio_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/configuracion/sitio.gsp" }
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
invokeTag('ifAnyGranted','sec',60,['roles':("ROLE_SysAdmin")],2)
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
for( sitio in (sitiosLista) ) {
printHtmlPart(18)
expressionOut.print(sitio.nombre)
printHtmlPart(19)
expressionOut.print(sitio.cliente)
printHtmlPart(19)
expressionOut.print(sitio.region)
printHtmlPart(19)
expressionOut.print(sitio.tarifa)
printHtmlPart(19)
expressionOut.print(sitio.telefono)
printHtmlPart(19)
expressionOut.print(sitio.estatus)
printHtmlPart(20)
}
printHtmlPart(21)
if(true && (flash.message)) {
printHtmlPart(22)
}
else {
printHtmlPart(23)
if(true && (flash.error)) {
printHtmlPart(24)
expressionOut.print(flash.error)
printHtmlPart(25)
}
printHtmlPart(26)
}
printHtmlPart(27)
createTagBody(2, {->
printHtmlPart(28)
for( cliente in (clientes) ) {
printHtmlPart(29)
expressionOut.print(cliente.id)
printHtmlPart(30)
expressionOut.print(cliente.razonSocial)
printHtmlPart(31)
}
printHtmlPart(32)
for( region in (regiones) ) {
printHtmlPart(33)
expressionOut.print(region.id)
printHtmlPart(30)
expressionOut.print(region.nombre)
printHtmlPart(31)
}
printHtmlPart(34)
for( tarifa in (tarifas) ) {
printHtmlPart(29)
expressionOut.print(tarifa.id)
printHtmlPart(30)
expressionOut.print(tarifa.nombre)
printHtmlPart(31)
}
printHtmlPart(35)
})
invokeTag('form','g',313,['name':("configuracion"),'action':("saveSitio")],2)
printHtmlPart(36)
createTagBody(2, {->
printHtmlPart(37)
for( nombre in (sitios.nombre) ) {
printHtmlPart(38)
expressionOut.print(nombre)
printHtmlPart(39)
}
printHtmlPart(40)
for( region in (regiones) ) {
printHtmlPart(41)
expressionOut.print(region.id)
printHtmlPart(30)
expressionOut.print(region.nombre)
printHtmlPart(42)
}
printHtmlPart(43)
for( tarifa in (tarifas) ) {
printHtmlPart(41)
expressionOut.print(tarifa.id)
printHtmlPart(30)
expressionOut.print(tarifa.nombre)
printHtmlPart(42)
}
printHtmlPart(44)
})
invokeTag('form','g',503,['name':("configuracion"),'action':("modificarSitio")],2)
printHtmlPart(45)
createTagBody(2, {->
printHtmlPart(46)
for( sitio in (sitios) ) {
printHtmlPart(47)
expressionOut.print(sitio.nombre)
printHtmlPart(48)
}
printHtmlPart(49)
for( usuario in (usuarios) ) {
printHtmlPart(50)
expressionOut.print(usuario.email)
printHtmlPart(30)
expressionOut.print(usuario.displayName)
printHtmlPart(51)
expressionOut.print(usuario.email)
printHtmlPart(39)
}
printHtmlPart(52)
})
invokeTag('form','g',576,['name':("configuracion"),'action':("crearAccesoASitio")],2)
printHtmlPart(53)
createTagBody(2, {->
printHtmlPart(54)
for( sitio in (sitios) ) {
printHtmlPart(55)
expressionOut.print(sitio.nombre)
printHtmlPart(56)
}
printHtmlPart(57)
createClosureForHtmlPart(58, 3)
invokeTag('form','g',649,['controller':("configuracion"),'name':("UserDeleteForm"),'action':("inhabilitarSitio")],3)
printHtmlPart(59)
})
invokeTag('ifAnyGranted','sec',653,['roles':("ROLE_SysAdmin")],2)
printHtmlPart(60)
expressionOut.print(request.contextPath)
printHtmlPart(61)
})
invokeTag('captureBody','sitemesh',1027,[:],1)
printHtmlPart(62)
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
