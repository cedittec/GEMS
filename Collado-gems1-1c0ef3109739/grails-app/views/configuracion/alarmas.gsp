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
        <title>Alarmas</title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE ROW -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>
                            Administraci&oacuten Alarmas
                        </h1>                           
                    </div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <!-- end PAGE TITLE ROW -->

                <!-- FAQ Accordion -->

            <div class="row">
                <div class="col-lg-12" >

                    <div class="portlet portlet-default">
                        <div class="portlet-body">


                            <g:if test='${flash.error}'>
                                <div class="alert alert-danger alert-dismissable">
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                    <strong>Error - </strong> ${flash.error}
                                </div>
                            </g:if>
                            <g:if test='${flash.message}'>

                                <div class="alert alert-success alert-dismissable" >
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                    ${flash.message}
                                </div>
                            </g:if>                           

                            <div class="row">
                                <h3>Alarmas</h3>
                                <div class="table-responsive">
                                    <table id ="paginated"  class="table table-hover table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>id</th>
                                                <th>Alarma</th>
                                                <th>Mensaje</th>
                                                <th>Sitio</th>
                                                <th>Fecha</th>
                                                <th>Apagar</th>
                                            </tr>
                                        </thead>
                                        <tbody>                                                            
                                            <g:each in="${alarmasLista}" var="alarmaReg" >

                                                <tr>
                                                    <td>${alarmaReg.id}</td>
                                                    <td>${alarmaReg.alarma}</td>
                                                    <td>${alarmaReg.mensaje}</td>
                                                    <td>${alarmaReg.sitio}</td>
                                                    <td>${alarmaReg.fecha}</td>
                                                    <td>
                                                        <g:if test = "${(alarmaReg.visto  == 0)}">
                                                            <button class="btn btn-blue" id="${alarmaReg.id}" onclick="apagar(${alarmaReg.id});" type="button">Desactivar</button>
                                                        </g:if>
                                                        <g:else>
                                                            <button class="btn btn-blue disabled" id="${alarmaReg.id}" onclick="apagar(${alarmaReg.id});" type="button">Desactivada</button>
                                                        </g:else>
                                                      
                                                    </td>
                                                </tr>

                                            </g:each>                                                            
                                        </tbody>
                                    </table>
                                    <div id="pageNavPosition"></div>
                                </div>

                            </div>

                        </div>
                        <!-- /.portlet-body -->
                    </div>
                    <!-- /.portlet -->

                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /.page-content -->

    </div>

    <script>
        var pager;
        $(document).ready(function(){                    
            pager = new Pager('paginated', 10); 
            pager.init(); 
            pager.showPageNav('pager', 'pageNavPosition'); 
            pager.showPage(1);

        });

        function apagar(idAlarma) {           
           //$.post('/GEMS1/Configuracion/desactivarAlarma', {alarmaId:idAlarma});
            var xmlhttp;
                    if (window.XMLHttpRequest)
                    {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                    }
                    else
                    {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                    }
           xmlhttp.open("POST","desactivarAlarma?alarmaId="+idAlarma,true);
           xmlhttp.send();
           document.getElementById(idAlarma).setAttribute('disabled','disabled');
           $("#data").val("");
        }
    </script>
    <script  src = "${request.contextPath}/Flex/js/paging.js"></script>

</body>
</html>
