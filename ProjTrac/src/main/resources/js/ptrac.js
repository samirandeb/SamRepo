var appinvdata = "0000";
function showWaitScreen(){
	//console.log('showing loading screen');
	$("#loadingScreen").show();
}

function hideWaitScreen(){
	//console.log('hiding the loading screen');
	$("#loadingScreen").hide();
}

function getAppData(){
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "dependency",
        success: function(data){     
          console.log(data._embedded.application);
           
           $('#appTable').DataTable( {
               data: data._embedded.dependency,
               columns: [
            	   
                   { data: "childAppName",title: "Child Name" },
                   { data: "parentApp", title: "Parent App" },
                   { data: "parentId", title: "Parent Id" }
               ]
           } );
           
           hideWaitScreen();
        }
    });
}


function getDependency(appName){
	
	//console.log("App Name :::::::::::::"+appName);
	
	var url = "/ptrac/dependency/search/findByParentApp?name="+appName;
	
	//console.log("url::"+url);
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: url,
        success: function(data){   
           //console.log(data._embedded.dependency);
           var depdencyData = data._embedded.dependency;
           
           if(depdencyData && depdencyData.length){
        	   
        	   var nodeArray = [];
               var edgeArray = [];
               
               var startId = depdencyData[0].parentId;
               
               nodeArray.push({ id: startId, label: depdencyData[0].parentApp, clicked:1, parentId:depdencyData[0].parentId, childId:depdencyData[0].childId});
               
               for (var j = 0; j < depdencyData.length; j++){

            	   nodeArray.push({ id: depdencyData[j].childId, label: depdencyData[j].childAppName, clicked:0, parentId:depdencyData[j].parentId, childId:depdencyData[j].childId});
            	   edgeArray.push({ from: startId, to: depdencyData[j].childId, arrows:'to'})

               }
               
               createNetwork();

               function createNetwork() {
                   var nodes = new vis.DataSet(nodeArray);
                   //console.log('Create nodes ', nodeArray.length);
                   var edges = new vis.DataSet(edgeArray);
                   //console.log('Create edge', edgeArray.length);
                   var container = document.getElementById('mynetwork');

                   var data = {
                       nodes: nodes,
                       edges: edges
                   };
                   var options = {
                       nodes: {
                           shape: 'dot',
                           size: 10
                       },
                       edges: {
                           width: 2,
                           shadow: true,
                           color:{color:'green', highlight:'red'}
                       }
                   };
                   //console.log('Create network');
                   var network = new vis.Network(container, data, options);
                   //console.log('event listener');
                   network.on("click", function (params) {
                       //console.log(nodes);
                       params.event = "[original event]";
                       //document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(nodes._data[this.getNodeAt(params.pointer.DOM)], null, 4);
                       document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(findApp(nodeArray,nodes._data[this.getNodeAt(params.pointer.DOM)].id), null, 4);
                       //console.log(nodes._data[this.getNodeAt(params.pointer.DOM)]);

                   });
                   network.on("doubleClick", function (params) {
                	   /*console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].id);
                	   console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].label);
                	   console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].clicked);*/
                	   
                	   //(nodes._data[this.getNodeAt(params.pointer.DOM)].clicked).value = 1;
                	   
                	   
                	   var clickedAppId = nodes._data[this.getNodeAt(params.pointer.DOM)].id;
                	   var clickedAppName = nodes._data[this.getNodeAt(params.pointer.DOM)].label;
                	   var clicked = nodes._data[this.getNodeAt(params.pointer.DOM)].clicked;
                	   
                	   //console.log("clicked:"+clicked);
                	   
                	   for (var i in nodeArray) {
            		     if (nodeArray[i].label == clickedAppName) {
            		    	 nodeArray[i].clicked = 1;
            		        break; //Stop this loop, we found it!
            		     }
            		   }
                	   
                	   //console.log(findApp(nodeArray,clickedAppName).label);
                	   
                	   var nodeLength = nodeArray.length;
                	   
                	   if(clicked==1){
                		   alert("Already Found dependency for ("+clickedAppName+")");
                	   }else{
                		   showWaitScreen();
                		   $.ajax({ 
	                   	        type: "GET",
	                   	        dataType: "json",
	                   	        url: "/ptrac/dependency/search/findByParentApp?name="+clickedAppName,
	                   	        success: function(data){
	                   	        	//console.log(data._embedded.dependency);
	                   	        	depdencyData = data._embedded.dependency;
	                   	        	if(depdencyData && depdencyData.length){
	                   	        		//alert(depdencyData);
	                   	        		//nodeArray.push({ id: 1, label: depdencyData[0].parentApp});
	                   	        		var existApp=null;
	                   	                for (var j = 0; j < depdencyData.length; j++){
	
	                   	                   existApp=findApp(nodeArray,depdencyData[j].childId);
	                   	                                     	             	   
	                   	             	   if(existApp){
		                   	             		//console.log("Inside IFF:"+existApp.label);
		                   	             	    //edgeArray.push({ from: existApp.childId, to: clickedAppId, arrows:'to'});
		                   	             	    edgeArray.push({ from: clickedAppId, to: existApp.childId, arrows:'to'});
	                   	             		    //findAppEdge(edgeArray,existApp.id).arrows='to, from';
	                   	             		    //edgeArray.push({ from: clickedAppId, to: depdencyData[j].childId,arrows:'to' });
	                   	             	   }else {	                   	             		   
		                   	             		//console.log("Else:"+existApp);
		                   	             		nodeArray.push({ id: depdencyData[j].childId, label: depdencyData[j].childAppName, clicked:0, parentId:depdencyData[j].parentId, childId:depdencyData[j].childId});
		                   	             		edgeArray.push({ from: clickedAppId, to: depdencyData[j].childId, arrows:'to'});	                   	             		   
	                   	             	   }
	                   	                }
	                   	        		
	                   	             createNetwork();
	                   	        	}else {
	                   	        		alert("No Dependency Found for ("+clickedAppName+")");
	                   	        	}
	                   	         hideWaitScreen();
	                   	        }
	                   	    });
                		   
                	   }
                   });
               }
           }
           hideWaitScreen();
        }
    });
}

