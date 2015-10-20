
<%@ page import="com.GEMS1.Usuario" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="frontend">
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>

    </head>
    <body>

        <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Configuraci&oacute;n
                            <small>Usuarios</small>
                        </h1>
                        <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                            <li style="padding-top:6px"><i class="fa fa-gear"></i> Configuraci&oacute;n</li>
                            <li class="active" style="padding-top:6px"> Usuarios</li>

                        </ol>

                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <!-- end PAGE TITLE AREA -->



            <div class="row">
                <div class="col-lg-12">

                    <div class="portlet portlet-default">
                        <div class="portlet-body">
                            <ul id="userTab" class="nav nav-tabs">

                                <sec:ifAnyGranted roles="ROLE_SysAdmin">     
                                    <g:if test="${flash.message}">
                                        <li><a href="#alta" data-toggle="tab">Crear usuario</a>
                                        </g:if>
                                        <g:else>
                                        <li class="active"><a href="#alta" data-toggle="tab">Crear usuario</a>
                                        </g:else>
                                    </li>

                                    <li><a href="#cambio" data-toggle="tab">Modificar usuario</a>
                                    </li>
                                    <li><a href="#baja" data-toggle="tab">Eliminar usuario</a>
                                    </li>        
                                </sec:ifAnyGranted> 
                                <g:if test="${flash.message}">
                                    <li class="active"><a href="#ver" data-toggle="tab">Usuarios</a>
                                    </g:if>
                                    <g:else>
                                    <li><a href="#ver" data-toggle="tab">Usuarios</a>
                                    </g:else>


                                </li>

                            </ul>
                            <div id="userTabContent" class="tab-content">

                                <g:if test="${flash.message}">
                                    <div class="tab-pane fade in active" id="ver">
                                        <div class="alert alert-success alert-dismissable">
                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                            <strong>Creaci&oacute;n exitosa:</strong> ${flash.message}
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <div class="tab-pane fade" id="ver">

                                        </g:else>

                                        <h3>Usuarios</h3>
                                        <div class="table-responsive">
                                            <table id ="paginated" class="table table-hover table-bordered table-striped">

                                                <g:each in="${usuarios}" status="i" var="usuario" >
                                                    <g:if test = "${(i  == 1)&& (i != 0)}">
                                                        <tr>
                                                            <th>Usuario</th>
                                                            <th>Correo electr&oacute;nico</th>
                                                            <th>Fecha de creaci&oacute;n</th>
                                                            <th colspan="2">Tel&eacute;fono</th>
                                                            

                                                        </tr>
                                                    </g:if>
                                                    <g:each var="telefono"  in="${usuario.telefono}" >
                                                        <tr>
                                                            <td>${usuario.displayName}</td>
                                                            <td>${usuario.email}</td>
                                                            <td>${usuario.dateCreated}</td>
                                                            <td>${telefono.tipoTelefono}</td>
                                                            <td>${telefono.displayName}</td>

                                                        </tr>
                                                    </g:each>     
                                                </g:each>  
                                            </table>
                                            <div id="pageNavPosition"></div>

                                        </div>

                                    </div>

                                    <sec:ifAnyGranted roles="ROLE_SysAdmin">

                                        <g:if test="${flash.message}">
                                            <div class="tab-pane fade" id="alta">
                                            </g:if>
                                            <g:else>
                                                <div class="tab-pane fade in active" id="alta">
                                                    <g:if test="${flash.error}">
                                                        <div class="alert alert-danger alert-dismissable">
                                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                                            <strong>Ha ocurrido un error - </strong> ${flash.error}
                                                        </div>
                                                    </g:if>
                                                </g:else>

                                                <div class="row">

                                                    <div class="col-sm-12">
                                                        <div id="userSettingsContent" class="tab-content">
                                                            <div class="tab-pane fade in active" id="basicInformation">

                                                                <g:form name="UserTelForm" action="save2" id="1">

                                                                    <h4 class="page-header">Informaci&oacute;n</h4>
                                                                    <div class="form-group">                                                        
                                                                        <label>Nombre</label>
                                                                        <div class="form-inline">    
                                                                            <div class="form-group" style="width:49%">
                                                                                <input required name="nombre1" type="text" class="form-control" placeholder="Nombre(s)" style="width:100%">                                                                            
                                                                            </div>

                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">                                                        
                                                                        <label>Apellidos</label>
                                                                        <div class="form-inline">    
                                                                            <div class="form-group" style="width:49%">
                                                                                <input required name="apellido1" type="text" class="form-control" placeholder="Apellido Paterno" maxlength="35" style="width:100%">
                                                                            </div>
                                                                            <div class="form-group" style="width:49%">
                                                                                <input name="apellido2" type="text" class="form-control" placeholder="Apellido Materno" maxlength="35" style="width:100%">
                                                                            </div>
                                                                        </div>
                                                                    </div>


                                                                    <div class="form-group">
                                                                        <label><i class="fa fa-building-o fa-fw"></i> Rol</label>
                                                                        <select class="form-control" name="rol">
                                                                            <g:each in="${roles}" var="role" >
                                                                                <option>${role.displayName}</option>                                                                       
                                                                            </g:each>
                                                                        </select>

                                                                    </div>

                                                                    <h4 class="page-header">Detalles de contacto:</h4>

                                                                    <div class="form-group">                                                        
                                                                        <label>N&uacute;mero telef&oacute;nico</label>
                                                                        <div class="form-inline" style="padding-left:25px">   
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Tipo de tel&eacute;fono</label>
                                                                                <select class="form-control" name="tipoTelefono">
                                                                                    <option>Celular</option>
                                                                                    <option>Trabajo</option>
                                                                                    <option>Casa</option>
                                                                                </select>

                                                                            </div> 
                                                                           
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Tel&eacute;fono</label>
                                                                                <input required name="numTelefono" type="tel" class="form-control" placeholder="(##)1234567891" maxlength="10" size="10"  >
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label><i class="fa fa-envelope-o fa-fw"></i> Correo electr&oacute;nico</label>
                                                                        <input required name="email" type="email" class="form-control" placeholder="ejemplo@ejemplo.com">
                                                                    </div>

                                                                    <button type="submit" class="btn btn-default">Crear usuario</button>
                                                                    <button class="btn btn-green">Cancelar</button>
                                                                </g:form>
                                                            </div>


                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="tab-pane fade" id="cambio">
                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                    <g:form role="form" style="padding-left:10px ">
                                                        <h4 class="page-header">Selecci&oacute;n de usuario:</h4>
                                                        <div class="form-group" style="padding:20px">

                                                            <label><i class="fa fa-users fa-fw"></i> Usuario</label>                                                    

                                                            <input required type="text" class="form-control" name="emailUsuarioSearch" id="buscarModificar" placeholder="Correo electr&oacute;nico" list="searchresults" autocomplete="off">
                                                            <datalist id="searchresults">
                                                                <g:each in="${usuarios.email}" var="email" >
                                                                    <option>${email}</option>                                                                       
                                                                </g:each>

                                                            </datalist>
                                                            <button type="button" class="btn btn-green" onClick="cargarDatos();">Buscar usuario</button>
                                                        </div>
                                                    </g:form>
                                                </div>

                                                <div style="display:none" id="divModificarUsuario" class="row">
                                                    <div class="col-sm-3">
                                                        <ul id="userSettings" class="nav nav-pills nav-stacked">
                                                            <li class="active"><a href="#basicInformationCambios" data-toggle="tab"><i class="fa fa-user fa-fw"></i> Informaci&oacute;n</a>
                                                            </li>                                                       
                                                        </ul>
                                                    </div>
                                                    <div class="col-sm-9">
                                                        <div id="userSettingsContent" class="tab-content">
                                                            <div class="tab-pane fade in active" id="basicInformationCambios">
                                                                <g:form name="UserTelForm" action="actualizarUsuario">

                                                                    <h4 class="page-header">Informaci&oacute;n:</h4>
                                                                    <div class="form-group">                                                        
                                                                        <label>Nombre</label>
                                                                        <div class="form-inline">    
                                                                            <div class="form-group" style="width:49%">
                                                                                <input required id="inputNombre" name="nombre1" type="text" class="form-control" placeholder="Nombre(s)" style="width:100%">                                                                            
                                                                            </div>

                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">                                                        
                                                                        <label>Apellidos</label>
                                                                        <div class="form-inline">    
                                                                            <div class="form-group" style="width:49%">
                                                                                <input required id="inputApellido1" name="apellido1" type="text" class="form-control" placeholder="Apellido Paterno" maxlength="35" style="width:100%">
                                                                            </div>
                                                                            <div class="form-group" style="width:49%">
                                                                                <input id="inputApellido2" name="apellido2" type="text" class="form-control" placeholder="Apellido Materno" maxlength="35" style="width:100%">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label><i class="fa fa-building-o fa-fw"></i> Rol</label>
                                                                        <select id="inputRol" class="form-control" name="rol">
                                                                            <g:each in="${roles}" var="role" >
                                                                                <option>${role.displayName}</option>                                                                       
                                                                            </g:each>
                                                                        </select>

                                                                        <input  type="hidden"  class="form-control" name="email" id="correoUsuarioModificar" placeholder="" autocomplete="off">

                                                                    </div>

                                                                    <h4 class="page-header">Detalles de contacto:</h4>

                                                                    <div class="form-group">    

                                                                        <label>N&uacute;mero telef&oacute;nico</label>
                                                                        <div id="telArea" class="form-inline" style="padding-left:25px">   
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Tipo de tel&eacute;fono</label>
                                                                                <select id="inputTipoTelefono" class="form-control" name="tipoTelefono">
                                                                                    <option>Celular</option>
                                                                                    <option>Trabajo</option>
                                                                                    <option>Casa</option>
                                                                                </select>

                                                                            </div>                                                                             
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Tel&eacute;fono</label>
                                                                                <input required id="inputNumTelefono" name="numTelefono" type="tel" class="form-control" placeholder="(##)1234567890" maxlength="10" size="10" >
                                                                            </div>
                                                                        </div>

                                                                    </div>

                                                                    <button type="submit" class="btn btn-default">Guardar cambios</button>
                                                                    <button class="btn btn-green">Cancelar</button>
                                                                </g:form>
                                                            </div>


                                                        </div>
                                                    </div>
                                                </div>

                                            </div>

                                            <div class="tab-pane fade" id="baja">
                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                    <g:form role="form" style="padding-left:10px ">
                                                        <h4 class="page-header">Selecci&oacute;n de usuario:</h4>
                                                        <div class="form-group" style="padding:20px">

                                                            <label><i class="fa fa-users fa-fw"></i> Usuario</label>                                                    

                                                            <input required type="text" class="form-control" name="emailUsuarioSearch" id="buscarEliminar" placeholder="Correo electr&oacute;nico" list="searchresults" autocomplete="off">
                                                            <datalist id="searchresults">
                                                                <g:each in="${usuarios.email}" var="email" >
                                                                    <option>${email}</option>                                                                       
                                                                </g:each>

                                                            </datalist>
                                                            <button type="button" class="btn btn-green" onclick="cargarDatosEliminar();">Buscar usuario</button>
                                                        </div>
                                                    </g:form>
                                                </div>
                                                <div style="display:none" id="divDatosABorrar" class="row">

                                                    <div class="col-lg-9 col-md-8">
                                                        <h1 id="nombreTitulo"></h1>

                                                        <ul class="list-inline">
                                                            <li id="rolTitulo"><i  class="fa fa-user fa-muted"></i> </li>
                                                            <li id="fechaTitulo" ><i   class="fa fa-calendar fa-muted"></i> Miembro desde: </li>
                                                        </ul>



                                                        <div style="display:none" id="divTablaResponsable" class="table-responsive">
                                                            <h3>El cliente es responsable de</h3>
                                                            <table class="table table-hover table-bordered table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Matriz</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodySitios">
                                                                    <tr>

                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </div>

                                                        <div style="display:none" id="divTablaAccesos" class="table-responsive">
                                                            <h3>Permisos de accesos a:</h3>
                                                            <table class="table table-hover table-bordered table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Sitio</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodyAccesos">
                                                                    <tr>

                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </div>

                                                    </div>
                                                    <g:form controller="usuario" name="UserDeleteForm" action="eliminarUsuario">
                                                        <input type="hidden" name="email" id="inputOcultoEmail" autocomplete="off">
                                                        <div class="col-lg-3 col-md-4">
                                                            <h3>Informaci&oacute;n </h3>


                                                            <p id="nombreTarjeta"><i class="fa fa-user fa-muted fa-fw"></i> </p>
                                                            <p id="telefonoTarjeta"><i class="fa fa-phone fa-muted fa-fw"></i> </p>
                                                            <p  id="correoTarjeta"><i class="fa fa-envelope-o fa-muted fa-fw"></i></a>


                                                            </p>
                                                            <div class="list-group">
                                                                <button type="submit" class="btn btn-default">Eliminar usuario</button>
                                                            </div>


                                                        </div>
                                                    </g:form>
                                                </div>

                                            </div>

                                    </sec:ifAnyGranted> 
