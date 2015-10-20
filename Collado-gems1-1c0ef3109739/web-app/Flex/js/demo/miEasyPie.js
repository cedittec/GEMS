$(function() {
    $('#mieasy-pie-1, #mieasy-pie-2, #mieasy-pie-3, #mieasy-pie-4').easyPieChart({
        barColor: "rgba(255,255,255,.5)",
        trackColor: "rgba(255,255,255,.5)",
        scaleColor: "rgba(255,255,255,.5)",
        lineWidth: 20,
        animate: 1500,
        size: 175,
        onStep: function(from, to, percent) {
            $(this.el).find('.percent').text(Math.round(percent));
        }
    });

});// JavaScript Document