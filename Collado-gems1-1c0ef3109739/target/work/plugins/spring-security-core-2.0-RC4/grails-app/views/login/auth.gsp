<!DOCTYPE html>

<%@ page import="com.GEMS1.Usuario" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <title><g:message code="springSecurity.login.title"/></title>
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Green Energy Management System</title>
        <link rel="shortcut icon" href="${request.contextPath}/Flex/img/logoIcon.ico">

    <!-- GLOBAL STYLES -->
        <link href="${request.contextPath}/Flex/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700,300italic,400italic,500italic,700italic' rel="stylesheet" type="text/css">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel="stylesheet" type="text/css">
        <link href="${request.contextPath}/Flex/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <!-- PAGE LEVEL PLUGIN STYLES -->

    <!-- THEME STYLES -->
        <link href="${request.contextPath}/Flex/css/style.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/ss/plugins.css" rel="stylesheet">

    <!-- THEME DEMO STYLES -->
        <link href="${request.contextPath}/Flex/css/demo.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->

    </head>

    <body  class="login">

        <div id='login'>
            <div class='inner'>
                <div class="container">
                    <div class="row">
                        <div class="col-md-4 col-md-offset-4">
                            <div class="login-banner text-center">
                                <img src="${request.contextPath}/Flex/img/mainLogo.png" class="img-responsive">
                            </div>
                            <div class="portlet portlet-green">
                                
                                <div class="portlet-heading login-heading">
                                    <div class="portlet-title">
                                        <h4><strong>Inicie sesi&oacute;n en GEMS!</strong>
                                        </h4>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                
                                <div class="portlet-body">

                               

                                <g:if test='${flash.message}'>
                                    <div class='login_message'>${flash.message}</div>
                                </g:if>
                                  <form action='${resource('file': 'j_spring_security_check')}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                                    <fieldset>
                                     <div class="form-group">
                                         <input type='text' placeholder="Correo electr&oacute;nico" class="form-control" name='j_username' id='username'/>
                                        <input type='text' name='lang' value="es_ES"  hidden/>
                                    </div>

                                    <div class="form-group">
                                        
                                        <input type='password' placeholder="Contrase&ntilde;a" class="form-control" name='j_password' id='password'/>
                                    </div>
                                    <div class="row">
                                    <div class="col-md-6">    
                                    <p id="remember_me_holder">
                                        <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
                                        <label>Recordar cuenta</label>
                                    </p>
                                    </div>
                                    <div class="col-md-6">    
                                        <a href="mailto:energia@emsmx.com?subject=GEMS:Recuperaci&oacute;n de contrase&ntilde;a&body=Solicitud de recuperaci&oacute;n de contrase&ntilde;a para el correo: INSERTE SU CORREO">Recuperar contrase&nacute;a</a>
                                    </div>
                                    </div>
                                    <p>
                                        <input class="btn btn-lg btn-green btn-block" type='submit' id="submit" value="Iniciar sesi&oacute;n"/> 
                                    </p>
                                    </fieldset>
                                </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    <!-- GLOBAL SCRIPTS -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <!-- HISRC Retina Images -->
        <script src="${request.contextPath}/Flex/js/plugins/hisrc/hisrc.js"></script>

    <!-- PAGE LEVEL PLUGIN SCRIPTS -->

    <!-- THEME SCRIPTS -->
        <script src="${request.contextPath}/Flex/js/flex.js"></script>

    </body>

</html>
