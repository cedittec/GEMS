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
        <title>Monitoreo Tiempo Real</title>
    </head> 
</head>
<body>
    <div class="page-content">

                <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
        <div class="row">
            <div class="col-lg-12">
                <div class="page-title">
                    <h1>Monitoreo en tiempo real
                        <small>Energ&iacute;a t&eacute;rmica</small>
                    </h1>
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                                 <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'TiempoReal', action:'index')}"> Monitoreo en tiempo real</a></li>
                                <li class="active" style="padding-top:6px"> Energ&iacute;a t&eacute;rmica</li>                                                                                        
                                
                                 <g:if test="${numeroSitios > 0}">
                            <li class="pull-right" style="margin-left: 1px">
                                    <div>
                                        <button class="form-control"id="comenzarPDF" style="background:#428bca; color:#FFF; border-color:#357ebd;">
                                            Generar PDF </button>
                                    </div>
                                </li>
                             </g:if>
                            
                            <li class="pull-right" style="margin-left: 1px">
                                <g:if test="${numFuentes < 1}">

                                    <select class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                        <option >Sin Fuentes</option>

                                    </g:if>

                                    <g:else>
                                        <select id="fuentesSelect" class="form-control" style="background:#16a085; color:#FFF; border-color:#16a085;">
                                             <g:each var="fuente"  in="${fuentes}" >
                                            <option value="${fuente.id}">${fuente.nombre}</option>
                                           </g:each>  
                                        </select>

                                    </g:else>


                                </li>

                             <li class="pull-right" >
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
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
    <!-- end PAGE TITLE AREA -->


