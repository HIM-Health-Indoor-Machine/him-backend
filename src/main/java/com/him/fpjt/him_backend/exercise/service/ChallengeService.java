package com.him.fpjt.him_backend.exercise.service;

import com.him.fpjt.him_backend.exercise.domain.Challenge;
import com.him.fpjt.him_backend.exercise.domain.ChallengeStatus;
import java.util.List;

public interface ChallengeService {
    public boolean createChallenge(Challenge challenge);
    public List<Challenge> getChallengeByStatusAndUserId(long userId, ChallengeStatus status);
    public Challenge getChallengeDetail(long id);
    public boolean removeChallenge(long id);
    public boolean existsChallengeById(long id);
}
