function getAppData(){
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "application",
        success: function(data){        
          // console.log(data._embedded.application);
           
           $('#appTable').DataTable( {
               data: data._embedded.application,
               columns: [
            	   { 
            		   data: "id",title:"#"
            	   },
            	   {
            		   data: "appName", title: "Name", 
            		   "render": function(data, type, row, meta){
            	            if(type === 'display'){
            	                data = '<a href="appDependency/'+data+'" >' + data + '</a>';
            	            }            	            
            	            return data;
            		   }
            	   },
                   /*{ data: "appOwner",title: "Owner" },*/
                   { data: "appType", title: "Type" },
                   { data: "cots", title: "COTS(Y/N)" },
                   { data: "database", title: "Database" },
                   { data: "java", title: "Java" },
                   { data: "microsoft", title: "Microsoft" },
                   /*{ data: "mainframe", title: "Mainframe" },*/
                   { data: "linesOfCodes", title: "LinesOfCode" }
               ]
           } );
        }
    });
}

function showDependency(appName) {
	alert(appName);
	
	/*$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "/appDependency/"+appName,
        success: function(data){        
           console.log("Success............../appDependency/{appName} ...!!");
        }
    });*/
}

function getDependency(appName){
	
	//console.log("App Name :::::::::::::"+appName);
	
	var url = "/ptrac/dependency/search/findByParentApp?name="+appName;
	
	//console.log("url::"+url);
	
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
               
               nodeArray.push({ id: 1, label: depdencyData[0].parentApp, clicked:1});
               
               for (var j = 0; j < depdencyData.length; j++){

            	   nodeArray.push({ id: j+2, label: depdencyData[j].childAppName, clicked:0});
            	   edgeArray.push({ from: 1, to: j+2 })

               }
               /*var nodeArray = [
                   { id: 1, label: 'Node 1' },
                   { id: 2, label: 'Node 2' },
                   { id: 3, label: 'Node 3' },
                   { id: 4, label: 'Node 4' },
                   { id: 5, label: 'Node 5', custom: "customValue" }
               ];*/

               //console.log(nodeArray);
               // create an array with edges
               /*edgeArray = [
                   { from: 1, to: 3 },
                   { from: 1, to: 2 },
                   { from: 2, to: 4 },
                   { from: 2, to: 5 },
                   { from: 3, to: 3 }
               ];*/
               
               createNetwork();

               function createNetwork() {
                   //console.log('*********************');
            	   //alert("Inside createNetwork()");
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
                           color:{color:'grey', highlight:'red'}
                       }/*,
                       layout: {
                    	   hierarchical: {
 	                	      enabled:true,
 	                	      levelSeparation: 150,
 	                	      nodeSpacing: 100,
 	                	      treeSpacing: 200,
 	                	      blockShifting: true,
 	                	      edgeMinimization: true,
 	                	      parentCentralization: true,
 	                	      direction: 'UD',        // UD, DU, LR, RL
 	                	      sortMethod: 'hubsize'   // hubsize, directed
 	                	    }
                       }*/
                       /*layout: {
	                	    randomSeed: undefined,
	                	    improvedLayout:true,
	                	    hierarchical: {
	                	      enabled:false,
	                	      levelSeparation: 150,
	                	      nodeSpacing: 100,
	                	      treeSpacing: 200,
	                	      blockShifting: true,
	                	      edgeMinimization: true,
	                	      parentCentralization: true,
	                	      direction: 'UD',        // UD, DU, LR, RL
	                	      sortMethod: 'hubsize'   // hubsize, directed
	                	    }
                	  }*/
                   };
                   //console.log('Create network');
                   var network = new vis.Network(container, data, options);
                   //console.log('event listener');
                   network.on("click", function (params) {
                       //console.log(nodes);
                       params.event = "[original event]";
                       document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
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
                	   
                	   console.log("clicked:"+clicked);
                	   
                	   for (var i in nodeArray) {
            		     if (nodeArray[i].label == clickedAppName) {
            		    	 nodeArray[i].clicked = 1;
            		        break; //Stop this loop, we found it!
            		     }
            		   }
                	   
                	   var nodeLength = nodeArray.length;
                	   
                	   if(clicked==1){
                		   alert("Already Found dependency for ("+clickedAppName+")");
                	   }else{
                		   $.ajax({ 
	                   	        type: "GET",
	                   	        dataType: "json",
	                   	        url: "/ptrac/dependency/search/findByParentApp?name="+clickedAppName,
	                   	        success: function(data){
	                   	        	console.log(data._embedded.dependency);
	                   	        	depdencyData = data._embedded.dependency;
	                   	        	if(depdencyData && depdencyData.length){
	                   	        		//alert(depdencyData);
	                   	        		
	                   	        		//nodeArray.push({ id: 1, label: depdencyData[0].parentApp});
	                   	                
	                   	                for (var j = 0; j < depdencyData.length; j++){
	
	                   	             	   nodeArray.push({ id: nodeLength+j+1, label: depdencyData[j].childAppName});
	                   	             	   edgeArray.push({ from: clickedAppId, to: nodeLength+j+1 })
	
	                   	                }
	                   	        		
	                   	             createNetwork();
	                   	        	}else {
	                   	        		alert("No Dependency Found for ("+clickedAppName+")");
	                   	        	}
	                   	        }
	                   	    });
                		   
                	   }
                	   
                	   /*params.event = "[original event]";
                       var nodeId = nodeArray.length + 1;
                       var fromId = this.getNodeAt(params.pointer.DOM);
                       var newItem = { id: nodeId, label: 'Node ' + nodeId };
                       nodeArray.push(newItem);
                       var newEdge = { from: fromId, to: nodeId };
                       edgeArray.push(newEdge);
                       document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);*/
                       //createNetwork();
                   });
                   // network.on("oncontext", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>oncontext (right click) event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragStart", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragStart event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragging", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragging event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragEnd", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragEnd event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("zoom", function (params) {
                   //     document.getElementById('eventSpan').innerHTML = '<h2>zoom event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("showPopup", function (params) {
                   //     document.getElementById('eventSpan').innerHTML = '<h2>showPopup event: </h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("hidePopup", function () {
                   //     console.log('hidePopup Event');
                   // });
                   // network.on("select", function (params) {
                   //     console.log('select Event:', params);
                   // });
                   // network.on("selectNode", function (params) {
                   //     console.log('selectNode Event:', params);
                   // });
                   // network.on("selectEdge", function (params) {
                   //     console.log('selectEdge Event:', params);
                   // });
                   // network.on("deselectNode", function (params) {
                   //     console.log('deselectNode Event:', params);
                   // });
                   // network.on("deselectEdge", function (params) {
                   //     console.log('deselectEdge Event:', params);
                   // });
                   // network.on("hoverNode", function (params) {
                   //     console.log('hoverNode Event:', params);
                   // });
                   // network.on("hoverEdge", function (params) {
                   //     console.log('hoverEdge Event:', params);
                   // });
                   // network.on("blurNode", function (params) {
                   //     console.log('blurNode Event:', params);
                   // });
                   // network.on("blurEdge", function (params) {
                   //     console.log('blurEdge Event:', params);
                   // });
               }
           }
           
        }
    });
}

