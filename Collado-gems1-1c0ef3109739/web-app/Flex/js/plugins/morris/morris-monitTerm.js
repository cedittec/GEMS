
Morris.Bar({
element: 'monitoreoTermico-chart-bar',
data: [
  { y: 'Int. del S. de\nCogeneración', b: 52, i: 67 },
  { y: 'T. Servicios S.\nde Cogeneración', b: 57, i: 70 },
  { y: 'T. de Inercia del\nS. de Cogeneración', b: 55, i: 68 },
  { y: 'Int. del S.\n Térmico', b: 60, i: 72}
],
xkey: 'y',
ykeys: ['b', 'i'],
yLabelFormat: function(y) {
        return y + "°C";
    },
labels: ['Temperatura del Agua de Entrada', 'Temperatura del Agua de Salida'],
barColors: ['#2980b9','#34495e'],
resize: true
});







Morris.Bar({
element: 'monitoreoTermicoFlujo-chart-bar',
data: [
  { y: 'S. Térmico', b: 52},
  { y: 'Intercambiador\nT. de Inercia', b: 57},
  { y: 'Placas T.\nde Inercia', b: 55},
  { y: 'Placas T.\nde Servicios', b: 60}
],
xkey: 'y',
ykeys: ['b'],
yLabelFormat: function(y) {
        return y + " m^3/h";
    },
labels: ['Flujo del circuito de agua'],
barColors: ['#16a061'],
resize: true
});


Morris.Bar({
element: 'monitoreoTermicoTemps-chart-bar',
data: [
  { y: 'Economizador', b: 52, i: 67 },
  { y: 'Vaporizador', b: 57, i: 70 },
  { y: 'Supercalentador', b: 55, i: 68 }
],
xkey: 'y',
ykeys: ['b', 'i'],
yLabelFormat: function(y) {
        return y + " °C";
    },
labels: ['Temperatura de Entrada', 'Temperatura de Salida'],
barColors: ['#8b5b9f','#6b3183'],
resize: true
});

Morris.Bar({
element: 'monitoreoTermicoPresion-chart-bar',
data: [
  { y: 'Tanque de\nservicios', b: 52},
  { y: 'Tanque de\nInercia', b: 57},
  { y: 'Tanque de\nCondensados', b: 55},
  { y: 'Tanque de\nPresión', b: 60},
  { y: 'Tubería de\nVapor', b: 60}
],
xkey: 'y',
ykeys: ['b'],
yLabelFormat: function(y) {
        return y + " Kg/cm^2";
    },
labels: ['Presión'],
barColors: ['#f39c12'],
resize: true
});




