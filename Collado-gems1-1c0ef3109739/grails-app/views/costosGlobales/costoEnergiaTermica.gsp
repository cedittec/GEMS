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
        <title>>GEMS - Costos Electricos</title>
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
                            <li style="padding-top:6px"> <a href="${createLink(controller:'CostosGlobales', action:'index')}">Costos globales</a></li>
                            <li class="active" style="padding-top:6px">Energ&iacute;a T&eacute;rmica</li>

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
                    <div class="col-sm-9">
                        <div class="col-sm-6">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading yellow">
                                        <i class="fa fa-bolt fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content yellow">
                                    <div class="circle-tile-description text-faded">
                                        Eficiencia t&eacute;rmica
                                    </div>
                                    <div id="arregloEficienciaTermica0" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${arregloEficienciaTermica[0]}" type="number" maxFractionDigits="3" /> % 
                                    </div>
                                    <li class="panel" style="list-style-type:none; background-color:#f1c40f">
                                        <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#energia">
                                            <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                        </a>
                                        <ul class="collapse nav text-faded" id="energia" style="text-align:justify; padding:10px">
                                            <li>
                                                Energ&iacute;a aprovechada por el S. de Cogeneraci&oacute;n:
                                            </li>
                                            <li id="arregloEficienciaTermica1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                            
                                                <g:formatNumber number="${arregloEficienciaTermica[1]}" type="number" maxFractionDigits="3" /> kWh 
                                            </li>
                                            <li>
                                                Energ&iacute;a aprovechada por el S. T&eacute;rmico: 
                                            </li>
                                            <li id="arregloEficienciaTermica2" style="text-align:right">
                                                <g:formatNumber number="${arregloEficienciaTermica[2]}" type="number" maxFractionDigits="3" /> kWh 
                                            </li>

                                        </ul>
                                    </li>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading green">
                                        <i class="fa fa-credit-card fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content green">
                                    <div class="circle-tile-description text-faded">
                                        Costo evitado por fuentes alternas
                                    </div>
                                    <div id="arregloCostoEvitadoAlternas0" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${arregloCostoEvitadoAlternas[0]}" type="number" maxFractionDigits="3" /> $ 
                                    </div>
                                    <li class="panel" style="list-style-type:none; background-color:#16a085">
                                        <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoe">
                                            <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                        </a>
                                        <ul class="collapse nav text-faded" id="costoe" style="text-align:justify; padding:10px">
                                            <li>
                                                Costo evitado en el S. de Cogeneraci&oacute;n:
                                            </li>
                                            <li id="arregloCostoEvitadoAlternas1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px;">

                                                <g:formatNumber number="${arregloCostoEvitadoAlternas[1]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en el S. T&eacute;rmico: 
                                            </li>
                                            <li id="arregloCostoEvitadoAlternas2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">
                                                <g:formatNumber number="${arregloCostoEvitadoAlternas[2]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en el Colector Solar:
                                            </li>
                                            <li id="arregloCostoEvitadoAlternas3" style="text-align:right">
                                                <g:formatNumber number="${arregloCostoEvitadoAlternas[3]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                        </ul>
                                    </li>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div hidden class="col-lg-3">
                        <div class="portlet portlet-blue">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Precio unitario de combustibles</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div class="portlet-body">

                                <div class="table-responsive">
                                    <table class="table table-striped" style="margin:0px">
                                        <thead>
                                            <tr>
                                                <th>Sistema</th>
                                                <th>Precio unitario</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Cogeneraci&oacute;n</td>
                                                <td id="unitarioCogeneracionId">
                                                    ${arregloPrecioUnitarioCombustibles[0]} $</td>
                                            </tr>
                                            <tr>
                                                <td>T&eacute;rmico</td>
                                                <td id="unitarioTermicoId">${arregloPrecioUnitarioCombustibles[1]} $</td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div><!--tabla precios unitarios-->
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-9">


                        <div class="col-sm-12">
                            <div class="portlet portlet-blue">
                                <div class="portlet-heading">
                                    <div class="portlet-title">
                                        <h4>Consumo de energ&iacute;a</h4>
                                    </div>

                                    <div class="clearfix"></div>
                                </div>
                                <div id="barChart" class="panel-collapse collapse in">
                                    <div class="portlet-body">
                                        <div id="costostermicos-chart-bar"></div>
                                    </div>
                                </div>
                            </div>
                        </div><!--col12-->
                    </div><!--col9-->
                    <div class="col-sm-3">

                        <div class="portlet portlet-green">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Costo por kW/h de energ&iacute;a t&eacute;rmica</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div class="portlet-body">

                                <div class="table-responsive">
                                    <table class="table table-striped" style="margin:0px">
                                        <thead>
                                            <tr>
                                                <th>Sistema</th>
                                                <th>Cargo por kW/h</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Cogeneraci&oacute;n</td>
                                                <td id="arregloKWHEnergiaTermica0">${arregloKWHEnergiaTermica[0]} $</td>
                                            </tr>
                                            <tr>
                                                <td>T&eacute;rmico</td>
                                                <td id="arregloKWHEnergiaTermica1">${arregloKWHEnergiaTermica[1]} $</td>
                                            </tr>
                                            <tr>
                                                <td>Colector Solar</td>
                                                <td id="arregloKWHEnergiaTermica2">${arregloKWHEnergiaTermica[2]} $</td>
                                            </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div><!--tabla precios unitarios-->
                        <!--col12-->
                    </div><!--col3-->

                </div><!--row-->
            </div>
        </g:if>
        <g:else>
        </g:else>


        <g:jasperReport type="hidden" jasper="CostosTermicos" format="PDF" name="PDF" action="createCostosTermicos" controller="pdfUtil">                 
            <input hidden  name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="costoAlternas" id="idCostoAlternas" />
            <input hidden name="costoCogeneracion" id="idCostoCogeneracion" />
            <input hidden name="costoTermico" id="idCostoTermico" />
            <input hidden name="costoSolar" id="idCostoSolar" />

            <input hidden name="cargoCogeneracion" id="idCargoCogeneracion" />
            <input hidden name="cargoTermico" id="idCargoTermico" />
            <input hidden name="cargoSolar" id="idCargoSolar" />        

            <input hidden name="precioCogeneracion" id="idPrecioCogeneracion" />
            <input hidden name="precioTermico" id="idPrecioTermico" />
            <input hidden name="precioSolar" id="idPrecioSolar" />

            <input hidden name="eficienciaTermica" id="idEficienciaTermica" />
            <input hidden name="energiaCogeneracion" id="idEnergiaCogeneracion" />
            <input hidden name="energiaTermico" id="idEnergiaTermico" />
            <input hidden name="energiaSolar" id="idEnergiaSolar" />


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

    <script src="${request.contextPath}/Flex/js/demo/costoEnergiaTermica.js"></script>
    <g:javascript>        
        Morris.Bar({
        element: 'costostermicos-chart-bar',
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
        yLabelFormat: function(y) {
        return y + " $/kWh";
        },
        labels: ['S. Cogeneración', 'S. Térmico', 'Colector Solar'],
        barColors: ['#f39c12','#e74c3c', '#16a085'],
        resize: true
        });
    </g:javascript>  
</body>
</html>
