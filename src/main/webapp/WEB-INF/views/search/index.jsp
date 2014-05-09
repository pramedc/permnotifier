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
			
			.panel {
				min-height: 160px;
			}			
		</style>
		
	</head>
	<body>
		
		<div class="container">
			<div class="row">
				<form class="navbar-form" role="search">
					<div class="col-md-3">
						<input type="text" class="form-control" placeholder="Search"
							id="term" id="srch-term">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
						<div class="clear"></div>
						<span id="timeExecution" class="small text-muted"></span>
					</div>
				<div class="col-md-9" id="criteria"></div>
				</form>
			</div>
	
	
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
				<ul id="pagination" class="pager" style="display: none;">
				  <li class="previous"><a href="javascript:void(0);">&larr; Previous</a></li>
				  <li class="next"><a href="javascript:void(0);">Next &rarr;</a></li>
				</ul>
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<div class="loader row text-center" style="display:none;"><img src="/images/ajax-loader.gif" /></div>
					<div class="data-container" id="facetDiv"></div>
				</div>
				<div class="col-md-9">
					<div class="loader row text-center" style="display:none;"><img src="/images/ajax-loader.gif" /></div>
					<div class="data-container" id="resultsDiv"></div>
				</div>
			</div>
		</div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    	
		<!-- Latest compiled and minified JavaScript -->
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		
		<script type="text/javascript">
			var searchData = JSON.parse(localStorage.getItem('searchData')) || {};
			var pageableData = {
				size: 100,
				page: 0,
				sort: 'hourlySalary_c,desc',
			};
			$(function() {
				var path = location.pathname.split("/");
				if(path && path[path.length - 1] !== 'search' && path[path.length - 1] !== '') {
					var term = decodeURI(path[path.length - 1]);
					$('#term').val(term);
					
					searchData.searchTerm = term;
					search();
				}
				
				$(document).on('click', '#facet_JOBPOSTYEAR > li', 'click', function(e) {
					pageableData.page = 0;
					searchData.year = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_EMPLOYER> li', 'click', function(e) {
					pageableData.page = 0;
					searchData.employer = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_JOBPOSTMONTH > li', 'click', function(e) {
					pageableData.page = 0;
					searchData.month = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_JOBTITLE > li', 'click', function(e) {
					pageableData.page = 0;
					searchData.jobTitle = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_STATE > li', 'click', function(e) {
					pageableData.page = 0;
					searchData.state = $(this).data('val');
					search();
				});
				$(document).on('click', '#facet_CITY > li', 'click', function(e) {
					pageableData.page = 0;
					searchData.city = $(this).data('val');
					search();
				});
				$(document).on('click', '#criteria > span', 'click', function(e) {
					var attr = $(this).data('val');
					delete searchData[attr];
					if(attr == 'searchTerm') {
						$('#term').val('');
						history.pushState(null, null, '/search')
					}
					search();
				});
				
				$('form').on('submit', function(e) {
					e.preventDefault();
					
					var term = $('#term').val();
					history.pushState(null, null, '/search/' + term)
					
					// remove all properties of search data if user did a new search
					for(var i in searchData) {
						delete searchData[i];
					}
					if(term != '') {
						searchData.searchTerm = term;
					}
					search();
				});
			});
			
			var clickPrevious = function() {
					pageableData.page--;
					search();
			}
			var clickNext = function() {
					pageableData.page++;
					search();
			}
			
			var url = 'http://localhost:9090/api/search/workinformation';
			var search = function() {
				
				// empty old data
				$('.data-container').empty();
				
				// show loader
				$('.loader').show();
				
				var request = $.ajax({
					url: url + "?" + $.param(pageableData),
					type: 'post',
					dataType: 'json',
					data: JSON.stringify(searchData),
					contentType: 'application/json',
					headers: {
						'Accept' : 'application/json'
					}
				});
				
				request.done(displayData);
				request.always(hideLoader);
			}; 
			var hideLoader = function() {
				$('.loader').hide();
			}
			var displayData = function(data) {
				// process pagination
				if(data.totalCount > pageableData.size) {
					var paginationDiv = $("#pagination");
					paginationDiv.show();
					
					// check if previous should be available
					if(data.start <= 0) {
						paginationDiv.find('.previous').addClass('disabled');
						paginationDiv.find('.previous a').unbind('click');
					}
					else {
						paginationDiv.find('.previous').removeClass('disabled');
						paginationDiv.find('.previous a').unbind('click').bind('click', clickPrevious);
					}
					
					// check if next should be available
					var totalPage = Math.ceil(data.totalCount / pageableData.size);
					if(pageableData.page >= totalPage) {
						paginationDiv.find('.next').addClass('disabled');
						paginationDiv.find('.next a').unbind('click');
					}
					else {
						paginationDiv.find('.next').removeClass('disabled');
						paginationDiv.find('.next a').unbind('click').bind('click', clickNext);
					}
				}
				
				// process facets
				var facetsDiv = $('#facetDiv'); 
				var facets = data.facets;
				
				// display facet
				var facetStr = '';
				$.each(facets, function(fi, f) {
					facetStr += '<h4>' + f.name + '</h4>';
					
					facetStr += '<ul class="nav nav-pills nav-stacked" id="facet_' + f.name + '">';
					f.list.sort(function(o1, o2) {
						return o1.name > o2.name ? 1 : -1
					});	
					$.each(f.list, function(di, d) {
						facetStr += '<li data-val="'+ d.name +'"><a href="javascript:void(0);"><span class="badge pull-right">'+ d.count +'</span>' + d.name + '</a></li>';
					});
					facetStr += '</ul>';
				});
				facetsDiv.html(facetStr);
				
				// process results
				var resultsDiv = $('#resultsDiv'); 
				var results = data.results
				var resultStr = '';
				$.each(results, function(fi, f) {
					resultStr += '<div class="col-md-6 panel panel-default">';
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
				
				// process criteria
				var maxSizeForPage = ((pageableData.page + 1) * pageableData.size);
				var criteriaStr = 'Showing ' + (maxSizeForPage > data.totalCount ? data.totalCount : maxSizeForPage) + " of " + data.totalCount + " - ";
				for(var k in searchData) {
					criteriaStr += k.toUpperCase() + ': <strong>' + searchData[k].toString().toUpperCase() + '</strong> <span class="criteria" data-val="'+k+'">[x]</span> ';
				}
				$('#criteria').html(criteriaStr);
				
				// process other things
				$("#timeExecution").text('**query executed in '+ data.ms +' ms');
				
				// save searchData
				localStorage.setItem('searchData', JSON.stringify(searchData))
			};
		</script>
	</body>
</html>