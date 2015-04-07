package fr.treeptik.model.assembler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;
import fr.treeptik.model.TypeOuvrage;
import org.springframework.stereotype.Component;


public class MarkerAssembler_Should {
	MarkerAssembler markerAssembler = new MarkerAssembler();

	@Test
	public void fromOuvrage_fillItemId() {
		Ouvrage ouvrage = ouvrageWithMinValue();
		ouvrage.setId(1234);
		assertThat(markerAssembler.fromOuvrage(ouvrage).getItemId(), is(1234));
	}

	@Test
	public void fromOuvrage_fillLat() {
		Ouvrage ouvrage = ouvrageWithMinValue();
		assertThat(markerAssembler.fromOuvrage(ouvrage).getLat(),
				is((float) 1.0));
	}

	@Test
	public void fromOuvrage_fillLng() {
		Ouvrage ouvrage = ouvrageWithMinValue();
		assertThat(markerAssembler.fromOuvrage(ouvrage).getLng(),
				is((float) 2.0));
	}

	@Test
	public void fromOuvrage_fillTitle() {
		Ouvrage ouvrage = ouvrageWithMinValue();
		ouvrage.setCodeOuvrage("anyCodeOuvrage");
		assertThat(markerAssembler.fromOuvrage(ouvrage).getTitle(),
				is("anyCodeOuvrage"));
	}

	@Test
	public void fromOuvrage_fillItemName() {
		Ouvrage ouvrage = ouvrageWithMinValue();
        ouvrage.setNom("anyName");
		assertThat(markerAssembler.fromOuvrage(ouvrage).getItemName(),
				is("anyName"));
	}

	@Test
	public void fromSite_fillItemId() {
		Site site = siteWithMinValue();
		site.setId(2345);
		assertThat(markerAssembler.fromSite(site).getItemId(), is(2345));
	}

	@Test
	public void fromSite_fillLat() {
		Site site = siteWithMinValue();
		assertThat(markerAssembler.fromSite(site).getLat(), is((float) 3.0));
	}

	@Test
	public void fromSite_fillLng() {
		Site site = siteWithMinValue();
		assertThat(markerAssembler.fromSite(site).getLng(), is((float) 4.0));
	}

	@Test
	public void fromSite_fillTitle() {
		Site site = siteWithMinValue();
		site.setCodeSite("anyCodeSite");
		assertThat(markerAssembler.fromSite(site).getTitle(), is("anyCodeSite"));
	}

	@Test
	public void fromSite_fillItemName() {
		Site site = siteWithMinValue();
		site.setNom("anySiteName");
		assertThat(markerAssembler.fromSite(site).getItemName(),
				is("anySiteName"));
	}

	@Test
	public void fromEtablissement_fillItemId() {
		Etablissement etablissement = etablissementWithMinValue();
		etablissement.setId(3456);
		assertThat(
				markerAssembler.fromEtablissement(etablissement).getItemId(),
				is(3456));
	}

	@Test
	public void fromEtablissement_fillLat() {
		Etablissement etablissement = etablissementWithMinValue();
		assertThat(markerAssembler.fromEtablissement(etablissement).getLat(),
				is((float) 5.0));
	}

	@Test
	public void fromEtablissement_fillLng() {
		Etablissement etablissement = etablissementWithMinValue();
		assertThat(markerAssembler.fromEtablissement(etablissement).getLng(),
				is((float) 6.0));
	}

	@Test
	public void fromEtablissement_fillTitle() {
		Etablissement etablissement = etablissementWithMinValue();
		etablissement.setCodeEtablissement("anyCodeEtablissement");
		assertThat(markerAssembler.fromEtablissement(etablissement).getTitle(),
				is("anyCodeEtablissement"));
	}

	@Test
	public void fromEtablissement_fillItemName() {
		Etablissement etablissement = etablissementWithMinValue();
		etablissement.setNom("anyEtablissementName");
		assertThat(markerAssembler.fromEtablissement(etablissement)
				.getItemName(), is("anyEtablissementName"));
	}

	private Ouvrage ouvrageWithMinValue() {
		Ouvrage ouvrage = new Ouvrage();
		ouvrage.setCoordonneesGeographique("1.0/2.0");
		ouvrage.setLatitude();
		ouvrage.setLongitude();
		ouvrage.setTypeOuvrage(new TypeOuvrage());
		return ouvrage;
	}

	private Site siteWithMinValue() {
		Site site = new Site();
		site.setCoordonneesGeographique("3.0/4.0");
		site.setLatitude();
		site.setLongitude();
		return site;
	}

	private Etablissement etablissementWithMinValue() {
		Etablissement etablissement = new Etablissement();
		etablissement.setCoordonneesGeographique("5.0/6.0");
		etablissement.setLatitude();
		etablissement.setLongitude();
		return etablissement;
	}
}
