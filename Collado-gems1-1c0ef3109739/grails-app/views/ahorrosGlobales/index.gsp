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
                        <h1>Estado Financiero
                            <small>Desglose de ahorros</small>
                        </h1>

                    </div>
                </div>
                <!-- /.col-sm-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">

                        <li style="padding-top:6px"><i class="fa fa-dashboard"></i><a href="${createLink(controller:'EstadoFinanciero', action:'index')}"> Estado Financiero</a></li>
                        <li class="active" style="padding-top:6px"> Ahorros globales</li>

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

        <g:if test="${numeroSitios > 0}">    
            <div id="contenidoId">
                <div class="row">

                    <!-- Area Chart Example -->
                    <div class="col-lg-4 col-sm-6">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading green">               
                                    <img src="${request.contextPath}/Flex/img/CFElogo.png" width="100%" style="padding-top:10px">
                                </div>
                            </a>
                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Costo energ&iacute;a total CFE
                                </div>
                                <div id="energiaCFE0" class="circle-tile-number text-faded">                                     
                                <g:formatNumber number="${arregloEnergiaTotalCFE[0]}" type="number" maxFractionDigits="3" /> $
                                </div>
                                &nbsp;
                                <li class="panel" style="list-style-type:none; background-color:#16a085">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoe">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="costoe" style="text-align:justify; padding:10px">
                                        <li>
                                            Costo energ&iacute;a base:
                                        </li>
                                        <li id="energiaCFE1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiaTotalCFE[1]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                        <li>
                                            Costo energ&iacute;a  intermedia: 
                                        </li>
                                        <li id="energiaCFE2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                         
                                            <g:formatNumber number="${arregloEnergiaTotalCFE[2]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                        <li>
                                            Costo energ&iacute;a  punta:
                                        </li>
                                        <li id="energiaCFE3" style="text-align:right">
                                            <g:formatNumber number="${arregloEnergiaTotalCFE[3]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                    </ul>
                                </li>

                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading yellow">
                                    <i class="fa fa-bolt fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Costo evitado en energ&iacute;a el&eacute;ctrica por fuentes alternas
                                </div>
                                <div id="energiaElectricaAlterna0" class="circle-tile-number text-faded">                                     
                                    <g:formatNumber number="${costoEvitadoElectricaAlternas}" type="number" maxFractionDigits="3" /> $
                                </div>

                                <a href="${createLink(controller:'AhorrosGlobales', action:'costoEvitadoElectrica')}"> M&aacute;s informaci&oacute;n<i class="fa fa-chevron-circle-right"></i></a>
                            </div>       
                        </div>
                    </div>
                    <div class="col-lg-4 col-sm-6">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading orange">
                                    <i class="fa fa-fire fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Costo evitado en energ&iacute;a t&eacute;rmica por fuentes alternas
                                </div>
                                <div id="energiaTermicaAlterna0" class="circle-tile-number text-faded">                                    
                                    <g:formatNumber number="${costoEvitadoTermicasAlternas}" type="number" maxFractionDigits="3" /> $
                                </div>

                                <a href="${createLink(controller:'AhorrosGlobales', action:'costoEvitadoTermica')}"> M&aacute;s informaci&oacute;n<i class="fa fa-chevron-circle-right"></i></a>
                            </div>
                        </div>
                    </div>



                    <!-- /.col-lg-12 -->



                    <!-- Bar Chart Example -->
                    <div class="col-lg-12">
                        <div class="portlet portlet-blue">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Hist&oacute;rico de ahorros</h4>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="ahorrosglobales-chart-bar"></div>
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


        <g:jasperReport type="hidden" jasper="AhorrosGlobales" format="PDF" name="PDF" action="createAhorrosGlobales" controller="pdfUtil">           
            <input name="sitioIdPDF" id="sitioIdPDF" />
            <input name="fechaIniPDF" id="fechaIniPDF" />
            <input name="fechaFinPDF" id="fechaFinPDF" />

            <input name="energiaElectrica" id="idenergiaElectrica" />
            <input name="energiaTermica" id="idenergiaTermica" />

            <input name="energiaCFE" id="idenergiaCFE" />
            <input name="energiaBase" id="idenergiaBase" />

            <input name="energiaIntermedia" id="idenergiaIntermedia" />
            <input name="energiaPunta" id="idenergiaPunta" />

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

    <script src="${request.contextPath}/Flex/js/demo/ahorrosIndex.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'ahorrosglobales-chart-bar',
        data: [
        { y:  '${arregloGraficaMeses[0]}', e: ${arregloGraficaValores[0]},  t: ${arregloGraficaValores2[0]} },
        { y:  '${arregloGraficaMeses[1]}', e: ${arregloGraficaValores[1]},  t: ${arregloGraficaValores2[1]}},
        { y:  '${arregloGraficaMeses[2]}', e: ${arregloGraficaValores[2]},  t: ${arregloGraficaValores2[2]} },
        { y:  '${arregloGraficaMeses[3]}', e: ${arregloGraficaValores[3]},  t: ${arregloGraficaValores2[3]} },
        { y:  '${arregloGraficaMeses[4]}', e: ${arregloGraficaValores[4]},  t: ${arregloGraficaValores2[4]} },
        { y:  '${arregloGraficaMeses[5]}', e: ${arregloGraficaValores[5]},  t: ${arregloGraficaValores2[5]} },
        { y:  '${arregloGraficaMeses[6]}', e: ${arregloGraficaValores[6]}, t: ${arregloGraficaValores2[6]} },
        { y:  '${arregloGraficaMeses[7]}', e: ${arregloGraficaValores[7]}, t: ${arregloGraficaValores2[7]} },
        { y:  '${arregloGraficaMeses[8]}', e: ${arregloGraficaValores[8]}, t: ${arregloGraficaValores2[8]} },
        { y:  '${arregloGraficaMeses[9]}', e: ${arregloGraficaValores[9]}, t: ${arregloGraficaValores2[9]} },
        { y:  '${arregloGraficaMeses[10]}', e: ${arregloGraficaValores[10]}, t: ${arregloGraficaValores2[10]} },
        { y:  '${arregloGraficaMeses[11]}', e: ${arregloGraficaValores[11]}, t: ${arregloGraficaValores2[11]} }

        ],
        xkey: 'y',
        ykeys: ['e', 't'],
        labels: ['Evitado electrica por energias alternas', 'Evitado energia termica por energias alternas'],
        barColors: ['#f39c12','#e74c3c'],
        resize: true
        });
    </g:javascript>

</body>
</html>
