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
        <title>Fuentes</title>
    </head>
    <body>
        <div class="page-content">
                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Configuraci&oacute;n
                            <small>Fuentes</small>
                        </h1>
                        <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                            <li style="padding-top:6px"><i class="fa fa-gear"></i> Configuraci&oacute;n</li>
                            <li class="active" style="padding-top:6px"> Fuentes</li>

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
                                    <li><a href="#alta" data-toggle="tab">Asignar fuente</a>
                                    </g:if>
                                    <g:else>
                                    <li class="active"><a href="#alta" data-toggle="tab">Asignar fuente</a>
                                    </g:else>
                                </li>

                                </li>
                                <li><a href="#cambio" data-toggle="tab">Modificar fuente</a>
                                </li>
                                <sec:ifAnyGranted roles="ROLE_SysAdmin">              
                                        <li><a href="#baja" data-toggle="tab">Desactivar/Activar fuentes</a>
                                        </li>
                                </sec:ifAnyGranted>              
                                <li><a href="#servicio" data-toggle="tab">Asignar servicios de fuente</a>
                                </li>

                                <g:if test="${flash.message}">
                                    <li class="active"><a href="#ver" data-toggle="tab">Fuentes</a>
                                    </g:if>
                                    <g:else>
                                    <li ><a href="#ver" data-toggle="tab">Fuentes</a>
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

                                        <h3>Fuentes asignadas</h3>
                                        <div class="table-responsive">
                                            <table id ="paginated" class="table table-hover table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Fuente de energ&iacute;a</th>
                                                        <th>Nombre</th>
                                                        <th>Combustible</th>
                                                        <th>Unidad de medida</th>                                                        
                                                        <th>Cantidad de equipos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>                                                            
                                                    <g:each in="${fuentesLista}" var="fuente" >

                                                        <tr>
                                                            <td>${fuente.id}</td>
                                                            <td>${fuente.capacidadGeneracion}</td>
                                                            <td>${fuente.combustible}</td>
                                                            <td>${fuente.unidad}</td>                                                            
                                                            <td>${fuente.cantidad}</td>
                                                        </tr>

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
                                                <g:if test="${flash.error}">
                                                    <div class="alert alert-danger alert-dismissable">
                                                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                                        <strong>Ha ocurrido un error - </strong> ${flash.error}
                                                    </div>
                                                </g:if>
                                            </g:else>

                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="userSettingsContent" class="tab-content" >
                                                        <div class="tab-pane fade in active" id="basicInformation">
                                                            <g:form name="configuracion" action="saveFuente">
                                                                <h4 class="page-header" style="margin-top:20px">Asignar fuente a un sitio:</h4>

                                                                <div class="form-group">
                                                                    <label></i>Sitio al que ser&aacute; asignada la fuente</label>                                                                                                                    
                                                                    <select name="sitioId" class="form-control">
                                                                        <g:each  var="sitio"  in="${sitios}" >
                                                                            <option value="${sitio.id}" >${sitio.nombre}</option>                                                                       
                                                                        </g:each>
                                                                    </select>

                                                                </div>

                                                                <div class="form-group">
                                                                    <label></i>Tipo de Fuente</label>                                                                                                                    
                                                                    <select id="inputFuenteDeEnergiaId" name="fuenteDeEnergiaId" class="form-control">
                                                                        <g:each  var="fuente"  in="${fuentes}" >
                                                                            <option value="${fuente.id}" >${fuente.tipo}</option>                                                                       
                                                                        </g:each>
                                                                    </select>

                                                                </div>

                                                                <div id="tipoCombustibleDiv" class="form-group">
                                                                    <label>Tipo de combustible</label>
                                                                    <select id="combustibleId1" name="combustibleFuenteId" class="form-control">
                                                                        <g:each  var="combustible"  in="${combustibles}" >
                                                                            <option value="${combustible.id}" >${combustible.nombre}</option>                                                                       
                                                                        </g:each>
                                                                    </select>
                                                                </div>

                                                                <div id="unidadMedidaDiv" class="form-group">                                                        
                                                                    <label>Unidad de medida</label>
                                                                    <select id="unidadMedidaId1" name="unidadDeMedidaId" class="form-control">
                                                                        <g:each  var="medida"  in="${medidas}" >
                                                                            <option value="${medida.id}" >${medida.abreviatura}</option>                                                                       
                                                                        </g:each>
                                                                    </select>
                                                                </div>

                                                                <div id="capacidadDelTanque1" class="form-group">                                                        
                                                                    <label>Capacidad del tanque</label> 
                                                                    <input  value ="0.0" name="capacidadTanque" type="number" step="any" class="form-control" placeholder="">
                                                                </div>
                                                                
                                                                 <div class="form-group">
                                                                    <label>Modelo</label>
                                                                      <input  name="modelo" type="text" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Nombre de identificaci&oacute;n</label> 
                                                                    <input required  name="nombre" type="text" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Capacidad generaci&oacuten de energ&iacute;a</label> 
                                                                    <input required  name="capacidadGeneracionEnergia" type="number" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Cantidad de equipos</label> 
                                                                    <input required  name="cantidad" type="number" class="form-control" placeholder="">
                                                                </div>


                                                                <button type="submit" class="btn btn-default">Crear fuente</button>
                                                                <button class="btn btn-green">Cancelar</button>

                                                            </g:form>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="tab-pane fade" id="cambio">
                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="userSettingsContent" class="tab-content" >
                                                        <div class="tab-pane fade in active" id="basicInformation">
                                                            <g:form name="configuracion" action="modificarFuenteSitio">
                                                                <h4 class="page-header" style="margin-top:20px">Modificar datos de Fuente</h4>

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Sitio a modificar:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>  

                                                                            <select name="sitioId" id="selectSitio" class="form-control">
                                                                                <option > </option> 
                                                                                <g:each  var="sitio"  in="${sitios}" >
                                                                                    <option value="${sitio.id}" >${sitio.nombre}</option>                                                                       
                                                                                </g:each>
                                                                            </select>

                                                                            <label><i class="fa fa-users fa-fw"></i>Fuente</label>  
                                                                            <select  name="fuenteSitioId" id="selectFuenteSitio" class="form-control" onchange="cargarDatos();">

                                                                            </select>

                                                                            <button type="button" class="btn btn-green" onclick="cargarDatos( )">Buscar fuente</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                               <div id = "editarFuente" hidden="true">
                                                                <div class="form-group">
                                                                    <label></i>Tipo de Fuente</label>                                                                                                                    
                                                                    <select name="fuenteDeEnergiaId" id="inputFuenteDeEnergiaId" class="form-control">
                                                                        <g:each  var="fuente"  in="${fuentes}" >
                                                                            <option value="${fuente.id}" >${fuente.tipo}</option>                                                                       
                                                                        </g:each>
                                                                    </select>

                                                                </div>

                                                                <div id="tipoCombustibleDiv2" class="form-group">
                                                                    <label>Tipo de combustible</label>
                                                                    <select name="combustibleFuenteId" id="inputCombustibleFuenteId" class="form-control">
                                                                        <g:each  var="combustible"  in="${combustibles}" >
                                                                            <option value="${combustible.id}" >${combustible.nombre}</option>                                                                       
                                                                        </g:each>
                                                                    </select>
                                                                </div>

                                                                <div id="unidadMedidaDiv2" class="form-group">                                                        
                                                                    <label>Unidad de medida</label>
                                                                    <select name="unidadDeMedidaId" id="inputUnidadDeMedidaId" class="form-control">
                                                                        <g:each  var="medida"  in="${medidas}" >
                                                                            <option value="${medida.id}" >${medida.abreviatura}</option>                                                                       
                                                                        </g:each>
                                                                    </select>
                                                                </div>

                                                                <div id="inputCapacidadTanqueDiv" class="form-group">                                                        
                                                                    <label>Capacidad del tanque</label> 
                                                                    <input required  name="capacidadTanque" id="inputCapacidadTanque" type="number" step="any" class="form-control" placeholder="">
                                                                </div>
                                                                
                                                                 <div class="form-group">
                                                                    <label>Modelo</label>
                                                                      <input id="idModelo" name="modelo" type="text" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Nombre de identificaci&oacute;n</label> 
                                                                    <input required  name="nombre" type="text" id="inputNombreFuente" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Capacidad generaci&oacuten de energ&iacute;a</label> 
                                                                    <input required  name="capacidadGeneracionEnergia" id="inputCapacidadGeneracionEnergia" type="number" step="any" class="form-control" placeholder="">
                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Cantidad de equipos</label> 
                                                                    <input required  name="cantidad" type="number" id="inputCantidad" class="form-control" placeholder="">
                                                                </div>


                                                                <button type="submit" class="btn btn-default">Guardar cambios</button>
                                                                <button class="btn btn-green">Cancelar</button>

                                                            </g:form>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>
                                            </div>
                                        </div>

 <!-- Inicia de servicios Div-->

                                        <div class="tab-pane fade" id="servicio">

                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="userSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="servicios">

                                                            <g:form name="configuracion" action="crearServiciosDeFuente">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Asignar servicios:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>  

                                                                            <select name="sitioId" id="selectSitioEliminar" class="form-control">
                                                                                <option > </option> 
                                                                                <g:each  var="sitio"  in="${sitios}" >
                                                                                    <option value="${sitio.id}" >${sitio.nombre}</option>                                                                       
                                                                                </g:each>
                                                                            </select>

                                                                            <label><i class="fa fa-users fa-fw"></i>Fuente</label>  
                                                                            <select name="fuenteSitioId" id="selectFuenteSitioEliminar" class="form-control">

                                                                            </select>

                                                                            <button type="button" class="btn btn-green" onclick="cargarServiciosDeFuente(serviciosActuales);">Buscar fuente</button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div style="display:none" id="divAccesosSitios" class="portlet portlet-default" style="width:80%">
                                                                    <div class="portlet-heading">
                                                                        <div class="portlet-title">
                                                                            <h4>Servicios a asignar:</h4>
                                                                        </div>
                                                                        <div class="clearfix"></div>
                                                                    </div>

                                                                    <div class="form-group" style="padding-left:25px">   
                                                                        <div class="form-group" style="width:50%">
                                                                            <h4>Servicios registrados</h4>
                                                                            <select class="form-control" name="serviciosDisponibles" id="selectServiciosDisponibles" multiple>
                                                                                <g:each   var="servicio"  in="${servicios}" >
                                                                                    <option value="${servicio.id}" >${servicio.nombre} : ${servicio.descripcion }</option>                                                                       
                                                                                </g:each>

                                                                            </select>
                                                                        </div> 

                                                                        <div class="form-group" style="width:50%">

                                                                            <input class="form-control" TYPE="BUTTON" VALUE="Asignar" ONCLICK="MyMoveItem(serviciosDisponibles,serviciosActuales);">
                                                                            <INPUT class="form-control" TYPE="BUTTON" VALUE="Remover" ONCLICK="MyMoveItem(serviciosActuales,serviciosDisponibles);">

                                                                        </div>

                                                                        <div class="form-group" style="width:50%">
                                                                            <h4>Servicios proporcionados actualmente</h4>
                                                                            <select class="form-control" name="serviciosActuales" id="selectServiciosActuales" multiple>
                                                                            </select>
                                                                        </div>
                                                                        <input type="submit" value="Guardar cambios" onclick="selectAll();" class="btn btn-default">
                                                                    </div>    

                                                                </div>

                                                            </g:form>
                                                        </div>   
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

