import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_configuracioncliente_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/configuracion/cliente.gsp" }
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
invokeTag('ifAnyGranted','sec',61,['roles':("ROLE_SysAdmin")],2)
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
invokeTag('set','g',100,['var':("counter"),'value':(0)],-1)
printHtmlPart(18)
for( cliente in (clientes) ) {
printHtmlPart(19)
for( telefono in (cliente.telefono) ) {
printHtmlPart(20)
expressionOut.print(cliente.razonSocial)
printHtmlPart(21)
expressionOut.print(responsables[counter].email)
printHtmlPart(21)
expressionOut.print(cliente.rfc)
printHtmlPart(21)
expressionOut.print(cliente.dateCreated)
printHtmlPart(22)
expressionOut.print(telefono.tipoTelefono)
printHtmlPart(21)
expressionOut.print(telefono.displayName)
printHtmlPart(23)
}
printHtmlPart(24)
invokeTag('set','g',113,['var':("counter"),'value':(counter + 1)],-1)
printHtmlPart(25)
}
printHtmlPart(26)
if(true && (flash.message)) {
printHtmlPart(27)
}
else {
printHtmlPart(28)
}
printHtmlPart(29)
createTagBody(2, {->
printHtmlPart(30)
for( email in (usuarios.email) ) {
printHtmlPart(31)
expressionOut.print(email)
printHtmlPart(32)
}
printHtmlPart(33)
})
invokeTag('form','g',231,['name':("configuracion"),'action':("saveCliente")],2)
printHtmlPart(34)
createTagBody(2, {->
printHtmlPart(35)
for( razonSocial in (clientes.razonSocial) ) {
printHtmlPart(31)
expressionOut.print(razonSocial)
printHtmlPart(32)
}
printHtmlPart(36)
})
invokeTag('form','g',342,['name':("configuracion"),'action':("modificarCliente")],2)
printHtmlPart(37)
createTagBody(2, {->
printHtmlPart(38)
for( razonSocial in (clientes.razonSocial) ) {
printHtmlPart(39)
expressionOut.print(razonSocial)
printHtmlPart(40)
}
printHtmlPart(41)
createClosureForHtmlPart(42, 3)
invokeTag('form','g',417,['controller':("configuracion"),'name':("UserDeleteForm"),'action':("inhabilitarCliente")],3)
printHtmlPart(43)
})
invokeTag('ifAnyGranted','sec',421,['roles':("ROLE_SysAdmin")],2)
printHtmlPart(44)
expressionOut.print(request.contextPath)
printHtmlPart(45)
})
invokeTag('captureBody','sitemesh',603,[:],1)
printHtmlPart(46)
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
