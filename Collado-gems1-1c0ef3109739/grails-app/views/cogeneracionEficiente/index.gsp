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
        <title>Cogeneraci&oacute;n Eficiente</title>
    </head>
<body>
    <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
        <div class="row">
            <div class="col-lg-12">
                <div class="page-title">
                    <h1>Cogeneraci&oacute;n Eficiente  
                        <small id = "contexto">  Vista general</small>
                    </h1>                  
                </div>
            </div>
            <!-- /.col-lg-12 -->
            <div class = "col-sm-12">
                <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                    <li class="active" style="padding-top:6px"><i class="fa fa-dashboard"></i> Cogeneraci&oacute;n Eficiente</li>
                    
                    <g:if test="${numeroSitios > 0}">
                                <li class="pull-right" style="padding-left:1px;">
                                    <div id="reportrangeperiodo" class="btn btn-blue btn-square date-picker">
                                        <i class="fa fa-calendar"></i>&nbsp;Generar Reporte 
                                        <span class="date-range"></span>    
                                        <button id="comenzarPDF" style="display: none;"/>
                                    </div>
                                    <input type='date' id='dateInput'/>
                                    <input type="checkbox" id="pdf" value="true" checked> Descargar PDF<br>
                                </li>
                                
                            </g:if>

                <li class="pull-right" style="padding-left:1px;" >
                    <g:if test="${numeroSitios < 1}">

                        <select class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                            <option >Sin sitios</option>
                        </select>

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

        <div id="contenidoId">
            <div class="row">

                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading orange">
                            <i class="fa fa-bolt fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content orange">
                            <div class="circle-tile-description text-faded">
                                Entalp&iacute;a
                            </div>
                            <div id="entalpia" class="circle-tile-number text-faded">
                            <g:formatNumber number="${energiaTermicaGeneradaVal}" type="number"/> 
                            kWh
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading blue">
                            <i class="fa fa-sun-o fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content blue">
                            <div class="circle-tile-description text-faded">
                                Eficiencia
                            </div>
                            <div id="energiaNeta" class="circle-tile-number text-faded">
                           
                                <g:formatNumber number="${eficiencia}" type="number"/>
                            
                            %
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="circle-tile">

                        <div class="circle-tile-heading red">
                            <i class="fa fa-fire fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content red">
                            <div class="circle-tile-description text-faded">
                                Combustible
                            </div>
                            <div id="combustible" class="circle-tile-number text-faded">
                            
                                <g:formatNumber number="${energiaCombustibleVal}" type="number" /> 
                            
                            kWh
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3">
                    <div class="circle-tile">
                        <a href="#">
                            <div class="circle-tile-heading green">
                                <i class="fa fa-bolt fa-fw fa-3x"></i>
                            </div>
                        </a>
                        <div class="circle-tile-content green">
                            <div class="circle-tile-description text-faded">
                                Energ&iacute;a Neta
                            </div>
                            <div id="radiacionSolarVal" class="circle-tile-number text-faded">
                           
                                ${energiaNetaVal}
                               
                            
                            kWh
                            </div>
                            &nbsp;
                        </div>
                    </div>
                    <div class="circle-tile">

                        <div class="circle-tile-heading orange">
                            <i class="fa fa-bolt fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content orange">
                            <div class="circle-tile-description text-faded">
                                Flujo m&aacute;sico H2O
                            </div>
                            <div id="flujoMasicoH2O" class="circle-tile-number text-faded">
                            
                                <g:formatNumber number="${flujoMasicoAguaVal}" type="number" />
                            
                            Kg/seg
                            </div>
                            &nbsp;
                        </div>
                    </div>
                    <div class="circle-tile">

                        <div class="circle-tile-heading red">
                            <i class="fa fa-fire fa-fw fa-3x"></i>
                        </div>

                        <div class="circle-tile-content red">
                            <div class="circle-tile-description text-faded">
                                Flujo m&aacute;sico Gas
                            </div>
                            <div id="flujoMasicoGas" class="circle-tile-number text-faded">
                         
                                <g:formatNumber number="${flujoMasicoGasVal}" type="number" />
                            
                            Kg/seg
                            </div>
                            &nbsp;
                        </div>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="tile green" >
                        <h4>
                            <i class="fa fa-bolt"></i> Energ&iacute;a Neta  
                        </h4>
                        <div id="energiaGrafica" style="height: 230px"></div>
                    </div>
                    <div class="tile orange" align = "center">
                        <div class= "col-md-6 circle-tile-description text-faded">
                            Temperatura del agua a la entrada del intercambiador
                             <br>
                             <h3>
                                <g:formatNumber number="${temperaturaAguaEntradaVal}" type="number"  /> 
                           
                            ºC
                             </h3>
                            <div class = "circle-tile-footer">
                                <h2><b>Entrada</b></h2>
                            </font></div>
                        </div>
                        <div class= "col-md-6 circle-tile-description text-faded">
                            Temperatura del agua a la salida del intercambiador
                            <br>
                            <h3>
                                <g:formatNumber number="${temperaturaAguaSalidaVal}" type="number" /> 
                            ºC
                            </h3>
                            <div class = "circle-tile-footer"><font size = "12">
                                <h2><b>Salida</b></h2>
                            </font></div>
                        </div>
                    </div>
                    <div class="tile red" align = "center">
                        <div class="col-md-6 circle-tile-description text-faded">
                            Temperatura del gas a la entrada del intercambiador
                             <br>
                             <h3>
                                <g:formatNumber number="${temperaturaGasEntradaVal}" type="number" /> 
                            ºC
                             </h3>
                            <div class = "circle-tile-footer"><font size = "12">
                                <h2><b>Entrada</b></h2>
                            </font></div>
                        </div>
                        <div class="col-md-6 circle-tile-description text-faded">
                            Temperatura del gas a la salida del intercambiador
                             <br>
                             <h3>
                                <g:formatNumber number="${temperaturaGasSalidaVal}" type="number" /> 
                            ºC
                             </h3>
                            <div class = "circle-tile-footer"><font size = "12">
                                <h2><b>Salida</b></h2>
                            </font></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        
          <g:jasperReport type="hidden" jasper="CogeneracionEficiente" format="PDF" name="PDF" action="createCogeneracionEficienteIndex" controller="pdfUtil">                 
                        <input hidden name="sitioIdPDF" id="sitioIdPDF" />
                        <input hidden name="fechaIniPDF" id="fechaIniPDF" />
                        <input hidden name="fechaFinPDF" id="fechaFinPDF" />
                        
                        <input hidden name="energiaElectrica" id="energiaElectrica" />
                        <input hidden name="energiaTermica" id="energiaTermica" />
                        <input hidden name="combustible" id="combustiblePDF" />
                        
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

   
<!-- Librerías originales
<script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>
<script src="${request.contextPath}/Flex/js/plugins/bootstrap-daterangepicker-master/daterangepicker.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

