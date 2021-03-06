package com.github.scribejava.apis;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenJsonExtractor;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.Verb;

import com.github.scribejava.apis.service.HHOAuthServiceImpl;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;

public class HHApi extends DefaultApi20 {

    private static final String AUTHORIZE_URL = "https://hh.ru/oauth/authorize?response_type=code&" +
        "client_id=%s&redirect_uri=%s";

    private static final String TOKEN_URL = "https://hh.ru/oauth/token";

    protected HHApi() {
    }

    private static class InstanceHolder {
        private static final HHApi INSTANCE = new HHApi();
    }

    public static HHApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        return String.format(AUTHORIZE_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OAuth2AccessTokenJsonExtractor.instance();
    }

    @Override
    public OAuth20Service createService(OAuthConfig config) {
        return new HHOAuthServiceImpl(this, config);
    }
}
