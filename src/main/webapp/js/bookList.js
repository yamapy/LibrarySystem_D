'use strict';


var loginButton = '<buttoon>ログイン<button>'
var logoutinButton = '<buttoon>ログアウト<button>'

function findLendingBook() {
	console.log('renderButton start.')
	$('#buttonList').text();

	$('#buttonList').append(loginButton);
}

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

findAll();
initPageGenre();
makeGenreSelection();
//if(isLogin == "false"){
//	loginButtton()
//}

function findAll() {
	console.log('findAll start.')
	$.ajax({
		type : "GET",
		url : rootUrl+"/book",
		dataType : "json",

		success : renderTable
	});
}


var isLogin;

function loginButton(){
	$('#login').append('<button>ログイン</button>')
}


function renderTable(data) {
	$.ajax({
		type: "GET",
		url: rootUrl+"/isLogin",
		dataType: "text",
		success: function(data) {

			isLogin = data
			console.log(isLogin);

		}
	});
	console.log(data)
//
//	if(isLogin == "true"){
//
//		var headerRow = '<tr><th>タイトル</th><th>ジャンル</th><th>作者</th><th>詳細</th><th>貸出</th>';
//
//				$('#book').children().remove();
//
//
//
//				if (data.length === 0) {
//					$('#book').append('<p>現在データが存在していません。</p>')
//				} else {
//					var table = $('<table>').attr('border', 1);
//					table.append(headerRow);
//
//
//					$.each(data, function(index, book) {
//						var row = $('<tr>');
//						row.append($('<td>').text(book.title).attr("id","title"));
//						row.append($('<td nowrap>').text(book.genre).attr("id","genre"));
//						row.append($('<td>').text(book.author).attr("id","author"));
//						row.append($('<td nowrap>').append(
//								$('<button>').text("詳細").attr("type","button").attr("id","bookDetail")
//						));
//
//						if(book.status=="貸出中"){
//						row.append($('<td nowrap>').text(book.status).attr("id","status").attr("class","red"));
//						}else{
//							row.append($('<td nowrap>').append(
//									$('<button>').text("借りる").attr("type","button").attr("id",book.id).attr("class","borrow")
//							));
//						}
//
//						table.append(row);
//					});
//
//					$('#book').append(table);
//					$('.borrow').click(borrowById);
//				}
//
//
//
//	}

//	else{
//
//		//loginButton();
//
//
//		var headerRow = '<tr><th>タイトル</th><th>ジャンル</th><th>作者</th><th>詳細</th><th>ステータス</th>';
//
//		$('#book').children().remove();
//
//
//
//		if (data.length === 0) {
//			$('#book').append('<p>現在データが存在していません。</p>')
//		} else {
//			var table = $('<table>').attr('border', 1);
//			table.append(headerRow);
//
//
//			$.each(data, function(index, book) {
//				var row = $('<tr>');
//				row.append($('<td>').text(book.title).attr("id","title"));
//				row.append($('<td>').text(book.genre).attr("id","genre"));
//				row.append($('<td>').text(book.author).attr("id","author"));
//				row.append($('<td>').append(
//						$('<button>').text("詳細").attr("type","button").attr("id","bookDetail").attr("class","grayButton")
//				));
//
////				row.append($('<td>').text(book.status));
//				if(book.status=="貸出中"){
//					row.append($('<td nowrap>').text(book.status).attr("id","status").attr("class","red"));
//				}else
//					row.append($('<td nowrap>').text(book.status).attr("id","status"));
//
//
//				table.append(row);
//			});
//
//			$('#book').append(table);
//		}
//
//
//
//
//	}

	var headerRow = '<tr><th>タイトル</th><th>ジャンル</th><th>作者</th><th>詳細</th>';

	if(isLogin == "true")
		headerRow+='<th>貸出</th>';
	else
		headerRow+='<th>ステータス</th>';
//
	$('#book').children().remove();



	if (data.length === 0) {
		$('#book').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);


		$.each(data, function(index, book) {
			var row = $('<tr>');
			row.append($('<td>').text(book.title).attr("id","title"));
			row.append($('<td nowrap>').text(book.genre).attr("id","genre"));
			row.append($('<td>').text(book.author).attr("id","author"));
			row.append($('<td nowrap>').append(
					$('<button>').text("詳細").attr("type","button").attr("id","bookDetail").attr("class","grayButton")
			));







			if(isLogin == "true"){
				if(book.status=="貸出中"){
					row.append($('<td nowrap>').text(book.status).attr("id","status").attr("class","red"));
				}else{
					row.append($('<td nowrap>').append(
							$('<button>').text("借りる").attr("type","button").attr("id",book.id).attr("class","borrow")
					));
				}
			}
			else{
				var statusString = $('<td nowrap>').text(book.status);
				if(book.status=="貸出中"){
					statusString.attr("class","red");
				}
				row.append(statusString);
			}
			table.append(row);
		});

		$('#book').append(table);
		if(isLogin == "true")
			$('.borrow').click(borrowById);
	}
}




$('#findBook').click(function() {
	findByParam();
	return false;
})

//$('.borrow').click(function() {
//	borrowById();
//})

function initPageGenre() {
	var newOption = $('<option>').val("").text('指定しない').prop('selected', true);
	$('#genreParam').append(newOption);
	makeGenreSelection('#genreParam');
	findAll();
}


function findByParam() {
	console.log('findByParam start.');

	var urlWithParam = rootUrl+"/findByParam"+'?titleParam='+$('#titleParam').val()
		+'&authorParam='+$('#authorParam').val()
		+'&genre='+$('#genreParam').val()
		+'&status='+$('#statusParam').val();
	console.log( urlWithParam);
	$.ajax({
		type : "GET",
		url : urlWithParam,
		dataType : "json",
		success : renderTable
	});
}

function makeGenreSelection(selectionGenre, book) {
	console.log('makeGenreSelection start.')
	$.ajax({
		type : "GET",
		url : rootUrl+"/genre",
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$.each(data, function(index, book) {
				var newOption = $('<option>').val(book.genre).text(book.genre);

				$(selectionGenre).append(newOption);
			});
		}
	});

}

function borrowById(button) {
	var id = button.currentTarget.id;
	console.log('borrow start - id:' + id);
//	var fd = {id:id};
	$.ajax({
		type : "POST",
		url : rootUrl + '?id='+id,
//		data:fd,
		dataType:'json',
		success : function(data) {
			alert('借りました');
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('貸出失敗');
		}
	});
}

function findById(id) {
	console.log('findByID start - id:' + id);
	$.ajax({
		type : "GET",
		url : rootUrl + '/' + id,
		dataType : "json",
		success : function(data) {
			alert('借りました');
			console.log('findById success: ' + data.name);
			renderDetails(data)
		}
	});
}

function renderDetails(book) {
	$('.error').text('');
	$('#title').val(book.title);
	$('#genre').val(book.genre);
	$('#author').val(book.author);
}

function getMailAddress() {
	console.log('get mailAddress start');
	$.ajax({
		url : rootUrl + "/getLoginMailAddress",
		type : "GET",
//		data : requestQuery,
		dataType : "json",
		success : function(data) {
			if (data == '') {
				alert('取得失敗');
			} else {
				alert('取得成功');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}
