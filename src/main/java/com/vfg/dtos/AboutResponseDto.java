package com.vfg.dtos;

import java.io.Serializable;

/**
 * Respuesta del controlador about.
 * @author vifergo
 * @since v0.1
 */
public class AboutResponseDto implements Serializable {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -409895913526786306L;

    /**
     * Autor.
     */
    private String author;

    /**
     * Mail.
     */
    private String mail;

    /**
     * Twitter.
     */
    private String twitter;

    /**
     * Version.
     */
    private String version;

    /**
     * @return the author
     */
    public final String getAuthor() {
        return author;
    }

    /**
     * @param paramAuthor the author to set
     */
    public final void setAuthor(final String paramAuthor) {
        this.author = paramAuthor;
    }

    /**
     * @return the mail
     */
    public final String getMail() {
        return mail;
    }

    /**
     * @param paramMail the mail to set
     */

    public final void setMail(final String paramMail) {
        this.mail = paramMail;
    }

    /**
     * @return the twitter
     */
    public final String getTwitter() {
        return twitter;
    }

    /**
     * @param paramTwitter the twitter to set
     */
    public final void setTwitter(final String paramTwitter) {
        this.twitter = paramTwitter;
    }

    /**
     * @return the version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * @param paramVersion the version to set
     */
    public final void setVersion(final String paramVersion) {
        this.version = paramVersion;
    }

}
