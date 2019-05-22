/**
 *
 */
var loginButton =		$('<button>').text("ログイン").attr("type","button").attr("onclick", "location.href='login.html'").attr("class","blueButton");
var logoutButton =		$('<button>').text("ログアウト").attr("type","button").attr("onclick", "logOut()").attr("class","blueButton");
var myPageButton =		$('<button>').text("マイページ").attr("type","button").attr("onclick", "location.href='myPage.html'").attr("class","blueButton");
var manageButton =		$('<button>').text("管理者ページ").attr("type","button").attr("onclick", "location.href='bookList.html'").attr("class","blueButton");
var bookButton =		$('<button>').text("書籍一覧").attr("type","button").attr("onclick", "location.href='bookList.html'").attr("class","blueButton");

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";


setup();

function setup(){
//	var islogin;
	var returnResult;
	function getIsLogin(){
	    return $.ajax({
	    	type: "GET",
			url: rootUrl+"/isLogin",
			dataType: "text"
	    })
	}
	getIsLogin().done(function(result) {
		//islogin = result
		console.log('isloginAAAAAAAAAA='+result);
		//returnResult = result;
		setButton(result)
	}).fail(function(result) {
		alert('ログインしてるかわからん');
		//returnResult = null;
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
function setButton(loginStatus){

	// 拡張子なしで
	var fileName = window.location.href.match(".+/(.+?)\.[a-z]+([\?#;].*)?$")[1];

	var buttons = $('<div>').attr('align','right');
	//console.log('isLogin='+isLogin())

	switch( fileName ) {
	    case 'myPage':
	    	buttons.append(manageButton);
	    	buttons.append(bookButton);
	    	buttons.append(logoutButton);
	        break;
	    case 'managementTop':
	    	buttons.append(manageButton);
	    	buttons.append(bookButton);
	    	buttons.append(logoutButton);
	        break;
	    case 'bookList':
	    	if( loginStatus == 'true'){
	    		buttons.append(logoutButton);

	    	}else{
	    		buttons.append(loginButton);
	    	}

	    	break;

	    	break;
	    case 'login':
	    case 'createBook':
	    case 'createUser':
	        break;

	    default:
	    		alert(fileName+'に対応するボタンリストが宣言されていません')
	}

	//$('#buttonList').append(buttons);
	$("body").prepend(buttons);

}
