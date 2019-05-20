'use strict';

findAllName();

$('#createUser-login-button').click(function() {
	$('.error').children().remove();
	if ($('#mailAdress').val() === '') {
		$('.error').append('<div>メールアドレスを入力てください。</div>');
	}
	if (($('#password').val() === '') || ($('#password2').val() === '')) {
		$('.error').append('<div>パスワードは同じものを二回入力してください。</div>');
	} else if ($('#password').val() != $('#password2').val()) {
		$('.error').append('<div>パスワードが一致しません。</div>');
	}
	if ($('.error').children().length != 0) {
		return false;
	}
	createUserLogin();

})

// 検索結果を格納するための配列を用意
var employeeInfo = [];

function findAllName() {
	console.log('findAllName start');
	$.ajax({
		type : "GET",
		url : "/LibrarySystem_D/api/v1.1/resources/getEmployeeName",
		dataType : "json",
		success : function(data) {
			console.log('findById success: ');
			employeeInfo.put(data)
		}
	});
}

// $(function() {
// var $input = $('#input');
// var $output2 = $('#output2');
// $input.on('change', function(event) {
// $output2.text($input.val());
// });
// });
//
//searchWord = function() {
//	var searchText = $(this).val(); // 検索ボックスに入力された値
//
//	// 検索結果エリアの表示を空にする
//	$('#search-result__list').empty();
//	$('.search-result__hit-num').empty();
//
//	// 検索ボックスに値が入ってる場合
//	if (searchText != '') {
//		employeeInfo.each(function() {
//			for (var i = 0; i < employeeInfo.length; i++) {
//				if (employeeInfo[i] === searchText) {
//					$('#employeeName').val(employeeInfo[i].name);
//				}
//			}
//		});
//
//	}
//};
//
//// searchWordの実行
//$('#search-text').on('input', searchWord);

function createUserLogin() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		id : $('#mailAdress').val(),
		pass : $('#login-pass').val()
	};
	console.log($('#mailAdress').val());
	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/generalLogin",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
			if (data == true) {
				alert('登録ログイン成功');
				// location.href = './bookList.html';
			} else {
				alert('登録ログインに失敗しました');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}
