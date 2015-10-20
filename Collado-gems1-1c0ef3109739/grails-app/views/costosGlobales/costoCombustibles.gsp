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
                            <small>Vista general</small>
                        </h1>

                    </div>
                </div>
                <!-- /.col-sm-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                        <li style="padding-top:6px"> <a href="${createLink(controller:'EstadoFinanciero', action:'index')}">Estado Financiero</a></li>
                        <li style="padding-top:6px"> <a href="${createLink(controller:'CostosGlobales', action:'index')}">Costos globales</a></li>
                        <li class="active" style="padding-top:6px">Combustibles</li>

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
                    <div class="col-lg-4">
                        <div class="tile yellow dash-demo-tile" style="overflow: hidden;">
                            <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">S. DE COGENERACI&Oacute;N</div> </a>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Lectura Inicial</div>
                                <div id="cogeneracion0" class="circle-tile-number text-faded">

                                <g:formatNumber number="${arregloLecturaInicialFinalCogeneracion[0]}" type="number" maxFractionDigits="3" /> m^3
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Lectura final</div>
                                <div  id="cogeneracion1" class="circle-tile-number text-faded">                                             
                                    <g:formatNumber number="${arregloLecturaInicialFinalCogeneracion[1]}" type="number" maxFractionDigits="3" /> m^3
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>
                    <div class="col-lg-4">
                        <div class="tile orange dash-demo-tile" >
                            <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">S. T&Eacute;RMICO</div> </a>
                            <div class="col-lg-6" >
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Lectura Inicial</div>
                                <div  id="termico0" class="circle-tile-number text-faded">
                                    ${arregloLecturaInicialFinalTermico[0]} m^3
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Lectura final</div>
                                <div id="termico1" class="circle-tile-number text-faded">                                             
                                    <g:formatNumber number="${arregloLecturaInicialFinalTermico[1]}" type="number" maxFractionDigits="3" /> m^3
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>
                    <div class="col-lg-4">
                        <div class="tile blue dash-demo-tile" >
                            <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">COSTO DE COMBUSTIBLES</div> </a>
                            <div class="row"> <div class="col-lg-6" style="height:90px">
                                    <div class="circle-tile-description text-faded" style="padding-top:20px">S. de cogeneraci&oacute;n</div>
                                    <div id='combustible0' class="circle-tile-number text-faded">                                            
                                        <g:formatNumber number="${arregloCostoCombustibles[0]}" type="number" maxFractionDigits="3" /> $
                                    </div>
                                    &nbsp;<br>&nbsp;
                                </div>
                                <div class="col-lg-6" style="height:90px">
                                    <div class="circle-tile-description text-faded" style="padding-top:20px">S. T&eacute;rmico</div>
                                    <div id='combustible1' class="circle-tile-number text-faded">                                             
                                        <g:formatNumber number="${arregloCostoCombustibles[1]}" type="number" maxFractionDigits="3" /> $
                                    </div>
                                    &nbsp;<br>&nbsp;
                                </div></div>
                            <div class="row">
                                <a class="circle-tile-footer"> <div class="text-faded"><h3 style="text-align:center"><strong id='combustible2'>Total: ${arregloCostoCombustibles[2]} $</strong></h3></div> </a>
                            </div>
                        </div>  
                    </div>

                </div><!-- row -->
                <div class="row">

                    <!-- Bar Chart Example -->
                    <div class="col-sm-8">
                        <div class="portlet portlet-purple">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Consumo de combustible por tipo de sistema</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="costoscombustibles-chart-bar"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col-lg-12 -->
                    <div class="col-sm-4">

                        <div class="portlet portlet-green">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Precios Unitarios</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div class="portlet-body">

                                <div class="table-responsive">
                                    <table class="table table-striped" style="margin:0px">
                                        <thead>
                                            <tr>
                                                <th>Sistema</th>
                                                <th>Tipo de combustible</th>
                                                <th>Cargo</th>
                                                <th>Poder calor&iacute;fico</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Cogeneraci&oacute;n</td>
                                                <td id='tipoCombCogeneracion'>${tipoCombustibleCog}</td>
                                                <g:if test="${!arregloPrecioUnitarioCombustibles[0].equals('0')}"> 
                                                    <td> ${arregloPrecioUnitarioCombustibles[0]} </td>
                                                </g:if>
                                                <g:else>
                                                    <td> Utiliza precios página </td>
                                                </g:else>

                                                <td>3521 BTU/m^3</td>
                                            </tr>                                           
                                            <tr>
                                                <td>T&eacute;rmico</td>
                                                <td id='tipoCombTermico'>${tipoCombustibleTermico}</td>
                                                <g:if test="${!arregloPrecioUnitarioCombustibles[1].equals('0')}"> 
                                                    <td> ${arregloPrecioUnitarioCombustibles[1]} </td>
                                                </g:if>
                                                <g:else>
                                                    <td> Utiliza precios página </td>
                                                </g:else>
                                                <td>1365 BTU/m^3</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.row -->
            </div>
        </g:if>
        <g:else>
        </g:else>


        <g:jasperReport type="hidden" jasper="CostosCombustibles" format="PDF" name="PDF" action="createCostosCombustibles" controller="pdfUtil">           
            <input name="sitioIdPDF" id="sitioIdPDF" />
            <input name="fechaIniPDF" id="fechaIniPDF" />
            <input name="fechaFinPDF" id="fechaFinPDF" />

            <input name="lecturaInicial" id="idLecturaInicial" />
            <input name="lecturaFinal" id="idLecturaFinal" />

            <input name="lecturaInicial2" id="idLecturaInicial2" />
            <input name="lecturaFinal2" id="idLecturaFinal2" />

            <input name="sistemaCogeneracion" id="idSistemaCogeneracion" />
            <input name="sistemaTermico" id="idSistemaTermico" />

            <input name="cargoGas" id="idCargoGas" />  
            <input name="cargoDiesel" id="idCargoDiesel" />        

            <input name="calorGas" id="idCalorGas" />
            <input name="calorDiesel" id="idCalorDiesel" />

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
    <script src="${request.contextPath}/Flex/js/demo/costoCombustibles.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'costoscombustibles-chart-bar',
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
