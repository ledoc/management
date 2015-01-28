<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="urlResources" value="/resources" />

</section>
</section>
<footer class="bg-white clearfix mt15 pt15 pb15">
	<div class="col-lg-6">
		<p>© AH2D 2015</p>
	</div>

	<div class="col-lg-6 text-right">
		<a class="btn btn-social btn-xs btn-facebook mr5"><i
			class="fa fa-facebook"></i>Facebook </a> <a
			class="btn btn-social btn-xs btn-twitter mr5"><i
			class="fa fa-twitter"></i>Twitter </a>
	</div>
</footer>
</div>

<!-- core scripts -->
<script src="${urlResources}/plugins/jquery-1.11.1.min.js"></script>
<script src="${urlResources}/bootstrap/js/bootstrap.js"></script>
<script src="${urlResources}/plugins/jquery.slimscroll.min.js"></script>
<script src="${urlResources}/plugins/jquery.easing.min.js"></script>
<script src="${urlResources}/plugins/appear/jquery.appear.js"></script>
<script src="${urlResources}/plugins/jquery.placeholder.js"></script>
<script src="${urlResources}/plugins/fastclick.js"></script>
<script src="${urlResources}/plugins/parsley.min.js"></script>

<!-- /core scripts -->

<!-- page level scripts -->
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<!-- <script src="${urlResources}/plugins/gmaps.js"></script>  -->
<script src="${urlResources}/plugins/chosen/chosen.jquery.min.js"></script>
<script src="${urlResources}/plugins/datatables/jquery.dataTables.js"></script>
<script src="${urlResources}/plugins/icheck/icheck.js"></script>
<script src="${urlResources}/plugins/hightcharts/js/highcharts.js"></script>
<!-- /page level scripts -->

<!-- template scripts -->
<script src="${urlResources}/js/offscreen.js"></script>
<script src="${urlResources}/js/main.js"></script>
<!-- /template scripts -->

<!-- page script -->
<%-- <script src="${urlResources}/js/maps.js"></script> --%>
<script src="${urlResources}/js/bootstrap-datatables.js"></script>
<script src="${urlResources}/js/datatables.js"></script>
<script src="${urlResources}/js/mesures.js"></script>
<!-- /page script -->
<script type="text/javascript">
	$(function() {
		$('#form').parsley();
	});
</script>
</body>

</html>