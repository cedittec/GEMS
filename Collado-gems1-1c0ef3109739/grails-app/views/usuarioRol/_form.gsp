<%@ page import="com.GEMS1.UsuarioRol" %>



<div class="fieldcontain ${hasErrors(bean: usuarioRolInstance, field: 'rol', 'error')} required">
	<label for="rol">
		<g:message code="usuarioRol.rol.label" default="Rol" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="rol" name="rol.id" from="${com.GEMS1.Rol.list()}" optionKey="id" required="" value="${usuarioRolInstance?.rol?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioRolInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="usuarioRol.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${com.GEMS1.Usuario.list()}" optionKey="id" required="" value="${usuarioRolInstance?.usuario?.id}" class="many-to-one"/>

</div>

