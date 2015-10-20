

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
                    $("#evitadoCogeneracion0").html("0.0");
                    $("#evitadoCogeneracion1").html("0.0");
                    $("#evitadoCogeneracion2").html("0.0");
                    $("#evitadoCogeneracion3").html("0.0");
                    
                    $("#evitadoFotovoltaico0").html("0.0");
                    $("#evitadoFotovoltaico1").html("0.0");
                    $("#evitadoFotovoltaico2").html("0.0");
                    $("#evitadoFotovoltaico3").html("0.0");
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');                    
                    
                    $("#evitadoCogeneracion0").html(algo[0].innerHTML + " $");
                    $("#evitadoCogeneracion1").html(algo[1].innerHTML + " $");                   
                    $("#evitadoCogeneracion2").html(algo[2].innerHTML + " $");
                    $("#evitadoCogeneracion3").html(algo[3].innerHTML + " $");
                    
                    $("#evitadoFotovoltaico0").html(algo[4].innerHTML + " $");
                    $("#evitadoFotovoltaico1").html(algo[5].innerHTML + " $");
                    $("#evitadoFotovoltaico2").html(algo[6].innerHTML + " $");
                    $("#evitadoFotovoltaico3").html(algo[7].innerHTML + " $");
                    var meses= $dataXml.find('string');          
                    actualizarGrafica(algo,meses)
                }
            };
            var fechaInicio = start;
            var fechaFinal = end;
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","actualizaReporteEnergiaElectrica?sitioId="+sitioId+"&fechaInicio="+fechaInicio+"&fechaFinal="+fechaFinal,true);
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
    
     function actualizarGrafica(array,arrayMeses){
    
    //PARA ACTUALIZAR LA GRAFICA    
    var arreglo1 = [];
    var arreglo2 = [];
    var i;
    var index = 0;
    for (i = 8; i < array.length; i++)
    {
        if (i <= 19) {
            arreglo1[index] = array[i].innerHTML;           
        }
        if (i > 19 && i <= 31) {
            arreglo2[index] = array[i].innerHTML;
        }
        index++;
        if ((i == 19) || (i == 31))
            index = 0;
    }
    

    //FIN ACTUALIZAR GRAFICA

    $("#ahorrosMorrisArea").empty();
    //Morris Area Chart  
var sales_data = [{
    date: arrayMeses[0].innerHTML,
    productA: arreglo1[0],
    productB: arreglo2[0]
},
{
    date: arrayMeses[1].innerHTML,
    productA: arreglo1[1],
    productB: arreglo2[1]
},
{
    date: arrayMeses[2].innerHTML,
    productA: arreglo1[2],
    productB: arreglo2[2]
},
{
     date: arrayMeses[3].innerHTML,
    productA: arreglo1[3],
    productB: arreglo2[3]
},
{
    date: arrayMeses[4].innerHTML,
    productA: arreglo1[4],
    productB: arreglo2[4]
},
{
    date: arrayMeses[5].innerHTML,
    productA: arreglo1[5],
    productB: arreglo2[5]
},
{
     date: arrayMeses[6].innerHTML,
    productA: arreglo1[6],
    productB: arreglo2[6]
},
{
     date: arrayMeses[7].innerHTML,
    productA: arreglo1[7],
    productB: arreglo2[7]
},
{
    date: arrayMeses[8].innerHTML,
    productA: arreglo1[8],
    productB: arreglo2[8]
},
{
    date: arrayMeses[9].innerHTML,
    productA: arreglo1[9],
    productB: arreglo2[9]
},
{
     date: arrayMeses[10].innerHTML,
    productA: arreglo1[10],
    productB: arreglo2[10]
},
{
     date: arrayMeses[11].innerHTML,
    productA: arreglo1[11],
    productB: arreglo2[11]
}];

Morris.Area({
        element: 'ahorrosMorrisArea',
        data: sales_data,
        xkey: 'date',
        xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
        },
        xLabels: 'day',
        ykeys: ['productA', 'productB'],
        yLabelFormat: function(y) {
        return "$" + y;
        },
        labels: ['Evitado energia total Cog.', 'Evitado en base energia total fotovoltaico'],
        lineColors: ['#fff', '#fff', '#fff'],
        hideHover: 'auto',
        resize: true,
        gridTextFamily: ['Open Sans'],
        gridTextColor: ['rgba(255,255,255,0.7)'],
        fillOpacity: 0.1,
        pointSize: 0,
        smooth: true,
        lineWidth: 2,
        grid: true,
        dateFormat: function(date) {
        d = new Date(date);
        return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
        },
        });
}

 $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );               
                
        var txt = $("#evitadoCogeneracion0").html().split("$");
        $("#idsistemaCogneracion").val( txt[0].trim() );
        txt = $("#evitadoCogeneracion1").html().split("$");
        $("#idenergiaBase").val( txt[0].trim() );         
        
         txt = $("#evitadoCogeneracion2").html().split("$");
        $("#idenergiaIntermedia").val( txt[0].trim() );           
        txt = $("#evitadoCogeneracion3").html().split("$");
        $("#idenergiaPunta").val( txt[0].trim() );
        
        txt = $("#evitadoFotovoltaico0").html().split("$");
        $("#idsistemaFotovoltaico").val( txt[0].trim() );         
         txt = $("#evitadoFotovoltaico1").html().split("$");
        $("#idenergiaBase2").val( txt[0].trim() );    
        
        txt = $("#evitadoFotovoltaico2").html().split("$");
        $("#idenergiaIntermedia2").val( txt[0].trim() );         
         txt = $("#evitadoFotovoltaico3").html().split("$");
        $("#idenergiaPunta2").val( txt[0].trim() );            
                
         
        $(".jasperButton").trigger('click');                 
      
    });  
    
  $(".jasperReport").hide();