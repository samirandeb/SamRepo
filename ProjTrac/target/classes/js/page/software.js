function getAppSoftwareData(){

	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "prdMaster",
        success: function(data){     
          //console.log(data._embedded.software);
           $('#appSoftwareTable').DataTable( {
        	   fixedColumns: true,
        	   "columns": [
        		    { "width": "2%" },
        		    null,
        		    { "width": "20%" },,
        		    { "width": "35%" },
        		    null,
        		    { "width": "10%" }
        		  ],
        	   data: data._embedded.prdMaster,
               columns: [
            	   { data: "roadmap",title: "Roadmap",
            		   "render": function(value) {
                		   if(value === undefined || value == null || value.length <= 0 ){
                			   return value;
                		   }else {
//                			   return value.substring(0, 15);
                			   return value;
                		   }               	          
                	      }
            	   },
                   { data: "mgmtGrp", title: "Management Group"},
                   { data: "manufacturer", title: "Manufacturer",
                	   "render": function(value) {
                		   if(value === undefined || value == null || value.length <= 0 ){
                			   return value;
                		   }else {
//                			   return value.substring(0, 35);
                			   return value;
                		   }               	          
                	      }
                   },
                   { data: "prodDispName", title: "Product Display Name",
            		   
            		   "render": function(data, type, row, meta){
       	            if(type === 'display'){
    	                data = '<a href="#" class="scrollToDiv" onclick="javascript:displayGrpRslt(\''+row.prdId+'\')" >' + data + '</a>';
    	            }            	            
    	            return data;
    		   }
                   },
                   {
                	   data: "status", title: "Status"
                	  /* "render": function(data, type, row, meta){
                		   if(type === 'display'){
                			   if(row.status===null||row.status===''){
                				        data='<select class="form-control" id="status'+row.prdId+'" onchange="updateStatus(\''+row.prdId+'\',\''+row.roadmap+'\',\''+row.mgmtGrp+'\',\''+row.manufacturer+'\',\''+row.prodDispName+'\')"><option value="" selected="selected"></option><option value="Approved">Approved</option><option value="Recommendation">Recommendation</option></select>';
                				  
                			   }else if(row.status==='Approved'){
                				   		data='<select class="form-control" id="status'+row.prdId+'" onchange="updateStatus(\''+row.prdId+'\',\''+row.roadmap+'\',\''+row.mgmtGrp+'\',\''+row.manufacturer+'\',\''+row.prodDispName+'\')"><option value=""></option><option value="Approved" selected="selected">Approved</option><option value="Recommendation">Recommendation</option></select>';
                					   
                			   }else if(row.status==='Recommendation'){
                				   		data='<select class="form-control" id="status'+row.prdId+'" onchange="updateStatus(\''+row.prdId+'\',\''+row.roadmap+'\',\''+row.mgmtGrp+'\',\''+row.manufacturer+'\',\''+row.prodDispName+'\')"><option value=""></option><option value="Approved">Approved</option><option value="Recommendation" selected="selected">Recommendation</option></select>';
                					   
                			   }
                		   }
                		   return data;
                	   }*/
                		   
                   },
                   {
                	   data: "approveDate", title: "Date", 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   {
                	   data: null, title: "",
                	   "render": function(data, type, row, meta){
                		   
                		  if(row.status==='Recommended'){
//                		   data = '<a href="#" onclick="updateApproveStatus(\''+row.prdId+'\',\''+row.roadmap+'\',\''+row.mgmtGrp+'\',\''+row.manufacturer+'\',\''+row.prodDispName+'\')">' + '<i class="fa fa-check"></i>' + '</a>';
                		   data = '<a href="#" onclick="addPoupmey(\''+row.prdId+'\',\''+row.roadmap+'\',\''+row.mgmtGrp+'\',\''+row.manufacturer+'\',\''+row.prodDispName+'\')">' + '<i class="fa fa-check"></i>' + '</a>';
                		  }else{
                			  //data= '<span class="disable-links"><a href="#" style="color:#808080">' + 'Approved' + '</a></span>';
//                			  data= '';
                			  data= '<span class="disable-links"><a href="#" style="color:#808080">' + '<i class="fa fa-check"></i>' + '</a></span>';
                			  
                		  }
                		   return data;
                	   }
                		   
                   },
                   /*{
                	   data: null, title: "",
                	   "render": function(data, type, row, meta){
                		   
                		   data = '<a href="#" onclick="todo(\''+row.appName+'\' , \''+row.lob+'\', \''+row.operatingSys+'\', \''+row.progLug+'\',\''+row.appServer+'\',\''+row.frameWork+'\',\''+row.database+'\' )">' + '<i class="fa fa-edit"></i>' + '</a>';
                		   return data;
                	   }
                		   
                   }*/
                   {
                	   data: null, title: "History",
                	   "render": function(data, type, row, meta){
                		   
                		   data = '<a href="#" onclick="fetchAuditData(\''+row.prdId+'\',\''+row.prodDispName+'\')">' + '<i class="fa fa-edit"></i>' + '</a>';
                		   return data;
                	   }
                		   
                   }
               ]
           } );
           
           hideWaitScreen();
        }
    });
	
	

}
function addPoupmey(prdId,roadmap,mgmtGrp,manufacturer,prodDispName) {
    $("#popupView").modal("show");
    $("#popupView #prdMstrId").val(prdId);
    $("#popupView #roadmap").val(roadmap);
    $("#popupView #manf").val(manufacturer);
    $("#popupView #mnfgrp").val(mgmtGrp);
    $("#popupView #prodDispName").val(prodDispName);
    
}
function addPopupData(){
	
	var prdId = $("#popupView #prdMstrId").val();
	var url = "approvePrd/"+prdId;
	
	$.ajax({ 
	    type: "GET",
	    dataType: "text",
	    url: url,
	    success: function (resp) {
	          $("#masterView").modal("hide");
	          location.reload();
	          //console.log("Success Respose : ",resp);
	          //showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
	     },
	     error : function(err){
	          console.log("Err : ",err);
	     }
	});
		
		
 /*var softwareData  = {};
    softwareData.roadmap =  $("#popupView #roadmap").val();
    softwareData.mgmtGrp = $("#popupView #mnfgrp").val();
    softwareData.manufacturer = $("#popupView #manf").val();
    softwareData.prodDispName = $("#popupView #prodDispName").val();
    softwareData.prdId= $("#popupView #prdMstrId").val();
    softwareData.status='Approved';
    console.log("value is for rodmap",softwareData.status);
    console.log("going to update with",softwareData);

    $.ajax({
     url: 'prdMaster',
           contentType : "application/json",
     type: 'post',
     dataType: 'text',
     success: function (resp) {
          $("#masterView").modal("hide");
          location.reload();
          console.log("Success Respose : ",resp);
          //showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
     },
     error : function(err){
          console.log("Err : ",err);
          //showAlert("Failed to update",null,error);
     },
     data: JSON.stringify(softwareData)
 });*/
 
}

