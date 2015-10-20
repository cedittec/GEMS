


//Morris Bar Chart


Morris.Bar({
element: 'ahorrosglobales-chart-bar',
data: [
  { y: '01/01/14', e: 3923, t: 7487},
  { y: '01/02/14', e: 3777,  t: 7200},
  { y: '01/03/14', e: 3855,  t: 6998},
  { y: '01/04/14', e: 4000,  t: 7732},
  { y: '01/05/14', e: 3352,  t: 7667},
  { y: '01/06/14', e: 3500,  t: 6985},
  { y: '01/07/14', e: 3792.82, t: 7566}
],
xkey: 'y',
ykeys: ['e', 't'],
labels: ['Energía eléctrica', 'Energía térmica'],
barColors: ['#f39c12','#e74c3c'],
resize: true
});

