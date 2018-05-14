function displayPie(){
	
	if($("#reportType").val()==='report2'){
	$("#pieDiv").show();
	//pie
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "prdMaster",
        success: function(data){ 
        	var data1 = data._embedded.prdMaster;
        	var approved=0, recommended=0, blank=0;
        	//console.log(data1);
        	
        	for (var i = 0; i < data1.length; i++){
        		if(data1[i].status==='Recommended'){
        			recommended++;
        		} else if(data1[i].status==='Approved'){
        			approved++;
        		} else{
        			blank++;
        		}
        	}
        	//console.log(approved+'-'+recommended+'-'+blank);
        	 var pdata = [
     	      	{
     	      		value: blank,
     	      		color:"#F7464A",
     	      		highlight: "#FF5A5E",
     	      		label: "Not Defined"
     	      	},
     	      	{
     	      		value: approved,
     	      		color: "#46BFBD",
     	      		highlight: "#5AD3D1",
     	      		label: "Approved"
     	      	},
     	      	{
     	      		value: recommended,
     	      		color: "#FDB45C",
     	      		highlight: "#FFC870",
     	      		label: "Recommended"
     	      	}
     	      ];
     	 var ctxp = $("#pieChartDemo").get(0).getContext("2d");
          var barChart = new Chart(ctxp).Pie(pdata);
        	
        }
	});
	
	/** Table data **/
	var status=  $("#selectByStatus #byStatus").val();
	var filterval;
	if(status === 'report1'){
		filterval = 'Approved';
	}else if(status === 'report2'){
		filterval = 'Recommended';
	}else if(status === 'report3'){
		filterval = '';
	}else if(status === 'report4'){
		filterval = '';
	}
	
	//console.log("status--"+filterval);
	
	showWaitScreen();
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "prdMaster",
        success: function(data){     
           $('#appSoftwareTable').DataTable( {
        	   destroy:true,
        	   data: data._embedded.prdMaster,
               columns: [
            	   { data: "roadmap",title: "Roadmap"
            	   },
                   { data: "mgmtGrp", title: "Management Group"},
                   { data: "manufacturer", title: "Manufacturer"
                   },
                   { data: "prodDispName", title: "Product Display Name"
                   },
                   {
                	   data: "status", title: "Status"
                   }
               ]
           } );
           
           hideWaitScreen();
           var table1 = $('#appSoftwareTable').DataTable();
           table1.search(filterval).draw();
        }
    });
	}
	    
}