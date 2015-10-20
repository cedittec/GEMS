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
        <title>Costos Globales</title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="page-title">
                        <h1>Estado Financiero
                            <small>Desglose de costos</small>
                        </h1>
                        
                    </div>
                </div>
                <!-- /.col-sm-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                          <li style="padding-top:6px"> <a href="${createLink(controller:'EstadoFinanciero', action:'index')}">Estado Financiero</a></li>
                            <li class="active" style="padding-top:6px"><i class="fa fa-dashboard"></i> Costos Globales</li>

                        <g:if test="${numeroSitios > 0}">
                                <li class="pull-right" style="padding-left:1px;">
                                    <div id="reportrangeperiodo" class="btn btn-blue btn-square date-picker">
                                        <i class="fa fa-calendar"></i>&nbsp;Generar Reporte:
                                        <span class="date-range"></span> <i class="fa fa-caret-down"></i>    
                                        <button id="comenzarPDF" style="display: none;"/>
                                    </div>
                                </li>
                            </g:if>

                    <li class="pull-right" style="padding-left:1px;" >
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
        <!-- /.row -->
        <!-- end PAGE TITLE AREA -->

                <!-- begin DASHBOARD CIRCLE TILES -->
        <g:if test="${numeroSitios > 0}">
            <div id="contenidoId">
                <div class="row">

  <!-- Area Chart Example -->
                    <div class="col-sm-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading orange">
                                    <i class="fa fa-bolt fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Costo de energ&iacute;a el&eacute;ctrica
                                </div>
                                <div id="costoEnergiaElectricaId" class="circle-tile-number text-faded">

                                <g:formatNumber number="${costoEnergiaElectrica}" type="number" maxFractionDigits="3" /> $
                                </div>

                                <a href="${createLink(controller:'CostosGlobales', action:'costoEnergiaElectrica')}" class="circle-tile-footer">
                                    <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading red">
                                    <i class="fa fa-fire fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content red">
                                <div class="circle-tile-description text-faded">
                                    Costo de energ&iacute;a t&eacute;rmica
                                </div>
                                <div id="costoEnergiaTermicaId" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${costoEnergiaTermica}" type="number" maxFractionDigits="3" /> $
                                </div>
                                <a href="${createLink(controller:'CostosGlobales', action:'costoEnergiaTermica')}" class="circle-tile-footer">
                                    <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading green">
                                    <i class="fa fa-apple fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Costo de combustibles
                                </div>
                                <div id="costoCombustiblesId" class="circle-tile-number text-faded">                                 
                                    <g:formatNumber number="${costoCombustibles}" type="number" maxFractionDigits="3" /> $
                                </div>
                                <a href="${createLink(controller:'CostosGlobales', action:'costoCombustibles')}" class="circle-tile-footer">
                                    <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                                </a>
                            </div>
                        </div>
                    </div>



                    <!-- /.col-lg-12 -->



                    <!-- Bar Chart Example -->
                    <div class="col-sm-12">
                        <div class="portlet portlet-blue">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Hist&oacute;rico de costos</h4>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="costosglobales-chart-bar"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col-lg-12 -->


                </div>
                <!-- /.row -->
            </div>
        </g:if>
        <g:else>
        </g:else>


        <g:jasperReport type="hidden" jasper="CostosGlobalesIndex" format="PDF" name="PDF" action="createCostosGlobalesIndex" controller="pdfUtil">                 
            <input name="sitioIdPDF" id="sitioIdPDF" />
            <input name="fechaIniPDF" id="fechaIniPDF" />
            <input name="fechaFinPDF" id="fechaFinPDF" />

            <input name="energiaElectrica" id="costoEnergiaElectrica" />
            <input name="energiaTermica" id="costoEnergiaTermica" />
            <input name="costoCombustible" id="costoCombustibles" />

        </g:jasperReport>  

    </div>
<!-- Morris Charts -->
    <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
    <!-- Flot Charts  -->
    <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.js"></script>
    <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.resize.js"></script>
    <!-- Sparkline Charts -->
    <script src="${request.contextPath}/Flex/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- Moment.js -->
    <script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>    
    <script src="${request.contextPath}/Flex/js/demo/costosGlobales.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'costosglobales-chart-bar',
        data: [
        { y:  '${arregloGraficaMeses[0]}', e: ${arregloGraficaValores[0]},  t: ${arregloGraficaValores2[0]}, c: ${arregloGraficaValores3[0]} },
        { y:  '${arregloGraficaMeses[1]}', e: ${arregloGraficaValores[1]},  t: ${arregloGraficaValores2[1]}, c: ${arregloGraficaValores3[1]} },
        { y:  '${arregloGraficaMeses[2]}', e: ${arregloGraficaValores[2]},  t: ${arregloGraficaValores2[2]}, c: ${arregloGraficaValores3[2]} },
        { y:  '${arregloGraficaMeses[3]}', e: ${arregloGraficaValores[3]},  t: ${arregloGraficaValores2[3]}, c: ${arregloGraficaValores3[3]} },
        { y:  '${arregloGraficaMeses[4]}', e: ${arregloGraficaValores[4]},  t: ${arregloGraficaValores2[4]}, c: ${arregloGraficaValores3[4]} },
        { y:  '${arregloGraficaMeses[5]}', e: ${arregloGraficaValores[5]},  t: ${arregloGraficaValores2[5]}, c: ${arregloGraficaValores3[5]} },
        { y:  '${arregloGraficaMeses[6]}', e: ${arregloGraficaValores[6]}, t: ${arregloGraficaValores2[6]}, c: ${arregloGraficaValores3[6]} },
        { y:  '${arregloGraficaMeses[7]}', e: ${arregloGraficaValores[7]}, t: ${arregloGraficaValores2[7]}, c: ${arregloGraficaValores3[7]} },
        { y:  '${arregloGraficaMeses[8]}', e: ${arregloGraficaValores[8]}, t: ${arregloGraficaValores2[8]}, c: ${arregloGraficaValores3[8]} },
        { y:  '${arregloGraficaMeses[9]}', e: ${arregloGraficaValores[9]}, t: ${arregloGraficaValores2[9]}, c: ${arregloGraficaValores3[9]} },
        { y:  '${arregloGraficaMeses[10]}', e: ${arregloGraficaValores[10]}, t: ${arregloGraficaValores2[10]}, c: ${arregloGraficaValores3[10]} },
        { y:  '${arregloGraficaMeses[11]}', e: ${arregloGraficaValores[11]}, t: ${arregloGraficaValores2[11]}, c: ${arregloGraficaValores3[11]} }

        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c'],
        labels: ['Evitado por energias alternas', 'Evitado energia termica por energias alternas', 'Costo Total Combustible'],
        barColors: ['#f39c12','#e74c3c', '#16a085'],
        resize: true
        });
    </g:javascript>      


</body>
</html>
