/**
 *
 */
var loginButton = $('<button>').text("ログイン").attr("type","button").attr("onclick", "location.href='login.html'");
var logoutButton = $('<button>').text("ログアウト").attr("type","button").attr("onclick", "location.href='bookList.html'");
var manageButton = $('<button>').text("管理者ページ").attr("type","button").attr("onclick", "location.href='bookList.html'");
var bookButton = $('<button>').text("書籍一覧").attr("type","button").attr("onclick", "location.href='bookTList.html'");

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

setButton();

function isLogin(){
	$.ajax({
		type: "GET",
		url: rootUrl+"/isLogin",
		dataType: "text",
		success: function(data) {
			isLogin = data
			console.log(isLogin);
		}
	});
	return isLogin;
}
function setButton(){

	// 拡張子なしで
	var fileName = window.location.href.match(".+/(.+?)\.[a-z]+([\?#;].*)?$")[1];

	var buttons = $('<div>').attr('align','right');
	console.log('isLogin='+isLogin())

	switch( fileName ) {
	    case 'myPage':
	    	buttons.append(logoutButton);
	        break;
	    case 'bookList':
	    	if(isLogin){
	    		buttons.append(loginButton);
	    	}else{
	    		buttons.append(logoutButton);
	    	}

	    	break;
	    case 'login':
	    case 'createBook':
	    case 'createUser':
	        break;

	    default:
	    		alert(fileName+'に対応するボタンリストが宣言されていません')
	}

	$('#buttonList').append(buttons);

}
