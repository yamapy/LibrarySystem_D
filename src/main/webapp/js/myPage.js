
var tableId = '#table';
var rootUrl = '/LibrarySystem_D/api/v1.1/resources';

findLendingBook();

function findLendingBook() {
	console.log('findLendingBook start.')
	$.ajax({
		type : "GET",
		url : rootUrl + '/findLendingBookById',
		dataType : "json",
		success : renderTable
	});
}

function renderTable(data) {
	console.log('renderTable start.')
	console.log(data);
	var headerRow = '<tr><th>タイトル</th><th>借り手</th><th>返却予定日</th></tr>';

	if (data.length === 0) {
		$('#table').append('<p>現在、貸出中の書籍は存在しません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, lendingBook) {
			var row = $('<tr>');
			var returnButton  = '<button id="login-button" class="login-button">すべて返却</button>';
			row.append($('<td>').text(lendingBook.title));
			row.append($('<td>').text(lendingBook.borrower));

			//
			row.append($('<td>').text(lendingBook.returnDate));
			row.append($('<td>').append(
					$('<button>').text("返却").attr("type","button").attr("onclick", 'returnBookById('+lendingBook.id+')')
				));
			if(index==0){
				row.append($('<td>').append(
						$('<button>').text("返却").attr("type","button").attr("onclick", 'returnAll()')
				));
			}

			table.append(row);
		});

		$('#table').append(table);

	}
}

function returnBookById(Id) {
	alert(Id+'を返却する予定');
	console.log('returnById start.')
	var param =
		{
			id : '1'
		};
	$.ajax({
		type : "GET",
		url : rootUrl + '/returnBookById',
		//data : param,
		dataType : "json",
		success : function(data) {
			console.log(data);
			var tableHTML='';
			if (data == true) {
				alert('すべて返却しました');
//				location.href = './Expense.html';
			} else {
				alert('返却失敗');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	});
}
function returnAll() {
	alert('全部返却する予定');
}

function init() {
	// 入力されたユーザーIDとパスワード

	var requestQuery = {
		id : $('#login-id').val(),
		pass : $('#login-pass').val()
	};

	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/myPage",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
			$('#table').text("");
			if (data == true) {
				$('#table').append("<table><th>タイトル<th>返却予定日<th>返却");
				$('#table').append("</table>");
			} else {
				$('#table').append("貸出本はありません");
				alert('返却失敗aaaa');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}
/*
function login() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		id : $('#login-id').val(),
		pass : $('#login-pass').val()
	};
	console.log($('#login-id').val());
	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/myPage",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
			var tableHTML='';
			if (data == true) {
				alert('すべて返却しました');
//				location.href = './Expense.html';
			} else {
				alert('返却失敗');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}
*/


$(document).ready(function() {
	// ログインボタンを押したときのイベント
	$('#login-button').click(init);
});
