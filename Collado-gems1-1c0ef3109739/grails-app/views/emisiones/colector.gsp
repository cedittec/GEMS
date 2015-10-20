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
        <title>Emisi&oacute;n Colector Solar</title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Emisiones
                            <small>Vista general</small>
                        </h1>
                    
                    </div>
                </div>
                <!-- /.col-lg-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                         <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'emisiones', action:'index')}"> Emisiones</a></li>
                            <li class="active" style="padding-top:6px"> Sistema Colector Solar</li>

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
                    <div class="col-md-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading green">                  
                                    <img src="${request.contextPath}/Flex/img/CFElogo.png" width="100%" style="padding-top:10px">
                                </div>
                            </a>
                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Emisiones CFE
                                </div>
                                <div id="emisionesCFE" class="circle-tile-number text-faded">

                                <g:formatNumber number="${emisionCFE}" type="number" maxFractionDigits="3" /> lbs CO2
                                </div>
                                &nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading orange">
                                    <i class="fa fa-bolt fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Emisiones Colector Solar
                                </div>
                                <div id="emisionesCSolar" class="circle-tile-number text-faded">                                 
                                    <g:formatNumber number="${emisionColector}" type="number" maxFractionDigits="3" /> lbs CO2
                                </div>
                                &nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="circle-tile">
                            <a href="#">
                                <div class="circle-tile-heading yellow">
                                    <i class="fa fa-sun-o fa-fw fa-3x"></i>
                                </div>
                            </a>
                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Reducci&oacute;n de emisiones
                                </div>
                                <div id="reduccionEmisiones" class="circle-tile-number text-faded">                                 
                                    <g:formatNumber number="${reduccionEmisionesAmbiente}" type="number" maxFractionDigits="3" /> lbs CO2
                                </div>
                                &nbsp;
                            </div>
                        </div>
                    </div>

                </div>
                <!-- end DASHBOARD CIRCLE TILES -->

                <div class="row">



                    <div class="col-sm-12">
                        <div class="tile blue" >
                            <h4>
                                <i class="fa fa-filter"></i> Hist&oacute;rico: Desglose de reducciones 
                            </h4>
                            <div id="emisionesMorrisArea" style="height: 270px"></div>
                        </div>


                    </div>
                    <!-- /.row -->


                </div>
                <!-- /.page-content -->
            </div>
        </g:if>
        <g:else>
        </g:else>


        <g:jasperReport type="hidden" jasper="EmisionesColectorSolar" format="PDF" name="PDF" action="createEmisionesColectorSolar" controller="pdfUtil">           
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="emisionesCFE" id="idemisionesCFE"/>        
            <input hidden name="emisionesColector" id="idemisionesPanel" />
            <input hidden name="reduccionEmisiones" id="idreduccionEmisiones" />        

        </g:jasperReport> 


    </div>
    <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
    <!-- Flot Charts  -->
    <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.js"></script>
    <script src="${request.contextPath}/Flex/js/plugins/flot/jquery.flot.resize.js"></script>
    <!-- Sparkline Charts -->
    <script src="${request.contextPath}/Flex/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- Moment.js -->
    <script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>  
    <script src="${request.contextPath}/Flex/js/demo/emisionesColectorSolar.js"></script>

        <g:javascript>       

            //Morris Area Chart  
            var sales_data = [{
        date: '${arregloGraficaMeses[0]}',
        productA: ${arregloGraficaValores[0]}
        },
        {
        date: '${arregloGraficaMeses[1]}',
        productA: ${arregloGraficaValores[1]}
        },
        {
        date: '${arregloGraficaMeses[2]}',
        productA: ${arregloGraficaValores[2]}
        },
        {
        date: '${arregloGraficaMeses[3]}',
        productA: ${arregloGraficaValores[3]}
        },
        {
        date: '${arregloGraficaMeses[4]}',
        productA: ${arregloGraficaValores[4]}
        },
        {
        date: '${arregloGraficaMeses[5]}',
        productA: ${arregloGraficaValores[5]}
        },
        {
        date: '${arregloGraficaMeses[6]}',
        productA: ${arregloGraficaValores[6]}
        },
        {
        date: '${arregloGraficaMeses[7]}',
        productA: ${arregloGraficaValores[7]}
        },
        {
        date: '${arregloGraficaMeses[8]}',
        productA: ${arregloGraficaValores[8]}
        },
        {
        date: '${arregloGraficaMeses[9]}',
        productA: ${arregloGraficaValores[9]}
        },
        {
        date: '${arregloGraficaMeses[10]}',
        productA: ${arregloGraficaValores[10]}
        },
        {
        date: '${arregloGraficaMeses[11]}',
        productA: ${arregloGraficaValores[11]}
        }];

        Morris.Area({
        element: 'emisionesMorrisArea',
        data: sales_data,
        xkey: 'date',
        xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
        },
        xLabels: 'day',
        ykeys: ['productA'],
        yLabelFormat: function(y) {
        return  y +"lbs CO2";
        },
        labels: ['Colector Solar'],
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
