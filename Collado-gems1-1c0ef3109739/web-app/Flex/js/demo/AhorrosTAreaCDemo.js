//Morris Area Chart
var emisiones_data = [{
    date: '2014-1-25',
    productA: 1530.34,
    productB: 2135.39,
    productC: 3792.83,
}, {
    date: '2014-2-25',
    productA: 1665.91,
    productB: 2623.81,
    productC: 4178.67,
}, {
    date: '2014-3-25',
    productA: 1510.23,
    productB: 2315.65,
    productC: 4367.55,
}, {
    date: '2014-4-25',
    productA: 1538.26,
    productB: 2415.97,
    productC: 4485.94,
}, {
    date: '2014-5-25',
    productA: 1642.53,
    productB: 2105.63,
    productC: 4467.15,
}, {
    date: '2014-6-25',
    productA: 1561.26,
    productB: 2341.82,
    productC: 4672.39,
}, {
    date: '2014-7-25',
    productA: 1654.48,
    productB: 2138.34,	
    productC: 4512.62,
}, ];


Morris.Area({
    element: 'ahorrosMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB', 'productC'],
    yLabelFormat: function(y) {
		
        return "$" + y  ;
    },
    labels: ['S. de Cogeneración', 'S. Térmico', 'Colector Solar'],
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