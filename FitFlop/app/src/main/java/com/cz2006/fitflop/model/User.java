package com.cz2006.fitflop.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class to store all the user information for the current user
 */
public class User implements Parcelable{

    private String email;
    private String user_id;
    private String username;
    private String avatar;

    private float height;
    private float weight;

    private ArrayList<StarredItem> starredItems;

    private boolean isHeightWeightInitialized;

    /**
     * Constructor
     * @param email
     * @param user_id
     * @param username
     * @param avatar
     */
    public User(String email, String user_id, String username, String avatar) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.avatar = avatar;

        this.height = 0.0f;
        this.weight = 0.0f;
        this.isHeightWeightInitialized = false;
    }

    /**
     * Constructor
     * @param email
     * @param user_id
     * @param username
     * @param avatar
     * @param height
     * @param weight
     */
    public User(String email, String user_id, String username, String avatar, float height, float weight) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.avatar = avatar;

        this.height = height;
        this.weight = weight;
        this.isHeightWeightInitialized = true;
    }

    /**
     * Constructor
     */
    public User() {

    }

    protected User(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * Getter method for avatar
     * @return
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Setter method for avatar
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    /**
     * Getter method for email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for user id
     * @return
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Setter method for user id
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Getter method for username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for height
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     * Setter method for height
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Getter method for weight
     * @return
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Setter method for weight
     * @param weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Getter method for starred items
     * @return
     */
    public ArrayList<StarredItem> getStarredItems() {
        return starredItems;
    }

    /**
     * Setter method for starred items
     * @param starredItems
     */
    public void setStarredItems(ArrayList<StarredItem> starredItems) {
        this.starredItems = starredItems;
    }

    /**
     * Method to add a starred item
     * @param nameOfGym
     * @param addressOfGym
     */
    public void addStarredItem(String nameOfGym, String addressOfGym) {
        if(getStarredItems()==null){
            starredItems = new ArrayList<StarredItem>();
            setStarredItems(starredItems);
        }

        int j;
        boolean k=false;
        if(starredItems != null) {
            for (j = 0; j < starredItems.size(); j++) {
                if (starredItems.get(j).getName().equals(nameOfGym)) {
                    k = true;
                }
            }
        }

        if (k==false){
            this.starredItems.add(new StarredItem(nameOfGym, addressOfGym));
        }
    }

    /**
     * Method to remove a starred item
     * @param nameOfGym
     */
    public void removeStarredItem(String nameOfGym) {
        int i;
        for (i=0;i<starredItems.size();i++){
            if (starredItems.get(i).getName().equals(nameOfGym)){
                starredItems.remove(i);
                break;
            }
        }
    }

    /**
     * Method to remove all starred items
     * @param starredItem
     */
    public void clearStarredItem(StarredItem starredItem) {
        this.starredItems = new ArrayList<StarredItem>();
    }

    /**
     * Getter method to check if height and weight have been initialised
     * @return
     */
    public boolean isHeightWeightInitialized() {
        return isHeightWeightInitialized;
    }

    /**
     * Setter method to set if height and weight have been initialised
     * @param isHeightWeightInitialized
     */
    public void setHeightWeightInitialized(boolean isHeightWeightInitialized) {
        this.isHeightWeightInitialized = isHeightWeightInitialized;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("User{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'');
        if (height+weight > 0) str.append(", height='" + height + '\'' +
                                           ", weight='" + weight + '\'');
        str.append('}');
        return str.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(avatar);
        dest.writeFloat(height);
        dest.writeFloat(weight);
    }
}

