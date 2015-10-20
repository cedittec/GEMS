


//Morris Bar Chart


Morris.Bar({
element: 'costosglobales-chart-bar',
data: [
  { y: '01/01/14', e: 4923, t: 4487, c: 2330 },
  { y: '01/02/14', e: 4777,  t: 4200, c: 2157 },
  { y: '01/03/14', e: 4855,  t: 3998, c: 2255 },
  { y: '01/04/14', e: 4000,  t: 4732, c: 2400 },
  { y: '01/05/14', e: 5352,  t: 4667, c: 2115 },
  { y: '01/06/14', e: 4500,  t: 3985, c: 2322 },
  { y: '01/07/14', e: 4800, t: 4384, c: 2624 }
],
xkey: 'y',
ykeys: ['e', 't', 'c'],
labels: ['Energía eléctrica', 'Energía térmica', 'Combustibles'],
barColors: ['#f39c12','#e74c3c', '#16a085'],
resize: true
});