function updateApproveStatus(prdId,roadmap,mgmtGrp,manufacturer,prodDispName){
	
    if (confirm("Do you want to update the Status ?") == true) {
        var softwareData  = {};
    	softwareData.roadmap = roadmap;
    	softwareData.mgmtGrp = mgmtGrp;
    	softwareData.manufacturer = manufacturer;
    	softwareData.prodDispName = prodDispName;
        softwareData.prdId=prdId;
        softwareData.status='Approved';
    	console.log("value is for rodmap",softwareData.status);
    	console.log("going to update with",softwareData);

    	$.ajax({
            url: 'prdMaster',
    		contentType : "application/json",
            type: 'post',
            dataType: 'text',
            success: function (resp) {
            	$("#masterView").modal("hide");
            	location.reload();
            	console.log("Success Respose : ",resp);
            	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
            },
            error : function(err){
            	console.log("Err : ",err);
            	//showAlert("Failed to update",null,error);
            },
            data: JSON.stringify(softwareData)
        });
        
    } else {
    	
    }

}
var globalDetailsRow={};
function displayGrpRslt(prdId){
	//console.log("input param::"+roadmap+","+mgmtGrp+","+manufacturer+","+prodDispName);
	/*if($.fn.dataTable.isDataTable( '#dependentData')) {
		$('#dependentData').DataTable().clear();
	}*/
	//console.log(prdId);
	$('#prdMstrId').val(prdId);
	$("#addDtlsBtn").show(); 
	var myUrl = "prddtls/search/findByPrdId?prdId="+prdId;
	console.log("myUrl:"+myUrl);
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data){     
        	globalDetailsRow = data._embedded.prddtls;
          //console.log(data._embedded.software);
           $('#dependentData').DataTable({
        	   destroy: true,
        	   paging: false,
        	   scrollX: true,
               scrollCollapse: true,
        	   searching: false,
        	   bAutoWidth: false,
        	   /*"columnDefs": [
        		    { "width": "20px", "targets": 2 }
        		  ],*/
        	   data: data._embedded.prddtls,
        	   columnDefs: [
                   {
                       targets: 3,
                       className: 'highlightCol'
                   },
                   {
                       targets: 4,
                       className: 'highlightCol'
                   }
               ],
               columns: [
                   { data: "indProdName",title: "Industry Name",
                	   "render": function(value) {
                		   $("#btnVoyaNSave").show();
                		   if(value === undefined || value == null || value.length <= 0 ){
                			   return value;
                		   }else {
//                			   return value.substring(0, 12);
                			   return value;
                		   }               	          
                	      },   
                   },
                   {
            		   data: "version", title: "Version", 
            		   "render": function(data, type, row, meta){
            			   if(data === undefined || data == null || data.length <= 0 ){
                			   data='NA';
                		   }
            	            if(type === 'display'){
            	            	 data = '<a href="#" onclick="editDtlsData('+row.prdtlId+')">' + data + '</a>';
            	            }            	            
            	            return data;
            		   }
            	   },
                   { data: "voyaProdLfCyclDispo", title: "Disposition" },
                   /*{ 
                	   data: "emerge", title: "", 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   { 
                	   data: "selectDt",title: "" , 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   { 
                	   data: "insStd", title: "Install Std", 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   { 
                	   data: "voyaDeSuppDt", title: "De-Support Date" , 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   { 
                	   data: "retire",title: "Retire" , 
                       "render": function (data) {

                    	   return dateFormat(data);
                       }
                   },
                   { 
                	   data: "endLifeDt", title: "EOL Date" , 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },
                   { 
                       data: "obsDt", title: "Obsolete Date", 
                       "render": function (data) {
                    	   return dateFormat(data);
                       }
                   },*/
                   {
            		   data: "voyaN", title: "Voya N", 
            		   "render": function(data, type, row, meta){
            	            if(type === 'display'){
            	            	if(data==='Y'){
            	                data = '<input type="radio" checked="checked" name="voyaN" value=\''+row.prdtlId+'\'/>';
            	            	} else{
            	            		 data = '<input type="radio" name="voyaN" value=\''+row.prdtlId+'\'/>';
            	            	}
            	            }            	            
            	            return data;
            		   }
            	   },
            	   {
            		   data: "voyaN_1", title: "Voya N-1", 
            		   "render": function(data, type, row, meta){
            	            if(type === 'display'){
            	            	if(data==='Y'){
            	                data = '<input type="checkbox" checked="checked" name="voyaN_1" value=\''+row.prdtlId+'\'/>';
            	            	} else{
            	            		 data = '<input type="checkbox" name="voyaN_1" value=\''+row.prdtlId+'\'/>';
            	            	}
            	            }            	            
            	            return data;
            		   }
            	   },
            	   { data: null, title: "App Traceability" , 
            		   "render": function(data, type, row, meta){
            			   
            	            	console.log(data);
            	                data = '<a href="/ptrac/appInventory?'+row.prdtlId+'">' + 'Trace' + '</a>';
            	                
            	                 console.log();
            	                return data;
            	            }            	            
            	             
            	            }
               ]
           });
           
           hideWaitScreen();
           //scroll to N/N-1 view
	   	   $('html,body').animate({scrollTop: $("#n-1view").offset().top},'slow');  
			
        }
    });
}

