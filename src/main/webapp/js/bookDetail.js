'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/lendingBook";

function findById(id) {
	console.log('findByID start - id:' + id);
	$.ajax({
		type : "GET",
		url : rootUrl + '/' + id,
		dataType : "json",
		success : function(data) {
			console.log('findById success: ' + data.name);
			renderDetails(data)
		}
	});
}

function renderTable(data) {
	var success;
	$.ajax({
		type : "GET",
		url : rootUrl + "/isLogin",
		dataType : "text",
		success : function(data) {

			success = data
			console.log(success);

		}
	});

	if (success) {

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
						$('<button>').text("詳細").attr("type", "button").attr(
								"id", "bookDetail")));

				row.append($('<td>').text(book.status));

				table.append(row);
			});

			$('#book').append(table);
		}

	}

	else {

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
						$('<button>').text("詳細").attr("type", "button").attr(
								"id", "bookDetail")));

				if (book.status == "貸出中") {
					row.append($('<td>').text(book.status));
				} else {
					row.append($('<td>').append(
							$('<button>').text("すぐ借りる").attr("type", "button")
									.attr("id", "borrow")));
				}

				table.append(row);
			});

			$('#book').append(table);
		}

	}

}

function renderDetails(book) {
	$('.error').text('');
	$('#id').val(employee.id);
	$('#empId').val(employee.empId);
	$('#name').val(employee.name);
	$('#age').val(employee.age);
	$('input[name="gender"]').val([ employee.gender ]);

	$('#currentPhoto').children().remove();
	$('#photoId').val(employee.photoId);
	if (employee.photoId != null && employee.photoId != 0) {
		// 末尾のDate.now()はキャッシュ対策
		var currentPhoto = $('<img>').attr('src',
				getPhotoUrl + '/' + employee.photoId + '?' + Date.now());
		$('#currentPhoto').append(currentPhoto)
	}
	$('#zip').val(employee.zip);
	$('#pref').val(employee.pref);
	$('#address').val(employee.address);
	if (employee.post != null) {
		$('#postId').val(employee.post.id);
	}
	$('#enterDate').val(employee.enterDate);
	$('#retireDate').val(employee.retireDate);
}
