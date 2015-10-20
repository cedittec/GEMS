

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
                    $("#arregloEnergiaTotal0").html("0.0");
                    $("#arregloEnergiaTotal1").html("0.0");
                    $("#arregloEnergiaTotal2").html("0.0");
                    $("#arregloEnergiaTotal3").html("0.0");
                    
                    $("#arregloEnergiaTotalCFE0").html("0.0");
                    $("#arregloEnergiaTotalCFE1").html("0.0");
                    $("#arregloEnergiaTotalCFE2").html("0.0");
                    $("#arregloEnergiaTotalCFE3").html("0.0");
                    
                    $("#arregloCostoEvitadoCogeneracion0").html("0.0");
                    $("#arregloCostoEvitadoCogeneracion1").html("0.0");
                    $("#arregloCostoEvitadoCogeneracion2").html("0.0");
                    $("#arregloCostoEvitadoCogeneracion3").html("0.0");
                    
                    $("#arregloCostoEvitadoFotovoltaico0").html("0.0");
                    $("#arregloCostoEvitadoFotovoltaico1").html("0.0");
                    $("#arregloCostoEvitadoFotovoltaico2").html("0.0");
                    $("#arregloCostoEvitadoFotovoltaico3").html("0.0");
                    
                    
                    $("#tipoTarifa").html();
                    
                    
                    var data = xmlhttp.responseText;
                    xmlDoc = $.parseXML( data );
                    $dataXml = $( xmlDoc );
                    algo = $dataXml.find('double');                    
                    
                    $("#arregloEnergiaTotal0").html(algo[0].innerHTML + " $");
                    $("#arregloEnergiaTotal1").html(algo[1].innerHTML + " $");
                    $("#arregloEnergiaTotal2").html(algo[2].innerHTML + " $");
                    $("#arregloEnergiaTotal3").html(algo[3].innerHTML + " $");
                    
                    $("#arregloEnergiaTotalCFE0").html(algo[4].innerHTML + " $");
                    $("#arregloEnergiaTotalCFE1").html(algo[5].innerHTML + " $");
                    $("#arregloEnergiaTotalCFE2").html(algo[6].innerHTML + " $");
                    $("#arregloEnergiaTotalCFE3").html(algo[7].innerHTML + " $");
                    
                    $("#arregloCostoEvitadoCogeneracion0").html(algo[8].innerHTML + " $");
                    $("#arregloCostoEvitadoCogeneracion1").html(algo[9].innerHTML + " $");
                    $("#arregloCostoEvitadoCogeneracion2").html(algo[10].innerHTML + " $");
                    $("#arregloCostoEvitadoCogeneracion3").html(algo[11].innerHTML + " $");
                    
                    $("#arregloCostoEvitadoFotovoltaico0").html(algo[12].innerHTML + " $");
                    $("#arregloCostoEvitadoFotovoltaico1").html(algo[13].innerHTML + " $");
                    $("#arregloCostoEvitadoFotovoltaico2").html(algo[14].innerHTML + " $");
                    $("#arregloCostoEvitadoFotovoltaico3").html(algo[15].innerHTML + " $");
                    
                    algo2 = $dataXml.find('string');
                    $("#tipoTarifa").html(algo2[0].innerHTML + "");
                                 
                    actualizarGrafica(algo,algo2);
                }
            };
            var fechaInicio = start;
            var fechaFinal = end;
            var sitioId = $('#sitiosSelect option:selected').val();
            xmlhttp.open("GET","actualizaReporteEnergiaElectrica?sitioId="+sitioId+"&fechaInicio="+fechaInicio+"&fechaFinal="+fechaFinal,true);
            xmlhttp.send();
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
    var meses = [];
    var arreglo1 = [];
    var arreglo2 = [];
    var arreglo3 = [];
    var arreglo4 = [];
    var i;
    var index = 0;
    for (i = 16; i < array.length; i++)
    {
        if (i <= 27) {
            arreglo1[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 27 && i <= 39) {
            arreglo2[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
        if (i > 39 && i <= 51) {
            arreglo3[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }
         if (i > 51 && i <= 63) {
            arreglo4[index] = array[i].innerHTML;
            //console.log("Se guardo: "+ arreglo1[index] );
        }

        index++;
        if ((i == 27) || (i == 39) || (i == 51)|| (i == 63))
            index = 0;
    }
    

    //FIN ACTUALIZAR GRAFICA

    $("#costoselectricos-chart-bar").empty();
    Morris.Bar({
        element: 'costoselectricos-chart-bar',
        data: [
           { y:  arrayMeses[1].innerHTML, e: arreglo1[0],  t: arreglo2[0], c: arreglo3[0], a: arreglo4[0] },
              { y:  arrayMeses[2].innerHTML, e: arreglo1[1],  t: arreglo2[1], c: arreglo3[1], a: arreglo4[1] },
              { y:  arrayMeses[3].innerHTML, e: arreglo1[2],  t: arreglo2[2], c: arreglo3[2], a: arreglo4[2] },
              { y:  arrayMeses[4].innerHTML, e: arreglo1[3],  t: arreglo2[3], c: arreglo3[3], a: arreglo4[3] },
              { y:  arrayMeses[5].innerHTML, e: arreglo1[4],  t: arreglo2[4], c: arreglo3[4], a: arreglo4[4] },
              { y:  arrayMeses[6].innerHTML, e: arreglo1[5],  t: arreglo2[5], c: arreglo3[5], a: arreglo4[5] },
              { y:  arrayMeses[7].innerHTML, e: arreglo1[6], t: arreglo2[6], c: arreglo3[6], a: arreglo4[6] },
              { y:  arrayMeses[8].innerHTML, e: arreglo1[7], t: arreglo2[7], c: arreglo3[7], a: arreglo4[7] },
              { y:  arrayMeses[9].innerHTML, e: arreglo1[8], t: arreglo2[8], c: arreglo3[8], a: arreglo4[8] },
              { y:  arrayMeses[10].innerHTML, e: arreglo1[9], t: arreglo2[9], c: arreglo3[9], a: arreglo4[9] },
              { y:  arrayMeses[11].innerHTML, e: arreglo1[10], t: arreglo2[10], c: arreglo3[10], a: arreglo4[10] },
              { y:  arrayMeses[12].innerHTML, e: arreglo1[11], t: arreglo2[11], c: arreglo3[11], a: arreglo4[11] }
        ],
        xkey: 'y',
        ykeys: ['e', 't', 'c','a'],
        labels: ['Energia Total', 'Energia Base', 'Energia Intermedia','Energia Punta'],
        barColors: ['#f39c12', '#e74c3c', '#16a085','#e74c3c'],
        resize: true
    });
}
    
     $("#comenzarPDF").click(function(){
        
        var before = moment().subtract('month', 1).startOf('month');
        var now = moment(); 
             
        $("#sitioIdPDF").val( $('#sitiosSelect').val() );        
        $("#fechaIniPDF").val( before ); 
        $("#fechaFinPDF").val( now );               
                     
        txt = $("#arregloEnergiaTotal1").html();
        $("#idenergiaBase").val( txt.trim() );           
        txt = $("#arregloEnergiaTotal2").html();
        $("#idenergiaIntermedia").val( txt.trim() );  
        txt = $("#arregloEnergiaTotal3").html();
        $("#idenergiaPunta").val( txt.trim() );  
        txt = $("#arregloEnergiaTotalCFE1").html();
        $("#idenergiaBase2").val( txt.trim() );  
        txt = $("#arregloEnergiaTotalCFE2").html();
        $("#idenergiaIntermedia2").val( txt.trim() );  
        txt = $("#arregloEnergiaTotalCFE3").html();
        $("#idenergiaPunta2").val( txt.trim() );  
        
        
         txt = $("#tipoTarifa").html();
        $("#idtarifaCFE").val( txt.trim() );  
        //**DAtos que faltan en pagina
         txt = $("#basePrecio").html();
        $("#idprecioBase").val( txt.trim() );  
         txt = $("#intermediaPrecio").html();
        $("#idprecioIntermedia").val( txt.trim() );  
         txt = $("#puntaPrecio").html();
        $("#idprecioPunta").val( txt.trim() );  
         txt = $("#demandaPrecio").html();
        $("#idprecioDemanda").val( txt.trim() ); 
        //****************************
        
         txt = $("#arregloEnergiaTotalCFE0").html();
        $("#idcostoCFE").val( txt.trim() );  
        
         txt = $("#arregloCostoEvitadoCogeneracion0").html();
        $("#idcostoCogeneracion").val( txt.trim() );  
         txt = $("#arregloCostoEvitadoCogeneracion1").html();
        $("#idenergiaBase3").val( txt.trim() );  
         txt = $("#arregloCostoEvitadoCogeneracion2").html();
        $("#idenergiaIntermedia3").val( txt.trim() );  
         txt = $("#arregloCostoEvitadoCogeneracion3").html();
        $("#idenergiaPunta3").val( txt.trim() );  
        
         txt = $("#arregloCostoEvitadoFotovoltaico0").html();
        $("#idcostoFotovoltaico").val( txt.trim() ); 
        
        txt = $("#arregloCostoEvitadoFotovoltaico1").html();
        $("#idenergiaBase4").val( txt.trim() );  
        txt = $("#arregloCostoEvitadoFotovoltaico2").html();
        $("#idenergiaIntermedia4").val( txt.trim() );  
        txt = $("#arregloCostoEvitadoFotovoltaico3").html();
        $("#idenergiaPunta4").val( txt.trim() );  
        
        
        
        $(".jasperButton").trigger('click');                 
      
    });  
    
    $(".jasperReport").hide();