var FlapBuffer = function(wrap, num_lines) {
    this.wrap = wrap;
    this.num_lines = num_lines;
    this.line_buffer = '';
    this.buffers = [[]];
    this.cursor = 0;
};

FlapBuffer.prototype = {

    pushLine: function(line) {

        if (this.buffers[this.cursor].length < this.num_lines) {
           this.buffers[this.cursor].push(line);
        } else {
            this.buffers.push([]);
            this.cursor++;
            this.pushLine(line);
        } 
    },

    pushWord: function(word) {
        if (this.line_buffer.length == 0) {
            this.line_buffer = word;
        } else if ((word.length + this.line_buffer.length + 1) <= this.wrap) {
            this.line_buffer += ' ' + word;
        } else {
            this.pushLine(this.line_buffer);
            this.line_buffer = word;
        }
    },

    flush: function() {
        if (this.line_buffer.length) {
            this.pushLine(this.line_buffer);
            this.line_buffer = '';
        }
    },

};

/*var FlapDemo = function(display_selector, input_selector, click_selector) {
    var _this = this;

    var onAnimStart = function(e) {
        var $display = $(e.target);
        $display.prevUntil('.flapper', '.activity').addClass('active');
    };

    var onAnimEnd = function(e) {
        var $display = $(e.target);
        $display.prevUntil('.flapper', '.activity').removeClass('active');
    };

    this.opts = {
        chars_preset: 'alphanum',
        align: 'left',
        width: 42,
        on_anim_start: onAnimStart,
        on_anim_end: onAnimEnd
    };

    this.timers = [];

    this.$displays = $(display_selector);
    this.num_lines = this.$displays.length;

    this.line_delay = 300;
    this.screen_delay = 7000;

    this.$displays.flapper(this.opts);

    this.$typesomething = $(input_selector);

    $(click_selector).click(function(e){
        var text = _this.cleanInput(_this.$typesomething.val());
        _this.$typesomething.val('');

        if (text.match(/what is the point/i) || text.match(/what's the point/i)) {
            text = "WHAT'S THE POINT OF YOU?";
        }

        var buffers = _this.parseInput(text);

        _this.stopDisplay();
        _this.updateDisplay(buffers);

        e.preventDefault();
    });
};
*/

var FlapDemo = function(display_message) {
    var _this = this;

    var onAnimStart = function(e) {
        var $display = $(e.target);
        $display.prevUntil('.flapper', '.activity').addClass('active');
    };

    var onAnimEnd = function(e) {
        var $display = $(e.target);
        $display.prevUntil('.flapper', '.activity').removeClass('active');
    };

    this.opts = {
        chars_preset: 'alphanum',
        align: 'left',
        width: 42,
        on_anim_start: onAnimStart,
        on_anim_end: onAnimEnd
    };

    this.timers = [];

    this.$displays = $('input.display');
    this.num_lines = this.$displays.length;

    this.line_delay = 200;
    this.screen_delay = 3000;

    this.$displays.flapper(this.opts);
	
	var text = _this.cleanInput(display_message);
	var buffers = _this.parseInput(text);

	_this.stopDisplay();
	_this.updateDisplay(buffers);
	//e.preventDefault();
};

FlapDemo.prototype = {

    cleanInput: function(text) {
        return text.trim().toUpperCase();
    },

    parseInput: function(text) {
        var buffer = new FlapBuffer(this.opts.width, this.num_lines);
        var lines = text.split(/\n/);

        for (i in lines) {
            var words = lines[i].split(/\s/);
            for (j in words) {
                buffer.pushWord(words[j]);
            }
            buffer.flush();
        }

        buffer.flush();
        return buffer.buffers;
    },

    stopDisplay: function() {
        for (i in this.timers) {
            clearTimeout(this.timers[i]);
        }

        this.timers = [];
    },

    updateDisplay: function(buffers) {
        var _this = this;
        var timeout = 100;

        for (i in buffers) {

            _this.$displays.each(function(j) {

                var $display = $(_this.$displays[j]);

                (function(i,j) {
                    _this.timers.push(setTimeout(function(){
                        if (buffers[i][j]) {
                            $display.val(buffers[i][j]).change();
                        } else {
                            $display.val('').change();
                        }
                    }, timeout));
                } (i, j));

                timeout += _this.line_delay;
            });

            timeout += _this.screen_delay;
        }
    }

};

var stockData=[];

function arrayToString(data){
	return data.map( el => el.user+' >'+el.stock.name+' $'+el.stock.price+' @ '+(new Date(Date.parse(el.when))).toLocaleTimeString());
}

function sortMessage(stocks){
	/*console.log("user",stocks.user);
	console.log("stock-name",stocks.stock.name);
	console.log("stock price",stocks.stock.price);*/
	
	var index = stockData.indexOf(stocks);
	
	if(index === -1){
		var obj = stockData.find(function (data) { return ((data.user == stocks.user) && (data.stock.name == stocks.stock.name)); });
		var dataIndex = stockData.indexOf(obj);
		if(dataIndex === -1){
			//console.log("If->",stocks);
			stockData.push(stocks);
		}else {
			//console.log("Else->",stocks);
			stockData[dataIndex].stock.price = stocks.stock.price;
			stockData[dataIndex].when = stocks.when;
		}
	}
	else {
		//console.log("UPDATED - 2");
		stockData[index].stock.price = stocks.stock.price;
		stockData[index].when = stocks.when;
	}
	
	//sorting by user output array
	stockData.sort(function(a, b){
	    var nameA=a.user.toLowerCase(), nameB=b.user.toLowerCase()
	    if (nameA < nameB) //sort string ascending
	        return -1 
	    if (nameA > nameB)
	        return 1
	    return 0 //default return value (no sorting)
	});
	
	//sorting by user output array
	/*stockData.sort(function(a, b){
	    var nameA=a.stock.name.toLowerCase(), nameB=b.stock.name.toLowerCase()
	    if (nameA < nameB) //sort string ascending
	        return -1 
	    if (nameA > nameB)
	        return 1
	    return 0 //default return value (no sorting)
	});*/
	
	return arrayToString(stockData).join("\n");
}

$(document).ready(function(){

var flapperObj = new FlapDemo('Welcome to Sam Spring Reactive - SSE Demo.');

function getEvents(){
	if(typeof(EventSource) !== "undefined") {
	    var source = new EventSource("/stocks");
	    source.onmessage = function(event) {
	    	
	    	var stocks = JSON.parse(event.data);	    	
	    	//console.log("stocks:",stocks);
	    	
	    	console.log("Message:",sortMessage(stocks));
	    	
	    	flapperObj.stopDisplay();
	    	flapperObj.updateDisplay(flapperObj.parseInput(flapperObj.cleanInput(sortMessage(stocks))));
	    };
	} else {
	    document.getElementById("sseDiv").innerHTML = "Your browser does not support server-sent events.";
	}
}

getEvents();

});

