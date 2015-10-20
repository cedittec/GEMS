<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <head>
        <meta name="layout" content="frontend">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Monitoreo Tiempo Real</title>
    </head> 
 </head>
  <body>
      <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="page-title">
                            <h1>Monitoreo en tiempo real
                                <small>Energ&iacute;a El&eacute;ctrica</small>
                            </h1>
                             <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                                 <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'TiempoReal', action:'index')}"> Monitoreo en tiempo real</a></li>
                                <li class="active" style="padding-top:6px"> Energía eléctrica</li>

                                                                                        
                                
                                 <g:if test="${numeroSitios > 0}">
                            <li class="pull-right" style="margin-left: 1px">
                                    <div>
                                        <button class="form-control"id="comenzarPDF" style="background:#428bca; color:#FFF; border-color:#357ebd;">
                                            Generar PDF </button>
                                    </div>
                                </li>
                             </g:if>
                            
                            <li class="pull-right" style="margin-left: 1px">
                                <g:if test="${numFuentes < 1}">

                                    <select class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                        <option >Sin Fuentes</option>

                                    </g:if>

                                    <g:else>
                                        <select id="fuentesSelect" class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                             <g:each var="fuente"  in="${fuentes}" >
                                            <option value="${fuente.id}">${fuente.nombre}</option>
                                           </g:each>  
                                        </select>

                                    </g:else>


                                </li>

                             <li class="pull-right" >
                            <g:if test="${numeroSitios < 1}">

                                <select class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                    <option >Sin sitios</option>

                                </g:if>

                                <g:else>
                                    <select id="sitiosSelect" class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                        <g:each var="sitio"  in="${sitios}" >
                                            <option value="${sitio.id}">${sitio.nombre}</option>
                                        </g:each>   
                                    </select>

                                </g:else>


                        </li>        
                                 
                            </ol>
                            
                        </div>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <!-- end PAGE TITLE AREA -->

                
                <g:if test="${numeroSitios > 0}">                        
                 <div id="contenidoId">  
                     
                <!-- begin DASHBOARD CIRCLE TILES -->
                <div class="row">
                    <div class="col-sm-3">
                    	<div class="circle-tile">
                    		<a href="#">
                                <div class="circle-tile-heading green" >
                                    <img src="${request.contextPath}/Flex/img/CFElogo.png" width="100%" style="padding-top:10px">
                                </div>
                            </a>
                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Potencia CFE
                                </div>
                                <div id="potenciaCFE" class="circle-tile-number text-faded">
                                     
                                    <g:formatNumber number="${resultadoPotenciaCFE}" type="number" maxFractionDigits="3" /> kW
                                </div>
                               &nbsp;
                            </div>
                        </div>
                     </div> 
                     <div class="col-sm-3">
                     	<div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading orange">
                                    <i class="fa fa-sun-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Potencia del S. fotovolt&aacute;ico
                                </div>
                                <div id="potenciaFotovoltaico" class="circle-tile-number text-faded">
                                    
                                    <g:formatNumber number="${resultadoPotenciaFotovoltaico}" type="number" maxFractionDigits="3" /> kW
                                </div>
                                &nbsp;
                            </div>
                        </div>
                     </div> 
                     <div class="col-sm-3">
                         <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading dark-blue">
                                    <i class="fa fa-building-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content dark-blue">
                                <div class="circle-tile-description text-faded">
                                    Potencia del S. de cogeneraci&oacute;n
                                </div>
                                <div id="potenciaCogeneracion" class="circle-tile-number text-faded">
                                    
                                    <g:formatNumber number="${resultadoPotenciaCogeneracion}" type="number" maxFractionDigits="3" /> kW
                                </div>
                                &nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                         <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading blue">
                                    <i class="fa fa-bullseye fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Potencia total
                                </div>
                                <div id="potenciaTotal" class="circle-tile-number text-faded">
                                    
                                    <g:formatNumber number="${resultadoPotenciaTotal}" type="number" maxFractionDigits="3" /> kW
                                </div>
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                
                <div class="col-lg-12">
                
                    <div class="tile blue" style="height: 320px">
                                    <h4><i class="fa fa-bolt"></i> Potencia total  
                                    </h4>
                                    <p class="small text-faded">
                                        Potencia total del <strong>Sistema</strong>
                                    </p>
                                    <div class="flot-chart flot-chart-dashboard">
                                        <div class="flot-chart-content" id="miflot-chart-moving-line"></div>
                                    </div>
                                </div>

				</div><!--col-->
                </div>

 
                
               
            </div>
            <!-- /.page-content -->
                                
          <g:jasperReport type="hidden" jasper="MTRealEElectrica" format="PDF" name="PDF" action="createMTRealEElectrica" controller="pdfUtil">           
        <input hidden name="sitioIdPDF" id="sitioIdPDF" />
        <input hidden name="fuenteIdPDF" id="fuenteIdPDF" />
        <input hidden name="fechaIniPDF" id="fechaIniPDF" />
        <input hidden name="fechaFinPDF" id="fechaFinPDF" />
        </g:jasperReport> 
                    </div>
                
        </g:if>
        <g:else>
        </g:else>
                                
        <!-- Flot Charts  -->
        <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.resize.js"></script>
        <script src="${request.contextPath}/Flex/js/demo/energiaElectrica.js"></script>


    

               
  </body>
</html>
