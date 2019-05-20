init();

function init() {
	// 入力されたユーザーIDとパスワード

//	var requestQuery = {
//		id : $('#login-id').val(),
//		pass : $('#login-pass').val()
//	};

	console.log('renderTable start');
	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/myPage",
		type : "POST",
//		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
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



$(document).ready(function() {
	// ログインボタンを押したときのイベント
	$('#login-button').click(login);
});
