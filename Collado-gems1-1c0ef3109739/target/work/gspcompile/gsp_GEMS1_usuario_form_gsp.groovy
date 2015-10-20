import com.GEMS1.Usuario
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_usuario_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/usuario/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'password', 'error'))
printHtmlPart(1)
invokeTag('message','g',7,['code':("usuario.password.label"),'default':("Password")],-1)
printHtmlPart(2)
invokeTag('textField','g',10,['name':("password"),'required':(""),'value':(usuarioInstance?.password)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'email', 'error'))
printHtmlPart(4)
invokeTag('message','g',16,['code':("usuario.email.label"),'default':("Email")],-1)
printHtmlPart(2)
invokeTag('field','g',19,['type':("email"),'name':("email"),'required':(""),'value':(usuarioInstance?.email)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'nombre1', 'error'))
printHtmlPart(5)
invokeTag('message','g',25,['code':("usuario.nombre1.label"),'default':("Nombre1")],-1)
printHtmlPart(2)
invokeTag('textField','g',28,['name':("nombre1"),'maxlength':("15"),'required':(""),'value':(usuarioInstance?.nombre1)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'nombre2', 'error'))
printHtmlPart(6)
invokeTag('message','g',34,['code':("usuario.nombre2.label"),'default':("Nombre2")],-1)
printHtmlPart(2)
invokeTag('textField','g',37,['name':("nombre2"),'maxlength':("15"),'required':(""),'value':(usuarioInstance?.nombre2)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'apellido1', 'error'))
printHtmlPart(7)
invokeTag('message','g',43,['code':("usuario.apellido1.label"),'default':("Apellido1")],-1)
printHtmlPart(2)
invokeTag('textField','g',46,['name':("apellido1"),'maxlength':("35"),'required':(""),'value':(usuarioInstance?.apellido1)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'apellido2', 'error'))
printHtmlPart(8)
invokeTag('message','g',52,['code':("usuario.apellido2.label"),'default':("Apellido2")],-1)
printHtmlPart(2)
invokeTag('textField','g',55,['name':("apellido2"),'maxlength':("35"),'required':(""),'value':(usuarioInstance?.apellido2)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'accountExpired', 'error'))
printHtmlPart(9)
invokeTag('message','g',61,['code':("usuario.accountExpired.label"),'default':("Account Expired")],-1)
printHtmlPart(10)
invokeTag('checkBox','g',64,['name':("accountExpired"),'value':(usuarioInstance?.accountExpired)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'accountLocked', 'error'))
printHtmlPart(11)
invokeTag('message','g',70,['code':("usuario.accountLocked.label"),'default':("Account Locked")],-1)
printHtmlPart(10)
invokeTag('checkBox','g',73,['name':("accountLocked"),'value':(usuarioInstance?.accountLocked)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'displayName', 'error'))
printHtmlPart(12)
invokeTag('message','g',79,['code':("usuario.displayName.label"),'default':("Display Name")],-1)
printHtmlPart(2)
invokeTag('textField','g',82,['name':("displayName"),'required':(""),'value':(usuarioInstance?.displayName)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'enabled', 'error'))
printHtmlPart(13)
invokeTag('message','g',88,['code':("usuario.enabled.label"),'default':("Enabled")],-1)
printHtmlPart(10)
invokeTag('checkBox','g',91,['name':("enabled"),'value':(usuarioInstance?.enabled)],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioInstance, field: 'passwordExpired', 'error'))
printHtmlPart(14)
invokeTag('message','g',97,['code':("usuario.passwordExpired.label"),'default':("Password Expired")],-1)
printHtmlPart(10)
invokeTag('checkBox','g',100,['name':("passwordExpired"),'value':(usuarioInstance?.passwordExpired)],-1)
printHtmlPart(15)
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
