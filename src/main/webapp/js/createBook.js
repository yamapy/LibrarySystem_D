'use strict';

// LendingBookResources.java内の@Path("lendingBook")
var rootUrl = "/LibrarySystem_D/api/v1.1/lendingBook";
var postUrl = "/LibrarySystem_D/api/v1.1/lendingBook/createBook";

// Resources.java内の@Path("resources")
var genreUrl = "/LibrarySystem_D/api/v1.1/resources/genre";

initPageGenre();
makeGenreSelection();

$(function() {

	/* 以下カッコ内はボタンID */
	$('#returnManageTopPage')
			.click(
					function() {
						window.location.href = './managementTop.html';
					});

})

/* 書籍の登録動作 */
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

	addBook();
	return false;
})

/* 書籍追加のメソッド */
function addBook() {

	console.log('addBook start');

	var info = document.getElementById("genre").disabled;
	document.getElementById("genre").disabled = false;

	var fd = new FormData(document.getElementById("createBookForm"));


	$.ajax({
		url : postUrl,
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
	document.getElementById("genre").disabled = info;
}

/* フォーム要素の内容が変更されたときにイベント処理を実行できる */
$('select').change(function() {
	var text = $('option:selected').text();

	// 変数targetに、入力不可にしたい項目を定義
	//var target = document.getElementById("genre");
	if (text === '新規作成') {
		$('#genre').val('');
		// 変数testの要素を入力可にする
		document.getElementById("genre").disabled = false;
	} else {
		$('#genre').val(text);
		// 変数testの要素を入力不可にする
		document.getElementById("genre").disabled = true;
	}
});



function initPageGenre() {
	var newOption = $('<option>').val("").text('新規作成').prop('selected', true);
	$('#genreParam').append(newOption);
	makeGenreSelection('#genreParam');

	/* findAll(); */
}

function makeGenreSelection(selectionGenre, book) {
	console.log('makeGenreSelection start.')
	$.ajax({
		type : "GET",
		url : genreUrl,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$.each(data, function(index, book) {
				var newOption = $('<option>').val(book.genre).text(book.genre);

				$(selectionGenre).append(newOption);
			});
		}
	});

}