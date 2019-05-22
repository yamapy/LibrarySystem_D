'use strict';

// LendingBookResources.java内の@Path("lendingBook")
var rootUrl = "/LibrarySystem_D/api/v1.1/lendingBook";
var countUrl = "/LibrarySystem_D/api/v1.1/lendingBook/countLendingBook";

// メソッドの実行が必要！
countLendingBook();
findLendingBook();

function countLendingBook() {
	console.log('countLendingBook start. ')
	$.ajax({
		type : "GET",
		url : countUrl,
		dataType : "json",
		success : dt
	});
}

function findLendingBook() {
	console.log('findLendingBook start.')
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderTable
	});
}

function dt(data) {
	var headerRow = '<tr><th>現在貸出中の書籍件数</th><tr>';

	if (data.length === 0) {
		$('#lendingBookNumber').append('<p>0件</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$('#lendingBookNumber').append(table);
		$('#lendingBookNumber').append('全' + data + '件');
	}
}

function renderTable(data) {
	var headerRow = '<tr><th>タイトル</th><th>借り手</th><th>返却</th></tr>';
	console.log(data);
	if (data.length === 0) {
		$('#lendingBookList').append('<p>現在、貸出中の書籍は存在しません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, lendingBook) {
			var row = $('<tr>');
			row.append($('<td>').text(lendingBook.title));
			row.append($('<td>').text(lendingBook.borrower));
			row.append($('<td>').text(lendingBook.returnDate).attr("id", lendingBook.id));
			table.append(row);
		});

//		var now = new Date();
//		console.log(now);

		$('#lendingBookList').append(table);

		$.each(data, function(index, lendingBook) {
			var date = new Date(lendingBook.returnDate);
//			console.log(date); 返却予定日の出力　確認のために使っていた

			var now = new Date();
//			console.log(now); 今日の日付を出力　確認のために使っていた
			if (date < now) {
				$('#' + lendingBook.id).attr("class", "red");
			}
			else {
//			デフォルトは黒なので何も書かない
			}

		});

	}
}


$(function() {

	/* 以下カッコ内はボタンID */
	$('#returnManageTopPage').click(
					function() {
						window.location.href = 'http://localhost:8080/LibrarySystem_D/html/managementTop.html';
					});

})


/* function compareDate(date, format){

	format = format.replace(/YYYY/, date.getFullYear());
	format = format.replace(/MM/, date.getMonth() + 1);
	format = format.replace(/DD/, date.getDate());

	return format;
}

*/
