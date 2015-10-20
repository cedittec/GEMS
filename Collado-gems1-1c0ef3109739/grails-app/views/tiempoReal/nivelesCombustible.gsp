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
                    <div class="col-lg-12">
                        <div class="page-title">
                            <h1>Monitoreo en tiempo real
                                <small>Combustibles</small>
                            </h1>
                             <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                                 <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'TiempoReal', action:'index')}"> Monitoreo en tiempo real</a></li>
                                <li class="active" style="padding-top:6px"> Combustibles</li>
                               
                               <li class="pull-right" >
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
                    <div class = "col-lg-12">
                        <div class="tile yellow dash-demo-tile" style="height:160px">
                                    <div class="col-lg-6" style="height:100%">
                                        <div class="circle-tile-description text-faded" style="padding-top:20px">Temperatura del Gas LP</div>
                                        <div id="tempCombustibleTermico" class="circle-tile-number text-faded">
                                            <g:formatNumber number="${tempCombustibleTermico}" type="number" maxFractionDigits="3" /> &#176;C
                                        </div>
                                        <div class="circle-tile-description text-faded"> Sistema t&eacute;rmico </div>
                                        &nbsp;<br>&nbsp;
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="circle-tile-description text-faded" style="padding-top:20px">Temperatura del Diesel</div>
                                        <div  id="tempCombustibleCogeneracion" class="circle-tile-number text-faded">
                                         <g:formatNumber number="${tempCombustibleCogeneracion}" type="number" maxFractionDigits="3" /> &#176;C
                                         </div>
                                         <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                        &nbsp;<br>&nbsp;
                            		</div>
                        </div>  
                    </div>
                	<div class="col-lg-5">
                    	<div class="col-lg-6 col-sm-6">
                        	<div class="tile blue dash-demo-tile" style="height:280px">
                            	<h4><i class="fa fa-fire fa-fw"></i> Nivel del tanque de Gas LP del Sistema T&eacute;rmico</h4>
                                <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultadoNivelTanqueTermico}">
                                	<span id="nievelTanqueTermico" class="percent"><g:formatNumber number="${resultadoNivelTanqueTermico}" type="number" maxFractionDigits="3" /></span>
                                </div>
                             </div>
                    	</div>
                        <div class="col-lg-6 col-sm-6">
                        	<div class="tile green dash-demo-tile" style="height:280px">
                            	<h4><i class="fa fa-building-o fa-fw"></i> Nivel del tanque de Diesel del Sistema de Cogeneraci&oacute;n</h4>
                                <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultadoNivelTanqueCondensados}">
                                	<span id="nivelTanqueCogeneracion" class="percent"><g:formatNumber number="${resultadoNivelTanqueCondensados}" type="number" maxFractionDigits="3" /></span>
                                </div>
                            </div>
                        </div>
                         
                        <div class="col-lg-6">
                                <div class="circle-tile">
                                    <div class="circle-tile-content purple">
                                        <div class="circle-tile-description text-faded" style="padding:0px">Presi&oacute;n del gas LP</div>
                                        <div  id="presionCombustibleTermico" class="circle-tile-number text-faded">
                                            <g:formatNumber number="${presionCombustibleTermico}" type="number" maxFractionDigits="3" /> Kg/cm^2
                                        </div>
                                        <div class="circle-tile-description text-faded"> Sistema t&eacute;rmico </div>
                                        &nbsp;<br>&nbsp;
                                    </div>
                                </div>                            
                          </div>
                          <div class="col-lg-6"> 
                                <div class="circle-tile">
                                    <div class="circle-tile-content red">
                                        <div class="circle-tile-description text-faded" style="padding:0px">Presi&oacute;n del Diesel</div>
                                        <div  id="presionCombustibleCogeneracion"  id="presionCombustibleCogeneracion" class="circle-tile-number text-faded">
                                            <g:formatNumber number="${presionCombustibleCogeneracion}" type="number" maxFractionDigits="3" /> Kg/cm^2
                                        </div>
                                       <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                        &nbsp;<br>&nbsp;
                                    </div>
                                </div>  
                          </div>
                    </div><!--col5-->
                    <div class="col-lg-7">
                         <div class="portlet portlet-green">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4><i class="fa fa-retweet fa-fw"></i>Flujo de combustibles</h4>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="monitoreoFlujoCombustibles-chart-bar" style="height:420px"></div>
                                </div>
                            </div>
                         </div>
                     </div><!--col-->
                    
                
                                
                   
                </div><!--row-->
                    

              </div>
            
            </g:if>
            <g:else>
            </g:else>
                    
            </div>
            <!-- /.page-content -->

    <!-- HISRC Retina Images -->
    <script src="${request.contextPath}/Flex/js/plugins/hisrc/hisrc.js"></script>

    
    <!-- Easy Pie Chart -->
    <script src="${request.contextPath}/Flex/js/plugins/easypiechart/jquery.easypiechart.min.js"></script>    
    <script src="${request.contextPath}/Flex/js/demo/donutChart-demo.js"></script>
    <script src="${request.contextPath}/Flex/js/demo/miEasyPie.js"></script>
    <script src="${request.contextPath}/Flex/js/demo/tiempoRealCombustibles.js"></script>
    
    <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
    
    <g:javascript>        
        
        
        Morris.Bar({
            element: 'monitoreoFlujoCombustibles-chart-bar',
            data: [
              { y:  'Sistema Termico', a: ${graficaFlujoCombustibles[0]} },
              { y:  'Sistema de Cogeneracion', a: ${graficaFlujoCombustibles[1]}}             
            ],
            xkey: 'y',
            ykeys: ['a'],
             yLabelFormat: function(y) {
            return  y + " m^3/h";
            },
            labels: ['Flujo del combustible'],
            barColors: ['#16a061'],
            resize: true
            });
            
    </g:javascript>
               
  </body>
</html>
