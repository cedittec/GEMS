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
        <title>Sitios</title>
    </head>
    <body>
        <div class="page-content">
                 <!-- begin PAGE TITLE AREA -->
                 <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Configuraci&oacute;n
                            <small>Sitios</small>
                        </h1>
                        <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                            <li style="padding-top:6px"><i class="fa fa-gear"></i> Configuraci&oacute;n</li>
                            <li class="active" style="padding-top:6px"> Sitios</li>

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
                                    <li><a href="#alta" data-toggle="tab">Crear sitios</a>
                                    </g:if>
                                    <g:else>
                                    <li class="active"><a href="#alta" data-toggle="tab">Crear sitios</a>
                                    </g:else>
                                </li>
                                <li><a href="#cambio" data-toggle="tab">Modificar sitios</a>
                                </li>

                                <sec:ifAnyGranted roles="ROLE_SysAdmin">
                                    <li><a href="#baja" data-toggle="tab">Desactivar sitios</a>
                                    </li>
                                </sec:ifAnyGranted>


                                <li><a href="#acceso" data-toggle="tab">Acceso a sitios</a>
                                </li>

                                <g:if test="${flash.message}">
                                    <li class="active"><a href="#ver" data-toggle="tab">Sitios</a>
                                    </g:if>
                                    <g:else>
                                    <li ><a href="#ver" data-toggle="tab">Sitios</a>
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

                                        <h3>Sitios</h3>
                                        <div class="table-responsive">
                                            <table id ="paginated" class="table table-hover table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Sitio</th>
                                                        <th>Cliente</th>
                                                        <th>Regi&oacute;n</th>
                                                        <th>Tipo tarifa</th>
                                                        <th>Tel&eacute;fono</th>
                                                        <th>Estado</th>

                                                    </tr>
                                                </thead>
                                                <tbody>                                                            
                                                    <g:each in="${sitiosLista}" var="sitio" >                                                      
                                                            <tr>
                                                                <td>${sitio.nombre}</td>
                                                                <td>${sitio.cliente}</td>
                                                                <td>${sitio.region}</td>
                                                                <td>${sitio.tarifa}</td>
                                                                <td>${sitio.telefono}</td>
                                                                <td>${sitio.estatus}</td>
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

                                                            <g:form name="configuracion" action="saveSitio">

                                                                <h4 class="page-header" style="margin-top:20px">Informaci&oacute;n:</h4>
                                                                <div class="form-group">                                                        
                                                                    <label>Nombre</label> 
                                                                    <input required name="nombre" type="text" class="form-control" placeholder="Nombre del sitio" style="width:100%">
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Cliente al que pertenece el sitio</label>                                                                

                                                                    <select required name="clienteId" class="form-control" name = "clienteId" >                                                                
                                                                        <g:each   var="cliente"  in="${clientes}" >
                                                                            <option value="${cliente.id}" >${cliente.razonSocial}</option>                                                                       
                                                                        </g:each>
                                                                    </select>


                                                                </div>

                                                                <div class="form-group">
                                                                    <label>Direcci&oacute;n</label>
                                                                    <div class="form-inline" style="padding-left:25px">

                                                                        <div class="form-group" style="width:33%">
                                                                            <label>Calle</label>
                                                                            <input required name="calle" type="text" class="form-control" maxlength="50" placeholder="Market St.">
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>N&uacute;mero externo</label>
                                                                            <input required name="numeroExt" type="number" class="form-control" placeholder="#110" style="width:50%">
                                                                        </div>
                                                                        <div class="form-group" style="width:33%">
                                                                            <label>N&uacute;mero interno</label>
                                                                            <input  name="numeroInt" type="text" class="form-control" placeholder="#32" style="width:50%">
                                                                        </div>
                                                                        <div class="form-group" style="width:33%; padding-top:10px">
                                                                            <label>Colonia</label>
                                                                            <input required name="colonia" type="text" class="form-control" maxlength="50" placeholder="Bayville" style="width:50%">
                                                                        </div>  

                                                                        <div class="form-group" style="width:33%; padding-top:10px" >
                                                                            <label>C&oacute;digo Postal</label>
                                                                            <input required name="codigoPostal" type="number" class="form-control" placeholder="55555" style="width:50%">
                                                                        </div>                                                              	
                                                                        <div class="form-group" style="width:33%; padding-top:10px">
                                                                            <label>Ciudad</label>
                                                                            <input required name="ciudad" type="text" class="form-control" maxlength="35" placeholder="Bayville">
                                                                        </div>
                                                                        <div class="form-group" style="width:33%; padding-top:10px">
                                                                            <label>Estado</label>
                                                                            <input required name="estado" type="text" class="form-control" maxlength="25" placeholder="FL" style="width:50%">
                                                                        </div>
                                                                        <div class="form-group" style="width:33%; padding-top:10px">
                                                                            <label>Pa&iacute;s</label>
                                                                            <input required name="pais" type="text" class="form-control" maxlength="20" placeholder="US" style="width:50%">
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <h4 class="page-header">Contacto del Sitio:</h4>

                                                                <div class="form-group">                                                        
                                                                    <label>N&uacute;mero telef&oacute;nico del sitio</label>
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

                                                                <h4 class="page-header">Detalles del Sitio:</h4>

                                                                <div class="form-group">                                                        
                                                                    <label>Ubicaci&oacute;n geogr&aacute;fica</label> 
                                                                    <div class="form-inline" style="padding-left:25px">

                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Latitud</label>
                                                                            <input required name="latitud" type="number" step="any" class="form-control" placeholder="18.803420">
                                                                        </div>
                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Longitud</label>
                                                                            <input required name="longitud" type="number" step="any" class="form-control" placeholder="-99.221452">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Regi&oacute;n</label>
                                                                    <select name="regionId" class="form-control">
                                                                        <g:each  var="region"  in="${regiones}" >
                                                                            <option  value="${region.id}" >${region.nombre}</option>                                                                       
                                                                        </g:each>
                                                                    </select>

                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Tipo de tarifa</label>
                                                                    <select name="tarifaId" class="form-control">
                                                                        <g:each  var="tarifa"  in="${tarifas}" >
                                                                            <option value="${tarifa.id}" >${tarifa.nombre}</option>                                                                       
                                                                        </g:each>
                                                                    </select>

                                                                </div>

                                                                <div class="form-group">                                                        
                                                                    <label>Precios acordados con proveedores</label> 
                                                                    <div class="form-inline" style="padding-left:25px">

                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Precio gas LP</label>
                                                                            <input value="0.0" name="precioGasLp" type="number" step="any" min="0" class="form-control" placeholder="Llenar en caso de tener acuerdo llenar">
                                                                        </div>
                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Precio gas natural</label>
                                                                            <input value="0.0" name="precioGasNatural" type="number" step="any" min="0" class="form-control" placeholder="Llenar en caso de tener acuerdo llenar">
                                                                        </div>

                                                                        <div class="form-group" style="width:49%; padding-top: 10px">
                                                                            <label>Precio diesel</label>
                                                                            <input value="0.0" name="precioDiesel" type="number" step="any" min="0" class="form-control" placeholder="Llenar en caso de tener acuerdo">
                                                                        </div>

                                                                    </div>
                                                                </div>

                                                               

                                                                <div class="form-group">  


                                                                    <div class="form-group" style="width:49%">
                                                                        <label>Poder Calor&iacute;fico Gas LP</label>
                                                                        <input value="0.0" name="poderCalorificoGasLp" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico gas LP">
                                                                    </div>

                                                                    <div class="form-group" style="width:49%">
                                                                        <label>Poder Calor&iacute;fico Gas Natural</label>
                                                                        <input value="0.0" name="poderCalorificoGasNatural" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico gas Natural">
                                                                    </div>

                                                                    <div class="form-group" style="width:49%">
                                                                        <label>Poder Calor&iacute;fico Diesel </label>
                                                                        <input value="0.0" name="poderCalorificoDiesel" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico Diesel">
                                                                    </div>

                                                                </div>
                                                                
                                                                

                                                                    <div class="form-group" style="width:49%">
                                                                        <label>Inversi&oacute;n</label>
                                                                        <input value="0.0" name="inversion" type="number" step="any" min="0" class="form-control" placeholder="Inversi&oacute;n del sitio">
                                                                    </div>

                                                               


                                                                <button type="submit" class="btn btn-default">Crear sitio</button>
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
                                                    <div id="userSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="basicInformationC">
                                                            <g:form name="configuracion" action="modificarSitio">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Sitio a modificar:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>                                                    
                                                                            <input required type="text" class="form-control" name="nombreSitio" id="buscarModificar2" placeholder="" list="searchresults2" autocomplete="off">

                                                                            <datalist id="searchresults2">
                                                                                <g:each in="${sitios.nombre}" var="nombre" >
                                                                                    <option>${nombre}</option>                                                                       
                                                                                </g:each>

                                                                            </datalist>
                                                                            <button type="button" class="btn btn-green" onclick="cargarDatos()">Buscar sitio</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div id = "editarSitio" hidden = "true">
                                                                    <h4 class="page-header">Detalles de Sitio:</h4>

                                                                    <div class="form-group">                                                        
                                                                        <label>Nombre</label> 
                                                                        <input required id="inputNombre" name="nombre" type="text" class="form-control" placeholder="Nombre del sitio" style="width:100%">
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label>Direcci&oacute;n</label>
                                                                        <div class="form-inline" style="padding-left:25px">   
                                                                            
                                                                            
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Calle</label>
                                                                                <input required  id="inputCalle" name="calle" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>N&uacute;mero externo</label>
                                                                                <input required id="inputNumeroExterno" name="numeroExt" type="number" class="form-control" placeholder="#" >                                                                                    
                                                                            </div> 
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>N&uacute;mero interno</label>
                                                                                <input id="inputNumeroInterno" name="numeroInt" type="number" class="form-control" placeholder="#" >      
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>Colonia</label>
                                                                                <input required id="inputColonia" name="colonia" type="text" class="form-control" placeholder="" maxlength="50" >
                                                                            </div>
                                                                            <div class="form-group" style="width:33%">
                                                                                <label>C&oacute;digo Postal</label>
                                                                                <input id="inputCodigoPostal" name="codigoPostal" type="number" class="form-control" placeholder="" >      
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

                                                                    <h4 class="page-header">Contacto del Sitio:</h4>

                                                                    <div class="form-group">                                                        
                                                                        <label>N&uacute;mero telef&oacute;nico del sitio</label>
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

                                                                    <h4 class="page-header">Detalles del Sitio:</h4>

                                                                    <div class="form-group">                                                        
                                                                        <label>Ubicaci&oacute;n geogr&aacute;fica</label> 
                                                                        <div class="form-inline" style="padding-left:25px">

                                                                            <div class="form-group" style="width:49%">
                                                                                <label>Latitud</label>
                                                                                <input required id="inputLatitud" name="latitud" type="number" step="any" class="form-control" placeholder="18.803420">
                                                                            </div>
                                                                            <div class="form-group" style="width:49%">
                                                                                <label>Longitud</label>
                                                                                <input required id="inputLongitud" name="longitud"  type="number" step="any" class="form-control" placeholder="-99.221452">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Regi&oacute;n</label>
                                                                        <select id="inputRegion" class="form-control">
                                                                            <g:each  var="region"  in="${regiones}" >
                                                                                <option value="${region.id}" >${region.nombre}</option>                                                                       
                                                                            </g:each>
                                                                        </select>

                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Tipo de tarifa</label>
                                                                        <select id="inputTarifa" class="form-control">
                                                                            <g:each  var="tarifa"  in="${tarifas}" >
                                                                                <option value="${tarifa.id}" >${tarifa.nombre}</option>                                                                       
                                                                            </g:each>
                                                                        </select>

                                                                    </div>

                                                                  

                                                                    <div class="form-group">                                                        
                                                                        <label>Precios acordados</label> 
                                                                        <div class="form-inline" style="padding-left:25px">

                                                                            <div class="form-group" style="width:49%">
                                                                                <label>Precio gas LP</label>
                                                                                <input id="inputGasLp" name="precioGasLp" type="number" step="any" min="0" class="form-control" placeholder="">
                                                                            </div>
                                                                            <div class="form-group" style="width:49%">
                                                                                <label>Precio gas natural</label>
                                                                                <input id="inputGasNatural" name="precioGasNatural" type="number" min="0" step="any" class="form-control" placeholder="">
                                                                            </div>

                                                                            <div class="form-group" style="width:49%">
                                                                                <label>Precio diesel</label>
                                                                                <input id="inputDiesel" name="precioDiesel" type="number" step="any" min="0" class="form-control" placeholder="">
                                                                            </div>

                                                                        </div>
                                                                    </div>

                                                                    <div class="form-group">      
                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Poder Calor&iacute;fico Gas LP</label>
                                                                            <input value="0.0" id="pCalGasLp" name="poderCalorificoGasLp" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico gas LP">
                                                                        </div>

                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Poder Calor&iacute;fico Gas Natural</label>
                                                                            <input value="0.0" id="pCalGasNatural" name="poderCalorificoGasNatural" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico gas Natural">
                                                                        </div>

                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Poder Calor&iacute;fico Diesel </label>
                                                                            <input value="0.0" id="pCalDiesel" name="poderCalorificoDiesel" type="number" step="any" min="0" class="form-control" placeholder="Poder Calor&iacute;fico Diesel">
                                                                        </div>

                                                                    </div>
                                                                                                                                        
                                                                        <div class="form-group" style="width:49%">
                                                                            <label>Inversi&oacute;n</label>
                                                                            <input id="inputInversion" value="0.0" name="inversion" type="number" step="0.0001" min="0" class="form-control" placeholder="Inversi&oacute;n del sitio">
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

                                        <div class="tab-pane fade" id="acceso">

                                            <div class="row">

                                                <div class="col-sm-12">
                                                    <div id="userSettingsContent" class="tab-content">
                                                        <div class="tab-pane fade in active" id="accesos">

                                                            <g:form name="configuracion" action="crearAccesoASitio">

                                                                <div style=" width:100%; margin-right:0px; height:" class="tile gray" >
                                                                    <div class="form-group">   
                                                                        <h4 class="page-header">Acceso de usuario a sitio:</h4>
                                                                        <div class="form-group" style="padding:20px">

                                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>                                                    
                                                                            <input required type="text" class="form-control" name="nombreSitio" id="accesoNombreSitio" placeholder="" list="sitiosLista" autocomplete="off">

                                                                            <datalist id="sitiosLista">
                                                                                <g:each var="sitio" in="${sitios}">
                                                                                    <option >${sitio.nombre}</option>                                                                   
                                                                                </g:each>

                                                                            </datalist>
                                                                            <button type="button" class="btn btn-green" onclick="cargarUsuariosEnSitio(emailActuales);">Buscar sitio</button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div style="display:none" id="divAccesosSitios" class="portlet portlet-default" style="width:80%">
                                                                    <div class="portlet-heading">
                                                                        <div class="portlet-title">
                                                                            <h4>Usuarios a asignar al sitio:</h4>
                                                                        </div>
                                                                        <div class="clearfix"></div>
                                                                    </div>

                                                                    <div class="form-group" style="padding-left:25px">   
                                                                        <div class="form-group" style="width:50%">
                                                                            <h4>Usuarios existentes</h4>
                                                                            <select class="form-control" name="emailDisponibles" id="selectUsuariosDisponibles" multiple>
                                                                                <g:each   var="usuario"  in="${usuarios}" >
                                                                                    <option value="${usuario.email}" >${usuario.displayName} : ${usuario.email}</option>                                                                       
                                                                                </g:each>

                                                                            </select>
                                                                        </div> 

                                                                        <div class="form-group" style="width:50%">

                                                                            <input class="form-control" TYPE="BUTTON" VALUE="Dar acceso" ONCLICK="MyMoveItem(emailDisponibles,emailActuales);">
                                                                            <INPUT class="form-control" TYPE="BUTTON" VALUE="Remover acceso" ONCLICK="MyMoveItem(emailActuales,emailDisponibles);">

                                                                        </div>

                                                                        <div class="form-group" style="width:50%">
                                                                            <h4>Usuarios con acceso al sitio</h4>
                                                                            <select class="form-control" name="emailActuales" id="selectUsuariosActuales" multiple>
                                                                            </select>
                                                                        </div>
                                                                        <input type="submit" value="Dar acceso" onclick="selectAll();" class="btn btn-default">
                                                                    </div>    

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
                                                        <h4 class="page-header">Sitio a desactivar:</h4>
                                                        <div class="form-group" style="padding:20px">

                                                            <label><i class="fa fa-users fa-fw"></i>Nombre de sitio</label>                                                    
                                                            <input required type="text" class="form-control" name="emailUsuarioSearch" id="buscarEliminar" placeholder="" list="sitiosLista" autocomplete="off">

                                                            <datalist id="sitiosLista">
                                                                <g:each var="sitio" in="${sitios}">
                                                                    <option >${sitio.nombre}</option>                                                                   
                                                                </g:each>

                                                            </datalist>
                                                            <button type="button" class="btn btn-green" onclick="cargarDatosEliminar();">Buscar sitio</button>
                                                        </div>
                                                    </div>
                                                </div>




                                                <div style="display:none" id="divDatosABorrar" class="row">

                                                    <div class="col-lg-9 col-md-8">
                                                        <h1 id="nombreTitulo"></h1>

                                                        <div style="display:none" id="divTablaResponsable" class="table-responsive">
                                                            <h3 id="estatusTitulo"></h3>
                                                            <table class="table table-hover table-bordered table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Usuarios</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody id="tbodySitios">
                                                                    <tr>

                                                                    </tr>

                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <g:form controller="configuracion" name="UserDeleteForm" action="inhabilitarSitio">

                                                        <div class="col-lg-3 col-md-4">
                                                            <input type="hidden" name="nombreSitio" id="inputOcultoRazonSocial" autocomplete="off">
                                                            <h3>Informaci&oacute;n</h3>
                                                            <p id="nombreTarjeta"><i class="fa fa-user fa-muted fa-fw"></i> Cliente 5</p>                                                
                                                            <p id="direccionTarjeta"><i class="fa fa-globe fa-muted"></i> Market St. #32, Col. Bayville. <br> Bayville, FL. US CP 55555  </p>
                                                            <p id="regionTarjeta"><i class="fa fa-globe fa-muted">&nbsp;Regi&oacute;n</i> Noreste  </p>
                                                            <p id="tarifaTarjeta"><i class="fa fa-bolt fa-muted">&nbsp;Tarifa</i> HT-R  </p>
                                                            <p id="telefonoTarjeta"><i class="fa fa-phone fa-muted fa-fw"></i> (777) 9999999</p>

                                                            </p>

                                                            <div class="list-group">
                                                                <button id="botonActivarDesactivar" type="submit" class="btn btn-default">Cambiar estado</button>
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

                $title = $usuarioXml.find( "estatus" );  
                $('#estatusTitulo').html('Estado: ' +   $title.text());     



                $(usuarioXml).find('tarifaCfe ').each(function () {
                var tel;
                tel = $(this).children('nombre').text();
                                                $('#tarifaTarjeta').html('<i class="fa fa-bolt fa-muted"><FONT FACE="modern, arial, veranda">&nbsp;Tarifa</FONT></i> ' +  tel );  

                });

                $(usuarioXml).find('regionCfe ').each(function () {
                var tel;
                tel = $(this).children('nombre').text();
                                                $('#regionTarjeta').html('<i class="fa fa-globe fa-muted"><FONT FACE="modern, arial, veranda">&nbsp;Regi&oacute;n</FONT></i> ' +  tel );  

                });



                $title = $usuarioXml.find( "pais" );  
                                             $('#direccionTarjeta').html('<i class="fa fa-globe fa-muted"></i> ' +   $title.text());     


                $('#inputOcultoRazonSocial').val( $('#buscarEliminar').val() ) ;

                                            $('#nombreTarjeta').html('<i class="fa fa-user fa-muted fa-fw"/> ' +   $('#buscarEliminar').val());      



                $(usuarioXml).find('telefonoSitio').each(function () {
                var tel;
                tel = $(this).children('displayName').text();
                                                $('#telefonoTarjeta').html('<i   class="fa fa-phone fa-muted fa-fw"/>' +  tel );  

                });

                $('#divTablaResponsable').hide();
                $('#tbodySitios').html('');

                $(usuarioXml).find('string').each(function () {

                $('#divTablaResponsable').show()
                var tel;
                cadena = $(this).text();
                                                $('#tbodySitios').append('<tr><td>'+cadena+'</td></tr>');

                });  



                }

                }


                xmlhttp.open("GET","infoSitio?nombreSitio="+inputText,true);
                xmlhttp.send();

                }
            </script>


            <script>

                function selectAll() 
                { 
                var selectBox = document.getElementById("selectUsuariosDisponibles");

                for (var i = 0; i < selectBox.options.length; i++) 
                { 
                selectBox.options[i].selected = true; 
                } 

                var selectBox2 = document.getElementById("selectUsuariosActuales");

                for (var i = 0; i < selectBox2.options.length; i++) 
                { 
                console.log(selectBox2.options[i]);
                selectBox2.options[i].selected = true; 
                } 

                }

                function MyMoveItem(fromObj, toObj)
                {
                           for (var selIndex = fromObj.length - 1; selIndex >= 0; selIndex--)
                {
                // Is this option selected?
                if (fromObj.options[selIndex].selected)
                {
                // Get the text and value for this option.
                var newText  = fromObj.options[selIndex].text;
                var newValue = fromObj.options[selIndex].value;

                // Create a new option, and add to the other select box.
                var newOption = new Option(newText, newValue);

                //Condici&oacute;n que revisa que no exista esa opci&oacute;n ya en otra....
                for (var i = 0; i<toObj.length; i++){
                if (toObj[i].text == newText || toObj[i].value == newValue){
                fromObj[selIndex] = null;
                return;
                }
                }
                //Final de la condici&oacute;n...
                toObj[toObj.length] = newOption;

                // Delete the option in the first select box.
                fromObj[selIndex] = null;
                }
                }
                }


                function cargarUsuariosEnSitio(obj)
                {   

                for (var cont=0; cont<obj.length; cont++)
                {
                obj[cont] = null

                }



                $('#divAccesosSitios').hide();
                var selectUsuariosConAcceso = document.getElementById("selectUsuariosActuales");    
                var nombreSitio = document.getElementById("accesoNombreSitio").value;
                if (nombreSitio == ""){
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
                var arregloUsuarios = []
                var sitioXml = xmlhttp.responseText;
                xmlDoc = $.parseXML( sitioXml ),
                $sitioXml = $( xmlDoc ),

                $(sitioXml).find('usuario').each(function () {
                var nombre = ($(this).children('displayName').text());
                var email = ($(this).children('email').text());
                selectUsuariosConAcceso.options[selectUsuariosConAcceso.options.length] = 
                new Option(nombre + " - "+email, email);
                arregloUsuarios.push(email);



                });

                limpiarRepetidos();


                }

                }


                xmlhttp.open("GET","verAccesosSitio?nombreSitio="+nombreSitio,true);
                xmlhttp.send();
                }



                function cargarDatos(){
                $('#editarSitio').show();
                var inputText = document.getElementById("buscarModificar2").value;

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

                var sitioXml = xmlhttp.responseText;
                xmlDoc = $.parseXML( sitioXml ),
                $sitioXml = $( xmlDoc );

                $('#inputNombre').val(inputText) ;

                $title = $sitioXml.find( "latitud" );
                $('#inputLatitud').val($title.text()) ;

                $title = $sitioXml.find( "longitud" );
                $('#inputLongitud').val($title.text()) ;

                $title = $sitioXml.find( "regionId" );
                $('#inputRegion').val($title.text()) ;

                $title = $sitioXml.find( "tarifaId" );
                $('#inputTarifa').val($title.text()) ;

                $title = $sitioXml.find( "precioGasLp" );
                $('#inputGasLp').val($title.text()) ;

                $title = $sitioXml.find( "precioGasNatural" );
                $('#inputGasNatural').val($title.text()) ;

                $title = $sitioXml.find( "precioDiesel" );
                $('#inputDiesel').val($title.text()) ;
                
                 $title = $sitioXml.find( "inversion" );
                $('#inputInversion').val($title.text()) ;
                
                   $title = $sitioXml.find( "poderCalorificoGasLp" );
                $('#pCalGasLp').val($title.text()) ;
                
                   $title = $sitioXml.find( "poderCalorificoGasNatural" );
                $('#pCalGasNatural').val($title.text()) ;
                
                   $title = $sitioXml.find( "poderCalorificoDiesel" );
                $('#pCalDiesel').val($title.text()) ;

                $(sitioXml).find('telefonoSitio').each(function () {
                var id, name;
                $('#inputTipoTelefono').val($(this).children('tipoTelefono').text()) ;
                
                $('#inputNumTelefono').val($(this).children('numTelefono').text()) ;

                });

                $title = $sitioXml.find( "numeroExt" );
                $('#inputNumeroExterno').val($title.text()) ;

                $title = $sitioXml.find( "numeroInt" );
                $('#inputNumeroInterno').val($title.text()) ;

                $title = $sitioXml.find( "codigoPostal" );
                $('#inputCodigoPostal').val($title.text()) ;

                $title = $sitioXml.find( "calle" );
                $('#inputCalle').val($title.text()) ;

                $title = $sitioXml.find( "colonia" );
                $('#inputColonia').val($title.text()) ;

                $title = $sitioXml.find( "estado" );
                $('#inputEstado').val($title.text()) ;

                $title = $sitioXml.find( "ciudad" );
                $('#inputCiudad').val($title.text()) ;

                $title = $sitioXml.find( "pais" );
                $('#inputPais').val($title.text()) ;


                }

                }


                xmlhttp.open("GET","infoSitio?nombreSitio="+inputText,true);
                xmlhttp.send();


                }

                function limpiarRepetidos(){
                var foo1 = document.getElementById("selectUsuariosDisponibles");
                var foo2 = document.getElementById("selectUsuariosActuales");
                for (var i = 0; i< foo1.options.length; i++){

                if (foo1.options[i] == null)
                continue;

                for (var j = 0; j< foo2.options.length; j++){
                if (foo1.options[i].value == null)
                continue;
                if (foo2.options[j].value == foo1.options[i].value){
                foo1.options.remove(i);
                i--;
                }
                }
                }

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
