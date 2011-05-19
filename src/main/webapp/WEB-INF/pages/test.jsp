<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglibs.jsp" %>
<t:layout>
	<script>

    var params = '';/*dest='+destination+
    '&multiple_destinations='+multi_dest+
    '&from='+from+
    '&termin='+termin+
    '&termin_od='+termin_od+
    '&termin_do='+termin_do+
    '&cena_od='+cena_od+
    '&cena_do='+cena_do+
    '&standard_od='+standard_od+
    '&standard_do='+standard_do+ 
    '&alionly='+alionly+
    '&only7='+only7+
    '&lmsonly='+lmsonly+
    '&hoteltype='+hoteltype+
    '&ileosob='+ileosob+ 
    '&tvonly='+tvonly+
    '&promoonly='+promoonly+
    '&transtype='+transtype+
    '&sort_by='+sort_by+ 
    '&asc_desc='+asc_desc+
    '&start='+start+
    '&limit='+limit+
    '&images_path='+images_path+
    '&uimages_path='+uimages_path+
    '&self_path='+self+
    '&tree='+tree+
    '&page='+page+
    '&cos='+cos+
    '&page_ver='+page_ver
    ;*/

	jQuery.ajax({
		  type: 'POST',
		  url: 'http://www.itaka.pl/cms/inc/szukaj/search_act.php?work=getOfferList',
		  success: function(data) {
		  	alert(data);
		  	jQuery('#result').text(data);
			},
		  dataType: 'json'
		});
	</script>	
	
	<form action="http://www.itaka.pl/cms/inc/szukaj/search_act.php?work=getOfferList" method="post">
	<input type="submit" value="submit"/>
	</form>
	
	<pre id="result">
	</pre>
</t:layout> 
