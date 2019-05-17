/**
 *
 */

var rootUrl = "/LibrarySystem_D/api/v1.1/resources";

initPage();

function initPage() {
	findAll();
}

function findAll() {
	console.log('findAll start.')

	$.ajax({
		type : "GET",
		url : rootUrl,
		dataType : "json",
		success : renderTable
	});
}

function renderTable(data) {
	var headerRow = '<tr><th>社員ID</th><th>氏名</th></tr>';

	$('#employees').children().remove();

	if (data.length === 0) {
		$('#employees').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);

		$.each(data, function(index, employee) {
			var row = $('<tr>');
			row.append($('<td>').text(employee.empId));
			row.append($('<td>').text(employee.name));
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick", "findById("+employee.id+')')
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick", "deleteById("+employee.id+')')
				));
			table.append(row);
		});

		$('#employees').append(table);
	}
}

