package si.um.voteit.voteit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import si.um.voteit.voteit.dao.SurveyRepository;
import si.um.voteit.voteit.dao.VoteRepository;
import si.um.voteit.voteit.logic.TokenGenerator;
import si.um.voteit.voteit.vao.Option;
import si.um.voteit.voteit.vao.Survey;
import java.util.logging.Logger;

@SpringBootApplication
public class VoteitApplication {

	Logger log=Logger.getLogger(this.getClass().toString());

	public static void main(String[] args) {
		SpringApplication.run(VoteitApplication.class, args);
	}

	@Bean
	public CommandLineRunner insertSampleSurveyBean(SurveyRepository surveyDao, VoteRepository voteDao) {
		return args -> {
			log.info("Ready, Set, Go!");
			VoteitApplication.insertSampleSurvey(surveyDao,voteDao);
		};
	}

	static void insertSampleSurvey(SurveyRepository surveyDao, VoteRepository voteDao){

		//TODO remove after production deployment
		Survey survey=new Survey("sample.user@vote.it","Sample Survey");
		survey.setEnabled(true);
		survey.setResultsPubliclyAvailable(true);
		survey.setVoteCanBeAltered(false);

		survey.getOptions().add(new Option(1,"Option 1"));
		survey.getOptions().add(new Option(2,"Option 2"));
		survey.getOptions().add(new Option(3,"Option 3"));
		survey.getOptions().add(new Option(4,"Option 4"));

		surveyDao.save(survey);

		TokenGenerator tg=new TokenGenerator(survey,voteDao);
		tg.generateTokens(20);
	}

}
