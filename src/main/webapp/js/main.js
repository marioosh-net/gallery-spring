jQuery(document).ready(function(){
	  /*var footerHeight = jQuery('#footer').height();
	  jQuery('#main').css('padding-bottom', footerHeight);
	  jQuery('#footer').css('margin-top', -footerHeight);*/

	  /* modal dialogs */
	  jQuery(".modalInput").overlay({
			mask: {
				color: '#aaa',
				loadSpeed: 'fast',
				opacity: 0.5
			},
			speed: 'fast',
			closeOnClick: true
			/* cos zamiast rel triggera 
			,target: '#yesno' */
	  });
	  jQuery('.modalInput').click(function(){
		  var modal = jQuery(this).attr('rel');
		  jQuery(modal).find('.yes').eq(0).attr('href', jQuery(this).attr('rev'));
	  });
	  /* modal dialogs END */
	  
	  jQuery('a.bu').click(function(){
		  this.blur();
	  });
});

function isuserexist(input) {

	var email = jQuery(input).val();
	jQuery('#status1').show();
	jQuery.ajax({
		url:	'ajax/isuserexist.html',
		data: {
			email: email
		},
		beforeSend: function() {
			jQuery('#loginexist').hide();
		},
		success: function(data){
			jQuery('#status1').hide();
			if(data == '1') {
				jQuery('#loginexist').show();
				/*jQuery('#useremail').focus();
				jQuery('#useremail').select();*/
			} else {
				jQuery('#loginexist').hide();
			}			
		}
	});
}

function ispasswordgood(input, id) {

	var password = jQuery(input).val();
	jQuery('#status2').show();
	jQuery.ajax({
		url:	'ajax/ispasswordgood.html',
		data: {
			password: password,
			id: id
		},
		beforeSend: function() {
			jQuery('#badpassword').hide();
		},
		success: function(data){
			jQuery('#status2').hide();
			if(data == '1') {
				jQuery('#badpassword').hide();
			} else {
				jQuery('#badpassword').show();
			}			
		}
	});
}