function fullDependency(){
	
	console.log("Inside : fullDependency");
	var url = "/ptrac/dependency";
	
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
               
               nodeArray.push({ id: 1, label: depdencyData[0].parentApp, clicked:1});
               
               for (var j = 0; j < depdencyData.length; j++){

            	   nodeArray.push({ id: j+2, label: depdencyData[j].childAppName, clicked:0});
            	   edgeArray.push({ from: 1, to: j+2 })

               }
               
               createNetwork();

               function createNetwork() {
                   //console.log('*********************');
            	   //alert("Inside createNetwork()");
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
                           color:{color:'grey', highlight:'red'}
                       },
                       layout: {
	                	    improvedLayout:false
                       }
                   };
                   //console.log('Create network');
                   var network = new vis.Network(container, data, options);
                   //console.log('event listener');
                   network.on("click", function (params) {
                       //console.log(nodes);
                       params.event = "[original event]";
                       document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
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
                	   
                	   console.log("clicked:"+clicked);
                	   
                	   for (var i in nodeArray) {
            		     if (nodeArray[i].label == clickedAppName) {
            		    	 nodeArray[i].clicked = 1;
            		        break; //Stop this loop, we found it!
            		     }
            		   }
                	   
                	   var nodeLength = nodeArray.length;
                	   
                	   if(clicked==1){
                		   alert("Already Found dependency for ("+clickedAppName+")");
                	   }else{
                		   $.ajax({ 
	                   	        type: "GET",
	                   	        dataType: "json",
	                   	        url: "/ptrac/dependency/search/findByParentApp?name="+clickedAppName,
	                   	        success: function(data){
	                   	        	console.log(data._embedded.dependency);
	                   	        	depdencyData = data._embedded.dependency;
	                   	        	if(depdencyData && depdencyData.length){
	                   	        		//alert(depdencyData);
	                   	        		
	                   	        		//nodeArray.push({ id: 1, label: depdencyData[0].parentApp});
	                   	                
	                   	                for (var j = 0; j < depdencyData.length; j++){
	
	                   	             	   nodeArray.push({ id: nodeLength+j+1, label: depdencyData[j].childAppName});
	                   	             	   edgeArray.push({ from: clickedAppId, to: nodeLength+j+1 })
	
	                   	                }
	                   	        		
	                   	             createNetwork();
	                   	        	}else {
	                   	        		alert("No Dependency Found for ("+clickedAppName+")");
	                   	        	}
	                   	        }
	                   	    });
                		   
                	   }
                	   
                	   /*params.event = "[original event]";
                       var nodeId = nodeArray.length + 1;
                       var fromId = this.getNodeAt(params.pointer.DOM);
                       var newItem = { id: nodeId, label: 'Node ' + nodeId };
                       nodeArray.push(newItem);
                       var newEdge = { from: fromId, to: nodeId };
                       edgeArray.push(newEdge);
                       document.getElementById('eventSpan').innerHTML = '<h2>doubleClick event:</h2>' + JSON.stringify(params, null, 4);*/
                       //createNetwork();
                   });
                   // network.on("oncontext", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>oncontext (right click) event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragStart", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragStart event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragging", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragging event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("dragEnd", function (params) {
                   //     params.event = "[original event]";
                   //     document.getElementById('eventSpan').innerHTML = '<h2>dragEnd event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("zoom", function (params) {
                   //     document.getElementById('eventSpan').innerHTML = '<h2>zoom event:</h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("showPopup", function (params) {
                   //     document.getElementById('eventSpan').innerHTML = '<h2>showPopup event: </h2>' + JSON.stringify(params, null, 4);
                   // });
                   // network.on("hidePopup", function () {
                   //     console.log('hidePopup Event');
                   // });
                   // network.on("select", function (params) {
                   //     console.log('select Event:', params);
                   // });
                   // network.on("selectNode", function (params) {
                   //     console.log('selectNode Event:', params);
                   // });
                   // network.on("selectEdge", function (params) {
                   //     console.log('selectEdge Event:', params);
                   // });
                   // network.on("deselectNode", function (params) {
                   //     console.log('deselectNode Event:', params);
                   // });
                   // network.on("deselectEdge", function (params) {
                   //     console.log('deselectEdge Event:', params);
                   // });
                   // network.on("hoverNode", function (params) {
                   //     console.log('hoverNode Event:', params);
                   // });
                   // network.on("hoverEdge", function (params) {
                   //     console.log('hoverEdge Event:', params);
                   // });
                   // network.on("blurNode", function (params) {
                   //     console.log('blurNode Event:', params);
                   // });
                   // network.on("blurEdge", function (params) {
                   //     console.log('blurEdge Event:', params);
                   // });
               }
           }
           
        }
    });
	
}