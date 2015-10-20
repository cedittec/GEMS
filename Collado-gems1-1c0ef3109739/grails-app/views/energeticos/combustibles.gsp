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
        <title>Energ&iacute;a Electrica</title>
    </head>
    <body>
        <div class="page-content">

           <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Energ&eacute;ticos 
                            <small>Combustibles</small>
                        </h1>
                      
                    </div>
                </div>
                <!-- /.col-lg-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                        <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'energeticos', action:'index')}"> Energeticos</a></li>
                            <li class="active" style="padding-top:6px"> Combustibles</li>

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
                    <div class="col-sm-6">
                        <div class="tile yellow dash-demo-tile">
                            <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">S. DE COGENERACI&Oacute;N</div> </a>
                            <div class="col-lg-6" >
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Consumo de Combustible ${tipoCombustibleCog}</div>
                                <div id="ConsumoGasLP" class="circle-tile-number text-faded">

                                <g:formatNumber number="${combustibleCogeneracionTotal[0]}" type="number" maxFractionDigits="3" /> 
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Energ&iacute;a consumida</div>
                                <div id="energiaConsumida" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${combustibleCogeneracionTotal[1]}" type="number" maxFractionDigits="3" /> kWt
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>
                    <div class="col-sm-6">
                        <div class="tile orange dash-demo-tile">
                            <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">S. T&Eacute;RMICO</div> </a>
                            <div class="col-lg-6" >
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Consumo de Combustible ${tipoCombustibleCog}</div>
                                <div  id="ConsumoDiesel" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${combustibleTermicoTotal[0]}" type="number" maxFractionDigits="3" /> 
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:35px">Energ&iacute;a consumida</div>
                                <div id="EnergiaConsumida2" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${combustibleTermicoTotal[1]}" type="number" maxFractionDigits="3" /> kWt
                                </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>               



                </div><!--row-->

                <div class="row">
                    <div class="col-lg-6">
                        <div class="tile blue" >
                            <h4>
                                <i class="fa fa-usd"></i> Consumo de combustible  
                            </h4>
                            <div id="combustibleGrafica" style="height: 230px"></div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="tile green" >
                            <h4>
                                <i class="fa fa-bolt"></i> Consumo de energ&iacute;a  
                            </h4>
                            <div id="energiaGrafica" style="height: 230px"></div>
                        </div>
                    </div>
                </div><!--row-->


            </div>
        </g:if>
        <g:else>
        </g:else>

        <g:jasperReport type="hidden" jasper="EnergeticosCombustibles" format="PDF" name="PDF" action="createEnergeticosCombustibles" controller="pdfUtil">           
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="termico" id="idtermico" />
            <input hidden name="cogeneracion" id="idcogeneracion" />
            <input hidden name="termico2" id="idtermico2" />
            <input hidden name="cogeneracion2" id="idcogeneracion2" />       

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
    <script src="${request.contextPath}/Flex/js/demo/energeticosCombustibles.js"></script>
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
        element: 'combustibleGrafica',
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
        labels: ['Combustible Cog.', 'Combustible Termico'],
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
        productA: ${arregloGraficaValores3[0]},
        productB: ${arregloGraficaValores4[0]},
        },
        {
        date: '${arregloGraficaMeses[1]}',
        productA: ${arregloGraficaValores3[1]},
        productB:  ${arregloGraficaValores4[1]},
        },
        {
        date: '${arregloGraficaMeses[2]}',
        productA: ${arregloGraficaValores3[2]},
        productB:  ${arregloGraficaValores4[2]},
        },
        {
        date: '${arregloGraficaMeses[3]}',
        productA: ${arregloGraficaValores3[3]},
        productB:  ${arregloGraficaValores4[3]},
        },
        {
        date: '${arregloGraficaMeses[4]}',
        productA: ${arregloGraficaValores3[4]},
        productB:  ${arregloGraficaValores4[4]},
        },
        {
        date: '${arregloGraficaMeses[5]}',
        productA: ${arregloGraficaValores3[5]},
        productB:  ${arregloGraficaValores4[5]},
        },
        {
        date: '${arregloGraficaMeses[6]}',
        productA: ${arregloGraficaValores3[6]},
        productB:  ${arregloGraficaValores4[6]},
        },
        {
        date: '${arregloGraficaMeses[7]}',
        productA: ${arregloGraficaValores3[7]},
        productB:  ${arregloGraficaValores4[7]},
        },
        {
        date: '${arregloGraficaMeses[8]}',
        productA: ${arregloGraficaValores3[8]},
        productB:  ${arregloGraficaValores4[8]},
        },
        {
        date: '${arregloGraficaMeses[9]}',
        productA: ${arregloGraficaValores3[9]},
        productB:  ${arregloGraficaValores4[9]},
        },
        {
        date: '${arregloGraficaMeses[10]}',
        productA: ${arregloGraficaValores3[10]},
        productB:  ${arregloGraficaValores4[10]},
        },
        {
        date: '${arregloGraficaMeses[11]}',
        productA: ${arregloGraficaValores3[11]},
        productB:  ${arregloGraficaValores4[11]},
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
        labels: ['Energia Cog.', 'Energia Termico'],
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
