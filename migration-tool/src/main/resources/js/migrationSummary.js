$(document).ready(function() {
	selectedItems.length = 0;
	var table = $('#datatableLM').DataTable({
		lengthChange : true,
		dom : "<'row'<'col-sm-12 dataTables_filter' B>>" + "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>",
		buttons : [ {
			extend : 'excel',
			text : ' Export',
			className : 'btn-data-table fa fa-file-excel-o',
			filename : 'migrationSummary',
			exportOptions : {
				columns : ':visible'
			}
		}, {
			extend : 'colvis',
			text : ' Visible Columns',
			className : 'btn-data-table fa fa fa-eye-slash',

		} ]
	});
});



