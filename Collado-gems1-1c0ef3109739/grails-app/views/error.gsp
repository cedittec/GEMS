<!DOCTYPE html>
<html>
	<head>
		<title><g:if env="development">Problema de conexi&oacute;n con GEMS Core</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="frontend">
		<g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
	</head>
	<body>
		<g:if env="development">
			<g:renderException exception="${exception}" />
		</g:if>
		<g:else>
			<ul class="errors">
				<li>El enlace con el Servidor GEMS Core se ha perdido</li>
			</ul>
		</g:else>
	</body>
</html>
