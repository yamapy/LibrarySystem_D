
var tableId = '#table';
var rootUrl = '/LibrarySystem_D/api/v1.1/resources';

checkLogin();

function findLendingBook() {

	console.log('findLendingBook start.')
	$.ajax({
		type : "GET",
		url : rootUrl + '/findLendingBookById',
		dataType : "json",
		success : renderTable
	});
};

function checkLogin() {

	function getIsLogin(){
	    return $.ajax({
	    	type: "GET",
			url: rootUrl+"/isLogin",
			dataType : "json"
	    })
	}
	getIsLogin().done(function(result) {
		if(result == true){	//ログインしてたら表示
			findLendingBook();
		}
		else{	//ログインしてたら表示
			alert('ログインしてください');
			location.href='login.html'
		}

	}).fail(function(result) {
		alert('ログインしてるかわからん');
	});
};

/*
function checkLogin() {

	function getIsLogin(){
	    return $.ajax({
	    	type: "GET",
			url: rootUrl+"/isLogin",
			dataType : "json"
	    })
	}


	getIsLogin().done(function(result) {
		console.log('isloginAAAAAAAAAA='+result);

		function isManagerLogin(){
		    return $.ajax({
		    	url : rootUrl + "/isManagerLogin",
				type : "GET",
				dataType : "json",
		    })
		}
		getIsLogin().done(function(management) {

			setButton(result,management);

		}).fail(function(result) {

			alert('管理者かわからん');

		});

	}).fail(function(result) {
		alert('ログインしてるかわからん');
	});
};
*/

function renderTable(data) {

	console.log('renderTable start.')
	console.log(data);

	var headerRow = '<tr><th>タイトル</th><th>返却予定日</th><th colspan="2">返却</th></tr>';

	if ( data == undefined || data.length === 0) {
		$('#table').append('<p>現在、貸出中の書籍は存在しません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, lendingBook) {
			var row = $('<tr>');
			var color;

			var date = new Date(lendingBook.returnDate);

			var now = new Date();
			if (date < now) {
				color = 'red';
			}else{
				color = 'black';
			}


			//var returnButton  = '<button colspan="3" class="grayButton">すべて返却</button>';
			row.append($('<td>').text(lendingBook.title));
			//row.append($('<td nowrap>').text(lendingBook.borrower));

			row.append($('<td nowrap>').text(lendingBook.returnDate)).attr("class", color);
			row.append(
				$('<td nowrap>').append(
					$('<button>').text("返却").attr("type","button").attr("onclick", 'returnBookById('+lendingBook.id+')').attr("class", color+' grayButton')
				).attr("id",lendingBook.id).attr("class",'returnButtongray')
			);
			if(index==0){
				row.append
					($('<td nowrap>').append(
						$('<button>').text("すべて返却").attr("type","button").attr("onclick", 'returnBookAll()').attr("class",' blueButton')
					).attr("class",'returnButton').attr("rowspan",data.length)
				);
			}

			table.append(row);
		});

		$('#table').append(table);
	}
}

function returnBookById(returnId) {
	//alert(returnId+'を返却する予定');
	console.log('returnById start.')
//	var param =
//		{
//			id : returnId
//		};
	$.ajax({
		type : "GET",
		url : rootUrl + '/returnBook/'+returnId,
		//data : param,
		dataType : "json",
		success : function(data) {
			console.log(data);
			var tableHTML='';
			if (data == true) {
				$('#'+returnId).text('(済み)');
				alert('返却しました');
			} else {
				alert('返却失敗');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	});
}

function returnBookAll() {
	alert('すべて返却する予定');
	console.log('returnBookAll start.')

	$.ajax({
		type : "GET",
		url : rootUrl + '/returnBook/',
		dataType : "json",
		success : function(data) {
			console.log(data);
			var tableHTML='';
			if (data > 0) {
				$('.returnButton').text('済み');
				alert(data+'冊返却しました');;
			} else {
				alert('返却失敗');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	});
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
