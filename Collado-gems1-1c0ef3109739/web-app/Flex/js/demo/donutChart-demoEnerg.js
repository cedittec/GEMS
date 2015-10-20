//Morris Donut Chart
Morris.Donut({
element: 'morris-chart-donut',
data: [
 {label: "S. de Cogeneración", value: 27.7},
 {label: "S. Fotovoltaico", value: 10},
 {label: "Colector Solar", value: 40.0},
],
resize: true,
colors: ['#16a085','#f39c12', '#f1c40f'],
formatter: function (y) { return y + "%" ;}
});

