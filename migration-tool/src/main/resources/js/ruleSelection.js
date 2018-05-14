var selectedItems = [];
var checkBoxPredicate = '.selectRule:checkbox';
$(document).ready(function() {
	selectedItems.length = 0;

	// $('#datatable').dataTable({
	// lengthChange: false,
	// buttons: [ 'copy', 'excel', 'pdf', 'colvis' ]
	// } );

	$("#datatable").dataTable();

	// table.buttons().container().appendTo(
	// '#datatable_wrapper.col-sm-6:eq(0)' );

	$("[data-toggle=tooltip]").tooltip();

	$(checkBoxPredicate).each(function() {
		this.checked = false;
	});

	$("#selectType").change(function() {
		$("#changeButton").css('display', 'block');
	});

});

function updateRuleSelection(ruleGuid, checkboxId) {
	if ($('#' + checkboxId).is(':checked')) {
		selectedItems.push(ruleGuid);
//		console.log("Rule Selected : " + ruleGuid)
	} else {
		var index = selectedItems.indexOf(ruleGuid);
		if (index > -1) {
			selectedItems.splice(index, 1);
//			console.log("Rule de-selected : " + ruleGuid);
		}
	}

//	console.log("Selected Items : " + selectedItems);
	$('#selectedRules').val(selectedItems);
}

function selectedRulesForMigration() {
//	console.log("Selected Rules : " + selectedItems);
	if(selectedItems.length == 0){
		toastr.warning('No rule has been selected');
		return;
	}
	showWaitScreen();
	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : "addedInCart",
		data : JSON.stringify(selectedItems),
		dataType : "text",
		success : function(data) {
//			console.log("SUCCESS: ", data);
			hideWaitScreen();
			toastr.success('Added successfully.');
			$("#ruleCount").html(data);
			selectedItems = [];
			if(data>0){
				$("#btnGotoVersionSelection").prop('disabled', false);;
			}
			else{
				$("#btnGotoVersionSelection").prop('disabled', true);
			}

		},
		error : function(e) {
			hideWaitScreen();
			toastr.error('Failed to add.');
		},
		done : function(d) {
			console.log("DONE", d);
		}
	});
}

function changeRulesForMigration() {
	showWaitScreen();
	var ruleType = $("#selectType").val();
	var filter = "Filter";
	$("#filter-content :input:text").each(function() {
		if ($(this).val())
			filter = filter + "~" + $(this).val();
		else
			filter = filter + "~" + "null";
	});

	var url = "getRules/" + ruleType + "/" + filter;
	$("#ruleSelectionData").load(url, function() {
		$("#datatable").dataTable();
		hideWaitScreen();
	});
}