<!-- begin DASHBOARD CIRCLE TILES -->
    <g:if test="${numeroSitios > 0}">                     
        <div id="contenidoId"> 
            <div class="row">


                <div class="col-lg-2">
                    <div class="tile purple dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-gears fa-fw"></i> Temperatura del Tanque de Inercia</h4>

                    <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultTempTanqueInercia}">
                        <span id="tempTanqueInercia" class="temperature">${resultTempTanqueInercia}</span>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="tile green dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-cogs fa-fw"></i> Temperatura del Tanque Servicios</h4>

                    <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultTempTanqueServicios}">
                        <span id="tempTanqueServicio" class="temperature"><g:formatNumber number="${resultTempTanqueServicios}" type="number" maxFractionDigits="3" /></span>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="tile orange dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-globe fa-fw"></i> Temperatura del Tanque del S. T&eacute;rmico</h4>

                        <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultTempTanqueSistemaTermico}">
                            <span id="tempTanqueTermico" class="temperature"><g:formatNumber number="${resultTempTanqueSistemaTermico}" type="number" maxFractionDigits="3" /></span>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="tile yellow dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-cloud fa-fw"></i> Temperatura del Tanque de Condensados</h4>

                        <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultTempTanqueCondensados}">
                            <span id="tempTanqueCondensados" class="temperature"><g:formatNumber number="${resultTempTanqueCondensados}" type="number" maxFractionDigits="3" /></span>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="tile blue dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-fire fa-fw"></i> Temperatura del Tanque de Vapor</h4>

                        <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultTempTanqueVapor}">
                            <span id="tempTanqueVapor" class="temperature"><g:formatNumber number="${resultTempTanqueVapor}" type="number" maxFractionDigits="3" /></span>
                        </div>

                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="tile red dash-demo-tile" style="height:270px">
                        <h4><i class="fa fa-arrows fa-fw"></i> Posici&oacute;n Diverter</h4>

                        <div id="mieasy-pie-1" class="easy-pie-chart" data-percent="${resultPosicionDiverter}">
                            <span id="posicionDiverter" class="percent"><g:formatNumber number="${resultPosicionDiverter}" type="number" maxFractionDigits="3" /></span>
                        </div>

                    </div>
                </div>


            </div>
            <div class="row"> 
                <div class="col-lg-6">

                    <div class="col-lg-12">
                        <div class="tile yellow dash-demo-tile" style="height:190px">
                            <div class="col-lg-6" style="height:100%">
                                <div class="circle-tile-description text-faded" style="padding-top:20px">Temperatura del aire</div>
                                <div id="tempAireEntrada" class="circle-tile-number text-faded">
                                    <i class="fa fa-sign-in"></i><g:formatNumber number="${resultTempAireCalienteEntradaIntercambiador}" type="number" maxFractionDigits="3" /> &#176;C
                                </div>
                                <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">ENTRADA</div> </a>
                            </div>
                            <div class="col-lg-6">
                                <div class="circle-tile-description text-faded" style="padding-top:20px">Temperatura del aire</div>
                                <div id="tempAireSalida" class="circle-tile-number text-faded">
                                    <i class="fa fa-sign-out"></i><g:formatNumber number="${resultTempAireCalienteSalidaIntercambiador}" type="number" maxFractionDigits="3" /> &#176;C
                                </div>
                                <a class="circle-tile-footer"> <div class="circle-tile-number text-faded">SALIDA</div> </a>
                            </div>
                        </div>                                                         
                    </div>


                    <div class="col-lg-4">
                        <div class="circle-tile">
                            <div class="circle-tile-content purple">
                                <div class="circle-tile-description text-faded" style="padding:0px">Flujo economizador</div>
                                <div id="flujoEconomizador" class="circle-tile-number text-faded">
                                    <i class="fa fa-retweet"></i><g:formatNumber number="${resultFlujoEconomizador}" type="number" maxFractionDigits="3" /> m^3/h
                                </div>
                                <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>                            
                    </div>
                    <div class="col-lg-4"> 
                        <div class="circle-tile">
                            <div class="circle-tile-content red">
                                <div class="circle-tile-description text-faded" style="padding:0px">Flujo vaporizador</div>
                                <div id="flujoVaporizador" class="circle-tile-number text-faded">
                                    <i class="fa fa-retweet"></i><g:formatNumber number="${resultFlujoVaporizador}" type="number" maxFractionDigits="3" /> m^3/h
                                </div>
                                <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>
                    <div class="col-lg-4"> 
                        <div class="circle-tile">
                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded" style="padding:0px">Flujo supercalentador</div>
                                <div id="flujoSupercalentador" class="circle-tile-number text-faded">
                                    <i class="fa fa-retweet"></i><g:formatNumber number="${resultFlujoSupercalentador}" type="number" maxFractionDigits="3" /> m^3/h
                                </div>
                                <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                &nbsp;<br>&nbsp;
                            </div>
                        </div>  
                    </div>


                </div>               
                <div class="col-lg-6">
                    <div class="portlet portlet-green">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <h4><i class="fa fa-retweet fa-fw"></i>Flujo del circuito de agua</h4>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div id="barChart" class="panel-collapse collapse in">
                            <div class="portlet-body">
                                <div id="monitoreoTermicoFlujoAguaCog-chart-bar" style="height:350px"></div>
                            </div>
                        </div>
                    </div><!--col-->
                </div>
            </div>

            <div class="row" >   

                <div class="col-lg-5">
                    <div class="portlet portlet-purple">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <h4><i class="fa fa-dashboard fa-fw"></i>Temperaturas de entrada al S. de Cogeneraci&oacute;n</h4>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div id="barChart" class="panel-collapse collapse in">
                            <div class="portlet-body">
                                <div id="monitoreoTermicoTempEntradaCog-chart-bar"  style="height: 400px"></div>
                            </div>
                        </div>
                    </div>       
                </div>
                <div class="col-lg-7">
                    <div class="portlet portlet-dark-blue">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <h4><i class="fa fa-dashboard fa-fw"></i>Temperatura del agua</h4>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div id="barChart" class="panel-collapse collapse in">
                            <div class="portlet-body">
                                <div id="monitoreoTermicoTempAgua-chart-bar"  style="height:400px"></div>
                            </div>
                        </div>
                    </div><!--col-->
                </div><!--col-->

                <div class="row"> 
                    <div class="col-lg-7">
                        <div class="portlet portlet-orange">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4><i class="fa fa-warning fa-fw"></i>Presi&oacute;n de los tanques del S. de Cogeneraci&oacute;n</h4>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="monitoreoTermicoPresionTanque-chart-bar" style="height:310px"></div>
                                </div>
                            </div>
                        </div>



                    </div>
                    <div class="col-lg-5">


                        <div class="col-lg-6">
                            <div class="circle-tile">
                                <div class="circle-tile-content yellow">
                                    <div class="circle-tile-description text-faded" style="padding:0px">Nivel T. de Servicios</div>
                                    <div id="nivelTanqueServicios" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${resultNivelTanqueServicio}" type="number" maxFractionDigits="3" /> %
                                    </div>
                                    <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                    &nbsp;<br>&nbsp;
                                </div>
                            </div>                            
                        </div>

                        <div class="col-lg-6">
                            <div class="circle-tile">
                                <div class="circle-tile-content blue">
                                    <div class="circle-tile-description text-faded" style="padding:0px">Nivel T. de Inercia</div>
                                    <div id="nivelTanqueInercia" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${resultNivelTanqueInercia}" type="number" maxFractionDigits="3" /> %
                                    </div>
                                    <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                    &nbsp;<br>&nbsp;
                                </div>
                            </div>                            
                        </div>
                        <div class="col-lg-6"> 
                            <div class="circle-tile">
                                <div class="circle-tile-content green">
                                    <div class="circle-tile-description text-faded" style="padding:0px">Nivel T. Condensados</div>
                                    <div id="nivelTanqueCondensados" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${resultNivelTanqueCondensados}" type="number" maxFractionDigits="3" /> %
                                    </div>
                                    <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                    &nbsp;<br>&nbsp;
                                </div>
                            </div>  
                        </div>
                        <div class="col-lg-6"> 
                            <div class="circle-tile">
                                <div class="circle-tile-content dark-blue">
                                    <div class="circle-tile-description text-faded" style="padding:0px">Nivel T. de Vapor</div>
                                    <div id="nivelTanqueVapor" class="circle-tile-number text-faded">

                                        <g:formatNumber number="${resultNivelTanqueVapor}" type="number" maxFractionDigits="3" /> %
                                    </div>
                                    <div class="circle-tile-description text-faded"> Sistema de cogeneraci&oacute;n </div>
                                    &nbsp;<br>&nbsp;
                                </div>
                            </div>  
                        </div>


                    </div> 
                </div>




            </div>
            <g:jasperReport type="hidden" jasper="MTRealETermica" format="PDF" name="PDF" action="createMTRealETermica" controller="pdfUtil">           
                <input hidden name="sitioIdPDF" id="sitioIdPDF" />
                <input hidden name="fuenteIdPDF" id="fuenteIdPDF" />
                <input hidden name="fechaIniPDF" id="fechaIniPDF" />
                <input hidden name="fechaFinPDF" id="fechaFinPDF" />
            </g:jasperReport> 
        </div>

    </g:if>
    <g:else>
    </g:else>

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

    <!-- HISRC Retina Images -->
    <script src="${request.contextPath}/Flex/js/plugins/hisrc/hisrc.js"></script>

    <script src="${request.contextPath}/Flex/js/demo/energiaTermica.js"></script>
    <!-- Easy Pie Chart -->
    <script src="${request.contextPath}/Flex/js/plugins/easypiechart/jquery.easypiechart.min.js"></script>    

    <script src="${request.contextPath}/Flex/js/demo/miEasyPie.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'monitoreoTermicoFlujoAguaCog-chart-bar',
        data: [
        { y:  'Sistema Termico', a: ${graficaFlujoCircuitoValores[0]} },
        { y:  'Intercambiador T. inercia', a: ${graficaFlujoCircuitoValores[1]} },
        { y:  'Placas T. de Inercia', a: ${graficaFlujoCircuitoValores[2]} },
        { y:  'Placas T. de Servicio', a: ${graficaFlujoCircuitoValores[3]} }

        ],
        xkey: 'y',
        ykeys: ['a'],
        yLabelFormat: function(y) {
        return  y + " m^3/h";
        },
        labels: ['Flujo Circuito de Agua'],
        barColors: ['#16a061'],
        resize: true
        });
    </g:javascript>

    <g:javascript>        
        Morris.Bar({
        element: 'monitoreoTermicoTempEntradaCog-chart-bar',
        data: [
        { y:  'Economizador', a: ${graficaTempEntradaCog[0]}, b:${graficaTempEntradaCog[1]} },
        { y:  'Vaporizador', a: ${graficaTempEntradaCog[1]}, b:${graficaTempEntradaCog[2]} },
        { y:  'Supercalentador', a: ${graficaTempEntradaCog[3]}, b:${graficaTempEntradaCog[4]} }

        ],
        xkey: 'y',
        ykeys: ['a','b'],
        yLabelFormat: function(y) {
        return  y + " &#176;C";
        },
        labels: ['Temp. Entrada' , 'Temp de salida'],
        barColors: ['#f39c12'],
        resize: true
        });
    </g:javascript>

    <g:javascript>        
        Morris.Bar({
        element: 'monitoreoTermicoTempAgua-chart-bar',
        data: [
        { y:  'Int. del S. deCogeneraci&oacute;n', a: ${graficaTempAgua[0]}, b:${graficaTempAgua[1]} },
        { y:  'T.Servicios Cogeneraci&oacute;n', a: ${graficaTempAgua[1]}, b:${graficaTempAgua[2]} },
        { y:  'T.Inercia Cogeneraci&oacute;n', a: ${graficaTempAgua[3]}, b:${graficaTempAgua[4]}  }

        ],
        xkey: 'y',
        ykeys: ['a','b'],
        yLabelFormat: function(y) {
        return  y + " &#176;C";
        },
        labels: ['Temp. Agua Entrada' , 'Temp. Agua Salida'],
        barColors: ['#2980b9','#34495e'],
        resize: true
        });
    </g:javascript>


    <g:javascript>        
        Morris.Bar({
        element: 'monitoreoTermicoPresionTanque-chart-bar',
        data: [
        { y:  'Tanque Servicios', a: ${graficaPresionTanquesCog[0]} },
        { y:  'Tanque Inercia', a: ${graficaPresionTanquesCog[1]} },
        { y:  'Tanque de Condensados', a: ${graficaPresionTanquesCog[2]} },
        { y:  'Tanque de Presion', a: ${graficaPresionTanquesCog[3]} },
        { y:  'Tuberia de vapor', a: ${graficaPresionTanquesCog[4]} }

        ],
        xkey: 'y',
        ykeys: ['a'],
        yLabelFormat: function(y) {
        return  y + " kg/cm^2";
        },
        labels: ['Presion'],
        barColors: ['#16a061'],
        resize: true
        });
    </g:javascript>



</body>
</html>
