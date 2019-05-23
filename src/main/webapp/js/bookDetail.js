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

	$('#puchaseDate').append(  dateFormat(new Date(book.purchaseDate))  );
	$('#buyer').append( addEmp(book.buyer) );
	if( book.borrower != undefined ){
		$('#borrower').append( addEmp(book.borrower) );
		$('#borrowerMailaddress').append( addEmp(book.borrowerMailaddress) );
		$('#returnDate').append( addEmp(dateFormat(new Date(book.returnDate))) );
	}else{
		$('#borrowerArea').remove();
	}

	function addEmp(data){
		return ( data == undefined  ? '(なし)' : data );
	}
	function dateFormat(date) {
		var format = 'YYYY年MM月DD日';
	    format = format.replace(/YYYY/, date.getFullYear());
	    format = format.replace(/MM/, date.getMonth() + 1);
	    format = format.replace(/DD/, date.getDate());

	    return format;
	}
}

/* 書籍一覧へ戻る */
$(function() {

	/* 以下カッコ内は戻るボタンのID名称 */
	$('#return')
			.click(
					function() {
//						window.location.href = './bookList.html';
						window.close();
					});

})
