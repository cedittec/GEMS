
Morris.Bar({
element: 'costoscombustibles-chart-bar',
data: [
  { y: '01/01/14', c: 523, i: 447 },
  { y: '01/02/14', c: 577, i: 420 },
  { y: '01/03/14', c: 555, i: 398 },
  { y: '01/04/14', c: 600, i: 472 },
  { y: '01/05/14', c: 552, i: 467 },
  { y: '01/06/14', c: 500, i: 395},
  { y: '01/07/14', c: 524, i: 434 }
],
xkey: 'y',
ykeys: ['c', 'i'],
yLabelFormat: function(y) {
        return y + " m^3/h";
    },
labels: ['S. de cogeneración', 'S. térmico'],
barColors: ['#f1c40f','#f39c12'],
resize: true
});