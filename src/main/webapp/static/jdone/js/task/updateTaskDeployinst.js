$(function() {
	$("#ff").validate();
});

function submitForm() {

	$('#ff').form('submit', {
		url : "updateTaskDeployinstData",
		onSubmit : function() {
			var isValid = !$("#ff").valid()
			if (isValid) {
				return false; // hide progress bar while the form is invalid
			}
			return true; // return false will stop the form submission
		},
		success : function(data) {
			var data = eval('(' + data + ')'); // change the JSON string to
												// javascript object
			if (data.success) {
				$.messager.alert('提示', data.msg, 'info', function() {
					window.location.reload();
				});
				// $('#ff').form('clear');
			} else {
				$.messager.alert('提示', data.msg, 'error');
			}

		}
	});

}
