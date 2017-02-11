package com.gochain.gochainandroid.rest;

import android.util.Log;

import com.gochain.gochainandroid.SessionValueHelper;
import com.gochain.gochainandroid.vo.AuthenticatedUserVo;
import com.gochain.gochainandroid.vo.CampaignVo;
import com.gochain.gochainandroid.vo.CampaignVoContainer;
import com.gochain.gochainandroid.vo.ProjectFlatVo;
import com.gochain.gochainandroid.vo.ProjectFlatVoContainer;
import com.gochain.gochainandroid.vo.ProjectVo;
import com.gochain.gochainandroid.vo.UserVo;
import com.gochain.gochainandroid.vo.VoteVo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brad on 10/02/2017.
 */

public class GoChainRestService extends AbstractRestService {
    private static final String AUTHENTICATE_USER_URL = BASE_URL + "/auth/login";
    private static final String VOTE_URL = BASE_URL + "/vote";
    private static final String FETCH_CAMPAIGNS_URL = BASE_URL + "/fetch_campaigns";
    private static final String VOTER_USER_URL = BASE_URL + "/api/v1/projects/voter/{user}";


    public AuthenticatedUserVo authenticateUser(UserVo userVo) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        try {
            AuthenticatedUserVo authenticatedUserVo = restTemplate.postForObject(AUTHENTICATE_USER_URL, userVo, AuthenticatedUserVo.class);
            return authenticatedUserVo;
        } catch (Throwable t) {
            Log.e(this.getClass().getName(), "Error trying to authenticate user with server", t);
        }
        return null;
    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptTypes = new ArrayList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptTypes);
        headers.set("x-access-token", SessionValueHelper.getSessionUser().getToken());
        Log.i("Token", SessionValueHelper.getSessionUser().getToken());
        return headers;
    }

    public List<ProjectFlatVo> fetchCampaigns() {
        HttpEntity<String> entity = new HttpEntity<>(this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
//            CampaignVoContainer campaignVos = restTemplate.getForObject(VOTE_URL, CampaignVoContainer.class)
            ResponseEntity<ProjectFlatVoContainer> responseEntity = restTemplate.exchange(VOTER_USER_URL, HttpMethod.GET, entity, ProjectFlatVoContainer.class, SessionValueHelper.getSessionUser().getUser().getUsername());
            ProjectFlatVoContainer projectFlatVoContainer = responseEntity.getBody();
            Log.i("nnkn", "got back: " + projectFlatVoContainer);
            return projectFlatVoContainer.getProjectFlatVos();
        } catch (Throwable t) {
            Log.e(this.getClass().getName(), "Error trying to authenticate user with server", t);
        }
        return null;
    }

    public Boolean sendVote(VoteVo voteVo) {
        HttpEntity<String> entity = new HttpEntity<>(this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Boolean success = restTemplate.postForObject(VOTE_URL, entity, Boolean.class, voteVo);
            return success;
        } catch (Throwable t) {
            Log.e(this.getClass().getName(), "Error trying to authenticate user with server", t);
        }
        return false;
    }

    public Boolean sendDummyVote() {
        VoteVo voteVo = new VoteVo("bradDigiD", null);
        return this.sendVote(voteVo);
    }
}
