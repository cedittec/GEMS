//Morris Area Chart
var emisiones_data = [{
    date: '2014-1-25',
    productB: 5.62,
}, {
    date: '2014-2-25',
    productB: 9.26,
}, {
    date: '2014-3-25',
    productB: 7.94,
}, {
    date: '2014-4-25',
    productB: 9.98,
}, {
    date: '2014-5-25',
    productB: 7.26,
}, {
    date: '2014-6-25',
    productB: 6.87,
}, {
    date: '2014-7-25',
    productB: 6.79,
}, ];


Morris.Area({
    element: 'emisionesMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productB'],
    yLabelFormat: function(y) {
		
        return y + " lbs CO2" ;
    },
    labels: ['S. Fotovoltaico'],
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