//Morris Area Chart
var emisiones_data = [{
    date: '2014-1-25',
    productA: 1530.34,
    productB: 2135.39,
}, {
    date: '2014-2-25',
    productA: 1665.91,
    productB: 2623.81,
}, {
    date: '2014-3-25',
    productA: 1510.23,
    productB: 2315.65,
}, {
    date: '2014-4-25',
    productA: 1538.26,
    productB: 2415.97,
}, {
    date: '2014-5-25',
    productA: 1642.53,
    productB: 2105.63,
}, {
    date: '2014-6-25',
    productA: 1561.26,
    productB: 2341.82,
}, {
    date: '2014-7-25',
    productA: 1654.48,
    productB: 2138.34,
}, ];


Morris.Area({
    element: 'ahorrosMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB'],
    yLabelFormat: function(y) {
		
        return "$" + y  ;
    },
    labels: ['S. de Cogeneración', 'S. Fotovoltaico'],
    lineColors: ['#fff', '#fff'],
    hideHover: 'auto',
    resize: true,
    gridTextFamily: ['Open Sans'],
    gridTextColor: ['rgba(255,255,255,0.7)'],
    fillOpacity: 0.1,
    pointSize: 0,
    smooth: true,
    lineWidth: 2,
    grid: true,
    
});