function fullDependency(){
	
	var url = "/ptrac/dependency/search/findByChildApp?name=TRACK_1";

	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: url,
        success: function(data){   
           //console.log(data._embedded.dependency);
           var depdencyData = data._embedded.dependency;
           
           if(depdencyData && depdencyData.length){
        	   
        	   var nodeArray = [];
               var edgeArray = [];
               
               //var startId = depdencyData[0].parentId;
               
               //nodeArray.push({ id: startId, label: depdencyData[0].parentApp, clicked:1, parentId:depdencyData[0].parentId, childId:depdencyData[0].childId});
               
			    var existApp=null;
			    for (var j = 0; j < depdencyData.length; j++){
				
				    existApp=findApp(nodeArray,depdencyData[j].childId);
				                         	             	   
				 	if(existApp){
				 		//console.log("Inside IFF:"+existApp.label);
				    //edgeArray.push({ from: existApp.childId, to: clickedAppId, arrows:'to'});
				    edgeArray.push({ from: depdencyData[0].parentId, to: existApp.childId, arrows:'to'});
					    //findAppEdge(edgeArray,existApp.id).arrows='to, from';
					    //edgeArray.push({ from: clickedAppId, to: depdencyData[j].childId,arrows:'to' });
				   }else {	                   	             		   
					//console.log("Else:"+existApp);
					nodeArray.push({ id: depdencyData[j].childId, label: depdencyData[j].childAppName, clicked:0, parentId:depdencyData[j].parentId, childId:depdencyData[j].childId});
					edgeArray.push({ from: depdencyData[0].parentId, to: depdencyData[j].childId, arrows:'to'});	                   	             		   
				   }
				}
               
               createNetwork();

               function createNetwork() {
                   var nodes = new vis.DataSet(nodeArray);
                   //console.log('Create nodes ', nodeArray.length);
                   var edges = new vis.DataSet(edgeArray);
                   //console.log('Create edge', edgeArray.length);
                   var container = document.getElementById('mynetwork');

                   var data = {
                       nodes: nodes,
                       edges: edges
                   };
                   var options = {
                       nodes: {
                           shape: 'dot',
                           size: 10
                       },
                       edges: {
                           width: 2,
                           shadow: true,
                           color:{color:'green', highlight:'red'}
                       }
                   };
                   //console.log('Create network');
                   var network = new vis.Network(container, data, options);
                   //console.log('event listener');
                   network.on("click", function (params) {
                       //console.log(nodes);
                       params.event = "[original event]";
                       //document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(nodes._data[this.getNodeAt(params.pointer.DOM)], null, 4);
                       document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(findApp(nodeArray,nodes._data[this.getNodeAt(params.pointer.DOM)].id), null, 4);
                       //console.log(nodes._data[this.getNodeAt(params.pointer.DOM)]);

                   });
                   network.on("doubleClick", function (params) {
                	   /*console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].id);
                	   console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].label);
                	   console.log(nodes._data[this.getNodeAt(params.pointer.DOM)].clicked);*/
                	   
                	   //(nodes._data[this.getNodeAt(params.pointer.DOM)].clicked).value = 1;
                	   
                	   
                	   var clickedAppId = nodes._data[this.getNodeAt(params.pointer.DOM)].id;
                	   var clickedAppName = nodes._data[this.getNodeAt(params.pointer.DOM)].label;
                	   var clicked = nodes._data[this.getNodeAt(params.pointer.DOM)].clicked;
                	   
                	   //console.log("clicked:"+clicked);
                	   
                	   for (var i in nodeArray) {
            		     if (nodeArray[i].label == clickedAppName) {
            		    	 nodeArray[i].clicked = 1;
            		        break; //Stop this loop, we found it!
            		     }
            		   }
                	   
                	   //console.log(findApp(nodeArray,clickedAppName).label);
                	   
                	   var nodeLength = nodeArray.length;
                	   
                	   if(clicked==1){
                		   alert("Already Found dependency for ("+clickedAppName+")");
                	   }else{
                		   showWaitScreen();
                		   $.ajax({ 
	                   	        type: "GET",
	                   	        dataType: "json",
	                   	        url: "/ptrac/dependency/search/findByParentApp?name="+clickedAppName,
	                   	        success: function(data){
	                   	        	//console.log(data._embedded.dependency);
	                   	        	depdencyData = data._embedded.dependency;
	                   	        	if(depdencyData && depdencyData.length){
	                   	        		//alert(depdencyData);
	                   	        		//nodeArray.push({ id: 1, label: depdencyData[0].parentApp});
	                   	        		var existApp=null;
	                   	                for (var j = 0; j < depdencyData.length; j++){
	
	                   	                   existApp=findApp(nodeArray,depdencyData[j].childId);
	                   	                                     	             	   
	                   	             	   if(existApp){
		                   	             		//console.log("Inside IFF:"+existApp.label);
		                   	             	    //edgeArray.push({ from: existApp.childId, to: clickedAppId, arrows:'to'});
		                   	             	    edgeArray.push({ from: clickedAppId, to: existApp.childId, arrows:'to'});
	                   	             		    //findAppEdge(edgeArray,existApp.id).arrows='to, from';
	                   	             		    //edgeArray.push({ from: clickedAppId, to: depdencyData[j].childId,arrows:'to' });
	                   	             	   }else {	                   	             		   
		                   	             		//console.log("Else:"+existApp);
		                   	             		nodeArray.push({ id: depdencyData[j].childId, label: depdencyData[j].childAppName, clicked:0, parentId:depdencyData[j].parentId, childId:depdencyData[j].childId});
		                   	             		edgeArray.push({ from: clickedAppId, to: depdencyData[j].childId, arrows:'to'});	                   	             		   
	                   	             	   }
	                   	                }
	                   	        		
	                   	             createNetwork();
	                   	        	}else {
	                   	        		alert("No Dependency Found for ("+clickedAppName+")");
	                   	        	}
	                   	         hideWaitScreen();
	                   	        }
	                   	    });
                		   
                	   }
                   });
               }
           }
           hideWaitScreen();
        }
    });
}

