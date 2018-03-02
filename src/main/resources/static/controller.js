$(document).ready(function () {
	$.ajax({
		url: "http://localhost:8080/geoquiz/question"
	}).then(function (restData) {
		$('#questionContent').append(restData.fullQuestion);
		$('#goodAnswers').append(restData.goodAnswers);
		$('#badAnswers').append(restData.badAnswers);

		google.charts.load('current', {
			'packages': ['geochart'],
			'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
		});
		google.charts.setOnLoadCallback(function () {
			drawRegionsMap(restData);
		});

		function drawRegionsMap(restData) {
			var data = google.visualization.arrayToDataTable([
				['Country'],
				[restData.countryName]
			]);
			var options = {region: restData.countryAlpha2Code};
			var chart = new google.visualization.GeoChart(document.getElementById('map'));
			chart.draw(data, options);
		}

		$('input[id=answerA]').attr('value', restData.possibleAnswers[0]);
		$('input[id=answerB]').attr('value', restData.possibleAnswers[1]);
		$('input[id=answerC]').attr('value', restData.possibleAnswers[2]);
		$('input[id=answerD]').attr('value', restData.possibleAnswers[3]);
		$('label[for=answerA]').text(restData.possibleAnswers[0]);
		$('label[for=answerB]').text(restData.possibleAnswers[1]);
		$('label[for=answerC]').text(restData.possibleAnswers[2]);
		$('label[for=answerD]').text(restData.possibleAnswers[3]);

		Cookies.set('token', restData.token);
	});

	$("input[name='answer']").change(function () {
		if ($("input[name='answer']").is(':checked')) {
			$('button[id=submitBtn]').removeAttr("disabled");
		}
	});

	$("#submitBtn").click(function () {
		var radioValues = '[';
		$("input[name='answer']").each(function () {
			radioValues += '"' + $(this).val() + '"' + ", ";
		});
		radioValues = radioValues.substring(0, radioValues.length - 2);
		radioValues += ']';
		var jsonFile = '{"token": "' + Cookies.get('token') + '", "userAnswer": "' +
			$("input[name='answer']:checked").val() + '", "possibleAnswers": ' + radioValues + '}';
		Cookies.remove('token');

		$.ajax({
			url: "http://localhost:8080/geoquiz/question/answer",
			dataType: "json",
			type: "post",
			contentType: "application/json",
			data: jsonFile,
			processData: false,
			success: function (responseData) {
				$('button[id=submitBtn]').attr('disabled', true);
				if (responseData.correct.toString() === "false") {
					$('div[id=correctAnswer]').text("Bad answer :( correct answer is " + responseData.correctAnswer);
				} else {
					$('div[id=correctAnswer]').text("Good answer! Excellent!");
				}
				setTimeout(function () {
					location.reload();
				}, 3000)
			}
		});
	});
});