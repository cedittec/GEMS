//Morris Area Chart
var energy_data = [{
    date: '2013-12-25',
    productA: 918,
    productB: 920,
}, {
    date: '2014-1-25',
    productA: 952,
    productB: 981,
}, {
    date: '2014-2-25',
    productA: 913,
    productB: 922,
}, {
    date: '2014-3-25',
    productA: 901,
    productB: 931,
}, {
    date: '2014-4-25',
    productA: 915,
    productB: 934,
}, {
    date: '2014-5-25',
    productA: 917,
    productB: 910,
}, {
    date: '2014-6-25',
    productA: 973,
    productB: 930,
}, {
    date: '2014-7-25',
    productA: 965,
    productB: 965,
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
    productA: 1234.00,
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
		
        return y + " kWt";
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
        return  y + " m^3/h";
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