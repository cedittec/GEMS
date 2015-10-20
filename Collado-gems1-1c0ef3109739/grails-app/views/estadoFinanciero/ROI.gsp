<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->
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
                                    <small>Desglose del ROI</small>
                                </h1>
                                
                            </div>
                        </div>
                        <!-- /.col-sm-12 -->
                         
                        <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                            <li class="active" style="padding-top:6px"><i class="fa fa-dashboard"></i> Estado Financiero</li>
                           
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
                                    
                                        <div class="circle-tile-heading blue">
                                            <i class="fa fa-money fa-fw fa-3x"></i>
                                        </div>
                                    
                                    <div class="circle-tile-content blue">
                                        <div class="circle-tile-description text-faded">
                                            Costo de combustible del S. de Cogeneraci&oacute;n
                                        </div>
                                        <div id="costoCombustibleCogeneracion" class="circle-tile-number text-faded">
                                          
                                        <g:formatNumber number="${costoCombustibleROI}" type="number" maxFractionDigits="3" /> $
                                        </div>
                                        &nbsp;
                                    </div>
                                </div>


                            </div>
                            <div class="col-sm-9">
                                <div class="row">
                                     <div class="col-sm-9">
                                <div class="tile green">
                                    <h4>
                                        <i class="fa fa-filter"></i> Flujo de efectivo acumulado 
                                    </h4>
                                     <div id="ROIMorrisArea" style="height: 38%"></div>
                                </div>
                                 </div>
                            </div>
                            </div>
                            
                            


                        </div>
                        <!-- end DASHBOARD CIRCLE TILES -->
                    </div>
             </g:if>       
            <g:jasperReport type="hidden" jasper="FlujoDeEfectivo" format="PDF" name="PDF" action="createFlujoDeEfectivo" controller="pdfUtil">           
                  <input hidden name="sitioIdPDF" id="sitioIdPDF" />
                  <input hidden name="fechaIniPDF" id="fechaIniPDF" />
                  <input hidden name="fechaFinPDF" id="fechaFinPDF" />

                  <input hidden name="sistemaCogeneracion" id="idsistemaCogneracion" />

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
    
        
    <script src="${request.contextPath}/Flex/js/demo/ROI.js"></script> 
    
   
        
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
        element: 'ROIMorrisArea',
        data: sales_data,
        xkey: 'date',
        xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
        },
        xLabels: 'day',
        ykeys: ['productA'],
        yLabelFormat: function(y) {
        return "$" +  y ;
        },
        labels: ['Sistema de Cog.'],
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
