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
        <title>Energ&iacute;a T&eacute;rmica</title>
    </head>
    <body>
        <div class="page-content">

           <!-- begin PAGE TITLE AREA -->
                <!-- Use this section for each page's title and breadcrumb layout. In this example a date range picker is included within the breadcrumb. -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>Energ&eacute;ticos 
                            <small>Energ&iacute;a T&eacute;rmica</small>
                        </h1>
                       
                    </div>
                </div>
                <!-- /.col-lg-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                        <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'energeticos', action:'index')}"> Energeticos</a></li>
                            <li class="active" style="padding-top:6px"> Energ&iacute;a T&eacute;rmica</li>

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
                                <div id="termicaGenerada" class="circle-tile-number text-faded">

                                <g:formatNumber number="${arregloTermicaGenerada[0]}" type="number" maxFractionDigits="3" /> kWt
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#f39c12">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#energia">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="energia" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a generada por el S. de Cogeneraci&oacute;n:
                                        </li>
                                        <li id="termicaCogeneracion" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloTermicaGenerada[1]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a generada por el S. T&eacute;rmico: 
                                        </li>
                                        <li id="termicaTermico" style="text-align:right">

                                            <g:formatNumber number="${arregloTermicaGenerada[2]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>

                                    </ul>
                                </li>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="circle-tile">

                            <div class="circle-tile-heading blue">
                                <i class="fa fa-building-o fa-fw fa-3x"></i>
                            </div>

                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Energ&iacute;a aprovechada por el S. de Cogeneraci&oacute;n
                                </div>
                                <div id="cogeneracionAprovechada" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${arregloEnergiaAprovechada[0]}" type="number" maxFractionDigits="3" /> kWt
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#2980b9">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoe">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="costoe" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a del circuito de tanque de inercia (Intercambiador Aire/Agua):
                                        </li>
                                        <li id="cogeneracionTanqueInercia" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiaAprovechada[1]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a del circuito de tanque de inercia (Intercambiador Agua/Agua): 
                                        </li>
                                        <li id="cogeneracionTanqueInercia2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiaAprovechada[2]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a del circuito de tanque de servicios (Intercambiador Agua/Agua):
                                        </li>
                                        <li id="cogeneracionTanqueServicios" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px"> 

                                            <g:formatNumber number="${arregloEnergiaAprovechada[3]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a para generaci&oacute;n de vapor:
                                        </li>
                                        <li id="cogeneracionVapor" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px"> 

                                            <g:formatNumber number="${arregloEnergiaAprovechada[4]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a economizador:
                                        </li>
                                        <li id="cogeneracionEconomizador" style="text-align:right"> <g:formatNumber number="${arregloEnergiaAprovechada[5]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>

                                    </ul>
                                </li>

                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="circle-tile">

                            <div class="circle-tile-heading dark-blue">
                                <i class="fa fa-star fa-fw fa-3x"></i>
                            </div>

                            <div class="circle-tile-content dark-blue">
                                <div class="circle-tile-description text-faded">
                                    Eficiencia t&eacute;rmica
                                </div>
                                <div id="eficienciaTermica" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${eficiencia[0]}" type="number" maxFractionDigits="3" />%
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#34495e">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#eficter">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="eficter" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a aprovechada por el S. de Cogeneraci&oacute;n:
                                        </li>
                                        <li id="eficienciaCogeneracion" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${eficiencia[1]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a aprovechada por el S. T&eacute;rmico: 
                                        </li>
                                        <li id="eficienciaTermico" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${eficiencia[2]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>
                                        <li>
                                            Energ&iacute;a aprovechada por el Colector Solar:
                                        </li>
                                        <li id="eficienciaColectorSolar" style="text-align:right"> <g:formatNumber number="${eficiencia[3]}" type="number" maxFractionDigits="3" /> kWt
                                        </li>

                                    </ul>
                                </li>

                            </div>
                        </div>
                    </div>


                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="portlet portlet-orange">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Energ&iacute;a generada</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="energeticostermicos-chart-bar"></div>
                                </div>
                            </div>
                        </div>
                    </div><!--col12-->


                </div><!--row-->
                <!-- end DASHBOARD CIRCLE TILES -->
            </div>
        </g:if>
        <g:else>
        </g:else>

        <g:jasperReport type="hidden" jasper="EnergeticosETermica" format="PDF" name="PDF" action="createEnergeticosETermica" controller="pdfUtil">           
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="generada" id="idgenerada" />
            <input hidden name="cogeneracion" id="idcogeneracion" />
            <input hidden name="termico" id="idtermico" />
            <input hidden name="aireAgua" id="idaireAgua" />
            <input hidden name="aguaAgua" id="idaguaAgua" />
            <input hidden name="aguaAgua2" id="idaguaAgua2" />

            <input hidden name="vapor" id="idvapor" />
            <input hidden name="economizador" id="ideconomizador" />
            <input hidden name="eficiencia" id="ideficiencia" />
            <input hidden name="cogeneracion2" id="idcogeneracion2" />
            <input hidden name="termico2" id="idtermico2" />
            <input hidden name="colector" id="idcolector" />

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
    <script src="${request.contextPath}/Flex/js/demo/energeticosTermica.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'energeticostermicos-chart-bar',
        data: [
        { y:  '${arregloGraficaMeses[0]}', e: ${arregloGraficaValores[0]},  t: ${arregloGraficaValores2[0]} },
        { y:  '${arregloGraficaMeses[1]}', e: ${arregloGraficaValores[1]},  t: ${arregloGraficaValores2[1]} },
        { y:  '${arregloGraficaMeses[2]}', e: ${arregloGraficaValores[2]},  t: ${arregloGraficaValores2[2]} },
        { y:  '${arregloGraficaMeses[3]}', e: ${arregloGraficaValores[3]},  t: ${arregloGraficaValores2[3]} },
        { y:  '${arregloGraficaMeses[4]}', e: ${arregloGraficaValores[4]},  t: ${arregloGraficaValores2[4]} },
        { y:  '${arregloGraficaMeses[5]}', e: ${arregloGraficaValores[5]},  t: ${arregloGraficaValores2[5]} },
        { y:  '${arregloGraficaMeses[6]}', e: ${arregloGraficaValores[6]}, t: ${arregloGraficaValores2[6]} },
        { y:  '${arregloGraficaMeses[7]}', e: ${arregloGraficaValores[7]}, t: ${arregloGraficaValores2[7]} },
        { y:  '${arregloGraficaMeses[8]}', e: ${arregloGraficaValores[8]}, t: ${arregloGraficaValores2[8]} },
        { y:  '${arregloGraficaMeses[9]}', e: ${arregloGraficaValores[9]}, t: ${arregloGraficaValores2[9]} },
        { y:  '${arregloGraficaMeses[10]}', e: ${arregloGraficaValores[10]}, t: ${arregloGraficaValores2[10]} },
        { y:  '${arregloGraficaMeses[11]}', e: ${arregloGraficaValores[11]}, t: ${arregloGraficaValores2[11]} }

        ],
        xkey: 'y',
        ykeys: ['e', 't'],
        labels: ['Sistema Cog.', 'Sistema Termico'],
        barColors: ['#f39c12','#e74c3c', '#16a085'],
        resize: true
        });
    </g:javascript>    

</body>
</html>
