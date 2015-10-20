// JavaScript Document
$("#misparklineD").sparkline([72, 65, 45, 65, 82, 78, 92, 83, 46, 87, 69, 96], {
    type: 'line',
    lineColor: 'rgba(255,255,255,0.8)',
    fillColor: 'rgba(255,255,255,0.3)',
    spotColor: '#ffffff',
    minSpotColor: '#ffffff',
    maxSpotColor: '#ffffff',
    highlightLineColor: '#ffffff',
    height: 24,
    chartRangeMin: 25,
    drawNormalOnTop: false
});