function showWaitScreen(){
	//console.log('showing loading screen');
	$("#loadingScreen").show();
}

function hideWaitScreen(){
	//console.log('hiding the loading screen');
	$("#loadingScreen").hide();
}

function fullScreen() {
	var ele = document.getElementById('mynetwork');
	if (ele.requestFullscreen) {
		ele.requestFullscreen();
	} else if (ele.webkitRequestFullscreen) {
		ele.webkitRequestFullscreen();
	} else if (ele.mozRequestFullScreen) {
		ele.mozRequestFullScreen();
	} else if (ele.msRequestFullscreen) {
		ele.msRequestFullscreen();
	} else {
		console.log('Fullscreen API is not supported.');
	}
};

function loadApplication(){
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "application",
        success: function(data){     
        	 $('#ApplicationData').DataTable({
          	   destroy: true,
          	   data: data._embedded.application,
                 columns: [
                	  /* {
                		   data: "appId", title: "Select", 
                		   "render": function(data, type, row, meta){
                			   if(type === 'display'){
                 	                data = '<input type="checkbox" name="record" value="'+data+'">';
                 	            }            	            
                 	            return data;
                		   }
                	   },*/
                     {
              		   data: "name", title: "Name", 
              		   "render": function(data, type, row, meta){
              	            if(type === 'display'){
              	            	//console.log(row.roadmap);
              	                data = '<a href="#" onclick="javascript:displayAppDtls(\''+row.appId+'\',\''+row.name+'\')" >' + data + '</a>';
              	            }            	            
              	            return data;
              		   }
              	   },
                     { data: "lob",title: "LOB" },
                    /* { data: "score", title: "Score" },*/
                     { data: "status", title: "Status"},
                     {
                  	   data: null, title: "",
                  	   "render": function(data, type, row, meta){
                  		  if(row.status==='Recommended'){
                  		   data = '<a href="#" onclick="popUpViewBox(\''+row.appId+'\',\''+row.name+'\',\''+row.lob+'\')">' + '<i class="fa fa-check"></i>' + '</a>';
                  		  }else{
                  		   data= '<span class="disable-links"><a href="#" style="color:#808080">' + '<i class="fa fa-check"></i>' + '</a></span>';
                  			  
                  		  }
                  		   return data;
                  	   }
                  		   
                     }

                 ]
           
             });
           hideWaitScreen();
        }
    });

	$(".evaluate-row").click(function(){
		 var favorite = [];
         $.each($("input[name='record']:checked"), function(){            
             favorite.push($(this).val());
         });
         var myUrl = "evaluate/" +favorite.join(", ");
	//console.log(myUrl);
	showWaitScreen();
		$.ajax({ 
	        type: "GET",
	        dataType: "text",
	        url: myUrl,
	        success: function(data){
	        	alert('Successfully');
	         hideWaitScreen();
	        }
	            
	    });
	
	 });

}
var globatIdList=[];
function displayAppDtls(appId ,name){
	
	var localIdList = [];
	//alert(appId);
	var myUrl = "appDtlsData/"+appId;
	//console.log(myUrl);
	//data=[{"type":"Programming Language","value":"Oracle JDK","itt_n":"8","itt_n_1":"8","currVer":"8", "tarVer":"8", "category":"LS" },{"type":"Operating System","value":"Red Hat Linux","itt_n":"9.5","itt_n_1":"9.4/9.3","currVer":"8.2", "tarVer":"9.5", "category":"VU"},{"type":"Database","value":"Oracle","itt_n":"12g","itt_n_1":"11g","currVer":"10g", "tarVer":"12g", "category":"VU"},{"type":"Application Server","value":"Websphere","itt_n":"9.0","itt_n_1":"8.5","currVer":"7", "tarVer":"9.0", "category":"VU"}, {"type":"Database","value":"DB2","itt_n":"10.5","itt_n_1":"10.5","currVer":"10.5", "tarVer":"10.5", "category":"LS"},{"type":"Framework","value":"Struts","itt_n":"2.6","itt_n_1":"2.6","currVer":"2.6", "tarVer":"2.6", "category":"LS"}];
	//data1=[{"TYPE":"Programming Language","value":"Oracle JDK","ITT_N":"8","ITT_N_1":"8","CURRVER":"8", "tarVer":"8", "COMMENT":"LS" }];
	$.ajax({ 
        type: "GET",
        dataType: "text",
        url: myUrl,
        success: function(data){
        	var obj = JSON.parse(data);
        	//console.log(obj);
        	$('#ApplicationDetails').DataTable({
        	   	   destroy: true,
        	   	   paging:   false,
        	   	   searching: false,
        	   	   data: obj,
        	   	"columnDefs": [
        	       
        	        {
        	            "targets": [ 7 ],
        	            "visible": false
        	        },
        	        {
        	            "targets": [ 8 ],
        	            "visible": false
        	        },
        	        {
        	            "targets": [ 9 ],
        	            "visible": false
        	        }
        	    ],
        	          columns: [
        	       	   
        	              { data: "TYPE", title: "Type"  ,
        	            	  
                          	   "render": function(value) {
                          		   if(value === 'db' ){
                          			   value = 'Databse';
                          			   
                          		   }else if(value === 'as') {
                          			   value = 'Application Server'
                          		   }else if(value === 'pl'){
                          			 value = 'Programming Language'  
                          		   }  else if(value === 'os'){
                          			 value = 'Operating System'  
                          		   }   else if(value === 'middleware'){
                          			 value = 'Middleware'  
                          		   }   else if(value === 'devops'){
                          			 value = 'DevOps'  
                          		   }    else if(value === 'framework'){
                          			 value = 'Framework'  
                          		   }    
                          		return value;
                          	      } },
        	              { data: "VALUE",title: "Value" },
        	              { data: "ITT_N", title: "ITT N" },
        	              { data: "ITT_N_1", title: "ITT N-1" },
        	              { data: "CURRVER", title: "Current Version" },
        	              { data: "TARGET", title: "Target Version" ,
                        	   "render": function(data, type, row, meta){
                          		  if(type === 'display'){
                          			$("#btnDecisionSave").show();
                          			$("#appDetails").text('('+name+')').show();
                          		data='<select class="" style="width:75px;" id="target-'+row.APP_ID+'-'+row.APP_DTLS__ID+'">';
                          		var arr = row.combo;
                          		for(var i=0; i<arr.length;i++){
                          			if(arr[i]===row.TARGET){
                          			data=data+'<option value="'+arr[i]+'" selected="selected">'+arr[i]+'</option>';
                          			} else{
                          				data=data+'<option value="'+arr[i]+'">'+arr[i]+'</option>';
                          			}
                          		}
                          		data=data+'</select>';
                          		
                          		//selected="selected"
                          		  }
                          		   return data;
                          	   }},
                          {
                         	   data: "COMMENT", title: "Comment",
                         	   "render": function(data, type, row, meta){
                         		  if(type === 'display'){
                         			 
                         			  var found = localIdList.some(function (el) {
                         			    return el === row.APP_ID+'-'+row.APP_DTLS__ID;
                         			  });
                         			  if (!found) { localIdList.push(row.APP_ID+'-'+row.APP_DTLS__ID); }
                         			  
                         		   data = '<input type="text" class="" value="'+data+'" id="obsoleteSupport-'+row.APP_ID+'-'+row.APP_DTLS__ID+'"/>';
                         		  }
                         		   return data;
                         	   }
                          },
                          { data: "APP_DTLS__ID", title: "APP_DTLS__ID" }
                          ,
                          { data: "APP_ID", title: "APP_ID" },
                          { data: "PRD_ID", title: "PRD_ID" },
                          { data: null, title:"",
                        	  "render": function(data, type, row, meta){
                       		   
                        		  if( data.category == "VU" ){
                        		   data = '<i class="fa fa-circle" style="color: orange"></i>';
                        		  }
                        		  else{
                        		   data= '<i class="fa fa-circle" style="color: green"></i>';
                        		  }
                        		   return data;
                        	   }
                          }
        	          ]/*,
        	          "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {

        	        	  if ( aData.category == "VU" ){         
        	        		  $(nRow).children().each(function (index, td) {
        	        		       $(this).addClass('highlightRow');
        	        		   });
        	        	   }
        	        	 // var table = $('#ApplicationDetails').DataTable();
        	        	  
        	        	  
        	        	 }*/

        	      })
        	      globatIdList = localIdList;
        	      console.log(globatIdList.length);
        	 
        }
            
    });
	
	
}

