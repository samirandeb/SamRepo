var regionP, regionVal, regionI = 0, countryP, countryI, causeP, causeI, region, country, cause, scorePoints = [];
var dataLoaded = false;
function loadChartData(){
	
	$.ajax({
		type : 'GET',
		contentType : "application/json",
		url : "appGroupScore",
		// 			data : JSON.stringify(selectedItems),
		dataType : "text",
		success : function(data) {
			
			processMapData(JSON.parse(data));

		},
		error : function(e) {
			console.log("ERROR", e);
		},
		done : function(d) {
			console.log("DONE", d);
		}
	});
	
}

function clearChartData(){
	$('#chartView').empty();
	
}

function getColorCode(dataValue){
	if(dataValue>70){
		return "#e56d67";
	}
	if(dataValue<30){
		return "#8cd877";
	}
	if(dataValue>=30){
		return "#efec8f";
	}
	if(dataValue<0){
		return "#ff9900";
	}
	
}

function processMapData(dataArray) {
//	console.log("dataArray: ", dataArray);
	scorePoints = [];
	for (var i = 0; i < dataArray.length; i++) {
		regionVal = 0;
//		console.log("App group : " + dataArray[i].applicationGroup);
//		console.log("items : " + dataArray[i].scoreCards);
		regionP = {
			id : 'id_' + regionI,
			name : dataArray[i].applicationGroup
			
		};
		countryI = 0;
		for (var j = 0; j < dataArray[i].scoreCards.length; j++) {
			countryP = {
				id : regionP.id + '_' + countryI,
				name : dataArray[i].scoreCards[j].applicationName,
				parent : regionP.id,
				value : dataArray[i].scoreCards[j].applicationScore,
				color : getColorCode(dataArray[i].scoreCards[j].applicationScore)
			};
			scorePoints.push(countryP);
			
			causeI = 0;
			//ITEM : 1
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'plScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].plScore,
				color : getColorCode(dataArray[i].scoreCards[j].plScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;

			//ITEM:2
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'appServerScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].appServerScore,
				color : getColorCode(dataArray[i].scoreCards[j].appServerScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;

			
			//ITEM:3
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'dbScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].dbScore,
				color : getColorCode(dataArray[i].scoreCards[j].dbScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;
			
			//ITEM:4
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'applicationFrameworkScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].applicationFrameworkScore,
				color : getColorCode(dataArray[i].scoreCards[j].applicationFrameworkScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;
			
			//ITEM:5
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'presentationLayerScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].presentationLayerScore,
				color : getColorCode(dataArray[i].scoreCards[j].presentationLayerScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;
			
			//ITEM:6
			causeP = {
				id : countryP.id + '_' + causeI,
				name : 'dataAccessScore',
				parent : countryP.id,
				value : dataArray[i].scoreCards[j].dataAccessScore,
				color : getColorCode(dataArray[i].scoreCards[j].dataAccessScore)
			};
			scorePoints.push(causeP);
			causeI = causeI + 1;
			
			
			countryI = countryI + 1;
		}
		regionP.value = dataArray[i].scoreCards.length;
		regionP.color = Highcharts.getOptions().colors[regionP.value]
		scorePoints.push(regionP);
		regionI = regionI + 1;
	}

	Highcharts.chart('chartView', {
		series : [ {
			type : 'treemap',
			layoutAlgorithm : 'squarified',
			allowDrillToNode : true,
			animationLimit : 1000,
			dataLabels : {
				enabled : false
			},
			levelIsConstant : false,
			levels : [ {
				level : 1,
				dataLabels : {
					enabled : true
				},
				borderWidth : 3
			} ],
			data : scorePoints
		} ],
		subtitle : {
			text : 'Click points to drill down'
		},
		title : {
			text : 'Technology Heatmap'
		}
	});
//	console.log(JSON.stringify(dataArray));
}
