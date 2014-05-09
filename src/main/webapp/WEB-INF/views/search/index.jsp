<html>
	<head>
		<title>Search</title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
		
		<style type="text/css">
			#criteria span {
				color: red;
				font-weight: bold;
			}
		</style>
		
	</head>
	<body>
		
		<form class="navbar-form" role="search">
			<input type="text" class="form-control" placeholder="Search"
				id="term" id="srch-term">
			<button class="btn btn-default" type="submit">
				<i class="glyphicon glyphicon-search"></i>
			</button>
		</form>


		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-8" id="criteria"></div>
		</div>
		<div class="row">
			<div class="col-md-4" id="facetDiv"></div>
			<div class="col-md-8" id="resultsDiv"></div>
		</div>
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    	
		<!-- Latest compiled and minified JavaScript -->
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		
		<script type="text/javascript">
			var searchData = {};
			$(function() {
				var path = location.pathname.split("/");
				if(path && path[path.length - 1] !== 'search' && path[path.length - 1] !== '') {
					var term = decodeURI(path[path.length - 1]);
					$('#term').val(term);
					
					searchData.searchTerm = term;
					search();
				}
				
				$(document).on('click', '#facet_JOBPOSTYEAR > li', 'click', function(e) {
					searchData.year = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_EMPLOYER> li', 'click', function(e) {
					searchData.employer = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_JOBPOSTMONTH > li', 'click', function(e) {
					searchData.month = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_STATE > li', 'click', function(e) {
					searchData.state = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_CITY > li', 'click', function(e) {
					searchData.city = $(this).data('val');
					search();
				});
				$(document).on('click', '#criteria > span', 'click', function(e) {
					delete searchData[$(this).data('val')];
					search();
				});
				
				$('form').on('submit', function(e) {
					e.preventDefault();
					
					var term = $('#term').val();
					history.pushState(null, null, '/search/' + term)
					searchData.searchTerm = term;
					search();
				});
			});
			
			var url = 'http://localhost:9090/api/search/workinformation';
			var search = function() {
				var criteriaStr = '';
				for(var k in searchData) {
					criteriaStr += k.toUpperCase() + ': ' + searchData[k].toString().toUpperCase() + ' <span class="criteria" data-val="'+k+'">[x]</span> ';
				}
				$('#criteria').html(criteriaStr);
				
				var request = $.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data: JSON.stringify(searchData),
					contentType: 'application/json',
					headers: {
						'Accept' : 'application/json'
					}
				});
				
				request.done(displayData);
			}; 
			
			var displayData = function(data) {
				
				// process facets
				var facetsDiv = $('#facetDiv'); 
				facetsDiv.empty();
				
				var facets = data.facets;
				
				// display facet
				var facetStr = '';
				$.each(facets, function(fi, f) {
					facetStr += '<h4>' + f.name + '</h4>';
					
					facetStr += '<ul id="facet_' + f.name + '">';
					f.list.sort(function(o1, o2) {
						return o1.name > o2.name ? 1 : -1
					});	
					$.each(f.list, function(di, d) {
						facetStr += '<li data-val="'+ d.name +'">' + d.name + " (" + d.count + ")</li>";
					});
					facetStr += '</ul>';
				});
				facetsDiv.html(facetStr);
				
				// process results
				var resultsDiv = $('#resultsDiv'); 
				resultsDiv.empty();
				
				var results = data.results
				var resultStr = '';
				$.each(results, function(fi, f) {
					resultStr += '<div class="panel panel-default">';
					resultStr += '<div class="panel-heading">';
					resultStr += '<strong>' + f.employer + '</strong><br />';
					resultStr += f.jobTitle + ' ' + (f.jobLevel != null ? f.jobLevel : '');
					resultStr += '</div>';
					resultStr += '<div class="panel-body">';
					resultStr += 'Location: ' + f.city + ', ' + f.state;
					resultStr += '<br />Salary : $' + f.hourlySalary + '/hr';
					resultStr += '</div>';
					resultStr += '</div>';
					
				});
				resultsDiv.html(resultStr);
				
				
			};
		</script>
	</body>
</html>