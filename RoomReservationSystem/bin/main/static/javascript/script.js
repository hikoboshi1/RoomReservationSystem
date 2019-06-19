$(function(){
	$('#date').datetimepicker({
		format: 'YYYY-MM-DD',
    });
	
	$('#date').on('change.datetimepicker', function(e){
		$("#date_form").submit();
	})
});