package com.intecore.angularcontacts.dto;

import java.util.Date;

public class SearchCriteria {
    private String[] anyName;
    private String firstName;
    private String lastName;
    private String userId;
    private String email;
    private Date dobStart;
    private Date dobEnd;
    
    public SearchCriteria() {
    }
    
    public SearchCriteria(Builder b) {
        this.anyName = b.anyName;
        this.firstName = b.firstName;
        this.lastName = b.lastName;
        this.userId = b.userId;
        this.email = b.email;
        this.dobStart = b.dobStart;
        this.dobEnd = b.dobEnd;
    }

    public String[] getAnyName() {
        return this.anyName;
    }

    public void setAnyName(String[] anyName) {
        this.anyName = anyName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDobStart() {
        return this.dobStart;
    }

    public void setDobStart(Date dobStart) {
        this.dobStart = dobStart;
    }

    public Date getDobEnd() {
        return this.dobEnd;
    }

    public void setDobEnd(Date dobEnd) {
        this.dobEnd = dobEnd;
    }
    
    public static class Builder {
        private String[] anyName;
        private String firstName;
        private String lastName;
        private String userId;
        private String email;
        private Date dobStart;
        private Date dobEnd;
        
        public SearchCriteria build() {
            return new SearchCriteria(this);
        }
        
        public Builder setAnyName(String[] anyName) {
            this.anyName = anyName;
            return this;
        }
        
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }
        
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        
        public Builder setDobStart(Date dobStart) {
            this.dobStart = dobStart;
            return this;
        }
        
        public Builder setDobEnd(Date dobEnd) {
            this.dobEnd = dobEnd;
            return this;
        }
    }
}
