'use strict';

$(function() {

	/* 以下カッコ内はボタンID */
	$('#lendingBookList')
			.click(
					function() {
						window.location.href = 'http://localhost:8080/LibrarySystem_D/html/lendingBookList.html';
					});

})

$(function() {

	/* 以下カッコ内はボタンID */
	$('#createBook')
			.click(
					function() {
						window.location.href = 'http://localhost:8080/LibrarySystem_D/html/createBook.html';
					});

})
