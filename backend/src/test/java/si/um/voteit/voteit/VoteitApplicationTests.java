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

	void setupData(){
		Survey survey=new Survey("test@vote.it","Sample Survey");
//		survey.getAttributes().put(Survey.ATTRIBUTE_ENABLED,true);
//		survey.getAttributes().put(Survey.ATTRIBUTE_RESULTS_PUBLICLY_AVAILABLE,true);
//		survey.getAttributes().put(Survey.ATTRIBUTE_VOTE_CAN_BE_ALTERED,false);

		survey.getOptions().add(new Option(1,"Option 1"));
		survey.getOptions().add(new Option(2,"Option 2"));
		survey.getOptions().add(new Option(3,"Option 3"));
		survey.getOptions().add(new Option(4,"Option 4"));

		surveyDao.save(survey);

//		TokenGenerator tg=new TokenGenerator(survey,voteDao);
//		tg.generateTokens(20000);
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

		setupData();

//		System.out.println("Surveys");
//		surveyDao.findAll().forEach(System.out::println);

		//voteDao.findAll().forEach(System.out::println);
	}

}
