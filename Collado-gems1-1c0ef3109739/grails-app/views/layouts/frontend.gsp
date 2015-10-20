<%@ page import="com.GEMS1.Usuario" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>GEMS</title>
        <link rel="shortcut icon" href="${request.contextPath}/Flex/img/logoIcon.ico">

    <!-- PACE LOAD BAR PLUGIN - This creates the subtle load bar effect at the top of the page. -->
        <link href="${request.contextPath}/Flex/css/plugins/pace/pace.css" rel="stylesheet">
        <script src="${request.contextPath}/Flex/js/plugins/pace/pace.js"></script>
        
        <asset:javascript src="spring-websocket" />        

    <!-- GLOBAL STYLES - Include these on every page. -->
        <link href="${request.contextPath}/Flex/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700,300italic,400italic,500italic,700italic' rel="stylesheet" type="text/css">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel="stylesheet" type="text/css">
        <link href="${request.contextPath}/Flex/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">

        <g:javascript library="jquery" plugin="jquery"/>
        <!-- PAGE LEVEL PLUGIN STYLES -->
        <link href="${request.contextPath}/Flex/css/plugins/messenger/messenger.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins/messenger/messenger-theme-flat.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins/morris/morris.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins/datatables/datatables.css" rel="stylesheet">

<!-- THEME STYLES - Include these on every page. -->
        <link href="${request.contextPath}/Flex/css/style.css" rel="stylesheet">
        <link href="${request.contextPath}/Flex/css/plugins.css" rel="stylesheet">

    <!-- THEME DEMO STYLES - Use these styles for reference if needed. Otherwise they can be deleted. -->
        <link href="${request.contextPath}/Flex/css/demo.css" rel="stylesheet">
        <asset:javascript src="spring-websocket" />
          
         <script type="text/javascript">
            $(function() { 
                var socket = new SockJS("${createLink(uri: '/stomp')}");
                var client = Stomp.over(socket);

                client.connect({}, function() {
                    client.subscribe("/topic/alarma", function(message) {                        
                        var str = JSON.parse(message.body);
                        var res = str.split("%");                        
                        var cantAlarmasNuevas = res.pop();                     
                        var tamArreglo = res.length;
                        var cantAlarmas = res.length /2;
                       
                        
                        if(cantAlarmas>0)
                        {  
                            var inputData = $( "#data" );
                            //inputData.val( inputData.val() + res[i] + "," );
                           
                            for(var i=0; i<tamArreglo; i++)
                            { 
                                var textInputData = inputData.val().split(",");
                                if(contains(textInputData,res[i]))                            
                                {                               
                                    i++;
                                }else{
                                   // cantAlarmasNuevas++;
                                    inputData.val( inputData.val() + res[i] + "," );
                                    i++;
                                    var textoAlarma = res[i];
                                    $("#listaAlarmas").append('<li><div class="alert-icon yellow pull-left"><i class="fa fa-wrench"></i></div>'+textoAlarma+'</li>');
                                    $(".number").show();
                                    $(".number").html(cantAlarmasNuevas+"");
                                }
                            }
                        }                                                                         
                    });
                });            
            });                     
            
            function contains(a, obj) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i] === obj) {
                        return true;
                    }
                }
                return false;
            }
        </script> 
        
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->

    </head>

    <body>

        <div id="wrapper">

        <!-- begin TOP NAVIGATION -->
            <nav class="navbar-top" role="navigation">

            <!-- begin BRAND HEADING -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle pull-right" data-toggle="collapse" data-target=".sidebar-collapse">
                        <i class="fa fa-bars"></i> Menu
                    </button>
                    <div class="navbar-brand">
                       
                            <img src="${request.contextPath}/Flex/img/flex-admin-logo.png" data-1x="${request.contextPath}/Flex/img/flex-admin-logo@1x.png" data-2x="${request.contextPath}/Flex/img/flex-admin-logo@2x.png" class="hisrc img-responsive" alt="">
                       
                    </div>
                </div>
                <!-- end BRAND HEADING -->

                <div class="nav-top">

                <!-- begin LEFT SIDE WIDGETS -->
                    <ul class="nav navbar-left">
                        <li class="tooltip-sidebar-toggle">
                            <a href="#" id="sidebar-toggle" data-toggle="tooltip" data-placement="right" title="Minimizar">
                                <i class="fa fa-bars"></i>
                            </a>
                        </li>
                        <!-- You may add more widgets here using <li> -->
                    </ul>
                    <!-- end LEFT SIDE WIDGETS -->

                <!-- begin MESSAGES/ALERTS/TASKS/USER ACTIONS DROPDOWNS -->
                    <ul class="nav navbar-right">
                    <!-- begin ALERTS DROPDOWN -->
                     <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico">
                        <li id="alarmasIcon" class="dropdown">
                            <a   href="#" class="alerts-link dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-bell"></i> 
                                <span hidden id="cantidadIcono" class="number">-</span><i class="fa fa-caret-down"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-scroll dropdown-alerts">

                            <!-- Alerts Dropdown Heading -->
                                <li class="dropdown-header">
                                    <i id="bannerNuevas" class="fa fa-bell"></i> Alertas
                                </li>

                            <!-- Alerts Dropdown Body - This is contained within a SlimScroll fixed height box. You can change the height using the SlimScroll jQuery features. -->
                                <li id="alertScroll">
                                    <ul id="listaAlarmas" class="list-unstyled">                                                                                                                 
                                    </ul>
                                    <li class="dropdown-footer"> <a href="${createLink(controller:'Configuracion', action:'alarmas')}">
                                            Ver todas las alertas
                                        </a>
                                    </li>
                                </li>
                            </ul>
                            <!-- /.dropdown-menu -->
                        </li>
                       </sec:ifAnyGranted>
                        <!-- /.dropdown -->
                        <!-- end ALERTS DROPDOWN -->                    

                    <!-- begin USER ACTIONS DROPDOWN -->
                    
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-user"></i>  <i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li>
                                
                                <a href="${createLink(controller:'AyudaSoporte', action:'util')}">
                                    <i class="fa fa-user"></i> Mi Perfil
                                </a>
                                
                               <!--  <sec:ifAnyGranted roles="ROLE_SysAdmin">
                                     <a href="${createLink(controller:'Configuracion', action:'backup')}">
                                        <i class="fa fa-flash"></i> Respaldar Base de datos
                                     </a>
                                 </sec:ifAnyGranted>
                                 -->
                                 <sec:ifAnyGranted roles="ROLE_SysAdmin">
                                     <a href="${createLink(controller:'Configuracion', action:'configuracionArranque')}">
                                        <i class="fa fa-flash"></i> Configuraci&oacute;n y respaldo
                                     </a>
                                      <a href="${createLink(controller:'Configuracion', action:'configurarContrasena')}">
                                        <i class="fa fa-asterisk"></i> Configuraci&oacute;n de contrase&ntilde;as
                                     </a>
                                 </sec:ifAnyGranted>
                                
                            </li>
                        </ul>
                    </li>
                    <!-- /.dropdown -->
                    <!-- end USER ACTIONS DROPDOWN -->

                    </ul>
                    <!-- /.nav -->
                    <!-- end MESSAGES/ALERTS/TASKS/USER ACTIONS DROPDOWNS -->

                </div>
                <!-- /.nav-top -->
            </nav>
            <!-- /.navbar-top -->
            <!-- end TOP NAVIGATION -->

        <!-- begin SIDE NAVIGATION -->
            <nav class="navbar-side" role="navigation">
                <div class="navbar-collapse sidebar-collapse collapse">
                    <ul id="side" class="nav navbar-nav side-nav">
                        <!-- begin SIDE NAV USER PANEL -->
                        <li class="side-user hidden-xs">
                            <a href="${createLink(controller:'Usuario', action:'homeRedirect')}" style="width:100%"><img class="img-circle" src="${request.contextPath}/Flex/img/profile-pic.png" alt="" ></a>

                            <p class="welcome">
                                <i class="fa fa-key"></i> Registrado como
                            </p>
                            <p class="name tooltip-sidebar-logout">
                                <g:set var="usuarioActual" value="${Usuario.findByEmail(sec.loggedInUserInfo(field:'username'))}"/>
                                ${usuarioActual.nombre1}

                                <span class="last-name">${usuarioActual.apellido1}</span>  <a style="color: inherit" class="logout_open" href="#logout" data-toggle="tooltip" data-placement="top" title="Logout"><i class="fa fa-sign-out"></i></a>
                            </p>
                            <div class="clearfix"></div>
                        </li>
                        <!-- end SIDE NAV USER PANEL -->

