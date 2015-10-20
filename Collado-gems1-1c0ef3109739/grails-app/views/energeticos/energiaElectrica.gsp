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
                            <small>Energ&iacute;a El&eacute;ctrica</small>
                        </h1>
                       

                    </div>
                </div>
                <!-- /.col-lg-12 -->
                <div class = "col-sm-12">
                    <ol class="breadcrumb" style="padding-top:0px; padding-right:0px">
                        <li style="padding-top:6px"><i class="fa fa-dashboard"></i> <a href="${createLink(controller:'energeticos', action:'index')}"> Energeticos</a></li>
                            <li class="active" style="padding-top:6px"> Energ&iacute;a El&eacute;ctrica</li>

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
                    <div class="col-sm-3">
                        <div class="circle-tile">

                            <div class="circle-tile-heading green">
                                <img src="${request.contextPath}/Flex/img/CFElogo.png" width="100%" style="padding-top:10px">
                            </div>

                            <div class="circle-tile-content green">
                                <div class="circle-tile-description text-faded">
                                    Energ&iacute;a el&eacute;ctrica CFE
                                </div>
                                <div id="arregloEnergiasCFE" class="circle-tile-number text-faded">

                                <g:formatNumber number="${arregloEnergiasCFE[0]+arregloEnergiasCFE[1]+arregloEnergiasCFE[2]}" type="number" maxFractionDigits="3" /> kWh
                                </div>
                                &nbsp;
                                <li class="panel" style="list-style-type:none; background-color:#16a085">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#energia">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="energia" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Base:
                                        </li>
                                        <li id="arregloEnergiasCFE1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiasCFE[0]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Intermedia: 
                                        </li>
                                        <li id="arregloEnergiasCFE2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiasCFE[1]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Punta: 
                                        </li>
                                        <li id="arregloEnergiasCFE3" style="text-align:right">                                            
                                            <g:formatNumber number="${arregloEnergiasCFE[2]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>

                                    </ul>
                                </li>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="circle-tile">

                            <div class="circle-tile-heading blue">
                                <i class="fa fa-building-o fa-fw fa-3x"></i>
                            </div>

                            <div class="circle-tile-content blue">
                                <div class="circle-tile-description text-faded">
                                    Energ&iacute;a el&eacute;ctrica del S. de Cogeneraci&oacute;n
                                </div>
                                <div id="arregloEnergiasElectricasCogeneracion" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${arregloEnergiasElectricasCogeneracion[0]+arregloEnergiasElectricasCogeneracion[1]+arregloEnergiasElectricasCogeneracion[2]}" type="number" maxFractionDigits="3" /> kWh
                                </div>
                                <li class="panel" style="list-style-type:none; background-color:#2980b9">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#costoe">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="costoe" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Base:
                                        </li>
                                        <li id="arregloEnergiasElectricasCogeneracion1" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiasElectricasCogeneracion[0]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Intermedia: 
                                        </li>
                                        <li id="arregloEnergiasElectricasCogeneracion2" style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">

                                            <g:formatNumber number="${arregloEnergiasElectricasCogeneracion[1]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Punta: 
                                        </li>
                                        <li id="arregloEnergiasElectricasCogeneracion3" style="text-align:right">

                                            <g:formatNumber number="${arregloEnergiasElectricasCogeneracion[2]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>

                                    </ul>
                                </li>

                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="circle-tile">

                            <div class="circle-tile-heading orange">
                                <i class="fa fa-sun-o fa-fw fa-3x"></i>
                            </div>

                            <div class="circle-tile-content orange">
                                <div class="circle-tile-description text-faded">
                                    Energ&iacute;a el&eacute;ctrica del S. Fotovolt&aacute;ico
                                </div>
                                <div id="arregloEnergiasElectricasFotovoltaico" class="circle-tile-number text-faded">                                     
                                    <g:formatNumber number="${arregloEnergiasElectricasFotovoltaico[0]+arregloEnergiasElectricasFotovoltaico[1]+arregloEnergiasElectricasFotovoltaico[2]}" type="number" maxFractionDigits="3" /> kWh
                                </div>
                                &nbsp;
                                <li class="panel" style="list-style-type:none; background-color:#f39c12">
                                    <a href="javascript:;"  data-toggle="collapse" class="accordion-toggle" data-target="#eficter">
                                        <div class="circle-tile-description text-faded">Desglose <i class="fa fa-caret-down"></i></div>
                                    </a>
                                    <ul class="collapse nav text-faded" id="eficter" style="text-align:justify; padding:10px">
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Base:
                                        </li>
                                        <li style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                             
                                            <g:formatNumber number="${arregloEnergiasElectricasFotovoltaico[0]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Intermedia: 
                                        </li>
                                        <li style="text-align:right; border-bottom-color:(0,0,0,.5); border-bottom-style:dotted; border-bottom-width:thin; margin-bottom:5px">                                            
                                            <g:formatNumber number="${arregloEnergiasElectricasFotovoltaico[1]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>
                                        <li>
                                            Energ&iacute;a El&eacute;ctrica en la Punta: 
                                        </li>
                                        <li style="text-align:right">                                            
                                            <g:formatNumber number="${arregloEnergiasElectricasFotovoltaico[2]}" type="number" maxFractionDigits="3" /> kWh
                                        </li>

                                    </ul>
                                </li>

                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div class="circle-tile">

                            <div class="circle-tile-heading yellow">
                                <i class="fa fa-bolt fa-fw fa-3x"></i>
                            </div>

                            <div class="circle-tile-content yellow">
                                <div class="circle-tile-description text-faded">
                                    Energ&iacute;a El&eacute;ctrica <br>Total 
                                </div>
                                <div id="totalEnergiaElectrica" class="circle-tile-number text-faded">

                                    <g:formatNumber number="${totalEnergiaElectrica}" type="number" maxFractionDigits="3" /> kWh
                                </div>
                                &nbsp;


                            </div>
                        </div>
                    </div>


                </div>

                <div class="row">



                    <div class="col-sm-12">
                        <div class="portlet portlet-purple">
                            <div class="portlet-heading">
                                <div class="portlet-title">
                                    <h4>Energ&iacute;a generada</h4>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div id="barChart" class="panel-collapse collapse in">
                                <div class="portlet-body">
                                    <div id="energeticoselectricos-chart-bar"></div>
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


        <g:jasperReport type="hidden" jasper="EnergeticosEElectrica" format="PDF" name="PDF" action="createEnergeticosEElectrica" controller="pdfUtil">           
            <input hidden name="sitioIdPDF" id="sitioIdPDF" />
            <input hidden name="fechaIniPDF" id="fechaIniPDF" />
            <input hidden name="fechaFinPDF" id="fechaFinPDF" />

            <input hidden name="energiaGenerada" id="idenergiaGenerada" />
            <input hidden name="energiaCFE" id="idenergiaCFE" />
            <input hidden name="energiaBase" id="idenergiaBase" />
            <input hidden name="energiaIntermedia" id="idenergiaIntermedia" />
            <input hidden name="energiaPunta" id="idenergiaPunta" />
            <input hidden name="energiaBase2" id="idenergiaBase2" />

            <input hidden name="energiaIntermedia2" id="idenergiaIntermedia2" />
            <input hidden name="energiaPunta2" id="idenergiaPunta2" />
            <input hidden name="energiaCogeneracion" id="idenergiaCogeneracion" />
            <input hidden name="energiaFotovoltaico" id="idenergiaFotovoltaico" />
            <input hidden name="energiaBase3" id="idenergiaBase3" />
            <input hidden name="energiaIntermedia3" id="idenergiaIntermedia3" />
            <input hidden name="energiaPunta3" id="idenergiaPunta3" />

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
    <script src="${request.contextPath}/Flex/js/demo/energeticosEnergiaElectrica.js"></script>

    <g:javascript>        
        Morris.Bar({
        element: 'energeticoselectricos-chart-bar',
        data: [
        { y:  '${arregloGraficaMeses[0]}', e: ${arregloGraficaValores[0]},  t: ${arregloGraficaValores2[0]}, c: ${arregloGraficaValores3[0]} },
        { y:  '${arregloGraficaMeses[1]}', e: ${arregloGraficaValores[1]},  t: ${arregloGraficaValores2[1]}, c: ${arregloGraficaValores3[1]} },
        { y:  '${arregloGraficaMeses[2]}', e: ${arregloGraficaValores[2]},  t: ${arregloGraficaValores2[2]}, c: ${arregloGraficaValores3[2]} },
        { y:  '${arregloGraficaMeses[3]}', e: ${arregloGraficaValores[3]},  t: ${arregloGraficaValores2[3]}, c: ${arregloGraficaValores3[3]} },
        { y:  '${arregloGraficaMeses[4]}', e: ${arregloGraficaValores[4]},  t: ${arregloGraficaValores2[4]}, c: ${arregloGraficaValores3[4]} },
        { y:  '${arregloGraficaMeses[5]}', e: ${arregloGraficaValores[5]},  t: ${arregloGraficaValores2[5]}, c: ${arregloGraficaValores3[5]} },
        { y:  '${arregloGraficaMeses[6]}', e: ${arregloGraficaValores[6]}, t: ${arregloGraficaValores2[6]}, c: ${arregloGraficaValores3[6]} },
        { y:  '${arregloGraficaMeses[7]}', e: ${arregloGraficaValores[7]}, t: ${arregloGraficaValores2[7]}, c: ${arregloGraficaValores3[7]} },
        { y:  '${arregloGraficaMeses[8]}', e: ${arregloGraficaValores[8]}, t: ${arregloGraficaValores2[8]}, c: ${arregloGraficaValores3[8]} },
        { y:  '${arregloGraficaMeses[9]}', e: ${arregloGraficaValores[9]}, t: ${arregloGraficaValores2[9]}, c: ${arregloGraficaValores3[9]} },
        { y:  '${arregloGraficaMeses[10]}', e: ${arregloGraficaValores[10]}, t: ${arregloGraficaValores2[10]}, c: ${arregloGraficaValores3[10]} },
        { y:  '${arregloGraficaMeses[11]}', e: ${arregloGraficaValores[11]}, t: ${arregloGraficaValores2[11]}, c: ${arregloGraficaValores3[11]} }

        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c'],
        labels: ['CFE ', 'Sistema Cog.', 'Sistema Fotovoltaico'],
        barColors: ['#f39c12','#e74c3c', '#16a085'],
        resize: true
        });
    </g:javascript>

</body>
</html>
