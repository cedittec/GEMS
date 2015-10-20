package com.GEMS1

class Rol {

	String authority
        String displayName

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
