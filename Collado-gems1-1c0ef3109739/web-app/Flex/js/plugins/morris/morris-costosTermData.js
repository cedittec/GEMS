
Morris.Bar({
element: 'costostermicos-chart-bar',
data: [
  { y: '01/01/14', b: 523, i: 447 },
  { y: '01/02/14', b: 577, i: 420 },
  { y: '01/03/14', b: 555, i: 398 },
  { y: '01/04/14', b: 600, i: 472 },
  { y: '01/05/14', b: 552, i: 467 },
  { y: '01/06/14', b: 500, i: 395 },
  { y: '01/07/14', b: 524, i: 434 }
],
xkey: 'y',
ykeys: ['b', 'i'],
yLabelFormat: function(y) {
        return y + " $/kWh";
    },
labels: ['S. Cogeneración', 'S. Térmico'],
barColors: ['#f39c12','#e74c3c', '#16a085'],
resize: true
});