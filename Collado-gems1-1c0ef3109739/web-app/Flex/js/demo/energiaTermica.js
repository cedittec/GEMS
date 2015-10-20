

    function xmlRequest(){
        //hacer llamada a método para actualizar
        
            var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            
            xmlhttp.onreadystatechange=function()
            {
                
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                    //$(selector).attr('data-percent', valor);
                    
                    $("#tempTanqueInercia").html("0.0");
                    $("#tempTanqueServicio").html("0.0");
                    $("#tempTanqueTermico").html("0.0");
                    $("#tempTanqueCondensados").html("0.0");
                    $("#tempTanqueVapor").html("0.0");                    
                    $("#posicionDiverter").html("0.0");
                    $("#tempAireEntrada").html("0.0");
                    $("#tempAireSalida").html("0.0");
                    $("#flujoEconomizador").html("0.0");
                    $("#flujoVaporizador").html("0.0");
                    $("#flujoSupercalentador").html("0.0");
                    $("#nivelTanqueServicios").html("0.0");
                    $("#nivelTanqueInercia").html("0.0");
                    $("#nivelTanqueCondensados").html("0.0");
                    $("#nivelTanqueVapor").html("0.0");
                    
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    var algo = $dataXml.find('double');      
                    
                    $("#").html(algo[0].innerHTML + " kW");
                    $("#").html(algo[1].innerHTML + " °C");
                    
                    
                     $("#tempTanqueInercia").html(algo[0].innerHTML + " °C");
                    $("#tempTanqueServicio").html(algo[1].innerHTML + " °C");
                    $("#tempTanqueTermico").html(algo[2].innerHTML + " °C");
                    $("#tempTanqueCondensados").html(algo[3].innerHTML + " °C");
                    $("#tempTanqueVapor").html(algo[4].innerHTML + " °C");        
                    $("#posicionDiverter").html(algo[5].innerHTML + " %");
                    $("#tempAireEntrada").html(algo[6].innerHTML + " °C");
                    $("#tempAireSalida").html(algo[7].innerHTML + " °C");
                    $("#flujoEconomizador").html(algo[8].innerHTML + " m^3/h");
                    $("#flujoVaporizador").html(algo[9].innerHTML + " m^3/h");
                    $("#flujoSupercalentador").html(algo[10].innerHTML + " m^3/h");
                    $("#nivelTanqueServicios").html(algo[11].innerHTML + " %");
                    $("#nivelTanqueInercia").html(algo[12].innerHTML + " %");
                    $("#nivelTanqueCondensados").html(algo[13].innerHTML + " %");
                    $("#nivelTanqueVapor").html(algo[14].innerHTML + " %");
                    
                   
                    actualizarGraficas(algo);
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            var fuenteId = $('#fuentesSelect option:selected').val();
            xmlhttp.open("GET","reporteActualizaEnergiaTermica?sitioId="+sitioId+"&fuenteId="+fuenteId,true);
            xmlhttp.send();
    }
    
    $( document ).ready(function() {
    
});
    
    function cambiarFuentesSitio()
    {
        $('#fuentesSelect')
        .find('option')
        .remove()
        .end()
        ;
         var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
        
         xmlhttp.onreadystatechange=function()
            {
                
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                  
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('long');                                     
                    algo2 = $dataXml.find('string');      
                    
                    if(algo.length < 1)
                    {
                        $('#contenidoId').hide();
                    }
                    else{
                    for(i=0; i<algo.length; i++){
                        $('#fuentesSelect')                        
                            .append('<option value="'+algo[i].innerHTML+'">'+algo2[i].innerHTML+'</option>')
                        ;
                    }
                      xmlRequest();
                      $('#contenidoId').show();
                    }
                    
                    
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","getFuentesActivas?sitioId="+sitioId,true);
            xmlhttp.send();
        
        
        
    }
    setInterval(function(){  xmlRequest(); }, 15000);
    
    $('#sitiosSelect').on('change', function(){           
       
        cambiarFuentesSitio();
         
        
    });
    
     $('#fuentesSelect').on('change', function(){
        xmlRequest();   
        
    });
    
    
    function actualizarGraficas(array){
   
    $("#monitoreoTermicoFlujoAguaCog-chart-bar").empty();
    $("#monitoreoTermicoTempEntradaCog-chart-bar").empty();
    $("#monitoreoTermicoTempAgua-chart-bar").empty();
    $("#monitoreoTermicoPresionTanque-chart-bar").empty();
    
    Morris.Bar({
            element: 'monitoreoTermicoFlujoAguaCog-chart-bar',
            data: [
              { y:  'Sistema Termico', a: array[15].innerHTML },
              { y:  'Intercambiador T. inercia', a: array[16].innerHTML },
              { y:  'Placas T. de Inercia',a: array[17].innerHTML },
              { y:  'Placas T. de Servicio', a: array[18].innerHTML }             
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
            
    Morris.Bar({
            element: 'monitoreoTermicoTempEntradaCog-chart-bar',
            data: [
              { y:  'Economizador', a: array[19].innerHTML, b:array[20].innerHTML },
              { y:  'Vaporizador', a: array[21].innerHTML, b:array[22].innerHTML },
              { y:  'Supercalentador', a: array[23].innerHTML, b:array[24].innerHTML }
             
            ],
            xkey: 'y',
            ykeys: ['a','b'],
             yLabelFormat: function(y) {
            return  y + " °C";
            },
            labels: ['Temp. Entrada' , 'Temp de salida'],
            barColors: ['#f39c12'],
            resize: true
            });
            
            
    Morris.Bar({
            element: 'monitoreoTermicoTempAgua-chart-bar',
            data: [
              { y:  'Int. del S. deCogeneración', a: array[25].innerHTML, b:array[26].innerHTML },
              { y:  'T.Servicios Cogeneración', a: array[27].innerHTML, b:array[28].innerHTML },
              { y:  'T.Inercia Cogeneración', a: array[29].innerHTML, b:array[30].innerHTML  }
             
            ],
            xkey: 'y',
            ykeys: ['a','b'],
            yLabelFormat: function(y) {
            return  y + " °C";
            },
            labels: ['Temp. Agua Entrada' , 'Temp. Agua Salida'],
            barColors: ['#2980b9','#34495e'],
            resize: true
            });
            
    Morris.Bar({
            element: 'monitoreoTermicoPresionTanque-chart-bar',
            data: [
              { y:  'Tanque Servicios',  a: array[31].innerHTML },
              { y:  'Tanque Inercia',  a: array[32].innerHTML },
              { y:  'Tanque de Condensados',  a: array[33].innerHTML },
              { y:  'Tanque de Presion',  a: array[34].innerHTML },
              { y:  'Tuberia de vapor',  a: array[35].innerHTML }
             
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
            
            
        }
        
        $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fuenteIdPDF").val( $('#fuentesSelect').val() );   
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );                                               
        $(".jasperButton").trigger('click');                 
      
    });  
    
  $(".jasperReport").hide();