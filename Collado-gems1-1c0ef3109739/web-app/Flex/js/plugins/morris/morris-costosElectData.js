
Morris.Bar({
element: 'costoselectricos-chart-bar',
data: [
  { y: '01/01/14', b: 523, i: 447, p: 233 },
  { y: '01/02/14', b: 577, i: 420, p: 215 },
  { y: '01/03/14', b: 555, i: 398, p: 225 },
  { y: '01/04/14', b: 600, i: 472, p: 240 },
  { y: '01/05/14', b: 552, i: 467, p: 211 },
  { y: '01/06/14', b: 500, i: 395, p: 232 },
  { y: '01/07/14', b: 524, i: 434, p: 262 }
],
xkey: 'y',
ykeys: ['b', 'i', 'p'],
yLabelFormat: function(y) {
        return y + "kW/h";
    },
labels: ['Base', 'Intermedia', 'Punta'],
barColors: ['#f39c12','#e74c3c', '#16a085'],
resize: true
});