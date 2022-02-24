package si.um.voteit.voteit.logic;

import si.um.voteit.voteit.dto.SurveyWithResultsDto;
import si.um.voteit.voteit.vao.Survey;
import si.um.voteit.voteit.vao.Vote;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyScoreCalculator {

    private Survey surveyToWatch;

    private List<Vote> votes;

    public SurveyScoreCalculator(Survey surveyToWatch, List<Vote> votes) {
        this.surveyToWatch = surveyToWatch;
        this.votes = votes;
    }

    int votesSoFar=0;

    public SurveyWithResultsDto getResults() {
        SurveyWithResultsDto ret=new SurveyWithResultsDto(surveyToWatch);

        votesSoFar=0;

        Map<Integer,Integer> scorePerOption=new HashMap<>();

        //TODO calculate rank-all type od survey

        votes.forEach(v -> {
            if (ret.getFirstVote()==null) ret.setFirstVote(v.getTimeVoted());
            if (ret.getLastVote()==null) ret.setLastVote(v.getTimeVoted());
            if (ret.getFirstVote()!=null && v.getTimeVoted()!=null && ret.getFirstVote().isAfter(v.getTimeVoted())) ret.setFirstVote(v.getTimeVoted());
            if (ret.getLastVote()!=null && v.getTimeVoted()!=null && ret.getLastVote().isBefore(v.getTimeVoted())) ret.setLastVote(v.getTimeVoted());

            scorePerOption.put(
                    v.getOptionIdSelected(),
                    1+scorePerOption.getOrDefault(v.getOptionIdSelected(),0));

            if (v.getOptionIdSelected()!=null) votesSoFar++;

        });

        ret.setVotesSoFar(votesSoFar);

        ret.getOptions().forEach(o ->
            o.setVoteScore(scorePerOption.getOrDefault(Integer.valueOf(o.getId()),0))
        );

        return ret;
    }

}
