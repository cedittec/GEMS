
Morris.Bar({
element: 'energeticostermicos-chart-bar',
data: [
  { y: '01/01/14', b: 10230, i: 10447 },
  { y: '01/02/14', b: 10770, i: 10420 },
  { y: '01/03/14', b: 10505, i: 9398 },
  { y: '01/04/14', b: 10000, i: 10472 },
  { y: '01/05/14', b: 9552, i: 10067 },
  { y: '01/06/14', b: 9500, i: 9395 },
  { y: '01/07/14', b: 10257, i: 9995 }
],
xkey: 'y',
ykeys: ['b', 'i'],
yLabelFormat: function(y) {
        return y + " kWt";
    },
labels: ['S. Cogeneración', 'S. Térmico'],
barColors: ['#f39c12','#e74c3c'],
resize: true
});