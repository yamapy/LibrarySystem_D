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

/* 詳細表示 */
function renderBookDetails(book) {
	var emp = '(なし)'

	$('#title').append( addEmp(book.title) );
	$('#author').append( addEmp(book.author) );
	$('#publisher').append( addEmp(book.publisher) );
	$('#genre').append( addEmp(book.genre) );
	$('#puchaseDate').append(book.purchaseDate);
	$('#buyer').append( addEmp(book.buyer) );
	if( book.borrower != undefined ){
		$('#borrower').append(book.borrower);
		$('#borrowerMailaddress').append( addEmp(book.borrowerMailaddress) );
		$('#returnDate').append( addEmp(book.returnDate) );
	}else{
		$('#borrowerArea').remove();
	}

	function addEmp(data){
		return ( data == undefined  ? '(なし)' : data );
	}

}

/* 書籍一覧へ戻る */
$(function() {

	/* 以下カッコ内は戻るボタンのID名称 */
	$('#return')
			.click(
					function() {
						window.location.href = './bookList.html';
					});

})
