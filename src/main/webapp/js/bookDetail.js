'use strict';

var detailUrl = "/LibrarySystem_D/api/v1.1/lendingBook";


initPage();


/* 詳細表示 */
function initPage() {
	var id = sessionStorage.getItem('id');

	console.log('findByID start - id:' + id);
	$.ajax({
		type : "GET",
		url : detailUrl + '/' + id,
		dataType : "json",
		success : function(data) {
			console.log('findById success: ' + data.name);
			renderBookDetails(data)
		}
	});
}

function renderBookDetails(book) {
	$('.error').text('');
	$('#id').val(book.id);
	$('#title').val(book.title);
	$('#author').val(book.author);
	$('#publisher').val(book.publisher);
	$('#genre').val(book.genre);
	$('#puchaseDate').val(book.purchaseDate);
	$('#buyer').val(book.buyer);
	$('#borrower').val(book.borrower);
	$('#borrowerMailaddress').val(book.borrowerMailaddress);
	$('#returnDate').val(book.returnDate);

}
