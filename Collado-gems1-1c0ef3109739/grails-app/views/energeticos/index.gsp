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
        <title>Energ&eacute;ticos</title>
    </head>
</head>
<body>
    <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
        <div class="row">
            <div class="col-lg-12">
                <div class="page-title">
                    <h1>Energ&eacute;ticos
                        <small>Vista general</small>
                    </h1>                  
                </div>
            </div>
            <!-- /.col-lg-12 -->
            <div class = "col-sm-12">
                <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                    <li class="active" style="padding-top:6px"><i class="fa fa-dashboard"></i> Energ&eacute;ticos</li>

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

                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading orange">
                            <i class="fa fa-fire fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content orange">
                            <div class="circle-tile-description text-faded">
                                Energ&iacute;a t&eacute;rmica generada
                            </div>
                            <div id="energiaTermicaGeneradaVal" class="circle-tile-number text-faded">

                            <g:formatNumber number="${energiaTermicaGeneradaVal}" type="number" maxFractionDigits="3" /> kWt
                            </div>

                            <a href="${createLink(controller:'Energeticos', action:'energiaTermica')}" class="circle-tile-footer">
                                <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading yellow">
                            <i class="fa fa-bolt fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content yellow">
                            <div class="circle-tile-description text-faded">
                                Energ&iacute;a el&eacute;ctrica generada
                            </div>
                            <div id="energiaElectricaGeneradaVal" class="circle-tile-number text-faded">

                                <g:formatNumber number="${energiaElectricaGeneradaVal}" type="number" maxFractionDigits="3" /> kW
                            </div>
                            <a href="${createLink(controller:'Energeticos', action:'energiaElectrica')}" class="circle-tile-footer">
                                <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading blue">
                            <i class="fa fa-fire-extinguisher fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content blue">
                            <div class="circle-tile-description text-faded">
                                Consumo de combustible
                            </div>
                            <div id="consumoCombustibleVal" class="circle-tile-number text-faded">

                                <g:formatNumber number="${consumoCombustibleVal}" type="number" maxFractionDigits="3" /> BTU
                            </div>
                            <a href="${createLink(controller:'Energeticos', action:'combustibles')}" class="circle-tile-footer">
                                <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3">
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
                            <div id="radiacionSolarVal" class="circle-tile-number text-faded">

                                <g:formatNumber number="${radiacionSolarVal}" type="number" maxFractionDigits="3" /> W/m^2
                            </div>
                            &nbsp;
                        </div>
                    </div>
                    <div class="circle-tile">

                        <div class="circle-tile-heading blue">
                            <i class="fa fa-cloud fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content blue">
                            <div class="circle-tile-description text-faded">
                                N&iacute;veles de humedad
                            </div>
                            <div id="nivelesHumedadVal" class="circle-tile-number text-faded">

                                <g:formatNumber number="${nivelesHumedadVal}" type="number" maxFractionDigits="3" /> %
                            </div>
                            &nbsp;
                        </div>
                    </div>
                    <div class="circle-tile">

                        <div class="circle-tile-heading purple">
                            <i class="fa fa-coffee fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content purple">
                            <div class="circle-tile-description text-faded">
                                Temperatura
                            </div>
                            <div id="temperaturaVal" class="circle-tile-number text-faded">

                                <g:formatNumber number="${temperaturaVal}" type="number" maxFractionDigits="3" /> C
                            </div>
                            &nbsp;
                        </div>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="tile green" >
                        <h4>
                            <i class="fa fa-bolt"></i> Energ&iacute;a generada  
                        </h4>
                        <div id="energiaGrafica" style="height: 230px"></div>
                    </div>
                    <div class="tile blue" >
                        <h4><i class="fa fa-usd"></i> Consumo de combustible  
                        </h4>

                        <div id="combustibleGrafica" style="height: 230px"></div>
                    </div>
                </div>
            </div>

        </div>
    </g:if>
    <g:else>
    </g:else>

    <g:jasperReport type="hidden" jasper="EnergeticosIndex" format="PDF" name="PDF" action="createEnergeticosIndex" controller="pdfUtil">           
        <input hidden name="sitioIdPDF" id="sitioIdPDF" />
        <input hidden name="fechaIniPDF" id="fechaIniPDF" />
        <input hidden name="fechaFinPDF" id="fechaFinPDF" />

        <input hidden name="radiacion" id="idradiacion" />
        <input hidden name="humedad" id="idhumedad" />
        <input hidden name="temperatura" id="idtemperatura" />
        <input hidden name="termica" id="idtermica" />
        <input hidden name="electrica" id="idelectrica" />
    </g:jasperReport> 

</div>
<!-- /.page-content -->
<script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
<script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
<!-- Flot Charts  -->
<script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.js"></script>
<script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.resize.js"></script>
<!-- Sparkline Charts -->
<script src="${request.contextPath}/Flex/js/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- Moment.js -->
<script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script> 
<script src="${request.contextPath}/Flex/js/demo/energeticos.js"></script>

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
    element: 'energiaGrafica',
    data: sales_data,
    xkey: 'date',
    xLabelFormat: function(date) {
    return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB'],
    yLabelFormat: function(y) {
    return y;
    },
    labels: ['Energia Termica', 'Energia Electrica'],
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

<g:javascript>       

    //Morris Area Chart  
    var sales_data = [{
    date: '${arregloGraficaMeses[0]}',
    productA: ${arregloGraficaValores3[0]}
    },
    {
    date: '${arregloGraficaMeses[1]}',
    productA: ${arregloGraficaValores3[1]}
    },
    {
    date: '${arregloGraficaMeses[2]}',
    productA: ${arregloGraficaValores3[2]}
    },
    {
    date: '${arregloGraficaMeses[3]}',
    productA: ${arregloGraficaValores3[3]}
    },
    {
    date: '${arregloGraficaMeses[4]}',
    productA: ${arregloGraficaValores3[4]}
    },
    {
    date: '${arregloGraficaMeses[5]}',
    productA: ${arregloGraficaValores3[5]}
    },
    {
    date: '${arregloGraficaMeses[6]}',
    productA: ${arregloGraficaValores3[6]}
    },
    {
    date: '${arregloGraficaMeses[7]}',
    productA: ${arregloGraficaValores3[7]}
    },
    {
    date: '${arregloGraficaMeses[8]}',
    productA: ${arregloGraficaValores3[8]}
    },
    {
    date: '${arregloGraficaMeses[9]}',
    productA: ${arregloGraficaValores3[9]}
    },
    {
    date: '${arregloGraficaMeses[10]}',
    productA: ${arregloGraficaValores3[10]}
    },
    {
    date: '${arregloGraficaMeses[11]}',
    productA: ${arregloGraficaValores3[11]}
    }];

    Morris.Area({
    element: 'combustibleGrafica',
    data: sales_data,
    xkey: 'date',
    xLabelFormat: function(date) {
    return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA'],
    yLabelFormat: function(y) {
    return y;
    },
    labels: ['Energia Cog.'],
    lineColors: ['#fff', '#fff'],
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
