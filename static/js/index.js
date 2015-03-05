$(document).ready(function() {
	
	// Login Box functionality
	
	// Cache jquery objects for use
    var button = $('#loginButton');
    var box = $('#loginBox');
    var form = $('#loginForm');
    
    button.click(function() {
        box.toggle();
        button.toggleClass('active');
    });
	
});