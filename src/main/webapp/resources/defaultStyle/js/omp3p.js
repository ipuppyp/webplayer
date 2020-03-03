

/**
 * Replace the src attribute of img element with specified id.
 * Used on icons on mouse over events. 
 * @param id
 * @param src
 */
function onMouse(id, src) {
	$(id).attr("src", src);
}



/**
 * Show and hide popup box with selected message
 * @param txt
 */
function loadPopupBox(txt) {
	$("#popup_text").html(txt);
    $('#popup_box').fadeIn("slow");
    $('#popup_box').delay(500);
    $('#popup_box').fadeOut("slow");
}

