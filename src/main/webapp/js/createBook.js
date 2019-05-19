'use strict';

$(function() {

	/* 以下カッコ内はボタンID */
	$('#returnManageTopPage')
			.click(
					function() {
						window.location.href = 'http://localhost:8080/LibrarySystem_D/html/managementTop.html';
					});

})