function addNew() {
	$("#masterView").modal("show");
}

function editDtlsData(prdtlId) {

	$("#btnDtlsSave").hide(); 
	$("#btnDtlsEdit").show(); 
	$("#commentDiv").show();
	$("#historyDiv").show();
	$('#prdDtlId').val(prdtlId);
	//$("#btn-save").text('Update');
	//updates the values in modal
	var row = {};
	for (var j = 0; j < globalDetailsRow.length; j++){
 	   if(globalDetailsRow[j].prdtlId ===prdtlId){
 		 row = globalDetailsRow[j];
 		$('#indPrdName').val(row.indProdName);
 		$('#version').val(row.version);
 		$('#disposition').val(row.voyaProdLfCyclDispo);
 		$('#emerge').val(dateFormat(row.emerge));
 		$('#select').val(dateFormat(row.selectDt));
 		$('#install').val(dateFormat(row.insStd));
 		$('#contain').val(dateFormat(row.contain));
 		$('#deSupport').val(dateFormat(row.voyaDeSuppDt));
 		$('#retire').val(dateFormat(row.retire));
 		$('#vendorN').val(row.vendN);
 		$('#alert').val(row.vendSuppAlrt);
 		$('#gaDate').val(dateFormat(row.gaDt));
 		$('#eolDate').val(dateFormat(row.endLifeDt));
 		$('#supportLevel').val(row.endLifeSuppLvl);
 		$('#obsolete').val(dateFormat(row.obsDt));
 		$('#obsoleteSupport').val(row.obsSuppLvl);
 	   }
    }

	
	// fetch the history data
	 
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "prd_dtls_his/search/findByPrdtlId?prdtlId="+prdtlId,
        success: function(data){     
          //console.log(data._embedded.prd_dtls_his);
        	
        	 data= data._embedded.prd_dtls_his;
        	 var newString = '';
        	
        	 for(var i=0; i<data.length; i++){
        		 newString += data[i].createdBy+' ('+dateFormat(data[i].createdDt)+')- '+data[i].comments+'\n';
        	 }
        	 $('#history').val(newString);
        }
    });
	//Set Read Only
	$('#indPrdName').attr('readonly', true);
	$('#version').attr('readonly', true);
	//$('#vendorN').attr('readonly', true);
	//$('#vendorN').attr('disabled', 'disabled');
	$('#alert').attr('readonly', true);
	$('#supportLevel').attr('readonly', true);
	$('#obsoleteSupport').attr('readonly', true);
	$("#detailView").modal("show");
	  $(disposition).css('backgroundColor','orange');
}

