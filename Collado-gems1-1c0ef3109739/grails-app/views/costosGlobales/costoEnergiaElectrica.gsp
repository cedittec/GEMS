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
                        <li class="active" style="padding-top:6px">Energ&iacute;a El&eacute;ctrica</li>

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

                <!-- /.row -->
                <!-- end PAGE TITLE AREA -->
        <g:if test="${numeroSitios > 0}">   
            <div id="contenidoId">
                <div class="row">
                    <div class="col-lg-9">
                        <div class="col-lg-3">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading yellow">
                                        <i class="fa fa-bolt fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content yellow">
                                    <div class="circle-tile-description text-faded">
                                        Energia total
                                    </div>
                                    <div id="arregloEnergiaTotal0" class="circle-tile-number text-faded">                                    
                                    <g:formatNumber number="${arregloEnergiaTotal[0]}" type="number" maxFractionDigits="3" /> kWh
                                    </div>
                                    &nbsp;
                                    <li class="panel" style="list-style-type:none; background-color:#f1c40f">
                                        <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#energia">
                                            <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                        </a>
                                        <ul class="collapse nav text-faded" id="energia" style="text-align:justify; padding:10px">
                                            <li>
                                                Energ&iacute;a base total:
                                            </li>
                                            <li id="arregloEnergiaTotal1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloEnergiaTotal[1]}" type="number" maxFractionDigits="3" /> kWh
                                            </li>
                                            <li>
                                                Energ&iacute;a intermedia total: 
                                            </li>
                                            <li id="arregloEnergiaTotal2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                             
                                                <g:formatNumber number="${arregloEnergiaTotal[2]}" type="number" maxFractionDigits="3" /> kWh
                                            </li>
                                            <li>
                                                Energ&iacute;a punta total:
                                            </li>
                                            <li id="arregloEnergiaTotal3" style="text-align:right"> 
                                                <g:formatNumber number="${arregloEnergiaTotal[3]}" type="number" maxFractionDigits="3" /> kWh
                                            </li>
                                        </ul>
                                    </li>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading green">
                                        <i class="fa fa-credit-card fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content green">
                                    <div class="circle-tile-description text-faded">
                                        Costo energ&iacute;a total CFE
                                    </div>
                                    <div id="arregloEnergiaTotalCFE0" class="circle-tile-number text-faded">

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
                                            <li id="arregloEnergiaTotalCFE1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloEnergiaTotalCFE[1]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo energ&iacute;a  intermedia: 
                                            </li>
                                            <li id="arregloEnergiaTotalCFE2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloEnergiaTotalCFE[2]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo energ&iacute;a  punta:
                                            </li>
                                            <li id="arregloEnergiaTotalCFE3"  style="text-align:right">  <g:formatNumber number="${arregloEnergiaTotalCFE[3]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                        </ul>
                                    </li>

                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading purple">
                                        <i class="fa fa-building-o fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content purple">
                                    <div class="circle-tile-description text-faded">
                                        Costo evitado por el S. de Cogeneraci&oacute;n
                                    </div>
                                    <div id="arregloCostoEvitadoCogeneracion0" class="circle-tile-number text-faded">                                     
                                        <g:formatNumber number="${arregloCostoEvitadoCogeneracion[0]}" type="number" maxFractionDigits="3" /> $
                                    </div>
                                    <li class="panel" style="list-style-type:none; background-color:#8e44ad">
                                        <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoevc">
                                            <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                        </a>
                                        <ul class="collapse nav text-faded" id="costoevc" style="text-align:justify; padding:10px">
                                            <li>
                                                Costo evitado en energ&iacute;a base:
                                            </li>
                                            <li id="arregloCostoEvitadoCogeneracion1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                            
                                                <g:formatNumber number="${arregloCostoEvitadoCogeneracion[1]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en energ&iacute;a  intermedia: 
                                            </li>
                                            <li id="arregloCostoEvitadoCogeneracion2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloCostoEvitadoCogeneracion[2]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en energ&iacute;a  punta:
                                            </li>
                                            <li id="arregloCostoEvitadoCogeneracion3" style="text-align:right"><g:formatNumber number="${arregloCostoEvitadoCogeneracion[3]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                        </ul>
                                    </li>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3">
                            <div class="circle-tile">
                                <a href="#">
                                    <div class="circle-tile-heading orange">
                                        <i class="fa fa-sun-o fa-fw fa-3x"></i>
                                    </div>
                                </a>
                                <div class="circle-tile-content orange">
                                    <div class="circle-tile-description text-faded">
                                        Costo evitado por el S. Fotovoltaico
                                    </div>
                                    <div id="arregloCostoEvitadoFotovoltaico0" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${arregloCostoEvitadoFotovoltaico[0]}" type="number" maxFractionDigits="3" /> $
                                    </div>
                                    <li class="panel" style="list-style-type:none; background-color:#f39c12">
                                        <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoevf">
                                            <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                        </a>
                                        <ul class="collapse nav text-faded" id="costoevf" style="text-align:justify; padding:10px">
                                            <li>
                                                Costo evitado en energ&iacute;a base:
                                            </li>
                                            <li id="arregloCostoEvitadoFotovoltaico1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloCostoEvitadoFotovoltaico[1]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en energ&iacute;a  intermedia: 
                                            </li>
                                            <li id="arregloCostoEvitadoFotovoltaico2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                                <g:formatNumber number="${arregloCostoEvitadoFotovoltaico[2]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                            <li>
                                                Costo evitado en energ&iacute;a  punta:
                                            </li>
                                            <li id="arregloCostoEvitadoFotovoltaico3" style="text-align:right"> <g:formatNumber number="${arregloCostoEvitadoFotovoltaico[3]}" type="number" maxFractionDigits="3" /> $
                                            </li>
                                        </ul>
                                    </li>
                                </div>
                            </div>
                        </div>

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
                                        <div id="costoselectricos-chart-bar"></div>
                                    </div>
                                </div>
                            </div>
                        </div><!--col12-->
                    </div><!--col9-->
                    <div class="col-md-3">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading green" >                                        
                                    <img src="${request.contextPath}/Flex/img/CFElogo.png" width="100%" style="padding-top:10px">
                                </div>
                            </a>
                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Tipo de tarifa
                                </div>
                                <div id="tipoTarifa" class="circle-tile-number text-faded">
                                    ${tipoTarifa}
                                </div>
                                &nbsp;
                            </div>
                        </div><!--circleTile tipoTarifa-->



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
                                                <th>Tipo de energ&iacute;a</th>
                                                <th>Cargo por kW/h</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Base</td>
                                                <td id="basePrecio">$1.1179</td>
                                            </tr>
                                            <tr>
                                                <td>Intermedia</td>
                                                <td id="intermediaPrecio">$1.3370</td>
                                            </tr>
                                            <tr>
                                                <td>Punta</td>
                                                <td id="puntaPrecio">$2.1932</td>
                                            </tr>
                                            <tr>
                                                <td>Demanda Facturable</td>
                                                <td id="demandaPrecio">$2.1932</td>
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


        <g:jasperReport type="hidden" jasper="CostosElectricos" format="PDF" name="PDF" action="createCostosElectricos" controller="pdfUtil">                 
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="energiaBase" id="idenergiaBase" /> 
            <input hidden name="energiaIntermedia" id="idenergiaIntermedia">
            <input hidden name="energiaPunta" id="idenergiaPunta" />
            <input hidden name="energiaBase2" id="idenergiaBase2" />
            <input hidden name="energiaIntermedia2" id="idenergiaIntermedia2" />
            <input hidden name="energiaPunta2" id="idenergiaPunta2" />

            <input hidden name="tarifaCFE" id="idtarifaCFE" />      
            <input hidden  name="precioBase" id="idprecioBase"/>
            <input hidden name="precioIntermedia" id="idprecioIntermedia"/>
            <input hidden name="precioPunta" id="idprecioPunta"/>
            <input hidden name="precioDemanda" id="idprecioDemanda" />  
            <input hidden name="costoCFE" id="idcostoCFE" />

            <input hidden name="costoCogeneracion" id="idcostoCogeneracion" />
            <input hidden name="energiaBase3" id="idenergiaBase3" />
            <input hidden name="energiaIntermedia3" id="idenergiaIntermedia3" />
            <input hidden name="energiaPunta3" id="idenergiaPunta3" />

            <input hidden name="costoFotovoltaico" id="idcostoFotovoltaico" />          
            <input hidden name="energiaBase4" id="idenergiaBase4" />
            <input hidden name="energiaIntermedia4" id="idenergiaIntermedia4" />
            <input hidden name="energiaPunta4" id="idenergiaPunta4" />




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

    <script src="${request.contextPath}/Flex/js/demo/costoEnergiaElectrica.js"></script>
    <g:javascript>        
        Morris.Bar({
        element: 'costoselectricos-chart-bar',
        data: [
        { y:  '${arregloGraficaMeses[0]}', e: ${arregloGraficaValores[0]},  t: ${arregloGraficaValores2[0]}, c: ${arregloGraficaValores3[0]}, a: ${arregloGraficaValores4[0]} } ,
        { y:  '${arregloGraficaMeses[1]}', e: ${arregloGraficaValores[1]},  t: ${arregloGraficaValores2[1]}, c: ${arregloGraficaValores3[1]}, a: ${arregloGraficaValores4[1]} },
        { y:  '${arregloGraficaMeses[2]}', e: ${arregloGraficaValores[2]},  t: ${arregloGraficaValores2[2]}, c: ${arregloGraficaValores3[2]}, a: ${arregloGraficaValores4[2]} },
        { y:  '${arregloGraficaMeses[3]}', e: ${arregloGraficaValores[3]},  t: ${arregloGraficaValores2[3]}, c: ${arregloGraficaValores3[3]}, a: ${arregloGraficaValores4[3]} },
        { y:  '${arregloGraficaMeses[4]}', e: ${arregloGraficaValores[4]},  t: ${arregloGraficaValores2[4]}, c: ${arregloGraficaValores3[4]}, a: ${arregloGraficaValores4[4]} },
        { y:  '${arregloGraficaMeses[5]}', e: ${arregloGraficaValores[5]},  t: ${arregloGraficaValores2[5]}, c: ${arregloGraficaValores3[5]}, a: ${arregloGraficaValores4[5]} },
        { y:  '${arregloGraficaMeses[6]}', e: ${arregloGraficaValores[6]}, t: ${arregloGraficaValores2[6]}, c: ${arregloGraficaValores3[6]}, a: ${arregloGraficaValores4[6]} },
        { y:  '${arregloGraficaMeses[7]}', e: ${arregloGraficaValores[7]}, t: ${arregloGraficaValores2[7]}, c: ${arregloGraficaValores3[7]}, a: ${arregloGraficaValores4[7]} },
        { y:  '${arregloGraficaMeses[8]}', e: ${arregloGraficaValores[8]}, t: ${arregloGraficaValores2[8]}, c: ${arregloGraficaValores3[8]}, a: ${arregloGraficaValores4[8]} },
        { y:  '${arregloGraficaMeses[9]}', e: ${arregloGraficaValores[9]}, t: ${arregloGraficaValores2[9]}, c: ${arregloGraficaValores3[9]}, a: ${arregloGraficaValores4[9]} },
        { y:  '${arregloGraficaMeses[10]}', e: ${arregloGraficaValores[10]}, t: ${arregloGraficaValores2[10]}, c: ${arregloGraficaValores3[10]}, a: ${arregloGraficaValores4[10]} },
        { y:  '${arregloGraficaMeses[11]}', e: ${arregloGraficaValores[11]}, t: ${arregloGraficaValores2[11]}, c: ${arregloGraficaValores3[11]}, a: ${arregloGraficaValores4[11]} }

        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c','a'],
        labels: ['Energia total','Energía base', 'Energía intermedia', 'Energía punta'],
        barColors: ['#f39c12','#e74c3c', '#16a085','#f39c12'],
        resize: true
        });
    </g:javascript>
</body>
</html>
