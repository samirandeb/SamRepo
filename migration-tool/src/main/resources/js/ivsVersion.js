$(document).ready(function() {
	
	var ruleCompareFormOptions = {
			beforeSend : function() {
				//disable delete button
				showWaitScreen();
			},
			success : function(responseText, statusText, xhr) {
				$("#ruleComparison").html("");
				$("#ruleComparison").html(responseText);
				diffUsingJS(1);
				$("#ruleXmlComparison").modal();
				hideWaitScreen();
			},
			complete : function(xhr) {
			},
			error : function(xhr, textStatus, errorThrown) {
				hideWaitScreen();
				toastr.error('Failed to generate comparison view.');
			}
	};
	
	$('#ruleCompareForm').ajaxForm(ruleCompareFormOptions);
});

function loadVersion(ruleGuid) {
	showWaitScreen();
	var currentVersion = $("#"+ruleGuid+"_version_v").val();
	var versionUrl = 'ivsVersions/' + ruleGuid+"/"+currentVersion;
	$('#versionSection').load(versionUrl,function() {
		  $('#datatableVersion').dataTable();
		  hideWaitScreen();
	});
}

function updateVersion(ruleGuid, versionGuid, author, updatedOn, version, latest) {
	event.preventDefault();
	if (latest != version) {
		$.confirm({
		    title: '<span class="warning">Warning!<span>',
			type: 'red',
			icon: 'fa fa-exclamation-triangle warning',
		    content: 'Selected version  (<b style="color:red;">'+version+'</red></b>) is not the latest, are you sure?',
		    buttons: {
		    	confirm: {
		            text: 'Confirm',
		            btnClass: 'btn-warning',
		            action: function(){
		                doUpdate(ruleGuid, versionGuid, author, updatedOn, version, latest,true);
		            }
		        },
		        cancel: function () {
		            return;
		        }
		    }
		});
	}
	else{
		doUpdate(ruleGuid, versionGuid, author, updatedOn, version, latest,false);
	}
}

function doUpdate(ruleGuid, versionGuid, author, updatedOn, version, latest,isWarning){
	var rowSelector = '#' + ruleGuid;
	var elementSelector = rowSelector+"_author_t";
	//set display
	$(rowSelector+"_author_t").html(author);
	$(rowSelector+"_updatedOn_t").html(updatedOn);
	$(rowSelector+"_version_t").html(version);
	//set value
	$(rowSelector+"_author_v").val(author);
	$(rowSelector+"_updatedOn_v").val(updatedOn);
	$(rowSelector+"_version_v").val(version);
	$(rowSelector+"_versionGuid_v").val(versionGuid);
	
	//set the styling
	if(isWarning){
		$(rowSelector).addClass("warning");
	}
	else{
		$(rowSelector).removeClass("warning");
	}
	
	$("#ivsVersionModal").modal('hide');
}

function removeRules() {
//	//console.log("Selected Rules : " + selectedItems);
	if(selectedItems.length == 0){
		toastr.warning('No rule has been selected');
		return;
	}
	showWaitScreen();
	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : "removeFromCart",
		data : JSON.stringify(selectedItems),
		dataType : "text",
		success : function(data) {
			toastr.success('Removed successfully.');
			$("#ruleCount").html(data);
			selectedItems = [];
			hideWaitScreen();
			location.reload();
		},
		error : function(e) {
			hideWaitScreen();
			toastr.error('Failed to remove.');
		},
		done : function(d) {
		}
	});
}


function diffUsingJS(viewType) {
	
	var baseXml = $("#rc_sourceXml").val();
	var newXml = $("#rc_destinationXml").val();
	var byId = function (id) { return document.getElementById(id); }, 
		base = difflib.stringAsLines(baseXml),
		newtxt = difflib.stringAsLines(newXml),
		sm = new difflib.SequenceMatcher(base, newtxt),
		opcodes = sm.get_opcodes(),
		diffoutputdiv = byId("ruleComparisonView");
			
	diffoutputdiv.innerHTML = "";
//	contextSize = contextSize || null;

	diffoutputdiv.appendChild(diffview.buildView({
		baseTextLines: base,
		newTextLines: newtxt,
		opcodes: opcodes,
		baseTextName: (""+$("#rc_sourceName").val()+" [Version : "+$("#rc_sourceVersion").val()+"]"),
		newTextName:  (""+$("#rc_destinationName").val()+" [Version : "+$("#rc_destinationVersion").val()+"]") ,
//		contextSize: contextSize,
		viewType: viewType
	}));
	
	//update title for modal
	$("#titleRuleName").html($("#rc_ruleName").val());
	$("#titleSource").html((""+$("#rc_sourceName").val()+" [Version : "+$("#rc_sourceVersion").val()+"]"));
	$("#titleDestination").html((""+$("#rc_destinationName").val()+" [Version : "+$("#rc_destinationVersion").val()+"]"));
}

function viewDiff(ruleGuid,ruleName,ruleType,version){
	//set the field values
	console.log("Diffing : "+ruleGuid+" - "+ruleName);
	$("#compareRuleName").val(ruleName);
	$("#compareRuleGuid").val(ruleGuid);
	$("#compareRuleType").val(ruleType);
	if(null!=version){
		$("#compareSourceVersion").val(version);
	}else{
		$("#compareSourceVersion").val($("#"+ruleGuid+"_version_v").val());
	}
//	$("#compareRuleType").val($("#"+ruleGuid+"_ruleType_v").val());
	$("#ruleCompareForm" ).submit();
	
}
