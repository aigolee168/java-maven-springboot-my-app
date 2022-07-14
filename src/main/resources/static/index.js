window.onload = function () {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://192.168.2.35:8180/data-center/api/projectKpi');
	xhr.setRequestHeader('access-token', '10afb9b69b354e55a53e2fceb063b0c0');
	xhr.onreadystatechange = () => {
		if(xhr.readyState === XMLHttpRequest.DONE) {
			var status = xhr.status;
		    if (status === 0 || (status >= 200 && status < 400)) {
		    	console.log(xhr.response);
		    } else {
		    	console.log('request failed');
		    }
		}
	}
	xhr.send();
}