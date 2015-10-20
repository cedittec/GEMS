

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
                applyLabel: 'Consultar',
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
                    $("#cogeneracion0").html("0.0");
                    $("#cogeneracion1").html("0.0");
                    
                    $("#termico0").html("0.0");
                    $("#termico1").html("0.0");
                    
                    $("#combustible0").html("0.0");
                    $("#combustible1").html("0.0");
                    $("#combustible2").html("0.0");
                    
                    $("#tipoCombCogeneracion").html("");
                    $("#tipoCombTermico").html("");
                    
                    
                    $("#tipoTarifa").html();
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');                    
                    
                     $("#cogeneracion0").html(algo[0].innerHTML + " $");
                    $("#cogeneracion1").html(algo[1].innerHTML + " $");
                    
                    $("#termico0").html(algo[2].innerHTML + " $");
                    $("#termico1").html(algo[3].innerHTML + " $");
                    
                    $("#combustible0").html(algo[4].innerHTML + " $");
                    $("#combustible1").html(algo[5].innerHTML + " $");
                    $("#combustible2").html(algo[6].innerHTML + " $");
                    
                    
                    algo2 = $dataXml.find('string');
                    $("#tipoCombCogeneracion").html(algo2[0].innerHTML + "");
                    $("#tipoCombTermico").html(algo2[1].innerHTML + "");
                    
                    actualizarGrafica(algo,algo2);
                }
            };
            var fechaInicio = start;
            var fechaFinal = end;
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","actualizaReporteCostoCombustibles?sitioId="+sitioId+"&fechaInicio="+fechaInicio+"&fechaFinal="+fechaFinal,true);
            xmlhttp.send();
    }
    
     function actualizarGrafica(array,arrayMeses){
    
    //PARA ACTUALIZAR LA GRAFICA
    var meses = [];
    var arreglo1 = [];
    var arreglo2 = [];
    var arreglo3 = [];
    var i;
    var index = 0;
    for (i = 9; i < array.length; i++)
    {
        if (i <= 20) {
            arreglo1[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 20 && i <= 32) {
            arreglo2[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 32 && i <= 44) {
            arreglo3[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }

        index++;
        if ((i == 20) || (i == 32) || (i == 44))
            index = 0;
    }
    

    //FIN ACTUALIZAR GRAFICA

    $("#costoscombustibles-chart-bar").empty();
    Morris.Bar({
        element: 'costoscombustibles-chart-bar',
        data: [
           { y:  arrayMeses[2].innerHTML, e: arreglo1[0],  t: arreglo2[0], c: arreglo3[0] },
              { y:  arrayMeses[3].innerHTML, e: arreglo1[1],  t: arreglo2[1], c: arreglo3[1] },
              { y:  arrayMeses[4].innerHTML, e: arreglo1[2],  t: arreglo2[2], c: arreglo3[2] },
              { y:  arrayMeses[5].innerHTML, e: arreglo1[3],  t: arreglo2[3], c: arreglo3[3] },
              { y:  arrayMeses[6].innerHTML, e: arreglo1[4],  t: arreglo2[4], c: arreglo3[4] },
              { y:  arrayMeses[7].innerHTML, e: arreglo1[5],  t: arreglo2[5], c: arreglo3[5] },
              { y:  arrayMeses[8].innerHTML, e: arreglo1[6], t: arreglo2[6], c: arreglo3[6] },
              { y:  arrayMeses[9].innerHTML, e: arreglo1[7], t: arreglo2[7], c: arreglo3[7] },
              { y:  arrayMeses[10].innerHTML, e: arreglo1[8], t: arreglo2[8], c: arreglo3[8] },
              { y:  arrayMeses[11].innerHTML, e: arreglo1[9], t: arreglo2[9], c: arreglo3[9] },
              { y:  arrayMeses[12].innerHTML, e: arreglo1[10], t: arreglo2[10], c: arreglo3[10] },
              { y:  arrayMeses[13].innerHTML, e: arreglo1[11], t: arreglo2[11], c: arreglo3[11] }
        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c'],
        labels: ['Costo combustible sist. Cog.', 'Costo combustible sist. Term.', 'Costo Total Combustible'],
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
                
        var txt = $("#cogeneracion0").html().split("m");
        $("#idLecturaInicial").val( txt[0].trim() );
        txt = $("#cogeneracion1").html().split("m");
        $("#idLecturaFinal").val( txt[0].trim() );    
        
        
         txt = $("#termico0").html().split("m");
        $("#idLecturaInicial2").val( txt[0].trim() );           
        txt = $("#termico1").html().split("m");
        $("#idLecturaFinal2").val( txt[0].trim() );
        
        txt = $("#combustible0").html().split("$");
        $("#idSistemaCogeneracion").val( txt[0].trim() );         
         txt = $("#combustible1").html().split("$");
        $("#idSistemaTermico").val( txt[0].trim() ); 
        
        
        //  txt = $("#arregloKWHEnergiaTermica2").html().split("$");
        $("#idCargoGas").val( "No disp." ); 
        //  txt = $("#unitarioCogeneracionId").html().split("$");
        $("#idCargoDiesel").val( "No disp."  ); 
        
        
        // txt = $("#arregloKWHEnergiaTermica2").html().split("$");
        $("#idCalorGas").val( "No disp."  ); 
        //  txt = $("#unitarioCogeneracionId").html().split("$");
        $("#idCalorDiesel").val( "No disp."  ); 
        
        $(".jasperButton").trigger('click');                 
      
    });  
    
    $(".jasperReport").hide();