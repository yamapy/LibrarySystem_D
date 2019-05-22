/**
 *
 */
var loginButton =		$('<button>').text("ログイン").attr("type","button").attr("onclick", "location.href='login.html'").attr("class","blueButton");
var logoutButton =		$('<button>').text("ログアウト").attr("type","button").attr("onclick", "logOut()").attr("class","orangeButton");
var myPageButton =		$('<button>').text("マイページ").attr("type","button").attr("onclick", "location.href='myPage.html'").attr("class","blueButton");
var manageButton =		$('<button>').text("管理者ページ").attr("type","button").attr("onclick", "location.href='managementTop.html'").attr("class","grayButton");
var bookButton =		$('<button>').text("書籍一覧").attr("type","button").attr("onclick", "location.href='bookList.html'").attr("class","blueButton");
var blank = $('<a>').text(" ")
var blank2 = $('<a>').text(" ")

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";


setup();

function setup(){
//	var islogin;
//	var returnResult;
	function getIsLogin(){
	    return $.ajax({
	    	type: "GET",
			url: rootUrl+"/isLogin",
			dataType : "json"
	    })
	}
	getIsLogin().done(function(result) {
		console.log('isloginAAAAAAAAAA='+result);

		function isManagerLogin(){
		    return $.ajax({
		    	url : rootUrl + "/isManagerLogin",
				type : "GET",
				dataType : "json",
		    })
		}
		getIsLogin().done(function(management) {

			setButton(result,management);

		}).fail(function(result) {

			alert('管理者かわからん');

		});

	}).fail(function(result) {
		alert('ログインしてるかわからん');
	});
}

function logOut() {
	console.log('logout');

	$.ajax({
		type : "GET",
		url : rootUrl + "/logout",
		dataType : "json",
		success : function(data) {
			if (data == true) {
				console.log("logout ok");
				alert('ログアウトしました')
				location.href = './bookList.html';
			} else {
				console.log("logout ng");
				alert('ログアウトできません')
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('通信失敗');
		}
	})
}
/*

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

*/
function setButton(loginStatus,management){

	// 拡張子なしで
	var fileName = window.location.href.match(".+/(.+?)\.[a-z]+([\?#;].*)?$")[1];

	var buttons = $('<div>').attr('align','right');
	//console.log('isLogin='+isLogin())

	switch( fileName ) {
	    case 'myPage':
	    	if(management==true){
	    		buttons.append(manageButton);
	    		buttons.append(blank);
			}
	    	buttons.append(bookButton);
	    	buttons.append(blank2);
	    	buttons.append(logoutButton);
	        break;
	    case 'managementTop':
	    	buttons.append(bookButton);
	    	buttons.append(blank2);
	    	buttons.append(logoutButton);
	        break;
	    case 'bookList':
	    	if( loginStatus == true){
	    		if( management == true ){
	    			buttons.append(manageButton);
	    			buttons.append(blank);
	    		}
	    		buttons.append(myPageButton);
	    		buttons.append(blank2);
	    		buttons.append(logoutButton);
	    	}else{
	    		buttons.append(loginButton);
	    	}

	    	break;

	    	break;
	    case 'lendingBookList':
	    	buttons.append(logoutButton);
	        break;
	    case 'login':
	    case 'createBook':
	    case 'createUser':
	        break;

	    default:
	    		alert(fileName+'.htmlに対応するボタンリストが"buttonList.js"で宣言されていません')
	}

	$("body").prepend(buttons);

}