<!-- tabs-->
                                </div>
                            </div>
                            <!-- /.portlet-body -->
                        </div>
                        <!-- /.portlet -->


                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
        <script>                
            function cargarDatos(){                                
            var inputText = document.getElementById("buscarModificar").value;

            $('#divModificarUsuario').hide();    

            if (inputText == "")
            return;

            var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange=function()
            {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
            //document.getElementById("basicInformationCambios").innerHTML=xmlhttp.responseText;

            $('#divModificarUsuario').show();    

            var usuarioXml = xmlhttp.responseText;
            xmlDoc = $.parseXML( usuarioXml ),
            $usuarioXml = $( xmlDoc ),

            $title = $usuarioXml.find( "nombre1" );
            $('#inputNombre').val($title.text()) ;


            $title = $usuarioXml.find( "apellido1" );
            $('#inputApellido1').val($title.text()) ;


            $title = $usuarioXml.find( "apellido2" );
            $('#inputApellido2').val($title.text()) ;

            $('#correoUsuarioModificar').val($('#buscarModificar').val());

            $title = $usuarioXml.find( "apellido2" );
            $('#inputRol').val($title.text()) ;


            var displayName;                         
            $(usuarioXml).find('rol').each(function () 
            {
            var id, name;
            //id          = $(this).attr('id'); 
            //name        = $(this).children('authority').text();   

            displayName = $(this).children('displayName').text();                            
            });
            $('#inputRol').val(displayName) ;


            $(usuarioXml).find('telefonoUsuario').each(function () {
            var id, name;
            $('#inputTipoTelefono').val($(this).children('tipoTelefono').text()) ;
            
            $('#inputNumTelefono').val($(this).children('numTelefono').text()) ;

            });


            }

            }
            xmlhttp.open("GET","infoUsuario?email="+inputText,true);
            xmlhttp.send();

            }
        </script>

        <script>

            function cargarDatosEliminar(){
            var inputText = document.getElementById("buscarEliminar").value;
            $('#divDatosABorrar').hide();
            if (inputText == "")
            return;

            var xmlhttp;
            if (window.XMLHttpRequest)
            {
            xmlhttp=new XMLHttpRequest();
            }
            else
            {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange=function()
            {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {

            var usuarioXml = xmlhttp.responseText;
            xmlDoc = $.parseXML( usuarioXml ),
            $usuarioXml = $( xmlDoc ),


            $('#divDatosABorrar').show();


            $title = $usuarioXml.find( "nombre1" );
            $title2 = $usuarioXml.find( "apellido1" );
            var nombreCompuesto =  $title.text() + " " + $title2.text();

            document.getElementById("nombreTitulo").innerHTML = nombreCompuesto;


                                             $('#correoTarjeta').html('<i class="fa fa-envelope-o fa-muted fa-fw"/>' +   $('#buscarEliminar').val());      


            $('#inputOcultoEmail').val( $('#buscarEliminar').val() ) ;

                                            $('#nombreTarjeta').html('<i class="fa fa-user fa-muted fa-fw"/>' +   nombreCompuesto);      

            var displayName;                         
            $(usuarioXml).find('rol').each(function () 
            {
            var id, name;
            //id          = $(this).attr('id'); 
            //name        = $(this).children('authority').text();   

            displayName = $(this).children('displayName').text(); 
                                                $('#rolTitulo').html('<i   class="fa fa-user fa-muted"/>' +  displayName );
            });




            $title = $usuarioXml.find( "dateCreated" );                          
                                            $('#fechaTitulo').html('<i   class="fa fa-calendar fa-muted"/>' +   $title.text() );  

            $(usuarioXml).find('telefonoUsuario').each(function () {
            var tel;
            tel = $(this).children('displayName').text();
                                                $('#telefonoTarjeta').html('<i   class="fa fa-phone fa-muted fa-fw"/>' +  tel );  

            });

            $('#divTablaResponsable').hide();
            $('#tbodySitios').html('');
            var check = 0;
            $(usuarioXml).find('cliente').each(function () {

            $('#divTablaResponsable').show()
            var tel;
            tel = $(this).children('razonSocial').text();
                                                $('#tbodySitios').append('<tr><td>'+tel+'</td></tr>');
            check = 1;
            });  

            if(check == 1)
            {
            window.alert("Este usuario es el contacto responsable de al menos un cliente, recuerde modificar al responsable del cliente");
            }

            $('#divTablaAccesos').hide();
            $('#tbodyAccesos').html('');
            $(usuarioXml).find('string').each(function () {

            $('#divTablaAccesos').show()
            var nombreSitio;
            nombreSitio = $(this).text();
                                                $('#tbodyAccesos').append('<tr><td>'+nombreSitio+'</td></tr>');
            check = 1;
            });  


            }

            }


            xmlhttp.open("GET","infoUsuario?email="+inputText,true);
            xmlhttp.send();

            }
            var pager;
            $(document).ready(function(){
            //Cambiar por el numero que es (10)...
            pager = new Pager('paginated', 10); 
            pager.init(); 
            pager.showPageNav('pager', 'pageNavPosition'); 
            pager.showPage(1);

            });
        </script>
        <script  src = "${request.contextPath}/Flex/js/paging.js"></script>

    </body>
</html>
