<%@ page import="com.GEMS1.Usuario" %>



<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="usuario.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="password" required="" value="${usuarioInstance?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="usuario.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${usuarioInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'nombre1', 'error')} required">
	<label for="nombre1">
		<g:message code="usuario.nombre1.label" default="Nombre1" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre1" maxlength="15" required="" value="${usuarioInstance?.nombre1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'nombre2', 'error')} required">
	<label for="nombre2">
		<g:message code="usuario.nombre2.label" default="Nombre2" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre2" maxlength="15" required="" value="${usuarioInstance?.nombre2}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'apellido1', 'error')} required">
	<label for="apellido1">
		<g:message code="usuario.apellido1.label" default="Apellido1" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apellido1" maxlength="35" required="" value="${usuarioInstance?.apellido1}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'apellido2', 'error')} required">
	<label for="apellido2">
		<g:message code="usuario.apellido2.label" default="Apellido2" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="apellido2" maxlength="35" required="" value="${usuarioInstance?.apellido2}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'accountExpired', 'error')} ">
	<label for="accountExpired">
		<g:message code="usuario.accountExpired.label" default="Account Expired" />
		
	</label>
	<g:checkBox name="accountExpired" value="${usuarioInstance?.accountExpired}" />

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'accountLocked', 'error')} ">
	<label for="accountLocked">
		<g:message code="usuario.accountLocked.label" default="Account Locked" />
		
	</label>
	<g:checkBox name="accountLocked" value="${usuarioInstance?.accountLocked}" />

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'displayName', 'error')} required">
	<label for="displayName">
		<g:message code="usuario.displayName.label" default="Display Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="displayName" required="" value="${usuarioInstance?.displayName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="usuario.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${usuarioInstance?.enabled}" />

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'passwordExpired', 'error')} ">
	<label for="passwordExpired">
		<g:message code="usuario.passwordExpired.label" default="Password Expired" />
		
	</label>
	<g:checkBox name="passwordExpired" value="${usuarioInstance?.passwordExpired}" />

</div>

