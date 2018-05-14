$(document).ready(function() {
// 		console.log($('.nav-tabs .active').attr('name'));
		var targetID = '#' + $('.nav-tabs .active').attr('name');
		var url = "regionSpecificHistory/" + $('.nav-tabs .active').attr('name');
// 		console.log(url);
		var dataTableID = '#'+$('.nav-tabs .active').attr('name')+"HistoryTable";
//		console.log(dataTableID);
		$(targetID).load(url, function() {
			$(dataTableID).dataTable({
				lengthChange : true,
				dom : "<'row'<'col-sm-12 dataTables_filter' B>>" + "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>",
				buttons : [ {
					extend : 'excel',
					text : ' Export',
					className : 'btn-data-table fa fa-file-excel-o',
					filename : 'migrationDashboard',
					exportOptions : {
						columns : ':visible'
					}
				}, {
					extend : 'colvis',
					text : ' Visible Columns',
					className : 'btn-data-table fa fa fa-eye-slash',
					collectionLayout : "three-column"

				} ],
				scrollX:        true,
			    scrollCollapse: false,
				fixedColumns:   {
			        leftColumns: 4
			    }
			});
		});

	});