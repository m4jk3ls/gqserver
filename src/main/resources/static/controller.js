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
	});
});