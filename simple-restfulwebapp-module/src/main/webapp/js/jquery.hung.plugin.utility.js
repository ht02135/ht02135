
// example of common jQuery utility function (work on javascript object)

// sum utility function 
(function($){
	$.sum = function(array) {
		var total = 0;
		$.each(array, function(index, value) {
			total += value;
		});
		return total;
	}
})(jQuery);

