package si.um.voteit.voteit.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.um.voteit.voteit.dao.OptionRepository;
import si.um.voteit.voteit.dao.SurveyRepository;
import si.um.voteit.voteit.rest.security.SecurityFacade;
import si.um.voteit.voteit.vao.Option;
import si.um.voteit.voteit.vao.Survey;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/surveys")
public class SurveyController {

	private static final Logger log = Logger.getLogger(SurveyController.class.toString());

	public SurveyController(SurveyRepository dao,OptionRepository optDao) {
		this.dao = dao;
		this.optDao=optDao;
	}

	private SurveyRepository dao;

	private OptionRepository optDao;

	@GetMapping
	public @ResponseBody Iterable<Survey> getAll() {
        SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		return dao.findAllByOwner(user.getEmail());
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Survey> getItem(@PathVariable("id") int id) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+" ; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//return
		return ResponseEntity.ok(val.get());
	}

	@PostMapping
	public ResponseEntity<Survey> postItem(@RequestBody Survey vao) {
		//validate
		Optional<Survey> val=dao.findById(vao.getId());
		if (!val.isEmpty()) {
			log.info("Survey already exists!");
			return new ResponseEntity("survey-already-exist", HttpStatus.NOT_ACCEPTABLE);
		}
		//save
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		vao.setOwner(user.getEmail());
		vao.setTimeCreated(LocalDateTime.now());
		vao.setTimeLastModified(null);
		dao.save(vao);
	    return ResponseEntity.ok(vao);
	}

	@PostMapping("/{id}/options")
	public ResponseEntity<Survey> postOption(@PathVariable("id") int id, @RequestBody Option vao) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"/options ; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//save
		val.get().getOptions().add(new Option(vao.getOrderValue(), vao.getValue()));
		val.get().setModifiedNow();
		dao.save(val.get());
		return ResponseEntity.ok(val.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Survey> updateBasicInfo(@PathVariable("id") int id, @RequestBody Survey vao) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//update
		val.get().setName(vao.getName());
		if (vao.getEnabled()!=null) val.get().setEnabled(vao.getEnabled());
		if (vao.getVoteCanBeAltered()!=null) val.get().setVoteCanBeAltered(vao.getVoteCanBeAltered());
		if (vao.getResultsPubliclyAvailable()!=null) val.get().setResultsPubliclyAvailable(vao.getResultsPubliclyAvailable());
		if (vao.getType()!=null) val.get().setType(vao.getType());
		val.get().setModifiedNow();
		dao.save(val.get());
		return ResponseEntity.ok(val.get());
	}

	@PutMapping("/{id}/options")
	public ResponseEntity<Survey> updateOption(@PathVariable("id") int id, @RequestBody Option vao) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		Optional<Option> valOpt=optDao.findById(vao.getId());
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"/options; Option not found!");
			return new ResponseEntity("option-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//update
		valOpt.get().setValue(vao.getValue());
		valOpt.get().setOrderValue(vao.getOrderValue());
		optDao.save(valOpt.get());
		return ResponseEntity.ok(val.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Survey> deleteOption(@PathVariable("id") int id) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//delete
		dao.delete(val.get());
		return ResponseEntity.ok(val.get());
	}

	@DeleteMapping("/{id}/options/{optId}")
	public ResponseEntity<Survey> deleteOption(@PathVariable("id") int id,@PathVariable("optId") int optId) {
		//validate
		Optional<Survey> val=dao.findById(id);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"; Survey not found!");
			return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		Optional<Option> valOpt=optDao.findById(optId);
		if (val.isEmpty()) {
			log.info("/surveys/"+id+"/options; Option not found!");
			return new ResponseEntity("option-not-found", HttpStatus.NOT_ACCEPTABLE);
		}
		//security
		SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
		if (!val.get().getOwner().equals(user.getEmail())) {
			log.info("/surveys/"+id+" ; Security violation!");
			return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
		}
		//delete
		val.get().removeOptionWithId(valOpt.get().getId());
		val.get().setModifiedNow();
		dao.save(val.get());
		optDao.delete(valOpt.get());
		return ResponseEntity.ok(val.get());
	}

}