'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

getUser();

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
				 location.href = './login.html';
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
			id : $('#mailAddress').val()
			,pass:$('#password').val()
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
				 location.href = './login.html.html';
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

	$.ajax({
		type : "GET",
		url : rootUrl + "/isManagerLogin",
		dataType : "json",
		success : function(data){
			if(data ==true){
				console.log("ok");
			}else{
				console.log("ng");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}