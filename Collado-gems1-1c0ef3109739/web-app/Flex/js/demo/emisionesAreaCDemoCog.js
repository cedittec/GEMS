//Morris Area Chart
var emisiones_data = [{
    date: '2014-1-25',
    productA: 330.34,
}, {
    date: '2014-2-25',
    productA: 365.91,
}, {
    date: '2014-3-25',
    productA: 310.23,
}, {
    date: '2014-4-25',
    productA: 302.26,
}, {
    date: '2014-5-25',
    productA: 314.53,
}, {
    date: '2014-6-25',
    productA: 344.26,
}, {
    date: '2014-7-25',
    productA: 341.82,
}, ];


Morris.Area({
    element: 'emisionesMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA'],
    yLabelFormat: function(y) {
		
        return y + " lbs CO2" ;
    },
    labels: ['S. de Cogeneración'],
    lineColors: ['#fff', '#fff', '#fff'],
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