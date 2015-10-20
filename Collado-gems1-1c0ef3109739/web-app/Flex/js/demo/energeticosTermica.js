

    $(document).ready(function() {
        //Set the initial state of the picker label
        $('#reportrangeperiodo').daterangepicker({
            startDate: moment().subtract('month', 1).startOf('month'),
            endDate: moment(),
            minDate: '01/01/2010',
            maxDate:  moment(),
            dateLimit: {
                days: 266
            },
            showDropdowns: true,
            showWeekNumbers: true,
            timePicker: false,
            timePickerIncrement: 1,
            timePicker12Hour: true,
            ranges: {  
            'Este Bimestre': [moment().subtract('month', 1).startOf('month'), moment()],
            'Este Año': [moment().startOf('year'), moment()]
            },
            opens: 'left',
            buttonClasses: ['btn btn-default'],
            applyClass: 'btn-small btn-primary',
            cancelClass: 'btn-small',
            format: 'MM/DD/YYYY',
            separator: ' to ',
            locale: {
                applyLabel: 'Exportar',
                fromLabel: 'De',
                toLabel: 'A',
                customRangeLabel: 'Rango personalizado',
                daysOfWeek: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                firstDay: 1
            }
        },
        function(start, end) {
            xmlRequest(start, end);

            $('#reportrangeperiodo span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        });

        $('#reportrangeperiodo span').html(moment().subtract('month', 1).startOf('month').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
    });
    
    function xmlRequest(start, end){
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
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');
                    console.log(algo);
                    $("#termicaGenerada").html(algo[0].innerHTML + " kWt");
                    $("#termicaCogeneracion").html(algo[1].innerHTML + " kWt");
                    $("#termicaTermico").html(algo[2].innerHTML + " kWt");
                    $("#cogeneracionAprovechada").html(algo[3].innerHTML + " kWt");
                    $("#cogeneracionTanqueInercia").html(algo[4].innerHTML + " kWt");
                    $("#cogeneracionTanqueInercia2").html(algo[5].innerHTML + " kWt");
                    $("#cogeneracionTanqueServicios").html(algo[6].innerHTML + " kWt");
                    $("#cogeneracionVapor").html(algo[7].innerHTML + " kWt");
                    $("#cogeneracionEconomizador").html(algo[8].innerHTML + " kWt");
                    $("#eficienciaTermica").html(algo[9].innerHTML + " %");
                    $("#eficienciaCogeneracion").html(algo[10].innerHTML + " kWt");
                    $("#eficienciaTermico").html(algo[11].innerHTML + " kWt");
                    $("#eficienciaColectorSolar").html(algo[12].innerHTML + " kWt");
                    
                      var arregloMeses = $dataXml.find('string');                                        
                    actualizarGrafica(algo,arregloMeses);
                    
                }
            };
            var fechaInicio = start;
            var fechaFinal = end;
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","actualizaEnergiaTermica?sitioId="+sitioId+"&fechaInicio="+fechaInicio+"&fechaFinal="+fechaFinal,true);
            xmlhttp.send();
    }
    
  function actualizarGrafica(array,arrayMeses){
    
    //PARA ACTUALIZAR LA GRAFICA    
    var arreglo1 = [];
    var arreglo2 = [];    
    var i;
    var index = 0;
    for (i = 14; i < array.length; i++)
    {
        if (i <= 25) {
            arreglo1[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 25 && i <= 37) {
            arreglo2[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }

        index++;
        if ((i == 25) || (i == 37) )
            index = 0;
    }
    

    //FIN ACTUALIZAR GRAFICA

    $("#energeticostermicos-chart-bar").empty();
    Morris.Bar({
        element: 'energeticostermicos-chart-bar',
        data: [
           { y:  arrayMeses[0].innerHTML, e: arreglo1[0],  t: arreglo2[0] },
              { y:  arrayMeses[1].innerHTML, e: arreglo1[1],  t: arreglo2[1] },
              { y:  arrayMeses[2].innerHTML, e: arreglo1[2],  t: arreglo2[2] },
              { y:  arrayMeses[3].innerHTML, e: arreglo1[3],  t: arreglo2[3] },
              { y:  arrayMeses[4].innerHTML, e: arreglo1[4],  t: arreglo2[4] },
              { y:  arrayMeses[5].innerHTML, e: arreglo1[5],  t: arreglo2[5] },
              { y:  arrayMeses[6].innerHTML, e: arreglo1[6], t: arreglo2[6] },
              { y:  arrayMeses[7].innerHTML, e: arreglo1[7], t: arreglo2[7] },
              { y:  arrayMeses[8].innerHTML, e: arreglo1[8], t: arreglo2[8] },
              { y:  arrayMeses[9].innerHTML, e: arreglo1[9], t: arreglo2[9] },
              { y:  arrayMeses[10].innerHTML, e: arreglo1[10], t: arreglo2[10] },
              { y:  arrayMeses[11].innerHTML, e: arreglo1[11], t: arreglo2[11] }
        ],
        xkey: 'y',
        ykeys: ['e', 't'],
        labels: ['Sistema Cog.', 'Sistema Termico'],
        barColors: ['#f39c12', '#e74c3c', '#16a085'],
        resize: true
    });
}
    
  function cambiarFuentesSitio()
    {
       
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
                     // xmlRequest();
                      $('#contenidoId').show();
                    }
                    
                    
                }
            };
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","getFuentesActivas?sitioId="+sitioId,true);
            xmlhttp.send();
        
        
        
    }
    
  $('#sitiosSelect').on('change', function(){
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment();
        xmlRequest(before, now);
         cambiarFuentesSitio();
        $('#reportrangeperiodo span').html(moment().subtract('month', 1).startOf('month').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
        $('div.ranges > ul li.active').removeClass('active');
        $('div.ranges > ul li:first-child').addClass('active');
        
    });
    
    $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );               
                
        
        var txt = $("#termicaGenerada").html().replace("kWt","");        
        $("#idgenerada").val( txt.trim() );        
        
        var txt = $("#termicaCogeneracion").html().replace("kWt","");        
        $("#idcogeneracion").val( txt.trim() );        
        
         var txt = $("#termicaTermico").html().replace("kWt","");        
        $("#idtermico").val( txt.trim() );                                
        
         var txt = $("#cogeneracionTanqueInercia").html().replace("kWt","");        
        $("#idaireAgua").val( txt.trim() );      
        
        var txt = $("#cogeneracionTanqueInercia2").html().replace("kW","");        
        $("#idaguaAgua").val( txt.trim() );      
        
        var txt = $("#cogeneracionTanqueServicios").html().replace("kWt","");        
        $("#idaguaAgua2").val( txt.trim() );      
        
        var txt = $("#cogeneracionVapor").html().replace("kWt","");        
        $("#idvapor").val( txt.trim() );      
        
        var txt = $("#cogeneracionEconomizador").html().replace("kWt","");        
        $("#ideconomizador").val( txt.trim() );      
        
        var txt = $("#eficienciaTermica").html().replace("%","");        
        $("#ideficiencia").val( txt.trim() );      
        
        var txt = $("#eficienciaCogeneracion").html().replace("kWt","");        
        $("#idcogeneracion2").val( txt.trim() );      
        
        var txt = $("#eficienciaTermico").html().replace("kWt","");        
        $("#idtermico2").val( txt.trim() );      
        
        var txt = $("#eficienciaColectorSolar").html().replace("kWt","");        
        $("#idcolector").val( txt.trim() );      
                
         
        $(".jasperButton").trigger('click');                 
      
    });  
    
  $(".jasperReport").hide();