package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import com.him.fpjt.him_backend.exercise.dto.ChallengeDto;
import java.util.List;

public interface ChallengeService {
    public void createChallenge(Challenge challenge);
    public List<Challenge> getChallengeByStatusAndUserId(long userId, ChallengeStatus status);
    public Challenge getChallengeDetail(long id);
    public void modifyChallenge(long id, ChallengeDto challengeDto);
    public void removeChallenge(long id);
    public boolean existsChallengeById(long id);
    public boolean modifyChallengeAchieveCnt(long id);
    public void modifyChallengeStatus();
    public List<Long> getAllChallengeId();
}       