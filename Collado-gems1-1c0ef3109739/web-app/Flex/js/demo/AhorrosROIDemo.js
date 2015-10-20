//Morris Area Chart
var emisiones_data = [{
    
    date: '2010-3-25',
    productA: 1110.23,
}, {
    date: '2011-4-25',
    productA: 1538.26,
}, {
    date: '2012-5-25',
    productA: 1642.53,
}, {
    date: '2013-6-25',
    productA: 1961.26,
}, {
    date: '2014-1-25',
    productA: 2654.48,
}, ];


Morris.Area({
    element: 'ROIMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabels: 'day',
    ykeys: ['productA'],
    yLabelFormat: function(y) {
		
        return "$" + y  ;
    },
    labels: ['Flujo de efectivo acumulado'],
    lineColors: ['#fff'],
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