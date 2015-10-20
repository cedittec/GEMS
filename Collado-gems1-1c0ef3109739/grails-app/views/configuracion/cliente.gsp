<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="frontend">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clientes</title>
    </head>
    <body>

        <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Configuraci&oacute;n
                            <small>Clientes</small>
                        </h1>
                        <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                            <li style="padding-top:6px"><i class="fa fa-gear"></i> Configuraci&oacute;n</li>
                            <li class="active" style="padding-top:6px"> Clientes</li>

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

                                <g:if test="${flash.message}">
                                    <li><a href="#alta" data-toggle="tab">Crear cliente</a>
                                    </g:if>
                                    <g:else>
                                    <li class="active"><a href="#alta" data-toggle="tab">Crear cliente</a>
                                    </g:else>
                                </li>
                                <li><a href="#cambio" data-toggle="tab">Modificar cliente</a>
                                </li>
                                <sec:ifAnyGranted roles="ROLE_SysAdmin">              
                                    <li><a href="#baja" data-toggle="tab">Desactivar cliente</a>
                                    </li>
                                </sec:ifAnyGranted>              
                                <g:if test="${flash.message}">
                                    <li class="active"><a href="#ver" data-toggle="tab">Clientes</a>
                                    </g:if>
                                    <g:else>
                                    <li ><a href="#ver" data-toggle="tab">Clientes</a>
                                    </g:else>
                                </li>
                            </ul>
                            <div id="userTabContent" class="tab-content">

                                <g:if test="${flash.message}">
                                    <div class="tab-pane fade in active" id="ver">
                                        <div class="alert alert-success alert-dismissable">
                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                            <strong>Operaci&oacute;n exitosa:</strong> ${flash.message}
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <div class="tab-pane fade" id="ver">
                                        </g:else>


                                        <h3>Clientes</h3>
                                        <div class="table-responsive">
                                            <table id ="paginated" class="table table-hover table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Cliente</th>
                                                        <th>Responsable</th>
                                                        <th>R.F.C.</th>
                                                        <th>Fecha creaci&oacute;n</th>
                                                        <th>Tipo</th>
                                                        <th>Tel&eacute;fono</th>

                                                    </tr>
                                                </thead>
                                                <tbody>                                                            

                                                    <g:set var="counter" value="${0}" />
                                                    <g:each in="${clientes}" var="cliente" >  
                                                        <g:each var="telefono"  in="${cliente.telefono}" >
                                                            <tr>
                                                                <td>${cliente.razonSocial}</td>
                                                                <td>${responsables[counter].email}</td>
                                                                <td>${cliente.rfc}</td>
                                                                <td>${cliente.dateCreated}</td>

                                                                <td>${telefono.tipoTelefono}</td>
                                                                <td>${telefono.displayName}</td>                                      
                                                            </tr>
                                                        </g:each>   
                                                        <g:set var="counter" value="${counter + 1}" />

                                                    </g:each>
                                                </tbody>
                                            </table>
                                            <div id="pageNavPosition"></div>
                                        </div>

                                    </div>




                                    <g:if test="${flash.message}">
                                        <div class="tab-pane fade" id="alta">
                                        </g:if>
                                        <g:else>
                                            <div class="tab-pane fade in active" id="alta">
                                            </g:else>

                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="clientSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="basicInformation">

                                                            <g:form name="configuracion" action="saveCliente">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Selecci&oacute;n de usuario responsable:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Usuario (cliente) Responsable</label>                                                    
                                                                            <input required type="text" class="form-control" name="emailCliente" id="search" placeholder="Correo electr&oacute;nico" list="searchresults" autocomplete="off">

                                                                            <datalist id="searchresults">
                                                                                <g:each in="${usuarios.email}" var="email" >
                                                                                    <option>${email}</option>                                                                       
                                                                                </g:each>

                                                                            </datalist>

                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <h4 class="page-header">Detalles de Cliente:</h4>

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
                                                                    <label><i class="fa  fa-fire fa-fw"></i> Raz&oacute;n Social</label>
                                                                    <input required  name="razonSocial" type="text" class="form-control" placeholder="">
                                                                    <label><i  class="fa  fa-fire fa-fw"></i> R.F.C.</label>
                                                                    <input required  name="rfc" type="text" class="form-control" placeholder="RRRRaammddA1B" maxlength="13">

                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Direcci&oacute;n matriz</label>
                                                                    <div class="form-inline" style="padding-left:25px">   
                                                                        
                                                                        
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Calle</label>
                                                                            <input required name="calle" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>N&uacute;mero externo</label>
                                                                            <input required name="numeroExt" type="text"  min="0" class="form-control" placeholder="#" >                                                                                    
                                                                        </div> 
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>N&uacute;mero interno</label>
                                                                            <input name="numeroInt" type="text" class="form-control" placeholder="#" >      
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Colonia</label>
                                                                            <input required name="colonia" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>C&oacute;digo Postal</label>
                                                                            <input required name="codigoPostal" type="text" class="form-control" placeholder="" >      
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Ciudad</label>
                                                                            <input required name="ciudad" type="text" class="form-control" placeholder="" maxlength="35" >
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Estado</label>
                                                                            <input required name="estado" type="text" class="form-control" placeholder="" maxlength="25" >
                                                                        </div>                                                                        
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Pa&iacute;s</label>
                                                                            <input required name="pais" value="M&eacute;xico" type="text" class="form-control" placeholder="" maxlength="20" >
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <button type="submit" class="btn btn-default">Crear cliente</button>

                                                            </g:form>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="tab-pane fade" id="cambio">
                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="clientSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="basicInformation">

                                                            <g:form name="configuracion" action="modificarCliente">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Cliente a modificar:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Raz&oacute;n Social / Nombre del cliente</label>                                                    
                                                                            <input required type="text" class="form-control" name="razonSocialClientes" id="buscarModificar2" placeholder="" list="searchresults2" autocomplete="off">

                                                                            <datalist id="searchresults2">
                                                                                <g:each in="${clientes.razonSocial}" var="razonSocial" >
                                                                                    <option>${razonSocial}</option>                                                                       
                                                                                </g:each>

                                                                            </datalist>
                                                                            <button type="button" class="btn btn-green" onclick="cargarDatos()">Buscar cliente</button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div style="display:none" id="divModificarCliente" class="row">
                                                                    <h4 class="page-header">Detalles de Cliente:</h4>
                                                                    <div class="form-group">                                                        
                                                                        <label>N&uacute;mero telef&oacute;nico</label>
                                                                        <div class="form-inline" style="padding-left:25px">   
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
                                                                                <input required id="inputNumTelefono" name="numTelefono" type="tel" class="form-control" placeholder="(##)1234567891" maxlength="10" size="10"  >
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label><i class="fa  fa-fire fa-fw"></i> Raz&oacute;n Social</label>
                                                                        <input required id="inputRazonSocial" name="razonSocial" type="text" class="form-control" placeholder="">
                                                                        <label><i class="fa  fa-fire fa-fw"></i> R.F.C.</label>
                                                                        <input required id="inputRfc" name="rfc" type="text" class="form-control" placeholder="">

                                                                        <label><i  class="fa  fa-fire fa-fw"></i> Usuario responsable</label>
                                                                        <input required  id="inputEmailResponsable" name="responsableIdEmail" type="text" class="form-control" placeholder="">
                                                                    </div>

                                                                    <div class="form-group">                                                       
                                                                        <label>Direcci&oacute;n matriz</label>
                                                                        <div class="form-inline" style="padding-left:25px">   
                                                                            
                                                                            
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Calle</label>
                                                                                <input required id="inputCalle" name="calle" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>N&uacute;mero externo</label>
                                                                                <input required id="inputNumeroExterno" name="numeroExt" type="text" min="0" class="form-control" placeholder="#" >                                                                                    
                                                                            </div> 
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>N&uacute;mero interno</label>
                                                                                <input id="inputNumeroInterno" name="numeroInt" type="text" class="form-control" placeholder="#" >      
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Colonia</label>
                                                                                <input required id="inputColonia" name="colonia" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>C&oacute;digo Postal</label>
                                                                                <input required id="inputCodigoPostal" name="codigoPostal" type="number" class="form-control" placeholder="" >      
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Ciudad</label>
                                                                                <input required id="inputCiudad" name="ciudad" type="text" class="form-control" placeholder="" maxlength="35" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Estado</label>
                                                                                <input required id="inputEstado" name="estado" type="text" class="form-control" placeholder="" maxlength="25" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Pa&iacute;s</label>
                                                                                <input required id="inputPais" name="pais" value="M&eacute;xico" type="text" class="form-control" placeholder="" maxlength="20" >
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <button type="submit" class="btn btn-default">Guardar cambios</button>
                                                                    <button class="btn btn-green">Cancelar</button>
                                                                </div>
                                                            </g:form>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>

                                        </div>


                                        <sec:ifAnyGranted roles="ROLE_SysAdmin">              
                                            <div class="tab-pane fade" id="baja">

                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                    <div class="form-group">   
                                                        <h4 class="page-header">Cliente a modificar:</h4>
                                                        <div class="form-group" style="padding:20px">

                                                            <label><i class="fa fa-users fa-fw"></i>Raz&oacute;n Social/Nombre cliente</label>                                                    
                                                            <input required type="text" class="form-control" name="emailUsuarioSearch" id="buscarEliminar" placeholder="" list="searchresults2" autocomplete="off">

                                                            <datalist id="searchresults">
                                                                <g:each in="${clientes.razonSocial}" var="razonSocial" >
                                                                    <option>${razonSocial}</option>                                                                       
                                                                </g:each>

                                                            </datalist>
                                                            <button type="button" class="btn btn-green" onclick="cargarDatosEliminar()">Buscar cliente</button>
                                                        </div>
                                                    </div>
                                                </div>




                                                <div style="display:none" id="divDatosABorrar" class="row">

                                                    <div class="col-lg-9 col-md-8">
                                                        <h1 id="nombreTitulo"></h1>

                                                        <div style="display:none" id="divTablaResponsable" class="table-responsive">
                                                            <h3>El cliente cuenta con los sitios </h3>
                                                            <table class="table table-hover table-bordered table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Sitio</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodySitios">
                                                                    <tr>

                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <g:form controller="configuracion" name="UserDeleteForm" action="inhabilitarCliente">

                                                        <div class="col-lg-3 col-md-4">
                                                            <input type="hidden" name="razonSocial" id="inputOcultoRazonSocial" autocomplete="off">
                                                            <h3>Informaci&oacute;n b&aacute;sica</h3>


                                                            <p id="nombreTarjeta"><i class="fa fa-user fa-muted fa-fw"></i> </p>
                                                            <p id="rfcTarjeta"><i class="fa fa-muted">R.F.C&nbsp</i></p>
                                                            <p id="telefonoTarjeta"><i class="fa fa-phone fa-muted fa-fw"></i> </p>

                                                            <div class="list-group">
                                                                <button type="submit" class="btn btn-default">Desactivar sitios</button>
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
                var inputText = document.getElementById("buscarModificar2").value;

                $('#divModificarCliente').hide(); 

                if (inputText == "")
                return;

                $('#divModificarCliente').hide(); 

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

                $('#divModificarCliente').show();  

                //document.getElementById("basicInformationCambios").innerHTML=xmlhttp.responseText;
                var clienteXml = xmlhttp.responseText;
                xmlDoc = $.parseXML( clienteXml ),
                $clienteXml = $( xmlDoc ),

                $title = $clienteXml.find( "razonSocial" );
                $('#inputRazonSocial').val($title.text()) ;
                $title = $clienteXml.find( "rfc" );
                $('#inputRfc').val($title.text()) ;





                $(clienteXml).find('telefonoCliente').each(function () {
                var id, name;
                $('#inputTipoTelefono').val($(this).children('tipoTelefono').text()) ;                
                $('#inputNumTelefono').val($(this).children('numTelefono').text()) ;

                });


                $(clienteXml).find('usuario').each(function () {
                var id, name;
                $('#inputEmailResponsable').val($(this).children('email').text()) ;


                });


                $(clienteXml).find('direccionCliente').each(function () {
                var id, name;
                $('#inputNumeroExterno').val($(this).children('numeroExt').text()) ;
                $('#inputNumeroInterno').val($(this).children('numeroInt').text()) ;
                $('#inputCodigoPostal').val($(this).children('codigoPostal').text()) ;
                $('#inputCalle').val($(this).children('calle').text()) ;
                $('#inputColonia').val($(this).children('colonia').text()) ;
                $('#inputEstado').val($(this).children('estado').text()) ;
                $('#inputCiudad').val($(this).children('ciudad').text()) ;
                $('#inputPais').val($(this).children('pais').text()) ;

                });


                }

                }


                xmlhttp.open("GET","infoCliente?razonSocial="+inputText,true);
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


                document.getElementById("nombreTitulo").innerHTML = $('#buscarEliminar').val();

                $title = $usuarioXml.find( "rfc" );  
                                             $('#rfcTarjeta').html('<i class="fa fa-muted"><FONT FACE="modern, arial, veranda">R.F.C.&nbsp;</FONT></i>' +   $title.text());      

                $('#inputOcultoRazonSocial').val( $('#buscarEliminar').val() ) ;

                                            $('#nombreTarjeta').html('<i class="fa fa-user fa-muted fa-fw"/>' +   $('#buscarEliminar').val());      


                $(usuarioXml).find('telefonoCliente').each(function () {
                var tel;
                tel = $(this).children('displayName').text();
                                                $('#telefonoTarjeta').html('<i   class="fa fa-phone fa-muted fa-fw"/>' +  tel );  

                });

                $('#divTablaResponsable').hide();
                $('#tbodySitios').html('');

                $(usuarioXml).find('sitio').each(function () {

                $('#divTablaResponsable').show()
                var tel;
                tel = $(this).children('nombre').text();
                                                $('#tbodySitios').append('<tr><td>'+tel+'</td></tr>');

                });  
                }
                }
                xmlhttp.open("GET","infoCliente?razonSocial="+inputText,true);
                xmlhttp.send();
                }
            </script>

            <script>
                var pager;
                $(document).ready(function(){                    
                pager = new Pager('paginated', 10); 
                pager.init(); 
                pager.showPageNav('pager', 'pageNavPosition'); 
                pager.showPage(1);

                });

            </script>
            <script  src = "${request.contextPath}/Flex/js/paging.js"></script>

    </body>
</html>
