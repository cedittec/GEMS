

$(document).ready(function() {

    //Set the initial state of the picker label
    $('#reportrangeperiodo').daterangepicker({
        startDate: moment().subtract('month', 1).startOf('month'),
        endDate: moment(),
        minDate: '01/01/2010',
        maxDate: moment(),
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

function xmlRequest(start, end) {
    //hacer llamada a método para actualizar

    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function()
    {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            $("#arregloEficienciaTermica0").html("0.0");
            $("#arregloEficienciaTermica1").html("0.0");
            $("#arregloEficienciaTermica2").html("0.0");

            $("#arregloCostoEvitadoAlternas0").html("0.0");
            $("#arregloCostoEvitadoAlternas1").html("0.0");
            $("#arregloCostoEvitadoAlternas2").html("0.0");
            $("#arregloCostoEvitadoAlternas4").html("0.0");

            //Falta lo de ccosto de comb.

            $("#unitarioCogeneracionId").html("0.0");
            $("#unitarioTermicoId").html("0.0");

            $("#arregloKWHEnergiaTermica0").html("0.0");
            $("#arregloKWHEnergiaTermica1").html("0.0");
            $("#arregloKWHEnergiaTermica2").html("0.0");


            var data = xmlhttp.responseText;
            xmlDoc = $.parseXML(data);
            $dataXml = $(xmlDoc);
            algo = $dataXml.find('double');
            $("#arregloEficienciaTermica0").html(algo[0].innerHTML + " $");
            $("#arregloEficienciaTermica1").html(algo[1].innerHTML + " $");
            $("#arregloEficienciaTermica2").html(algo[2].innerHTML + " $");

            $("#arregloCostoEvitadoAlternas0").html(algo[3].innerHTML + " $");
            $("#arregloCostoEvitadoAlternas1").html(algo[4].innerHTML + " $");
            $("#arregloCostoEvitadoAlternas2").html(algo[5].innerHTML + " $");
            $("#arregloCostoEvitadoAlternas4").html(algo[6].innerHTML + " $");

            $("#unitarioCogeneracionId").html(algo[7].innerHTML + " $");
            $("#unitarioTermicoId").html(algo[8].innerHTML + " $");

            $("#arregloKWHEnergiaTermica0").html(algo[9].innerHTML + " $");
            $("#arregloKWHEnergiaTermica1").html(algo[10].innerHTML + " $");
            $("#arregloKWHEnergiaTermica2").html(algo[11].innerHTML + " $");


            var arregloMeses = $dataXml.find('string');
            actualizarGrafica(algo,arregloMeses);

        }
    };
    var fechaInicio = start;
    var fechaFinal = end;
    var sitioId = $('#sitiosSelect option:selected').val();
    xmlhttp.open("GET", "actualizaReporteEnergiaTermica?sitioId=" + sitioId + "&fechaInicio=" + fechaInicio + "&fechaFinal=" + fechaFinal, true);
    xmlhttp.send();
}

$('#sitiosSelect').on('change', function() {
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
        xmlhttp = new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange = function()
    {

        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {


            var data = xmlhttp.responseText;
            xmlDoc = $.parseXML(data);
            $dataXml = $(xmlDoc);
            algo = $dataXml.find('long');
            algo2 = $dataXml.find('string');

            if (algo.length < 1)
            {
                $('#contenidoId').hide();
            }
            else {
                for (i = 0; i < algo.length; i++) {
                    $('#fuentesSelect')
                            .append('<option value="' + algo[i].innerHTML + '">' + algo2[i].innerHTML + '</option>')
                            ;
                }
                // xmlRequest();
                $('#contenidoId').show();
            }


        }
    };
    var sitioId = $('#sitiosSelect option:selected').val();
    xmlhttp.open("GET", "getFuentesActivas?sitioId=" + sitioId, true);
    xmlhttp.send();



}

function actualizarGrafica(array, arrayMeses) {

    //PARA ACTUALIZAR LA GRAFICA

    var arreglo1 = [];
    var arreglo2 = [];
    var arreglo3 = [];
    var i;
    var index = 0;
    for (i = 12; i < array.length; i++)
    {
        if (i <= 23) {
            arreglo1[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 23 && i <= 35) {
            arreglo2[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 35 && i <= 47) {
            arreglo3[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }

        index++;
        if ((i == 23) || (i == 35) || (i == 47))
            index = 0;
    }


    //FIN ACTUALIZAR GRAFICA

    $("#costostermicos-chart-bar").empty();
    Morris.Bar({
        element: 'costostermicos-chart-bar',
        data: [
            {y: arrayMeses[0].innerHTML, e: arreglo1[0], t: arreglo2[0], c: arreglo3[0]},
            {y: arrayMeses[1].innerHTML, e: arreglo1[1], t: arreglo2[1], c: arreglo3[1]},
            {y: arrayMeses[2].innerHTML, e: arreglo1[2], t: arreglo2[2], c: arreglo3[2]},
            {y: arrayMeses[3].innerHTML, e: arreglo1[3], t: arreglo2[3], c: arreglo3[3]},
            {y: arrayMeses[4].innerHTML, e: arreglo1[4], t: arreglo2[4], c: arreglo3[4]},
            {y: arrayMeses[5].innerHTML, e: arreglo1[5], t: arreglo2[5], c: arreglo3[5]},
            {y: arrayMeses[6].innerHTML, e: arreglo1[6], t: arreglo2[6], c: arreglo3[6]},
            {y: arrayMeses[7].innerHTML, e: arreglo1[7], t: arreglo2[7], c: arreglo3[7]},
            {y: arrayMeses[8].innerHTML, e: arreglo1[8], t: arreglo2[8], c: arreglo3[8]},
            {y: arrayMeses[9].innerHTML, e: arreglo1[9], t: arreglo2[9], c: arreglo3[9]},
            {y: arrayMeses[10].innerHTML, e: arreglo1[10], t: arreglo2[10], c: arreglo3[10]},
            {y: arrayMeses[11].innerHTML, e: arreglo1[11], t: arreglo2[11], c: arreglo3[11]}
        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c'],
        yLabelFormat: function(y) {
            return y + " $/kWh";
        },
        labels: ['S. Cogeneración', 'S. Térmico', 'Colector Solar'],
        barColors: ['#f39c12', '#e74c3c', '#16a085'],
        resize: true
    });
}


 $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );               
                
        var txt = $("#arregloCostoEvitadoAlternas0").html().split("$");
        $("#idCostoAlternas").val( txt[0].trim() );
        txt = $("#arregloCostoEvitadoAlternas1").html().split("$");
        $("#idCostoCogeneracion").val( txt[0].trim() );         
         txt = $("#arregloCostoEvitadoAlternas2").html().split("$");
        $("#idCostoTermico").val( txt[0].trim() );           
        txt = $("#arregloCostoEvitadoAlternas3").html().split("$");
        $("#idCostoSolar").val( txt[0].trim() );
        
        txt = $("#arregloKWHEnergiaTermica0").html().split("$");
        $("#idCargoCogeneracion").val( txt[0].trim() );         
         txt = $("#arregloKWHEnergiaTermica1").html().split("$");
        $("#idCargoTermico").val( txt[0].trim() );   
          txt = $("#arregloKWHEnergiaTermica2").html().split("$");
        $("#idCargoSolar").val( txt[0].trim() ); 
        
          txt = $("#unitarioCogeneracionId").html().split("$");
        $("#idPrecioCogeneracion").val( txt[0].trim() );         
         txt = $("#unitarioTermicoId").html().split("$");
        $("#idPrecioTermico").val( txt[0].trim() );   
         // txt = $("#arregloCostoEvitadoAlternas3").html().split(" ");
        $("#idPrecioSolar").val( "0.0"); 
        
        
        txt = $("#arregloEficienciaTermica0").html().split("%");
        $("#idEficienciaTermica").val( txt[0].trim() );         
        txt = $("#arregloEficienciaTermica1").html().split("kWh");
        $("#idEnergiaCogeneracion").val( txt[0].trim() );         
         txt = $("#arregloEficienciaTermica2").html().split("kWh");
        $("#idEnergiaTermico").val( txt[0].trim() );   
        //txt = $("#arregloEficienciaTermica3").html().split(" ");
        $("#idEnergiaSolar").val( "0.0" ); 
        
        
         
        $(".jasperButton").trigger('click');                 
      
    });  
    
    $(".jasperReport").hide();