function findApp(nodeArray,childAppId){
	return nodeArray.filter(function(item) {
        return item.id === childAppId;
    })[0];
}

function findAppEdge(edgeArray,appId){
	return edgeArray.filter(function(item) {
        return item.from === appId || item.to === appId;
    })[0];
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
                	   {
                		   data: "appId", title: "Select", 
                		   "render": function(data, type, row, meta){
                			   if(type === 'display'){
                 	                data = '<input type="checkbox" name="record" value="'+data+'">';
                 	            }            	            
                 	            return data;
                		   }
                	   },
                     {
              		   data: "name", title: "Name", 
              		   "render": function(data, type, row, meta){
              	            if(type === 'display'){
              	            	//console.log(row.roadmap);
              	                data = '<a href="#" onclick="javascript:displayAppDtls(\''+row.appId+'\')" >' + data + '</a>';
              	            }            	            
              	            return data;
              		   }
              	   },
                     { data: "lob",title: "LOB" },
                     { data: "score", title: "Score" }
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

function displayAppDtls(appId){
	//alert(appId);
	data=[{"type":"Programming Language","value":"Oracle JDK","itt_n":"8","itt_n_1":"8","currVer":"8", "tarVer":"8", "category":"LS" },{"type":"Operating System","value":"Red Hat Linux","itt_n":"9.5","itt_n_1":"9.4/9.3","currVer":"8.2", "tarVer":"9.5", "category":"VU"},{"type":"Database","value":"Oracle","itt_n":"12g","itt_n_1":"11g","currVer":"10g", "tarVer":"12g", "category":"VU"},{"type":"Application Server","value":"Websphere","itt_n":"9.0","itt_n_1":"8.5","currVer":"7", "tarVer":"9.0", "category":"VU"}, {"type":"Database","value":"DB2","itt_n":"10.5","itt_n_1":"10.5","currVer":"10.5", "tarVer":"10.5", "category":"LS"},{"type":"Framework","value":"Struts","itt_n":"2.6","itt_n_1":"2.6","currVer":"2.6", "tarVer":"2.6", "category":"LS"}];
	  $('#ApplicationDetails').DataTable({
   	   destroy: true,
   	   paging:   false,
   	   searching: false,
   	   data: data,
   	"columnDefs": [
       
        {
            "targets": [ 6 ],
            "visible": false
        }
    ],
          columns: [
       	   
              { data: "type", title: "Type" },
              { data: "value",title: "Value" },
              { data: "itt_n", title: "ITT N" },
              { data: "itt_n_1", title: "ITT N-1" },
              { data: "currVer", title: "Current Version" },
              { data: "tarVer", title: "Target Version" },
              { data: "category", title: "Category" }

          ],
          "fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        	 // console.log(aData.category);
        	// alert(nRow[iDisplayIndex].children()[1]);
        	 /* $(nRow).children().each(function (index, td) {
    			  console.log(td);
    		       $(this).addClass('highlightRow');
    		   });*/
        	  if ( aData.category == "VU" ){         
        	     //$(nRow).addClass('highlightRow');
        	    // console.log(iDisplayIndex);
        		  $(nRow).children().each(function (index, td) {
        			 // console.log(td);
        		       $(this).addClass('highlightRow');
        		   });
        	   }
        	 }

      });
	//console.log("input param::"+roadmap+","+mgmtGrp+","+manufacturer+","+prodDispName);
	/*if($.fn.dataTable.isDataTable( '#dependentData')) {
		$('#dependentData').DataTable().clear();
	}*/
	   
	//var myUrl = "software/search/findByGroupingRslt?roadmap="+roadmap;
	//console.log("myUrl:"+myUrl);
	//showWaitScreen();
	
    
/*	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data){     
           $('#dependentData').DataTable({
        	   destroy: true,
        	   paging:   false,
        	   searching: false,
        	   "columnDefs": [
        		    { "width": "20px", "targets": 2 }
        		  ],
        	   data: data._embedded.software,
               columns: [
            	   
                   { data: "prodDispName", title: "Product Name" },
                   { data: "indProdName",title: "Industray Name" },
                   { data: "version", title: "Version" },
                   { data: "voyaProdLfCyclDispo", title: "DISPOSITION" },
                   { data: "vendN", title: "Vendor N" },
                   { data: "voyaN", title: "Voya N" },
                   { data: "voyaN_1", title: "Voya N-1" }

               ]

           });
           
           hideWaitScreen();
           
        }
    });*/
}

