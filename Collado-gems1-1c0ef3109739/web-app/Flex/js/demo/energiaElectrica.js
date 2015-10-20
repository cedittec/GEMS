
 $(document).ready(function() {
    grafica();
 });

function xmlRequest() {
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
            $("#potenciaCFE").html("0.0");
            $("#potenciaFotovoltaico").html("0.0");
            $("#potenciaCogeneracion").html("0.0");
            $("#potenciaTotal").html("0.0");

            var data = xmlhttp.responseText;
            xmlDoc = $.parseXML(data);
            $dataXml = $(xmlDoc);
            algo = $dataXml.find('double');

            $("#potenciaCFE").html(algo[0].innerHTML + " kW");
            $("#potenciaFotovoltaico").html(algo[1].innerHTML + " kW");
            $("#potenciaCogeneracion").html(algo[2].innerHTML + " kW");
            $("#potenciaTotal").html(algo[3].innerHTML + " kW");


            var sitioId = $('#sitiosSelect option:selected').val();
            var fuenteId = $('#fuentesSelect option:selected').val();
            //refreshHistoricChart(sitioId, start, end);
        }
    };
    var sitioId = $('#sitiosSelect option:selected').val();
    var fuenteId = $('#fuentesSelect option:selected').val();
    xmlhttp.open("GET", "reporteActualizaIndex?sitioId=" + sitioId + "&fuenteId=" + fuenteId, true);
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
                xmlRequest();
                $('#contenidoId').show();
            }


        }
    };
    var sitioId = $('#sitiosSelect option:selected').val();
    xmlhttp.open("GET", "getFuentesActivas?sitioId=" + sitioId, true);
    xmlhttp.send();



}

$('#sitiosSelect').on('change', function() {


    cambiarFuentesSitio();


});

$('#fuentesSelect').on('change', function() {
    xmlRequest();

});


function grafica()
{
    var container = $("#miflot-chart-moving-line");

// Determine how many data points to keep based on the placeholder's initial size;
// this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 10 || 300;
    var data = [];

    function getRandomData() {

        if (data.length) {
            data = data.slice(1);
        }

        while (data.length < maximum) {
            var previous = data.length ? data[data.length - 1] : 50;
            var y = previous + Math.random()  * 10 - 5;
            data.push(y < 0 ? 0 : y > 100 ? 100 : y);
        }

        // zip the generated y values with the x values

        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }

        return res;
    }

    series = [{
            data: getRandomData(),
            lines: {
                fill: true,
                fillColor: "rgba(255,255,255,0.4)",
            },
        }];

    var plot = $.plot(container, series, {
        grid: {
            borderWidth: 0,
            minBorderMargin: 10,
            labelMargin: 10,
            backgroundColor: {
                colors: ["rgba(255, 255, 255,0)", "rgba(255, 255, 255,0)"]
            },
            margin: {
                top: 0,
                bottom: 0,
                left: 0,
                right: 0
            },
            markings: function(axes) {
                var markings = [];
                var xaxis = axes.xaxis;
                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                    markings.push({
                        xaxis: {
                            from: x,
                            to: x + xaxis.tickSize
                        },
                        color: "rgba(255, 255, 255, 0)"
                    });
                }
                return markings;
            }
        },
        xaxis: {
            tickFormatter: function() {
                return "";
            }
        },
        yaxis: {
            min: 10,
            max: 110,
            show: false
        },
        legend: {
            show: false
        },
        colors: ["#fff"]

    });

// Update the random dataset at 25FPS for a smoothly-animating chart

    setInterval(function updateRandom() {
        series[0].data = getRandomData();
        plot.setData(series);
        plot.draw();
    }, 500);


//Chat Widget SlimScroll Box
    $(function() {
        $('.chat-widget').slimScroll({
            start: 'bottom',
            height: '300px',
            alwaysVisible: true,
            disableFadeOut: true,
            touchScrollStep: 50
        });
    });

//Moment.js Time Display
    var datetime = null,
            date = null;

    var update = function() {
        date = moment(new Date())
        datetime.html(date.format('dddd<br>MMMM Do, YYYY<br>h:mm:ss A'));
    };

    $(document).ready(function() {
        datetime = $('#datetime')
        update();
        setInterval(update, 1000);
    });

//Custom jQuery - Changes background on time tile based on the time of day.
    $(document).ready(function() {
        datetoday = new Date(); // create new Date()
        timenow = datetoday.getTime(); // grabbing the time it is now
        datetoday.setTime(timenow); // setting the time now to datetoday variable
        hournow = datetoday.getHours(); //the hour it is

        if (hournow >= 18) // if it is after 6pm
            $('div.tile-img').addClass('evening');
        else if (hournow >= 12) // if it is after 12pm
            $('div.tile-img').addClass('afternoon');
        else if (hournow >= 6) // if it is after 6am
            $('div.tile-img').addClass('morning');
        else if (hournow >= 0) // if it is after midnight
            $('div.tile-img').addClass('midnight');
    });

//Vector Maps
    $(function() {
        $('#map').vectorMap({
            map: 'world_mill_en',
            backgroundColor: 'transparent',
            regionStyle: {
                initial: {
                    fill: '#bdc3c7'
                }
            },
            series: {
                regions: [{
                        values: visitorData,
                        scale: ['#bdc3c7', '#16a085'],
                        normalizeFunction: 'polynomial'
                    }]
            },
            onRegionLabelShow: function(e, el, code) {
                el.html(el.html() + ' (Total Visits - ' + visitorData[code] + ')');
            }
        });
    });

//To-Do List jQuery - Adds a strikethrough on checked items
    $('.checklist input:checkbox').change(function() {
        if ($(this).is(':checked'))
            $(this).parent().addClass('selected');
        else
            $(this).parent().removeClass('selected')
    });

//Easy Pie Charts
    $(function() {
        $('#easy-pie-1, #easy-pie-2, #easy-pie-3, #easy-pie-4').easyPieChart({
            barColor: "rgba(255,255,255,.5)",
            trackColor: "rgba(255,255,255,.5)",
            scaleColor: "rgba(255,255,255,.5)",
            lineWidth: 20,
            animate: 1500,
            size: 175,
            onStep: function(from, to, percent) {
                $(this.el).find('.percent').text(Math.round(percent));
            }
        });

    });

//DataTables Initialization for Map Table Example
    $(document).ready(function() {
        $('#map-table-example').dataTable();
    });
// JavaScript Document
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
    