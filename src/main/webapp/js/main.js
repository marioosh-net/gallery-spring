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
	  
	  /*albums();*/
});

function covers() {
	jQuery('#covers').load('covers.html');
}
function albums() {
	jQuery('#albums').load('albums.html');
}
function slimboxstart() {
	jQuery("a[rel^='lightbox-gal']").slimbox({
			resizeDuration: 200,
			initialWidth: 50,
			initialHeight: 50,
			loop: true
		}, null, function(el) {
		return (this == el) || ((this.rel.length > 8) && (this.rel == el.rel));
	});
}
