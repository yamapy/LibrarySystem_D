/**
 *
 */

var loginButton = $('<button>').text("ログイン").attr("type","button").attr("onclick","button");
var logoutButton = $('<button>').text("ログアウト").attr("type","button");

buttonList();

function buttonList() {
	console.log('renderButton start.')
	var row = $('<td>');
	row.append(loginButton)
	row.append(logoutButton)

	$('#buttonList').append(row);
}
