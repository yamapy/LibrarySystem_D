'use strict';

var rootUrl = "/java_s04/api/v1.1/bookList";

findAll();

function findAll() {
	console.log('findAll start.')
	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderTable
	});
}



function renderTable(data) {
	var headerRow = '<tr><th>タイトル</th><th>ジャンル</th><th>作者</th><th>詳細</th><th>ステータス</th>';

	$('#book').children().remove();



	if (data.length === 0) {
		$('#book').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);

		$.each(data, function(index, book) {
			var row = $('<tr>');
			row.append($('<td>').text(book.title));
			row.append($('<td>').text(book.genre));
			row.append($('<td>').text(book.author));
			row.append($('<td>').append(
					$('<button>').text("詳細").attr("type","button").attr("onclick")
			));

			row.append($('<td>').text(book.genre));


			table.append(row);
		});

		$('#book').append(table);
	}



//	$('#registExpense').click(function() {
//		renderDetails({});
//	});


}


//$('#saveExpense').click(function() {
//
//	var id = $('#appId').val()
//
//		addExpense();
//
//	return false;
//})



