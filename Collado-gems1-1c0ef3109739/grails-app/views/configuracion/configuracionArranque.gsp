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
        <title>Configuraci&oacute;n </title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE ROW -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>
                            Configuraci&oacute;n y respaldo
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
                                <h3>Configuraci&oacute;n</h3>
                                <div class="form-group">

                                    
                                    <g:form name="Configuracion" action="dieselNuevo">                                                                                
                                        <div class="form-group" style="width:33%">                                            
                                            <div class="form-inline" style="padding-left:25px">  
                                                <label>Ultimo registro: ${ultimoPrecioDiesel} fecha: ${fechaUltimoPrecioDiesel} </label>
                                                <label>Registrar precio diesel mes actual</label>
                                                <input step="0.0001" min="0" name="precio" type="number" class="form-control" placeholder="0.3344" required>   
                                                <button type="submit" class="btn btn-default">Registrar</button>                                         
                                            </div>
                                        </div>                                        
                                    </g:form>                                                                                                               
                                </div>
                                <div class="col-lg-6">
                                <div class="table-responsive">
                                   
                                        <table class="table table-hover table-bordered table-striped">                                                                                
                                            <thead>
                                                <tr>
                                                    <g:form name="Configuracion" action="runServiceData">
                                                        <td>Ejecutar algoritmos de datos externos manualmente </td>
                                                        <td> <button type="submit" class="btn btn-blue">Ejecutar</button></td>
                                                    </g:form>                                                               
                                                </tr>

                                                <tr>
                                                    <g:form name="Configuracion" action="backup">
                                                        <td>Respaldar sistema</td>
                                                        <td> <button type="submit" class="btn btn-blue">Respaldar</button></td>
                                                    </g:form>                                                   
                                                </tr>

                                            </thead>                                        
                                        </table>                                                                
                                </div>
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

</body>
</html>
