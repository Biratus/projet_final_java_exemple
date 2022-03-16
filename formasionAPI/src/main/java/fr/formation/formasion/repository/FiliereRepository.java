package fr.formation.formasion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Formation;

public interface FiliereRepository extends JpaRepository<Filiere, Integer> {

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "join formasion.user as u on u.personne_id=m.personne_id "
					+ "where u.username=:username")
	List<Filiere> findFiliereOfFormateur(String username);

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "join formasion.user as u on u.personne_id=m.personne_id "
					+ "where u.username=:username and m.date_debut<NOW() and m.date_fin>NOW()")
	List<Filiere> findActiveFiliereOfFormateur(String username);

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "join formasion.user as u on u.personne_id=m.personne_id "
					+ "where u.username=:username and m.date_debut>NOW() or m.date_fin<NOW()")
	List<Filiere> findInactiveFiliereOfFormateur(String username);

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "where m.date_debut<NOW() and m.date_fin>NOW()")
	List<Filiere> findActiveFiliere();

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "where m.date_debut>NOW() or m.date_fin<NOW()")
	List<Filiere> findInactiveFiliere();

	List<Filiere> findFiliereByFormation(Formation formation);

	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "where m.date_debut<NOW() and m.date_fin>NOW() and m.personne_id=:formateurId")
	List<Filiere> findActiveFiliereByFormateurId(Integer formateurId);
	
	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "where m.date_debut>NOW() or m.date_fin<NOW() and m.personne_id=:formateurId")
	List<Filiere> findInactiveFiliereByFormateurId(Integer formateurId);
	
	@Query(nativeQuery=true,
			value="select f.* from filiere as f "
					+ "join module as m on m.filiere_id=f.id "
					+ "where m.personne_id=:formateurId")
	List<Filiere> findFiliereByFormateurId(Integer formateurId);

	@Query(nativeQuery=true,
			value="select distinct f.* from filiere as f "
					+ "left join module as m on f.id=m.filiere_id "
					+ "where f.formation_libelle=:libelleFormation "
					+ "and (m.date_debut>NOW() or m.id is null)")
	List<Filiere> findFilieresNotStartedOfFormation(String libelleFormation);
}
