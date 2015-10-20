

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
                    $("#nievelTanqueTermico").html("0.0");
                    $("#nivelTanqueCogeneracionx").html("0.0");
                    $("#tempCombustibleTermico").html("0.0");
                    $("#tempCombustibleCogeneracion").html("0.0");
                    $("#presionCombustibleTermico").html("0.0");
                    $("#presionCombustibleCogeneracion").html("0.0");
                   
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');                                     
                    
                    $("#nievelTanqueTermico").html(algo[0].innerHTML + " %");
                    $("#tempCombustibleTermico").html(algo[1].innerHTML + " °C");
                    //$("#nivelTanqueCogeneracionx").html(algo[1].innerHTML + " kW");
                    $("#tempCombustibleCogeneracion").html(algo[2].innerHTML + " °C");
                    $("#presionCombustibleTermico").html(algo[3].innerHTML + " Kg/cm^2");
                    $("#presionCombustibleCogeneracion").html(algo[4].innerHTML + " Kg/cm^2");
                    
                    
                    var sitioId = $('#sitiosSelect option:selected').val();
                    var fuenteId = $('#fuentesSelect option:selected').val();
                    //refreshHistoricChart(sitioId, start, end);
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            var fuenteId = $('#fuentesSelect option:selected').val();
            xmlhttp.open("GET","reporteActualizaCombustibles?sitioId="+sitioId+"&fuenteId="+fuenteId,true);
            xmlhttp.send();
    }
    
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
    
    
    
    