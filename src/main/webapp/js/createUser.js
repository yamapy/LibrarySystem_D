'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

findAllEmployees();
findAllUserMailAddresses();
logout();

$('#createUser-login-button')
		.click(
				function() {
					$('.error').children().remove();
					if ($('#mailAddress').val() === '') {
						$('.error').append('<div>メールアドレスを入力てください。</div>');
					}
					if ($('#mailAddress').val() != ''
							&& $('#employeeName').val() === '') {
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
					if (checkIsUser()) {
						return false;
					}

					createUserLogin();
					return false;
				})

// 検索結果を格納するための配列を用意
var employeeInfo = [];
var userMailAddresses = [];
var empName;

function findAllEmployees() {
	console.log('findAllEmployees start');
	$.ajax({
		type : "GET",
		url : rootUrl + "/getEmployeeName",
		dataType : "json",
		success : renderEmpInfo

	});
}

function findAllUserMailAddresses() {
	console.log('findAllUsers start');
	$.ajax({
		type : "GET",
		url : rootUrl + "/getUserMailAddresses",
		dataType : "json",
		success : renderUserInfo

	});

}

function renderEmpInfo(data) {
	employeeInfo = [];
	for (var i = 0; i < Object.keys(data).length; i++) {
		employeeInfo.push(data[i]);
	}
	console.log(employeeInfo.length + " Employees");
}

function renderUserInfo(data) {
	userMailAddresses = [];
	for (var i = 0; i < Object.keys(data).length; i++) {
		userMailAddresses.push(data[i]);
	}
	console.log(userMailAddresses.length + " Users");
}


function searchWord() {
	var searchText = $('#mailAddress').val(); // 検索ボックスに入力された値
	/*
	console.log('append');
	var empName = 'バカ';
	$("#employeeName").append(empName);

	*/
	// 検索結果エリアの表示を空にする
	$('#employeeName').text('');
	// 検索ボックスに値が入ってる場合
	if (searchText != '') {
		// employeeInfo.each(function() {
		for (var i = 0; i < employeeInfo.length; i++) {
			if (employeeInfo[i].mailAddress === searchText) {
				empName = employeeInfo[i].name;
				$("#employeeName").append(empName);
				return;
			}
		}
		$("#employeeName").append('(該当社員なし)');
		return;
	}
	$("#employeeName").text('(該当する社員名を表示)');
};

$("#mailAddress").keyup(function() {
	console.log('focus');
	searchWord();
	// $("#employeeName").val(empName);
	console.log(empName);
});

function checkIsUser() {
	if ($('#employeeName').val() != '') {
		for (var i = 0; i < userMailAddresses.length; i++) {
			if (userMailAddresses[i] === $('#mailAddress').val()) {
				alert('このメールアドレスは登録済みです。');
				return true;
			}
		}return false;
	}
}

function createUserLogin() {
	// 入力されたユーザーIDとパスワード
	var fd = new FormData(document.getElementById("userForm"));

	console.log($('#mailAddress').val());
	$.ajax({
		url : rootUrl + "/createUser",
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if (data == true) {
				alert('新規ユーザー登録完了。');
				generalLogin();
			} else {
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
		id : $('#mailAddress').val(),
		pass : $('#password').val()
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
				location.href = './bookList.html';
			} else {
				alert('ログインに失敗しました');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}

function logout() {
	$.ajax({
		type : "GET",
		url : rootUrl + "/logout",
		dataType : "json",
		success : function(data) {
			if (data == true) {
				console.log("logout ok");
				location.href = './bookList.html';
			} else {
				console.log("logout ng");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}