function saveData() {
	
	var prdDetailsData  = {};
	prdDetailsData.prdId = $('#prdMstrId').val();
	prdDetailsData.roadmap = $('#roadmap').val();
	prdDetailsData.mgmtGrp = $('#mgmtGrp').val();
	prdDetailsData.manufacturer = $('#manufacturer').val();
	prdDetailsData.prodDispName = $('#prdName').val();
	prdDetailsData.indProdName = $('#indPrdName').val();
	prdDetailsData.version = $('#version').val();
	prdDetailsData.voyaProdLfCyclDispo = $('#disposition').val();
	prdDetailsData.emerge =  formatDate($('#emerge').val());//$('#emerge').val();
	//console.log( $('#emerge').val()); formatDate($('#retire').val());
	prdDetailsData.selectDt =formatDate($('#select').val());// $('#select').val();
	prdDetailsData.insStd = formatDate($('#install').val());//$('#install').val();
	prdDetailsData.contain =formatDate($('#contain').val());// $('#contain').val();
	prdDetailsData.voyaDeSuppDt = formatDate($('#deSupport').val());//$('#deSupport').val();
	prdDetailsData.retire = formatDate($('#retire').val());//$('#retire').val();
	prdDetailsData.vendN = $('#vendorN').val();
	prdDetailsData.vendSuppAlrt = $('#alert').val();
	prdDetailsData.gaDt = formatDate($('#gaDate').val());//$('#gaDate').val();
	prdDetailsData.endLifeDt = formatDate($('#eolDate').val());// $('#eolDate').val();
	prdDetailsData.endLifeSuppLvl = $('#supportLevel').val();
	prdDetailsData.obsDt = formatDate($('#obsolete').val());//$('#obsolete').val();
	prdDetailsData.obsSuppLvl = $('#obsoleteSupport').val();
	//softwareData.voyaN = $('#voyaN').val();
	//softwareData.voyaN_1 = $('#voyaN-1').val();
	
	console.log("going to update with",prdDetailsData);
	

	$.ajax({
        url: 'prddtls',
		contentType : "application/json",
        type: 'post',
        dataType: 'text',
        success: function (resp) {
        	$("#detailView").modal("hide");
        	console.log("Success Respose : ",resp);
        	//location.reload();
        	displayGrpRslt($('#prdMstrId').val());   
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data: JSON.stringify(prdDetailsData)
    });
	
	//$("#detailView").modal("hide");
}

function addDetails() {
	console.log($('#prdMstrId').val());
	$("#btnDtlsSave").show(); 
	$("#btnDtlsEdit").hide();
	$("#commentDiv").hide();
	$("#historyDiv").hide();
	$("#detailView").modal("show");
	$('#indPrdName').val('');
		$('#version').val('');
		$('#disposition').val('');
//		$('#emerge').val(dateFormat(new Date()));
		$('#emerge').val('');
//		$('#select').val(dateFormat(new Date()));
		$('#select').val('');
//		$('#install').val(dateFormat(new Date()));
		$('#install').val('');
//		$('#contain').val(dateFormat(new Date()));
		$('#contain').val('');
//		$('#deSupport').val(dateFormat(new Date()));
		$('#deSupport').val('');
//		$('#retire').val(dateFormat(new Date()));
		$('#retire').val('');
		$('#vendorN').val('');
		$('#alert').val('');
//		$('#gaDate').val(dateFormat(new Date()));
		$('#gaDate').val('');
//		$('#eolDate').val(dateFormat(new Date()));
		$('#eolDate').val('');
		$('#supportLevel').val('');
//		$('#obsolete').val(dateFormat(new Date()));
		$('#obsolete').val('');
		$('#obsoleteSupport').val('');
		
		//Set Read Only
		$('#indPrdName').attr('readonly', false);
		$('#version').attr('readonly', false);
		//$('#vendorN').attr('readonly', false);
		//$('#vendorN').attr('disabled', false);
		$('#alert').attr('readonly', false);
		$('#supportLevel').attr('readonly', false);
		$('#obsoleteSupport').attr('readonly', false);
		$("#detailView").modal("show");
		  $(disposition).css('backgroundColor','white');
}

/*
function saveDetails() {
	
	var softwareData  = {};
	softwareData.prdId = $('#prdId').val();//TODO
	softwareData.indProdName = $('#indPrdName').val();
	softwareData.version = $('#version').val();
	softwareData.voyaProdLfCyclDispo = $('#disposition').val();
	softwareData.emerge = $('#emerge').val();
	softwareData.selectDt = $('#select').val();
	softwareData.insStd = $('#install').val();
	softwareData.contain = $('#contain').val();
	softwareData.voyaDeSuppDt = $('#deSupport').val();
	softwareData.retire = $('#retire').val();
	//softwareData.vendN = $('#vendorN').val();
	//softwareData.vendSuppAlrt = $('#alert').val();
	//softwareData.gaDt = $('#gaDate').val();
	softwareData.endLifeDt = $('#eolDate').val();
	//softwareData.endLifeSuppLvl = $('#supportLevel').val();
	softwareData.obsDt = $('#obsolete').val();
	//softwareData.obsSuppLvl = $('#obsoleteSupport').val();
	//softwareData.voyaN = $('#voyaN').val();
	//softwareData.voyaN_1 = $('#voyaN-1').val();
	
	console.log("going to update with",softwareData);
	

	$.ajax({
        url: 'software',
		contentType : "application/json",
        type: 'post',
        dataType: 'text',
        success: function (resp) {
        	$("#detailView").modal("hide");
        	console.log("Success Respose : ",resp);
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data: JSON.stringify(softwareData)
    });
	
	//$("#detailView").modal("hide");
}
*/
function updateDtlsData() {
	var prdDetailsData  = {};
	var myUrl="update/"+ $('#prdDtlId').val();
	console.log(myUrl);
	prdDetailsData.prdtlId = $('#prdDtlId').val();
	prdDetailsData.prdId = $('#prdMstrId').val();
	prdDetailsData.roadmap = $('#roadmap').val();
	prdDetailsData.mgmtGrp = $('#mgmtGrp').val();
	prdDetailsData.manufacturer = $('#manufacturer').val();
	prdDetailsData.prodDispName = $('#prdName').val();
	prdDetailsData.indProdName = $('#indPrdName').val();
	prdDetailsData.version = $('#version').val();
	prdDetailsData.voyaProdLfCyclDispo = $('#disposition').val();
	prdDetailsData.emerge = formatDate($('#emerge').val());
	prdDetailsData.selectDt = formatDate($('#select').val());
	prdDetailsData.insStd = formatDate($('#install').val());
	prdDetailsData.contain = formatDate($('#contain').val());
	prdDetailsData.voyaDeSuppDt = formatDate($('#deSupport').val());
	prdDetailsData.retire = formatDate($('#retire').val());
	prdDetailsData.vendN = $('#vendorN').val();
	prdDetailsData.vendSuppAlrt = $('#alert').val();
	prdDetailsData.gaDt = formatDate($('#gaDate').val());
	prdDetailsData.endLifeDt = formatDate($('#eolDate').val());
	prdDetailsData.endLifeSuppLvl = $('#supportLevel').val();
	prdDetailsData.obsDt = formatDate($('#obsolete').val());
	prdDetailsData.obsSuppLvl = $('#obsoleteSupport').val();
	//softwareData.voyaN = $('#voyaN').val();
	//softwareData.voyaN_1 = $('#voyaN-1').val();
	
	console.log("going to update with",prdDetailsData);
	

	$.ajax({
        url: myUrl,
		contentType : "application/json",
        type: 'POST',
        dataType: 'text',
        success: function (resp) {
        	//$("#detailView").modal("hide");
        	console.log("Success Respose : ",resp);
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data: JSON.stringify(prdDetailsData)
    });
	
	//Update history
	var prdHistory  = {};
	prdHistory.prdtlId=$('#prdDtlId').val();
	prdHistory.comments=$('#comments').val();
	prdHistory.createdBy='user';
	prdHistory.createdDt=formatDate(''+new Date());
	$.ajax({
        url: 'prd_dtls_his',
		contentType : "application/json",
        type: 'POST',
        dataType: 'text',
        success: function (resp) {
        	$('#history').val(' ');
       	 $('#comments').val(' ');
        	$("#detailView").modal("hide");
        	//console.log("Success Respose : ",resp);
        	//location.reload();
        	displayGrpRslt($('#prdMstrId').val());
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data: JSON.stringify(prdHistory)
    });
	
	//$("#detailView").modal("hide");
}

function saveDataToMaster() {
	
	var softwareData  = {};
	softwareData.roadmap = $("#masterView #roadmap").val();
	softwareData.mgmtGrp = $('#masterView #mgmtGrp').val();
	softwareData.manufacturer = $('#masterView #manufacturer').val();
	softwareData.prodDispName = $('#masterView #prdName').val();
	console.log("value is for rodmap",softwareData.roadmap);
	console.log("going to update with",softwareData);
	

	$.ajax({
        url: 'prdMaster',
		contentType : "application/json",
        type: 'post',
        dataType: 'text',
        success: function (resp) {
        	$("#masterView").modal("hide");
        	console.log("Success Respose : ",resp);
        	location.reload();
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data: JSON.stringify(softwareData)
    });
	
	//$("#detailView").modal("hide");
}

function dateFormat(data) {
	if (data === undefined || data == null || data.length <= 0) {
		return data;
	} else {
		var d = new Date(data),
		month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
		
		if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;
	    
		return (month + "/" + day
				+ "/" + year);
	}
}

function formatDate(date) {
	if (date){
		
	 var d = new Date(date),
	 	month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
  
	}else{
		  return null;
	}
}

function updateVoyaN(){
	var valN = $('input[name=voyaN]:checked').val(),
	prdId = $('#prdMstrId').val();
	//var valN_1 = $('input[name="voyaN_1[]"]:checked').val();
	var valN_1 = [];
	valN_1.push(-5);
    $.each($("input[name='voyaN_1']:checked"), function(){            
    	valN_1.push($(this).val());
    });
	//  alert(prdId+'#'+valN+'-'+valN_1.join(", "));
	//alert($("#voyaN").val()+'-'+$("#voyaN_1").val());
	  var myUrl = "updateVoyaN/"+prdId+'/'+valN+'/'+valN_1.join(", ");
	  console.log(myUrl);
		$.ajax({ 
	        type: "GET",
	        dataType: "text",
	        url: myUrl,
	        success: function(data){   
	           //hideWaitScreen();
	           //window.location.reload(true);
	        	location.reload();
	        }
	    });
	  
}


function fetchAuditData(prdId,prdName){
	
	/*$('#prdAuditData').DataTable( {
  	   "searching": false,
  	   destroy: true,
  	   paging: false,
  	   data: data._embedded.prdAudit,
         columns: [
      	   { data: "version",title: "Version"},
             { data: "diposition", title: "Diposition"},
             { data: "voyaN", title: "Voya N"},
             { data: "voyaN_1", title: "Voya N-1"},
             {
          	   data: "approveDate", title: "Approve Date", 
                 "render": function (data) {
              	   return dateFormat(data);
                 }
             }
         ]
     } );*/
	
	// create a handlebars template
	var source   = document.getElementById('prdAudit-template').innerHTML;
	var template = Handlebars.compile(document.getElementById('prdAudit-template').innerHTML);
	var prdData =[];
	var result =[];
	var approveDate="";
	
	$( "#prdHeader" ).text('Product History ('+prdName+')');
	
	$.ajax({
        type: "GET",
        dataType: "json",
        url: "prdAudit/search/findByPrdId?prdId="+prdId,
        success: function(data){     
          $("#auditView").modal("show");
  	  	  $( "#prdTimeline" ).empty();
        	
  	  	  //time line using vis.js start
  	  	  var container = document.getElementById('prdTimeline');
  	  	
  	  	  result = data._embedded.prdAudit
	  	  var options = {};
	  	  var items = new vis.DataSet(options);
           
  	  	if(result && result.length){
  	  		
  	  		//console.log("Data :",prdData);
	  	  	for (var j = 0; j < result.length; j++){
	  	  	
	  	  		if(approveDate != result[j].approveDate && approveDate != "") {
	  	  			
	  	  			//console.log("Inside IF :",result[j]);
	  	  			items.add([{prd:prdData,start: approveDate,title: '<b>'+approveDate+'</b>'}]);
	  	  			prdData=[];
	  	  			prdData.push(result[j]);
	  	  			
	  	  		}
	  	  		else {
	  	  			//console.log("Inside ELSE :::",result[j]);
	  	  			prdData.push(result[j]);
	  	  		}
	  	  		approveDate = result[j].approveDate;
	  	  		//console.log("approveDate",approveDate);
	  	  	}
	  	  items.add([{prd:prdData,start: approveDate,title: '<b>'+approveDate+'</b>'}]);
	  	  $( "#loading" ).hide();
               
  	  	}
  	  	else{
  	  		$( "#loading" ).show();
  	  		$( "#loading" ).text('No data found..!!');
  	  		return;
  	  	}
           console.log("items :",items);
 	      
 	       // Configuration for the Timeline
           var options = {
    		    stack: true,
    		    horizontalScroll: true,
        		zoomKey: 'ctrlKey',
			    template: template
			};
 	
 	       // Create a Timeline
 	       var timeline = new vis.Timeline(container, items, options);
           //time line using vis.js end
                      
           hideWaitScreen();
        }
    });
}

function showOption(option) {
	switch (option) {
	case "add":
		$("#inputFileOption").hide();
		$('#appSoftwareTable').parents('div.dataTables_wrapper').first().show();
		$("#n-1view").show();
		$('#dependentData').parents('div.dataTables_wrapper').first().hide();
		
		$("#header").hide();
		$("#header1").hide();
		$("#header2").hide();
		$("#header3").hide();
		$("#header4").hide();
		$("#header5").hide();
		$("#header6").hide();
		$("#timelineView").hide();
		$("#previousData").hide();
		$("#jdkData").hide();
		addNew();
		break;
	case "upload":
		$("#inputFileOption").show();
		$('#appSoftwareTable').parents('div.dataTables_wrapper').first().hide();
		$("#n-1view").hide();
		$('#dependentData').parents('div.dataTables_wrapper').first().hide();
		
		$("#header").show();
		$("#header1").show();
		$("#header2").show();
		$("#header3").show();
		$("#header4").show();
		$("#header5").show();
		$("#header6").show();
		$("#timelineView").show();
		$("#previousData").show();
		$("#jdkData").show();
		drawTimeline();
		break;
	}
}

function bytesToSize(bytes) {
	var sizes = [ 'Bytes', 'KB', 'MB', 'GB', 'TB' ];
	if (bytes == 0)
		return '0 Byte';
	var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
	return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
};

function onFileSelectionChange() {
	var fileObj = $("#inputFile")[0].files[0]
	console.log(fileObj.name);
	$("#inputFileDetail").val(fileObj.name + " ( " + bytesToSize(fileObj.size) + " )");
	var percentVal = '0%';
	 $('#progressBar').width(percentVal);
	 $('#progressBarText').html(percentVal);
	$('#status').html("");

}

function drawTimeline(){
	
	$( "#previousData" ).empty();
	var container = document.getElementById('previousData');	  	
  	var items = new vis.DataSet([
      {id: 1, content: '2017- Q2', start: '2017-06-01'},
      {id: 2, content: '2017- Q3', start: '2017-09-01'},
      {id: 100, content: '<a href="/ptrac/custom/static/VOYA_PRD_STANSARD_TEMPLATE.xlsx" target="_blank"><img src="/ptrac/custom/static/excel.jpg" style="width:24px; height:24px;"></a>', start: '2017-10-23',title: '2017-10-23'},
      {id: 3, content: '2017- Q4', start: '2017-12-01'},
      {id: 4, content: '2018- Q1', start: '2018-03-01'},
      {id: 5, content: '2018- Q2', start: '2018-06-01'},
      {id: 6, content: '2018- Q3', start: '2018-09-01'},
      {id: 7, content: '2018- Q4', start: '2018-12-01'},
      {id: 12, content: '2019- Q1', start: '2019-03-01'}
    ]);
    var options = {
		    stack: true,
		    horizontalScroll: true,
		    zoomKey: 'ctrlKey'
		};
     var timeline = new vis.Timeline(container, items, options);
     
     
     var template = Handlebars.compile(document.getElementById('prdAudit-template').innerHTML);

     $( "#jdkData" ).empty();
     var container = document.getElementById('jdkData');	  	
   	 var items = new vis.DataSet([
       {prd:[{version:"1.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"1.7",diposition:"Install Standard",voyaN:"Y"},{version:"1.6",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2017-09-01',title: '2017-09-01'},
       {prd:[{version:"1.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"1.7",diposition:"Install Standard",voyaN:"Y"},{version:"1.6",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2017-12-01',title: '2017-12-01'},
       {prd:[{version:"1.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"1.7",diposition:"Install Standard",voyaN:"Y"},{version:"1.6",diposition:"Install Standard",voyaN:"N",voyaN_1:"N"}],start: '2018-03-01',title: '2018-03-01'},
       {prd:[{version:"1.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"1.7",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2018-06-01',title: '2018-03-01'}
     ]);
     var options = {
     	stack: true,
     	horizontalScroll: true,
     	zoomKey: 'ctrlKey',
     	template: template
     };
     var timeline = new vis.Timeline(container, items, options);
     
     $( "#jbossData" ).empty();
     var container = document.getElementById('jbossData');	  	
   	 var items = new vis.DataSet([
       {prd:[{version:"7.3",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"},{version:"5.0",diposition:"Contain",voyaN:"N",voyaN_1:"N"}],start: '2017-09-01',title: '2017-09-01'},
       {prd:[{version:"7.3",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"},{version:"5.0",diposition:"Contain",voyaN:"N",voyaN_1:"N"}],start: '2017-12-01',title: '2017-12-01'},
       {prd:[{version:"7.3",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2018-03-01',title: '2018-03-01'}
     ]);
     var options = {
     	stack: true,
     	horizontalScroll: true,
     	zoomKey: 'ctrlKey',
     	template: template
     };
     var timeline = new vis.Timeline(container, items, options);
     
     $( "#rhelData" ).empty();
     var container = document.getElementById('rhelData');	  	
   	 var items = new vis.DataSet([
   		{prd:[{version:"6.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2017-09-01',title: '2017-09-01'},
        {prd:[{version:"6.8",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2017-12-01',title: '2017-12-01'},
        {prd:[{version:"7.0",diposition:"Industry N/ Not in Environment",voyaN:"Y",voyaN_1:"N"},{version:"6.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2018-03-01',title: '2018-03-01'}
     ]);
     var options = {
     	stack: true,
     	horizontalScroll: true,
     	zoomKey: 'ctrlKey',
     	template: template
     };
     var timeline = new vis.Timeline(container, items, options);
     
     $( "#dotnetData" ).empty();
     var container = document.getElementById('dotnetData');	  	
   	 var items = new vis.DataSet([
   		{prd:[{version:"4.5",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"4.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"},{version:"3.5",diposition:"Contain",voyaN:"N",voyaN_1:"N"}],start: '2017-09-01',title: '2017-09-01'},
        {prd:[{version:"4.5",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"4.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"},{version:"3.5",diposition:"Contain",voyaN:"N",voyaN_1:"N"}],start: '2017-12-01',title: '2017-12-01'},
        {prd:[{version:"4.5",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"},{version:"4.0",diposition:"Install Standard",voyaN:"N",voyaN_1:"Y"}],start: '2018-03-01',title: '2018-03-01'}
     ]);
     var options = {
     	stack: true,
     	horizontalScroll: true,
     	zoomKey: 'ctrlKey',
     	template: template
     };
     var timeline = new vis.Timeline(container, items, options);
     
     $( "#windowsData" ).empty();
     var container = document.getElementById('windowsData');	  	
   	 var items = new vis.DataSet([
   		{prd:[{version:"10.0",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"}],start: '2017-09-01',title: '2017-09-01'},
   		{prd:[{version:"10.0",diposition:"Industry N",voyaN:"Y",voyaN_1:"N"}],start: '2017-12-01',title: '2017-12-01'}
     ]);
     var options = {
     	stack: true,
     	horizontalScroll: true,
     	zoomKey: 'ctrlKey',
     	template: template
     };
     var timeline = new vis.Timeline(container, items, options);

}