function loadSoftware(id) {
	showWaitScreen();
	 $.ajax({ 
   	        type: "GET",
   	        dataType: "json",
   	        url: "/ptrac/prddtls",
   	        success: function(data){
   	        	
   	        	software = data._embedded.prddtls;
       	        	var softData = [];
       	        	for (var j = 0; j < software.length; j++){
   	                	var temp = software[j];
   	                	var ver =temp.version;
   	                	var disp = "";
   	                 if(ver === undefined || ver == null || ver.length <= 0 ){
   	                	disp = temp.indProdName;
   	                 } else{
   	                	disp = temp.indProdName+'('+temp.version+')';
   	                 }
   	                	softData.push({"id":temp.prdtlId, "text":disp});
   	                }
       	     
       	      fillData($("#pl"), softData);
       	      fillData($("#db"), softData);
       	      fillData($("#as"), softData);
       	      fillData($("#middleware"), softData);
       	      fillData($("#devops"), softData);
       	      fillData($("#framework"), softData);
       	   fillData($("#os"), softData);
       	   //setSelectedData($("#pl"),["102","320","451","1109","420"]);
       	   //setSelectedData($("#db"),["102","420"]); 
	       if(id.length>0) {
	 		 loadAppInvdata(id);
	       }
       	   //console.log("done");
       	   hideWaitScreen();
   	    }
      	            
   	    });
	 
	
}

