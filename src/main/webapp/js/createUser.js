'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

findAllName();

$('#createUser-login-button')
		.click(
				function() {
					$('.error').children().remove();
					if ($('#mailAddress').val() === '') {
						$('.error').append('<div>メールアドレスを入力てください。</div>');
					}
					if ($('#mailAddress').val() != '' && $('#employeeName').val() === '') {
						$('.error').append('<div>メールアドレスが正しくありません。</div>');
					}
					if (($('#password').val() === '')
							|| ($('#password2').val() === '')) {
						$('.error').append('<div>パスワードは同じものを二回入力してください。</div>');
					}
					if (($('#password').val() != '')
							&& ($('#password2').val() != '')
							&& $('#password').val() != $('#password2').val()) {
						$('.error').append('<div>パスワードが一致しません。</div>');
					}
					if ($('.error').children().length != 0) {
						return false;
					}
					createUserLogin();
					return false;
				})

// 検索結果を格納するための配列を用意
var employeeInfo = [];
var empName;

function findAllName() {
	console.log('findAllName start');
	$.ajax({
		type : "GET",
		url : rootUrl + "/getEmployeeName",
		dataType : "json",
		success : renderEmpInfo

	});
}

function renderEmpInfo(data) {
	employeeInfo = [];
	for (var i = 0; i < Object.keys(data).length; i++) {
		employeeInfo.push(data[i])
	}
	console.log(employeeInfo.length);
}

// // searchWordの実行
// $('#mailAddress').on('change', searchWord);

function searchWord() {
	var searchText = $('#mailAddress').val(); // 検索ボックスに入力された値

	// 検索結果エリアの表示を空にする
	 $('#employeeName').val('');
	// 検索ボックスに値が入ってる場合
	if (searchText != '') {
		// employeeInfo.each(function() {
		for (var i = 0; i < employeeInfo.length; i++) {
			if (employeeInfo[i].mailAddress === searchText) {
				empName = employeeInfo[i].name;
				$("#employeeName").val(empName);
			}
		}
		// });

	}
};

$("#mailAddress").focusout(function() {
	searchWord();
//	$("#employeeName").val(empName);
	console.log(empName);
});

function createUserLogin() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
			id : $('#mailAddress').val()
			,pass:$('#password').val()
	};
	console.log($('#mailAddress').val());
	$.ajax({
		url : rootUrl + "/createUser",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			if(data == true){
				alert('新規ユーザー登録完了。');
				generalLogin();
			}else{
				alert('新規ユーザー登録に失敗しました。');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}

function generalLogin() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
			id : $('#mailAddress').val()
			,pass:$('#password').val()
	};
	console.log($('#mailAddress').val());
	$.ajax({
		url : rootUrl + "/generalLogin",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
			if (data == true) {
				alert('ログイン成功');
				 location.href = './testLogin.html';
			} else {
				alert('ログインに失敗しました');
				location.href = './login.html';
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}
