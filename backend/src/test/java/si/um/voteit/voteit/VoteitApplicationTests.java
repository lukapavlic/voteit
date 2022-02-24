package si.um.voteit.voteit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import si.um.voteit.voteit.dao.OptionRepository;
import si.um.voteit.voteit.dao.SurveyRepository;
import si.um.voteit.voteit.dao.VoteRepository;
import si.um.voteit.voteit.logic.TokenGenerator;
import si.um.voteit.voteit.vao.Option;
import si.um.voteit.voteit.vao.Survey;

import java.util.logging.Logger;

@SpringBootTest
class VoteitApplicationTests {

	static Logger log=Logger.getLogger(VoteitApplicationTests.class.toString());

	@Autowired
	SurveyRepository surveyDao;

	@Autowired
	OptionRepository optionsDao;

	@Autowired
	VoteRepository voteDao;

	String insertSurvey(String name, boolean enabled, boolean resultsAvailable, boolean alterVote, int tokens){
		Survey survey=new Survey("sample.user@vote.it",name);
		survey.setEnabled(enabled);
		survey.setResultsPubliclyAvailable(resultsAvailable);
		survey.setVoteCanBeAltered(alterVote);

		survey.getOptions().add(new Option(1,name+"-Option 1"));
		survey.getOptions().add(new Option(2,name+"-Option 2"));
		survey.getOptions().add(new Option(3,name+"-Option 3"));
		survey.getOptions().add(new Option(4,name+"-Option 4"));

		surveyDao.save(survey);

		TokenGenerator tg=new TokenGenerator(survey,voteDao);
		tg.generateTokens(tokens);

		return tg.getLastGeneratedToken();

	}

	@BeforeAll
	static void beforeAll() {
		log.info("@BeforeAll");
	}

	@AfterAll
	static void afterAll() {
		log.info("@AfterAll");
	}



	@Test
	void contextLoads() {
		log.info("@Test contextLoads");

		String token1=insertSurvey("EnabledSurvey",true,true,true,5);
		String token2=insertSurvey("DisabledSurvey",false,true,true,5);
		String token3=insertSurvey("EnabledSurvey-OneVote",true,true,false,5);
		String token4=insertSurvey("EnabledSurvey-OneVote-NoResults",true,false,false,5);

	}

}
