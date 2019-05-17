'use strict';

//LendingBookResources.java内の@Path("lendingBook")
var rootUrl = "/LibrarySystem_D/api/v1.1/lendingBook";

//メソッドの実行が必要！
findLendingBook();

function findLendingBook() {
	console.log('findLendingBook start.')
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderTable
	});
}

function renderTable(data) {
	var headerRow = '<tr><th>タイトル</th><th>借り手</th><th>返却予定日</th></tr>';

	if (data.length === 0) {
		$('#lendingBookList').append('<p>現在、貸出中の書籍は存在しません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, lendingBook) {
			var row = $('<tr>');
			row.append($('<td>').text(lendingBook.title));
			row.append($('<td>').text(lendingBook.borrower));
			row.append($('<td>').text(lendingBook.returnDate));
			table.append(row);
		});

		$('#lendingBookList').append(table);
	}
}
