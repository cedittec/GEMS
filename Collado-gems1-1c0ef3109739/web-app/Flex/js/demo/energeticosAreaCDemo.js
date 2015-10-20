//Morris Area Chart
var energy_data = [{
    date: '2013-12-25',
    productA: 21505.26,
    productB: 20920.26,
}, {
    date: '2014-1-25',
    productA: 22510.34,
    productB: 21150.62,
}, {
    date: '2014-2-25',
    productA: 21965.91,
    productB: 22890.26,
}, {
    date: '2014-3-25',
    productA: 20910.23,
    productB: 21875.94,
}, {
    date: '2014-4-25',
    productA: 24802.26,
    productB: 20519.98,
}, {
    date: '2014-5-25',
    productA: 21374.53,
    productB: 22711.26,
}, {
    date: '2014-6-25',
    productA: 22684.26,
    productB: 23620.87,
}, {
    date: '2014-7-25',
    productA: 20234,
    productB: 20702,
}, ];

//Morris Area Chart
var comb_data = [{
    date: '2013-12-25',
    productA: 985.26,
}, {
    date: '2014-1-25',
    productA: 1210.34,
}, {
    date: '2014-2-25',
    productA: 905.91,
}, {
    date: '2014-3-25',
    productA: 990.23,
}, {
    date: '2014-4-25',
    productA: 1402.26,
}, {
    date: '2014-5-25',
    productA: 1174.53,
}, {
    date: '2014-6-25',
    productA: 1064.26,
}, {
    date: '2014-7-25',
    productA: 1756.00,
},  
];


Morris.Area({
    element: 'energiaMorrisArea',
    data: energy_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + 1 + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA', 'productB'],
    yLabelFormat: function(y) {
		
        return y ;
    },
    labels: ['E. Termica', 'E. Electrica'],
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

Morris.Area({
    element: 'combustibleMorrisArea',
    data: comb_data,
    xkey: 'date',
    xLabelFormat: function(date) {
        return (date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear();
    },
    xLabels: 'day',
    ykeys: ['productA'],
    yLabelFormat: function(y) {
        return  y + " BTU";
    },
    labels: ['Combustibles'],
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
    dateFormat: function(date) {
        d = new Date(date);
        return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
    },
});