function popUpViewBox(appId,name,lob) {
    $("#popupViewDF").modal("show");
    $("#popupViewDF #appId").val(appId);
    $("#popupViewDF #lob").val(lob);
    $("#popupViewDF #name").val(name);
    
}
function updateApprvStatus(){
 var softwareData  = {};
    softwareData.appId =  $("#popupViewDF #appId").val();
    softwareData.lob = $("#popupViewDF #lob").val();
    softwareData.name = $("#popupViewDF #name").val();
    softwareData.status='Approved';
	console.log("value is for rodmap",softwareData.status);
	console.log("going to update with",softwareData);
//	decision
	$.ajax({
        url: 'application',
		contentType : "application/json",
        type: 'post',
        dataType: 'text',
        success: function (resp) {
//        	$("#masterView").modal("hide");
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
    
 
}


/*
function updateApproveStatus(appId,name,lob){
	
    if (confirm("Are you sure you want to approve the recommended versions?") == true) {
        var softwareData  = {};
    	softwareData.name = name;
    	softwareData.lob = lob;
        softwareData.appId=appId;
        softwareData.status='Approved';
    	console.log("value is for rodmap",softwareData.status);
    	console.log("going to update with",softwareData);
//    	decision
    	$.ajax({
            url: 'application',
    		contentType : "application/json",
            type: 'post',
            dataType: 'text',
            success: function (resp) {
//            	$("#masterView").modal("hide");
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

}*/



function saveDecision(){
	
	
	/* console.log( $("#"+globatIdList[6]).val());
	 $( globatIdList ).each(function( i ) {
		 console.log(globatIdList[i].split('-')[0]);
		 console.log(globatIdList[i].split('-')[1]);
		      console.log( $("#"+'target-'+globatIdList[i]).val());
		      console.log( $("#"+'obsoleteSupport-'+globatIdList[i]).val());//obsoleteSupport
		  });*/
	/*var table = $('#ApplicationDetails').DataTable();
	var data=table.rows().data();
	//console.log(data.length); */
	 var catalogue = [];
	 for(var i=0; i<globatIdList.length; i++){

		 var comment = $("#"+'obsoleteSupport-'+globatIdList[i]).val();
		 if(comment === undefined ){
			 comment = null;
		 }
		  
		 var app_id = (globatIdList[i].split('-')[0]);
		 if(app_id === undefined ){
			 app_id = null;
		 }
		 
		 var app_dtls_id = (globatIdList[i].split('-')[1]);
		 if(app_dtls_id === undefined ){
			 app_dtls_id = null;
		 }
		 
		 var tarVer =  $("#"+'target-'+globatIdList[i]).val();
		 if(tarVer === undefined ){
			 tarVer = null;
		 }
		// localproducts.push( {'app_id':app_id});
		 
		 //var car = {type:"Fiat", model:"500", color:"white"};
		 var localproducts = {'COMMENT':comment, 'app_dtls_id':app_dtls_id, 'app_id':app_id, 'tarVer':tarVer };
		// console.log(localproducts);
		 catalogue.push(localproducts);  
	 }

/*	$('data').each(function() {
		 var localproducts = {};
		var $itm = $(this);
        localproducts.push({
            'PRD_ID' : $itm.attr('PRD_ID'), 
            'COMMENT' : $itm.attr('COMMENT'),
            'tarVer' : $itm.attr('tarVer')
        });
        console.log(localproducts);
	    catalogue.push(localproducts);  
	});*/
	//console.log(catalogue);
	$.ajax({
        url: 'decision',
		contentType : "application/json",
        type: 'post',
        dataType: 'text',
        success: function (resp) {
//        	$("#masterView").modal("hide");
        	location.reload();
        	console.log("Success Respose : ",resp);
        	//showAlert("Updated successfully !!!",null,"success",function(){window.location = "applications";});
        },
        error : function(err){
        	console.log("Err : ",err);
        	//showAlert("Failed to update",null,error);
        },
        data:JSON.stringify(catalogue)
    });
}
