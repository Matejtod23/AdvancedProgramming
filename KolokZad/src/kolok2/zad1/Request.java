package kolok2.zad1;

import java.util.ArrayList;

public class Request {
    private String status;
    private String in_reply_to_id = null;
    private String quote_id = null;
    ArrayList < Object > media_ids = new ArrayList< Object >();
    private boolean sensitive;
    private String spoiler_text;
    private String visibility;
    private String content_type;
    private String poll = null;
    private String scheduled_at = null;
    ArrayList < Object > to = new ArrayList < Object > ();


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getIn_reply_to_id() {
        return in_reply_to_id;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public boolean getSensitive() {
        return sensitive;
    }

    public String getSpoiler_text() {
        return spoiler_text;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getPoll() {
        return poll;
    }

    public String getScheduled_at() {
        return scheduled_at;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIn_reply_to_id(String in_reply_to_id) {
        this.in_reply_to_id = in_reply_to_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public void setSpoiler_text(String spoiler_text) {
        this.spoiler_text = spoiler_text;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public void setPoll(String poll) {
        this.poll = poll;
    }

    public void setScheduled_at(String scheduled_at) {
        this.scheduled_at = scheduled_at;
    }
}