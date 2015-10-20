package com.GEMS1



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured(['ROLE_SysAdmin'])
class UsuarioRolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UsuarioRol.list(params), model:[usuarioRolInstanceCount: UsuarioRol.count()]
    }

    def show(UsuarioRol usuarioRolInstance) {
        respond usuarioRolInstance
    }

    def create() {
        respond new UsuarioRol(params)
    }

    @Transactional
    def save(UsuarioRol usuarioRolInstance) {
        if (usuarioRolInstance == null) {
            notFound()
            return
        }

        if (usuarioRolInstance.hasErrors()) {
            respond usuarioRolInstance.errors, view:'create'
            return
        }

        usuarioRolInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), usuarioRolInstance.id])
                redirect usuarioRolInstance
            }
            '*' { respond usuarioRolInstance, [status: CREATED] }
        }
    }

    def edit(UsuarioRol usuarioRolInstance) {
        respond usuarioRolInstance
    }

    @Transactional
    def update(UsuarioRol usuarioRolInstance) {
        if (usuarioRolInstance == null) {
            notFound()
            return
        }

        if (usuarioRolInstance.hasErrors()) {
            respond usuarioRolInstance.errors, view:'edit'
            return
        }

        usuarioRolInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'UsuarioRol.label', default: 'UsuarioRol'), usuarioRolInstance.id])
                redirect usuarioRolInstance
            }
            '*'{ respond usuarioRolInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(UsuarioRol usuarioRolInstance) {

        if (usuarioRolInstance == null) {
            notFound()
            return
        }

        usuarioRolInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'UsuarioRol.label', default: 'UsuarioRol'), usuarioRolInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
