package com.GEMS1

class Usuario {
	transient springSecurityService
        
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
       
	String password
        String nombre1
        String apellido1
        
        String apellido2
        String displayName
        Date dateCreated
        Date lastUpdated
       
       // static belongsTo = com.GEMS1.Sitio
        static hasMany = [telefono:TelefonoUsuario, usuarioSitio:UsuarioSitio]
    
        //static belongsTo = [createdBy:Usuario]        
       // static hasMany = [clienteResponsable: Cliente, telefono:TelefonoUsuario, usuario:UsuarioSitio]
       // static hasMany = [telefono: TelefonoUsuario]
        
	static transients = ['springSecurityService']

	static constraints = {
		password blank: false
                email email: true, blank: false, unique:true          
                displayName nullable:true
                
                apellido1 maxSize: 35
                apellido2 nullable:true, maxSize: 35 
                
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this).collect { it.rol }
	}

	def beforeInsert() {
                displayName = nombre1 +" "+ apellido1
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
