var firstTime =true;

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
        refreshDonut();
    });
        
    //REQUEST
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
                    $("#costosGlobalesId").html("0.0");
                    $("#ahorrosGlobalesId").html("0.0");
                    $("#flujoEfectivoAcumuladoId").html("0.0");
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');
                    $("#costosGlobalesId").html(algo[0].innerHTML + " $");
                    $("#ahorrosGlobalesId").html(algo[1].innerHTML + " $");
                    $("#flujoEfectivoAcumuladoId").html(algo[2].innerHTML + " $");
                    
                    
                    var arregloMeses = $dataXml.find('string');   
                    actualizarGrafica(algo,arregloMeses);
                    refreshDonut();
                }
            };
            var fechaInicio = start;
            var fechaFinal = end;
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","actualizaReporteIndex?sitioId="+sitioId+"&fechaInicio="+fechaInicio+"&fechaFinal="+fechaFinal,true);
            xmlhttp.send();
    }
    
     function refreshDonut(){
        $("#morris-chart-donut").empty();
        //--------------- Cogeneracion
        //obtener dato del div
        var cogSis = $('#costosGlobalesId').html();
        //recortar caracteres en blanco
        cogSis = $.trim(cogSis).split(" ");        
        //hacer valor negativo a positivo
        cogSis = (cogSis[0]<0)?(Number(cogSis[0])*(-1)): cogSis[0];
        
        //-------------- Fotovoltaico
        //obtener dato del div
        var fotoSis = $('#ahorrosGlobalesId').html();
        //recortar caracteres en blanco
        fotoSis = $.trim(fotoSis).split(" ");
        //hacer valor negativo a positivo
        fotoSis = (fotoSis[0]<0)? (Number(fotoSis[0])*(-1)): fotoSis[0];
        
        //-------------- Colector Solar
        //obtener dato del div
        var colSolar = $('#flujoEfectivoAcumuladoId').html();
        
        colSolar = $.trim(colSolar).split(" ");       
        
        //hacer valor negativo a positivo
        colSolar = (colSolar[0]<0)? (colSolar[0]*(-1)): colSolar[0];
        
        var total = Number(cogSis) + Number(fotoSis) + Number(colSolar);

        var cog = ((cogSis ) / total )* 100;
        var fot = ((fotoSis) / total )* 100;
        var colector = ((colSolar) / total )* 100;
        
       
        
        Morris.Donut({            
        element: 'morris-chart-donut',
        data: [
        {label: "Costos Globales", value:cog},
        {label: "Ahorros Globales", value:fot},
        {label: "Efectivo acumulado", value: colector},
        ],
        resize: true,
        colors: ['#16a085','#f39c12', '#f1c40f'],
        formatter: function (y) { return y + "%" ;}
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
    
    function actualizarGrafica(array,arrayMeses){
    
    //PARA ACTUALIZAR LA GRAFICA    
    var arreglo1 = [];
    var arreglo2 = [];
    var i;
    var index = 0;
    for (i = 3; i < array.length; i++)
    {
        if (i <= 14) {
            arreglo1[index] = array[i].innerHTML;           
        }
        if (i > 14 && i <= 26) {
            arreglo2[index] = array[i].innerHTML;
        }
        index++;
        if ((i == 14) || (i == 26) || (i == 38))
            index = 0;
    }
    

    //FIN ACTUALIZAR GRAFICA

    $("#morris-chart-dashboard").empty();
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
    element: 'morris-chart-dashboard',
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
    labels: ['Evitado Energias Alternas', 'Evitado energia Termica'],
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
                
        var txt = $("#costosGlobalesId").html().split("$");
        $("#costoGlobal").val( txt[0].trim() );
        
        txt = $("#ahorrosGlobalesId").html().split("$");
        $("#ahorroGlobal").val( txt[0].trim() );         
         txt = $("#flujoEfectivoAcumuladoId").html().split("$");
        $("#efectivoAcumulado").val( txt[0].trim() );               
        $(".jasperButton").trigger('click');
        
      
    });  
    
    $(".jasperReport").hide();
        