-->


<script src="${request.contextPath}/Flex/js/moment.js"></script>
<script src="${request.contextPath}/Flex/js/daterangepicker.js"></script>


<!-- Las únicas librerías necesarias en el ejemplo básico que ya está probado
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/Flex/js/moment.js"></script>
    <script type="text/javascript" src="${request.contextPath}/Flex/js/daterangepicker.js"></script>
<!--Fin de las librerías-->

<link href="http://code.jquery.com/ui/1.9.2/themes/smoothness/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>
<script src="${request.contextPath}/Flex/js/demo/cogeneracionEficiente.js"></script>
    
    <g:javascript>    
        var meses = ["No se usa", "Enero","Febrero",
            "Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre",
            "Noviembre", "Diciembre"
        ];
        var fechaFinal = '${outSalida}';
        if (fechaFinal.length > 10){
            var mes = fechaFinal.substring(5,7);
            var periodo = parseInt(fechaFinal.substring(0,4));
            if (parseInt(mes) > 0 && parseInt(mes) <13){
                document.getElementById("contexto").innerHTML = 
                    "Reporte anual del " + fechaFinal.substring(8,10)
                    +" de "+
                    meses[parseInt(fechaFinal.substring(5,7))]
                    + " " + (periodo -1) + 
                    " al "+ fechaFinal.substring(8,10)
                    +" de "+
                    meses[parseInt(fechaFinal.substring(5,7))]
                    + " " + (periodo);
            }
        }
        
        
        //Morris Area Chart  
        
        
        var sales_data = [{
    date: '${arregloGraficaMeses[0]}',
    productA: ${arregloGraficaValores[0]},
    productB: ${arregloGraficaValores2[0]},
    productC: ${arregloGraficaValores3[0]},
    },
    {
    date: '${arregloGraficaMeses[1]}',
    productA: ${arregloGraficaValores[1]},
    productB:  ${arregloGraficaValores2[1]},
    productC: ${arregloGraficaValores3[1]},
    },
    {
    date: '${arregloGraficaMeses[2]}',
    productA: ${arregloGraficaValores[2]},
    productB:  ${arregloGraficaValores2[2]},
    productC: ${arregloGraficaValores3[2]},
    },
    {
    date: '${arregloGraficaMeses[3]}',
    productA: ${arregloGraficaValores[3]},
    productB:  ${arregloGraficaValores2[3]},
    productC: ${arregloGraficaValores3[3]},
    },
    {
    date: '${arregloGraficaMeses[4]}',
    productA: ${arregloGraficaValores[4]},
    productB:  ${arregloGraficaValores2[4]},
    productC: ${arregloGraficaValores3[4]},
    },
    {
    date: '${arregloGraficaMeses[5]}',
    productA: ${arregloGraficaValores[5]},
    productB:  ${arregloGraficaValores2[5]},
    productC: ${arregloGraficaValores3[5]},
    },
    {
    date: '${arregloGraficaMeses[6]}',
    productA: ${arregloGraficaValores[6]},
    productB:  ${arregloGraficaValores2[6]},
    productC: ${arregloGraficaValores3[6]},
    },
    {
    date: '${arregloGraficaMeses[7]}',
    productA: ${arregloGraficaValores[7]},
    productB:  ${arregloGraficaValores2[7]},
    productC: ${arregloGraficaValores3[7]},
    },
    {
    date: '${arregloGraficaMeses[8]}',
    productA: ${arregloGraficaValores[8]},
    productB:  ${arregloGraficaValores2[8]},
    productC: ${arregloGraficaValores3[8]},
    },
    {
    date: '${arregloGraficaMeses[9]}',
    productA: ${arregloGraficaValores[9]},
    productB:  ${arregloGraficaValores2[9]},
    productC: ${arregloGraficaValores3[9]},
    },
    {
    date: '${arregloGraficaMeses[10]}',
    productA: ${arregloGraficaValores[10]},
    productB:  ${arregloGraficaValores2[10]},
    productC: ${arregloGraficaValores3[10]},
    },
    {
    date: '${arregloGraficaMeses[11]}',
    productA: ${arregloGraficaValores[11]},
    productB:  ${arregloGraficaValores2[11]},
    productC: ${arregloGraficaValores3[11]},
    }];

    Morris.Area({
    element: 'energiaGrafica',
    data: sales_data,
    xkey: 'date',
    xLabelFormat: function(date) {
    return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB', 'productC'],
    yLabelFormat: function(y) {
    return y;
    },
    labels: ['Energia Termica', 'Energia Electrica', 'Combustible Consumido'],
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
