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

                <!-- begin DASHBOARD CIRCLE TILES -->
            <g:if test="${numeroSitios > 0}">
                 <!-- <g:jasperReport jasper="EstadoFinancieroIndex" format="PDF" name="PDF" action="createEstadoFinancieroIndex" controller="pdfUtil" />-->
                          

                <div id="contenidoId">
                    <div class="row">
                        <div class="col-sm-4 col-sm-6">
                            <div class="circle-tile">
                              
                                    <div class="circle-tile-heading red">
                                        <i class="fa fa-credit-card fa-fw fa-3x"></i>
                                    </div>
                              
                                <div class="circle-tile-content red">
                                    <div class="circle-tile-description text-faded">
                                        Costos globales
                                    </div>
                                    <div id="costosGlobalesId" class="circle-tile-number text-faded">
                                         
                                    $ <g:formatNumber number="${resultadoCostos}"/> 
                                    </div>

                                    <a href="${createLink(controller:'CostosGlobales', action:'index')}" class="circle-tile-footer">
                                        <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                                    </a>

                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4 col-sm-6">
                            <div class="circle-tile">
                              
                                    <div class="circle-tile-heading green">
                                        <i class="fa fa-money fa-fw fa-3x"></i>
                                    </div>
                               
                                <div class="circle-tile-content green">
                                    <div class="circle-tile-description text-faded">
                                        Ahorros globales
                                    </div>
                                    <div id="ahorrosGlobalesId" class="circle-tile-number text-faded">
                                        
                                        $ <g:formatNumber number="${resultadoAhorros}"/> 
                                    </div>
                                    <a href="${createLink(controller:'AhorrosGlobales', action:'index')}" class="circle-tile-footer">
                                        <i class="fa fa-chevron-circle-right"></i> M&aacute;s informaci&oacute;n 
                                    </a>
                                </div>
                            </div>
                        </div>
                         <sec:ifNotGranted roles="ROLE_adminVentas">   
                        <div class="col-sm-4 col-sm-6">
                            <div class="circle-tile">
                               
                                    <div class="circle-tile-heading orange">
                                        <i class="fa fa-chevron-circle-up fa-fw fa-3x"></i>
                                    </div>
                               
                             
                                <div class="circle-tile-content orange">
                                    <div class="circle-tile-description text-faded">
                                        Flujo de efectivo acumulado
                                    </div>
                                    <div id="flujoEfectivoAcumuladoId" class="circle-tile-number text-faded">
                                        
                                       $ <g:formatNumber number="${resultadoFlujoEfectivo}" /> 
                                    </div>
                                    <a href="ROI.html" class="circle-tile-footer">M&aacute;s informaci&oacute;n <i class="fa fa-chevron-circle-right"></i></a>
                                </div>
                            
                            </div>
                        </div>
                         </sec:ifNotGranted>
                    </div>
                     <!-- end DASHBOARD CIRCLE TILES -->

                    <div class="row">

                        <div class="col-sm-3 col-sm-6">
                            <div class="portlet portlet-orange" >
                                <div class="portlet-heading">
                                    <div class="portlet-title">
                                        <h4>Desglose de costos por energ&iacute;a</h4>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div id="donutChart" >
                                    <div class="portlet-body">
                                        <div id="morris-chart-donut" style="height:250px"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-9">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="tile green" style="height: 320px">
                                        <h4><i class="fa fa-usd"></i> Desglose de ahorros
                                        </h4>
                                        <p class="small text-faded">
                                            De:
                                            <strong>${arregloGraficaMeses[0]} a </strong>                                    
                                            <strong>${arregloGraficaMeses[11]}</strong>
                                        </p>
                                        <div id="morris-chart-dashboard"></div>
                                    </div>
                                </div>


                            </div>
                        </div>

                    </div>
                </div>
              <!-- /.row -->
            </g:if>
            <g:else>

            </g:else>

                 <g:jasperReport type="hidden" jasper="EstadoFinancieroIndex" format="PDF" name="PDF" action="createEstadoFinancieroIndex" controller="pdfUtil">                 
                        <input hidden name="sitioIdPDF" id="sitioIdPDF" />
                        <input hidden name="fechaIniPDF" id="fechaIniPDF" />
                        <input hidden name="fechaFinPDF" id="fechaFinPDF" />
                        
                        <input hidden name="costoGlobal" id="costoGlobal" />
                        <input hidden name="ahorroGlobal" id="ahorroGlobal" />
                        <input hidden name="efectivoAcumulado" id="efectivoAcumulado" />
                        
                </g:jasperReport>    

        </div>
<!-- Morris Charts -->
        <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
        <!-- Moment.js -->
        <script src="${request.contextPath}/Flex/js/demo/estadoFinancieroIndex.js"></script>

        <script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>                   
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
            element: 'morris-chart-dashboard',
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
            labels: ['Evitado Energias Alternas', 'Evitado energia Termica'],
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