<!-- begin DASHBOARD LINK -->
                        <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico,ROLE_clienteAdmin,ROLE_adminVentas">
                            <li >
                                <a href="${createLink(controller:'EstadoFinanciero', action:'index')}">
                                    <i class="fa fa-angle-double-right"></i> Estado financiero
                                </a>
                            </li>                           
                        </sec:ifAnyGranted>

                        <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico,ROLE_clienteAdmin,ROLE_clienteTecnico,ROLE_adminVentas">
                            <li>
                                <a href="${createLink(controller:'TiempoReal', action:'index')}">
                                    <i class="fa fa-angle-double-right"></i> Tiempo Real
                                </a>
                            </li>
                        </sec:ifAnyGranted>
                        <!-- end DASHBOARD LINK -->

                    <!-- begin FORMS DROPDOWN -->
                        <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico,ROLE_clienteAdmin,ROLE_clienteTecnico,ROLE_adminVentas">
                            <li>
                                <a href="${createLink(controller:'energeticos', action:'index')}">
                                    <i class="fa fa-angle-double-right"></i> Energeticos
                                </a>
                            </li>
                        </sec:ifAnyGranted>
                        <!-- end FORMS DROPDOWN -->
                       
                        <li>
                            <a href="${createLink(controller:'CogeneracionEficiente', action:'index')}">
                                <i class="fa fa-angle-double-right"></i> Cogeneracion eficiente
                            </a>
                        </li>
                            
                        <!-- begin CALENDAR LINK -->
                        <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico,ROLE_clienteAdmin,ROLE_clienteTecnico,ROLE_adminVentas">
                            <li>
                                <a href="${createLink(controller:'emisiones', action:'index')}">
                                    <i class="fa fa-angle-double-right"></i> Emisiones
                                </a>
                            </li>
                        </sec:ifAnyGranted>
                        <!-- end CALENDAR LINK -->
                        <!-- begin TABLES DROPDOWN -->
                        <sec:ifAnyGranted roles="ROLE_SysAdmin, ROLE_adminTecnico">
                            <li class="panel">
                                <a href="javascript:;" data-parent="#side" data-toggle="collapse" class="accordion-toggle" data-target="#tables">
                                    <i class="fa fa-gear"></i> Configuraci&oacute;n <i class="fa fa-caret-down"></i>
                                </a>
                                <ul class="collapse nav" id="tables">
                                    <li>

                                        <a href="${createLink(controller:'usuario', action:'index')}">
                                            <i class="fa fa-angle-double-right"></i> Usuarios
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${createLink(controller:'Configuracion', action:'cliente')}">
                                            <i class="fa fa-angle-double-right"></i> Clientes
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${createLink(controller:'Configuracion', action:'sitio')}">
                                            <i class="fa fa-angle-double-right"></i> Sitios
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${createLink(controller:'Configuracion', action:'fuente')}">
                                            <i class="fa fa-angle-double-right"></i> Fuentes
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </sec:ifAnyGranted>
                        <!-- end TABLES DROPDOWN -->
                        <!-- begin UI ELEMENTS DROPDOWN -->   
                        <sec:ifAnyGranted roles="ROLE_SysAdmin,ROLE_adminTecnico,ROLE_clienteAdmin,ROLE_clienteTecnico,ROLE_AdminVentas"> 
                            <li>
                                <a href="${createLink(controller:'AyudaSoporte', action:'index')}">
                                    <i class="fa fa-ambulance"></i> Ayuda y soporte
                                </a>
                            </li>
                        </sec:ifAnyGranted>

