

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
                    
                    //$("#tempTanqueInercia").html("0.0");
                    //$("#tempTanqueServicio").html("0.0");
                    //$("#tempTanqueTermico").html("0.0");
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    var algo = $dataXml.find('double');      
                   
                    $("#nievelTanqueTermico").html(algo[0].innerHTML);
                    $("#tempCombustibleTermico").html(algo[1].innerHTML + " &#176;C");
                    $("#tempCombustibleCogeneracion").html(algo[2].innerHTML + " &#176;C");
                    $("#presionCombustibleTermico").html(algo[3].innerHTML + " Kg/cm^2");
                    $("#presionCombustibleCogeneracion").html(algo[4].innerHTML + " Kg/cm^2");
                    
                    actualizarGraficas(algo);
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            var fuenteId = $('#fuentesSelect option:selected').val();
            xmlhttp.open("GET","reporteActualizaCombustibles?sitioId="+sitioId+"&fuenteId="+fuenteId,true);
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
   
    $("#monitoreoFlujoCombustibles-chart-bar").empty();
    
    Morris.Bar({
            element: 'monitoreoFlujoCombustibles-chart-bar',
            data: [
              { y:  'Sistema Termico', a: array[5].innerHTML },
              { y:  'Sistema de Cogeneracion', a: array[6].innerHTML}             
            ],
            xkey: 'y',
            ykeys: ['a'],
             yLabelFormat: function(y) {
            return  y + " m^3/h";
            },
            labels: ['Flujo del combustible'],
            barColors: ['#16a061'],
            resize: true
            });
    
            
            
        }
     