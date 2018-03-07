package com.nonobank.platformuser.entity.mongoEntity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tangrubei on 2018/2/24.
 */
public class SessionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;


    @Id
    private String _id;
    private String session;
    private Date expires;

    public  SessionEntity(){
        super();
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
