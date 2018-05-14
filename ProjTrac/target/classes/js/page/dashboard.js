function showWaitScreen(){
	$("#loadingScreen").show();
}

function hideWaitScreen(){
	$("#loadingScreen").hide();
}


var globatIdList=[];
function getAllAppsCurrencyDetails(){
	
	showWaitScreen();
	var localIdList = [];
	var myUrl = "dashboard/all";
	$.ajax({ 
        type: "GET",
        dataType: "text",
        url: myUrl,
        success: function(data){
        	var obj = JSON.parse(data);
        
        	console.log("+++++++++++"+obj);
        	$('#AllAppsCurrencyDetails').DataTable({
        		destroy: true,
        		paging:   false,
        		searching: false,
        		data: obj,
        		columns: [
	       	   
        		          { data: "LOB",title: "LOB" },
        		          { data: "NAME", title: "Application Name" },
        		          { data: "TECHNOLOGY", title: "Technology" },
        		          { data: "STATUS", title: "Status" },	              
	              ]

        	})
        	globatIdList = localIdList;
        	console.log(globatIdList.length);        	 
        }
            
    });	
	hideWaitScreen();
}


function getStatistics(){
	
	showWaitScreen();
	var myUrl = "dashboard/stats";
	$.ajax({ 
        type: "GET",
        dataType: "text",
        url: myUrl,
        success: function(data){
        	var obj = JSON.parse(data);
        
        	console.log("+++++++++++"+obj);
        	$('#AllAppsCurrencyDetails').DataTable({
        		destroy: true,
        		paging:   false,
        		searching: false,
        		data: obj,
        		columns: [
	       	   
        		          { data: "LOB",title: "LOB" },
        		          { data: "NAME", title: "Application Name" },
        		          { data: "TECHNOLOGY", title: "Technology" },
        		          { data: "STATUS", title: "Status" },	              
	              ]

        	})
        	globatIdList = localIdList;
        	console.log(globatIdList.length);        	 
        }
            
    });	
	hideWaitScreen();
}
