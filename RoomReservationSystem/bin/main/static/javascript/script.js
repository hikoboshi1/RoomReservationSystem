	//利用開始時刻を入力したら、自動で1時間後の値を利用終了時刻に入れる
	const get = function (start){
	console.log(moment("20:00", "HH:mm"))
		if(start != ''){
			let startMoment = moment(start, "HH:mm");
			let endMoment = moment(startMoment).add(1,'h').format('HH:mm');
			let endMomentFormat = endMoment
			return endMoment;
		}
	}

$(function(){
	$('.date').datetimepicker({
    	format:'YYYY-MM-DD',
  	});
  	
  	$('#dateForm').on('change.datetimepicker', function(e){
  		$("#dateForm").attr('action',"");
  		$("#dateForm").submit();
  	});
  	
  	$('.time').datetimepicker({
    	format:'HH:mm',
  	});
  	
  	$('#useStartTime1').on('change.timepicker', function(e){
  		$('#useEndTime1').val(get($('#useStartTime1').val()));		
	});
	$('#time2').on('change.timepicker', function(e){
		$('#useEndTime1').val(get($('time2').val()));
	});
	  	
});
  	var toDate = function(dateStr, timeStr){
		dateStr = dateStr == null ? new Date().toDateString() : dateStr;
		return new Date( dateStr + ' ' + timeStr)
  	}

$("#reservationForm").validate({
    rules: {
        'useDate': {
            required: true,
            date: true
        },
        'useStartTime': {
            required: true,
            time: true,
            future:true
        },
        'useEndTime': {
            required: true,
            time: true,
            startIsBeforeEnd:true
        },
        'roomUsername': {
            required: true,
            maxlength: 15
        },
        'note': {
            maxlength: 140
        }
    }, 
    messages: {
        'useDate': {
            required: $("#useDateIsRequired").text(),
            date: $("#useDateIsDate").text()
        },
        'useStartTime': {
            required: $("#useStartTimeIsRequired").text(),
            time: $("#useStartTimeIsTime").text(),
            future: $("#startDateTimeIsFuture").text()
        },
        'useEndTime': {
            required: $("#useEndTimeIsRequired").text(),
            time: $("#useEndTimeIsTime").text(),
            startIsBeforeEnd: $("#startTimeIsBeforeEnd").text()
        },
        'roomUsername': {
             required: $("#roomUserNameIsRequired").text(),
            maxlength: $("#roomUsernameMaxLength").text(),
        },
        'note': {
            maxlength: $("#noteMaxLength").text(),
        }
    },
    errorPlacement: function (error, element) {
        error.appendTo($('#'+ element.attr('name') + '_err'));
    },
    errorClass: "is-invalid"
});
$.validator.addMethod("future", function(){
    var dateStr = $('#_useDate').val();
    var timeStr = $('#_useStartTime').val();
    var now = new Date();
    if ( toDate(dateStr, timeStr).getTime() >= now.getTime() ){
        return true;
    } else {
        return false;
    }
});

$.validator.addMethod("startIsBeforeEnd", function(){
    var startTimeStr = $('#_useStartTime').val();
    var endTimeStr = $('#_useEndTime').val();
    if ( toDate(null, startTimeStr).getTime() < toDate(null, endTimeStr).getTime() ){
        return true;
    } else {
        return false;
    }
});

$.validator.addMethod( "time", function( value, element ) {
    return this.optional( element ) || /^([01]\d|2[0-3]|[0-9])(:[0-5]\d){1,2}$/.test( value );
} );
