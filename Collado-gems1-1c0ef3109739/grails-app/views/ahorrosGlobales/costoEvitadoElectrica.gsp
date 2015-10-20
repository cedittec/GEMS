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
                        <li style="padding-top:6px"> <a href="${createLink(controller:'AhorrosGlobales', action:'index')}">Ahorros globales</a></li>
                        <li class="active" style="padding-top:6px"> Energ&iacute;a el&eacute;ctrica</li>
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
           <!-- begin DASHBOARD CIRCLE TILES -->
                <div class="row">
                    <div class="col-sm-3">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading blue">
                                    <i class="fa fa-building-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Costo evitado por el S. de Cogeneraci&oacute;n
                                </div>
                                <div id="evitadoCogeneracion0" class="circle-tile-number text-faded">
                                <g:formatNumber number="${costoEvitadoCogeneracion[0]}" type="number" maxFractionDigits="3" /> $                                                            
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#2980b9">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoevc">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="costoevc" style="text-align:justify; padding:10px">
                                        <li>
                                            Costo evitado en energ&iacute;a base:
                                        </li>
                                        <li id="evitadoCogeneracion1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                             
                                            <g:formatNumber number="${costoEvitadoCogeneracion[1]}" type="number" maxFractionDigits="3" /> $       
                                        </li>
                                        <li>
                                            Costo evitado en energ&iacute;a  intermedia: 
                                        </li>
                                        <li id="evitadoCogeneracion2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                             
                                            <g:formatNumber number="${costoEvitadoCogeneracion[2]}" type="number" maxFractionDigits="3" /> $  
                                        </li>
                                        <li>
                                            Costo evitado en energ&iacute;a  punta:
                                        </li>
                                        <li id="evitadoCogeneracion3" style="text-align:right">  
                                            <g:formatNumber number="${costoEvitadoCogeneracion[3]}" type="number" maxFractionDigits="3" /> $                                                   
                                        </li>
                                    </ul>
                                </li>
                            </div>
                        </div>

                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading yellow">
                                    <i class="fa fa-sun-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Costo evitado por el <br>S. Fotovoltaico
                                </div>
                                <div id="evitadoFotovoltaico0" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${costoEvitadoFotovoltaico[0]}" type="number" maxFractionDigits="3" /> $ 
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#f1c40f">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoevf">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="costoevf" style="text-align:justify; padding:10px">
                                        <li>
                                            Costo evitado en energ&iacute;a base:
                                        </li>
                                        <li id="evitadoFotovoltaico1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">
                                            <g:formatNumber number="${costoEvitadoFotovoltaico[1]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                        <li>
                                            Costo evitado en energ&iacute;a  intermedia: 
                                        </li>
                                        <li id="evitadoFotovoltaico2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                 
                                            <g:formatNumber number="${costoEvitadoFotovoltaico[2]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                        <li>
                                            Costo evitado en energ&iacute;a  punta:
                                        </li>
                                        <li id="evitadoFotovoltaico3" style="text-align:right">
                                            <g:formatNumber number="${costoEvitadoFotovoltaico[3]}" type="number" maxFractionDigits="3" /> $
                                        </li>
                                    </ul>
                                </li>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-9">
                        <div class="tile green">
                            <h4>
                                <i class="fa fa-filter"></i> Hist&oacute;rico de ahorros
                            </h4>
                            <div id="ahorrosMorrisArea" style="height: 380px"></div>
                        </div>
                    </div>


                </div>
                <!-- end DASHBOARD CIRCLE TILES -->
            </div>
        </g:if>
        <g:else>
        </g:else>


        <g:jasperReport type="hidden" jasper="AhorrosElectricos" format="PDF" name="PDF" action="createAhorrosElectricos" controller="pdfUtil">           
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="sistemaCogneracion" id="idsistemaCogneracion" />
            <input hidden name="energiaBase" id="idenergiaBase" />

            <input hidden name="energiaIntermedia" id="idenergiaIntermedia" />
            <input hidden name="energiaPunta" id="idenergiaPunta" />

            <input hidden name="sistemaFotovoltaico" id="idsistemaFotovoltaico" />
            <input hidden name="energiaBase2" id="idenergiaBase2" />

            <input hidden name="energiaIntermedia2" id="idenergiaIntermedia2" />
            <input hidden name="energiaPunta2" id="idenergiaPunta2" />

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
    <script src="${request.contextPath}/Flex/js/demo/ahorrosEvitadoElectrica.js"></script>

    <g:javascript>       

        //Morris Area Chart  
        var sales_data = [{
        date: '${arregloGraficaMeses[0]}',
        productA: ${arregloGraficaValores[0]},
        productB: ${arregloGraficaValores2[0]},
        },
        {
        date: '${arregloGraficaMeses[1]}',
        productA: ${arregloGraficaValores[1]},
        productB:  ${arregloGraficaValores2[1]},
        },
        {
        date: '${arregloGraficaMeses[2]}',
        productA: ${arregloGraficaValores[2]},
        productB:  ${arregloGraficaValores2[2]},
        },
        {
        date: '${arregloGraficaMeses[3]}',
        productA: ${arregloGraficaValores[3]},
        productB:  ${arregloGraficaValores2[3]},
        },
        {
        date: '${arregloGraficaMeses[4]}',
        productA: ${arregloGraficaValores[4]},
        productB:  ${arregloGraficaValores2[4]},
        },
        {
        date: '${arregloGraficaMeses[5]}',
        productA: ${arregloGraficaValores[5]},
        productB:  ${arregloGraficaValores2[5]},
        },
        {
        date: '${arregloGraficaMeses[6]}',
        productA: ${arregloGraficaValores[6]},
        productB:  ${arregloGraficaValores2[6]},
        },
        {
        date: '${arregloGraficaMeses[7]}',
        productA: ${arregloGraficaValores[7]},
        productB:  ${arregloGraficaValores2[7]},
        },
        {
        date: '${arregloGraficaMeses[8]}',
        productA: ${arregloGraficaValores[8]},
        productB:  ${arregloGraficaValores2[8]},
        },
        {
        date: '${arregloGraficaMeses[9]}',
        productA: ${arregloGraficaValores[9]},
        productB:  ${arregloGraficaValores2[9]},
        },
        {
        date: '${arregloGraficaMeses[10]}',
        productA: ${arregloGraficaValores[10]},
        productB:  ${arregloGraficaValores2[10]},
        },
        {
        date: '${arregloGraficaMeses[11]}',
        productA: ${arregloGraficaValores[11]},
        productB:  ${arregloGraficaValores2[11]},
        }];

        Morris.Area({
        element: 'ahorrosMorrisArea',
        data: sales_data,
        xkey: 'date',
        xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
        },
        xLabels: 'day',
        ykeys: ['productA', 'productB'],
        yLabelFormat: function(y) {
        return "$" + y;
        },
        labels: ['Evitado energia total Cog.', 'Evitado en base energia total fotovoltaico'],
        lineColors: ['#fff', '#fff', '#fff'],
        hideHover: 'auto',
        resize: true,
        gridTextFamily: ['Open Sans'],
        gridTextColor: ['rgba(255,255,255,0.7)'],
        fillOpacity: 0.1,
        pointSize: 0,
        smooth: true,
        lineWidth: 2,
        grid: true,
        dateFormat: function(date) {
        d = new Date(date);
        return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
        },
        });



    </g:javascript>

</body>
</html>
