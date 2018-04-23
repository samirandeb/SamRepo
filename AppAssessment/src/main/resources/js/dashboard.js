$(document).ready(function() {
       var table = $('#exportTable').DataTable({
              dom : "<'row'<'col-sm-12 dataTables_filter' B>>" + "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-5'i><'col-sm-7'p>>",
              buttons : [ 'copy', 'csv', 'pdf', 'print','excel' ]

       });

       $('#applications').DataTable();

});
