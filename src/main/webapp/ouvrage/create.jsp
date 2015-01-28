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

<!-- content wrapper -->
<div class="content-wrap bg-default clearfix row">
	<div class="col-lg-12 col-md-12 col-xs-12">
		<div class="bg-white p15 shadow content-inner">
			<div class="panel">
				<header class="panel-heading no-b col-lg-offset-2">
					<h1 class="h3 text-primary mt0">Création d'un Ouvrage</h1>
					<p class="text-muted">Le formulaire permet de créer un Ouvrage.</p>
				</header>
				<div class="panel-body">
					<c:url var="createOuvrage" value="/ouvrage/create" />
					<form:form id="form" method="POST" action="${createOuvrage}"
						modelAttribute="ouvrage" role="form" class="parsley-form"
						data-validate="parsley" data-show-errors="true">

						<form:hidden path="id" />

						<div class="col-md-4 col-lg-4 col-md-4 col-xs-12 col-lg-offset-2">
							<div class="form-group">
								<label for="nom">Nom</label>
								<form:input type="text" class="form-control" id="nom" path="nom"
									placeholder="" />
							</div>
							<div class="form-group">
								<label for="codeOuvrage">Code de l'ouvrage</label>
								<form:input type="text" class="form-control" id="codeOuvrage"
									path="codeOuvrage" placeholder="" />
							</div>
							<div class="form-group">
								<label for="typeOuvrage">Type de l'ouvrage</label>
								<div>
									<form:radiobuttons path="typeOuvrage"
										items="${typesOuvrageRadioButton}" id="typeOuvrage"
										itemLabel="description"
										onchange="javascript:activateNappeOrSurface();" />
								</div>
							</div>
							<div class="form-group">
								<label for="codeSite">Code site</label>
								<form:input type="tel" class="form-control" id="codeSite"
									path="codeSite" placeholder="" />
							</div>
							<div class="form-group">
								<label for="coteRepereNGF">Côte repère NGF</label>
								<form:input type="text" class="form-control" id="coteRepereNGF"
									path="coteRepereNGF" placeholder="" />
							</div>
							<!-- 					<div class="form-group"> -->
							<!-- 						<label for="niveauManuel">Niveau manuel</label> -->
							<%-- 						<form:input type="text" class="form-control" id="niveauManuel" --%>
							<%-- 							path="niveauManuel" placeholder="" /> --%>
							<!-- 					</div> -->

							<!-- 					<div class="form-group"> -->
							<!-- 						<label for="mesureEnregistreur">Dernière mesure</label> -->
							<%-- 						<form:input type="text" class="form-control" --%>
							<%-- 							id="mesureEnregistreur" path="mesureEnregistreur" placeholder="" /> --%>
							<!-- 					</div> -->
							<div class="form-group">
								<label for="commentaire">Commentaire</label>
								<form:input type="text" class="form-control" id="commentaire"
									path="commentaire" placeholder="" />
							</div>
							<div class="form-group">
								<label for="asservissement">Asservissement</label>
								<form:radiobutton id="asservissement" path="asservissement"
									label="oui" value="true"
									onchange="javascript:activateOuvrageMaitre();" />
								<form:radiobutton id="asservissement" path="asservissement"
									label="non" value="false"
									onchange="javascript:activateOuvrageMaitre();" />
							</div>
							<div id="ouvrageMaitre" class="form-group">
								<label for="ouvrageMaitre">Ouvrage maître</label>
								<form:input type="text" class="form-control" id="ouvrageMaitre"
									path="ouvrageMaitre" placeholder="" />
							</div>
							<div class="form-group">
								<label for="croquisDynamique">Croquis dynamique</label>
								<form:input type="text" class="form-control"
									id="croquisDynamique" path="croquisDynamique" placeholder="" />
							</div>
							<div id="coteSol" class="form-group">
								<label for="coteSol">côte Sol Berge (eau de surface)</label>
								<form:input type="text" class="form-control" path="coteSol"
									placeholder="" />
							</div>
							<div id="numeroBSS" class="form-group">
								<label for="numeroBSS">numéro BSS (nappe souterraine)</label>
								<form:input type="text" class="form-control" id="numeroBSS"
									path="numeroBSS" placeholder="" />
							</div>
							<div id="mesureProfondeur" class="form-group">
								<label for="mesureProfondeur">Mesure repère Profondeur
									/NGF (nappe souterraine)</label>
								<form:input type="text" class="form-control"
									path="mesureProfondeur" placeholder="" />
							</div>
							<div id="mesureRepereNGFSol" class="form-group">
								<label for="mesureRepereNGFSol">Mesure repère NGF / Sol
									(nappe souterraine)</label>
								<form:input type="text" class="form-control"
									path="mesureRepereNGFSol" placeholder="" />
							</div>
							<div class="form-group">
								<label for="coteSolNGF">côte Sol / NGF</label>
								<form:input type="text" class="form-control" id="coteSolNGF"
									path="coteSolNGF" placeholder="" />
							</div>
							<div class="form-group">
								<label for="enregistreurs">Attacher un ou des
									enregistreurs</label>
								<form:select multiple="true" id="enregistreursCombo"
									name="enregistreursCombo" path="enregistreurs"
									items="${enregistreursCombo}" itemValue="id" itemLabel="id"
									data-placeholder=" Sélectionnez
							un ou des enregistreurs"
									class="form-control chosen">
								</form:select>
							</div>
							<div class="pull-right">
								<a href="<c:url  value="/ouvrage/list" />"
									class="btn btn-default btn-outline">Retour</a>
								<button type="submit" class="btn btn-outline btn-primary">Créer</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /content wrapper -->
<a class="exit-offscreen"></a>
<jsp:include page="/template/footer.jsp" />

<script type="text/javascript">
	function activateNappeOrSurface() {

		if (document.getElementById("typeOuvrage1").checked == true) {

			$('#mesureRepereNGFSol *').attr('disabled', false);
			$('#mesureRepereNGFSol *').show();

			$('#mesureProfondeur *').attr('disabled', false);
			$('#mesureProfondeur *').show();

			$('#numeroBSS *').attr('disabled', false);
			$('#numeroBSS *').show();

			$('#coteSol *').attr('disabled', true);
			$('#coteSol *').hide();
		} else {

			$('#mesureRepereNGFSol *').attr('disabled', true);
			$('#mesureRepereNGFSol *').hide();

			$('#mesureProfondeur *').attr('disabled', true);
			$('#mesureProfondeur *').hide();

			$('#numeroBSS *').attr('disabled', true);
			$('#numeroBSS *').hide();

			$('#coteSol *').attr('disabled', false);
			$('#coteSol *').show();
		}
	}

	function activateOuvrageMaitre() {

		if (document.getElementById("asservissement").checked == true) {
			$('#ouvrageMaitre *').attr('disabled', false);
			$('#ouvrageMaitre *').show();
		} else {
			$('#ouvrageMaitre *').attr('disabled', true);
			$('#ouvrageMaitre *').hide();
		}
	}
</script>