function loadAppInvdata(id) {
	
var url = "appInvExistData/"+id;
appinvdata = id;	
	//console.log("url::"+url);
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: url,
        success: function(data){   
           //console.log("appinv");
           //console.log(data[0].appServerId.split(','));
           //var as = $.map(data[0].appServerId.split(','), $.trim);
           //console.log("AS",as);
         /*  var a = data[0].appServerId;
           var x = new Array();
           x = a.split(",");
           alert(x);*/
        	console.log("dat--->"+data[0].appId);
        	   if(data[0].progLugId !== null){
        	   setSelectedData($("#pl"),$.map(data[0].progLugId.split(','), $.trim));
        	   }
        	   
        	   if(data[0].databaseId !== null){
	           setSelectedData($("#db"),$.map(data[0].databaseId.split(','), $.trim));
        	   }
        	   if(data[0].appServerId !== null){
	           setSelectedData($("#as"),$.map(data[0].appServerId.split(','), $.trim));
        	   }
        	   if(data[0].frameWorkId !== null){
	           setSelectedData($("#framework"),$.map(data[0].frameWorkId.split(','), $.trim));
        	   }
        	   if(data[0].operatingSysId !== null){
	           setSelectedData($("#os"),$.map(data[0].operatingSysId.split(','), $.trim));
        	   }
        	   if(data[0].devopsId !== null){
	           setSelectedData($("#devops"),$.map(data[0].devopsId.split(','), $.trim)); 
        	   }
        	   if(data[0].middlewareId !== null){
	           setSelectedData($("#middleware"),$.map(data[0].middlewareId.split(','), $.trim)); 
        	   } 
           //setSelectedData($("#db"),$.map(data[0].appServerId.split(','), $.trim)); 
           
           $("#appName").val(data[0].appName);
           $("#appName").prop('readonly',true);
           $("#lob").val(data[0].lob);
           $("#curr_scope").val(data[0].scope);
           $("#technology").val(data[0].technology);
           $("#targetDC").val(data[0].targetDC);
           //$("#lob").prop('disabled',true);
           console.log(data);
           hideWaitScreen();
        }
    });
	
}


