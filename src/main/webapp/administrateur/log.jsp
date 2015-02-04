<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuAdministrateurActive" />
	<jsp:param value="Solices - Liste Administrateur" name="titreOnglet" />
</jsp:include>


<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- start page content -->
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<section class="panel">
					<header class="panel-heading no-b">
						<h1 class="h3 text-primary mt0">Liste des actions utilisateurs</h1>

						<p class="text-muted">Présentation de toutes les actions utilisateurs (Création, modification, suppression)</p>

					</header>
					<div class="panel-body">

						<table class="table table-striped list no-m">
							<thead>
								<tr>
									<th>Logs</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logs}" var="log">

									<tr>
										<td><c:out value="${log}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</section>
			</div>
			<!-- /inner content wrapper -->

		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />

