<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/template/header.jsp">
	<jsp:param value="active" name="menuUtilisateurActive" />
	<jsp:param value="Solices - Détail Ouvrage" name="titreOnglet" />
</jsp:include>

<section class="layout">
	<!-- /sidebar -->
	<section class="main-content bg-white rounded shadow">
		<!-- content wrapper -->
		<div class="content-wrap clearfix pt15">
			<div class="col-lg-12 col-md-12 col-xs-12">
				<div class="panel">
					<header class="panel-heading no-b col-lg-offset-2">
						<h1 class="h3 text-primary mt0">Création d'un Ouvrage</h1>
						<p class="text-muted">Permet de créer ou mettre à jour un
							ouvrage.</p>
					</header>
					<div class="panel-body">
						<c:url var="createOuvrage" value="/ouvrage/create" />
						<form:form id="form" method="POST" action="${createOuvrage}"
							modelAttribute="ouvrage" role="form" class="parsley-form"
							data-validate="parsley" data-show-errors="true">

							<form:hidden path="id" />

							<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
								<div class="form-group">
									<label for="typeOuvrage">Type de l'ouvrage</label>
									<form:select id="typesOuvrageCombo" name="typesOuvrageCombo"
										path="typeOuvrage" items="${typesOuvrageCombo}"
										data-placeholder="Sélectionnez 
							un type"
										itemLabel="description" data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										onchange="javascript:activateNappeOrSurface();"
										class="form-control chosen">
									</form:select>
								</div>
								<div class="form-group">
									<label for="nom">Nom</label>
									<form:input type="text" class="activeInput form-control"
										id="nom" path="nom" placeholder=""
										data-parsley-trigger="change" data-parsley-required="true"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="codeOuvrage">Code de l'ouvrage</label>
									<form:input type="text" class="activeInput form-control"
										id="codeOuvrage" path="codeOuvrage" placeholder=""
										data-parsley-required="true" data-parsley-trigger="change"
										data-parsley-required-message="Champ requis"
										data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="site">Site </label>
									<form:hidden path="site.id" />
									<form:input readonly="true" type="text"
										class="activeInput form-control" id="site" path="site.code" />
								</div>
								<div class="form-group">
									<label for="asservissement">Asservissement</label>
									<div class="form-group">
										<form:select id="asservissement" path="asservissement"
											class="form-control chosen"
											onchange="javascript:activateOuvrageMaitre();">
											<form:option value="true" label="Oui" />
											<form:option value="false" label="Non" />
										</form:select>
									</div>

								</div>
								<div style="display: none;" id="ouvrageMaitre"
									class="form-group">
									<label for="ouvrageMaitre">Ouvrage maître</label>
									<form:input type="text" class="activeInput form-control"
										id="ouvrageMaitre" path="ouvrageMaitre" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="croquisDynamique">Croquis dynamique</label>
									<form:input type="text" class="activeInput form-control"
										id="croquisDynamique" path="croquisDynamique" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="form-group">
									<label for="commentaire">Commentaire</label>
									<form:textarea type="text" class="activeInput form-control"
										id="commentaire" path="commentaire" placeholder=""
										data-parsley-trigger="change" data-parsley-mincheck="2"
										data-parsley-mincheck-message="2 caractères minimum" />
								</div>
								<div class="pull-right">
									<a href="<c:url  value="/ouvrage/list" />"
										class="btn btn-default btn-outline">Retour</a>
									<button type="submit" class="btn btn-outline btn-primary">Créer</button>
								</div>
							</div>
							<div class="col-md-4">
								<div class="panel panel-default no-b">
									<div id="coteSol" class="form-group">
										<label for="coteSol">côte Sol Berge (eau de surface)</label>
										<form:input class="activeInput form-control" id="coteSol"
											path="coteSol" placeholder="" data-parsley-trigger="change"
											step="any" data-parsley-type="number"
											data-parsley-type-message="valeur numérique"
											data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<div id="numeroBSS" class="form-group">
										<label for="numeroBSS">numéro BSS (nappe souterraine)</label>
										<form:input type="text" class="activeInput form-control"
											path="numeroBSS" placeholder="" data-parsley-trigger="change"
											data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<div id="mesureProfondeur" class="form-group">
										<label for="mesureProfondeur">Mesure repère Profondeur
											/NGF (nappe souterraine)</label>
										<form:input type="text" class="activeInput form-control"
											id="mesureProfondeur" path="mesureProfondeur" placeholder=""
											data-parsley-required="true" data-parsley-trigger="change"
											data-parsley-required-message="Champ requis"
											data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<div id="mesureRepereNGFSol" class="form-group">
										<label for="mesureRepereNGFSol">Mesure repère NGF /
											Sol (nappe souterraine)</label>
										<form:input type="text" class="activeInput form-control"
											path="mesureRepereNGFSol" placeholder=""
											data-parsley-trigger="change" data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<div class="form-group">
										<label for="coteSolNGF">côte Sol / NGF</label>
										<form:input type="text" class="activeInput form-control"
											id="coteSolNGF" path="coteSolNGF" placeholder=""
											data-parsley-trigger="change" data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<div class="form-group">
										<label for="coteRepereNGF">Côte repère NGF</label>
										<form:input type="text" class="activeInput form-control"
											id="coteRepereNGF" path="coteRepereNGF" placeholder=""
											data-parsley-trigger="change" data-parsley-mincheck="2"
											data-parsley-mincheck-message="2 caractères minimum" />
									</div>
									<c:set var="enregistreurs" value="${ouvrage.enregistreurs}" />
									<c:if test="${empty enregistreurs }">
										<div class="form-group">
											<label for="enregistreurs">Attacher un ou des
												enregistreurs</label>
											<form:select multiple="true" id="enregistreursCombo"
												name="enregistreursCombo" path="enregistreurs"
												items="${enregistreursCombo}" itemValue="id" itemLabel="mid"
												data-placeholder=" Sélectionnez
							un ou des enregistreurs"
												class="activeInput form-control chosen">
											</form:select>
										</div>
									</c:if>
									<c:if test="${not empty enregistreurs }">
										<div class="form-group">
											<label for="enregistreurs">Attacher un ou des
												enregistreurs</label>
											<form:select multiple="true" id="enregistreursCombo"
												name="enregistreursCombo" path="enregistreurs"
												items="${enregistreursCombo}" itemValue="id" itemLabel="mid"
												data-placeholder=" Sélectionnez
							un ou des enregistreurs"
												class="activeInput form-control chosen">
											</form:select>
										</div>
										<table class="list-group text-primary">
											<thead>
												<tr>
													<th>Enregistreur</th>
													<th>Dernière mesure</th>
													<th>Niveau manuel</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${ouvrage.enregistreurs}"
													var="enregistreur">
													<c:url var="urlEnregistreurUpdate"
														value="/enregistreur/update/${enregistreur.id}" />
													<tr>
														<td class="text-primary"><a
															class="list-group-item text-uppercase"
															href="${urlEnregistreurUpdate}">${enregistreur.mid}</a></td>
														<td><c:out
																value="${enregistreur.niveauManuel.valeur}" /></td>
														<td><c:out
																value="${enregistreur.mesureEnregistreur.valeur}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>


		<!-- /content wrapper -->
		<a class="exit-offscreen"></a>
		<jsp:include page="/template/footer.jsp" />

		<script type="text/javascript">
			function activateNappeOrSurface() {

				if ($('#typeOuvrage :selected').val() == 'NAPPESOUTERRAINE') {
					console.log('nappe  ' + $('#typeOuvrage').value)
					$('.nappesouterraine').attr('style', 'display: block;');
					$('.nappesouterraine').attr('disabled', false);
					$('.nappesouterraine').show();

					$('.eaudesurface').attr('style', 'display: none;');
					$('.eaudesurface').attr('disabled', true);
					$('.eaudesurface').hide();
				}
				if ($('#typeOuvrage :selected').val() == 'EAUDESURFACE') {
					$('.nappesouterraine').attr('style', 'display: none;');
					$('.nappesouterraine').attr('disabled', true);
					$('.nappesouterraine').hide();

					$('.eaudesurface').attr('style', 'display: block;');
					$('.eaudesurface').attr('disabled', false);
					$('.eaudesurface').show();
				}
			}

			function activateOuvrageMaitre() {

				if ($('#asservissement :selected').val() == 'true') {
					$('#ouvrageMaitre').attr('style', 'display: block;');
					$('#ouvrageMaitre').attr('disabled', false);
					$('#ouvrageMaitre').show();
				}
				if ($('#asservissement :selected').val() == 'false') {
					$('#ouvrageMaitre').attr('style', 'display: none;');
					$('#ouvrageMaitre').attr('disabled', true);
					$('#ouvrageMaitre').hide();
				}
			}
		</script>