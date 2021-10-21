
package com.picnic.model.pojos;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "url",
    "forks_url",
    "commits_url",
    "id",
    "node_id",
    "git_pull_url",
    "git_push_url",
    "html_url",
    "files",
    "public",
    "created_at",
    "updated_at",
    "description",
    "comments",
    "user",
    "comments_url",
    "owner",
    "forks",
    "history",
    "truncated"
})
public class GistCreatePayload {

    @JsonProperty("url")
    private String url;
    @JsonProperty("forks_url")
    private String forksUrl;
    @JsonProperty("commits_url")
    private String commitsUrl;
    @JsonProperty("id")
    private String id;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("git_pull_url")
    private String gitPullUrl;
    @JsonProperty("git_push_url")
    private String gitPushUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("files")
    private Map<String, Map<String, String>> files;
    @JsonProperty("public")
    private Boolean _public;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("description")
    private String description;
    @JsonProperty("comments")
    private Integer comments;
    @JsonProperty("user")
    private Object user;
    @JsonProperty("comments_url")
    private String commentsUrl;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("forks")
    private List<Object> forks = null;
    @JsonProperty("history")
    private List<History> history = null;
    @JsonProperty("truncated")
    private Boolean truncated;

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("forks_url")
    public String getForksUrl() {
        return forksUrl;
    }

    @JsonProperty("forks_url")
    public void setForksUrl(String forksUrl) {
        this.forksUrl = forksUrl;
    }

    @JsonProperty("commits_url")
    public String getCommitsUrl() {
        return commitsUrl;
    }

    @JsonProperty("commits_url")
    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("node_id")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("node_id")
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("git_pull_url")
    public String getGitPullUrl() {
        return gitPullUrl;
    }

    @JsonProperty("git_pull_url")
    public void setGitPullUrl(String gitPullUrl) {
        this.gitPullUrl = gitPullUrl;
    }

    @JsonProperty("git_push_url")
    public String getGitPushUrl() {
        return gitPushUrl;
    }

    @JsonProperty("git_push_url")
    public void setGitPushUrl(String gitPushUrl) {
        this.gitPushUrl = gitPushUrl;
    }

    @JsonProperty("html_url")
    public String getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    @JsonProperty("files")
    public Map<String, Map<String, String>> getFiles() {
        return files;
    }

    @JsonProperty("files")
    public void setFiles(Map<String, Map<String, String>> files) {
        this.files = files;
    }

    @JsonProperty("public")
    public Boolean getPublic() {
        return _public;
    }

    @JsonProperty("public")
    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("comments")
    public Integer getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @JsonProperty("user")
    public Object getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(Object user) {
        this.user = user;
    }

    @JsonProperty("comments_url")
    public String getCommentsUrl() {
        return commentsUrl;
    }

    @JsonProperty("comments_url")
    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("forks")
    public List<Object> getForks() {
        return forks;
    }

    @JsonProperty("forks")
    public void setForks(List<Object> forks) {
        this.forks = forks;
    }

    @JsonProperty("history")
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
    }

    @JsonProperty("truncated")
    public Boolean getTruncated() {
        return truncated;
    }

    @JsonProperty("truncated")
    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("url", url).append("forksUrl", forksUrl).append("commitsUrl", commitsUrl).append("id", id).append("nodeId", nodeId).append("gitPullUrl", gitPullUrl).append("gitPushUrl", gitPushUrl).append("htmlUrl", htmlUrl).append("files", files).append("_public", _public).append("createdAt", createdAt).append("updatedAt", updatedAt).append("description", description).append("comments", comments).append("user", user).append("commentsUrl", commentsUrl).append("owner", owner).append("forks", forks).append("history", history).append("truncated", truncated).toString();
    }

}
