$(document).ready(function() {
	var table = $('#heatmap').DataTable({
		dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-5'i><'col-sm-7'p>>",
		buttons : [ 'copy', 'csv', 'pdf', 'print',{
            extend: 'excelHtml5',
            customize: function(xlsx) {
                var sheet = xlsx.xl.worksheets['sheet1.xml'];
                // Loop over the cells in column `F`
                $('row c[r^="C"]', sheet).each( function () {
                	console.log($(this).val);
                    // Get the value and strip the non numeric characters
                      $(this).attr( 'innerHTML', '20' );
                });
            }
        } ],
		createdRow : function(row, data, index) {
			for (i = 2; i < 12; i++) {
				var val = data[i];
				if (val < 0) {
					$('td', row).eq(i).addClass('negative');
				}
				if (val <= 30 && val>=0) {
					$('td', row).eq(i).addClass('low');
				}
				if (val <= 70 && val > 30) {
					$('td', row).eq(i).addClass('moderate');
				}
				if (val > 70) {
					$('td', row).eq(i).addClass('high');
				}
			}
		}
	});
});