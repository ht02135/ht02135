// example of jQuery command (work on dom element)

// makeItBlueOrRed jQuery command
(function($) { 
	$.fn.makeItBlueOrRed = function(options) {
		options = $.extend({}, $.fn.makeItBlueOrRed.defaultOptions, options);
		
		// this is the wrapped set, filter out non-input:text element
		return this.filter('input:text').each(function(){
			if (options.color == 'blue') {
				// $(this) is the element
				$(this).css('color', 'blue');
			}
			if (options.color == 'red') {
				$(this).css('color', 'red');
			}
		});
	} 
	
	$.fn.makeItBlueOrRed.defaultOptions = {
		color: 'blue'
	}
})(jQuery);

//makeItBlueOrRed2 jQuery command
(function($) { 
	$.fn.makeItBlueOrRed2 = function(options) {		
	    var settings = $.extend({
	    	color: 'blue'
	    },options||{});
		
		return this.filter('input:text').each(function(){
			if (settings.color == 'blue') {
				$(this).css('color', 'blue');
			}
			if (settings.color == 'red') {
				$(this).css('color', 'red');
			}
		});
	} 
})(jQuery);

// ready handler
jQuery(document).ready(function($) {
	$("div").css("border", "3px solid green");
	$('#helloWorld').makeItBlueOrRed({color: 'red'});
	$('#helloWorld2').makeItBlueOrRed2({color: 'red'});
	
	// make use of sum util function created in jquery.hung.plugin.utility.js
	var numbers = new Array(1, 2, 3, 4, 5, 6);
	$('#helloWorld').val($.sum(numbers));
});

