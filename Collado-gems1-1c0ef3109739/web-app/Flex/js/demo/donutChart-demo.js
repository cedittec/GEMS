//Morris Donut Chart
Morris.Donut({
element: 'morris-chart-donut',
data: [
 {label: "Eléctrica", value: 42.7},
 {label: "Térmica", value: 28.3},
 {label: "Combustibles", value: 29.0},
],
resize: true,
colors: ['#f39c12','#e74c3c', '#2980b9'],
formatter: function (y) { return y + "%" ;}
});

