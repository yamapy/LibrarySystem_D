'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

findAll();

function findAll() {
	console.log('findAll start.')
	$.ajax({
		type : "GET",
		url : rootUrl+"/book",
		dataType : "json",

		success : renderTable
	});
}



function renderTable(data) {
	var success;
	$.ajax({
		type: "GET",
		url: rootUrl+"/isLogin",
		dataType: "text",
		success: function(data) {

			success = data
			console.log(success);

		}
	});

	if(success){


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
						$('<button>').text("詳細").attr("type","button").attr("id","bookDetail")
				));

				row.append($('<td>').text(book.status));


				table.append(row);
			});

			$('#book').append(table);
		}


	}

	else{


		var headerRow = '<tr><th>タイトル</th><th>ジャンル</th><th>作者</th><th>詳細</th><th>貸出</th>';

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
						$('<button>').text("詳細").attr("type","button").attr("id","bookDetail")
				));

				if(book.status=="貸出中"){
				row.append($('<td>').text(book.status));
				}else{
					row.append($('<td>').append(
							$('<button>').text("すぐ借りる").attr("type","button").attr("id","borrow")
					));
				}

				table.append(row);
			});

			$('#book').append(table);
		}


	}

}

//$('#findBook').click(function() {
//	findByParam();
//	return false;
//})

//function initPage() {
//	var newOption = $('<option>').val(0).text('指定しない').prop('selected', true);
//	$('#genreParam').append(newOption);
//	makeGenreSelection('#genreParam');
//	findAll();
//	makeGenreSelection('#genre');
//}
//
//function findByParam() {
//	console.log('findByParam start.');
//
//	var urlWithParam = rootUrl+"/book"+'?titleParam='+$('#titleParam').val()
//		+'&authorParam='+$('#authorParam').val()
//		+'&genre='+$('#genreParam').val()
//		+'&status='+$('#statusParam').val();
//	$.ajax({
//		type : "GET",
//		url : urlWithParam,
//		dataType : "json",
//		success : renderTable
//	});
//}
//
//function makeGenreSelection(selectionGenre, book) {
//	console.log('makeGenreSelection start.')
//	$.ajax({
//		type : "GET",
//		url : rootUrl+"/genre",
//		dataType : "json",
//		success : function(data, textStatus, jqXHR) {
//			$.each(data, function(index, book) {
//				var newOption = $('<option>').val(book.genre).text(book.genre);
//				if (book != null && book.genre == book.genre) {
//					newOption.prop('selected', isSelected);
//				}
//				$(selectionGenre).append(newOption);
//			});
//		}
//	});
//}




