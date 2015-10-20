

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
                    $("#tempTanqueInercia").html("0.0");
                    $("#potenciaGenerada").html("0.0");
                    $("#consumoTermico").html("0.0");
                    $("#consumoCogeneracion").html("0.0");
                    $("#radiacionSolar").html("0.0");
                    
                    $("#nivelesHumedad").html("0.0");
                    $("#temp").html("0.0");
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');                                     
                    
                    $("#tempTanqueInercia").html(algo[0].innerHTML);
                    $("#potenciaGenerada").html(algo[1].innerHTML + " kW");
                    $("#consumoTermico").html(" Consumo sistema T&eacute;mico: " + algo[2].innerHTML);                   
                    $("#consumoCogeneracion").html(" Consumo sistema Cogeneracion: " + algo[3].innerHTML); 
                    $("#radiacionSolar").html(algo[4].innerHTML + " W/m^2");
                    
                    $("#nivelesHumedad").html(algo[5].innerHTML + " %");
                    $("#temp").html(algo[6].innerHTML + " &#176;C");
                    
                    
                    var sitioId = $('#sitiosSelect option:selected').val();
                    var fuenteId = $('#fuentesSelect option:selected').val();
                    //refreshHistoricChart(sitioId, start, end);
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            var fuenteId = $('#fuentesSelect option:selected').val();
            xmlhttp.open("GET","reporteActualizaIndex?sitioId="+sitioId+"&fuenteId="+fuenteId,true);
            xmlhttp.send();
    }
    
    setInterval(function(){  xmlRequest(); }, 15000);
    
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
    
    $('#sitiosSelect').on('change', function(){   
        
       
        cambiarFuentesSitio();
          
        
    });
    
     $('#fuentesSelect').on('change', function(){
        xmlRequest();   
        
    });
    
    $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );               
                
        
        var txt = $("#radiacionSolar").html();        
        $("#idradiacion").val( txt.trim() );                
        var txt = $("#nivelesHumedad").html();
        $("#idhumedad").val( txt.trim() );                
         var txt = $("#temp").html();
        $("#idtemperatura").val( txt.trim() );                                
        
         var txt = $("#tempTanqueInercia").html();
        $("#idtempTanque").val( txt.trim() );              
        var txt = $("#potenciaGenerada").html();
        $("#idpotencia").val( txt.trim() );  
        var txt = $("#consumoCogeneracion").html();
        $("#idnivel").val( txt.trim() );  
                
         
        $(".jasperButton").trigger('click');                 
      
    });  
    
  $(".jasperReport").hide();
    
    
    
    