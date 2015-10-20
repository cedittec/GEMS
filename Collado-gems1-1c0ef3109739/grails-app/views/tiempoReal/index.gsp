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
        <title>Estado Financiero</title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title">
                        <h1>Monitoreo en tiempo real
                                <small>Vista general</small>
                            </h1>
                             <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                                <li class="active" style="padding-top:6px"><i class="fa fa-dashboard"></i> Monitoreo en tiempo real</li>                       
                            
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
                <!-- /.col-sm-12 -->
            </div>
            <!-- /.row -->
            <!-- end PAGE TITLE AREA -->

             <g:if test="${numeroSitios > 0}">          
              
                 
                 <div id="contenidoId">
                 
                 <div class="row">
                    
                     <div class="col-sm-4">
                         <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading orange">
                                    <i class="fa fa-fire fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Temperatura del tanque de inercia
                                </div>
                                <div id="tempTanqueInercia" class="circle-tile-number text-faded">${resultTempTanqueInercia}&#176;C
                                </div>
                                 <a href="${createLink(controller:'TiempoReal', action:'energiaTermica')}" class="circle-tile-footer">M&aacute;s informaci&oacute;n <i class="fa fa-chevron-circle-right"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                         <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading yellow">
                                    <i class="fa fa-bolt fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Potencia generada
                                </div>
                                <div id="potenciaGenerada" class="circle-tile-number text-faded">
                                    
                                    <g:formatNumber number="${resultPotenciaTotal}" type="number" maxFractionDigits="3" /> kW
                                </div>
                                 <a href="${createLink(controller:'TiempoReal', action:'energiaElectrica')}" class="circle-tile-footer">M&aacute;s informaci&oacute;n <i class="fa fa-chevron-circle-right"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                         <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading blue">
                                    <i class="fa fa-fire-extinguisher fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Niveles de Combustible
                                </div>
                                <div id="consumoTermico" class="ircle-tile-description text-faded">
                                    Consumo sistema T&eacute;mico <g:formatNumber number="${comsutiblesArreglo[0]}" type="number" maxFractionDigits="3" /> %
                                </div>
                                <div id="consumoCogeneracion" class="ircle-tile-description text-faded">
                                    Consumo sistema Cogeneraci&oacuten; <g:formatNumber number="${comsutiblesArreglo[1]}" type="number" maxFractionDigits="3" /> %
                                </div>
                                 <a href="${createLink(controller:'TiempoReal', action:'nivelesCombustible')}" class="circle-tile-footer">M&aacute;s informaci&oacute;n <i class="fa fa-chevron-circle-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                <div class="col-sm-3">
                    <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading yellow">
                                    <i class="fa fa-sun-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Radiaci&oacute;n solar
                                </div>
                                <div id="radiacionSolar" class="circle-tile-number text-faded">
                                   <g:formatNumber number="${radiacionSolar}" type="number" maxFractionDigits="3" /> W/m^2
                                </div>
                                &nbsp;
                            </div>
                        </div>
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading blue">
                                    <i class="fa fa-cloud fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Niveles de humedad
                                </div>
                                <div id="nivelesHumedad" class="circle-tile-number text-faded">
                                    <g:formatNumber number="${nivelesHumedad}" type="number" maxFractionDigits="3" /> %
                                </div>
                                &nbsp;
                            </div>
                        </div>
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading purple">
                                    <i class="fa fa-coffee fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content purple">
                                <div class="circle-tile-description text-faded">
                                    Temperatura
                                </div>
                                <div id="temp" class="circle-tile-number text-faded">
                                     <g:formatNumber number="${temp}" type="number" maxFractionDigits="3" /> &#176;C
                                </div>
                                &nbsp;
                            </div>
                        </div>
                </div>
                <div class="col-sm-9">
                
                    <div class="portlet portlet-green">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <h4>Diagrama de principios</h4>
                            </div>
                            <div class="clearfix"></div>
                         </div>
                         <div class="portlet-body">
                             <div class="pull-right">
                                <button id="zoomIn" class="btn-small btn-primary applyBtn btn btn-default">+ Zoom</button>
                                <button id="zoomOut" class="btn-small btn-primary applyBtn btn btn-default">- Zoom</button>
                             </div>
                             <hr/>
                             <canvas id="diagramaPrincipios" style="width: 100%">
                                Actualice la version de su navegador para poder ver este contenido
                             </canvas>
                             
                             
                             <!--<img src="${request.contextPath}/Flex/img/Generacion.png" id="image"/>-->
                         </div>
                    </div>

                </div><!--col-->
                </div>
               
                </div>
                
        </g:if>
        <g:else>
        </g:else>

         <g:jasperReport type="hidden" jasper="MonTRealIndex" format="PDF" name="PDF" action="createMonTRealIndex" controller="pdfUtil">           
        <input hidden name="sitioIdPDF" id="sitioIdPDF" />
        <input hidden name="fechaIniPDF" id="fechaIniPDF" />
        <input hidden name="fechaFinPDF" id="fechaFinPDF" />

        <input hidden name="radiacion" id="idradiacion" />
        <input hidden name="humedad" id="idhumedad" />
        <input hidden name="temperatura" id="idtemperatura" />
        <input hidden name="tempTanque" id="idtempTanque" />
        <input hidden name="potencia" id="idpotencia" />
        <input hidden name="nivel" id="idnivel" />
        </g:jasperReport> 



        </div>
<!-- Morris Charts -->
        <script> var contextPth = "${request.contextPath}" </script>                
        <script src="${request.contextPath}/Flex/js/demo/canvasDPrincipios.js"></script>         
        <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
        <!-- Flot Charts  -->
        <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.resize.js"></script>
        <!-- Sparkline Charts -->
        <script src="${request.contextPath}/Flex/js/plugins/sparkline/jquery.sparkline.min.js"></script>
        <!-- Moment.js -->
        <script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>                  
         <script src="${request.contextPath}/Flex/js/demo/tiempoRealIndex.js"></script>

    </body>
</html>
