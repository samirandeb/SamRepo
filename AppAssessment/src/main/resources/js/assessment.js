/**
 *
 */
$(document).ready(
		function() {

			console.log("Assessment document ready..");
			$('.alphaNumeric').keypress(
					function(e) {
						var regex = new RegExp("^[a-zA-Z0-9\\-\\s]+$");
						var str = String.fromCharCode(!e.charCode ? e.which
								: e.charCode);
						if (regex.test(str)) {
							return true;
						}
						e.preventDefault();
						return false;
					});

			var assessmentFormOption = {
				beforeSend : function() {
					// show wait cursor
					showWaitScreen();
				},
				success : function(responseText, statusText, xhr) {
					// hide wait cursor
					hideWaitScreen();
					// show success information and make the app name readonly
					console.log("1");

					if (!$('#appName').is('[readonly]')) {
						window.location.href = "/caaf/application/"
								+ encodeURI($('#appName').val());
					} else {
						toastr.success('Information saved.');
					}

				},
				complete : function(xhr) {
					// hide wait cursor
					hideWaitScreen();
				},

				error : function(xhr, textStatus, errorThrown) {
					// hide wait cursor
					hideWaitScreen();
					// show error ticker
					toastr.error('Information can not be saved.');
				}
			};
			$('#assessmentForm').ajaxForm(assessmentFormOption);
		});
//target.split(search).join(replacement);
function showOption(value, questionId) {
	// console.log("Value passed : "+value);
	// console.log("QuestionID passed : "+questionId);
	var elementId = "#" + value.split(" ").join("_") + "_" + questionId;
	var searchId = '[id*="_' + questionId + '"]';
	// console.log("Element ID : "+elementId);
	// console.log("Search by "+searchId);
	$(searchId).prop("disabled", true);
	$(searchId).hide();
	$(elementId + ' :nth-child(1)').prop('selected', true);
	$(elementId).prop("disabled", false);
	$(elementId).show();
	$(elementId).val("");

}
