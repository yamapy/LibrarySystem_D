'use strict';

var rootUrl = "/LibrarySystem_D/api/v1.1/createBook";

$(function() {

	/* 以下カッコ内はボタンID */
	$('#returnManageTopPage')
			.click(
					function() {
						window.location.href = 'http://localhost:8080/LibrarySystem_D/html/managementTop.html';
					});

})

$('#saveBook').click(function() {
	$('.error').children().remove();
	if ($('#bookTitle').val() === '') {
		$('.error').append('<div>タイトルは必須入力です。</div>');
	}
	if ($('#author').val() === '') {
		$('.error').append('<div>作者は必須入力です。</div>');
	}
	if ($('#publisher').val() === '') {
		$('.error').append('<div>出版社は必須入力です。</div>');
	}
	if ($('#genre').val() === '') {
		$('.error').append('<div>ジャンルは必須入力です。</div>');
	}
	if ($('#puchaseDate').val() === '') {
		$('.error').append('<div>購入日は必須入力です。</div>');
	}
	if ($('#buyer').val() === '') {
		$('.error').append('<div>購入者は必須入力です。</div>');
	}
	if ($('.error').children().length != 0) {
		return false;
	}

	/* idは欄としてつくるのか？ */
	var id = $('#id').val()
	if (id === '')
		addBook();
	else
		updateBook(id);
	return false;
})

/*追加*/
function addBook() {
	console.log('addBook start');
	var fd = new FormData(document.getElementById("createBook"));
	$.ajax({
		url : rootUrl,
		type : "POST",
		data : fd,
		contentType : false,
		processData : false,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			alert('書籍の追加に成功しました');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('書籍の追加に失敗しました');
		}
	})
}