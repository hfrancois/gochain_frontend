package com.gochain.gochainandroid.model;

import java.util.List;

/**
 * Created by vinguyen on 07/02/2017.
 */

public class Poll {
    private String name;
    private int image;
    private List<PollDetails> pollDetails;

    public Poll() {
    }

    public Poll(String name, int image, List<PollDetails> pollDetails) {
        this.name = name;
        this.image = image;
        this.pollDetails = pollDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PollDetails> getPollDetails() {
        return pollDetails;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPollDetails(List<PollDetails> pollDetails) {
        this.pollDetails = pollDetails;
    }
}