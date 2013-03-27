/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This file was generated.
 *  with google-apis-code-generator 1.3.0 (build: 2013-03-20 15:12:36 UTC)
 *  on 2013-03-20 at 22:24:17 UTC 
 */

package com.gottagged380.userendpoint;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.common.base.Preconditions;

/**
 * Service definition for Userendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link UserendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * <p>
 * Upgrade warning: this class now extends {@link AbstractGoogleJsonClient}, whereas in prior
 * version 1.8 it extended {@link com.google.api.client.googleapis.services.GoogleClient}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Userendpoint extends AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    Preconditions.checkState(GoogleUtils.VERSION.equals("1.13.2-beta"),
        "You are currently running with version %s of google-api-client. " +
        "You need version 1.13.2-beta of google-api-client to run version " +
        "1.13.2-beta of the  library.", GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://gottagged380.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "userendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   * @deprecated (scheduled to be removed in 1.13)
   */
  @Deprecated
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport
   * @param jsonFactory JSON factory
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Userendpoint(HttpTransport transport, JsonFactory jsonFactory,
      HttpRequestInitializer httpRequestInitializer) {
    super(transport,
        jsonFactory,
        DEFAULT_ROOT_URL,
        DEFAULT_SERVICE_PATH,
        httpRequestInitializer,
        false);
  }

  /**
   * @param transport HTTP transport
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @param rootUrl root URL of the service
   * @param servicePath service path
   * @param jsonObjectParser JSON object parser
   * @param googleClientRequestInitializer Google request initializer or {@code null} for none
   * @param applicationName application name to be sent in the User-Agent header of requests or
   *        {@code null} for none
   * @param suppressPatternChecks whether discovery pattern checks should be suppressed on required
   *        parameters
   */
  Userendpoint(HttpTransport transport,
      HttpRequestInitializer httpRequestInitializer,
      String rootUrl,
      String servicePath,
      JsonObjectParser jsonObjectParser,
      GoogleClientRequestInitializer googleClientRequestInitializer,
      String applicationName,
      boolean suppressPatternChecks) {
    super(transport,
        httpRequestInitializer,
        rootUrl,
        servicePath,
        jsonObjectParser,
        googleClientRequestInitializer,
        applicationName,
        suppressPatternChecks);
  }

  @Override
  protected void initialize(AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getUser".
   *
   * This request holds the parameters needed by the the userendpoint server.  After setting any
   * optional parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetUser getUser(String id) throws java.io.IOException {
    GetUser result = new GetUser(id);
    initialize(result);
    return result;
  }

  public class GetUser extends UserendpointRequest<com.gottagged380.userendpoint.model.User> {

    private static final String REST_PATH = "user/{id}";

    /**
     * Create a request for the method "getUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link GetUser#execute()} method to invoke the remote operation.
     * <p> {@link GetUser#initialize(AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetUser(String id) {
      super(Userendpoint.this, "GET", REST_PATH, null, com.gottagged380.userendpoint.model.User.class);
      this.id = Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetUser setAlt(String alt) {
      return (GetUser) super.setAlt(alt);
    }

    @Override
    public GetUser setFields(String fields) {
      return (GetUser) super.setFields(fields);
    }

    @Override
    public GetUser setKey(String key) {
      return (GetUser) super.setKey(key);
    }

    @Override
    public GetUser setOauthToken(String oauthToken) {
      return (GetUser) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUser setPrettyPrint(Boolean prettyPrint) {
      return (GetUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUser setQuotaUser(String quotaUser) {
      return (GetUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUser setUserIp(String userIp) {
      return (GetUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private String id;

    /**

     */
    public String getId() {
      return id;
    }

    public GetUser setId(String id) {
      this.id = id;
      return this;
    }

  }

  /**
   * Create a request for the method "insertUser".
   *
   * This request holds the parameters needed by the the userendpoint server.  After setting any
   * optional parameters, call the {@link InsertUser#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.gottagged380.userendpoint.model.User}
   * @return the request
   */
  public InsertUser insertUser(com.gottagged380.userendpoint.model.User content) throws java.io.IOException {
    InsertUser result = new InsertUser(content);
    initialize(result);
    return result;
  }

  public class InsertUser extends UserendpointRequest<com.gottagged380.userendpoint.model.User> {

    private static final String REST_PATH = "user";

    /**
     * Create a request for the method "insertUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link InsertUser#execute()} method to invoke the remote
     * operation. <p> {@link InsertUser#initialize(AbstractGoogleClientRequest)} must be called to
     * initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.gottagged380.userendpoint.model.User}
     * @since 1.13
     */
    protected InsertUser(com.gottagged380.userendpoint.model.User content) {
      super(Userendpoint.this, "POST", REST_PATH, content, com.gottagged380.userendpoint.model.User.class);
    }

    @Override
    public InsertUser setAlt(String alt) {
      return (InsertUser) super.setAlt(alt);
    }

    @Override
    public InsertUser setFields(String fields) {
      return (InsertUser) super.setFields(fields);
    }

    @Override
    public InsertUser setKey(String key) {
      return (InsertUser) super.setKey(key);
    }

    @Override
    public InsertUser setOauthToken(String oauthToken) {
      return (InsertUser) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertUser setPrettyPrint(Boolean prettyPrint) {
      return (InsertUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertUser setQuotaUser(String quotaUser) {
      return (InsertUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertUser setUserIp(String userIp) {
      return (InsertUser) super.setUserIp(userIp);
    }

  }

  /**
   * Create a request for the method "listUser".
   *
   * This request holds the parameters needed by the the userendpoint server.  After setting any
   * optional parameters, call the {@link ListUser#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListUser listUser() throws java.io.IOException {
    ListUser result = new ListUser();
    initialize(result);
    return result;
  }

  public class ListUser extends UserendpointRequest<com.gottagged380.userendpoint.model.CollectionResponseUser> {

    private static final String REST_PATH = "user";

    /**
     * Create a request for the method "listUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link ListUser#execute()} method to invoke the remote operation.
     * <p> {@link ListUser#initialize(AbstractGoogleClientRequest)} must be called to initialize this
     * instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListUser() {
      super(Userendpoint.this, "GET", REST_PATH, null, com.gottagged380.userendpoint.model.CollectionResponseUser.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListUser setAlt(String alt) {
      return (ListUser) super.setAlt(alt);
    }

    @Override
    public ListUser setFields(String fields) {
      return (ListUser) super.setFields(fields);
    }

    @Override
    public ListUser setKey(String key) {
      return (ListUser) super.setKey(key);
    }

    @Override
    public ListUser setOauthToken(String oauthToken) {
      return (ListUser) super.setOauthToken(oauthToken);
    }

    @Override
    public ListUser setPrettyPrint(Boolean prettyPrint) {
      return (ListUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListUser setQuotaUser(String quotaUser) {
      return (ListUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListUser setUserIp(String userIp) {
      return (ListUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private String cursor;

    /**

     */
    public String getCursor() {
      return cursor;
    }

    public ListUser setCursor(String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private Integer limit;

    /**

     */
    public Integer getLimit() {
      return limit;
    }

    public ListUser setLimit(Integer limit) {
      this.limit = limit;
      return this;
    }

  }

  /**
   * Create a request for the method "removeUser".
   *
   * This request holds the parameters needed by the the userendpoint server.  After setting any
   * optional parameters, call the {@link RemoveUser#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveUser removeUser(String id) throws java.io.IOException {
    RemoveUser result = new RemoveUser(id);
    initialize(result);
    return result;
  }

  public class RemoveUser extends UserendpointRequest<com.gottagged380.userendpoint.model.User> {

    private static final String REST_PATH = "user/{id}";

    /**
     * Create a request for the method "removeUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link RemoveUser#execute()} method to invoke the remote
     * operation. <p> {@link RemoveUser#initialize(AbstractGoogleClientRequest)} must be called to
     * initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveUser(String id) {
      super(Userendpoint.this, "DELETE", REST_PATH, null, com.gottagged380.userendpoint.model.User.class);
      this.id = Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveUser setAlt(String alt) {
      return (RemoveUser) super.setAlt(alt);
    }

    @Override
    public RemoveUser setFields(String fields) {
      return (RemoveUser) super.setFields(fields);
    }

    @Override
    public RemoveUser setKey(String key) {
      return (RemoveUser) super.setKey(key);
    }

    @Override
    public RemoveUser setOauthToken(String oauthToken) {
      return (RemoveUser) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveUser setPrettyPrint(Boolean prettyPrint) {
      return (RemoveUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveUser setQuotaUser(String quotaUser) {
      return (RemoveUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveUser setUserIp(String userIp) {
      return (RemoveUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private String id;

    /**

     */
    public String getId() {
      return id;
    }

    public RemoveUser setId(String id) {
      this.id = id;
      return this;
    }

  }

  /**
   * Create a request for the method "updateUser".
   *
   * This request holds the parameters needed by the the userendpoint server.  After setting any
   * optional parameters, call the {@link UpdateUser#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.gottagged380.userendpoint.model.User}
   * @return the request
   */
  public UpdateUser updateUser(com.gottagged380.userendpoint.model.User content) throws java.io.IOException {
    UpdateUser result = new UpdateUser(content);
    initialize(result);
    return result;
  }

  public class UpdateUser extends UserendpointRequest<com.gottagged380.userendpoint.model.User> {

    private static final String REST_PATH = "user";

    /**
     * Create a request for the method "updateUser".
     *
     * This request holds the parameters needed by the the userendpoint server.  After setting any
     * optional parameters, call the {@link UpdateUser#execute()} method to invoke the remote
     * operation. <p> {@link UpdateUser#initialize(AbstractGoogleClientRequest)} must be called to
     * initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.gottagged380.userendpoint.model.User}
     * @since 1.13
     */
    protected UpdateUser(com.gottagged380.userendpoint.model.User content) {
      super(Userendpoint.this, "PUT", REST_PATH, content, com.gottagged380.userendpoint.model.User.class);
    }

    @Override
    public UpdateUser setAlt(String alt) {
      return (UpdateUser) super.setAlt(alt);
    }

    @Override
    public UpdateUser setFields(String fields) {
      return (UpdateUser) super.setFields(fields);
    }

    @Override
    public UpdateUser setKey(String key) {
      return (UpdateUser) super.setKey(key);
    }

    @Override
    public UpdateUser setOauthToken(String oauthToken) {
      return (UpdateUser) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateUser setPrettyPrint(Boolean prettyPrint) {
      return (UpdateUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateUser setQuotaUser(String quotaUser) {
      return (UpdateUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateUser setUserIp(String userIp) {
      return (UpdateUser) super.setUserIp(userIp);
    }

  }

  /**
   * Builder for {@link Userendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport
     * @param jsonFactory JSON factory
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(HttpTransport transport, JsonFactory jsonFactory,
        HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Userendpoint}. */
    @Override
    public Userendpoint build() {
      return new Userendpoint(getTransport(),
          getHttpRequestInitializer(),
          getRootUrl(),
          getServicePath(),
          getObjectParser(),
          getGoogleClientRequestInitializer(),
          getApplicationName(),
          getSuppressPatternChecks());
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    /**
     * Set the {@link UserendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setUserendpointRequestInitializer(
        UserendpointRequestInitializer userendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(userendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