function insertData() {
	var myUrl = "insertApplication/" + $("#appName").val() + '/'
			+ $("#lob").val()+ '/'+ $("#curr_scope").val()+ '/'+ $("#technology").val()+ '/'+ $("#targetDC").val() + '/' + $("#os").val() + '/'
			+ $("#pl").val() + '/' + $("#db").val() + '/'
			+ $("#as").val() + '/' + $("#middleware").val() + '/'
			+ $("#devops").val()+ '/' + $("#framework").val() + '/' +appinvdata;
	showWaitScreen();
	
	if( $("#appName").val()!== null && $("#lob").val()!== null && $("#appName").val().length> 0 && $("#lob").val().length> 0 
			/*&& $("#os").val()!== null && $("#pl").val()!== null
			&& $("#db").val()!== null && $("#as").val()!== null 
			&& $("#devops").val()!== null && $("#framework").val()!== null 
			&& $("#middleware").val()!== null*/){
		
		$.ajax({ 
	        type: "GET",
	        dataType: "text",
	        url: myUrl,
	        success: function(data){
	        	//alert('Insert Application Data','Data Saved Successfully');
	        	 window.location = "/ptrac/appInventory";
	         hideWaitScreen();
	        }
  	            
	    });
} else{
	hideWaitScreen();
//	alert('All Fields are mandatory!');
	alert('Application name and LOB are mandatory!');
}
	
}

function clearData(){
	setSelectedData($("#pl"),[]);
    setSelectedData($("#db"),[]); 
    setSelectedData($("#as"),[]); 
    setSelectedData($("#framework"),[]); 
    setSelectedData($("#os"),[]); 
    setSelectedData($("#devops"),[]); 
    setSelectedData($("#middleware"),[]);
    if(appinvdata === "0000"){
    	$('#appName').val('');
    	$('#lob').val('');
    }
}

function fillData(field, jsonData){
	field.select2({
	 placeholder: "Select",
		data : jsonData,
		allowClear: true
});
}

function setSelectedData(name,data){
	name.val(data).trigger("change");
	//$("#pl").val(["100", "101"]).trigger("change");
} 


/*var exitFullscreen = function () {
	if (document.exitFullscreen) {
		document.exitFullscreen();
	} else if (document.webkitExitFullscreen) {
		document.webkitExitFullscreen();
	} else if (document.mozCancelFullScreen) {
		document.mozCancelFullScreen();
	} else if (document.msExitFullscreen) {
		document.msExitFullscreen();
	} else {
		console.log('Fullscreen API is not supported.');
	}
};

var fsDocButton = document.getElementById('full-Screen-id');
//var fsExitDocButton = document.getElementById('fs-exit-doc-button');

fsDocButton.addEventListener('click', function(e) {
	e.preventDefault();
	requestFullscreen(document.documentElement);
});
*/
function getAppInventoryData(id){
	var myUrl;
	if(id.length>0){
	 myUrl = "appInvData/"+id;
	}else{
		myUrl = "appInvData/"+"0000";	
	}
	console.log("myUrl:"+myUrl);
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data){     
          console.log(data);
           $('#inventory').DataTable({
        	   destroy: true,
        	   paging: true,
        	   fixedColumns: true,
        	   "columns": [
        		    { "width": "2%" },
        		    null,
        		    null,
        		    { "width": "20%" },
        		    { "width": "35%" },
        		    { "width": "30%" },
        		    null,
        		    null
        		  ],
        	   /*scrollX: true,
               scrollCollapse: true,*/
        	   searching: true,
        	   data: data,
               columns: [            	 
                   { data: "appName", title: "Application Name" },
                   { data: "lob", title: "LOB" },
                   { data: "scope", title: "Currency Scope" },
                   { data: "technology", title: "Technology Type" },
                   { data: "targetDC", title: "Target DC" },
                   { data: "operatingSys", title: "OS" },
                   { data: "progLug", title: "Programming Language" },
                   { data: "appServer", title: "Application Server" },
                   { data: "frameWork", title: "Framework" },
                   { data: "database", title: "Database" },
                   { data: null, title: "Action" , 
            		   "render": function(data, type, row, meta){
            			   /*console.log(row.appId);*/
            	            if(row.appId !== null){
            	                data = '<a href="/ptrac/addApplication?'+row.appId+'">' + '<i class="fa fa-edit"></i>' + '</a>';
            	            }else{
            	            	data = '';
            	            }
            	                 console.log();
            	                return data;
            	            }            	            
            	             
            	            }
                   
                   
                   
               ]
           });
           
           hideWaitScreen();
        }
    });
}