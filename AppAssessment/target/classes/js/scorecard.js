$(document).ready(function() {
       var table = $('#scoreCard').DataTable({
              dom : "<'row'<'col-sm-12 dataTables_filter' B>>" + "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>",          buttons : [ 'copy', 'csv', 'pdf', 'print','excel' ],
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

       $('#heatmap').DataTable();

});
