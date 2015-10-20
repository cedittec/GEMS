import com.GEMS1.Usuario
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_usuarioindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/usuario/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',6,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("frontend")],-1)
printHtmlPart(2)
invokeTag('set','g',7,['var':("entityName"),'value':(message(code: 'usuario.label', default: 'Usuario'))],-1)
printHtmlPart(2)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('message','g',8,['code':("default.list.label"),'args':([entityName])],-1)
})
invokeTag('captureTitle','sitemesh',8,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',8,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',10,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
createTagBody(2, {->
printHtmlPart(6)
if(true && (flash.message)) {
printHtmlPart(7)
}
else {
printHtmlPart(8)
}
printHtmlPart(9)
})
invokeTag('ifAnyGranted','sec',58,['roles':("ROLE_SysAdmin")],2)
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
loop:{
int i = 0
for( usuario in (usuarios) ) {
printHtmlPart(18)
if(true && ((i  == 1)&& (i != 0))) {
printHtmlPart(19)
}
printHtmlPart(18)
for( telefono in (usuario.telefono) ) {
printHtmlPart(20)
expressionOut.print(usuario.displayName)
printHtmlPart(21)
expressionOut.print(usuario.email)
printHtmlPart(21)
expressionOut.print(usuario.dateCreated)
printHtmlPart(21)
expressionOut.print(telefono.tipoTelefono)
printHtmlPart(21)
expressionOut.print(telefono.displayName)
printHtmlPart(22)
}
printHtmlPart(23)
i++
}
}
printHtmlPart(24)
createTagBody(2, {->
printHtmlPart(25)
if(true && (flash.message)) {
printHtmlPart(26)
}
else {
printHtmlPart(27)
if(true && (flash.error)) {
printHtmlPart(28)
expressionOut.print(flash.error)
printHtmlPart(29)
}
printHtmlPart(30)
}
printHtmlPart(31)
createTagBody(3, {->
printHtmlPart(32)
for( role in (roles) ) {
printHtmlPart(33)
expressionOut.print(role.displayName)
printHtmlPart(34)
}
printHtmlPart(35)
})
invokeTag('form','g',202,['name':("UserTelForm"),'action':("save2"),'id':("1")],3)
printHtmlPart(36)
createTagBody(3, {->
printHtmlPart(37)
for( email in (usuarios.email) ) {
printHtmlPart(38)
expressionOut.print(email)
printHtmlPart(39)
}
printHtmlPart(40)
})
invokeTag('form','g',228,['role':("form"),'style':("padding-left:10px ")],3)
printHtmlPart(41)
createTagBody(3, {->
printHtmlPart(42)
for( role in (roles) ) {
printHtmlPart(33)
expressionOut.print(role.displayName)
printHtmlPart(34)
}
printHtmlPart(43)
})
invokeTag('form','g',303,['name':("UserTelForm"),'action':("actualizarUsuario")],3)
printHtmlPart(44)
createTagBody(3, {->
printHtmlPart(45)
for( email in (usuarios.email) ) {
printHtmlPart(38)
expressionOut.print(email)
printHtmlPart(39)
}
printHtmlPart(46)
})
invokeTag('form','g',330,['role':("form"),'style':("padding-left:10px ")],3)
printHtmlPart(47)
createClosureForHtmlPart(48, 3)
invokeTag('form','g',397,['controller':("usuario"),'name':("UserDeleteForm"),'action':("eliminarUsuario")],3)
printHtmlPart(49)
})
invokeTag('ifAnyGranted','sec',402,['roles':("ROLE_SysAdmin")],2)
printHtmlPart(50)
expressionOut.print(request.contextPath)
printHtmlPart(51)
})
invokeTag('captureBody','sitemesh',613,[:],1)
printHtmlPart(52)
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
