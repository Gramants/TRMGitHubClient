package demo.ste.mvpcleanarch.model.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import demo.ste.mvpcleanarch.util.Config;

@Entity(tableName = Config.TABLE_REPOS)
public class RepoResponseDbEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "isprivate")
    private Boolean isPrivate;
    @ColumnInfo(name = "htmlUrl")
    private String htmlUrl;
    @ColumnInfo(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RepoResponseDbEntity(Long id, String name, Boolean isPrivate, String htmlUrl, String description) {

        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
        this.htmlUrl = htmlUrl;
        this.description = description;
    }
}