<!-- fin de servicios Div-->
                                        <sec:ifAnyGranted roles="ROLE_SysAdmin">              
                                        <div class="tab-pane fade" id="baja">

                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="userSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="bajas">

                                                            <g:form name="configuracion" action="inhabilitarFuente">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Desactivar/Activar fuente:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>  

                                                                            <select name="sitioId" id="selectSitioBaja" class="form-control">
                                                                                <option > </option> 
                                                                                <g:each  var="sitio"  in="${sitios}" >
                                                                                    <option value="${sitio.id}" >${sitio.nombre}</option>                                                                       
                                                                                </g:each>
                                                                            </select>

                                                                            <label><i class="fa fa-users fa-fw"></i>Fuente</label>  
                                                                            <select name="fuenteSitioId" id="selectFuenteSitioBaja" class="form-control">

                                                                            </select>

                                                                            <button type="button" class="btn btn-green" onclick="cargarDatosActivar();">Buscar fuente</button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div style="display:none" id="divDatosABorrar"  class="row">
                                                                    <div class="col-lg-12 col-md-12" style="padding-left:20px">
                                                                        <h1 id="fuenteTitulo"></h1>
                                                                        <h2 id="estatusTitulo"></h2>
                                                                        <ul class="list-inline">
                                                                            <li id="sitioTitulo"><i class="fa fa-globe fa-muted"></i> </li>
                                                                            <li id="clienteTitulo"><i class="fa fa-user fa-muted"></i> </li>
                                                                            <li id="fechaTitulo"><i class="fa fa-calendar fa-muted"></i> </li>
                                                                        </ul>

                                                                        <h3>Informaci&oacute;n</h3>

                                                                        <p id="tipoTitulo"><i class="fa fa-building-o">&nbsp;<b>  </b> </i> </p>
                                                                        <p id="modeloTitulo"><i class="fa fa-bullseye  ">&nbsp; <b> </b></i> </p>
                                                                        <p id="numeroEquiposTitulo"><i class="fa fa-list ">&nbsp; <b></b></i> </p>
                                                                        <p id="combustibleTitulo"><i class="fa fa-apple ">&nbsp;<b></b></i>  </p>
                                                                        <p id="capacidadTanqueTitulo"><i class="fa fa-dashboard  ">&nbsp;<b></b></i> </p>
                                                                   <!--     <p id="poderCalorificoTitulo"><i class="fa fa-rocket ">&nbsp;<b></b></i> </p> -->
                                                                   <!--      <p id="serviciosTitulo"><i class="fa fa-fire-extinguisher ">&nbsp;<b></b></i>  </p> -->


                                                                        <button id="botonActivarDesactivarFuente" type="submit" class="btn btn-default">Desactivar fuente</button>
                                                                    </div>
                                                                
                                                               </div>
                                                            </div>




                                                        </g:form>
                                                    </div>   
                                                </div>
                                            </div>
                                        </div>
                                      </sec:ifAnyGranted>              
                                    </div>

