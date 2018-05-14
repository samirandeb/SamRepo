var reloadRequired = false;
var closeTemplate = "<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">×</a>";

$(document).ready(function() {
	// $('#datatable').dataTable({
	// lengthChange: false,
	// buttons: [ 'copy', 'excel', 'pdf', 'colvis' ]
	// } );

	
	var table = $('#datatableUser').DataTable({
		lengthChange : true
	});

	// table.buttons().container().appendTo(
	// '#datatable_wrapper.col-sm-6:eq(0)' );

	
	var deleteUserOptions = {
			beforeSend : function() {
				//disable delete button
				console.log("Delete USER : Before Send.....");
				reloadRequired = false;
				$('#btnDeleteUser').prop('disabled', true);
			},
//			uploadProgress : function(event, position, total, percentComplete) {
//				console.log("Create USER : Progress..... "+percentComplete);
//			},
			success : function(responseText, statusText, xhr) {
				console.log("Delete USER : On Success..... "+responseText);
//				showAlert(responseText, "success");
				reloadRequired = true;
				$("#deleteUserModal").modal("hide");
			},
			complete : function(xhr) {
				console.log("Delete USER : On Complete.....");
				
			},

			error : function(xhr, textStatus, errorThrown) {
				console.log("Delete USER : On Error.....");
//				showAlert("User can not be deleted ", "error");
			}
	};
	
	$('#deleteUserForm').ajaxForm(deleteUserOptions);
	
	$('#deleteUserModal').on('hide.bs.modal', function() {
	    if(reloadRequired){
	    	location.reload();
	    }
	})
	
	
	
});


function makeReadyForDeletion(userId,userName){
	console.log("Show popup for use deletion : "+userId+" >> "+userName);
	$('#btnDeleteUser').prop('disabled', false);
	$("#deleteUserName").html(userName);
	$("#deleteUserId").val(userId);
}


function showAlert(message,type){
	$('#status').html(closeTemplate+message);
	$('#status').removeClass( "alert alert-success alert-info alert-warning alert-danger" );
		switch(type){
			case "success":
				$('#status').addClass( "alert alert-success" );
				break;
			case "info":
				$('#status').addClass( "alert alert-info" );
				break;
			case "warning":
				$('#status').addClass( "alert alert-warning" );
				break;
			case "error":
				$('#status').addClass( "alert alert-danger" );
				break;
		}
		$('#status').show();
	}
	
	
	function validateForm() {
		console.log("Create USER : Before Send.....form >>>>>");
		if($("#password_type").val() != $("#password_confirmation").val()){
			showAlert("Password mismatches. Please try again", "warning");
			return false;
		}
		
		return true;
	}
	
	function encryptPassword() {
		$("#password").val(md5($("#password_type").val()));
		return true;   // Returns Value
	}
	

function loadUserManagementModal(userId){
	
	var modalUrl = "getUserForm" + (userId==null?"":("/"+userId));
	var errorMessage = userId==null?"User creation failed":"User information update failed";
	$("#userManagementModal").load(encodeURI(modalUrl), function(response, status, xhr) {
		console.log('html loaded');
		 if ( status == "error" ){
			 toastr.error('Failed to get user detail.');
			 return;
		 }
		$("#selectRole").selectpicker();
		$("#selectSource").selectpicker();
		$("#updateUserModal").modal();
		
		var options = {
				beforeSend : function() {
					reloadRequired = false;
					//update the user password to MD5
//					$("#password").val(md5($("#password").val()));
//					$("#password_confirmation").val(md5($("#password_confirmation").val()));
					return validateForm();
				},
				uploadProgress : function(event, position, total, percentComplete) {
				},
				success : function(responseText, statusText, xhr) {
					showAlert(responseText, "success");
					reloadRequired = true;
				},
				complete : function(xhr) {
				},

				error : function(xhr, textStatus, errorThrown) {
					console.log("Create USER : On Error.....");
					showAlert(errorMessage, "error");
				}
		};
		
		
		$('#createUserForm').ajaxForm(options);
		
		$('#updateUserModal').on('hide.bs.modal', function() {
		    if(reloadRequired){
		    	location.reload();
		    }
		});
	});
}
