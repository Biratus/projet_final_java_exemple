package fr.formation.formasion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.formation.formasion.model.Filiere;
import fr.formation.formasion.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

	List<Module> findByFiliere(Filiere f);

	List<Module> findByFormateurIsNull();
	
	@Query(value="select m.* from module as m "
			+ "left join personne as p on p.filiere_id=m.filiere_id "
			+ "left join formasion.user as u on u.personne_id=p.id "
			+ "where u.username=:username",nativeQuery=true)
	List<Module> findModuleOfStagiaire(String username);
	
	@Query(value="select m.* from module as m "
			+ "left join formasion.user as u on u.personne_id=m.personne_id "
			+ "where u.username=:username",nativeQuery=true)
	List<Module> findModuleOfFormateur(String username);

	@Query(value="select m.* from module as m "
			+ "left join formasion.user as u on u.personne_id=m.personne_id "
			+ "where u.username=:username and m.libelle like %:libelle% and m.filiere_id=:filiereId",nativeQuery=true)
	List<Module> findModuleOfFormateurByLibelleContainingAndFiliereId(String username, String libelle,
			Integer filiereId);

	List<Module> findByLibelleContainingAndFiliereId(String libelle, Integer filiereId);

	@Query(value="select m.* from module as m "
			+ "left join formasion.user as u on u.personne_id=m.personne_id "
			+ "where u.username=:username and m.libelle like %:libelle%",nativeQuery=true)
	List<Module> findModuleOfFormateurByLibelleContaining(String username, String libelle);

	List<Module> findByLibelleContaining(String libelle);

	@Query(value="select m.* from module as m "
			+ "left join formasion.user as u on u.personne_id=m.personne_id "
			+ "where u.username=:username and m.filiere_id=:filiereId",nativeQuery=true)
	List<Module> findModuleOfFormateurByFiliereId(String username, Integer filiereId);

	List<Module> findByFiliereIdAndFormateurId(Integer filiereId, Integer formateurId);

	List<Module> findByLibelleContainingAndFormateurId(String libelle, Integer formateurId);

	List<Module> findByLibelleContainingAndFiliereIdAndFormateurId(String libelle, Integer filiereId,
			Integer formateurId);

	List<Module> findByFormateurId(Integer formateurId);
}