<!-- end PAGES DROPDOWN -->
                    </ul>
                    <!-- /.side-nav -->
                </div>
                <!-- /.navbar-collapse -->
            </nav>
            <!-- /.navbar-side -->
            <!-- end SIDE NAVIGATION -->

        <!-- begin MAIN PAGE CONTENT -->
            <div id="page-wrapper">
                <g:layoutBody/>

    <!-- /.page-content -->

            </div>
            <!-- /#page-wrapper -->
            <!-- end MAIN PAGE CONTENT -->

        </div>
        <!-- /#wrapper -->

    <!-- GLOBAL SCRIPTS -->

        <script src="${request.contextPath}/Flex/js/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/popupoverlay/defaults.js"></script>
        <!-- Logout Notification Box -->
        <div id="logout">
            <div class="logout-message">
                <img class="img-circle img-logout" src="${request.contextPath}/Flex/img/profile-pic.png" alt="">
                <h3>
                    <i class="fa fa-sign-out text-green"></i> Listo para irse?
                </h3>
                <p>Seleccione "Salir" si esta listo para <br> terminar su sesi&oacute;n actual.</p>
                <ul class="list-inline">
                    <li>

                        <g:form  controller="logout" method="post" id="myForm">
                            <button class="btn btn-green">Salir</button>
                        </g:form>

                    </li>
                    <li>
                        <button class="logout_close btn btn-orange">Cancelar</button>
                    </li>
                </ul>
            </div>
        </div>
        
        <input hidden id="data" ></input>
        
        <!-- /#logout -->
        <!-- Logout Notification jQuery -->
        <script src="${request.contextPath}/Flex/js/plugins/popupoverlay/logout.js"></script>
        <!-- HISRC Retina Images -->
        <script src="${request.contextPath}/Flex/js/plugins/hisrc/hisrc.js"></script>

    <!-- PAGE LEVEL PLUGIN SCRIPTS -->
    <!-- Date Range Picker -->
        <script src="${request.contextPath}/Flex/js/plugins/daterangepicker/moment.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/daterangepicker/daterangepicker.js"></script>
        <!-- Morris Charts -->
        <script src="${request.contextPath}/Flex/js/plugins/morris/raphael-2.1.0.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/morris/morris.js"></script>
        <!-- Moment.js -->
        <script src="${request.contextPath}/Flex/js/plugins/moment/moment.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/morris/morris-monitTerm.js"></script>
        

    <!-- THEME SCRIPTS -->
        <script src="${request.contextPath}/Flex/js/flex.js"></script>

<!--Data-->
        <script src="${request.contextPath}/Flex/js/demo/energeticosAreaCDemo.js"></script>
        
        
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-tokenfield/bootstrap-tokenfield.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-tokenfield/scrollspy.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-tokenfield/affix.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-tokenfield/typeahead.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-maxlength/bootstrap-maxlength.js"  type="text/javascript"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-timepicker/bootstrap-timepicker.min.js"></script>
        <script src="${request.contextPath}/Flex/js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>

        <script>
            $("#alarmasIcon").click(function() {
                $(".number").hide();
            });
            </script>
        
    </body>

</html>