<!-- fin BAJA -->
                                </div>
                                <!-- /.portlet-body -->
                            </div>
                            <!-- /.portlet -->


                        </div>
                        <!-- /.col-lg-12 -->
                    </div>
                    <!-- /.row -->


                </div>

                <script>                    
                     
                      $('[name="fuenteDeEnergiaId"]').change(function() { 
                         
                        var fuenteNombre =$("#inputFuenteDeEnergiaId option:selected").text();                      
                        if(fuenteNombre == "Panel solar" || fuenteNombre == "Colector solar")
                        {
                          $("#capacidadDelTanque1").hide();
                          $("#tipoCombustibleDiv").hide();
                          $("#unidadMedidaDiv").hide();
                          
                          $("#inputCapacidadTanqueDiv").hide();
                          $("#tipoCombustibleDiv2").hide();
                          $("#unidadMedidaDiv2").hide();
                          
                          
                          
                        }
                        else
                        {
                          $("#capacidadDelTanque1").show();
                          $("#tipoCombustibleDiv").show();
                          $("#unidadMedidaDiv").show();
                          
                          $("#inputCapacidadTanqueDiv").hide();
                          $("#tipoCombustibleDiv2").hide();
                          $("#unidadMedidaDiv2").hide();
                        }
                      });
                     
                              
                     $('[name="combustibleFuenteId"]').change(function() {                          
                          var combustibleNombre =$("#combustibleId1 option:selected").text();
                         
                          if( combustibleNombre == "Gas LP" || combustibleNombre == "Gas Natural" || combustibleNombre=="Biogas")
                          {
                            $('#unidadMedidaId1 option:contains("m^3")').prop('selected', true);
                            
                          }else
                          {
                            $('#unidadMedidaId1 option:contains("Lts")').prop('selected', true);
                           
                          }
                          
                          if( combustibleNombre == "Gas LP" || combustibleNombre == "Diesel" )
                          {
                            $("#capacidadDelTanque1").show();
                          }else
                          {
                           $("#capacidadDelTanque1").hide();
                          }
                          
                      });
                      
                      $('#inputCombustibleFuenteId').change(function() {                          
                          var combustibleNombre =$("#inputCombustibleFuenteId option:selected").text();
                         
                          if( combustibleNombre == "Gas LP" || combustibleNombre == "Gas Natural" || combustibleNombre=="Biogas")
                          {
                            $('#inputUnidadDeMedidaId option:contains("m^3")').prop('selected', true);
                            
                          }else
                          {
                            $('#inputUnidadDeMedidaId option:contains("Lts")').prop('selected', true);
                           
                          }
                          
                          if( combustibleNombre == "Gas LP" || combustibleNombre == "Diesel" )
                          {
                            $("#inputCapacidadTanqueDiv").show();
                          }else
                          {
                           $("#inputCapacidadTanqueDiv").hide();
                          }
                          
                      });
                      
                    
                    function cargarDatosActivar()
                    {
                                var inputText = document.getElementById("selectFuenteSitioBaja").value;
                                
                                
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
                                            $usuarioXml = $( xmlDoc );
                                            $('#divDatosABorrar').show();
                                            $(usuarioXml).find('fuenteSitio ').each(function () {
                                                var nombre, estatus, modelo;
                                                nombre = $(this).children('nombre').text();
                                                estatus = $(this).children('estatus').text();
                                                 modelo = $(this).children('modelo').text();
                                                $('#fuenteTitulo').html(nombre ); 
                                                $('#estatusTitulo').html("Estatus: "+estatus ); 
                                                 $('#modeloTitulo').html('<i class="fa fa-bullseye  ">&nbsp; <b> Modelo:</b></i> ' +  modelo + '</p>' );  
                                                if(estatus == "inactivo")
                                                {
                                                    $('#botonActivarDesactivarFuente').html("Activar fuente");
                                                }
                                                $('#sitioTitulo').html('<i class="fa fa-user fa-muted"></i> ' +  nombre + '</li>' );  
                                                
                                                });
                                                
                                                
                                            $(usuarioXml).find('fuenteDeEnergia  ').each(function () {
                                                var tipo,equipos,capacidad;
                                                tipo = $(this).children('tipo').text();
                                               
                                                equipos =$(this).children('cantidad').text();
                                                
                                                $('#tipoTitulo').html('<i class="fa fa-building-o">&nbsp;<b> Tipo: </b> </i> ' +  tipo +'</p>' );  
                                               
                                                
                                                
                                                });
                                            
                                            $(usuarioXml).find('combustibleFuente').each(function () {
                                                var combustible;
                                                combustible = $(this).children('nombre').text();
                                                $('#combustibleTitulo').html('<i class="fa fa-apple ">&nbsp;<b>Combustible:&nbsp;</b></i>' +  combustible +'</p>' );  
                                              
                                                });  

                                          //  $title = $usuarioXml.find( "poderCalorifico" );   
                                          //  $('#poderCalorificoTitulo').html('<i class="fa fa-rocket ">&nbsp;<b>Poder calor&iacute;fico del combustible:</b></i>' +  $title.text()  +'</p>' );  
                                            
                                            $title = $usuarioXml.find( "capacidadTanque" );   
                                            $('#capacidadTanqueTitulo').html('<i class="fa fa-dashboard  ">&nbsp;<b>Capacidad del tanque:</b></i> ' +  $title.text() +' m^3</p>' );  
                                            
                                            $title = $usuarioXml.find( "cantidad" );  
                                            $('#numeroEquiposTitulo').html('<i class="fa fa-list ">&nbsp; <b>N&uacute;mero de equipos:</b></i> ' +  $title.text() + '</p>' );  
                                           
                                            
                                            var comp ="";
                                            $(usuarioXml).find('string').each(function () {
                                                comp += $(this).text() + ",";
                                             });
                                                
                                           // $('#serviciosTitulo').html('<i class="fa fa-fire-extinguisher ">&nbsp;<b>Servicios:</b></i> ' +  comp.substring(0, comp.length - 1) + '</p>' );  
                                           
                                            
                                    }

                                }


                                xmlhttp.open("GET","infoFuente?fuenteSitioId="+inputText,true);
                                xmlhttp.send();
                        
                    }

                    function selectAll() 
                    { 
                    var selectBox = document.getElementById("selectServiciosDisponibles");

                    for (var i = 0; i < selectBox.options.length; i++) 
                    { 
                    selectBox.options[i].selected = true; 
                    } 

                    var selectBox2 = document.getElementById("selectServiciosActuales");

                    for (var i = 0; i < selectBox2.options.length; i++) 
                    { 
                    
                    selectBox2.options[i].selected = true; 
                    } 

                    }




                    function cargarServiciosDeFuente(obj)
                    {   
                    for (var cont=0; cont<obj.length; cont++)
                    {
                    obj[cont] = null

                    }



                    $('#divAccesosSitios').hide();
                    var selectServiciosActuales = document.getElementById("selectServiciosActuales");    
                    var fuenteId = document.getElementById("selectFuenteSitioEliminar").value;

                    if (fuenteId == ""){
                    $('#divAccesosSitios').hide();
                    return;
                    }

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
                    $('#divAccesosSitios').show()
                    var sitioXml = xmlhttp.responseText;
                    xmlDoc = $.parseXML( sitioXml ),
                    $sitioXml = $( xmlDoc ),

                    $(sitioXml).find('servicio').each(function () {
                    var nombre = ($(this).children('nombre').text());
                    var id = $(this).attr('id'); 
                    selectServiciosActuales.options[selectServiciosActuales.options.length] = new Option(nombre, id);
                
                    });
                    
                    limpiarRepetidos();
                    
                    }

                    }


                    xmlhttp.open("GET","verServiciosFuente?fuenteId="+fuenteId,true);
                    xmlhttp.send();
                    }


                    function MyMoveItem(fromObj, toObj)
                    {
                       for (var selIndex = fromObj.length - 1; selIndex >= 0; selIndex--)
                       {

                        if (fromObj.options[selIndex].selected)
                        {

                                var newText  = fromObj.options[selIndex].text;
                                var newValue = fromObj.options[selIndex].value;


                                var newOption = new Option(newText, newValue)
                                toObj[toObj.length] = newOption;

                                fromObj[selIndex] = null;
                                }
                            }
                    }

                    function limpiarRepetidos(){
                        var foo1 = document.getElementById("selectServiciosDisponibles");
                        var foo2 = document.getElementById("selectServiciosActuales");
                        for (var i = 0; i< foo1.options.length; i++)
                            for (var j = 0; j< foo2.options.length; j++){
                                if (foo1.options[i].value == foo2.options[j].value){
                                    foo1.options.remove(i);
                                    i--;
                                }
                            }
                    }


                </script>

                <script>

                    function cargarDatos(){
                    
                    
                    $('#editarFuente').show(); 
                    
                    var inputText2 = document.getElementById("selectFuenteSitio").value;

                    if (inputText2 == "")
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
                    var clienteXml = xmlhttp.responseText;
                    xmlDoc = $.parseXML( clienteXml ),
                    $clienteXml = $( xmlDoc ),

                     //tipo de fuente, tipo de combustible, unidad de medida
                    $title = $clienteXml.find( "long" )[0].innerHTML;
                    console.log($title);
                    $('#inputFuenteDeEnergiaId').val($title) ;
                    
                 
                    $title = $clienteXml.find( "long" )[1].innerHTML;
                    console.log($title);
                    //tipo de fuente, tipo de combustible, unidad de medida
                    $('#inputCombustibleFuenteId').val($title) ;

                     //tipo de fuente, tipo de combustible, unidad de medida
                    $title = $clienteXml.find( "long" )[2].innerHTML;
                    console.log($title);
                    $('#inputUnidadDeMedidaId').val($title) ;


                    $title = $clienteXml.find( "capacidadTanque" );
                    $('#inputCapacidadTanque').val($title.text()) ;

                    $title = $clienteXml.find( "capacidadGeneracionEnergia" );
                    $('#inputCapacidadGeneracionEnergia').val($title.text()) ;

                    $title = $clienteXml.find( "cantidad" );
                    $('#inputCantidad').val($title.text()) ;

                    
                    $title = $clienteXml.find( "modelo" );
                    $('#idModelo').val($title.text()) ;

                    $title = $clienteXml.find( "nombre" );
                    $('#inputNombreFuente').val($title.text()) ;
                    }

                    }


                    xmlhttp.open("GET","infoFuenteEnSitio?fuenteSitioId="+inputText2,true);
                    xmlhttp.send();


                    }

                    
                   


                    $('#selectSitio').change(function() {

                    var inputText = document.getElementById("selectSitio").value;

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
                    var usuarioXml = xmlhttp.responseText;
                    xmlDoc = $.parseXML( usuarioXml ),
                    $usuarioXml = $( xmlDoc ),

                    $('#selectFuenteSitio').empty();

                    $(usuarioXml).find('FuenteSitio').each(function () {
                    var id, nombre;
                    id = $(this).attr('id'); 
                    nombre = $(this).children('nombre').text();                                                 
                    $("#selectFuenteSitio").append(new Option(nombre, id));
                    });


                    }

                    }


                    xmlhttp.open("GET","fuentesEnSitio?sitioId="+inputText,true);
                    xmlhttp.send();



                    });

                    $('#selectSitioEliminar').change(function() {

                    var inputText = document.getElementById("selectSitioEliminar").value;

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
                    var usuarioXml = xmlhttp.responseText;
                    xmlDoc = $.parseXML( usuarioXml ),
                    $usuarioXml = $( xmlDoc ),
                    $("#selectFuenteSitioEliminar").empty();
                    $(usuarioXml).find('FuenteSitio').each(function () {
                    var id, nombre;
                    id = $(this).attr('id'); 
                    nombre = $(this).children('nombre').text();            
                    $("#selectFuenteSitioEliminar").append(new Option(nombre, id));
                    });


                    }

                    }


                    xmlhttp.open("GET","fuentesEnSitio?sitioId="+inputText,true);
                    xmlhttp.send();



                    });    
                    
                    
                    $('#selectSitioBaja').change(function() {

                    var inputText = document.getElementById("selectSitioBaja").value;

                    
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
                    var usuarioXml = xmlhttp.responseText;
                    xmlDoc = $.parseXML( usuarioXml ),
                    $usuarioXml = $( xmlDoc ),
                    $("#selectFuenteSitioBaja").empty();
                    
                    $(usuarioXml).find('FuenteSitio').each(function () {
                      
                        var id, nombre;
                        id = $(this).attr('id'); 
                        nombre = $(this).children('nombre').text();            
                        $("#selectFuenteSitioBaja").append(new Option(nombre, id));
                    });


                    }

                    }


                    xmlhttp.open("GET","fuentesEnSitio?sitioId="+inputText,true);
                    xmlhttp.send();



                    });    
                    
                     
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
