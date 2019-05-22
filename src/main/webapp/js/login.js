'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

getUser();
logout();

$('#general-login-button').click(function() {
	$('.error').children().remove();
	if ($('#mailAddress').val() === '') {
		$('.error').append('<div>メールアドレスを入力てください。</div>');
	}
	if ($('#password').val() === '') {
		$('.error').append('<div>パスワードを入力してください。</div>');
	}
	if ($('.error').children().length != 0) {
		return false;
	}
	generalLogin();
	return false;

})

$('#manager-login-button').click(function() {
	$('.error').children().remove();
	if ($('#mailAddress').val() === '') {
		$('.error').append('<div>メールアドレスを入力てください。</div>');
	}
	if ($('#password').val() === '') {
		$('.error').append('<div>パスワードを入力してください。</div>');
	}
	if ($('.error').children().length != 0) {
		return false;
	}
	managerLogin();
	return false;

})

$('#create-user-button').click(function() {
	location.href = './createUser.html';
})

$('#back-to-bookList-button').click(function() {
	location.href = './bookList.html';
})

function logout() {
	console.log('logout start');
	$('.error').children().remove();

	$.ajax({
		type : "GET",
		url : rootUrl + "/logout",
		dataType : "json",
		success : function(data) {
			if (data == true) {
				console.log("logout ok");

			} else {
				console.log("logout ng");
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

function managerLogin() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		id : $('#mailAddress').val(),
		pass : $('#password').val()
	};
	console.log($('#mailAddress').val());
	$.ajax({
		url : rootUrl + "/mamnagerLogin",
		type : "POST",
		data : requestQuery,
		dataType : "json",
		success : function(data) {
			console.log(data);
			if (data == true) {
				alert('ログイン成功');
				location.href = './managementTop.html';
			} else {
				alert('ログインに失敗しました');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}

function getUser(user) {
	console.log('getUser start.');

//<<<<<<< HEAD
//	// ログインボタンを押したときのイベント
//	$('#login-button').click(login);
//});
//=======
	$.ajax({
		type : "GET",
		url : rootUrl + "/isManagerLogin",
		dataType : "json",
		success : function(data) {
			if (data == true) {
				console.log("ok");
			} else {
				console.log("ng");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}

function getMailAddress() {
	console.log('get mailAddress start');
	$.ajax({
		url : rootUrl + "/getLoginMailAddress",
		type : "GET",
		// data : requestQuery,
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

function isLogin() {
	console.log('isLogin start');
	$.ajax({
		url : rootUrl + "/isLogin",
		type : "GET",
		// data : requestQuery,
		dataType : "json",
		success : function(data) {
			if (data == true) {
				alert('ログインしてます');
			} else {
				alert('ログインしてません');
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}

function isManagerLogin() {
	console.log('isManagerLogin start');
	$.ajax({
		url : rootUrl + "/isManagerLogin",
		type : "GET",
		// data : requestQuery,
		dataType : "json",
		success : function(data) {
			if (data == true) {
				alert('管理者ログインしてます');
			} else {
				alert('管理者ログインしてません');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}