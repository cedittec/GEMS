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
        <title>Ayuda y Soporte</title>
    </head>
    <body>
        <div class="page-content">

                <!-- begin PAGE TITLE ROW -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="page-title">
                        <h1>
                            Administraci&oacuten contrase&ntilde;a
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
                                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                                <strong>Error - </strong> ${flash.error}
                            </div>
                            </g:if>
                             <g:if test='${flash.message}'>
                                
                                <div class="alert alert-success alert-dismissable" >
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&#215;</button>
                                 ${flash.message}
                                </div>
                            </g:if>
                             <g:else>
                                <div class="alert alert-success alert-dismissable" >
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                    Las contrase&ntilde;as deber&aacute;n de incluir a lo menos una letra min&uacute;scula, una letra may&uacute;scula, un n&uacute;mero y un punto.
                                    Debe ser de una longitud a lo menos de 10 caracteres
                                </div>
                            </g:else>
                            
                            <div class="row">
                                <div class="tab-pane fade in" id="changePasswordC">
                                    <h3>Cambiar contrase&ntilde;a:</h3>
                                    <g:form onsubmit="return checkForm(this);" name="AyudaSoporte" action="passwordMod">
                                        <div class="form-group">
                                            <label>Contrase&ntilde;a actual</label>
                                            <input name="passBefore" type="password" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Contrase&ntilde;a nueva</label>
                                            <input name="pwd1" type="password" class="form-control" value="" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Confirmar nueva contrase&ntilde;a</label>
                                            <input name="pwd2" type="password" class="form-control" value="" required>
                                        </div>
                                        <button type="submit" class="btn btn-default">Actualizar contrase&ntilde;a</button>
                                        <button class="btn btn-green">Cancelar</button>
                                    </g:form>
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

    <script type="text/javascript">

         function checkPassword(str)
        {
       
           //Al menos un numero, una minuscula y una mayuscula
           //puede tener letras, numeros y punto
            var re = /^(?=.*\d)(?=.*\.)(?=.*[a-z])(?=.*[A-Z]).{10,}$/;
            return re.test(str);
        }

        function checkForm(form)
        {
            
            if(form.pwd1.value != "" && form.pwd1.value == form.pwd2.value) {
                if(!checkPassword(form.pwd1.value)) {
                alert("El password debe tener longitud minima de 10, mayusculas, minusculas,numeros y punto");
                form.pwd1.focus();
                return false;
                }
            } else {
                alert("Error: Ingresar password y confirmar");
                form.pwd1.focus();
                return false;
            }
            return true;
        }

    </script>

</body>
</html>
