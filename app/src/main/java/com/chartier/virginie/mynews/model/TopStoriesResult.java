package com.chartier.virginie.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 08/10/2018.
 */
public class TopStoriesResult implements ArticleItem{

    // Serialized JSON names
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnail_standard")
    @Expose
    private String thumbnailStandard;
    @SerializedName("short_url")
    @Expose
    private String shortUrl;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;


    //-------------------
    // GETTER & SETTER
    //-------------------

    public String getsection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getArticleTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailStandard() {
        return thumbnailStandard;
    }

    public void setThumbnailStandard(String thumbnailStandard) {
        this.thumbnailStandard = thumbnailStandard;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getpublisheddate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String getUrlImage() {
        if ((getMultimedia() != null) && (!getMultimedia().isEmpty())){
            return getMultimedia().get(0).getUrl();
        }
        else {
                return null;
        }

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSection() {
        return section;
    }

    @Override
    public String getPublishedDate() {
        return publishedDate;
    }

    @Override
    public String getWebUrl() {
        return null;
    }

    @Override
    public String getPubDate() {
        return null;
    }

}
