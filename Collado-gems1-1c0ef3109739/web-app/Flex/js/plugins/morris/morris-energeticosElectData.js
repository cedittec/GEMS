
Morris.Bar({
element: 'energeticoselectricos-chart-bar',
data: [
  { y: '01/01/14', b: 5224, i: 9301, p:6032 },
  { y: '01/02/14', b: 5201, i: 9023, p: 6102 },
  { y: '01/03/14', b: 5132, i: 9398, p: 5982 },
  { y: '01/04/14', b: 5320, i: 9102, p: 5930 },
  { y: '01/05/14', b: 5012,  i: 9362, p: 6123 },
  { y: '01/06/14', b: 5126,  i: 9395, p: 5993 },
  { y: '01/07/14', b: 5378, i: 9275, p:6049 }
],
xkey: 'y',
ykeys: ['b', 'i', 'p'],
yLabelFormat: function(y) {
        return y + " kWt";
    },
labels: ['CFE','S. Cogeneración', 'S. Fotovoltáico'],
barColors: ['#16a085','#2980b9', '#f39c12'],
resize: true
});