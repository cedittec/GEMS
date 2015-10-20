var before = moment().subtract('month', 1).startOf('month');
var now = moment();

$('#dateInput').change(function(event)
{
    now = this.value;
    var aux = now.split("-");
    var fi = (parseInt(aux[0])-1)+"-"+aux[1]+"-"+aux[2]; //fi = fecha inicial
        
    if(document.getElementById("pdf").checked)
    {
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( fi ); 
        $("#fechaFinPDF").val( now );               

        var txt = $("#radiacionSolarVal").html().split("kWh");
        $("#energiaElectrica").val( txt[0].trim() );

        txt = $("#entalpia").html().split("kWh");
        $("#energiaTermica").val( txt[0].trim() );     

        txt = $("#combustible").html().split("kWh");
        $("#combustiblePDF").val( txt[0].trim() );   

        $(".jasperButton").trigger('click');
        
        $('#reportrangeperiodo span').html(now);
    }
    else
    {
        window.location.href = "index.gsp?fechaInicio="+fi+"&fechaFinal="+now;
        
        $('#reportrangeperiodo span').html(fi +" - "+now);
    }
});

$(document).ready(function() 
{
    /*
        $('#reportrangeperiodoUnico').datepicker({
                changeMonth: true,
                dateFormat: 'dd-mm-yy',
                 onSelect: function(dateText)
                {
                    alert("Selected date: " + dateText);
                }
            });
        
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
        function(start, end) 
        {
            
            before = start.format('MM DD YYYY');            
            now = end.format('MM DD YYYY');
            $('#reportrangeperiodo span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        });
*/

    var query_string = {};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    
    for (var i=0; i<vars.length; i++) 
    {
        var pair = vars[i].split("=");
        // If first entry with this name
        if (typeof query_string[pair[0]] === "undefined") 
        {
            query_string[pair[0]] = decodeURIComponent(pair[1]);
            // If second entry with this name
        } 
        else if (typeof query_string[pair[0]] === "string") 
        {
            var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
            query_string[pair[0]] = arr;
            // If third or later entry with this name
        }
        else 
        {
            query_string[pair[0]].push(decodeURIComponent(pair[1]));
        }
    } 
    
    
    if(query_string.fechaFinal == null)
    {
        $('#reportrangeperiodo span').html( moment().subtract(1, 'years').format('MMMM D, YYYY') +" - "+moment().format('MMMM D, YYYY'));
    }
    else
    {
        $('#reportrangeperiodo span').html(query_string.fechaInicio + " - " + query_string.fechaFinal );
    }
    //refreshDonut();    
});
 
function xmlRequest(start, end){
    //hacer llamada a método para actualizar       
}

$('#sitiosSelect').on('change', function(){
    xmlRequest(before, now);
    cambiarFuentesSitio();
    $('#reportrangeperiodo span').html(moment().subtract('month', 1).startOf('month').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
    $('div.ranges > ul li.active').removeClass('active');
    $('div.ranges > ul li:first-child').addClass('active');
});

$("#comenzarPDF").click(function(){
    $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
    $("#fechaIniPDF").val( before ); 
    $("#fechaFinPDF").val( now );               

    var txt = $("#radiacionSolarVal").html().split("kWh");
    $("#energiaElectrica").val( txt[0].trim() );

    txt = $("#entalpia").html().split("kWh");
    $("#energiaTermica").val( txt[0].trim() );     

    txt = $("#combustible").html().split("kWh");
    $("#combustiblePDF").val( txt[0].trim() );   

    $(".jasperButton").trigger('click');
});  

$(".jasperReport").hide(); 