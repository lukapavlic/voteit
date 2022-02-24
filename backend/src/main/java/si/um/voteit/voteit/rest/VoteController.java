package si.um.voteit.voteit.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.um.voteit.voteit.dao.OptionRepository;
import si.um.voteit.voteit.dao.SurveyRepository;
import si.um.voteit.voteit.dao.VoteRepository;
import si.um.voteit.voteit.dto.SurveyWithResultsDto;
import si.um.voteit.voteit.logic.SurveyScoreCalculator;
import si.um.voteit.voteit.rest.security.SecurityFacade;
import si.um.voteit.voteit.vao.Survey;
import si.um.voteit.voteit.vao.Vote;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/vote")
public class VoteController {

    private static final Logger log = Logger.getLogger(VoteController.class.toString());

    public VoteController(SurveyRepository survDao, OptionRepository optDao, VoteRepository voteDao) {
        this.survDao = survDao;
        this.optDao = optDao;
        this.voteDao = voteDao;
    }

    private SurveyRepository survDao;

    private OptionRepository optDao;

    private VoteRepository voteDao;

    @GetMapping
    public @ResponseBody ResponseEntity<Vote> getVote(@RequestHeader("token") String token) {
        //validate
        Optional<Vote> val=voteDao.findById(token);
        if (val.isEmpty()) {
            log.info("/vote/"+token+" ; Token not found!");
            return new ResponseEntity("token-not-found", HttpStatus.NOT_ACCEPTABLE);
        }
        //return
        return ResponseEntity.ok(val.get());
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Vote> postVote(@RequestHeader("token") String token,@RequestHeader("optionSelected") int optionSelected) {
        //validate
        Optional<Vote> val=voteDao.findById(token);
        if (val.isEmpty()) {
            log.info("/vote/"+token+" ; Token not found!");
            return new ResponseEntity("token-not-found", HttpStatus.NOT_ACCEPTABLE);
        }
        Vote vote=val.get();
        Optional<Survey> survey=survDao.findById(vote.getOwningSurveyId());
        if (vote.getOptionIdSelected()!=null)
            //update?
            if (!survey.isEmpty() && !survey.get().getVoteCanBeAltered()) {
                log.info("/vote/"+token+" ; Vote already taken, cannot be altered!");
                return new ResponseEntity("vote-already-taken-and-cannot-be-altered", HttpStatus.NOT_ACCEPTABLE);
            }

        if (!survey.isEmpty() && !survey.get().getEnabled()) {
            log.info("/vote/"+token+" ; Survey is not enabled!");
            return new ResponseEntity("survey-not-enabled", HttpStatus.NOT_ACCEPTABLE);
        }

        vote.setOptionIdSelected(optionSelected);
        vote.setTimeVoted(LocalDateTime.now());
        voteDao.save(vote);

        //return
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/{surveyId}")
    public @ResponseBody
    ResponseEntity<SurveyWithResultsDto> getResults(@PathVariable("surveyId") int surveyId) {
        //validate
        Optional<Survey> val=survDao.findById(surveyId);
        if (val.isEmpty()) {
            log.info("/vote/"+surveyId+" ; Survey not found!");
            return new ResponseEntity("survey-not-found", HttpStatus.NOT_ACCEPTABLE);
        }
        Survey survey=val.get();
        //security
        SecurityFacade.User user=SecurityFacade.getInstance().getCurrentUser();
        if (!survey.getOwner().equals(user.getEmail()))
            if (!survey.getResultsPubliclyAvailable()) {
                log.info("/vote/"+surveyId+" ; Security violation!");
                return new ResponseEntity("not-allowed", HttpStatus.FORBIDDEN);
            }
        //return
        SurveyScoreCalculator ssc=new SurveyScoreCalculator(
                survey,
                voteDao.findAllByOwningSurveyId(survey.getId()));
        return ResponseEntity.ok(ssc.getResults());
    }

}
