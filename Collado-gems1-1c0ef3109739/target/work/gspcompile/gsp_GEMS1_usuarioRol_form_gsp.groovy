import com.GEMS1.UsuarioRol
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_GEMS1_usuarioRol_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/usuarioRol/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(hasErrors(bean: usuarioRolInstance, field: 'rol', 'error'))
printHtmlPart(1)
invokeTag('message','g',7,['code':("usuarioRol.rol.label"),'default':("Rol")],-1)
printHtmlPart(2)
invokeTag('select','g',10,['id':("rol"),'name':("rol.id"),'from':(com.GEMS1.Rol.list()),'optionKey':("id"),'required':(""),'value':(usuarioRolInstance?.rol?.id),'class':("many-to-one")],-1)
printHtmlPart(3)
expressionOut.print(hasErrors(bean: usuarioRolInstance, field: 'usuario', 'error'))
printHtmlPart(4)
invokeTag('message','g',16,['code':("usuarioRol.usuario.label"),'default':("Usuario")],-1)
printHtmlPart(2)
invokeTag('select','g',19,['id':("usuario"),'name':("usuario.id"),'from':(com.GEMS1.Usuario.list()),'optionKey':("id"),'required':(""),'value':(usuarioRolInstance?.usuario?.id),'class':("many-to-one")],-1)
printHtmlPart(5)
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
