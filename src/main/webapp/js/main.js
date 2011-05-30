function covers(p,s) {
	if(p != '') {
		p = '/'+p;
	}
	if(s != '') {
		s = '/'+s;
	}
	loading('#photos');
	jQuery('#photos').load(context+'/app/'+'covers'+p+s);
}
function albums(p,s) {
	if(p != '') {
		p = '/'+p;
	}
	if(s != '') {
		s = '/'+s;
	}
	loading('#albums');
	jQuery('#albums').load(context+'/app/'+'albums'+p+s);
}
function subalbums(id, el) {
	jQuery.get(context+'/app/subalbums/'+id, function(data) {
		jQuery(el).html(data);
	});
}
function searches() {
	loading('#searches');
	jQuery('#searches').load(context+'/app/'+'searches');
}
function photos(a,pp) {
	if(a != '') {
		a = '/'+a;
	}
	if(pp != '') {
		pp = '/'+pp;
	}	
	loading('#photos');
	jQuery('#photos').load(context+'/app/'+'photos'+a+pp);
}
function loadingMainOff() {
	jQuery('#main-progress').hide();
}
function loadingMain() {
	jQuery('#main-progress').show();
}
function loading(selector) {
	jQuery(selector).html('<div style="padding: 5px;"><img src=\''+context+'/images/ajax-loader5.gif\'/>&#160;loading...</div>');
}
function loadingNoText(selector) {
	jQuery(selector).html('<div style="padding: 5px;"><img src=\''+context+'/images/ajax-loader5.gif\'/></div>');
}
function loadingIcon(selector) {
	jQuery(selector).html('<img src=\''+context+'/images/ajax-loader5.gif\'/>');
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

function openModal(selector) {
	var trigger = jQuery(selector);
	var yes = jQuery(trigger.attr('rel')).find('.yes').eq(0);
	
	if(trigger.hasClass('modalInputHref')) {
		yes.attr('href', jQuery(trigger).attr('rev'));
	}
	if(trigger.hasClass('modalInputClick')) {
		yes.unbind('click')
			.click(new Function(trigger.attr('rev')))
			.click(function(){trigger.data('overlay').close(); return false;});
	}	
	if (jQuery(selector).data('triggered')) {
		jQuery(selector).overlay().load();
	} else {
		jQuery(selector).data('triggered', true);
		jQuery(selector).overlay({
			mask: {
			color: '#aaa',
			loadSpeed: 'fast',
			opacity: 0.5
		},
		speed: 'fast',
		closeOnClick: true,
	    load: true
		/* cos zamiast rel triggera 
		,target: '#yesno' */			
		});
	}
}

/* programowo otworz overlay dla elementu wskazanego przez selector */
function openOverlay(selector, text) {
	if(text != null) {
		jQuery(selector).find('.modal-text').text(text);
	}
	if (jQuery(selector).data('triggered')) {
		jQuery(selector).overlay().load();
	} else {
		jQuery(selector).data('triggered', true);
		jQuery(selector).overlay({
			mask: {
				color: '#aaa',
				loadSpeed: 'fast',
				opacity: 0.5
			},
			speed: 'fast',
			closeOnClick: true,
			load: true
		});
	}
}

/* get exif */
var xhr;
function exif(id) {
	loading('#exif');
	xhr = jQuery.ajax({
		url: context+'/app/'+'exif/'+id+'/0',
		beforeSend: function() {
			if(xhr && xhr.readystate != 4){
	            xhr.abort();
	        }		
		},
		success: function(data){
			jQuery('#exif').html(data);
			/*jQuery('#u'+id).attr('title',data);*/
		}
	});
}

function hrefDate(selector) {
	var old = jQuery(selector).attr('href');
	jQuery(selector).attr('href', old+'&dummy='+(new Date()).getTime());
}

/**
 * NOT USED
 * 
function modalsnew() {
	  jQuery(".modalInput").overlay({
			mask: {
				color: '#aaa',
				loadSpeed: 'fast',
				opacity: 0.5
			},
			speed: 'fast',
			closeOnClick: true,
			onBeforeLoad: function(event) {
				var element = event.originalTarget || event.srcElement;
				var trigger = jQuery(element);
				var api = trigger.data('overlay');
				if(trigger.hasClass('modalInputHref')) {
					jQuery(trigger.attr('rel'))
					.find('.yes').eq(0)
					.attr('href', jQuery(trigger).attr('rev'));
				}
				if(trigger.hasClass('modalInputClick')) {
					jQuery(trigger.attr('rel'))
						.find('.yes').eq(0)
						.unbind('click')
						.click(new Function(trigger.attr('rev')))
						.click(function(){api.close();});
				}
		    }
	  });
}
function modalsold() {
	  jQuery(".modalInput").overlay({
			mask: {
				color: '#aaa',
				loadSpeed: 'fast',
				opacity: 0.5
			},
			speed: 'fast',
			closeOnClick: true
	  });
	  jQuery('.modalInputHref').click(function(){
		  var modal = jQuery(this).attr('rel');
		  jQuery(modal).find('.yes').eq(0).attr('href', jQuery(this).attr('rev'));
	  });
	  jQuery('.modalInputClick').click(function(){
		  var modal = jQuery(this).attr('rel');
		  jQuery(modal).find('.yes').eq(0).unbind('click').click(new Function(jQuery(this).attr('rev')));
	  });
}
function modalHref(el) {
	var modal = jQuery(el).attr('rel');
	jQuery(modal).find('.yes').eq(0).attr('href', jQuery(el).attr('rev'));	
}
function modalClick(el) {
	var modal = jQuery(el).attr('rel');
	jQuery(modal).find('.yes').eq(0).click(new Function(jQuery(el).attr('rev')));	
}
*/