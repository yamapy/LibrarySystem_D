$('#general-login-button').click(function() {
	$('.error').children().remove();
	if ($('#mailAdress').val() === '') {
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
	if ($('#mailAdress').val() === '') {
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
	var fd = new FormData(document.getElementById("loginForm"));

	console.log($('#mailAdress').val());
	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/generalLogin",
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if (data == true) {
				alert('ログイン成功');
				// location.href = './bookList.html';
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
	var fd = new FormData(document.getElementById("loginForm"));

	console.log($('#mailAdress').val());
	$.ajax({
		url : "/LibrarySystem_D/api/v1.1/resources/mamnagerLogin",
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if (data == true) {
				alert('ログイン成功');
				// location.href = './bookList.html';
			} else {
				alert('ログインに失敗しました');
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})

}
