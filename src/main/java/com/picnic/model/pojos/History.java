
package com.picnic.model.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user",
    "version",
    "committed_at",
    "change_status",
    "url"
})
public class History {

    @JsonProperty("user")
    private User user;
    @JsonProperty("version")
    private String version;
    @JsonProperty("committed_at")
    private String committedAt;
    @JsonProperty("change_status")
    private ChangeStatus changeStatus;
    @JsonProperty("url")
    private String url;

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("committed_at")
    public String getCommittedAt() {
        return committedAt;
    }

    @JsonProperty("committed_at")
    public void setCommittedAt(String committedAt) {
        this.committedAt = committedAt;
    }

    @JsonProperty("change_status")
    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    @JsonProperty("change_status")
    public void setChangeStatus(ChangeStatus changeStatus) {
        this.changeStatus = changeStatus;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("user", user).append("version", version).append("committedAt", committedAt).append("changeStatus", changeStatus).append("url", url).toString();
    }

}
