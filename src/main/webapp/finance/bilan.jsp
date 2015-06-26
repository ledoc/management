<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuFinanceActive" />
	<jsp:param value="Solices - Liste Finance" name="titreOnglet" />
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
						<h1 class="h3 text-primary mt0">Bilans</h1>
					</header>
					<div class="panel-body">

						<c:if test="${empty bilans }">

							<H2>Ooops !&nbsp;Liste vide.</H2>
						</c:if>
						<c:if test="${not empty bilans }">

							<table class="table table-striped list no-m">
								<thead>
									<tr>
										<th>Somme</th>
										<th>Catégorie</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${bilans}" var="finance">
										<tr>
											<td><c:out value="${finance.somme}" /></td>
											<td><c:out value="${finance.categorie}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</section>
			</div>
		</div>
		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<!-- end page content -->
		<jsp:include page="/template/footer.jsp" />