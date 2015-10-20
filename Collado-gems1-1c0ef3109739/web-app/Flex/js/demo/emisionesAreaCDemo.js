//Morris Area Chart
var emisiones_data = [{
    date: '2014-1-25',
    productA: 330.34,
    productB: 5.62,
    productC: 1235.23,
}, {
    date: '2014-2-25',
    productA: 365.91,
    productB: 9.26,
    productC: 1320.26,
}, {
    date: '2014-3-25',
    productA: 310.23,
    productB: 7.94,
    productC: 1165.78,
}, {
    date: '2014-4-25',
    productA: 302.26,
    productB: 9.98,
    productC: 1320.65,
}, {
    date: '2014-5-25',
    productA: 314.53,
    productB: 7.26,
    productC: 1220.26,
}, {
    date: '2014-6-25',
    productA: 344.26,
    productB: 6.87,
    productC: 1225.57,
}, {
    date: '2014-7-25',
    productA: 341.82,
    productB: 6.79,
    productC: 1234,
}, ];


Morris.Area({
    element: 'emisionesMorrisArea',
    data: emisiones_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB', 'productC'],
    yLabelFormat: function(y) {
		
        return y + " lbs CO2" ;
    },
    labels: ['S. de Cogeneración', 'S. Fotovoltaico', 'Colector Solar'],
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